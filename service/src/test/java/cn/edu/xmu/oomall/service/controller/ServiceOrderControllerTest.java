package cn.edu.xmu.oomall.service.controller;

import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.service.ServiceTestApplication;
import cn.edu.xmu.oomall.service.dao.bo.ServiceOrder;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ServiceTestApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ServiceOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RedisUtil redisUtil;

    private static String serviceProviderToken;

    @BeforeAll
    static void setUp() {
        JwtHelper jwtHelper = new JwtHelper();
        serviceProviderToken = jwtHelper.createToken(3L, "djy", 1L, 2, 3600);
    }

    // 根据服务商id查询服务单
    @Test
    void testFindServiceOrderById() throws Exception{
        Mockito.when(redisUtil.get("S1")).thenReturn(null);
        Mockito.when(redisUtil.set("S1", new ServiceOrder(), 3600)).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/maintainers/{did}/services/{id}", 1,4)
                        .header("authorization", serviceProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.serviceProviderId", is(1)));
    }

    // 服务商查看符合条件的服务单
    @Test
    void testRetrieveAllServiceOrders() throws Exception{
        Mockito.when(redisUtil.get("S1")).thenReturn(null);
        Mockito.when(redisUtil.set("S1", new ServiceOrder(), 3600)).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/maintainers/{did}/services", 1)
                        .header("authorization", serviceProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
        ;
    }

    // 服务商接受符合条件的服务单
    @Test
    void testAcceptServiceOrderById() throws Exception{
        Mockito.when(redisUtil.get("S1")).thenReturn(null);
        Mockito.when(redisUtil.set("S1", new ServiceOrder(), 3600)).thenReturn(true);

        String body = "{\"maintainerName\":\"老王\", \"maintainerMobile\":\"123456789\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/maintainers/{did}/services/{id}/accept", 1,1)
                        .header("authorization", serviceProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
        ;
    }

    // 服务商取消服务单
    @Test
    void testCancelServiceOrderById() throws Exception{
        Mockito.when(redisUtil.get("S1")).thenReturn(null);
        Mockito.when(redisUtil.set("S1", new ServiceOrder(), 3600)).thenReturn(true);

        String body = "";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/maintainers/{did}/services/{id}/cancel", 1,4)
                        .header("authorization", serviceProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
        ;
    }
}
