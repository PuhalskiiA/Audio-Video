package Utils.LayerOfTypes;

import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FullProduct {
    String name;
    String type;
    String subtype;
    int quantity;
    double price;
    String description;
    String characteristic;
    String characteristicDescription;
}
