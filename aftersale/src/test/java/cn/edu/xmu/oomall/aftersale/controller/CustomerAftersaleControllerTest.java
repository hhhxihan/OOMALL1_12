package cn.edu.xmu.oomall.aftersale.controller;

import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.aftersale.AftersaleApplication;
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

import static net.bytebuddy.matcher.ElementMatchers.is;


@SpringBootTest(classes = AftersaleApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRED)
class CustomerAftersaleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RedisUtil redisUtil;
    private static final String CREATEAFTERSALE = "/aftersales/createAftersale/{id}";

    @Test
    void CreateAftersale() throws Exception {
        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
        Mockito.when(redisUtil.get(Mockito.anyString())).thenReturn(null);
        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);
        String body = "{\"OrderItemId\":123456,\"type\":1,\"reason\":\"Some reason\",\"regionId\":789012,\"mobile\":\"1234567890\",\"customerId\":567890,\"shopId\":345678,\"address\":\"123 Main Street\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATEAFTERSALE,123456)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderItemId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customerId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.reason").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.mobile").exists());

    }


}