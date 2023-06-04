package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Utils.DAO.DirectorDao;
import Utils.LayerOfTypes.Characteristic;
import Utils.LayerOfTypes.FullProduct;
import Utils.LayerOfTypes.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.puhalskii.coursebd.App;

public class FullInformationAboutProductController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back_button;

    @FXML
    private TableColumn<FullProduct, String> c_characteristic;

    @FXML
    private TableColumn<FullProduct, String> c_characteristic_name;

    @FXML
    private TableColumn<FullProduct, String> c_description;

    @FXML
    private TableColumn<FullProduct, String> c_name;

    @FXML
    private TableColumn<FullProduct, Double> c_price;

    @FXML
    private TableColumn<FullProduct, Integer> c_quantity;

    @FXML
    private TableColumn<FullProduct, String> c_subtype;

    @FXML
    private TableColumn<FullProduct, String> c_type;

    @FXML
    private TableView<FullProduct> table;

    private static Product product;

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

        ObservableList<Product> p = FXCollections.observableArrayList();

        p.add(product);

        c_name.setCellValueFactory(new PropertyValueFactory<FullProduct, String>("name"));
        c_type.setCellValueFactory(new PropertyValueFactory<FullProduct, String>("type"));
        c_subtype.setCellValueFactory(new PropertyValueFactory<FullProduct, String>("subtype"));
        c_quantity.setCellValueFactory(new PropertyValueFactory<FullProduct, Integer>("quantity"));
        c_price.setCellValueFactory(new PropertyValueFactory<FullProduct, Double>("price"));
        c_description.setCellValueFactory(new PropertyValueFactory<FullProduct, String>("description"));
        c_characteristic_name.setCellValueFactory(new PropertyValueFactory<FullProduct, String>("characteristic"));
        c_characteristic.setCellValueFactory(new PropertyValueFactory<FullProduct, String>("characteristicDescription"));

        ObservableList<Characteristic> ch = DirectorDao.getCharacteristics(product.getName());
        ObservableList<FullProduct> fullProductList = FXCollections.observableArrayList();

        for (Characteristic c : ch) {
            FullProduct fp = new FullProduct(product.getName(), product.getType(), product.getSubtype(),
                    product.getQuantity(), product.getPrice(), product.getDescription(), c.getName(), c.getDescription());

            fullProductList.add(fp);
        }

        table.setItems(fullProductList);
    }

    public Product getProduct() {
        return product;
    }

    public static void setProduct(Product p) {
        product = p;
    }
}