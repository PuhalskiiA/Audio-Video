package Controllers;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Utils.DAO.AuthDao;
import Utils.Exceptions.UserException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.puhalskii.coursebd.App;

public class SignUpWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_signUp_box;

    @FXML
    private PasswordField password_signUp_box;

    @FXML
    private PasswordField r_password_signUp_box;

    @FXML
    private Button signUp_back_button;

    @FXML
    private Button signUp_button;

    @FXML
    void initialize() {
        signUp_button.setOnAction(event -> {
            String password = password_signUp_box.getText();
            String passwordRepeat = r_password_signUp_box.getText();

            if (password.equals(passwordRepeat) && !password.isEmpty()) {
                try {
                    try {
                        AuthDao.signUpUser(login_signUp_box.getText(), password_signUp_box.getText());
                        App.setAlert("SignUp", Alert.AlertType.CONFIRMATION, "Success");
                        App.setScene("Authorization", getClass().getClassLoader().getResource("AuthWindow.fxml"));
                    } catch (UserException e) {
                        App.setAlert("SignUp", Alert.AlertType.WARNING, "You can't take this login");
                    }
                } catch (SQLException | NoSuchAlgorithmException e) {
                    App.setAlert("Error", Alert.AlertType.ERROR, e.getMessage());
                }
            } else {
                App.setAlert("SignUp", Alert.AlertType.WARNING, "Passwords is not equals or empty");
            }
        });

        signUp_back_button.setOnAction(event -> {
            App.setScene("Authorization", getClass().getClassLoader().getResource("AuthWindow.fxml"));
        });
    }

}
