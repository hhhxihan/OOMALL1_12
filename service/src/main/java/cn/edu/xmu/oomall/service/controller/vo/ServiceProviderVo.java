package cn.edu.xmu.oomall.service.controller.vo;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServiceProviderVo {
    @NotBlank(message = "服务商名称不能为空", groups = {NewGroup.class})
    private String name;
    @NotBlank(message = "服务商联系人姓名不能为空")
    private String consignee;
    @NotBlank(message = "服务商联系人电话不能为空")
    private String mobile;
    @NotBlank(message = "服务商联系人详细地址不能为空")
    private String address;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
