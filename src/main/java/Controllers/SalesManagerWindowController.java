package Controllers;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;

import Utils.DAO.AccountDao;
import Utils.DAO.DirectorDao;
import Utils.DAO.SalesManagerDao;
import Utils.Exceptions.*;
import Utils.LayerOfTypes.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import ru.puhalskii.coursebd.App;

public class SalesManagerWindowController {

    @FXML
    private AnchorPane assortment_characteristic_description_box1;

    @FXML
    private Button assortment_new_characteristic_add_button;

    @FXML
    private Button assortment_new_characteristic_delete_button;

    @FXML
    private TextField assortment_new_characteristic_description_box;

    @FXML
    private Button assortment_new_characteristic_get_characteristics_button;

    @FXML
    private TextField assortment_new_characteristic_name_box;

    @FXML
    private Tab assortment_new_characteristic_tab;

    @FXML
    private Button assortment_new_product_add_button;

    @FXML
    private Button assortment_new_product_delete_button;

    @FXML
    private TextField assortment_new_product_description_box;

    @FXML
    private Button assortment_new_product_detail_add_button;

    @FXML
    private TextField assortment_new_product_detail_characteristic_name_box;

    @FXML
    private Button assortment_new_product_detail_delete_button;

    @FXML
    private TextField assortment_new_product_detail_description_box;

    @FXML
    private Button assortment_new_product_detail_get_details_button;

    @FXML
    private TextField assortment_new_product_detail_product_name_box;

    @FXML
    private Button assortment_new_product_get_subtypes_button;

    @FXML
    private Button assortment_new_product_get_types_button;

    @FXML
    private TextField assortment_new_product_name_box;

    @FXML
    private TextField assortment_new_product_price_box;

    @FXML
    private TextField assortment_new_product_quantity_box;

    @FXML
    private ComboBox<String> assortment_new_product_subtype_name_box;

    @FXML
    private Tab assortment_new_product_tab;

    @FXML
    private ComboBox<Type> assortment_new_product_type_name_box;

    @FXML
    private Button assortment_new_product_update_button;

    @FXML
    private Button assortment_new_subtype_add_button;

    @FXML
    private Button assortment_new_subtype_delete_button;

    @FXML
    private Button assortment_new_subtype_get_subtypes_button;

    @FXML
    private TextField assortment_new_subtype_name_box;

    @FXML
    private Tab assortment_new_subtype_tab;

    @FXML
    private TextField assortment_new_subtype_type_name_box;

    @FXML
    private Button assortment_new_type_add_button;

    @FXML
    private Button assortment_new_type_delete_button;

    @FXML
    private Button assortment_new_type_get_types_button;

    @FXML
    private TextField assortment_new_type_name_box;

    @FXML
    private Tab assortment_new_type_tab;

    @FXML
    private Tab assortment_product_detail_tab;

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
    private Button assortment_products_update_button;

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
    private Button my_account_personal_info_get_button;

    @FXML
    private Tab my_account_personal_info_tab;

    @FXML
    private TextArea my_account_personal_info_text_area;

    @FXML
    private Tab my_account_tab;

    @FXML
    private Button reports_check_reports_get_reports_button;

    @FXML
    private TableView<Report> reports_check_reports_table;

    @FXML
    private TableColumn<Report, Date> reports_check_reports_table_c_date;

    @FXML
    private TableColumn<Report, String> reports_check_reports_table_c_dep;

    @FXML
    private TableColumn<Report, String> reports_check_reports_table_c_description;

    @FXML
    private TableColumn<Report, String> reports_check_reports_table_c_emp;

    @FXML
    private Tab sales_activities_check_my_reports_tab;

    @FXML
    private Button sales_activities_check_standards_get_button;

    @FXML
    private Tab sales_activities_check_standards_tab;

    @FXML
    private TableView<Standard> sales_activities_check_standards_table;

    @FXML
    private TableColumn<Standard, Date> sales_activities_check_standards_table_c_date_end;

    @FXML
    private TableColumn<Standard, Date> sales_activities_check_standards_table_c_date_start;

    @FXML
    private TableColumn<Standard, String> sales_activities_check_standards_table_c_role;

    @FXML
    private TableColumn<Standard, String> sales_activities_check_standards_table_c_standard;

    @FXML
    private DatePicker sales_activities_my_reports_date_end_box;

    @FXML
    private DatePicker sales_activities_my_reports_date_start_box;

    @FXML
    private DatePicker sales_activities_new_report_date;

    @FXML
    private TextField sales_activities_new_report_description;

    @FXML
    private Button sales_activities_new_report_get_list_button;

    @FXML
    private ComboBox<Department> sales_activities_new_report_list;

    @FXML
    private Button sales_activities_new_report_ok_button;

    @FXML
    private Tab sales_activities_new_report_tab;

    @FXML
    private DatePicker sales_activities_orders_date_end_box;

    @FXML
    private DatePicker sales_activities_orders_date_start_box;

    @FXML
    private TableView<Order> sales_activities_orders_table;

    @FXML
    private TableColumn<Order, Date> sales_activities_orders_table_c_date;

    @FXML
    private TableColumn<Order, String> sales_activities_orders_table_c_login;

    @FXML
    private TableColumn<Order, String> sales_activities_orders_table_c_product;

    @FXML
    private TableColumn<Order, Integer> sales_activities_orders_table_c_quantity;

    @FXML
    private TableColumn<Order, String> sales_activities_orders_table_c_status;

    @FXML
    private TableColumn<Order, Double> sales_activities_orders_table_c_total_amount;

    @FXML
    private Button sales_activities_orders_table_update_button;

    @FXML
    private Tab sales_activities_tab;

    @FXML
    private Button assortment_products_get_more_information_button;

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
                my_account_personal_info_text_area.setText(AccountDao.getAccountInformation());
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

            try {
                if (newPassword.isEmpty() || repeatPassword.isEmpty() || !newPassword.equals(repeatPassword)) {
                    throw new NumberFormatException("Boxes are empty or passwords not equals");
                }

                AccountDao.updatePassword(newPassword);

                App.setAlert("SuccessWindow", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty or passwords not equals");
                e.printStackTrace();
            } catch (SQLException | NoSuchAlgorithmException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        //Sales activities
        //--------------------------------------------------------------------------------------------------------------
        //Check reports
        //--------------------------------------------------------------------------------------------------------------
        reports_check_reports_get_reports_button.setOnAction(event -> {
            Date dateStart = Date.valueOf(sales_activities_my_reports_date_start_box.getValue());
            Date dateEnd = Date.valueOf(sales_activities_my_reports_date_end_box.getValue());

            try {
                if (dateStart.toString().isEmpty() || dateEnd.toString().isEmpty()) {
                    throw new NumberFormatException("Boxes are empty");
                }

                if (dateStart.after(dateEnd)) {
                    throw new NumberFormatException("Date is incorrect");
                }

                reports_check_reports_table_c_description.setCellValueFactory(new PropertyValueFactory<Report, String>("description"));
                reports_check_reports_table_c_dep.setCellValueFactory(new PropertyValueFactory<Report, String>("department"));
                reports_check_reports_table_c_emp.setCellValueFactory(new PropertyValueFactory<Report, String>("employee"));
                reports_check_reports_table_c_date.setCellValueFactory(new PropertyValueFactory<Report, Date>("date"));


                reports_check_reports_table.setItems(SalesManagerDao.getReports(dateStart, dateEnd));

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Check standards
        //--------------------------------------------------------------------------------------------------------------
        sales_activities_check_standards_get_button.setOnAction(event -> {
            try {
                sales_activities_check_standards_table_c_standard.setCellValueFactory(new PropertyValueFactory<Standard, String>("standard"));
                sales_activities_check_standards_table_c_role.setCellValueFactory(new PropertyValueFactory<Standard, String>("role"));
                sales_activities_check_standards_table_c_date_start.setCellValueFactory(new PropertyValueFactory<Standard, Date>("start"));
                sales_activities_check_standards_table_c_date_end.setCellValueFactory(new PropertyValueFactory<Standard, Date>("end"));

                sales_activities_check_standards_table.setItems(SalesManagerDao.getStandards());

            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------
        //New report
        //--------------------------------------------------------------------------------------------------------------
        sales_activities_new_report_get_list_button.setOnAction(event -> {
            try {
                sales_activities_new_report_list.setItems(SalesManagerDao.getDepartments(SalesManagerDao.getEmployeeId(App.userId)));
            } catch (DepartmentException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find departments");
                e.printStackTrace();
            } catch (EmployeeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find employee");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });

        sales_activities_new_report_ok_button.setOnAction(event -> {
            String description = sales_activities_new_report_description.getText();
            String department = sales_activities_new_report_list.getValue().toString();
            Date date = Date.valueOf(sales_activities_new_report_date.getValue());

            try {
                if (department.isEmpty() || description.isEmpty() || date.toString().isEmpty()) {
                    throw new NumberFormatException("Boxes are empty");
                }
                int depEmpId = SalesManagerDao.getDepartmentEmployeesId(
                        SalesManagerDao.getEmployeeId(App.userId), SalesManagerDao.getDepartmentId(department));

                SalesManagerDao.addReport(description, depEmpId, date);

                App.setAlert("SuccessWindow", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty");
                e.printStackTrace();
            } catch (DepartmentsEmployeesException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find department employees");
                e.printStackTrace();
            } catch (EmployeeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find employee");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }

        });
        //--------------------------------------------------------------------------------------------------------------
        //Orders
        //--------------------------------------------------------------------------------------------------------------
        sales_activities_orders_table_update_button.setOnAction(event -> {
            Date dateStart = Date.valueOf(sales_activities_orders_date_start_box.getValue());
            Date dateEnd = Date.valueOf(sales_activities_orders_date_end_box.getValue());

            try {
                if (dateStart.toString().isEmpty() || dateEnd.toString().isEmpty()) {
                    throw new NumberFormatException("Boxes are empty");
                }

                if (dateStart.after(dateEnd)) {
                    throw new NumberFormatException("Date is incorrect");
                }

                sales_activities_orders_table_c_login.setCellValueFactory(new PropertyValueFactory<Order, String>("login"));
                sales_activities_orders_table_c_date.setCellValueFactory(new PropertyValueFactory<Order, Date>("date"));
                sales_activities_orders_table_c_product.setCellValueFactory(new PropertyValueFactory<Order, String>("productName"));
                sales_activities_orders_table_c_quantity.setCellValueFactory(new PropertyValueFactory<Order, Integer>("quantity"));
                sales_activities_orders_table_c_total_amount.setCellValueFactory(new PropertyValueFactory<Order, Double>("totalAmount"));
                sales_activities_orders_table_c_status.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));

                sales_activities_orders_table.setItems(SalesManagerDao.getOrders());

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            } catch (DepartmentException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        //Assortment
        //--------------------------------------------------------------------------------------------------------------
        //Get products
        //--------------------------------------------------------------------------------------------------------------
        assortment_products_update_button.setOnAction(event -> {
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
        //--------------------------------------------------------------------------------------------------------------
        //Get product
        //--------------------------------------------------------------------------------------------------------------
        assortment_products_get_more_information_button.setOnAction(event -> {
            try {
                Product product = assortment_products_table.getSelectionModel().getSelectedItem();

                if (product == null) {
                    throw new ProductException("You should choose a product");
                }

                FullInformationAboutProductController.setProduct(product);

                App.setScene("Full information", getClass().getClassLoader()
                        .getResource("FullInformationAboutProductWindow.fxml"));
            } catch (ProductException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Add, delete, get characteristic
        //--------------------------------------------------------------------------------------------------------------
        //Add characteristic
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_characteristic_add_button.setOnAction(event -> {
            String name = assortment_new_characteristic_name_box.getText();
            String description = assortment_new_characteristic_description_box.getText();

            try {
                if (name.isEmpty() || description.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.addCharacteristic(name, description);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty or contains invalid information");
                e.printStackTrace();
            } catch (CharacteristicException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Characteristic already exist");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Delete characteristic
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_characteristic_delete_button.setOnAction(event -> {
            String name = assortment_new_characteristic_name_box.getText();
            String description = assortment_new_characteristic_description_box.getText();

            try {
                if (name.isEmpty() || description.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.deleteCharacteristic(name, description);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty or contains invalid information");
                e.printStackTrace();
            } catch (CharacteristicException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Characteristic not found");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Get characteristics
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_characteristic_get_characteristics_button.setOnAction(event -> {
            App.setScene("Characteristics", getClass().getClassLoader()
                    .getResource("CharacteristicsTableWindow.fxml"));
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        //Add, delete, get product detail
        //--------------------------------------------------------------------------------------------------------------
        //Add
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_product_detail_add_button.setOnAction(event -> {
            String productName = assortment_new_product_detail_product_name_box.getText();
            String characteristicName = assortment_new_product_detail_characteristic_name_box.getText();
            String description = assortment_new_product_detail_description_box.getText();

            try {
                if (productName.isEmpty() || characteristicName.isEmpty() || description.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.addProductDetail(productName, characteristicName, description);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty or contains invalid information");
                e.printStackTrace();
            } catch (ProductDetailException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Detail already exist");
                e.printStackTrace();
            } catch (ProductException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Product not found");
                e.printStackTrace();
            } catch (CharacteristicException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Characteristic not found");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Delete
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_product_detail_delete_button.setOnAction(event -> {
            String productName = assortment_new_product_detail_product_name_box.getText();
            String characteristicName = assortment_new_product_detail_characteristic_name_box.getText();
            String description = assortment_new_product_detail_description_box.getText();

            try {
                if (productName.isEmpty() || characteristicName.isEmpty() || description.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.deleteProductDetail(productName, characteristicName, description);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty or contains invalid information");
                e.printStackTrace();
            } catch (ProductDetailException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find product detail");
                e.printStackTrace();
            } catch (ProductException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Product not found");
                e.printStackTrace();
            } catch (CharacteristicException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Characteristic not found");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Get
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_product_detail_get_details_button.setOnAction(event -> {
            App.setScene("Product details", getClass().getClassLoader()
                    .getResource("ProductDetailWindow.fxml"));
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        //Add, delete, get type
        //--------------------------------------------------------------------------------------------------------------
        //Add
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_type_add_button.setOnAction(event -> {
            String name = assortment_new_type_name_box.getText();

            try {
                if (name.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.addType(name);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty or contains invalid information");
                e.printStackTrace();
            } catch (TypeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Type already exist");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Delete
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_type_delete_button.setOnAction(event -> {
            String name = assortment_new_type_name_box.getText();

            try {
                if (name.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.deleteType(name);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty or contains invalid information");
                e.printStackTrace();
            } catch (TypeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find type");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Get
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_type_get_types_button.setOnAction(event -> {
            App.setScene("Types", getClass().getClassLoader()
                    .getResource("TypesTableWindow.fxml"));
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        //Add, delete, get subtype
        //--------------------------------------------------------------------------------------------------------------
        //Add
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_subtype_add_button.setOnAction(event -> {
            String typeName = assortment_new_subtype_type_name_box.getText();
            String subtypeName = assortment_new_subtype_name_box.getText();

            try {
                if (typeName.isEmpty() || subtypeName.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.addSubtype(typeName, subtypeName);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty or contains invalid information");
                e.printStackTrace();
            } catch (SubtypeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Subtype already exist");
                e.printStackTrace();
            } catch (TypeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find type");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Delete
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_subtype_delete_button.setOnAction(event -> {
            String typeName = assortment_new_subtype_type_name_box.getText();
            String subtypeName = assortment_new_subtype_name_box.getText();

            try {
                if (typeName.isEmpty() || subtypeName.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.deleteSubtype(typeName, subtypeName);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty or contains invalid information");
                e.printStackTrace();
            } catch (SubtypeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find subtype");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Get
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_subtype_get_subtypes_button.setOnAction(event -> {
            App.setScene("Subtypes", getClass().getClassLoader()
                    .getResource("SubtypeTableWindow.fxml"));
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        //Add, delete, update product
        //--------------------------------------------------------------------------------------------------------------
        //Add
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_product_get_types_button.setOnAction(event -> {
            try {
                assortment_new_product_type_name_box.setItems(DirectorDao.getTypes());
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });

        assortment_new_product_get_subtypes_button.setOnAction(event -> {
            try {
                if (assortment_new_product_type_name_box.getValue() == null) {
                    assortment_new_product_subtype_name_box.setItems(DirectorDao.getSubtypesAll());
                } else {
                    assortment_new_product_subtype_name_box.setItems(DirectorDao.getSubtypesByType(assortment_new_product_type_name_box.getValue().toString()));
                }
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });

        assortment_new_product_add_button.setOnAction(event -> {
            String name = assortment_new_product_name_box.getText();
            String subtype = assortment_new_product_subtype_name_box.getValue();
            String price = assortment_new_product_price_box.getText();
            String quantity = assortment_new_product_quantity_box.getText();
            String description = assortment_new_product_description_box.getText();

            try {
                if (name.isEmpty() || subtype == null  || price.isEmpty() || quantity.isEmpty() || description.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.addProduct(name, subtype, price, quantity, description);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            } catch (ProductException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Product already exist");
                e.printStackTrace();
            } catch (TypeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find type");
                e.printStackTrace();
                e.printStackTrace();
            } catch (SubtypeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find subtype");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }

        });
        //--------------------------------------------------------------------------------------------------------------
        //Delete
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_product_delete_button.setOnAction(event -> {
            String name = assortment_new_product_name_box.getText();

            try {
                if (name.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.deleteProduct(name);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty or contains invalid information");
                e.printStackTrace();
            } catch (ProductException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find product");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Update
        //--------------------------------------------------------------------------------------------------------------
        assortment_new_product_update_button.setOnAction(event -> {
            String name = assortment_new_product_name_box.getText();
            String subtype = assortment_new_product_subtype_name_box.getValue();
            String price = assortment_new_product_price_box.getText();
            String quantity = assortment_new_product_quantity_box.getText();
            String description = assortment_new_product_description_box.getText();

            try {
                if (name.isEmpty() || subtype == null || price.isEmpty() || quantity.isEmpty() || description.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.updateProduct(name, subtype, price, quantity, description);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty or contains invalid information");
                e.printStackTrace();
            } catch (ProductException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find product");
                e.printStackTrace();
            } catch (TypeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find type");
                e.printStackTrace();
                e.printStackTrace();
            } catch (SubtypeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find subtype");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------
    }
}

