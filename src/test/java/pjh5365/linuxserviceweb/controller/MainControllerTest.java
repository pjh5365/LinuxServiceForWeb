package pjh5365.linuxserviceweb.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import pjh5365.linuxserviceweb.service.ReadLogService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({MainController.class, ReadLogService.class})
public class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    ReadLogService readLogService;

    @Test
    @DisplayName("메인 컨트롤러가 메인페이지를 정상적으로 불러오는지 테스트한다.")
    void MainPageTest() throws Exception {
        // Given
        StringBuilder sb = new StringBuilder();
        sb.append("안녕하세요\n").append("반가워요\n");
        given(readLogService.getLog("testLog.txt")).willReturn(sb);

        // When
        String[] lines = sb.toString().split("\n");
        ModelMap model = new ModelMap();
        model.addAttribute("logs", lines);
        mockMvc.perform(MockMvcRequestBuilders.get("/"))

        // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("logs"));
    }
}
