package Utils.LayerOfTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Cart {
    String name;
    String type;
    String subtype;
    int quantity;
    double price;
    double totalAmount;
}
