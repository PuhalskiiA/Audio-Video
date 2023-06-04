package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Utils.DAO.DirectorDao;
import Utils.LayerOfTypes.Type;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.puhalskii.coursebd.App;

public class TypesTableWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back_button;

    @FXML
    private TableView<Type> types_table;

    @FXML
    private TableColumn<Type, String> types_table_c_name;

    @FXML
    void initialize() throws SQLException {
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

        types_table_c_name.setCellValueFactory(new PropertyValueFactory<Type, String>("name"));

        types_table.setItems(DirectorDao.getTypes());
    }

}
