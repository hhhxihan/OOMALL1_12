package cn.edu.xmu.oomall.service.mapper.openfeign;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.service.mapper.openfeign.po.FreightExpress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("freight-service")
public interface FreightExpressMapper {
    // 获得运单信息
    @GetMapping("/internal/packages/{id}")
    InternalReturnObject<FreightExpress> getFreightExpressById(@PathVariable Long id);

    // 服务商发出揽收
    @PostMapping("/internal/maintainers/{maintainerId}/packages/{id}/send")
    InternalReturnObject<FreightExpress> createFreightExpressById(@PathVariable Long serviceProviderId, @PathVariable Long id);
}
