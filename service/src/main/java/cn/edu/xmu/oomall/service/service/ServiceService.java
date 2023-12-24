package cn.edu.xmu.oomall.service.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.dao.ProductServiceDao;
import cn.edu.xmu.oomall.service.dao.bo.Services;
import cn.edu.xmu.oomall.service.dao.bo.ProductService;
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
public class ServiceService {
    private static final Logger logger = LoggerFactory.getLogger(ServiceService.class);

    private ServiceDao serviceDao;
    private ProductServiceDao productServiceDao;

    @Autowired
    public ServiceService(ServiceDao serviceDao, ProductServiceDao productServiceDao) {
        this.serviceDao = serviceDao;
        this.productServiceDao = productServiceDao;
    }

    /**
     * 商户取消服务商的所有服务
     */
    public void cancelAllService(Long shopId, Long serviceProviderId, UserDto user){
        List<Services> serviceList=this.serviceDao.findByServiceProviderId(serviceProviderId,1, 10);
        for (Services obj : serviceList){
            obj.setServiceDao(this.serviceDao);
            obj.setProductServiceDao(this.productServiceDao);
            obj.cancelProductServiceByShop(shopId);
        }
    }

}
