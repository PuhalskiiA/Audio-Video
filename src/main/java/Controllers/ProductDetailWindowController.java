package Controllers;

import java.sql.SQLException;

import Utils.DAO.DirectorDao;
import Utils.LayerOfTypes.ProductDetail;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.puhalskii.coursebd.App;

public class ProductDetailWindowController {

    @FXML
    private Button back_button;

    @FXML
    private TableColumn<ProductDetail, String> product_detail_window_c_characteristic;

    @FXML
    private TableColumn<ProductDetail, String> product_detail_window_c_description;

    @FXML
    private TableColumn<ProductDetail, String> product_detail_window_c_product;

    @FXML
    private TableColumn<ProductDetail, String> product_detail_window_c_subtype;

    @FXML
    private TableColumn<ProductDetail, String> product_detail_window_c_type;

    @FXML
    private TableView<ProductDetail> product_detail_window_table;


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

        product_detail_window_c_product.setCellValueFactory(new PropertyValueFactory<ProductDetail, String>("productName"));
        product_detail_window_c_characteristic.setCellValueFactory(new PropertyValueFactory<ProductDetail, String>("characteristicName"));
        product_detail_window_c_type.setCellValueFactory(new PropertyValueFactory<ProductDetail, String>("productType"));
        product_detail_window_c_subtype.setCellValueFactory(new PropertyValueFactory<ProductDetail, String>("productSubtype"));
        product_detail_window_c_description.setCellValueFactory(new PropertyValueFactory<ProductDetail, String>("characteristicDescription"));

        try {
            product_detail_window_table.setItems(DirectorDao.getProductDetail());
        } catch (SQLException e) {
            App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
            e.printStackTrace();
        }
    }

}
