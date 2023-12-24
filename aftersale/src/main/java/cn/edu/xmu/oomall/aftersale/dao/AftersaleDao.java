package cn.edu.xmu.oomall.aftersale.dao;

import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.mapper.AftersalePoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import jakarta.annotation.Resource;
import org.hibernate.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.IDNOTEXIST;

@ComponentScan(basePackages = "cn.edu.xmu.oomall.aftersale.mapper")
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@Repository
@RefreshScope
public class AftersaleDao {
    private final static Logger logger = LoggerFactory.getLogger(AftersaleDao.class);
    private final static String KEY = "R%d";



    @Value("${oomall.aftersale.timeout}")
    private int timeout;

    //    @Autowired
    private  AftersalePoMapper aftersalePoMapper;
    //    @Autowired
    private  RedisUtil redisUtil;
    @Lazy
    private ArbitrationDao arbitrationDao;
    private AftersaleDao aftersaleDao;
    @Autowired
    @Lazy
    public AftersaleDao(AftersaleDao aftersaleDao,AftersalePoMapper aftersalePoMapper, RedisUtil redisUtil,ArbitrationDao arbitrationDao) {
        this.aftersalePoMapper = aftersalePoMapper;
        this.arbitrationDao=arbitrationDao;
        this.aftersaleDao=aftersaleDao;
        this.redisUtil = redisUtil;
    }

/*    public void build(Aftersale bo) {
        bo.setAftersaleDao(AftersaleDao.this);
    }*/

    public Aftersale build(AftersalePo po, Optional<String> redisKey) {
        Aftersale bo = cn.edu.xmu.javaee.core.util.CloneFactory.copy(new Aftersale(), po);
        this.build(bo);
        redisKey.ifPresent(key -> redisUtil.set(key, bo, timeout));
        return bo;
    }

    private  Aftersale build(Aftersale bo){
        bo.setArbitrationDao(this.arbitrationDao);
        bo.setAftersaleDao(this);
        return bo;
    }



     public Aftersale findByOrderItemId(Long id){
        logger.debug("findByOrderItemId: id = {}", id);
        if(null == id) {
            throw new IllegalArgumentException("id can not be null");
        }
        String key = String.format(KEY, id);
        Aftersale bo = null;
        if (null != bo) {
            logger.debug("findByOrderItemId: hit in redis key = {}, region = {}", key, bo);
            this.build(bo);
            return bo;
        }else {
            Optional<AftersalePo> ret = aftersalePoMapper.findByOrderItemId(id);
            if (ret.isPresent()) {
                logger.debug("findByOrderItemId: retrieve from database region = {}", ret.get());
                return this.build(ret.get(), Optional.of(key));
            } else {
                return null;
            }
        }
    }
    /**
     * 通过售后单id查找售后
     *
     * @param id
     * @return Aftersale
     * @throws RuntimeException
     */
    public Aftersale findById(Long id){
        logger.debug("findById: id = {}", id);
        if(null == id) {
            throw new IllegalArgumentException("id can not be null");
        }
        String key = String.format(KEY, id);
        Aftersale bo = (Aftersale) redisUtil.get(key); //从redis缓存中查找舒服，非关系数据库
        if (null != bo) {
            logger.debug("findById: hit in redis key = {}, region = {}", key, bo);
            this.build(bo);
            return bo;
        }else {  //如果没有，那么访问数据库
            Optional<AftersalePo> ret = aftersalePoMapper.findById(id);
            if (ret.isPresent()) {
                logger.debug("findById: retrieve from database region = {}", ret.get());
                return this.build(ret.get(), Optional.of(key));
            } else {
                throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "售后单", id));
            }
        }
    }


//    public List<Aftersale> findAftersaleByCustomerId(Long customerId, Boolean all, Integer page, Integer pageSize) throws RuntimeException {
//        logger.debug("retrieveSubRegionsByPid: customerid = {}", customerId);
//        if (null == customerId) {
//            throw new IllegalArgumentException("custormerId can not be null.");
//        }
//
//        Pageable pageable = PageRequest.of(page - 1, pageSize);
//        List<AftersalePo> poPage;
//        poPage = this.aftersalePoMapper.findByCustomerId(customerId, pageable);
//        return poPage.stream()
//                .map(po -> this.build(po, Optional.ofNullable(null)))
//                .collect(Collectors.toList());
//    }
//


//    public List<Aftersale> findAftersaleByShopId(Long shopId, Boolean all, Integer page, Integer pageSize) throws RuntimeException {
//        logger.debug("retrieveSubRegionsByPid: shopid = {}", shopId);
//        if (null == shopId) {
//            throw new IllegalArgumentException("shopId can not be null.");
//        }
//
//        Pageable pageable = PageRequest.of(page - 1, pageSize);
//        List<AftersalePo> poPage;
//        poPage = this.aftersalePoMapper.findByShopId(shopId, pageable);
//        return poPage.stream()
//                .map(po -> this.build(po, Optional.ofNullable(null)))
//                .collect(Collectors.toList());
//    }


//    public List<Aftersale> findAftersaleByServiceId(Long serviceId, Boolean all, Integer page, Integer pageSize) throws RuntimeException {
//        logger.debug("retrieveSubRegionsByPid: shopid = {}", serviceId);
//        if (null == serviceId) {
//            throw new IllegalArgumentException("shopId can not be null.");
//        }
//
//        Pageable pageable = PageRequest.of(page - 1, pageSize);
//        List<AftersalePo> poPage;
//        poPage = this.aftersalePoMapper.findByServiceId(serviceId, pageable);
//        return poPage.stream()
//                .map(po -> this.build(po, Optional.ofNullable(null)))
//                .collect(Collectors.toList());
//    }


    public Aftersale insert(Aftersale bo, UserDto user) throws RuntimeException {

        bo.setCreator(user);
        bo.setGmtCreate(LocalDateTime.now());
        Random t=new Random();
        bo.setId(t.nextLong());
//        bo.setId(null);
        AftersalePo po = CloneFactory.copy(new AftersalePo(), bo);
        logger.debug("save: po = {}", po);
        po = aftersalePoMapper.save(po);
        bo.setId(po.getId());
        return bo;
    }


    public String save(Aftersale bo, UserDto user) throws RuntimeException {
        bo.setModifier(user);
        bo.setGmtModified(LocalDateTime.now());
        AftersalePo po = CloneFactory.copy(new AftersalePo(), bo);
        logger.debug("save: po = {}", po);
        AftersalePo updatePo = aftersalePoMapper.save(po);
        if(IDNOTEXIST.equals(updatePo.getId())) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "售后单", bo.getId()));
        }
        return String.format(KEY, bo.getId());
    }

    /**
     * 删除售后信息
     *
     * @param id 售后单id
     * @return
     */
//    public String deleteById(Long id)throws RuntimeException {
//
//        if (!this.aftersalePoMapper.existsById(id))
//            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "售后单", id));
//        this.aftersalePoMapper.deleteById(id);
//        return String.format(KEY, id);
//    }

}