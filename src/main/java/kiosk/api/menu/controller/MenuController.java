package kiosk.api.menu.controller;

import jakarta.validation.Valid;
import kiosk.api.ApiResponse;
import kiosk.api.menu.domain.request.MenuCreateRequest;
import kiosk.api.menu.domain.request.MenuUpdate;
import kiosk.api.menu.domain.response.MenuResponse;
import kiosk.api.menu.service.MenuService;
import kiosk.api.menu.service.MenuServiceImpl;
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

}
