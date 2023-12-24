package cn.edu.xmu.oomall.service.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.controller.dto.ServiceOrderDto;
import cn.edu.xmu.oomall.service.controller.dto.ServiceOrdersDto;
import cn.edu.xmu.oomall.service.controller.vo.ServiceOrderVo;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.service.ServiceOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ServiceOrderController {
    private final Logger logger = LoggerFactory.getLogger(ServiceOrderController.class);

    private ServiceOrderService serviceOrderService;

    @Autowired
    public ServiceOrderController(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    /**
     * 服务商查询未接单的服务单部分信息（符合查询条件）
     */
    @GetMapping("/maintainers/{did}/services")
    //@Audit(departName = "maintainers")
    public ReturnObject retrieveAllServiceOrders(@PathVariable("did") Long id, @RequestParam(required = false,defaultValue = "1") Integer page, @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        List<ServiceOrder> serviceOrders = this.serviceOrderService.findAllServiceOrders(id, page, pageSize);
        List<ServiceOrdersDto> dtos = serviceOrders.stream().map(bo -> new ServiceOrdersDto(bo.getId(),bo.getType(), bo.getDescription())).collect(Collectors.toList());
        return new ReturnObject(new PageDto<>(dtos, page, pageSize));
    }

    /**
     * 服务商根据服务单id查询服务单信息（自己的）
     */
    @GetMapping("/maintainers/{did}/services/{id}")
    @Audit(departName = "maintainers")
    public ReturnObject findServiceOrderById(@PathVariable("did") Long serviceProviderId, @PathVariable("id") Long id){
        ServiceOrder serviceOrder = serviceOrderService.findServiceOrderById(serviceProviderId,id);
        return new ReturnObject(new ServiceOrderDto(serviceOrder));
    }

    /**
     * 服务商接受服务单
     */
    @PutMapping("/maintainers/{did}/services/{id}/accept")
    @Audit(departName = "maintainers")
    public ReturnObject acceptServiceOrderById(@PathVariable("did") Long serviceProviderId, @PathVariable("id") Long id, @Validated @RequestBody ServiceOrderVo vo, @LoginUser UserDto user) {
        ServiceOrder serviceOrder = CloneFactory.copy(new ServiceOrder(), vo);
        serviceOrder.setMaintainerName(vo.getMaintainerName());
        serviceOrder.setMaintainerMobile(vo.getMaintainerMobile());
        this.serviceOrderService.acceptServiceOrder(serviceProviderId, id, serviceOrder, user);
        return new ReturnObject(ReturnNo.OK);
    }

    /**
     * 服务商取消服务单
     */
    @PutMapping("/maintainers/{did}/services/{id}/cancel")
    @Audit(departName = "maintainers")
    public ReturnObject cancelServiceOrderById(@PathVariable("did") Long serviceProviderId, @PathVariable("id") Long id, @LoginUser UserDto user) {
        this.serviceOrderService.cancelServiceOrder(serviceProviderId, id, user);
        return new ReturnObject(ReturnNo.OK);
    }
}
