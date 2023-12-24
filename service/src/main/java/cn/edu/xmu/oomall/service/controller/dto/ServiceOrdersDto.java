package cn.edu.xmu.oomall.service.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@CopyFrom({ServiceOrder.class})
public class ServiceOrdersDto {
    private Long id;
    // 种类
    private Byte type;
    // 描述
    private String description;
    private Long productId;
    private Long regionId;
    private Long shopId;

    public ServiceOrdersDto(Long id, Byte type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public Byte getType() {
        return type;
    }
    public void setType(Byte type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) { this.description = description; }
    public Long getProductId(){ return productId; }
    public void setProductId(Long productId){ this.productId = productId; }
    public Long getRegionId(){ return regionId; }
    public void setRegionId(Long regionId){ this.regionId = regionId; }
    public Long getShopId(){ return shopId; }
    public void setShopId(Long shopId){ this.shopId = shopId; }

}
