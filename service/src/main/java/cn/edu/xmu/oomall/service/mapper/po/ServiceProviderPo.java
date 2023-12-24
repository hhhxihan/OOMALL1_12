package cn.edu.xmu.oomall.service.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_service_provider")
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({ServiceProvider.class})
public class ServiceProviderPo {
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

    // 服务商名称
    private String name;

    // 服务商状态
    private Byte status;

    // 联系人姓名
    private String consignee;

    // 电话
    private String mobile;

    // 地址
    private String address;

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
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public Byte getStatus() { return status; }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public String getConsignee() { return consignee; }
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
