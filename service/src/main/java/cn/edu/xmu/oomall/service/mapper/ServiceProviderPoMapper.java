package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.ServiceProviderPo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceProviderPoMapper extends JpaRepository<ServiceProviderPo, Long> {
    List<ServiceProviderPo> findByCreatorId(Long creatorId, Pageable pageable);
    List<ServiceProviderPo> findById(Long id, Pageable pageable);
    List<ServiceProviderPo> findByNameContainingAndStatusIn(String name, List<Byte>validStatus, Pageable pageable);
}
