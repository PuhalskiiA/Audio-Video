package Controllers;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import Utils.DAO.UserDao;
import Utils.Exceptions.ProductException;
import Utils.LayerOfTypes.Characteristic;
import Utils.LayerOfTypes.FullProduct;
import Utils.LayerOfTypes.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.puhalskii.coursebd.App;

public class AddToCartWindowController {
    @FXML
    private Button add_to_cart_button;

    @FXML
    private Button additional_window_employee_director_back_button;

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
    private Spinner<Integer> count_box;

    @FXML
    private TableView<FullProduct> table;

    @FXML
    private static Product product;

    @FXML
    void initialize() throws SQLException {
        additional_window_employee_director_back_button.setOnAction(event -> {
            App.setScene("User", getClass().getClassLoader().getResource("UserWindow.fxml"));
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

        ObservableList<Characteristic> ch = UserDao.getCharacteristics(product.getName());
        ObservableList<FullProduct> fullProductList = FXCollections.observableArrayList();

        for (Characteristic c : ch) {
            FullProduct fp = new FullProduct(product.getName(), product.getType(), product.getSubtype(),
                    product.getQuantity(), product.getPrice(), product.getDescription(), c.getName(), c.getDescription());

            fullProductList.add(fp);
        }

        table.setItems(fullProductList);

        SpinnerValueFactory<Integer> gradesValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0);
        count_box.setValueFactory(gradesValueFactory);

        add_to_cart_button.setOnAction(event -> {
            try {
                int quantity = product.getQuantity() - count_box.getValue();

                if (quantity < 0) {
                    throw new ProductException("There are not enough products in the warehouse");
                }

                Date date = new Date(System.currentTimeMillis());

                int orderId = UserDao.getOrderId("Waiting", date);

                if (orderId > 0) {
                    UserDao.addOrderDetail(product.getName(), count_box.getValue(), orderId);
                } else {
                    UserDao.addOrder(0, date);
                    UserDao.addOrderDetail(product.getName(), count_box.getValue(), UserDao.getOrderId("Waiting", date));
                }

                double totalAmount = product.getPrice() * count_box.getValue();

                UserDao.updateOrderTotalAmount(totalAmount, date, UserDao.getOrderId("Waiting", date));

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

                App.setScene("User", getClass().getClassLoader()
                        .getResource("UserWindow.fxml"));

            } catch (ProductException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            }  catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
    }

    public Product getProduct() {
        return product;
    }

    public static void setProduct(Product p) {
        product = p;
    }
}
