package kiosk.api.menu.repository.queryDsl;

import kiosk.api.menu.domain.entity.MenuEntity;

import java.util.List;

public interface MenuQueryDsl {

    List<MenuEntity> selectMenu(String category, String name, String status);
}
