package kiosk.api.menu.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMenuEntity is a Querydsl query type for MenuEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenuEntity extends EntityPathBase<MenuEntity> {

    private static final long serialVersionUID = -275484330L;

    public static final QMenuEntity menuEntity = new QMenuEntity("menuEntity");

    public final EnumPath<MenuCategory> menuCategory = createEnum("menuCategory", MenuCategory.class);

    public final NumberPath<Long> menuId = createNumber("menuId", Long.class);

    public final StringPath menuName = createString("menuName");

    public final NumberPath<Integer> menuPrice = createNumber("menuPrice", Integer.class);

    public final EnumPath<MenuStatus> menuStatus = createEnum("menuStatus", MenuStatus.class);

    public QMenuEntity(String variable) {
        super(MenuEntity.class, forVariable(variable));
    }

    public QMenuEntity(Path<? extends MenuEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMenuEntity(PathMetadata metadata) {
        super(MenuEntity.class, metadata);
    }

}

