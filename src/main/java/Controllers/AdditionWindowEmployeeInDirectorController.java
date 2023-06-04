package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Utils.DAO.DirectorDao;
import Utils.LayerOfTypes.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.puhalskii.coursebd.App;

public class AdditionWindowEmployeeInDirectorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Employee, String> additional_window_Login_column;

    @FXML
    private TableColumn<Employee, String> additional_window_birthday_column;

    @FXML
    private TableView<Employee> additional_window_employee_director_table;

    @FXML
    private TableColumn<Employee, String> additional_window_hiring_date_column;

    @FXML
    private TableColumn<Employee, String> additional_window_middle_name_column;

    @FXML
    private TableColumn<Employee, String> additional_window_name_column;

    @FXML
    private TableColumn<Employee, Double> additional_window_salary_column;

    @FXML
    private TableColumn<Employee, String> additional_window_surname_column;

    @FXML
    private TableColumn<Employee, String> additional_window_telephone_column;

    @FXML
    private Button additional_window_employee_director_back_button;

    @FXML
    void initialize() {
        additional_window_employee_director_back_button.setOnAction(event -> {
            App.setScene("Director", getClass().getClassLoader().getResource("DirectorWindow.fxml"));
        });

        additional_window_name_column.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        additional_window_surname_column.setCellValueFactory(new PropertyValueFactory<Employee, String>("surname"));
        additional_window_middle_name_column.setCellValueFactory(new PropertyValueFactory<Employee, String>("middleName"));
        additional_window_Login_column.setCellValueFactory(new PropertyValueFactory<Employee, String>("login"));
        additional_window_salary_column.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
        additional_window_telephone_column.setCellValueFactory(new PropertyValueFactory<Employee, String>("telephoneNumber"));
        additional_window_birthday_column.setCellValueFactory(new PropertyValueFactory<Employee, String>("birthday"));
        additional_window_hiring_date_column.setCellValueFactory(new PropertyValueFactory<Employee, String>("hiringDate"));

        additional_window_employee_director_table.setItems(DirectorDao.getListOfEmployee());
    }

}

