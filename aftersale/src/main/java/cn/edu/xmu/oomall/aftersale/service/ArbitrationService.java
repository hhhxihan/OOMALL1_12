package cn.edu.xmu.oomall.aftersale.service;

import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.ArbitrationDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ArbitrationService {
    private final Logger logger = LoggerFactory.getLogger(ArbitrationService.class);

    @Value("3600")
    private int timeout;

    private ArbitrationDao arbitrationDao = null;
    private AftersaleDao aftersaleDao = null;
    private final RedisUtil redisUtil;

    @Autowired
    public ArbitrationService(ArbitrationDao arbitrationDao, AftersaleDao aftersaleDao,RedisUtil redisUtil) {
        this.arbitrationDao = arbitrationDao;
        this.aftersaleDao = aftersaleDao;
        this.redisUtil = redisUtil;
    }




    /**
     * 创建仲裁单
     * @param id aftersale id
     * @param arbitration arbitration对象
     * @param user 登录用户
     * @return 新arbitration对象，带id
     */
    public Arbitration createArbitration(Long id, Arbitration arbitration, UserDto user) {


        Aftersale aftersale = this.aftersaleDao.findById(id);
        Arbitration newArbitration=aftersale.createArbitrationByAftersale(arbitration, user);
        return newArbitration;
    }
    /**
     * 取消仲裁单
     */
    public void cancelArbitrationById(Long id, UserDto user) {
        Arbitration arbitration = this.arbitrationDao.findById(id);

        String key= arbitration.cancelArbitration(user);
        this.redisUtil.del(key);
    }
    /**
     * 应诉仲裁
     * @param arbitrationId 仲裁单
     * @param shopReply 商户回应
     * @param user 登录用户
     */
    /*public void appealArbitration(Long arbitrationId, String shopReply, UserDto user) {
        Arbitration arbitration = this.arbitrationDao.findById(arbitrationId);
        String key = arbitration.addReply(user,shopReply);
        this.redisUtil.del(key);
    }*/
    /**
     * 仲裁结案
     * @param id 仲裁单
     * @param result 仲裁结果
     * @param user 登录用户
     */
    /*public void finishArbitrationById(Long id, String result, UserDto user) {
        Arbitration arbitration = this.arbitrationDao.findById(id);
        String key = arbitration.finishArbitration(user,result);
        this.redisUtil.del(key);
    }*/
    /**
     * 受理仲裁
     * @param id 仲裁单
     * @param arbitrator 仲裁员信息
     * @param user 登录用户
     */
   /* public void acceptArbitrationById(Long id, IdNameTypeDto arbitrator, UserDto user) {
        Arbitration arbitration = this.arbitrationDao.findById(id);
        arbitration.setArbitratorId(arbitrator.getId());
        arbitration.setArbitratorName(arbitrator.getName());
        String key = arbitration.changeStatus(arbitration.getStatus(),user);
        this.redisUtil.del(key);
    }*/

    /**
     * 查询申请还未受理的仲裁单

     * @return
     */
    /*public List<Arbitration> retrieveArbitrations(Integer page, Integer pageSize) {
        //查询申请还未受理的仲裁单
        return this.arbitrationDao.retrieveByStatus(Arbitration.APPLY,page, pageSize);
    }*/
    /**
     * 根据id返回仲裁单
     *

     * @param id     仲裁单Id
     * @return
     */
   /* public Arbitration findById(Long id) throws BusinessException {
        return this.arbitrationDao.findById(id);
    }*/
/*
    */
/**
     * 根据状态查询仲裁单

     * @return
     *//*

    public List<Arbitration> retrieveArbitrationsBystatus(Byte status,Integer page, Integer pageSize) {

        return this.arbitrationDao.getArbitrationByStatus(status,page,pageSize);
    }
*/

    /**
     * 修改已有仲裁单
     * 已经完成的仲裁单不能修改
     * @param id     仲裁单Id
     *//*
    public void updateArbitrationById(Long id, Arbitration arbitration, UserDto modifier) {

        Arbitration oldArbitration = this.arbitrationDao.findById(id);
        //如果该仲裁单的状态是已经结案了，不允许修改
        if(oldArbitration.getStatus().equals(Arbitration.FINISH)){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "仲裁单", oldArbitration.getId(), "已结案"));
        }
        if (this.arbitrationDao == null){
            throw new IllegalArgumentException("ArbitrationDao.cancel: arbitrationDao is null");
        }
        arbitration.setId(id);
        arbitration.setStatus(Arbitration.APPLY);

        this.arbitrationDao.save(arbitration, modifier);
    }*/
}
