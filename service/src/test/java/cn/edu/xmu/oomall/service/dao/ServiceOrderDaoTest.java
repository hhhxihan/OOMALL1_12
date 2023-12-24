package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.jpa.SelectiveUpdateJpaRepositoryImpl;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.ServiceTestApplication;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = ServiceTestApplication.class)
@Transactional
public class ServiceOrderDaoTest {
    @Autowired
    ServiceOrderDao serviceOrderDao;

    @MockBean
    private RedisUtil redisUtil;

    // 插入
    @Test
    public void testInsert(){
        UserDto userDto = new UserDto();
        userDto.setId(2L);
        userDto.setName("小明");
        userDto.setDepartId(1L);

        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setType((byte)0);
        serviceOrder.setAddress("厦大");
        serviceOrder.setConsignee("小明");
        serviceOrder.setMobile("123456789");
        serviceOrder.setGmtCreate(LocalDateTime.now());
        serviceOrder.setCreatorId(2L);
        serviceOrder.setCreatorName("小明");
        serviceOrder.setProductId(1L);
        serviceOrder.setRegionId(1L);
        serviceOrder.setShopId(1L);

        ServiceOrder insert = serviceOrderDao.insert(serviceOrder, userDto);
        assertEquals("小明", insert.getConsignee());
        assertEquals("123456789",insert.getMobile());
        assertNotNull(insert.getId());
    }

    @Test
    public void testSave(){
        UserDto userDto = new UserDto();
        userDto.setId(2L);
        userDto.setName("admin2");
        userDto.setDepartId(1L);

        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setId(100L);
        serviceOrder.setType((byte)0);
        serviceOrder.setStatus((byte)5);
        serviceOrder.setAddress("厦大");
        serviceOrder.setConsignee("小明");
        serviceOrder.setMobile("123456789");
        serviceOrder.setMaintainerName("小红");
        serviceOrder.setMaintainerMobile("987654321");
        serviceOrder.setServiceProviderId(1L);
        serviceOrderDao.save(serviceOrder,userDto);
    }
}
