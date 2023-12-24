//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.service.controller.dto;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({ServiceProvider.class})
public class ServiceProviderDto {
    private Long id;
    private String name;
    private Byte status;
    private String consignee;
    private String mobile;
    private String address;

    public ServiceProviderDto(ServiceProvider serviceProvider){
        super();
        CloneFactory.copy(this, serviceProvider);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
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
}
