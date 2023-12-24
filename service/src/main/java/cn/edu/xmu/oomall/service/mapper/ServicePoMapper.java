package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.ServicePo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicePoMapper extends JpaRepository<ServicePo, Long> {
    List<ServicePo> findByCreatorId(Long creatorId, Pageable pageable);
    List<ServicePo> findById(Long id, Pageable pageable);
    List<ServicePo> findByServiceProviderIdEqualsAndStatusEquals(Long serviceProviderId, Byte validStatus, Pageable pageable);
}
