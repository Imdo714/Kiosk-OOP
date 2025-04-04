package kiosk.api.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kiosk.api.menu.domain.request.MenuCreateRequest;
import kiosk.api.menu.domain.request.MenuUpdate;
import kiosk.api.menu.domain.response.MenuResponse;
import kiosk.api.menu.service.MenuServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static kiosk.api.menu.domain.MenuCategory.HANDMADE;
import static kiosk.api.menu.domain.MenuStatus.SELLING;
import static kiosk.api.menu.domain.MenuStatus.STOP_SELLING;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = MenuController.class)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MenuServiceImpl menuServiceImpl;

    @DisplayName("새로운 매뉴 등록 성공 결과 값")
    @Test
    void createMenu() throws Exception {
        // given
        MenuCreateRequest request = MenuCreateRequest.builder()
                .menuName("아메리카노")
                .menuPrice(1000)
                .menuCategory(HANDMADE)
                .menuStatus(SELLING)
                .build();

        // when // then
        mockMvc.perform(post("/menu/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("새로운 매뉴 등록시 메뉴 이름은 필수입니다.")
    @Test
    void createMenuWhitNotMenuName() throws Exception {
        // given
        MenuCreateRequest request = MenuCreateRequest.builder()
                .menuPrice(1000)
                .menuCategory(HANDMADE)
                .menuStatus(SELLING)
                .build();

        // when // then
        mockMvc.perform(post("/menu/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("메뉴 이름은 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

    @DisplayName("메뉴 가격 상태값 수정 결과 값")
    @Test
    void updateMenu() throws Exception {
        // given
        Long menuId = 1L;
        MenuUpdate menuUpdate = MenuUpdate.builder()
                .menuPrice(1000)
                .menuStatus(SELLING)
                .build();

        MenuResponse expectedResponse = MenuResponse.builder()
                .menuId(menuId)
                .menuName("아메리카노")
                .menuPrice(2000)
                .menuCategory(HANDMADE)
                .menuStatus(STOP_SELLING)
                .build();

        when(menuServiceImpl.updateMenu(menuId, menuUpdate)).thenReturn(expectedResponse);

        // when & then
        mockMvc.perform(patch("/menu/{menuId}", menuId)
                .content(objectMapper.writeValueAsString(menuUpdate))
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}