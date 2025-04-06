package kiosk.api.menu.repository;

import kiosk.api.menu.domain.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long>, MenuQueryDsl {


}
