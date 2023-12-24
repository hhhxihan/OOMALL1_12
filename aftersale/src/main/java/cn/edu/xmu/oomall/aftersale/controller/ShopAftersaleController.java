package cn.edu.xmu.oomall.aftersale.controller;

import cn.edu.xmu.oomall.aftersale.controller.dto.ApproveDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;
import cn.edu.xmu.javaee.core.model.dto.PageDto;
import cn.edu.xmu.javaee.core.model.dto.UserDto;

import cn.edu.xmu.oomall.aftersale.controller.vo.ApproveVo;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.service.AftersaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/aftersales", produces = "application/json;charset=UTF-8")
public class ShopAftersaleController {

    private final Logger logger = LoggerFactory.getLogger(ShopAftersaleController.class);
    private final AftersaleService aftersaleService;

    @Autowired
    public ShopAftersaleController(AftersaleService aftersaleService) {
        this.aftersaleService = aftersaleService;
    }

    //审核售后单
    @PutMapping("/{id}/approve")
//    @Audit(departName = "shops")
    public ReturnObject approveAftersale(@PathVariable Long id, ApproveVo vo,@LoginUser UserDto user) {
        ApproveDto dto=CloneFactory.copy(new ApproveDto(),vo);
        aftersaleService.approveAftersale(id,dto,user);
        return new ReturnObject(ReturnNo.OK);
    }
//
//    //根据id查询售后单
//    @GetMapping("/shopInquire/{id}")
//    //@Audit(departName = "shops")  //, @LoginUser UserDto user
//    public ReturnObject getsAftersaleById(@PathVariable Long id) {
//
//        Aftersale aftersale = this.aftersaleService.findById(id);
//        return new ReturnObject(CloneFactory.copy(new AftersaleDto(), aftersale));
//    }
//
//    //获取所有的售后单
//    @GetMapping("/shop/{did}/")
////    @Audit(departName = "shops")
//    public ReturnObject getsAllAftersaleById(@PathVariable Long did,
//                                             @RequestParam(required = false, defaultValue = "1") Integer page,
//                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
//
//        List<Aftersale> aftersaleList = this.aftersaleService.findByShopId(did, page, pageSize);
//        return new ReturnObject(new PageDto<>(aftersaleList.stream().map(o -> IdNameTypeDto.builder().id(o.getId()).name(o.getName()).build()).collect(Collectors.toList()), page, pageSize));
//    }
//

//
//    //取消售后单
//    @PutMapping("/shop/{id}/cancle")
////    @Audit(departName = "shops") , @LoginUser UserDto user
//    public ReturnObject cancleAftersale(@PathVariable Long id, CancleVo vo) {
//        Aftersale aftersale = aftersaleService.findById(id);
//        UserDto user=new UserDto();
//        if (!aftersale.beFinsh(user)) {
//            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "状态不能改变", id));
//        }
//        aftersale.setConclusion(vo.getConclusion());
//        return new ReturnObject(ReturnNo.OK);
//    }

//    //确认收货
//    @PutMapping("/{did}/recieve/{id}")
////    @Audit(departName = "shops")
//    public ReturnObject recieveConfirm(@PathVariable Long did, @PathVariable Long id,
//                                       ApproveVo vo, @LoginUser UserDto user) {
//
//
//        return new ReturnObject(ReturnNo.OK);
//    }
}


