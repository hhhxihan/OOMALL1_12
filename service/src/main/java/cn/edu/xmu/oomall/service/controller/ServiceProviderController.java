package cn.edu.xmu.oomall.service.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.validation.NewGroup;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.controller.dto.ServiceProviderDto;
import cn.edu.xmu.oomall.service.controller.vo.ServiceProviderVo;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import cn.edu.xmu.oomall.service.service.ServiceProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.edu.xmu.javaee.core.model.Constants.NOSERVICEPROVIDER;
import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ServiceProviderController {
    private final Logger logger = LoggerFactory.getLogger(ServiceProviderController.class);

    private ServiceProviderService serviceProviderService;

    @Autowired
    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    /**
     * 服务商申请账号(ok)
     */
    @PostMapping("/maintainers")
    @Audit(departName = "maintainers")
    public ReturnObject createServiceProviders(@Validated(NewGroup.class) @RequestBody ServiceProviderVo serviceProviderVo, @LoginUser UserDto user) {
        if(NOSERVICEPROVIDER != user.getDepartId() || PLATFORM == user.getDepartId()) {
            throw new BusinessException(ReturnNo.SERVICEPROVIDER_USER_HASSHOP, String.format(ReturnNo.SERVICEPROVIDER_USER_HASSHOP.getMessage(), user.getId()));
        }
        ServiceProvider serviceProvider = CloneFactory.copy(new ServiceProvider(), serviceProviderVo);

        serviceProvider.setName(serviceProviderVo.getName());
        serviceProvider.setConsignee(serviceProviderVo.getConsignee());
        serviceProvider.setAddress(serviceProviderVo.getAddress());
        serviceProvider.setMobile(serviceProviderVo.getMobile());

        ServiceProvider ret = this.serviceProviderService.createServiceProvider(serviceProvider, user);
        ServiceProviderDto dto = new ServiceProviderDto(serviceProvider);
        return new ReturnObject(ReturnNo.CREATED, dto);
    }

    /**
     * 服务商修改账户信息(ok)
     */
    @PutMapping("/maintainers/{id}")
    @Audit(departName = "maintainers")
    public ReturnObject updateServiceProvider(@PathVariable("id") Long id, @Validated @RequestBody ServiceProviderVo vo, @LoginUser UserDto user){
        ServiceProvider serviceProvider = CloneFactory.copy(new ServiceProvider(), vo);
        serviceProvider.setId(id);
        serviceProvider.setName(vo.getName());
        serviceProvider.setConsignee(vo.getConsignee());
        serviceProvider.setAddress(vo.getAddress());
        serviceProvider.setMobile(vo.getMobile());
        ServiceProvider ret = this.serviceProviderService.updateServiceProvider(serviceProvider, user);
        return new ReturnObject(new ServiceProviderDto(ret));
    }

    /**
     * 服务商查看账户信息(ok)
     */
    @GetMapping("/maintainers/{id}")
    @Audit(departName = "maintainers")
    public ReturnObject findServiceProviderById(@PathVariable("id") Long id){
        ServiceProvider serviceProvider = serviceProviderService.findServiceProviderById(id);
        return new ReturnObject(new ServiceProviderDto(serviceProvider));
    }

}
