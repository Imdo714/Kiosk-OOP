package kiosk.api.menu.controller;

import jakarta.validation.Valid;
import kiosk.api.ApiResponse;
import kiosk.api.menu.domain.dto.request.MenuCreateRequest;
import kiosk.api.menu.domain.dto.request.MenuUpdate;
import kiosk.api.menu.domain.dto.response.MenuListResponse;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.api.menu.service.MenuService;
import kiosk.api.menu.service.menuCommand.menuCrate.MenuCreateService;
import kiosk.api.menu.service.menuCommand.menuUpdate.MenuUpdateService;
import kiosk.api.menu.service.menuQuery.menuListQuery.MenuListQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MenuController {

    private final MenuService menuService;
    private final MenuCreateService menuCreateService;
    private final MenuUpdateService menuUpdateService;
    private final MenuListQuery menuListQuery;

    @PostMapping("/menu/new")
    public ApiResponse<MenuResponse> createMenu(@Valid @RequestBody MenuCreateRequest request){
        return ApiResponse.ok(menuCreateService.createMenu(request));
    }

    @PatchMapping("/menu/{menuId}")
    public ApiResponse<MenuResponse> updateMenu(@Valid @PathVariable Long menuId, @RequestBody MenuUpdate request){
        return ApiResponse.ok(menuUpdateService.updateMenu(menuId, request));
    }

    @GetMapping("/menu")
    public ApiResponse<MenuListResponse> selectMenu(@RequestParam(required = false) String category,
                                                    @RequestParam(required = false) String name,
                                                    @RequestParam(required = false) String status
    ){
        return ApiResponse.ok(menuListQuery.listQueryMenu(category, name, status));
    }

}
