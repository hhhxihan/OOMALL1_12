package cn.edu.xmu.oomall.aftersale.service;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.controller.dto.ApproveDto;
import cn.edu.xmu.oomall.aftersale.controller.dto.OrderItemDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.aftersale.controller.dto.AftersaleDto;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(propagation = Propagation.REQUIRED)
@CopyFrom({OrderItemDto.class})
public class AftersaleService {
    private final Logger logger = LoggerFactory.getLogger(AftersaleService.class);

    @Value("${oomall.aftersale.timeout}")
    private int timeout;

    private final AftersaleDao aftersaleDao;
    private final RedisUtil redisUtil;

    @Autowired
    public AftersaleService(AftersaleDao aftersaleDao, RedisUtil redisUtil) {
        this.aftersaleDao = aftersaleDao;
        this.redisUtil = redisUtil;
    }


    public Aftersale createAftersale(OrderItemDto dto, UserDto customer) {
        Aftersale bo=aftersaleDao.findByOrderItemId(dto.getOrderItemId());
        Aftersale aftersale1=CloneFactory.copy(new Aftersale(),dto);
        if(null==bo){
            Aftersale aftersale=new Aftersale();
            aftersale.setAftersaleDao(this.aftersaleDao);
            return aftersale.createAftersale(aftersale1,customer);
        }
        else {
            throw new BusinessException(ReturnNo.AFTERSALE_ISEXISTING, String.format(ReturnNo.AFTERSALE_ISEXISTING.getMessage()));
        }

    }

    public Boolean approveAftersale(Long id, ApproveDto dto, UserDto user){
        Aftersale bo=aftersaleDao.findById(id);
        if(bo.getStatus()!=Aftersale.APPLY){
            bo.approve(dto,user);
            return true;
        }
        else{
            throw new BusinessException(ReturnNo.AFTERSALE_APPROVEFALIED, String.format(ReturnNo.AFTERSALE_APPROVEFALIED.getMessage()));
        }
    }

//    /**
//     * 通过id更新售后单
//     *
//     * @param aftersale 地区
//     * @param user      登录用户
//     */
//
//    public void updateById(Aftersale aftersale, UserDto user) {
//        Aftersale bo = this.aftersaleDao.findById(aftersale.getId());
//        logger.debug("updateRegionById: bo = {}", bo);
//
//        String key = this.aftersaleDao.save(aftersale, user);
//        this.redisUtil.del(key);
//    }
//
//    /**
//     * 根据id删除售后单
//     *
//     * @param id 售后单id
//     */
//    public void deleteRegion(Long id) {
//
//        String key = this.aftersaleDao.deleteById(id);
//        this.redisUtil.del(key);
//    }
//
//
//    /**
//     * 获取所有售后单状态
//     *
//     * @return
//     */
//    public List<StatusDto> retrieveRegionsStates() {
//        return Aftersale.STATUSNAMES.keySet().stream().map(key -> new StatusDto(key, Aftersale.STATUSNAMES.get(key))).collect(Collectors.toList());
//    }
//
//    /**
//     * 通过id查找地区
//     *
//     * @param id 地区id
//     * @return RegionDto
//     */
//    public Aftersale findById(Long id) {
//        logger.debug("findRegionById: id = {}", id);
//        return this.aftersaleDao.findById(id);
//    }
//
//    /**
//     * 通过shopid查找售后单
//     *
//     * @return List<Aftersale>
//     */
//    public List<Aftersale> findByShopId(Long id, Integer page, Integer pageSize) {
//        logger.debug("findAftersaleByShopId: id = {}", id);
//        return this.aftersaleDao.findAftersaleByShopId(id, true, page, pageSize);
//    }
//
//    /**
//     * 通过customerid查找售后单
//     *
//     * @return List<Aftersale>
//     */
//    public List<Aftersale> findByCustomerId(Long id, Integer page, Integer pageSize) {
//        logger.debug("findAftersaleByShopId: id = {}", id);
//        return this.aftersaleDao.findAftersaleByCustomerId(id, true, page, pageSize);
//    }
//
//    public List<Aftersale> findByServiceId(Long id, Integer page, Integer pageSize) {
//        logger.debug("findAftersaleByShopId: id = {}", id);
//        return this.aftersaleDao.findAftersaleByServiceId(id, true, page, pageSize);
//    }
//
//    public String createAftersale(List<OrderItemVo> items, AftersaleDto dto, UserDto customer) {
//
//        Aftersale bo = CloneFactory.copy(new Aftersale(), dto);
//        return new String(aftersaleDao.save(bo, customer));
//
//    }


}
