package cn.edu.xmu.oomall.aftersale.mapper;

import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RefreshScope
public interface AftersalePoMapper extends JpaRepository<AftersalePo, Long> {

    List<AftersalePo> findByCustomerId(Long ShopID,Pageable pageable);
    List<AftersalePo> findByShopId(Long ShopID,Pageable pageable);
    List<AftersalePo> findByServiceId(Long ShopID,Pageable pageable);

    AftersalePo findByCustomerId(Long OrderItemId);

    Optional<AftersalePo> findByOrderItemId(Long OrderItemId);
}
