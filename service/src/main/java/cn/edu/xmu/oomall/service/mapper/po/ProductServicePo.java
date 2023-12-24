package cn.edu.xmu.oomall.service.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.service.dao.bo.ProductService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_product_service")
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({ProductService.class})
public class ProductServicePo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为一个实体生成一个唯一标识的主键，主键自增长
    private Long id;

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

    // 服务id
    private Long serviceId;

    // 商铺id
    private Long shopId;

    // 商品id
    private Long productId;

    // 地区id
    private Long regionId;

    // 状态
    private Byte status;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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


    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
    public Long getRegionId() { return regionId; }
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Byte getStatus() { return status; }

    public void setStatus(Byte status) { this.status = status; }
}
