package cn.edu.xmu.oomall.aftersale.dao;


import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.AftersaleApplication;
import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.dao.AftersaleDao;
import cn.edu.xmu.oomall.aftersale.mapper.AftersalePoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

import cn.edu.xmu.oomall.aftersale.dao.bo.Aftersale;
import cn.edu.xmu.oomall.aftersale.mapper.AftersalePoMapper;
import cn.edu.xmu.oomall.aftersale.mapper.po.AftersalePo;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import static cn.edu.xmu.javaee.core.model.Constants.IDNOTEXIST;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@ComponentScan(basePackages = "cn.edu.xmu.oomall.aftersale.dao")
@ComponentScan(basePackages = "cn.edu.xmu.oomall.aftersale.mapper")
@AutoConfigureMockMvc
@SpringBootTest(classes = AftersaleApplication.class)
class AftersaleDaoTest {


    @Resource
    public AftersaleDao aftersaleDao;
    @MockBean
    private RedisUtil redisUtil;


   @Test
   public void testInsert(){

       Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
       Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);
       Mockito.when(redisUtil.bfExist(Mockito.anyString(), (Long) Mockito.any())).thenReturn(false);
       Mockito.when(redisUtil.bfAdd(Mockito.anyString(), Mockito.any())).thenReturn(true);
       Aftersale aftersale = new Aftersale();
       LocalDate date = LocalDate.now();
       LocalTime time = LocalTime.now();
       LocalDateTime dateTime = LocalDateTime.of(date, time);

       aftersale.setId(231L);
       aftersale.setOrderItemId(10L);
       aftersale.setCustomerId(3L);
       aftersale.setShopId(4L);
       aftersale.setAftersaleSn("AFT123456");
       aftersale.setType((byte) 1);
       aftersale.setReason("Defective product");
       aftersale.setConclusion("Refund approved");
       aftersale.setQuantity(5);
       aftersale.setRegionId(6L);
       aftersale.setAddress("123 Main St, City");
       aftersale.setConsignee("John Doe");
       aftersale.setMobile("123-456-7890");
       aftersale.setStatus((byte) 0);
       aftersale.setServiceId(7L);
       aftersale.setSerialNo("SER123");
       aftersale.setName("Product Name");
       aftersale.setCreatorId(8L);
       aftersale.setCreatorName("Admin");
       aftersale.setModifierId(9L);
       aftersale.setModifierName("SuperAdmin");
//       aftersale.setGmtCreate(dateTime.of(date,time));
//       aftersale.setGmtModified(dateTime.of(date,time));
       aftersale.setInArbitrated(1);

       UserDto userDto = new UserDto();
       userDto.setId(2L);
       userDto.setName("admin2");
       userDto.setDepartId(1L);
       userDto.setUserLevel(1);
       Aftersale insert = aftersaleDao.insert(aftersale, userDto);
       assertEquals((byte) 0, insert.getStatus());
       assertEquals(5,insert.getQuantity());
       assertNotNull(insert.getId());
   }
}