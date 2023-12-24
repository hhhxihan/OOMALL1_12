package cn.edu.xmu.oomall.service.controller;

import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/shops/{did}", produces = "application/json;charset=UTF-8")
public class ShopServiceController {

    private ServiceService serviceService;

    @Autowired
    public ShopServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }


    /**
     * 商户取消服务商所有服务
     */
    @DeleteMapping("/maintainers/{id}/cancel")
    //@Audit(departName = "shops")
    public ReturnObject cancelAllService(@PathVariable("did") Long shopId,
                                         @PathVariable("id") Long serviceProviderId,
                                         @LoginUser UserDto user){
        this.serviceService.cancelAllService(shopId, serviceProviderId, user);
        return new ReturnObject(ReturnNo.OK);
    }
}
