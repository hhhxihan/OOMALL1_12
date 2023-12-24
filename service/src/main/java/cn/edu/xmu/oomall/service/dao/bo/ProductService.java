package cn.edu.xmu.oomall.service.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.PostConstruct;
import cn.edu.xmu.oomall.service.dao.ProductServiceDao;
import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.mapper.po.ProductServicePo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({ProductServicePo.class})
public class ProductService extends OOMallObject implements Serializable {
    // 选择
    @JsonIgnore
    @ToString.Exclude
    public static final Byte VALID = 0;
    // 暂停
    @JsonIgnore
    @ToString.Exclude
    public static final Byte SUSPENDED = 1;

    /**
     * 状态和名称的对应
     */
    @JsonIgnore
    @ToString.Exclude
    public static final Map<Byte, String> STATUSNAMES = new HashMap() {
        {
            put(VALID, "可用");
            put(SUSPENDED, "暂停");
        }
    };

    /**
     * 允许的状态迁移
     */
    @JsonIgnore
    @ToString.Exclude
    private static final Map<Byte, Set<Byte>> toStatus = new HashMap<>() {
        {
            put(VALID, new HashSet<>() {
                {
                    add(SUSPENDED);
                }
            });
            put(SUSPENDED, new HashSet<>() {
                {
                    add(VALID);
                }
            });
        }
    };
    /**
     * 是否允许状态迁移
     */
    /*
    public boolean allowStatus(Byte status) {
        boolean ret = false;

        if (null != status && null != this.status) {
            Set<Byte> allowStatusSet = toStatus.get(this.status);
            if (null != allowStatusSet) {
                ret = allowStatusSet.contains(status);
            }
        }
        return ret;
    }
    */

    @Setter
    @JsonIgnore
    @ToString.Exclude
    public ProductServiceDao productServiceDao;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    public ServiceDao serviceDao;

    /**
     * 取消商品服务
     */
    public void cancel(){
        if (null == this.productServiceDao){
            throw new IllegalArgumentException("ProductService.cancel:productServiceDao is null");
        }
        //if(this.productServiceDao!=null) {
            this.productServiceDao.delete(this.id);
        //}
    }

    /**
     * 获得当前状态名称
     */
    @JsonIgnore
    public String getStatusName() {
        return STATUSNAMES.get(this.status);
    }

    private Long id;

    // 服务id
    private Long serviceId;
    // 商铺id
    private Long shopId;
    // 产品id
    private Long productId;
    // 地区id
    private Long regionId;
    // 服务状态
    private Byte status;

    @ToString.Exclude
    @JsonIgnore
    private ProductService productService;


    /**
     * 创建者id
     */
    private Long creatorId;

    /**
     * 创建者
     */
    private String creatorName;

    /**
     * 修改者id
     */
    private Long modifierId;

    /**
     * 修改者
     */
    private String modifierName;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    public Long getServiceId() { return serviceId;}
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    public Long getShopId() { return shopId;}
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
    public Long getProductId() { return productId;}
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getRegionId() { return regionId;}
    public void setRegionId(Long regionId) { this.regionId = regionId; }
    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

}
