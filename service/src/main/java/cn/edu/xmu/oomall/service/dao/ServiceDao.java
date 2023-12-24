package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.dao.bo.Services;
import cn.edu.xmu.oomall.service.mapper.ServicePoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ServicePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
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
public class ServiceDao {
    private static final Logger logger = LoggerFactory.getLogger(ServiceDao.class);

    public static final String KEY = "A%d";

    @Value("${oomall.service.timeout}")
    private long timeout;

    private RedisUtil redisUtil;
    private ServiceDao serviceDao;
    private ServicePoMapper servicePoMapper;
    @Lazy
    private ProductServiceDao productServiceDao;

    @Autowired
    @Lazy
    public ServiceDao(ServicePoMapper servicePoMapper, ProductServiceDao productServiceDao, ServiceDao serviceDao, RedisUtil redisUtil) {
        this.servicePoMapper = servicePoMapper;
        this.productServiceDao = productServiceDao;
        this.serviceDao = serviceDao;
        this.redisUtil = redisUtil;
    }


    public Services build(ServicePo po, String redisKey) {
        Services bo = CloneFactory.copy(new Services(), po);
        if (null != redisKey) {
            redisUtil.set(redisKey, bo, timeout);
        }
        this.build(bo);
        return bo;
    }
    private Services build(Services bo) {
        bo.setProductServiceDao(this.productServiceDao);
        bo.setServiceDao(this);
        return bo;
    }
    /**
     * 按照id获得对象
     *
     * @param id service id
     * @return service
     */
    public Services findById(Long id){
        if (id.equals(null)) {
            throw new IllegalArgumentException("findById: id is null");
        }
        logger.debug("findObjById: id = {}",id);
        String key = String.format(KEY, id);
        Services service = (Services) redisUtil.get(key);
        if (!Objects.isNull(service)) {
            service = this.build(service);
        } else {
            Optional<ServicePo> ret = this.servicePoMapper.findById(id);
            if (ret.isPresent()){
                service = this.build(ret.get(), key);
            }else{
                throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "服务", id));
            }
        }
        return service;
    }

    /**
     * 按照服务商和状态找到对象
     *
     * @param serviceProviderId
     * @return List<Services>
     */
    public List<Services> findByServiceProviderId(Long serviceProviderId, Integer page, Integer pageSize) throws RuntimeException {
        List<Services> ret = new ArrayList<>();
        if (null == serviceProviderId) {
            throw new IllegalArgumentException("serviceProviderId can not be null.");
        }
        Pageable pageable = PageRequest.of(page-1, pageSize);
        List<ServicePo> pos =  this.servicePoMapper.findByServiceProviderIdEqualsAndStatusEquals(serviceProviderId, Services.VALID, pageable);
        ret = pos.stream().map(po -> CloneFactory.copy(new Services(),po)).collect(Collectors.toList());
        logger.info("bos size:{}", ret.size());
        return ret;
    }

    /**
     * 插入服务
     */
    public Services insert(Services service, UserDto userDto) throws RuntimeException{
        service.setCreator(userDto);
        service.setGmtCreate(LocalDateTime.now());
        service.setStatus(Services.VALID);
        ServicePo po = CloneFactory.copy(new ServicePo(), service);
        po.setId(null);
        logger.debug("insert: po = {}", po);
        this.servicePoMapper.save(po);
        service.setId(po.getId());
        return service;
    }

    /**
     * 服务商修改服务信息
     */
    public String save(Services service, UserDto userDto) throws RuntimeException{
        if (service.getId().equals(null)){
            throw new IllegalArgumentException("save: service id is null");
        }
        String key = String.format(KEY, service.getId());
        service.setModifier(userDto);
        service.setGmtModified(LocalDateTime.now());
        ServicePo po = CloneFactory.copy(new ServicePo(),service);
        logger.debug("save: po = {}", po);
        this.servicePoMapper.save(po);
        build(po, key);
        return key;
    }

}
