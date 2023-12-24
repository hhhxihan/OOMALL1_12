package cn.edu.xmu.oomall.service.controller;

import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.service.ServiceApplication;
import cn.edu.xmu.oomall.service.ServiceTestApplication;
import cn.edu.xmu.oomall.service.dao.bo.ServiceProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ServiceTestApplication.class})
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ServiceProviderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RedisUtil redisUtil;

    private static String UnregisteredServiceProviderToken, serviceProviderToken;

    @BeforeAll
    static void setUp() {
        JwtHelper jwtHelper = new JwtHelper();
        // 未创建过服务商的用户(serviceProviderId = -1)
        UnregisteredServiceProviderToken = jwtHelper.createToken(5L, "djy", -1L, 2, 3600);
        // 创建过服务商的用户(serviceProviderId != -1)
        serviceProviderToken = jwtHelper.createToken(3L, "djy", 1L, 2, 3600);

    }

    // 成功创建一个服务商
    @Test
    void testCreateServiceProviderWhenUserHasNoServiceProvider() throws Exception {
        String body = "{\"name\":\"服务商1\", \"consignee\":\"小明\",\"mobile\":\"123456789\",\"address\":\"厦大\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/maintainers")
                        .header("authorization", UnregisteredServiceProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())));
    }

    // 服务商查看账户信息
    @Test
    void testFindServiceProviderById() throws Exception{
        Mockito.when(redisUtil.get("S1")).thenReturn(null);
        Mockito.when(redisUtil.set("S1", new ServiceProvider(), 3600)).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/maintainers/{id}", 1)
                        .header("authorization", serviceProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("服务商1")));
    }

    // 服务商修改账户信息
    @Test
    void testUpdateServiceProviderWithoutRedis() throws Exception {
        Mockito.when(redisUtil.get("S1")).thenReturn(null);
        Mockito.when(redisUtil.set("S1", new ServiceProvider(), 3600)).thenReturn(true);

        String body = "{\"name\":\"服务商1\", \"consignee\":\"小明\",\"mobile\":\"111111111\",\"address\":\"厦大\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/maintainers/{id}", 1)
                        .header("authorization", serviceProviderToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

}
