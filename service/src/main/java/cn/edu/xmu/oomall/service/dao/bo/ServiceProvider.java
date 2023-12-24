package cn.edu.xmu.oomall.service.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.oomall.service.controller.vo.ServiceProviderVo;
import cn.edu.xmu.oomall.service.dao.ServiceProviderDao;
import cn.edu.xmu.oomall.service.mapper.po.ServiceProviderPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ToString(callSuper = true, doNotUseGetters = true)
@NoArgsConstructor
@CopyFrom({ServiceProviderVo.class,  ServiceProviderPo.class})
public class ServiceProvider extends OOMallObject implements Serializable {
    // 申请
    @JsonIgnore
    @ToString.Exclude
    public static final Byte NEW = 0;
    // 有效
    @JsonIgnore
    @ToString.Exclude
    public static final Byte VALID = 1;
    // 暂停
    @JsonIgnore
    @ToString.Exclude
    public static final Byte PAUSE = 2;
    // 停用
    @JsonIgnore
    @ToString.Exclude
    public static final Byte ABANDON = 3;

    /**
     * 状态和名称的对应
     */
    @JsonIgnore
    @ToString.Exclude
    public static final Map<Byte, String> STATUSNAMES = new HashMap() {
        {
            put(NEW, "申请");
            put(VALID, "有效");
            put(PAUSE, "暂停");
            put(ABANDON, "停用");
        }
    };

    /*
    // 允许的状态迁移
    @JsonIgnore
    @ToString.Exclude
    private static final Map<Byte, Set<Byte>> toStatus = new HashMap<>() {
        {
            put(NEW, new HashSet<>() {
                {
                    add(VALID);
                }
            });
            put(PAUSE, new HashSet<>() {
                {
                    add(VALID);
                    add(ABANDON);
                }
            });
            put(VALID, new HashSet<>() {
                {
                    add(PAUSE);
                }
            });
        }
    };
    */

    /*
    // 是否允许状态迁移
    public boolean allowStatus(Byte status) {
        boolean ret = false;

        if (null != status && null != this.status) {
            Set<Byte> allowStatusSet = toStatus.get(this.status);
            if (null != allowStatusSet) {
                ret = allowStatusSet.contains(status);
            }
        }
        return ret;
    }
     */

    // 获得当前状态名称
    @JsonIgnore
    public String getStatusName() {
        return STATUSNAMES.get(this.status);
    }

    private Long id;

    // 服务商名称
    private String name;

    // 服务商状态
    private Byte status;

    // 联系人姓名
    private String consignee;

    // 详细地址
    private String address;

    // 联系人电话
    private String mobile;

    @ToString.Exclude
    @JsonIgnore
    private ServiceProvider serviceProvider;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private ServiceProviderDao serviceProviderDao;

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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }
    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
}
