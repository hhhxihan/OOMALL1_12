package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.ServiceTestApplication;
import cn.edu.xmu.oomall.service.dao.bo.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ServiceTestApplication.class)
@Transactional
public class ProductServiceDaoTest {
    @Autowired
    private ProductServiceDao productServiceDao;

    @MockBean
    RedisUtil redisUtil;

    @Test
    public void findByIdWhenExist(){
        try {
            productServiceDao.findById(1L);
        }catch (RuntimeException e) {
            assertEquals(e.getMessage(),"productServiceDao.findById: serviceId is null");
        }
    }

    @Test
    public void findByIdWhenNull(){
        assertThrows(BusinessException.class, ()->productServiceDao.findById(null));
    }


    @Test
    public void findByServiceIdAndShopId(){
        try {
            productServiceDao.findByServiceIdAndShopId(1L,1L,1,10);
        }catch (RuntimeException e) {
            assertEquals(e.getMessage()," productServiceDao.findByServiceIdAndShopId: serviceId or shopId is null");
        }
    }

    @Test
    public void findByServiceIdAndStatus(){
        try {
                productServiceDao.findByServiceIdAndStatus(1L,1,10);
        }catch (RuntimeException e) {
            assertEquals(e.getMessage()," productServiceDao.findByServiceIdAndStatus: serviceId is null");
        }
    }

    @Test
    public void findByProductIdAndRegionIdAndShopIdAndStatus(){
        try {
            productServiceDao.findByProductIdAndRegionIdAndShopIdAndStatus(1L,1L,1L,1,10);
        }catch (RuntimeException e) {
            assertEquals(e.getMessage()," productServiceDao.findByServiceIdAndStatus: serviceId is null");
        }
    }

    @Test
    public void testInsertService(){
        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);

        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("serviceProvider");

        ProductService productService = new ProductService();
        productService.setServiceId(1L);
        productService.setProductId(1551L);
        productService.setShopId(1L);
        productService.setRegionId(2L);

        ProductService newProductService = productServiceDao.insert(productService, user);
    }

    @Test
    public void testSaveService(){
        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);

        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("serviceProvider");

        ProductService productService = productServiceDao.findById(1L);
        productService.setServiceId(1L);
        productService.setProductId(1551L);
        productService.setShopId(1L);
        productService.setRegionId(2L);

        String updateProductService = productServiceDao.save(productService, user);
    }

    @Test
    public void delete(){
        try {
            productServiceDao.delete(4L);
        }catch (RuntimeException e) {
            assertEquals(e.getMessage()," productServiceDao.delete failed");
        }
    }
}
