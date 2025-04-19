package kiosk.api.menu.repository.queryDsl;

import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.api.menu.domain.entity.MenuEntity;

import java.util.List;

public interface MenuQueryDsl {

    List<MenuResponse> selectMenu(String category, String name, String status);
}
