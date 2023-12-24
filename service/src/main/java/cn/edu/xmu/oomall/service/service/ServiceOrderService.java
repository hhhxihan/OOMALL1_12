package cn.edu.xmu.oomall.service.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.ProductServiceDao;
import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.dao.ServiceOrderDao;
import cn.edu.xmu.oomall.service.dao.bo.ProductService;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.dao.bo.Services;
import cn.edu.xmu.oomall.service.dao.openfeign.FreightExpressDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ServiceOrderService {
    private final Logger logger = LoggerFactory.getLogger(ServiceOrderService.class);

    private ServiceOrderDao serviceOrderDao;
    @Autowired
    private ServiceDao serviceDao;
    @Autowired
    private ProductServiceDao productServiceDao;
    @Autowired
    private FreightExpressDao freightExpressDao;

    @Autowired
    public ServiceOrderService(ServiceOrderDao serviceOrderDao){
        this.serviceOrderDao = serviceOrderDao;
    }

    /**
     * 服务商查询符合条件的服务单
     */
    public List<ServiceOrder> findAllServiceOrders(Long serviceProviderId, Integer page, Integer pageSize) {
        List<ServiceOrder> serviceOrders = new ArrayList<>();
        // 查询符合条件的服务
        List<Services> services = this.serviceDao.findByServiceProviderId(serviceProviderId, page, pageSize);
        for (Services service : services) {
            logger.debug("111djyyyID: {}", service.getId());
        }
        // 遍历每个服务，查询对应的产品服务
        for (Services service : services) {
            Long serviceId = service.getId();
            List<ProductService> productServices = this.productServiceDao.findByServiceIdAndStatus(serviceId, page, pageSize);
            for (ProductService productService : productServices) {
                logger.debug("222djyyyID: {}", productService.getId());
            }
            // 遍历每个产品服务，查询对应的服务单
            for (ProductService productService : productServices) {
                Long productId = productService.getProductId();
                Long regionId = productService.getRegionId();
                Long shopId = productService.getShopId();
                List<ServiceOrder> orders = this.serviceOrderDao.findByProductIdAndRegionIdAndShopIdAndStatus(productId, regionId, shopId, page, pageSize);
                for (ServiceOrder order : orders) {
                    logger.debug("333djyyyID: {}", order.getId());
                }
                serviceOrders.addAll(orders);
            }
        }
        return serviceOrders;
    }

    /**
     * 服务商查看服务单信息
     */
    public ServiceOrder findServiceOrderById(Long serviceProviderId, Long id){
        ServiceOrder ret = this.serviceOrderDao.findById(id);
        if(ret.getServiceProviderId() == null || !ret.getServiceProviderId().equals(serviceProviderId)){
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT, String.format(ReturnNo.AUTH_NO_RIGHT.getMessage()));
        }
        return ret;
    }

    /**
     * 服务商接受服务单
     */
    public void acceptServiceOrder(Long serviceProviderId, Long id, ServiceOrder newServiceOrder, UserDto user){
        ServiceOrder serviceOrder = this.serviceOrderDao.findById(id);
        List<ProductService> productServices = this.productServiceDao.findByProductIdAndRegionIdAndShopIdAndStatus(serviceOrder.getProductId(), serviceOrder.getRegionId(), serviceOrder.getShopId(), 1, 10);
        List<Services> services = this.serviceDao.findByServiceProviderId(serviceProviderId, 1, 10);

        for (ProductService productService : productServices) {
            for (Services service : services) {
                if(service.getId() == productService.getServiceId()){
                   if(!serviceOrder.getStatus().equals(ServiceOrder.UNACCEPTED)){
                            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "服务单", serviceOrder.getId(), serviceOrder.getStatusName()));
                    }
                    serviceOrder.setServiceProviderId(serviceProviderId);
                    logger.debug("000status = {}", serviceOrder.getStatus());
                    serviceOrder.accept(user);
                    logger.debug("111status = {}", serviceOrder.getStatus());
                    serviceOrder.setMaintainerMobile(newServiceOrder.getMaintainerMobile());
                    serviceOrder.setMaintainerName(newServiceOrder.getMaintainerName());
                    this.serviceOrderDao.save(serviceOrder, user);
                    if(serviceOrder.getType().equals(ServiceOrder.REPAIR)){
                        this.freightExpressDao.createFreightExpress(serviceOrder);
                    }
                    return;
                }
            }
        }
        throw new IllegalArgumentException("serviceOrder can not be accepted.");
    }


    /**
     * 服务商取消服务单
     */
    public void cancelServiceOrder(Long serviceProviderId, Long id, UserDto user){
        ServiceOrder serviceOrder = this.serviceOrderDao.findById(id);
        if(serviceOrder.getStatus().equals(ServiceOrder.FINISHED) || serviceOrder.getServiceProviderId() != serviceProviderId){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "服务单", serviceOrder.getId(), serviceOrder.getStatusName()));
        }
        if(serviceOrder.getType().equals(ServiceOrder.REPAIR) && serviceOrder.getStatus().equals(ServiceOrder.RECEIVED)){
            this.freightExpressDao.createFreightExpress(serviceOrder);
        }
        ServiceOrder newServiceOrder = serviceOrder.cancel(serviceOrder,user);
        this.serviceOrderDao.insert(newServiceOrder, user);
    }
}
