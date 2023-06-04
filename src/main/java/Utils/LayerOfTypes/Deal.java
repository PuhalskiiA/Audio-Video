package Utils.LayerOfTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
public class Deal {
    int id;
    String name;
    String type;
    String subtype;
    int quantity;
    double price;
    double totalAmount;
    Date date;
    String description;
}
