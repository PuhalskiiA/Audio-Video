package Controllers;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;

import Utils.DAO.AccountDao;
import Utils.DAO.DirectorDao;
import Utils.DAO.UserDao;
import Utils.Exceptions.CartException;
import Utils.Exceptions.ProductException;
import Utils.Exceptions.UserException;
import Utils.LayerOfTypes.Cart;
import Utils.LayerOfTypes.Deal;
import Utils.LayerOfTypes.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.puhalskii.coursebd.App;

public class UserWindowController {

    @FXML
    private Button assortment_products_add_to_cart;

    @FXML
    private Button assortment_products_get_list_of_products_button;

    @FXML
    private Button assortment_products_get_more_info_button;

    @FXML
    private Tab assortment_products_tab;

    @FXML
    private TableView<Product> assortment_products_table;

    @FXML
    private TableColumn<Product, String> assortment_products_table_c_description;

    @FXML
    private TableColumn<Product, String> assortment_products_table_c_name;

    @FXML
    private TableColumn<Product, Double> assortment_products_table_c_price;

    @FXML
    private TableColumn<Product, Integer> assortment_products_table_c_quantity;

    @FXML
    private TableColumn<Product, String> assortment_products_table_c_subtype;

    @FXML
    private TableColumn<Product, String> assortment_products_table_c_type;

    @FXML
    private Tab assortment_tab;

    @FXML
    private Button exit_button;

    @FXML
    private PasswordField my_account_change_password_new_password_box;

    @FXML
    private Button my_account_change_password_ok_button;

    @FXML
    private PasswordField my_account_change_password_repeat_password_box;

    @FXML
    private Tab my_account_change_password_tab;

    @FXML
    private Button my_account_deals_add_comment_button;

    @FXML
    private Tab my_account_deals_tab;

    @FXML
    private TableView<Deal> my_account_deals_table;

    @FXML
    private TableColumn<Deal, Date> my_account_deals_table_c_date;

    @FXML
    private TableColumn<Deal, String> my_account_deals_table_c_name;

    @FXML
    private TableColumn<Deal, Double> my_account_deals_table_c_price;

    @FXML
    private TableColumn<Deal, Integer> my_account_deals_table_c_quantity;

    @FXML
    private TableColumn<Deal, String> my_account_deals_table_c_subtype;

    @FXML
    private TableColumn<Deal, Double> my_account_deals_table_c_total_amount;

    @FXML
    private TableColumn<Deal, String> my_account_deals_table_c_type;

    @FXML
    private Button my_account_deals_update_button;

    @FXML
    private Button my_account_personal_info_get_button;

    @FXML
    private Tab my_account_personal_info_tab;

    @FXML
    private TextArea my_account_personal_info_text_area;

    @FXML
    private Tab my_account_tab;

    @FXML
    private Button shopping_cart_my_cart_buy_button;

    @FXML
    private Tab shopping_cart_my_cart_tab;

    @FXML
    private TableView<Cart> shopping_cart_my_cart_table;

    @FXML
    private TableColumn<Cart, Double> shopping_cart_my_cart_table_c_total_amount;

    @FXML
    private TableColumn<Cart, String> shopping_cart_my_cart_table_c_name;

    @FXML
    private TableColumn<Cart, Double> shopping_cart_my_cart_table_c_price;

    @FXML
    private TableColumn<Cart, Integer> shopping_cart_my_cart_table_c_quantity;

    @FXML
    private TableColumn<Cart, String> shopping_cart_my_cart_table_c_subtype;

    @FXML
    private TableColumn<Cart, String> shopping_cart_my_cart_table_c_type;

    @FXML
    private Button shopping_cart_my_cart_remove_button;

    @FXML
    private Button shopping_cart_my_cart_update_cart_button;

    @FXML
    private Tab shopping_cart_tab;

    @FXML
    private Button assortment_products_add_to_cart_button;


    @FXML
    void initialize() {
        //Exit button
        //--------------------------------------------------------------------------------------------------------------
        exit_button.setOnAction(event -> {
            App.setScene("Authorization", getClass().getClassLoader().getResource("AuthWindow.fxml"));
        });
        //--------------------------------------------------------------------------------------------------------------

        //My account
        //--------------------------------------------------------------------------------------------------------------
        //Personal info
        //--------------------------------------------------------------------------------------------------------------
        my_account_personal_info_get_button.setOnAction(event -> {
            try {
                my_account_personal_info_text_area.setText(UserDao.getUserInfo());
            } catch (SQLException | UserException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Change password
        //--------------------------------------------------------------------------------------------------------------
        my_account_change_password_ok_button.setOnAction(event -> {
            String newPassword = my_account_change_password_new_password_box.getText();
            String repeatPassword = my_account_change_password_repeat_password_box.getText();

            if (!newPassword.isEmpty() && newPassword.equals(repeatPassword)) {
                try {
                    AccountDao.updatePassword(newPassword);
                } catch (SQLException | NoSuchAlgorithmException e) {
                    App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                    e.printStackTrace();
                }

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");
            } else {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Passwords are empty or not equals");
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Deals
        //--------------------------------------------------------------------------------------------------------------
        my_account_deals_update_button.setOnAction(event -> {
            my_account_deals_table_c_name.setCellValueFactory(new PropertyValueFactory<Deal, String>("name"));
            my_account_deals_table_c_type.setCellValueFactory(new PropertyValueFactory<Deal, String>("type"));
            my_account_deals_table_c_subtype.setCellValueFactory(new PropertyValueFactory<Deal, String>("subtype"));
            my_account_deals_table_c_quantity.setCellValueFactory(new PropertyValueFactory<Deal, Integer>("quantity"));
            my_account_deals_table_c_price.setCellValueFactory(new PropertyValueFactory<Deal, Double>("price"));
            my_account_deals_table_c_total_amount.setCellValueFactory(new PropertyValueFactory<Deal, Double>("totalAmount"));
            my_account_deals_table_c_date.setCellValueFactory(new PropertyValueFactory<Deal, Date>("date"));

            try {
                my_account_deals_table.setItems(UserDao.getBoughtProducts());
            }  catch (UserException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "User not found");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });

        my_account_deals_add_comment_button.setOnAction(event -> {
            Deal deal = my_account_deals_table.getSelectionModel().getSelectedItem();

            CommentWindowController.setOrder(deal.getId());

            App.setScene("Comment", getClass().getClassLoader()
                    .getResource("CommentWindow.fxml"));
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        //Get assortment
        //--------------------------------------------------------------------------------------------------------------
        assortment_products_get_list_of_products_button.setOnAction(event -> {
            assortment_products_table_c_name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            assortment_products_table_c_type.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));
            assortment_products_table_c_subtype.setCellValueFactory(new PropertyValueFactory<Product, String>("subtype"));
            assortment_products_table_c_quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
            assortment_products_table_c_price.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
            assortment_products_table_c_description.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));

            try {
                assortment_products_table.setItems(DirectorDao.getAssortment());
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });

        assortment_products_get_more_info_button.setOnAction(event -> {
            Product product = assortment_products_table.getSelectionModel().getSelectedItem();

            AddToCartWindowController.setProduct(product);

            App.setScene("Full information", getClass().getClassLoader()
                    .getResource("AddToCartWindow.fxml"));
        });
        //--------------------------------------------------------------------------------------------------------------

        //Cart
        //--------------------------------------------------------------------------------------------------------------
        shopping_cart_my_cart_update_cart_button.setOnAction(event -> {
            shopping_cart_my_cart_table_c_name.setCellValueFactory(new PropertyValueFactory<Cart, String>("name"));
            shopping_cart_my_cart_table_c_type.setCellValueFactory(new PropertyValueFactory<Cart, String>("type"));
            shopping_cart_my_cart_table_c_subtype.setCellValueFactory(new PropertyValueFactory<Cart, String>("subtype"));
            shopping_cart_my_cart_table_c_quantity.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("quantity"));
            shopping_cart_my_cart_table_c_price.setCellValueFactory(new PropertyValueFactory<Cart, Double>("price"));
            shopping_cart_my_cart_table_c_total_amount.setCellValueFactory(new PropertyValueFactory<Cart, Double>("totalAmount"));

            try {
                shopping_cart_my_cart_table.setItems(UserDao.getCart(new Date(System.currentTimeMillis()), "Waiting"));
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }

        });

        shopping_cart_my_cart_remove_button.setOnAction(event -> {
            Cart item = shopping_cart_my_cart_table.getSelectionModel().getSelectedItem();

            try {
                if (item == null) {
                    throw new CartException("You should select item to delete");
                }

                UserDao.deleteCartItem(item.getName(), item.getQuantity());

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (CartException | ProductException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });

        shopping_cart_my_cart_buy_button.setOnAction(event -> {
            try {
                ObservableList<Cart> cart = UserDao.getCart(new Date(System.currentTimeMillis()), "Waiting");

                if (cart == null) {
                    throw new CartException("Cart is empty");
                }

                for (Cart c : cart) {
                    UserDao.updateQuantity(c.getName(), UserDao.getProduct(c.getName()).getQuantity() - c.getQuantity());
                }

                UserDao.updateStatus();

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (CartException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Cart is empty");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------

    }
}
