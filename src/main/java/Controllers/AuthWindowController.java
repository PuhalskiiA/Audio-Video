package Controllers;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Utils.DAO.AuthDao;
import Utils.Exceptions.AuthException;
import Utils.LayerOfTypes.UserInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.puhalskii.coursebd.App;

public class AuthWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_box;

    @FXML
    private Button login_signIn_button;

    @FXML
    private Button login_signUp_button;

    @FXML
    private PasswordField password_box;

    @FXML
    void initialize() {
        login_signIn_button.setOnAction(event -> {
                    if (password_box.getText().isEmpty()) {
                        App.setAlert("SignIn", Alert.AlertType.WARNING, "Enter the password");
                    } else {
                        try {
                            UserInfo userInfo = AuthDao.authentication(login_box.getText(), password_box.getText());

                            App.userLogin = userInfo.getLogin();
                            App.userId = userInfo.getId();
                            App.userRole = userInfo.getRole();

                            if (!userInfo.getFBlocked()) {
                                switch (App.userRole) {
                                    case 1 -> {
                                        App.setScene("User", getClass().getClassLoader()
                                            .getResource("UserWindow.fxml"));
                                    }
                                    case 2 -> {
                                        App.setScene("Sales manager", getClass().getClassLoader()
                                            .getResource("SalesManagerWindow.fxml"));
                                    }
                                    case 3 -> {
                                        App.setScene("Production worker", getClass().getClassLoader()
                                                .getResource("ProductionWorkerWindow.fxml"));
                                    }
                                    case 4 -> {
                                        App.setScene("Director", getClass().getClassLoader()
                                                .getResource("DirectorWindow.fxml"));
                                    }
                                }
                            }
                        } catch (AuthException e) {
                            App.setAlert("Sign in", Alert.AlertType.ERROR, "Incorrect password or login");
                            e.printStackTrace();
                        } catch (SQLException | NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                    }
                });

                login_signUp_button.setOnAction(event -> {
                    App.setScene("SignUp", getClass().getClassLoader()
                            .getResource("SignUpWindow.fxml"));
                });
    }

}
