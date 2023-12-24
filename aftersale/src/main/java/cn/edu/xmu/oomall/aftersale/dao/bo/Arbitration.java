package cn.edu.xmu.oomall.aftersale.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.controller.vo.ArbitrationVo;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.dao.ArbitrationDao;
import cn.edu.xmu.oomall.aftersale.mapper.po.ArbitrationPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
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

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({ArbitrationPo.class, ArbitrationVo.class})
public class Arbitration extends OOMallObject implements Serializable {
    @ToString.Exclude
    @JsonIgnore
    private final static Logger logger = LoggerFactory.getLogger(Arbitration.class);

    /**
     * 仲裁单状态
     */
    //申请态
    @ToString.Exclude
    @JsonIgnore
    public static final Byte NEW = 0;
    //受理态
    @ToString.Exclude
    @JsonIgnore
    public static final Byte ACCEPT = 1;
    //应诉态
    @ToString.Exclude
    @JsonIgnore
    public static final Byte REPLY = 2;
    //完成态
    @ToString.Exclude
    @JsonIgnore
    public static final Byte SUCCESS = 3;
    //取消态
    @ToString.Exclude
    @JsonIgnore
    public static final Byte FAIL = 4;

    @ToString.Exclude
    @JsonIgnore
    public static final Map<Byte, String> STATUSNAMES = new HashMap<>() {
        {
            put(NEW, "申请态");
            put(ACCEPT, "受理态");
            put(REPLY, "应诉态");
            put(SUCCESS, "完成态");
            put(FAIL, "申请失败态");
        }
    };

    /**
     * 允许的状态迁移
     */
    @JsonIgnore
    @ToString.Exclude
    private static final Map<Byte, Set<Byte>> toStatus = new HashMap<>() {
        {
            put(NEW, new HashSet<>() {
                {
                    add(ACCEPT);
                    //add(ABANDONED);
                }
            });
            put(ACCEPT, new HashSet<>() {
                {
                    add(REPLY);
                    //add(ABANDONED);
                }
            });
            put(REPLY, new HashSet<>() {
                {
                    add(SUCCESS);
                    //add(ABANDONED);
                }
            });
            put(NEW, new HashSet<>() {
                {
                    add(FAIL);
                    //add(ABANDONED);
                }
            });
        }
    };


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    private Long aftersale_id;
    private String reason;
    private String shopReply;
    private String result;

    private Byte status;

    private Long creatorId;
    private String creatorName;

    private Long modifierId;
    private String modifierName;
    private Long arbitratorId;
    private String arbitratorName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private LocalDateTime gmtAccept;
    private LocalDateTime gmtArbitration;
    /**
     * 取消仲裁
     */

    public String cancelArbitration(UserDto user) {

        if (this.arbitrationDao == null){
            throw new IllegalArgumentException("ArbitrationDao.cancel: arbitrationDao is null");
        }
        if (this.aftersaleDao == null){
            throw new IllegalArgumentException("AftersaleDao.cancel: aftersaleDao is null");
        }
        if(this.status.equals(Arbitration.NEW)||this.status.equals(Arbitration.ACCEPT)||this.status.equals(Arbitration.REPLY)) {
            Aftersale aftersale = this.aftersaleDao.findById(this.getAftersale_id());
            aftersale.cancelArbitration(user);
            String key = this.arbitrationDao.delete(this.id);
            return key;
        }
        //如果该仲裁单的状态是已经结案了，不允许删除
        else
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "仲裁单", this.id, "已结案"));
    }
    public LocalDateTime getGmtAccept() {
        return gmtAccept;
    }

    public void setGmtAccept(LocalDateTime gmtAccept) {
        this.gmtAccept = gmtAccept;
    }

    public LocalDateTime getGmtArbitration() {
        return gmtArbitration;
    }

    public void setGmtArbitration(LocalDateTime gmtArbitration) {
        this.gmtArbitration = gmtArbitration;
    }



    @Setter
    @JsonIgnore
    @ToString.Exclude
    private AftersaleDao aftersaleDao;

    @Setter
    @JsonIgnore
    @ToString.Exclude
    private ArbitrationDao arbitrationDao;



    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getShopReply() {
        return shopReply;
    }

    public void setShopReply(String shopReply) {
        this.shopReply = shopReply;
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

    public Long getArbitratorId() {
        return arbitratorId;
    }

    public void setArbitratorId(Long arbitratorId) {
        this.arbitratorId = arbitratorId;
    }

    public String getArbitratorName() {
        return arbitratorName;
    }

    public void setArbitratorName(String arbitratorName) {
        this.arbitratorName = arbitratorName;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

   /* public LocalDateTime getGmtAccept() {
        return gmtAccept;
    }

    public void setGmtAccept(LocalDateTime gmtAccept) {
        this.gmtAccept = gmtAccept;
    }*/



    public Long getAftersale_id() {
        return aftersale_id;
    }

    public void setAftersale_id(Long aftersale_id) {
        this.aftersale_id = aftersale_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }



}

