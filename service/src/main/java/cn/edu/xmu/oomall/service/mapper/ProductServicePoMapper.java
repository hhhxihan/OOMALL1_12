package cn.edu.xmu.oomall.service.mapper;

import cn.edu.xmu.oomall.service.mapper.po.ProductServicePo;
import cn.edu.xmu.oomall.service.dao.bo.ProductService;
import cn.edu.xmu.oomall.service.mapper.po.ServicePo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductServicePoMapper extends JpaRepository<ProductServicePo, Long> {
    List<ProductServicePo> findByServiceIdEqualsAndShopIdEquals(Long serviceId,Long shopId, Pageable pageable);
    List<ProductServicePo> findByServiceIdEqualsAndStatusEquals(Long serviceId,Byte status, Pageable pageable);
    List<ProductServicePo> findByProductIdEqualsAndRegionIdEqualsAndShopIdEquals(Long productId, Long regionId, Long shopId, Pageable pageable);

    List<ServicePo> findById(Long id, Pageable pageable);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_product_service
     *
     * @mbg.generated
     */
    @Delete({
            "delete from service_product_service",
            "where `id` = #{id,jdbcType=BIGINT}"
    })
    int deleteProductServiceById(Long id);
}
