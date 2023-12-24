package cn.edu.xmu.oomall.aftersale.controller;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.validation.NewGroup;
import cn.edu.xmu.oomall.aftersale.controller.vo.ApproveVo;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.service.AftersaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/shops/{did}", produces = "application/json;charset=UTF-8")
public class AdminAftersaleController {

    private final Logger logger = LoggerFactory.getLogger(AdminAftersaleController.class);
    private final AftersaleService aftersaleService;

    @Autowired
    public AdminAftersaleController(AftersaleService aftersaleService) {
        this.aftersaleService = aftersaleService;
    }

//    //管理员根据id查询售后单
//    @GetMapping("/aftersales/{id}")
////    @Audit(departName = "shops")
//    public ReturnObject getAftersaleById(@PathVariable Long did, @PathVariable Long id) {
//        if (!PLATFORM.equals(did)) { //管理员id
//            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "管理员查找", id, did));
//        }
//        Aftersale aftersale=this.aftersaleService.findById(id);
//
//        return new ReturnObject(CloneFactory.copy(new AftersaleDto(), aftersale));
//    }
//
//    @PostMapping("/aftersales/{id}/confirm")
////    @Audit(departName = "shops")
//    public ReturnObject AdminconfirmAftersale(@PathVariable Long did, @PathVariable Long id, @LoginUser UserDto user,
//                                         @Validated(NewGroup.class) @RequestBody ApproveVo vo) {
//        if (!PLATFORM.equals(did)) {
//            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "售后管理员", id, did));
//        }
//        if(vo.getresult()) {
//            Aftersale aftersale=aftersaleService.findById(id);
//            aftersale.beGoing(user);
//            aftersale.setConclusion(vo.getcommit());
//        }
//        else{
//            Aftersale aftersale=aftersaleService.findById(id);
//            aftersale.setConclusion(vo.getcommit());
//            aftersaleService.updateById(aftersale,user);
//        }
//
//        return new ReturnObject(ReturnNo.OK);
//    }


}
