package cn.edu.xmu.oomall.service.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import cn.edu.xmu.oomall.service.dao.ServiceDao;
import cn.edu.xmu.oomall.service.mapper.po.ServicePo;
import cn.edu.xmu.oomall.service.dao.ProductServiceDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({ServicePo.class})
public class Services extends OOMallObject implements Serializable {
    @ToString.Exclude
    @JsonIgnore
    private final static Logger logger = LoggerFactory.getLogger(Services.class);
    // 申请
    @JsonIgnore
    @ToString.Exclude
    public static final Byte VALID = 0;
    // 停用
    @JsonIgnore
    @ToString.Exclude
    public static final Byte ABANDON = 1;

    /**
     * 状态和名称的对应
     */
    @JsonIgnore
    @ToString.Exclude
    public static final Map<Byte, String> STATUSNAMES = new HashMap() {
        {
            put(VALID, "有效");
            put(ABANDON, "停用");
        }
    };

    /**
     * 允许的状态迁移
     */
    @JsonIgnore
    @ToString.Exclude
    private static final Map<Byte, Set<Byte>> toStatus = new HashMap<>() {
        {
            put(VALID, new HashSet<>() {
                {
                    add(ABANDON);
                }
            });
        }
    };

    /**
     * 是否允许状态迁移
     */
    /*
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

    /**
     * 废弃服务
     */
    /*
    public void abandon(UserDto user){
        this.changeStatus(Services.ABANDON, user);
    }
    */

    /**
     *更改服务状态
     */
    /*
    private void changeStatus(Byte status, UserDto user){
        logger.debug("changeStatus: id = {}, status = {}", this.id, status);

        if (!this.allowStatus(status)) {
            // 状态不允许变动
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "服务", this.id, STATUSNAMES.get(this.status)));
        }
        Services service = new Services();
        service.setStatus(status);
        service.setId(this.id);
        this.serviceDao.save(service, user);
    }
    */

    /**
     * 创建服务
     */
    /*
    public Services createService(Services service, UserDto user){
        if (VALID == this.status) {
            service.setStatus(this.status);
            return this.serviceDao.insert(service, user);
        }else{
            throw new BusinessException(ReturnNo.REGION_ABANDONE, String.format(ReturnNo.REGION_ABANDONE.getMessage(), this.id));
        }
    }
    */

    /**
     * 获得当前状态名称
     */
    @JsonIgnore
    public String getStatusName() {
        return STATUSNAMES.get(this.status);
    }

    private Long id;

    // 服务名称
    private String name;

    // 服务状态
    private Byte status;

    // 服务类型
    private Byte type;

    // 描述
    private String description;

    // 服务商
    private Long serviceProviderId;

    @ToString.Exclude
    @JsonIgnore
    private Services service;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    public ServiceDao serviceDao;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    public ProductServiceDao productServiceDao;


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


    public void cancelProductServiceByShop(Long shopId){
        if (this.productServiceDao == null){
            throw new IllegalArgumentException("Service.cancel: productServiceDao is null");
        }
        //if(this.productServiceDao!=null) {
            List<ProductService> productServiceList = this.productServiceDao.findByServiceIdAndShopId(this.id, shopId, 1, 10);
            for (ProductService obj : productServiceList) {
                obj.setServiceDao(this.serviceDao);
                obj.setProductServiceDao(this.productServiceDao);
                obj.cancel();
            }
        //}
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
    public Byte getType() {
        return type;
    }
    public void setType(Byte type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getServiceProviderId() { return serviceProviderId;}
    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }


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

}
