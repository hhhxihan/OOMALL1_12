package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.ServiceOrderPo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOrderPoMapper extends JpaRepository<ServiceOrderPo, Long> {
    List<ServiceOrderPo> findById(Long id, Pageable pageable);
    List<ServiceOrderPo> findByStatusIn(List<Byte>validStatus, Pageable pageable);
    List<ServiceOrderPo> findByProductIdEqualsAndRegionIdEqualsAndShopIdEqualsAndStatusEquals(Long productId, Long regionId, Long shopId, Byte status, Pageable pageable);
}
