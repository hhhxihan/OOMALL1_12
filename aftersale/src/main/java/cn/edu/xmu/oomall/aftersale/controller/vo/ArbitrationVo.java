package cn.edu.xmu.oomall.aftersale.controller.vo;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
  *仲裁单视图对象
  */
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArbitrationVo {


    @NotBlank(message = "原因不能为空", groups = {NewGroup.class})
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    /*
    @JsonProperty(value = "shop_reply")
    private String shopReply;
    @JsonProperty(value = "result")
    private String result;
    @JsonProperty(value = "status")
    private Byte status;


   *//* private Long creatorId;
    private String creatorName;*//*

    @JsonProperty(value = "arbitratorId")
    private Long arbitratorId;
    @JsonProperty(value = "arbitratorName")
    private String arbitratorName;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty(value = "gmt_create")
    private LocalDateTime gmtCreate;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty(value = "gmt_accept")
    private LocalDateTime gmtAccept;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty(value = "gmt_arbitration")
    private LocalDateTime gmtArbitration;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public String getShopReply() {
        return shopReply;
    }

    public void setShopReply(String shopReply) {
        this.shopReply = shopReply;
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
    }*/
/*public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }*/



    /*public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }*/
}
