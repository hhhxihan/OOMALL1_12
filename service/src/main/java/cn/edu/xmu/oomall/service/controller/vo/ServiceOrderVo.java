//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.service.controller.vo;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class ServiceOrderVo {
    // 服务单id
    @NotBlank(message = "服务单号不能为空", groups = {NewGroup.class})
    private Long id;
    // 种类
    @NotBlank(message = "服务种类不能为空", groups = {NewGroup.class})
    private Byte type;

    // 服务地址
    @NotBlank(message = "服务地址不能为空", groups = {NewGroup.class})
    private String address;

    // 联系人姓名
    @NotBlank(message = "联系人姓名不能为空", groups = {NewGroup.class})
    private String consignee;

    // 联系人电话
    @NotBlank(message = "联系人电话不能为空", groups = {NewGroup.class})
    private String mobile;

    // 维修人姓名
    private String maintainerName;

    // 维修人电话
    private String maintainerMobile;

    // 状态
    @NotBlank(message = "服务状态不能为空", groups = {NewGroup.class})
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

    // 产品id
    private Long productId;

    // 地区id
    private Long regionId;

    // 商铺id
    private Long shopId;

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
    public String getMaintainerName() { return maintainerName; }
    public void setMaintainerName(String maintainerName) {
        this.maintainerName = maintainerName;
    }
    public String getMaintainerMobile() {return maintainerMobile;}
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
