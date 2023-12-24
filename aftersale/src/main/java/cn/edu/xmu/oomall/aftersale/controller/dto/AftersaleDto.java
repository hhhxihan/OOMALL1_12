package cn.edu.xmu.oomall.aftersale.controller.dto;


import cn.edu.xmu.javaee.core.aop.CopyFrom;

import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@CopyFrom({Aftersale.class})
public class AftersaleDto {
    private Long id;
    private Long orderItemId;
    private Long customerId;
    private Long shopId;
    private String aftersaleSn;
    private Byte type;
    private String reason;
    private String conclusion;
    private Integer quantity;
    private Long regionId;
    private String address;
    private String consignee;
    private String mobile;
    private Byte status;
    private Long serviceId;
    private String serialNo;
    private String name;
    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Integer inArbitrated;

    public AftersaleDto(Aftersale aftersale) {
        super();
//        CloneFactory.copy(this, aftersale);
//        //设置返回对象属性
//        this.setShop1(IdNameTypeDto.builder().id(product.getShop().getId()).name(product.getShop().getName()).build());
//        this.setOtherProducts1(product.getOtherProduct().stream().map(o -> IdNameTypeDto.builder().id(o.getId()).name(o.getName()).build()).collect(Collectors.toList()));
//        this.setCategory1(IdNameTypeDto.builder().id(product.getCategory().getId()).name(product.getCategory().getName()).build());
//        this.setTemplate1(IdNameTypeDto.builder().id(product.getTemplate().getId()).name(product.getTemplate().getName()).build());
//        this.setCreator(IdNameTypeDto.builder().id(product.getCreatorId()).name(product.getCreatorName()).build());
//        this.setModifier(IdNameTypeDto.builder().id(product.getModifierId()).name(product.getModifierName()).build());
//    }


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

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

    public String getAftersaleSn() {
        return aftersaleSn;
    }

    public void setAftersaleSn(String aftersaleSn) {
        this.aftersaleSn = aftersaleSn;
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

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getInArbitrated() {
        return inArbitrated;
    }

    public void setInArbitrated(Integer inArbitrated) {
        this.inArbitrated = inArbitrated;
    }
}

