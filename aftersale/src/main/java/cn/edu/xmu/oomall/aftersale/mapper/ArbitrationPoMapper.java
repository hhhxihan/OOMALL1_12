package cn.edu.xmu.oomall.aftersale.mapper;

import cn.edu.xmu.oomall.aftersale.mapper.po.ArbitrationPo;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RefreshScope
public interface ArbitrationPoMapper extends JpaRepository<ArbitrationPo, Long> {
    List<ArbitrationPo> findByIdEqualsAndStatusEquals(Long id, Byte status, Pageable pageable);

    List<ArbitrationPo> findById(Long id, Pageable pageable);

    List<ArbitrationPo> findByStatus(Byte status, Pageable pageable);
    Optional<ArbitrationPo> findById(Long id);
    //List<ArbitrationPo> findByPid(Long id);
}
