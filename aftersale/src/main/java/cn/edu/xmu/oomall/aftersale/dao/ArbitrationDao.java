package cn.edu.xmu.oomall.aftersale.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import cn.edu.xmu.oomall.aftersale.mapper.ArbitrationPoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.ArbitrationPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import static cn.edu.xmu.javaee.core.model.Constants.IDNOTEXIST;

@Repository
@RefreshScope
public class ArbitrationDao {
    private final static Logger logger = LoggerFactory.getLogger(ArbitrationDao.class);

    @Value("3600")
    private int timeout;

    public static final String KEY = "A%d";
    //@Autowired
    private ArbitrationPoMapper arbitrationPoMapper;
    //@Autowired
    private RedisUtil redisUtil;
    @Lazy
    private AftersaleDao aftersaleDao;

    private ArbitrationDao arbitrationDao;
    @Autowired
    @Lazy
    public ArbitrationDao(ArbitrationDao arbitrationDao,ArbitrationPoMapper arbitrationPoMapper, RedisUtil redisUtil,AftersaleDao aftersaleDao) {
        this.arbitrationPoMapper = arbitrationPoMapper;
        this.redisUtil = redisUtil;
        this.aftersaleDao=aftersaleDao;
        this.arbitrationDao=arbitrationDao;
    }

    /*public void build(Arbitration bo) {
        bo.setArbitrationDao(ArbitrationDao.this);
    }*/

    public Arbitration build(ArbitrationPo po, Optional<String> redisKey) {
        Arbitration bo = CloneFactory.copy(new Arbitration(), po);
        this.build(bo);
        redisKey.ifPresent(key -> redisUtil.set(key, bo, timeout));
        return bo;
    }
    private Arbitration build(Arbitration bo){
        bo.setArbitrationDao(this);
        bo.setAftersaleDao(this.aftersaleDao);
        return bo;
    }
    /**
     * 创建仲裁单
     *
     * @param bo   仲裁单bo
     * @param user 登录用户
     */
    public Arbitration insert(Arbitration bo, UserDto user) throws RuntimeException {
        bo.setId(null);
        bo.setCreator(user);
        LocalDateTime currentTime = LocalDateTime.now();
        bo.setGmtCreate(currentTime);

        ArbitrationPo po = CloneFactory.copy(new ArbitrationPo(), bo);

        logger.debug("save: po = {}", po);
        po = arbitrationPoMapper.save(po);
        bo.setId(po.getId());
        return bo;
    }
    /**
     * 按照主键获得对象
     * @param id
     * @return
     * @throws RuntimeException
     */
    public Arbitration findById(Long id) {
        if (null == id) {
            throw new IllegalArgumentException("ArbitrationDao.findById: id is null");
        }
        logger.debug("findObjById: id = {}", id);
        String key = String.format(KEY, id);
        if (redisUtil.hasKey(key)) {
            Arbitration arbitration = (Arbitration) redisUtil.get(key);
            this.build(arbitration);
            return arbitration;
        }  else {
            Optional<ArbitrationPo> poOpt = this.arbitrationPoMapper.findById(id);
            if (!poOpt.isPresent()) {
                throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "仲裁单", id));
            }
            return this.build(poOpt.get(), Optional.of(key));
        }

    }

    /**
     * 按照id更新仲裁单
     */
    public String save(Arbitration arbitration, UserDto user) {
        ArbitrationPo po = CloneFactory.copy(new ArbitrationPo(), arbitration);
        arbitration.setModifier(user);
        arbitration.setGmtModified(LocalDateTime.now());
        logger.debug("save: po = {}", po);
        ArbitrationPo updatePo = arbitrationPoMapper.save(po);
        if (IDNOTEXIST.equals(updatePo.getId())) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "仲裁单", arbitration.getId()));
        }
        return String.format(KEY, arbitration.getId());
    }

    //根据id删除仲裁
    public String delete(Long id) throws RuntimeException {

        if (!this.arbitrationPoMapper.existsById(id))
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "商品类目", id));
        this.arbitrationPoMapper.deleteById(id);
        return String.format(KEY, id);
    }
}
