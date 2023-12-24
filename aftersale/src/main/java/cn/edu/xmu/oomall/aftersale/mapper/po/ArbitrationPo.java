package cn.edu.xmu.oomall.aftersale.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Entity
@Table(name = "aftersale_arbitration")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@CopyFrom({Arbitration.class})
@Mapper
public class ArbitrationPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private Long aftersale_id;
    private String reason;
    private String shopReply;
    private String result;

    private Byte status;
    /*private Byte applicant_type;*/

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopReply() {
        return shopReply;
    }

    public void setShopReply(String shopReply) {
        this.shopReply = shopReply;
    }

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

    /*public Byte getApplicant_type() {
        return applicant_type;
    }

    public void setApplicant_type(Byte applicant_type) {
        this.applicant_type = applicant_type;
    }*/

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

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    /*public LocalDateTime getGmtAccept() {
        return gmtAccept;
    }

    public void setGmtAccept(LocalDateTime gmtAccept) {
        this.gmtAccept = gmtAccept;
    }*/
}
