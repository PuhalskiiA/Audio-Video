package Utils.DAO;

import Utils.Exceptions.*;
import Utils.LayerOfTypes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.puhalskii.coursebd.App;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesManagerDao {
    public static ObservableList<Report> getReports(Date dateStart, Date dateEnd) throws SQLException, NumberFormatException {
        if (dateStart.after(dateEnd)) {
            throw new NumberFormatException("Date is incorrect");
        }

        ObservableList<Report> listOfReports = FXCollections.observableArrayList();

        String request = "SELECT \"Reports\".\"Description\", \"Reports\".\"TheDate\", \"Department\".\"Name\", \"Users\".\"Login\" " +
                "FROM \"Reports\" " +
                "INNER JOIN \"DepartmentsEmployees\" ON \"DepartmentsEmployees\".\"id\" = \"Reports\".\"DepartmentEmployees\" " +
                "INNER JOIN \"Employee\" ON \"Employee\".\"id\" = \"DepartmentsEmployees\".\"EmployeeId\" " +
                "INNER JOIN \"Users\" ON \"Users\".\"id\" = \"Employee\".\"UserId\" " +
                "INNER JOIN \"Role\" ON \"Role\".\"id\" = \"Users\".\"RoleId\" " +
                "INNER JOIN \"Department\" ON \"Department\".\"Role\" = \"Role\".\"id\" " +
                "WHERE \"Users\".\"id\" = ? AND \"Reports\".\"TheDate\" > ? AND \"Reports\".\"TheDate\" < ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, App.userId);
        preparedStatement.setDate(2, dateStart);
        preparedStatement.setDate(3, dateEnd);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            listOfReports.add(new Report(
                    resultSet.getString("Description"),
                    resultSet.getString("Name"),
                    resultSet.getString("Login"),
                    resultSet.getDate("TheDate")
            ));
        }

        if (listOfReports.isEmpty()) {
            return null;
        } else {
            return listOfReports;
        }
    }

    public static ObservableList<Department> getEmployeeDepartments() throws SQLException {
        ObservableList<Department> departments = FXCollections.observableArrayList();

        String request = "SELECT \"Department\".\"Name\" " +
                "FROM \"Department\" " +
                "INNER JOIN \"Role\" ON \"Role\".\"id\" = \"Department\".\"Role\" " +
                "INNER JOIN \"Users\" ON \"Users\".\"RoleId\" = \"Role\".\"id\" " +
                "AND \"Users\".\"id\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, App.userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            departments.add(new Department(
                    resultSet.getString("Name")
            ));
        }

        if (departments.isEmpty()) {
            return null;
        } else {
            return departments;
        }
    }

    public static ObservableList<Standard> getStandards() throws SQLException {
        ObservableList<Standard> listOfStandards = FXCollections.observableArrayList();

        String request = "SELECT \"Standard\", \"Role\".\"Name\" AS role, \"DateStart\", \"DateEnd\" " +
                "FROM \"Standards\" " +
                "INNER JOIN \"Role\" ON \"Role\".\"id\" = \"Standards\".\"Role\" " +
                "INNER JOIN \"Users\" ON \"Users\".\"RoleId\" = \"Role\".\"id\" " +
                "AND \"Users\".\"id\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, App.userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            listOfStandards.add(new Standard(
                    resultSet.getString("role"),
                    resultSet.getString("Standard"),
                    resultSet.getDate("DateStart"),
                    resultSet.getDate("DateEnd")
            ));
        }

        if (listOfStandards.isEmpty()) {
            return null;
        } else {
            return listOfStandards;
        }
    }

    public static void addReport(String description, int depEmpId, Date date) throws SQLException {
        String request = "INSERT INTO \"Reports\" (\"Description\", \"DepartmentEmployees\", \"TheDate\") " +
                "VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, description);
        preparedStatement.setInt(2, depEmpId);
        preparedStatement.setDate(3, date);

        preparedStatement.executeUpdate();
    }

    public static int getDepartmentEmployeesId(int employeeId, int departmentId) throws SQLException, DepartmentsEmployeesException {
        String request = "SELECT \"DepartmentsEmployees\".\"id\" FROM \"DepartmentsEmployees\" " +
                "WHERE \"DepartmentsEmployees\".\"EmployeeId\" = ? AND \"DepartmentsEmployees\".\"DepartmentId\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, employeeId);
        preparedStatement.setInt(2, departmentId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            throw new DepartmentsEmployeesException("Can't find department for employee");
        }
    }

    public static ObservableList<Department> getDepartments(int employeeId) throws SQLException, DepartmentException {
        ObservableList<Department> listOfDepartments = FXCollections.observableArrayList();

        String request = "SELECT \"Department\".\"Name\" AS dName FROM \"Department\" " +
                "INNER JOIN \"DepartmentsEmployees\" ON \"DepartmentsEmployees\".\"DepartmentId\" = \"Department\".\"id\" " +
                "INNER JOIN \"Employee\" ON \"Employee\".\"id\" = \"DepartmentsEmployees\".\"EmployeeId\" " +
                "AND \"Employee\".\"id\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, employeeId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            listOfDepartments.add(new Department(
                    resultSet.getString("dName")
            ));
        }

        if (listOfDepartments.isEmpty()) {
            throw new DepartmentException("Can't find departments");
        } else {
            return listOfDepartments;
        }
    }

    public static int getEmployeeId(int userId) throws SQLException, EmployeeException {
        String request = "SELECT \"Employee\".\"id\" AS eId FROM \"Employee\" " +
                "INNER JOIN \"Users\" ON \"Users\".\"id\" = \"Employee\".\"UserId\" " +
                "AND \"Users\".\"id\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("eId");
        } else {
            throw new EmployeeException("Can't find employee");
        }
    }

    public static int getDepartmentId(String name) throws SQLException, EmployeeException {
        String request = "SELECT \"Department\".\"id\" AS dId FROM \"Department\" " +
                "WHERE \"Department\".\"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("dId");
        } else {
            throw new EmployeeException("Can't find department");
        }
    }

    public static ObservableList<Order> getOrders() throws SQLException, DepartmentException {
        ObservableList<Order> listOfOrders = FXCollections.observableArrayList();

        String request = "SELECT \"Users\".\"Login\" AS login, \"Product\".\"Name\" AS product, \"Order\".\"TheDate\" AS TheDate, \"Order\".\"TotalAmount\" AS amount, " +
                "\"OrderDetails\".\"Quantity\" AS quantity, \"Order\".\"Status\" " +
                "FROM \"Users\" " +
                "INNER JOIN \"Order\" ON \"Order\".\"UserId\" = \"Users\".\"id\"" +
                "INNER JOIN \"OrderDetails\" ON \"OrderDetails\".\"Order\" = \"Order\".\"id\"" +
                "INNER JOIN \"Product\" ON \"Product\".\"id\" = \"OrderDetails\".\"Product\"";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            listOfOrders.add(new Order(
                    resultSet.getString("login"),
                    resultSet.getDate("TheDate"),
                    resultSet.getString("product"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("amount"),
                    resultSet.getString("Status")
            ));
        }

        if (listOfOrders.isEmpty()) {
            throw new DepartmentException("Can't find orders");
        } else {
            return listOfOrders;
        }
    }

    //Get assortment
    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<Product> getAssortment() throws SQLException {
        ObservableList<Product> assortment = FXCollections.observableArrayList();

        String request = "SELECT \"Product\".\"Name\", \"Type\".\"Name\" AS \"Type\", \"Subtype\".\"Name\" AS \"Subtype\", \"Quantity\", \"Price\", \"Description\" " +
                "FROM \"Product\" " +
                "INNER JOIN \"Subtype\" ON \"Subtype\".id = \"Product\".\"Subtype\" " +
                "INNER JOIN \"Type\" ON \"Type\".id = \"Subtype\".\"TypeId\"";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            assortment.add(new Product(
                    resultSet.getString("Name"),
                    resultSet.getString("Type"),
                    resultSet.getString("Subtype"),
                    resultSet.getInt("Quantity"),
                    resultSet.getDouble("Price"),
                    resultSet.getString("Description"))
            );
        }

        if (assortment.isEmpty()) {
            return null;
        } else {
            return assortment;
        }
    }
    //--------------------------------------------------------------------------------------------------------------

    //Check product
    //--------------------------------------------------------------------------------------------------------------
    public static Boolean checkProduct(String name) throws SQLException {
        String request = "SELECT * FROM \"Product\" WHERE \"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }
    //--------------------------------------------------------------------------------------------------------------
}
