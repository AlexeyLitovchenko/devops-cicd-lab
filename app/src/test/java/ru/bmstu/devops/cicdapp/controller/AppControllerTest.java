package ru.bmstu.devops.cicdapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

class AppControllerTest {

    @Nested
    @SpringBootTest(properties = "feature.new-greeting=false")
    @AutoConfigureMockMvc
    class DefaultGreetingTests {

        @Autowired
        private MockMvc mockMvc;

        @Test
        void shouldReturnServiceInfo() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.service").value("devops-cicd-lab"))
                    .andExpect(jsonPath("$.version").value("1.0.0"))
                    .andExpect(jsonPath("$.hostname").exists());
        }

        @Test
        void shouldReturnHealthStatus() throws Exception {
            mockMvc.perform(get("/health"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("ok"));
        }

        @Test
        void shouldReturnClassicGreetingWhenFeatureDisabled() throws Exception {
            mockMvc.perform(get("/greeting"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Hello from the classic greeting!"));
        }
    }

    @Nested
    @SpringBootTest(properties = "feature.new-greeting=true")
    @AutoConfigureMockMvc
    class NewGreetingTests {

        @Autowired
        private MockMvc mockMvc;

        @Test
        void shouldReturnNewGreetingWhenFeatureEnabled() throws Exception {
            mockMvc.perform(get("/greeting"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Hello from the new greeting feature!"));
        }
    }
}