package Utils.LayerOfTypes;

import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Product {
    String name;
    String type;
    String subtype;
    int quantity;
    double price;
    String description;

    @Override
    public String toString() {
        return "Name='" + name +
                "\nType='" + type +
                "\nSubtype='" + subtype +
                "\nQuantity=" + quantity +
                "\nPrice=" + price +
                "\nDescription='" + description;
    }
}
