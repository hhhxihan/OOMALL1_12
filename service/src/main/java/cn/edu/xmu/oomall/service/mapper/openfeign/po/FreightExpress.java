package cn.edu.xmu.oomall.service.mapper.openfeign.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class FreightExpress {
    private Long id;
    private String sendAddress;
    private String sendMobile;
    private String receiveAddress;
    private String receiveMobile;
    private Byte status;
    private String orderCode;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSendAddress() {
        return sendAddress;
    }
    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }
    public String getSendMobile() {
        return sendMobile;
    }
    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile;
    }
    public String getReceiveAddress() {
        return receiveAddress;
    }
    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }
    public String getReceiveMobile() {
        return receiveMobile;
    }
    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }
    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
    }
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
