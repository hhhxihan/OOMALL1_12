package cn.edu.xmu.oomall.aftersale.controller;
import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;

//import cn.edu.xmu.oomall.
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.controller.dto.AftersaleDto;
import cn.edu.xmu.oomall.aftersale.controller.vo.*;
import cn.edu.xmu.oomall.aftersale.controller.dto.OrderItemDto;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.service.AftersaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController /*Restful的Controller对象*/
@RequestMapping(produces = "application/json;charset=UTF-8")
public class CustomerAftersaleController {

    private final Logger logger = LoggerFactory.getLogger(CustomerAftersaleController.class);
    private final AftersaleService aftersaleService;

    @Autowired
    public CustomerAftersaleController(AftersaleService aftersaleService) {
        this.aftersaleService = aftersaleService;
    }

    //顾客通过orderId创建售后单：
    @PostMapping("/aftersales/createAftersale/{id}")
//    @Audit(departName = "customer"),@LoginUser UserDto user
    public ReturnObject CreateAftersale(@PathVariable Long id,@RequestBody  OrderItemVo vo) {

        UserDto user=new UserDto();
        OrderItemDto dto=CloneFactory.copy(new OrderItemDto(),vo);
        dto.setOrderItemId(id);
        Aftersale aftersale=aftersaleService.createAftersale(dto,user);
        return new ReturnObject(CloneFactory.copy(new OrderItemDto(),aftersale));
    }



//    //顾客查询所有可申请的订单
//    @GetMapping("/aftersales/orderitms/{id}")
////    @Audit(departName = "orders")
//    public void getAllOrder(
//            @PathVariable(value = "id",required = true) Long id,
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "10") Integer pageSize
//    ){
////        List<Order> orders = this.orderService.findByCustomerId(id, page, pageSize);
////        List<orderDto> ret = orders.stream().map(obj -> CloneFactory.copy(new orderDto(), obj)).collect(Collectors.toList());
////        return new ReturnObject(new PageDto<>(ret, page, pageSize));
//    }
//
//    //顾客查询所有可售后
//    @GetMapping("/aftersales/SearchAll/{id}")
////    @Audit(departName = "orders")
//    public ReturnObject getAllAftersale( @PathVariable(value = "id",required = true) Long id,
//                @RequestParam(defaultValue = "0") Integer page,
//                @RequestParam(defaultValue = "10") Integer pageSize)
//    {
//        List<Aftersale> aftersales = this.aftersaleService.findByCustomerId(id, page, pageSize);
//        List<AftersaleDto> ret = aftersales.stream().map(obj -> CloneFactory.copy(new AftersaleDto(), obj)).collect(Collectors.toList());
//        return new ReturnObject(new PageDto<>(ret, page, pageSize));
//    }
//
//    //顾客更新售后单信息

//
//    //顾客删除售后单
//    @DeleteMapping("/aftersales/cancleAftersale/{id}")
////    @Audit(departName = "orders")
//    public ReturnObject cancleAftersale( @PathVariable(value = "id",required = true) Long id,
//                                         @RequestParam CancleVo vo,@LoginUser UserDto user)
//    {
//        Aftersale aftersale = aftersaleService.findById(id);
//        if(!aftersale.beFinsh(user)){
//        throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "取消售后单", id));
//        }
//        aftersale.setConclusion(vo.getConclusion());
//        aftersaleService.updateById(aftersale,user);
//        return new ReturnObject(ReturnNo.OK);
//    }

}
