package cn.edu.xmu.oomall.service.controller;

import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.service.ServiceTestApplication;
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

@SpringBootTest(classes = ServiceTestApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ShopServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RedisUtil redisUtil;
    private final String CANCEL_ALL_SERVICE = "/shops/{did}/maintainers/{id}/cancel";
    private static String shopToken;

    @BeforeAll
    static void setUp() {
        JwtHelper jwtHelper = new JwtHelper();
        shopToken = jwtHelper.createToken(1L, "shop1", 0L, 1, 3600);
    }

    @Test
    void testCancelAllService() throws Exception {
        Mockito.when(redisUtil.get(Mockito.anyString())).thenReturn(null);
        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(CANCEL_ALL_SERVICE, 1, 1)
                        .header("authorization", shopToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
}
