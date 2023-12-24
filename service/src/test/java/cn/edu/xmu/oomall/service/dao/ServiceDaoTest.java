package cn.edu.xmu.oomall.service.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.service.ServiceTestApplication;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.service.dao.bo.Services;
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
public class ServiceDaoTest {
    @Autowired
    private ServiceDao serviceDao;

    @MockBean
    RedisUtil redisUtil;

    @Test
    public void findByServiceProviderIdAndStatus(){
        try {
            serviceDao.findByServiceProviderId(4L,1,10);
        }catch (RuntimeException e) {
            assertEquals(e.getMessage(),"serviceDao.findByServiceProviderIdAndStatus: serviceProviderId is null");
        }
    }

    @Test
    public void findByIdWhenExist(){
        try {
            serviceDao.findById(1L);
        }catch (RuntimeException e) {
            assertEquals(e.getMessage(),"serviceDao.findById: serviceId is null");
        }
    }

    @Test
    public void findByIdWhenNull(){
        assertThrows(BusinessException.class, ()->serviceDao.findById(null));
    }

    @Test
    public void testInsertService(){
        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);

        Byte testType = 1;
        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("serviceProvider");

        Services service = new Services();
        service.setName("测试服务1");
        service.setDescription("服务描述1");
        service.setType(testType);

        Services newService = serviceDao.insert(service, user);
    }

    @Test
    public void testSaveService(){
        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);

        Byte testType = 0;
        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("serviceProvider");

        Services service = serviceDao.findById(3L);
        service.setName("测试服务1");
        service.setDescription("更改服务描述1");
        service.setType(testType);

        String updateService = serviceDao.save(service, user);
    }
}
