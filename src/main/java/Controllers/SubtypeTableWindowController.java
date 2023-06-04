package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Utils.DAO.DirectorDao;
import Utils.LayerOfTypes.Subtype;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.puhalskii.coursebd.App;

public class SubtypeTableWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back_button;

    @FXML
    private TableView<Subtype> subtype_table;

    @FXML
    private TableColumn<Subtype, String> subtype_table_c_subtype;

    @FXML
    private TableColumn<Subtype, String> subtype_table_c_type;

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

        subtype_table_c_type.setCellValueFactory(new PropertyValueFactory<Subtype, String>("typeName"));
        subtype_table_c_subtype.setCellValueFactory(new PropertyValueFactory<Subtype, String>("subtypeName"));

        subtype_table.setItems(DirectorDao.getSubtypes());
    }

}
