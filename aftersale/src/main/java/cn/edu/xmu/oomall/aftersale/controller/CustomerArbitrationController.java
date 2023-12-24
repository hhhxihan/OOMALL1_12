package cn.edu.xmu.oomall.aftersale.controller;
//import cn.edu.xmu.javaee.core.util.CloneFactory;

import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.aftersale.controller.dto.ArbitrationDto;
import cn.edu.xmu.oomall.aftersale.controller.vo.ArbitrationVo;
import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import cn.edu.xmu.oomall.aftersale.service.ArbitrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 仲裁顾客管理器
 */
@RestController /*Restful的Controller对象*/
@RequestMapping(produces = "application/json;charset=UTF-8")
@Transactional
public class CustomerArbitrationController {
    private final Logger logger = LoggerFactory.getLogger(CustomerArbitrationController.class);
    private ArbitrationService arbitrationService;

    @Autowired
    public CustomerArbitrationController(ArbitrationService arbitrationService) {
        this.arbitrationService = arbitrationService;
    }
    /**
     * 顾客申请仲裁
     *
     */
    @PostMapping("/aftersales/{id}/arbitrations")
    //@Audit(departName = "shops")
    public ReturnObject createArbitration(@PathVariable Long id,
                                          @LoginUser UserDto user,
                                          @RequestBody ArbitrationVo vo) {

        //UserDto user=new UserDto();
        Arbitration arbitration = CloneFactory.copy(new Arbitration(), vo);
        Arbitration newArbitration = this.arbitrationService.createArbitration(id, arbitration, user);

        ArbitrationDto dto = CloneFactory.copy(new ArbitrationDto(), newArbitration);

        return new ReturnObject(ReturnNo.CREATED, dto);
    }

/**
 * 顾客取消仲裁
 *
 */
@DeleteMapping("/aftersales/delete/arbitrations/{id}")
//@Audit(departName = "shops")
public ReturnObject deleteArbitrationById(@PathVariable Long id,
                                     @LoginUser UserDto user) {

    this.arbitrationService.cancelArbitrationById(id, user);
    return new ReturnObject(ReturnNo.OK);
}

    /**
     * 顾客更新仲裁单信息
     *
     */
  /*  @PutMapping("/arbitrations/{id}")
    @Audit(departName = "shops")
    public ReturnObject updateArbitration(@PathVariable Long id,
                                          @LoginUser UserDto modifier,
                                          @RequestBody ArbitrationVo vo) {

        Arbitration arbitration = Common.cloneObj(vo, Arbitration.class);
        arbitrationService.updateArbitrationById(id, arbitration, modifier);
        return new ReturnObject();
    }*/
}
