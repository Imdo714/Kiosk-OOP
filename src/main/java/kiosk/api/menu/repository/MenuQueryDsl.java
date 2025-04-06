package kiosk.api.menu.repository;

import kiosk.api.menu.domain.MenuEntity;

import java.util.List;

public interface MenuQueryDsl {

    List<MenuEntity> selectMenu(String category, String name, String status);
}
