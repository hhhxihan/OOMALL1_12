package cn.edu.xmu.oomall.service.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.service.dao.bo.Services;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_service")
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({Services.class})
public class ServicePo {
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


    // 服务名称
    private String name;

    // 服务状态
    private Byte status;

    // 服务类型
    private Byte type;

    // 服务描述
    private String description;

    // 服务商
    private Long serviceProviderId;

    public Long getId() { return id; }

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
    public Byte getType() { return type; }
    public void setType(Byte type) {
        this.type = type;
    }
    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getServiceProviderId() { return serviceProviderId; }
    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }
}
