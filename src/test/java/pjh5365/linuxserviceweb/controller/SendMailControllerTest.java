package pjh5365.linuxserviceweb.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pjh5365.linuxserviceweb.service.SendMailService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SendMailController.class, SendMailService.class})
public class SendMailControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("메일전송을 위한 페이지 이동이 정상적으로 이루어지는지 테스트")
    void sendMailService() throws Exception {
        // Given

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/sendmail"))

                // Then
                .andExpect(status().isOk());
    }
}
