package Utils.LayerOfTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserInfo {
    int id;
    String login;
    String hashedPassword;
    int role;
    Boolean fBlocked;
}
