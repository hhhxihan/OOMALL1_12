package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.mapper.ServiceOrderPoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ServiceOrderPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RefreshScope
public class ServiceOrderDao {
    private static final Logger logger = LoggerFactory.getLogger(ServiceOrderDao.class);

    public static final String KEY = "%d";

    @Value("${oomall.service.timeout}")
    private long timeout;

    private final ServiceOrderPoMapper serviceOrderPoMapper;

    private RedisUtil redisUtil;

    @Autowired
    public ServiceOrderDao(ServiceOrderPoMapper serviceOrderPoMapper, RedisUtil redisUtil) {
        this.serviceOrderPoMapper = serviceOrderPoMapper;
        this.redisUtil = redisUtil;
    }

    public ServiceOrder build(ServiceOrder bo) {
        bo.setServiceOrderDao(ServiceOrderDao.this);
        return bo;
    }

    public ServiceOrder build(ServiceOrderPo po, String redisKey) {
        ServiceOrder bo = CloneFactory.copy(new ServiceOrder(), po);
        if (null != redisKey) {
            redisUtil.set(redisKey, bo, timeout);
        }
        this.build(bo);
        return bo;
    }

    /**
     * 按照id获得对象
     */
    public ServiceOrder findById(Long id){
        if (id.equals(null)) {
            throw new IllegalArgumentException("findById: id is null");
        }
        logger.debug("findObjById: id = {}",id);
        String key = String.format(KEY, id);
        ServiceOrder serviceOrder = (ServiceOrder) redisUtil.get(key);
        if (!Objects.isNull(serviceOrder)) {
            serviceOrder = this.build(serviceOrder);
        } else {
            Optional<ServiceOrderPo> ret = this.serviceOrderPoMapper.findById(id);
            if (ret.isPresent()){
                serviceOrder = this.build(ret.get(),key);
            }else{
                throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "服务单", id));
            }
        }
        return serviceOrder;
    }

    /**
     * 按照productId和regionId和shopId获取服务单信息
     */
    public List<ServiceOrder> findByProductIdAndRegionIdAndShopIdAndStatus(Long productId, Long regionId, Long shopId, Integer page, Integer pageSize) throws RuntimeException{
        List<ServiceOrder> ret = new ArrayList<>();
        if (null == productId) {
            throw new IllegalArgumentException("productId can not be null.");
        }
        Pageable pageable = PageRequest.of(page-1, pageSize);
        List<ServiceOrderPo> pos = this.serviceOrderPoMapper.findByProductIdEqualsAndRegionIdEqualsAndShopIdEqualsAndStatusEquals(productId,regionId,shopId,ServiceOrder.UNACCEPTED,pageable);
        ret = pos.stream().map(po -> CloneFactory.copy(new ServiceOrder(),po)).collect(Collectors.toList());
        logger.info("bos size:{}", ret.size());
        return ret;
    }

    /**
     * 插入服务单
     */
    public ServiceOrder insert(ServiceOrder serviceOrder, UserDto userDto) throws RuntimeException{
        serviceOrder.setCreator(userDto);
        serviceOrder.setGmtCreate(LocalDateTime.now());
        serviceOrder.setStatus(ServiceOrder.UNACCEPTED);
        ServiceOrderPo po = CloneFactory.copy(new ServiceOrderPo(), serviceOrder);
        po.setId(null);
        logger.debug("insert: po = {}", po);
        this.serviceOrderPoMapper.save(po);
        serviceOrder.setId(po.getId());
        return serviceOrder;
    }

    /**
     * 修改服务单状态
     */
    public String save(ServiceOrder serviceOrder, UserDto userDto) throws RuntimeException{
        if (serviceOrder.getId().equals(null)){
            throw new IllegalArgumentException("save: serviceOrder id is null");
        }
        String key = String.format(KEY, serviceOrder.getId());
        serviceOrder.setModifier(userDto);
        serviceOrder.setGmtModified(LocalDateTime.now());
        if(serviceOrder.getStatus().equals(ServiceOrder.FINISHED)){
            serviceOrder.setGmtEnd(LocalDateTime.now());
        }
        ServiceOrderPo po = CloneFactory.copy(new ServiceOrderPo(),serviceOrder);
        logger.debug("save: po = {}", po);
        this.serviceOrderPoMapper.save(po);
        logger.debug("status = {}", po.getStatus());
        build(po, key);
        return key;
    }
}
