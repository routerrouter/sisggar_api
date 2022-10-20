package dev.router.sisggar_api.specifications;


import dev.router.sisggar_api.entity.DeliveryManager;
import dev.router.sisggar_api.entity.Location;
import dev.router.sisggar_api.entity.Storage;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    @And({
            @Spec(path = "storageStatus", spec = Equal.class),
            @Spec(path = "description", spec = Like.class)
    })
    public interface StorageSpec extends Specification<Storage> {}

    @And({
            @Spec(path = "status", spec = Equal.class),
            @Spec(path = "phoneNumber", spec = Like.class)
    })
    public interface DeliveryManagerSpec extends Specification<DeliveryManager> {}

    @And({
            @Spec(path = "locationStatus", spec = Equal.class),
            @Spec(path = "description", spec = Like.class)
    })
    public interface LocationSpec extends Specification<Location> {}


}
