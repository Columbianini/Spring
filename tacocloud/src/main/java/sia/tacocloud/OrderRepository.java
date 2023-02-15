package sia.tacocloud;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, String> {
}
