package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import cn.edu.xmu.oomall.service.mapper.ServiceProviderPoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ServiceProviderPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RefreshScope
public class ServiceProviderDao {
    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderDao.class);

    public static final String KEY = "D%d";

    @Value("${oomall.service.timeout}")
    private long timeout;

    private RedisUtil redisUtil;

    private ServiceProviderPoMapper serviceProviderPoMapper;

    @Autowired
    public ServiceProviderDao(ServiceProviderPoMapper serviceProviderPoMapper, RedisUtil redisUtil) {
        this.serviceProviderPoMapper = serviceProviderPoMapper;
        this.redisUtil = redisUtil;
    }

    public ServiceProvider build(ServiceProvider bo) {
        bo.setServiceProviderDao(ServiceProviderDao.this);
        return bo;
    }

    public ServiceProvider build(ServiceProviderPo po, String redisKey) {
        ServiceProvider bo = CloneFactory.copy(new ServiceProvider(), po);
        if (null != redisKey) {
            redisUtil.set(redisKey, bo, timeout);
        }
        this.build(bo);
        return bo;
    }

    /**
     * 按照id获得对象
     */
    public ServiceProvider findById(Long id){
        if (id.equals(null)) {
            throw new IllegalArgumentException("findById: id is null");
        }
        String key = String.format(KEY, id);
        ServiceProvider serviceProvider = (ServiceProvider) redisUtil.get(key);
        if (!Objects.isNull(serviceProvider)) {
            serviceProvider = this.build(serviceProvider);
        } else {
            Optional<ServiceProviderPo> ret = this.serviceProviderPoMapper.findById(id);
            if (ret.isPresent()){
                serviceProvider = this.build(ret.get(), key);
            }else{
                throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "服务商", id));
            }
        }
        return serviceProvider;
    }

    /**
     * 按照创建者找到对象
     */
    public List<ServiceProvider> retrieveByCreatorId(Long creatorId, Integer page, Integer pageSize) throws RuntimeException{
        List<ServiceProvider> ret = new ArrayList<>();
        Pageable pageable = PageRequest.of(page-1, pageSize);
        List<ServiceProviderPo> pos =  this.serviceProviderPoMapper.findByCreatorId(creatorId, pageable);
        ret = pos.stream().map(po -> CloneFactory.copy(new ServiceProvider(),po)).collect(Collectors.toList());
        logger.info("bos size:{}", ret.size());
        return ret;
    }

    /**
     * 插入服务商
     */
    public ServiceProvider insert(ServiceProvider serviceProvider, UserDto userDto) throws RuntimeException{
        serviceProvider.setCreator(userDto);
        serviceProvider.setCreatorId(userDto.getId());
        serviceProvider.setCreatorName(userDto.getName());
        serviceProvider.setGmtCreate(LocalDateTime.now());
        serviceProvider.setStatus(ServiceProvider.NEW);
        ServiceProviderPo po = CloneFactory.copy(new ServiceProviderPo(), serviceProvider);
        po.setId(null);
        po = serviceProviderPoMapper.save(po);
        serviceProvider.setId(po.getId());
        logger.debug("111: serviceProvider.getId = {}", serviceProvider.getId());
        logger.debug("111: serviceProvider.getCreatorId = {}", serviceProvider.getCreatorId());
        logger.debug("111: serviceProvider.getGmtCreate = {}", serviceProvider.getGmtCreate());
        return serviceProvider;
    }

    /**
     * 服务商修改账户信息
     */
    public ServiceProvider save(ServiceProvider serviceProvider, UserDto userDto) throws RuntimeException{
        if (serviceProvider.getId().equals(null)){
            throw new IllegalArgumentException("save: serviceProvider id is null");
        }
        String key = String.format(KEY, serviceProvider.getId());
        serviceProvider.setModifier(userDto);
        serviceProvider.setModifierId(userDto.getId());
        serviceProvider.setModifierName(userDto.getName());
        serviceProvider.setGmtModified(LocalDateTime.now());
        ServiceProviderPo po = CloneFactory.copy(new ServiceProviderPo(),serviceProvider);
        po = serviceProviderPoMapper.save(po);
        build(po, key);
        logger.debug("222: serviceProvider.getId = {}", serviceProvider.getId());
        logger.debug("222: serviceProvider.getCreatorId = {}", serviceProvider.getCreatorId());
        logger.debug("222: serviceProvider.getGmtCreate = {}", serviceProvider.getGmtCreate());
        logger.debug("222: serviceProvider.getModifierId = {}", serviceProvider.getModifierId());
        logger.debug("222: serviceProvider.getGmtModified = {}", serviceProvider.getGmtModified());
        return serviceProvider;
    }
}
