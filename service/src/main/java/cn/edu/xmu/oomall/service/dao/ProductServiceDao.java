package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.dao.bo.ProductService;
import cn.edu.xmu.oomall.service.dao.bo.Services;
import cn.edu.xmu.oomall.service.mapper.ProductServicePoMapper;
import cn.edu.xmu.oomall.service.mapper.po.ProductServicePo;
import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.mapper.po.ServicePo;
import lombok.Setter;
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

import static cn.edu.xmu.javaee.core.model.Constants.IDNOTEXIST;

@Repository
@RefreshScope
public class ProductServiceDao {
    private static final Logger logger = LoggerFactory.getLogger(ServiceDao.class);

    public static final String KEY = "A%d";

    @Value("${oomall.service.timeout}")
    private long timeout;

    private RedisUtil redisUtil;

    private ProductServicePoMapper productServicePoMapper;
    @Lazy
    private ServiceDao serviceDao;

    private ProductServiceDao productServiceDao;

    @Autowired
    @Lazy
    public ProductServiceDao(ProductServicePoMapper productServicePoMapper, ProductServiceDao productServiceDao,ServiceDao serviceDao, RedisUtil redisUtil) {
        this.productServicePoMapper = productServicePoMapper;
        this.serviceDao = serviceDao;
        this.productServiceDao = productServiceDao;

        this.redisUtil = redisUtil;
    }

    public ProductService build(ProductServicePo po, String redisKey) {
        ProductService bo = CloneFactory.copy(new ProductService(), po);
        if (null != redisKey) {
            redisUtil.set(redisKey, bo, timeout);
        }
        this.build(bo);
        return bo;
    }
    private ProductService build(ProductService bo) {
        bo.setProductServiceDao(this);
        bo.setServiceDao(this.serviceDao);
        return bo;
    }

    public ProductService findById(Long id){
        if (id.equals(null)) {
            throw new IllegalArgumentException("findById: id is null");
        }
        logger.debug("findObjById: id = {}",id);
        String key = String.format(KEY, id);
        ProductService productService = (ProductService) redisUtil.get(key);
        if (!Objects.isNull(productService)) {
            productService = this.build(productService);
        } else {
            Optional<ProductServicePo> ret = this.productServicePoMapper.findById(id);
            if (ret.isPresent()){
                productService = this.build(ret.get(), key);
            }else{
                throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "商品服务", id));
            }
        }
        return productService;
    }

    /**
     * 按照服务和商铺找到对象
     */
    public List<ProductService> findByServiceIdAndShopId(Long serviceId, Long shopId,  Integer page, Integer pageSize) throws RuntimeException{
        List<ProductService> ret = new ArrayList<>();
        if (null == serviceId) {
            throw new IllegalArgumentException("serviceId can not be null.");
        }
        if (null == shopId) {
            throw new IllegalArgumentException("shopId can not be null.");
        }
        Pageable pageable = PageRequest.of(page-1, pageSize);
        List<ProductServicePo> pos = this.productServicePoMapper.findByServiceIdEqualsAndShopIdEquals(serviceId,shopId,pageable);
        ret = pos.stream().map(po -> CloneFactory.copy(new ProductService(),po)).collect(Collectors.toList());
        logger.info("bos size:{}", ret.size());
        return ret;
    }

    /**
     * 按照服务和状态找到对象
     */
    public List<ProductService> findByServiceIdAndStatus(Long serviceId, Integer page, Integer pageSize) throws RuntimeException{
        List<ProductService> ret = new ArrayList<>();
        if (null == serviceId) {
            throw new IllegalArgumentException("serviceId can not be null.");
        }

        Pageable pageable = PageRequest.of(page-1, pageSize);
        List<ProductServicePo> pos = this.productServicePoMapper.findByServiceIdEqualsAndStatusEquals(serviceId,ProductService.VALID,pageable);
        ret = pos.stream().map(po -> CloneFactory.copy(new ProductService(),po)).collect(Collectors.toList());
        logger.info("bos size:{}", ret.size());
        return ret;
    }

    /**
     * 按照服务、地区和商铺找到对象
     */
    public List<ProductService> findByProductIdAndRegionIdAndShopIdAndStatus(Long productId, Long regionId, Long shopId,  Integer page, Integer pageSize) throws RuntimeException{
        List<ProductService> ret = new ArrayList<>();
        if (null == productId) {
            throw new IllegalArgumentException("productId can not be null.");
        }
        if (null == regionId) {
            throw new IllegalArgumentException("regionId can not be null.");
        }
        if (null == shopId) {
            throw new IllegalArgumentException("shopId can not be null.");
        }
        Pageable pageable = PageRequest.of(page-1, pageSize);
        List<ProductServicePo> pos = this.productServicePoMapper.findByProductIdEqualsAndRegionIdEqualsAndShopIdEquals(productId,regionId,shopId,pageable);
        ret = pos.stream().map(po -> CloneFactory.copy(new ProductService(),po)).collect(Collectors.toList());
        logger.info("bos size:{}", ret.size());
        return ret;
    }

    /**
     * 商户选择服务
     */
    public ProductService insert(ProductService productService, UserDto userDto) throws RuntimeException{
        productService.setCreator(userDto);
        productService.setGmtCreate(LocalDateTime.now());
        productService.setStatus(ProductService.VALID);
        ProductServicePo po = CloneFactory.copy(new ProductServicePo(), productService);
        po.setId(null);
        logger.debug("insert: po = {}", po);
        this.productServicePoMapper.save(po);
        productService.setId(po.getId());
        return productService;
    }

    /**
     * 商户修改服务
     */
    public String save(ProductService productService, UserDto userDto) throws RuntimeException{
        if (productService.getId().equals(null)){
            throw new IllegalArgumentException("save: productService id is null");
        }
        String key = String.format(KEY, productService.getId());
        productService.setModifier(userDto);
        productService.setGmtModified(LocalDateTime.now());
        ProductServicePo po = CloneFactory.copy(new ProductServicePo(),productService);
        logger.debug("save: po = {}", po);
        this.productServicePoMapper.save(po);
        build(po, key);
        return key;
    }

    /**
     * 商户取消服务
     */
    public void delete(Long id) throws RuntimeException {
        if (!this.productServicePoMapper.existsById(id))
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "商品类目", id));
        this.productServicePoMapper.deleteById(id);
    }
}
