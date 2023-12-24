package cn.edu.xmu.oomall.aftersale.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.controller.vo.OrderItemVo;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;



@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@CopyFrom({OrderItemVo.class,Aftersale.class})
public class OrderItemDto implements Serializable {
    private Long OrderItemId;
    private Byte type;
    private String reason;
    private Long regionId;
    private String mobile;
    private Long customerId;
    private Long shopId;
    private String address;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getOrderItemId() {
        return OrderItemId;
    }

    public void setOrderItemId(Long orderID) {
        OrderItemId = orderID;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

