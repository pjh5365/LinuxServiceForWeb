package pjh5365.linuxserviceweb.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pjh5365.linuxserviceweb.service.ReadNginxAccessLogService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({MainController.class, ReadNginxAccessLogService.class})
public class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("메인 컨트롤러가 메인페이지를 정상적으로 불러오는지 테스트한다.")
    void MainPageTest() throws Exception {
        // Given

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/"))

        // Then
                .andExpect(status().isOk());
    }
}
