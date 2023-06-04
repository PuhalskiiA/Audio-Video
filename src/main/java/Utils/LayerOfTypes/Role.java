package Utils.LayerOfTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Role {
    String name;

    @Override
    public String toString() {
        return name;
    }
}


