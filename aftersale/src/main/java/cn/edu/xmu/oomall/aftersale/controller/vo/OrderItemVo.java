package cn.edu.xmu.oomall.aftersale.controller.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;


public class OrderItemVo implements Serializable{

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



    public Long getShopId() {
        return shopId;
    }

    public String getAddress() {
        return address;
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



    public String getMobile() {
        return mobile;
    }


}
