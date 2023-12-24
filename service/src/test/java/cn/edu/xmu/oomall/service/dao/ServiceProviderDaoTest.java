package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.ServiceTestApplication;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ServiceTestApplication.class)
@AutoConfigureMockMvc
public class ServiceProviderDaoTest {
    @Autowired
    private ServiceProviderDao serviceProviderDao;

    @MockBean
    RedisUtil redisUtil;

    // 插入
    @Test
    public void testInsert(){
        UserDto userDto = new UserDto();
        userDto.setId(2L);
        userDto.setName("admin2");
        userDto.setDepartId(1L);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName("服务商01");
        serviceProvider.setConsignee("小明");
        serviceProvider.setMobile("123456789");
        serviceProvider.setAddress("厦大");
        serviceProvider.setGmtCreate(LocalDateTime.parse("2030-11-06T12:00:00", DateTimeFormatter.ISO_DATE_TIME));
        ServiceProvider insert = serviceProviderDao.insert(serviceProvider, userDto);
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

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setId(10L);
        serviceProvider.setName("服务商01");
        serviceProvider.setConsignee("小明");
        serviceProvider.setMobile("123456789");
        serviceProvider.setAddress("厦大");
        serviceProviderDao.save(serviceProvider, userDto);
    }

}
