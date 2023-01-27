package sia.tacocloud;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TacoOrder {
    private final String deliveryName;
    private final String deliveryStreet;
    private final String deliveryCity;
    private final String deliveryState;
    private final String deliveryZip;
    private final String ccNumber;
    private final String ccExpiration;
    private final String ccCVV;

    private final List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco){
        tacos.add(taco);
    }
}
