package com.lemon.computer.cotroller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @description SystemConfigControllerTest
 */
@WebMvcTest(SystemConfigController.class)
class SystemConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void testSystemConfigEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/systemConfig"))
                .andExpect(status().isOk());
    }
}
