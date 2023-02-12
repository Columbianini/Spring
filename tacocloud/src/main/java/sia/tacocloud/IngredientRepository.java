package sia.tacocloud;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
