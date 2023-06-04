package Utils.LayerOfTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Characteristic {
    String name;
    String description;

    @Override
    public String toString() {
        return name + " : " + description + "\n";
    }
}
