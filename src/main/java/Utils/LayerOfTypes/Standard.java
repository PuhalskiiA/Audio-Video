package Utils.LayerOfTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter
public class Standard {
    String role;
    String standard;
    Date start;
    Date end;
}
