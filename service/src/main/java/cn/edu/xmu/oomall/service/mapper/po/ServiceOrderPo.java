package cn.edu.xmu.oomall.service.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_service_order")
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({ServiceOrder.class})
public class ServiceOrderPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为一个实体生成一个唯一标识的主键，主键自增长
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
    private String maintainerName;

    // 维修人电话
    private String maintainerMobile;

    // 状态
    private Byte status;

    // 结果
    private String result;

    // 描述
    private String description;

    // 创建者id
    private Long creatorId;

    // 创建者姓名
    private String creatorName;

    // 修改者id
    private Long modifierId;

    // 修改者姓名
    private String modifierName;

    // 服务单创建时间
    private LocalDateTime gmtCreate;

    // 服务单接受时间
    private LocalDateTime gmtModified;

    // 收件时间
    private LocalDateTime gmtReceived;

    // 服务结束时间
    private LocalDateTime gmtEnd;

    // 服务商id
    private Long serviceProviderId;

    // 产品串号
    private Long serialNo;

    // 运单号
    private Long freightExpressId;

    // 产品id
    private Long productId;

    // 地区id
    private Long regionId;

    // 商铺id
    private Long shopId;

    public Long getId() {
        return id;
    }
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
    public String getMaintainerName() {
        return maintainerName;
    }
    public void setMaintainerName(String maintainerName) {
        this.maintainerName = maintainerName;
    }
    public String getMaintainerMobile() {
        return maintainerMobile;
    }
    public void setMaintainerMobile(String maintainerMobile) {
        this.maintainerMobile = maintainerMobile;
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
    public void setDescription(String description) {
        this.description = description;
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
    public Long getFreightExpressId(){ return freightExpressId; }
    public void setFreightExpressId(Long freightExpressId){ this.freightExpressId = freightExpressId; }
    public Long getSerialNo(){ return serialNo; }
    public void setSerialNo(Long serialNo){ this.serialNo = serialNo; }
    public Long getProductId(){ return productId; }
    public void setProductId(Long productId){ this.productId = productId; }
    public Long getRegionId(){ return regionId; }
    public void setRegionId(Long regionId){ this.regionId = regionId; }
    public Long getShopId(){ return shopId; }
    public void setShopId(Long shopId){ this.shopId = shopId; }
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
}
