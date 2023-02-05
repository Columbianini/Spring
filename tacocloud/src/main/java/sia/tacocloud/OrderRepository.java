package sia.tacocloud;

import java.util.Optional;
import sia.tacocloud.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
