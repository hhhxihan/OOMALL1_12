package cn.edu.xmu.oomall.service.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.controller.vo.ServiceOrderVo;
import cn.edu.xmu.oomall.service.dao.ServiceOrderDao;
import cn.edu.xmu.oomall.service.dao.openfeign.FreightExpressDao;
import cn.edu.xmu.oomall.service.mapper.openfeign.po.FreightExpress;
import cn.edu.xmu.oomall.service.mapper.po.ServiceOrderPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ToString(callSuper = true, doNotUseGetters = true)
@NoArgsConstructor
@CopyFrom({ServiceOrderVo.class, ServiceOrderPo.class})
public class ServiceOrder extends OOMallObject implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ServiceOrder.class);

    // 上门维修
    @JsonIgnore
    @ToString.Exclude
    public static final Byte DOORTODOOR = 0;

    // 寄修
    @JsonIgnore
    @ToString.Exclude
    public static final Byte REPAIR = 1;

    // 未接单
    @JsonIgnore
    @ToString.Exclude
    public static final Byte UNACCEPTED = 2;

    // 已接单未完成
    @JsonIgnore
    @ToString.Exclude
    public static final Byte UNFINISHED = 3;

    // 已收件
    @JsonIgnore
    @ToString.Exclude
    public static final Byte RECEIVED = 4;

    // 已完成
    @JsonIgnore
    @ToString.Exclude
    public static final Byte FINISHED = 5;

    // 已取消
    @JsonIgnore
    @ToString.Exclude
    public static final Byte CANCELED = 6;

    /**
     * 状态和名称的对应
     */
    @JsonIgnore
    @ToString.Exclude
    public static final Map<Byte, String> STATUSNAMES = new HashMap() {
        {
            put(UNACCEPTED, "未接单");
            put(UNFINISHED, "已接单未完成");
            put(RECEIVED, "已收件");
            put(FINISHED, "已完成");
        }
    };

    /**
     * 允许的状态迁移
     */
    @JsonIgnore
    @ToString.Exclude
    private static final Map<Byte, Set<Byte>> toStatus = new HashMap<>() {
        {
            put(UNACCEPTED, new HashSet<>() {
                {
                    add(UNFINISHED);
                    add(CANCELED);
                }
            });
            put(UNFINISHED, new HashSet<>() {
                {
                    add(RECEIVED);
                    add(FINISHED);
                    add(CANCELED);
                }
            });
            put(RECEIVED, new HashSet<>() {
                {
                    add(FINISHED);
                    add(CANCELED);
                }
            });
        }
    };

    /**
     * 是否允许状态迁移
     * @param status
     * @return
     */
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

    /**
     * 获得当前状态名称
     */
    @JsonIgnore
    public String getStatusName() {
        return STATUSNAMES.get(this.status);
    }

    /**
     * 修改服务单状态
     */
    private void changeStatus(ServiceOrder serviceOrder,Byte status, UserDto user) {
        if (!this.allowStatus(status)) {
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "服务单", this.id, STATUSNAMES.get(this.status)));
        }
        serviceOrder.setStatus(status);
        serviceOrder.setId(this.id);
        this.serviceOrderDao.save(serviceOrder, user);
        logger.debug("222status = {}", serviceOrder.getStatus());
        this.serviceOrderDao.save(this, user);
    }


    // 接受服务单
    public void accept(UserDto user) {
        if (!this.status.equals(ServiceOrder.UNACCEPTED)) {
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "服务单", id, user));
        }
        this.changeStatus(this, ServiceOrder.UNFINISHED, user);
        this.serviceOrderDao.save(this, user);
    }

    /*
    // 确认收件
    public void receive(UserDto user) {
        if (!this.status.equals(ServiceOrder.UNFINISHED)) {
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "服务单", id, user));
        }
        this.changeStatus(this,ServiceOrder.RECEIVED, user);
        this.serviceOrderDao.save(this, user);
    }
    */

    /*
    // 完成服务单
    public void finish(UserDto user) {
        if (this.status.equals(ServiceOrder.FINISHED)) {
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "服务单", id, user));
        }
        this.changeStatus(this,ServiceOrder.FINISHED, user);
        this.serviceOrderDao.save(this, user);
    }
     */

    /**
     * 取消服务单
     */
    public ServiceOrder cancel(ServiceOrder serviceOrder, UserDto user) {
        if (this.status.equals(ServiceOrder.FINISHED)) {
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "服务单", id, user));
        }
        this.changeStatus(this,ServiceOrder.CANCELED, user);
        this.serviceOrderDao.save(this, user);
        ServiceOrder newServiceOrder = serviceOrder;
        return newServiceOrder;
    }


    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // 种类
    private Byte type;
    // 联系人地址
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
    // 服务商id
    private Long serviceProviderId;
    // 运单号
    private Long freightExpressId;
    // 产品串号
    private Long serialNo;
    // 产品id
    private Long productId;
    // 地区id
    private Long regionId;
    // 商铺id
    private Long shopId;

    @ToString.Exclude
    @JsonIgnore
    private ServiceOrder serviceOrder;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private ServiceOrderDao serviceOrderDao;

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
    public Long getFreightExpressId(){ return freightExpressId; }
    public void setFreightExpressId(Long freightExpressId){ this.freightExpressId = freightExpressId; }
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
    public Long getSerialNo(){ return serialNo; }
    public void setSerialNo(Long serialNo){ this.serialNo = serialNo; }
    public Long getProductId(){ return productId; }
    public void setProductId(Long productId){ this.productId = productId; }
    public Long getRegionId(){ return regionId; }
    public void setRegionId(Long regionId){ this.regionId = regionId; }
    public Long getShopId(){ return shopId; }
    public void setShopId(Long shopId){ this.shopId = shopId; }
}
