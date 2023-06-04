package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Utils.DAO.DirectorDao;
import Utils.LayerOfTypes.Characteristic;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.puhalskii.coursebd.App;

public class CharacteristicsTableWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back_button;

    @FXML
    private TableView<Characteristic> characteristic_table;

    @FXML
    private TableColumn<Characteristic, String> characteristic_table_c_description;

    @FXML
    private TableColumn<Characteristic, String> characteristic_table_c_name;

    @FXML
    void initialize() {
        back_button.setOnAction(event -> {
            switch (App.userRole) {
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
        });

        characteristic_table_c_name.setCellValueFactory(new PropertyValueFactory<Characteristic, String>("name"));
        characteristic_table_c_description.setCellValueFactory(new PropertyValueFactory<Characteristic, String>("description"));

        try {
            characteristic_table.setItems(DirectorDao.getCharacteristics());
        } catch (SQLException e) {
            App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
            e.printStackTrace();
        }
    }

}
