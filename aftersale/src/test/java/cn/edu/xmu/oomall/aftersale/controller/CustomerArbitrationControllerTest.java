package cn.edu.xmu.oomall.aftersale.controller;

import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.aftersale.AftersaleApplication;
import cn.edu.xmu.oomall.aftersale.controller.vo.ArbitrationVo;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AftersaleApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerArbitrationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RedisUtil redisUtil;
    private static String userToken;
    private final String AFTERSALES_ID_ARBITRATIONS = "/aftersales/{id}/arbitrations";
    private final String DELETE_Arbitration = "/aftersales/delete/arbitrations/{id}";
    @BeforeAll
    public static void setup(){
        JwtHelper jwtHelper = new JwtHelper();
        userToken = jwtHelper.createToken(1L, "13088admin",0L,1, 3600);
    }
    @Test
    void createArbitration() throws Exception {
        Mockito.when(redisUtil.get("R3")).thenReturn(null);
        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);


        ArbitrationVo arbitrationVo=new ArbitrationVo();

        String RequestBody = "{\"reason\":\"换货商品质量不好\"}";
        arbitrationVo.setReason(RequestBody);


        this.mockMvc.perform(MockMvcRequestBuilders.post(AFTERSALES_ID_ARBITRATIONS, 3L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("authorization", userToken)
                        .content(arbitrationVo.getReason()))
                .andExpect(status().isCreated())
                //.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.aftersale_id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.reason").value("换货商品质量不好"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtCreate").exists());
    }
    /**
     * 删除仲裁单，只能删除一次
     */
    @Test
    public void deleteTest() throws Exception {
        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);
        Mockito.when(redisUtil.bfExist(Mockito.anyString(), (Long) Mockito.any())).thenReturn(false);
        Mockito.when(redisUtil.bfAdd(Mockito.anyString(), Mockito.any())).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_Arbitration, 2L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("authorization", userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno").value(0));
    }


    /**
     * 删除仲裁单，找不到售后单
     */
    @Test
    public void deleteTest1() throws Exception {
        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);
        Mockito.when(redisUtil.bfExist(Mockito.anyString(), (Long) Mockito.any())).thenReturn(false);
        Mockito.when(redisUtil.bfAdd(Mockito.anyString(), Mockito.any())).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_Arbitration, 4L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("authorization", userToken))
                //未找到对应的售后单
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno").value(4));
    }

    @Test
    /**
     * 仲裁单已结案,删除仲裁单
     */
    public void deleteTest2() throws Exception {
        Mockito.when(redisUtil.hasKey(Mockito.anyString())).thenReturn(false);
        Mockito.when(redisUtil.set(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(true);
        Mockito.when(redisUtil.bfExist(Mockito.anyString(), (Long) Mockito.any())).thenReturn(false);
        Mockito.when(redisUtil.bfAdd(Mockito.anyString(), Mockito.any())).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_Arbitration, 5L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("authorization", userToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno").value(7));
    }


}
