package cn.edu.xmu.oomall.service.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.dto.IdNameTypeDto;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({ServiceOrder.class})
public class ServiceOrderDto {
    // 服务单id
    private Long id;
    // 种类
    private Byte type;
    // 服务地址
    private String address;
    // 联系人姓名
    private String consignee;
    // 联系人电话
    private String mobile;
    // 维修人姓名
    private String maintainer_name;
    // 维修人电话
    private String maintainer_mobile;
    // 状态
    private Byte status;
    // 描述
    private String description;
    // 服务单创建时间
    private LocalDateTime gmtCreate;
    // 服务单接受时间
    private LocalDateTime gmtModified;
    // 收件时间
    private LocalDateTime gmtReceived;
    // 服务结束时间
    private LocalDateTime gmtEnd;
    // 服务结果
    private String result;
    private IdNameTypeDto creator;
    private IdNameTypeDto modifier;
    // 服务商id
    private Long serviceProviderId;
    // 产品id
    private Long productId;
    // 地区id
    private Long regionId;
    // 商铺id
    private Long shopId;

    public ServiceOrderDto(ServiceOrder serviceOrder){
        super();
        CloneFactory.copy(this, serviceOrder);
        this.creator = IdNameTypeDto.builder().id(serviceOrder.getCreatorId()).name(serviceOrder.getCreatorName()).build();
        this.modifier = IdNameTypeDto.builder().id(serviceOrder.getModifierId()).name(serviceOrder.getModifierName()).build();
    }

    public Long getId() { return id;}
    public void setId(Long id) {
        this.id = id;
    }
    public Byte getType() {
        return type;
    }
    public void setType(Byte type) {
        this.type = type;
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
    public String getMaintainer_name() {
        return maintainer_name;
    }
    public void setMaintainer_name(String maintainer_name) {
        this.maintainer_name = maintainer_name;
    }
    public String getMaintainer_mobile() {
        return maintainer_mobile;
    }
    public void setMaintainer_mobile(String maintainer_mobile) {
        this.maintainer_mobile = maintainer_mobile;
    }
    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) { this.description = description; }
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
    public LocalDateTime getGmtReceived() {
        return gmtReceived;
    }
    public void setGmtReceived(LocalDateTime gmtReceived) {
        this.gmtReceived = gmtReceived;
    }
    public LocalDateTime getGmtEnd() {
        return gmtEnd;
    }
    public void setGmtEnd(LocalDateTime gmtEnd) {
        this.gmtEnd = gmtEnd;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public Long getServiceProviderId(){ return serviceProviderId; }
    public void setServiceProviderId(Long serviceProviderId){ this.serviceProviderId = serviceProviderId; }
    public Long getProductId(){ return productId; }
    public void setProductId(Long productId){ this.productId = productId; }
    public Long getRegionId(){ return regionId; }
    public void setRegionId(Long regionId){ this.regionId = regionId; }
    public Long getShopId(){ return shopId; }
    public void setShopId(Long shopId){ this.shopId = shopId; }
}
