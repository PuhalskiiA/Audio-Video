package Controllers;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;

import Utils.DAO.AccountDao;
import Utils.DAO.DirectorDao;
import Utils.DAO.ProductionWorkerDao;
import Utils.Exceptions.*;
import Utils.LayerOfTypes.Department;
import Utils.LayerOfTypes.Product;
import Utils.LayerOfTypes.Report;
import Utils.LayerOfTypes.Standard;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.puhalskii.coursebd.App;

public class ProductionWorkerWindowController {
    @FXML
    private Button assortment_products_get_list_of_products_button;

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
    private Button my_account_personal_info_get_button;

    @FXML
    private Tab my_account_personal_info_tab;

    @FXML
    private TextArea my_account_personal_info_text_area;

    @FXML
    private Tab my_account_tab;

    @FXML
    private DatePicker production_activities_add_produced_product_date_box;

    @FXML
    private ComboBox<Department> production_activities_add_produced_product_departments_list;

    @FXML
    private Button production_activities_add_produced_product_get_departments_button;

    @FXML
    private TextField production_activities_add_produced_product_name_box;

    @FXML
    private Button production_activities_add_produced_product_ok_button;

    @FXML
    private TextField production_activities_add_produced_product_produced_box;

    @FXML
    private Tab production_activities_check_my_reports_tab;

    @FXML
    private Tab production_activities_check_standarts_tab;

    @FXML
    private TableView<Standard> production_activities_check_standarts_table;

    @FXML
    private TableColumn<Standard, Date> production_activities_check_standarts_table_c_date_end;

    @FXML
    private TableColumn<Standard, Date> production_activities_check_standarts_table_c_date_start;

    @FXML
    private TableColumn<Standard, String> production_activities_check_standarts_table_c_role;

    @FXML
    private TableColumn<Standard, String> production_activities_check_standarts_table_c_standard;

    @FXML
    private Button production_activities_check_standarts_update_button;

    @FXML
    private DatePicker production_activities_my_reports_date_end_box;

    @FXML
    private DatePicker production_activities_my_reports_date_start_box;

    @FXML
    private Button production_activities_my_reports_get_reports_button;

    @FXML
    private DatePicker production_activities_new_report_date;

    @FXML
    private TextField production_activities_new_report_description;

    @FXML
    private Button production_activities_new_report_get_list_button;

    @FXML
    private ComboBox<Department> production_activities_new_report_list;

    @FXML
    private Button production_activities_new_report_ok_button;

    @FXML
    private Tab production_activities_new_report_tab1;

    @FXML
    private Tab production_activities_tab;

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
    private Tab sales_activities_new_report_tab;

    @FXML
    private Button assortment_products_update_button;

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
        //Sales activities
        //--------------------------------------------------------------------------------------------------------------
        //Check reports
        //--------------------------------------------------------------------------------------------------------------
        production_activities_my_reports_get_reports_button.setOnAction(event -> {
            Date dateStart = Date.valueOf(production_activities_my_reports_date_start_box.getValue());
            Date dateEnd = Date.valueOf(production_activities_my_reports_date_end_box.getValue());

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


                reports_check_reports_table.setItems(ProductionWorkerDao.getReports(dateStart, dateEnd));

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
        production_activities_check_standarts_update_button.setOnAction(event -> {
            try {
                production_activities_check_standarts_table_c_standard.setCellValueFactory(new PropertyValueFactory<Standard, String>("standard"));
                production_activities_check_standarts_table_c_role.setCellValueFactory(new PropertyValueFactory<Standard, String>("role"));
                production_activities_check_standarts_table_c_date_start.setCellValueFactory(new PropertyValueFactory<Standard, Date>("start"));
                production_activities_check_standarts_table_c_date_end.setCellValueFactory(new PropertyValueFactory<Standard, Date>("end"));

                production_activities_check_standarts_table.setItems(ProductionWorkerDao.getStandards());

            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------
        //New report
        //--------------------------------------------------------------------------------------------------------------
        production_activities_new_report_get_list_button.setOnAction(event -> {
            try {
                production_activities_new_report_list.setItems(ProductionWorkerDao.getDepartments(ProductionWorkerDao.getEmployeeId(App.userId)));
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

        production_activities_new_report_ok_button.setOnAction(event -> {
            String description = production_activities_new_report_description.getText();
            String department = production_activities_new_report_list.getValue().toString();
            Date date = Date.valueOf(production_activities_new_report_date.getValue());

            try {
                if (department.isEmpty() || description.isEmpty() || date.toString().isEmpty()) {
                    throw new NumberFormatException("Boxes are empty");
                }
                int depEmpId = ProductionWorkerDao.getDepartmentEmployeesId(
                        ProductionWorkerDao.getEmployeeId(App.userId), ProductionWorkerDao.getDepartmentId(department));

                ProductionWorkerDao.addReport(description, depEmpId, date);

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

        //Add produced product
        //--------------------------------------------------------------------------------------------------------------
        production_activities_add_produced_product_get_departments_button.setOnAction(event -> {
            try {
                production_activities_add_produced_product_departments_list.setItems(ProductionWorkerDao.getDepartments(ProductionWorkerDao.getEmployeeId(App.userId)));
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

        production_activities_add_produced_product_ok_button.setOnAction(event -> {
            String product = production_activities_add_produced_product_name_box.getText();
            String produced = production_activities_add_produced_product_produced_box.getText();
            String department = production_activities_add_produced_product_departments_list.getValue().toString();
            Date date = Date.valueOf(production_activities_add_produced_product_date_box.getValue());

            try {
                if (produced.isEmpty() || product.isEmpty() || department.isEmpty() || date.toString().isEmpty()) {
                    throw new NumberFormatException("Boxes are empty");
                }

                ProductionWorkerDao.addProduction(product, produced, department, date);

                App.setAlert("SuccessWindow", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            } catch (ProductException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Product not dound");
                e.printStackTrace();
            } catch (EmployeeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Employee not found");
                e.printStackTrace();
            } catch (DepartmentsEmployeesException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Department of employee not found");
                e.printStackTrace();
            }catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------

        //Get assortment
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

    }
}
