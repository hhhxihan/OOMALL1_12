package cn.edu.xmu.oomall.aftersale.dao;


import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.aftersale.AftersaleApplication;
import cn.edu.xmu.oomall.aftersale.dao.bo.Arbitration;
import cn.edu.xmu.oomall.aftersale.mapper.ArbitrationPoMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@ComponentScan(basePackages = "cn.edu.xmu.oomall.aftersale.dao")
@ComponentScan(basePackages = "cn.edu.xmu.oomall.aftersale.mapper")
@EntityScan(basePackages = "cn.edu.xmu.oomall.aftersale.mapper.po")
@AutoConfigureMockMvc
@SpringBootTest(classes = AftersaleApplication.class)
class ArbitrationDaoTest {


    @Resource
    @Autowired
    public ArbitrationDao arbitrationDao;
    @Mock
    @Autowired
    private ArbitrationPoMapper arbitrationPoMapper;
    @MockBean
    private RedisUtil redisUtil;




    @Test
    public void testInsert(){

        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);
        Mockito.when(redisUtil.bfExist(Mockito.anyString(), (Long) Mockito.any())).thenReturn(false);
        Mockito.when(redisUtil.bfAdd(Mockito.anyString(), Mockito.any())).thenReturn(true);
        Arbitration arbitration = new Arbitration();


        arbitration.setAftersale_id(10L);
        arbitration.setReason("未及时退款");
        arbitration.setShopReply("未应诉");
        arbitration.setResult("未处理");
        arbitration.setStatus((byte) 0);
        /*arbitration.setApplicant_type((byte) 0);*/

        arbitration.setCreatorId(2L);
        arbitration.setCreatorName("Admin");
        arbitration.setModifierId(9L);
        arbitration.setModifierName("SuperAdmin");

        arbitration.setArbitratorId(11L);
        arbitration.setArbitratorName("ArbitratorTest");
        LocalDateTime currentTime = LocalDateTime.now();
        arbitration.setGmtCreate(currentTime);
        arbitration.setGmtModified(currentTime);

        arbitration.setGmtAccept(currentTime);
        arbitration.setGmtArbitration(currentTime);


        UserDto userDto = new UserDto();
        userDto.setId(2L);
        userDto.setName("admin2");
        userDto.setDepartId(1L);
        // Perform the test
        Arbitration result = arbitrationDao.insert(arbitration, userDto);
        // Assert the result
        assertEquals(arbitration, result);
    }


    @Test
    public void testSaveWhenNull() {
        Arbitration arbitration=new Arbitration();
        UserDto userDto=new UserDto();

        assertThrows(BusinessException.class, () ->arbitrationDao.save(arbitration,userDto));
    }



    @Test
    public void testFindByIdWhenIdIsNull()
    {
        try {
            arbitrationDao.findById(null);
        }catch (RuntimeException e) {
            assertEquals(e.getMessage(),"ArbitrationDao.findById: id is null");
        }
        assertThrows(BusinessException.class,()->arbitrationDao.findById(null));
    }
    /**
     * 不存在该对象
     */
    @Test
    void testFindByIdWhenObjectNotExist() {
        assertThrows(BusinessException.class,()->arbitrationDao.findById(9999L));
    }
    /**
     * 查找成功
     */
    @Test
    public void testFindByIdWhenSuccess()
    {
        // 准备
        Long arbitrationId = 1L;

        // 执行
        Arbitration arbitration = arbitrationDao.findById(arbitrationId);

        // 断言
        assertNotNull(arbitration);
        assertEquals(arbitrationId, arbitration.getId());
    }

    /* @Test
     public void testDeleteExistingId() {
         Long existingId = 13L;
         String result = null;
         try {
             result = arbitrationDao.delete(existingId);
         } catch (Exception e) {
             fail("An exception was thrown: " + e.getMessage());
         }
         // 验证返回结果是否符合预期
         assertNotNull(result);
         assertEquals(ReturnNo.OK, ReturnNo.OK);
     }*/
    @Test
    public void testDeleteWithNoExistingId()
    {
        Long id=499L;
        assertThrows(BusinessException.class,()->arbitrationDao.delete(id));
    }
}
