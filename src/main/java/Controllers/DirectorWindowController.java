package Controllers;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;

import Utils.DAO.AccountDao;
import Utils.DAO.DirectorDao;
import Utils.Exceptions.*;
import Utils.LayerOfTypes.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import ru.puhalskii.coursebd.App;

public class DirectorWindowController {

    @FXML
    private AnchorPane assortment_characteristic_description_box;

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
    private Button assortment_new_product_detail_get_details_button;

    @FXML
    private TextField assortment_new_product_detail_product_name_box;

    @FXML
    private TextField assortment_new_product_name_box;

    @FXML
    private TextField assortment_new_product_price_box;

    @FXML
    private TextField assortment_new_product_quantity_box;

    @FXML
    private ComboBox<String> assortment_new_product_subtype_name_box;

    @FXML
    private Button assortment_new_product_get_subtypes_button;

    @FXML
    private Tab assortment_new_product_tab;

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
    private AnchorPane e_h_middle_name_box;

    @FXML
    private Button employee_adding_emp_to_dep_add_button;

    @FXML
    private Button employee_adding_emp_to_dep_delete_button;

    @FXML
    private ComboBox<Department> employee_adding_emp_to_dep_dep_name_box;

    @FXML
    private Button employee_adding_emp_to_dep_dep_names_button;

    @FXML
    private TextField employee_adding_emp_to_dep_login_box;

    @FXML
    private Tab employee_adding_emp_to_dep_tab;

    @FXML
    private TextField employee_dismissal_login_box;

    @FXML
    private TextField employee_dismissal_name_box;

    @FXML
    private Button employee_dismissal_ok_button;

    @FXML
    private TextField employee_dismissal_surname_box;

    @FXML
    private Tab employee_dismissal_tab;

    @FXML
    private TextField employee_hiring_birthday_box;

    @FXML
    private TextField employee_hiring_hiring_date_box;

    @FXML
    private TextField employee_hiring_middle_name_box;

    @FXML
    private TextField employee_hiring_name_box;

    @FXML
    private Button employee_hiring_ok_button;

    @FXML
    private ComboBox<Role> employee_hiring_role_box;

    @FXML
    private TextField employee_hiring_salary_box;

    @FXML
    private TextField employee_hiring_surname_box;

    @FXML
    private Tab employee_hiring_tab;

    @FXML
    private TextField employee_hiring_telephone_number_box;

    @FXML
    private TextField employee_hiring_user_login_box;

    @FXML
    private Button employee_new_department_add_button;

    @FXML
    private Button employee_new_department_delete_button;

    @FXML
    private TextField employee_new_department_name_box;

    @FXML
    private ComboBox<Role> employee_new_department_role_box;

    @FXML
    private Button employee_new_department_get_roles_button;

    @FXML
    private Tab employee_new_department_tab;

    @FXML
    private TextField employee_personal_info_name_box;

    @FXML
    private Button employee_personal_info_ok_button;

    @FXML
    private TextField employee_personal_info_surname_box;

    @FXML
    private Tab employee_personal_info_tab;

    @FXML
    private TextField employee_salary_login_box;

    @FXML
    private TextField employee_salary_new_salary_box;

    @FXML
    private Button employee_salary_ok_button;

    @FXML
    private Tab employee_salary_tab;

    @FXML
    private Tab employees_tab;

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
    private DatePicker reports_check_reports_date_end_box;

    @FXML
    private DatePicker reports_check_reports_date_start_box;

    @FXML
    private Tab reports_check_reports_tab;

    @FXML
    private TableView<Report> reports_check_reports_table;

    @FXML
    private TableColumn<Report, Date> reports_check_reports_table_c_date;

    @FXML
    private TableColumn<?, ?> reports_check_reports_table_c_dep_emp;

    @FXML
    private TableColumn<Report, String> reports_check_reports_table_c_description;

    @FXML
    private TableColumn<?, ?> reports_check_reports_table_c_standard;

    @FXML
    private Button reports_check_standards_get_standards_button;

    @FXML
    private Tab reports_check_standards_tab;

    @FXML
    private TableView<Standard> reports_check_standards_table;

    @FXML
    private TableColumn<Standard, Date> reports_check_standards_table_c_date_end;

    @FXML
    private TableColumn<Standard, Date> reports_check_standards_table_c_date_start;

    @FXML
    private TableColumn<Standard, String> reports_check_standards_table_c_role;

    @FXML
    private TableColumn<Standard, String> reports_check_standards_table_c_standard;

    @FXML
    private DatePicker reports_new_standard_date_end_box;

    @FXML
    private DatePicker reports_new_standard_date_start_box;

    @FXML
    private TextField reports_new_standard_name_box;

    @FXML
    private Button reports_new_standard_ok_button;

    @FXML
    private ComboBox<Role> reports_new_standard_role_box;

    @FXML
    private Button reports_new_standard_get_roles_button;

    @FXML
    private Tab reports_new_standard_tab;

    @FXML
    private Tab reports_tab;

    @FXML
    private TextField assortment_new_product_detail_description_box;

    @FXML
    private Button assortment_new_product_update_button;

    @FXML
    private Button employee_personal_info_get_employees_button;

    @FXML
    private Button reports_check_reports_get_reports_button;

    @FXML
    private TableColumn<Report, String> reports_check_reports_table_c_dep;

    @FXML
    private TableColumn<Report, String> reports_check_reports_table_c_emp;

    @FXML
    private Button employee_hiring_role_get_button;

    @FXML
    private ComboBox<Type> assortment_new_product_type_name_box;

    @FXML
    private Button assortment_new_product_get_types_button;

    @FXML
    private Button assortment_products_get_more_information_button;

    @FXML
    void initialize() throws SQLException {
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

        //For Employee window
        //--------------------------------------------------------------------------------------------------------------
        //Check personal info
        //--------------------------------------------------------------------------------------------------------------
        employee_personal_info_ok_button.setOnAction(event -> {
            String name = employee_personal_info_name_box.getText();
            String surname = employee_personal_info_surname_box.getText();

            try {
                if (name.isEmpty() || surname.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty");
                }

                if (!DirectorDao.checkForEmployee(name, surname)) {
                    throw new EmployeeException("Employee not found");
                }

                DirectorDao.getEmployees(name, surname);

                App.setScene("Employees", getClass().getClassLoader()
                        .getResource("AdditionWindowEmployeeInDirector.fxml"));

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty");
                e.printStackTrace();
            }  catch (UserException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "user not found");
                e.printStackTrace();
            } catch (EmployeeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Employee not found");
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------

        //Check all employees
        //--------------------------------------------------------------------------------------------------------------
        employee_personal_info_get_employees_button.setOnAction(event -> {
            try {
                DirectorDao.getAllEmployees();

                App.setScene("Employees", getClass().getClassLoader()
                        .getResource("AdditionWindowEmployeeInDirector.fxml"));

            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------

        //Hiring
        //--------------------------------------------------------------------------------------------------------------
        employee_hiring_role_get_button.setOnAction(event -> {
            try {
                employee_hiring_role_box.setItems(DirectorDao.getRoles());
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });

        employee_hiring_ok_button.setOnAction(event -> {
            try {
                String name = employee_hiring_name_box.getText();
                String surname = employee_hiring_surname_box.getText();
                String middleName = employee_hiring_middle_name_box.getText();
                String telephoneNumber = employee_hiring_telephone_number_box.getText();
                String birthday = employee_hiring_birthday_box.getText();
                String hiring = employee_hiring_hiring_date_box.getText();
                String salary = employee_hiring_salary_box.getText();
                String login = employee_hiring_user_login_box.getText();
                String role = employee_hiring_role_box.getValue().toString();

                if (name.isEmpty() || surname.isEmpty() || middleName.isEmpty() || telephoneNumber.isEmpty()
                    || birthday.isEmpty() || hiring.isEmpty() || salary.isEmpty() || login.isEmpty() || role.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty");
                }

                DirectorDao.updateRoleOnUser(DirectorDao.getUserId(login), role);

                DirectorDao.addEmployee(name, surname, middleName, telephoneNumber, birthday, hiring, salary, login);

                App.setAlert("SuccessWindow", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            } catch (UserException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "User not found");
                e.printStackTrace();
            } catch (RoleException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Role not found");
                e.printStackTrace();
            } catch (RuntimeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "An employee with this username already exists");
                e.printStackTrace();
            } catch (EmployeeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Employee already exist");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again. Check your data");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------

        //Dismissal
        //--------------------------------------------------------------------------------------------------------------
        employee_dismissal_ok_button.setOnAction(event -> {
            String login = employee_dismissal_login_box.getText();

            try {
                if (login.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty");
                }

                DirectorDao.deleteEmployee(login);

                App.setAlert("SuccessWindow", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty");
                e.printStackTrace();
            } catch (EmployeeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Employee not found");
                e.printStackTrace();
            } catch (UserException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "User not found");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again. Check your data");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Change salary
        //--------------------------------------------------------------------------------------------------------------
        employee_salary_ok_button.setOnAction(event -> {
            String login = employee_salary_login_box.getText();
            String newSalary = employee_salary_new_salary_box.getText();

            try {
                if (login.isEmpty() || newSalary.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty");
                }

                DirectorDao.updateSalary(newSalary, login);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            } catch (UserException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "User not found");
                e.printStackTrace();
            } catch (EmployeeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Employee not found");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Add emp-to-dep relation
        //--------------------------------------------------------------------------------------------------------------
        employee_adding_emp_to_dep_dep_names_button.setOnAction(event -> {
            try {
                employee_adding_emp_to_dep_dep_name_box.setItems(DirectorDao.getDepartments());
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });

        employee_adding_emp_to_dep_add_button.setOnAction(event -> {
            String login = employee_adding_emp_to_dep_login_box.getText();
            String department = employee_adding_emp_to_dep_dep_name_box.getValue().toString();

            try {
                if (login.isEmpty() || department.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                if (DirectorDao.checkEmpToDep(login, department)) {
                    throw new DepartmentsEmployeesException("This connection is already exist");
                }

                DirectorDao.addEmpToDep(login, department);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty");
                e.printStackTrace();
            } catch (UserException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            } catch (DepartmentException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Department not found");
                e.printStackTrace();
            } catch (EmployeeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Employee not found");
                e.printStackTrace();
            } catch (DepartmentsEmployeesException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "This connection is already exist");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Delete emp-to-dep relation
        //--------------------------------------------------------------------------------------------------------------
        employee_adding_emp_to_dep_delete_button.setOnAction(event -> {
            String login = employee_adding_emp_to_dep_login_box.getText();
            String department = employee_adding_emp_to_dep_dep_name_box.getValue().toString();

            try {
                if (login.isEmpty() || department.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                if (!DirectorDao.checkEmpToDep(login, department)) {
                    throw new DepartmentsEmployeesException("This connection does not exist");
                }

                DirectorDao.deleteEmpToDep(login, department);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");
            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty");
                e.printStackTrace();
            } catch (UserException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "User not found");
                e.printStackTrace();
            } catch (DepartmentException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Department not found");
                e.printStackTrace();
            } catch (EmployeeException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Employee not found");
                e.printStackTrace();
            } catch (DepartmentsEmployeesException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "This connection does not exist");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Add department
        //--------------------------------------------------------------------------------------------------------------
        employee_new_department_get_roles_button.setOnAction(event -> {
            try {
                employee_new_department_role_box.setItems(DirectorDao.getRoles());
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });

        employee_new_department_add_button.setOnAction(event -> {
            String name = employee_new_department_name_box.getText();
            String role = employee_new_department_role_box.getValue().toString();

            try {
                if (name.isEmpty() || role.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.addNewDepartment(name, role);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty");
                e.printStackTrace();
            } catch (DepartmentException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Department already exists");
                e.printStackTrace();
            } catch (RoleException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find role");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //Delete department
        //--------------------------------------------------------------------------------------------------------------
        employee_new_department_delete_button.setOnAction(event -> {
            String name = employee_new_department_name_box.getText();
            String role = employee_new_department_role_box.getValue().toString();

            try {
                if (name.isEmpty() || role.isEmpty()) {
                    throw new NumberFormatException("Boxes are empty or contains invalid information");
                }

                DirectorDao.deleteDepartment(name, role);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");
            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Boxes are empty");
                e.printStackTrace();
            } catch (DepartmentException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find department");
                e.printStackTrace();
            } catch (RoleException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find role");
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------

        //For assortment window
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
                if (name.isEmpty() || subtype == null || price.isEmpty() || quantity.isEmpty() || description.isEmpty()) {
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
            String type = assortment_new_product_type_name_box.getValue().toString();
            String subtype = assortment_new_product_subtype_name_box.getValue();
            String price = assortment_new_product_price_box.getText();
            String quantity = assortment_new_product_quantity_box.getText();
            String description = assortment_new_product_description_box.getText();

            try {
                if (name.isEmpty() || subtype == null  || price.isEmpty() || quantity.isEmpty() || description.isEmpty()) {
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

        //Reports
        //--------------------------------------------------------------------------------------------------------------
        //Check reports
        //--------------------------------------------------------------------------------------------------------------
        reports_check_reports_get_reports_button.setOnAction(event -> {
            Date dateStart = Date.valueOf(reports_check_reports_date_start_box.getValue());
            Date dateEnd = Date.valueOf(reports_check_reports_date_end_box.getValue());

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


                reports_check_reports_table.setItems(DirectorDao.getReports(dateStart, dateEnd));

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------
        //Check standards
        //--------------------------------------------------------------------------------------------------------------
        reports_check_standards_get_standards_button.setOnAction(event -> {
            try {
                reports_check_standards_table_c_standard.setCellValueFactory(new PropertyValueFactory<Standard, String>("standard"));
                reports_check_standards_table_c_role.setCellValueFactory(new PropertyValueFactory<Standard, String>("role"));
                reports_check_standards_table_c_date_start.setCellValueFactory(new PropertyValueFactory<Standard, Date>("start"));
                reports_check_standards_table_c_date_end.setCellValueFactory(new PropertyValueFactory<Standard, Date>("end"));

                reports_check_standards_table.setItems(DirectorDao.getStandards());

            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        //New standard
        //--------------------------------------------------------------------------------------------------------------
        reports_new_standard_get_roles_button.setOnAction(event -> {
            try {
                reports_new_standard_role_box.setItems(DirectorDao.getRoles());
            } catch (SQLException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Something wrong, try again");
                e.printStackTrace();
            }
        });

        reports_new_standard_ok_button.setOnAction(event -> {
            try {
                String standard = reports_new_standard_name_box.getText();
                String role = reports_new_standard_role_box.getValue().toString();
                Date dateStart = Date.valueOf(reports_new_standard_date_start_box.getValue());
                Date dateEnd = Date.valueOf(reports_new_standard_date_end_box.getValue());

                if (standard.isEmpty() || role == null || dateStart.toString().isEmpty() || dateEnd.toString().isEmpty()) {
                    throw new NumberFormatException("Boxes are empty");
                }

                if (dateStart.after(dateEnd)) {
                    throw new NumberFormatException("Date is incorrect");
                }

                DirectorDao.addStandard(standard, role, dateStart, dateEnd);

                App.setAlert("Success", Alert.AlertType.INFORMATION, "Success");

            } catch (NumberFormatException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, e.getMessage());
                e.printStackTrace();
            } catch (RoleException e) {
                App.setAlert("Error", Alert.AlertType.INFORMATION, "Can't find role");
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