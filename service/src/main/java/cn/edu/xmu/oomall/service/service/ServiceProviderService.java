package cn.edu.xmu.oomall.service.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.ServiceOrderDao;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ServiceProviderService {
    private static  final Logger logger = LoggerFactory.getLogger(ServiceProviderService.class);

    private ServiceProviderDao serviceProviderDao;
    @Autowired
    private ServiceOrderDao serviceOrderDao;

    @Autowired
    public ServiceProviderService(ServiceProviderDao serviceProviderDao){
        this.serviceProviderDao = serviceProviderDao;
    }


    /**
     * 服务商注册账号
     */
    public ServiceProvider createServiceProvider(ServiceProvider serviceProvider, UserDto user){
        List<ServiceProvider> serviceProviders = this.serviceProviderDao.retrieveByCreatorId(user.getId(), 1, 10);
        if(serviceProviders.size()!=0) {
            throw new BusinessException(ReturnNo.SERVICEPROVIDER_USER_HASSHOP, String.format(ReturnNo.SERVICEPROVIDER_USER_HASSHOP.getMessage(), user.getId()));
        }
        ServiceProvider ret = this.serviceProviderDao.insert(serviceProvider, user);
        return ret;
    }

    /**
     * 服务商查看账户信息
     */
    public ServiceProvider findServiceProviderById(Long id){
        ServiceProvider ret = this.serviceProviderDao.findById(id);
        return ret;
    }

    /**
     * 服务商修改账户信息
     */
    public ServiceProvider updateServiceProvider(ServiceProvider serviceProvider, UserDto user){
        ServiceProvider oldServiceProvider = this.serviceProviderDao.findById(serviceProvider.getId());
        if(oldServiceProvider.getStatus().equals(ServiceProvider.ABANDON)){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "服务商", serviceProvider.getId(), serviceProvider.getStatusName()));
        }
        serviceProvider.setStatus(oldServiceProvider.getStatus());
        ServiceProvider ret = this.serviceProviderDao.save(serviceProvider, user);
        return ret;
    }
}
