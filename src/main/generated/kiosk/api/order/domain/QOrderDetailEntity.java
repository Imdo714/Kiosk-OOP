package kiosk.api.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderDetailEntity is a Querydsl query type for OrderDetailEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderDetailEntity extends EntityPathBase<OrderDetailEntity> {

    private static final long serialVersionUID = -336604335L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderDetailEntity orderDetailEntity = new QOrderDetailEntity("orderDetailEntity");

    public final kiosk.api.menu.domain.QMenuEntity menuEntity;

    public final NumberPath<Long> orderDetailId = createNumber("orderDetailId", Long.class);

    public final NumberPath<Integer> orderDetailQuantity = createNumber("orderDetailQuantity", Integer.class);

    public final QOrderEntity orderEntity;

    public QOrderDetailEntity(String variable) {
        this(OrderDetailEntity.class, forVariable(variable), INITS);
    }

    public QOrderDetailEntity(Path<? extends OrderDetailEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderDetailEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderDetailEntity(PathMetadata metadata, PathInits inits) {
        this(OrderDetailEntity.class, metadata, inits);
    }

    public QOrderDetailEntity(Class<? extends OrderDetailEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menuEntity = inits.isInitialized("menuEntity") ? new kiosk.api.menu.domain.QMenuEntity(forProperty("menuEntity")) : null;
        this.orderEntity = inits.isInitialized("orderEntity") ? new QOrderEntity(forProperty("orderEntity")) : null;
    }

}

