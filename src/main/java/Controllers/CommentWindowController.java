package Controllers;

import Utils.DAO.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import ru.puhalskii.coursebd.App;

import java.sql.SQLException;

public class CommentWindowController {

    @FXML
    private Button add_comment_button;

    @FXML
    private Button back_button;

    @FXML
    private TextArea text_area;

    private static int order;

    @FXML
    void initialize() {
        back_button.setOnAction(event -> {
            App.setScene("User", getClass().getClassLoader().getResource("UserWindow.fxml"));
        });

        add_comment_button.setOnAction(event -> {
            String comment = text_area.getText();

            try {
                UserDao.addComment(order, comment);

                App.setAlert("SuccessWindow", Alert.AlertType.INFORMATION, "Success");

                App.setScene("User", getClass().getClassLoader().getResource("UserWindow.fxml"));

            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
    }

    public static int getOrder() {
        return order;
    }

    public static void setOrder(int orderId) {
        order = orderId;
    }
}
