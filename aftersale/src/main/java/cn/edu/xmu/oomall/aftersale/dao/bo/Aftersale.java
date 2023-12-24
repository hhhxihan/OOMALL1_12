package cn.edu.xmu.oomall.aftersale.dao.bo;

//School of Informatics Xiamen University, GPL-3.0 license

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.controller.dto.ApproveDto;
import cn.edu.xmu.oomall.aftersale.controller.dto.OrderItemDto;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import cn.edu.xmu.oomall.aftersale.dao.ArbitrationDao;
import org.springframework.beans.factory.annotation.Autowired;


@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({AftersalePo.class, OrderItemDto.class})
public class Aftersale extends OOMallObject implements Serializable {
    @ToString.Exclude
    @JsonIgnore
    private final static Logger logger = LoggerFactory.getLogger(Aftersale.class);

    /*特殊的id*/
    @ToString.Exclude
    @JsonIgnore
    public static final Long TOP_ID = 0L;
    @ToString.Exclude
    @JsonIgnore
    public static final Long ROOT_PID = -1L;

    /**
     * 共三种类型：退款、维修、换货
     */
    @ToString.Exclude
    @JsonIgnore
    public static final Byte REFUND = 0; //退款

    @ToString.Exclude
    @JsonIgnore
    public static final Byte REPAIR = 1; //维修

    @ToString.Exclude
    @JsonIgnore
    public static final Byte CHANGE = 2; //换货

    /**
     * 共四种状态：申请中、进行中、仲裁中、已完成
     */
    //申请中
    @ToString.Exclude
    @JsonIgnore
    public static final Byte APPLY = 0;
    //进行中
    @ToString.Exclude
    @JsonIgnore
    public static final Byte GOING = 1;
    //仲裁中
    @ToString.Exclude
    @JsonIgnore
    public static final Byte ARBITRATION = 2;

    //已完成
    @ToString.Exclude
    @JsonIgnore
    public static final Byte FINISH = 3;

    @ToString.Exclude
    @JsonIgnore
    public static final Map<Byte, String> STATUSNAMES = new HashMap<>() {
        {
            put(APPLY, "申请中");
            put(GOING, "进行中");
            put(ARBITRATION, "仲裁中");
            put(FINISH, "已完成");
        }
    };


    @JsonIgnore
    @ToString.Exclude
    private static final Map<Byte, Set<Byte>> toStatus = new HashMap<>() {
        {
            put(APPLY, new HashSet<>() {
                {
                    add(GOING);
                    add(ARBITRATION);
                    add(FINISH);
                }
            });
            put(GOING, new HashSet<>() {
                {
                    add(ARBITRATION);
                    add(FINISH);
                }
            });
            put(ARBITRATION, new HashSet<>() {
                {
                    add(FINISH);
                }
            });
        }
    };

    /**
     * 判断能不能进行状态迁移是否允许状态迁移
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

    public Arbitration createArbitrationByAftersale(Arbitration arbitration, UserDto user) {
        if (null == this.aftersaleDao){
            throw new IllegalArgumentException("Aftersale.createArbitration:aftersaleDao is null");
        }
        if (null == this.arbitrationDao){
            throw new IllegalArgumentException("Aftersale.createArbitration:arbitrationDao is null");
        }

        //如果当前售后单在仲裁中，不能再仲裁;未仲裁才能申请仲裁
        if (0 == this.getInArbitrated()) {//0表示未仲裁

            arbitration.setAftersale_id(this.id);
            //仲裁单状态为已申请
            arbitration.setStatus(Arbitration.NEW);
            logger.debug("createArbitrationByAftersale: arbitration = {}", arbitration);
            Arbitration newArbitration= arbitrationDao.insert(arbitration, user);
            String key=this.aftersaleDao.save(this,user);
            return newArbitration;
        }
        else{
            //售后已经有仲裁单
            throw new BusinessException(ReturnNo.AFTERSALE_INARBITRATION, String.format(ReturnNo.AFTERSALE_INARBITRATION.getMessage(), this.id));
        }
    }

    /**
     * 取消仲裁时，修改售后单关于仲裁的信息

     */
    public String cancelArbitration(UserDto user) {
        Aftersale newAftersale = new Aftersale();
        newAftersale.setId(this.id);
        newAftersale.setInArbitrated(0);
        String key =  this.aftersaleDao.save(newAftersale, user);

        return key;
    }

    /**
     * 获得当前状态名称
     */
    @JsonIgnore
    public String getStatusName() {
        return STATUSNAMES.get(this.status);
    }


    private Long id;
    private Long orderItemId;
    private Long customerId;  //需要从order获取
    private Long shopId;//需要从order获取
    private String aftersaleSn;
    private Byte type; //前端设置，请求服务的原因
    private String reason;  //前端设置
    private String conclusion;
    private Integer quantity; //需要从order获取
    private Long regionId;   //是否需要前端传入
    private String address;  //前端设置
    private String consignee; //需要从order获取
    private String mobile; //前端设置？还是通过order获取？
    private Byte status;   //状态，初始时是申请态
    private Long serviceId; //需要从order获取
    private String serialNo; //需要从order获取
    private String name; //需要从order获取
    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Integer inArbitrated;


    @JsonIgnore
    @ToString.Exclude
    @Setter
    private AftersaleDao aftersaleDao;

    @JsonIgnore
    @ToString.Exclude
    @Setter
    private ArbitrationDao arbitrationDao;

    /**
     * 商家同意售后
     * @param user 操作者
     * @return
     */
    public Boolean beGoing(UserDto user){
        return this.changeStatus(Aftersale.GOING, user);
    }

    /**
     * 商家不同意
     * @param user 操作者
     * @return 删除的redis key
     */
    public Boolean beArbitration(UserDto user){
        return this.changeStatus(Aftersale.ARBITRATION, user);
    }

    /**
     * 商家同意售后
     * @param user 操作者
     * @return 删除的redis key
     */
    public Boolean beFinsh(UserDto user){
        return this.changeStatus(Aftersale.FINISH, user);
    }
    /**
     *更改状态
     *
     * @param status     状态
     */
    private boolean changeStatus(Byte status, UserDto user){
        logger.debug("changeStatus: id = {}, status = {}", this.id, status);

        if (!this.allowStatus(status)) {
            // 状态不允许变动
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "地区", this.id, STATUSNAMES.get(this.status)));
        }
        Aftersale aftersale = new Aftersale();
        aftersale.setStatus(status);
        aftersale.setId(this.id);
        String key = this.aftersaleDao.save(aftersale, user);

        return true;
    }


    public Aftersale createAftersale(Aftersale bo, UserDto user){
        bo.setStatus(Aftersale.APPLY);
        if(aftersaleDao==null){
            throw new RuntimeException();
        }
        return aftersaleDao.insert(bo,user);
    }

    public void refuse(UserDto user){
        if (!this.beFinsh(user)) { //设置为结束
            throw new BusinessException(ReturnNo.AFTERSALE_APPROVEFALIED, String.format(ReturnNo.AFTERSALE_APPROVEFALIED.getMessage(), "状态不能改变", id));
        }
    }
    public void accept(UserDto user){
        if (!this.beGoing(user)) { //通过后，设置为正在进行
            throw new BusinessException(ReturnNo.AFTERSALE_APPROVEFALIED, String.format(ReturnNo.AFTERSALE_APPROVEFALIED.getMessage(), "状态不能改变", id));
        }
    }
    public void approve(ApproveDto dto, UserDto user){
        if (dto.getresult()) { //此时是审核通过
            this.accept(user);
        } else { //审核不通过
            this.refuse(user);
        }
        this.setConclusion(dto.getcommit()); //设置审核原因
        aftersaleDao.save(this,user);
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


    public LocalDateTime getGmtModified() {
        return gmtModified;
    }



    public Integer getInArbitrated() {
        return inArbitrated;
    }

    public void setInArbitrated(Integer inArbitrated) {
        this.inArbitrated = inArbitrated;
    }

    public AftersaleDao getAftersaleDao() {
        return aftersaleDao;
    }

//    public void setAftersaleDao(AftersaleDao aftersaleDao) {
//        this.aftersaleDao = aftersaleDao;
//    }

    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {

    }

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {

    }
}
