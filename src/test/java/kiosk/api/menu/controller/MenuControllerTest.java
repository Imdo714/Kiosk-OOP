package kiosk.api.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kiosk.api.menu.domain.dto.request.MenuCreateRequest;
import kiosk.api.menu.domain.dto.request.MenuUpdate;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.api.menu.service.MenuService;
import kiosk.api.menu.service.MenuServiceImpl;
import kiosk.api.menu.service.menuCommand.menuCrate.MenuCreateService;
import kiosk.api.menu.service.menuCommand.menuUpdate.MenuUpdateService;
import kiosk.api.menu.service.menuQuery.menuListQuery.MenuListQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static kiosk.api.menu.domain.common.MenuCategory.HANDMADE;
import static kiosk.api.menu.domain.common.MenuStatus.SELLING;
import static kiosk.api.menu.domain.common.MenuStatus.STOP_SELLING;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private MenuService menuService;

    @MockitoBean
    private MenuCreateService menuCreateService;

    @MockitoBean
    private MenuUpdateService menuUpdateService;

    @MockitoBean
    private MenuListQuery menuListQuery;

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

        when(menuUpdateService.updateMenu(menuId, menuUpdate)).thenReturn(expectedResponse);

        // when & then
        mockMvc.perform(patch("/menu/{menuId}", menuId)
                .content(objectMapper.writeValueAsString(menuUpdate))
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("메뉴 조회")
    @Test
    void selectMenu() throws Exception {
        // given

        // when // then
        mockMvc.perform(get("/menu")
                        .queryParam("category", "HANDMADE")
                        .queryParam("name", "아메리카노")
                        .queryParam("status", "SELLING")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}