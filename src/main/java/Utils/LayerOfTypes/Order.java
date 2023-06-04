package Utils.LayerOfTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
public class Order {
    String login;
    Date date;
    String productName;
    int quantity;
    Double totalAmount;
    String status;
}
