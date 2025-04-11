package kiosk.api.menu.controller;

import jakarta.validation.Valid;
import kiosk.api.ApiResponse;
import kiosk.api.menu.domain.dto.request.MenuCreateRequest;
import kiosk.api.menu.domain.dto.request.MenuUpdate;
import kiosk.api.menu.domain.dto.response.MenuListResponse;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.api.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/menu/new")
    public ApiResponse<MenuResponse> createMenu(@Valid @RequestBody MenuCreateRequest request){
        return ApiResponse.ok(menuService.createMenu(request));
    }

    @PatchMapping("/menu/{menuId}")
    public ApiResponse<MenuResponse> updateMenu(@Valid @PathVariable Long menuId, @RequestBody MenuUpdate request){
        return ApiResponse.ok(menuService.updateMenu(menuId, request));
    }

    @GetMapping("/menu") // 메뉴 조회
    public ApiResponse<MenuListResponse> selectMenu(@RequestParam(required = false) String category,
                                                    @RequestParam(required = false) String name,
                                                    @RequestParam(required = false) String status
    ){
        return ApiResponse.ok(menuService.selectMenu(category, name, status));
    }

}
