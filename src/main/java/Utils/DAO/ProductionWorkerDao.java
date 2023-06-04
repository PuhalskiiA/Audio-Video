package Utils.DAO;

import Utils.Exceptions.DepartmentException;
import Utils.Exceptions.DepartmentsEmployeesException;
import Utils.Exceptions.EmployeeException;
import Utils.Exceptions.ProductException;
import Utils.LayerOfTypes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.puhalskii.coursebd.App;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductionWorkerDao {
    public static ObservableList<Report> getReports(Date dateStart, Date dateEnd) throws SQLException, NumberFormatException {
        if (dateStart.after(dateEnd)) {
            throw new NumberFormatException("Date is incorrect");
        }

        ObservableList<Report> listOfReports = FXCollections.observableArrayList();

        String request = "SELECT \"Reports\".\"Description\" AS description, \"Reports\".\"TheDate\" AS date, " +
                "\"Department\".\"Name\" AS dName, \"Users\".\"Login\" AS login " +
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
                    resultSet.getString("description"),
                    resultSet.getString("dName"),
                    resultSet.getString("login"),
                    resultSet.getDate("date")
            ));
        }

        if (listOfReports.isEmpty()) {
            return null;
        } else {
            return listOfReports;
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

    //Add produced product
    //------------------------------------------------------------------------------------------------------------------
    public static void addProducedProduct(String name, String produced) throws SQLException, ProductException {
        String request = "UPDATE \"Product\" SET \"Quantity\" = ? WHERE \"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setDouble(1, ProductionWorkerDao.getQuantity(name, produced));
        preparedStatement.setString(2, name);

        preparedStatement.executeUpdate();
    }

    public static void addProduction(String name, String produced, String department, Date date) throws SQLException, ProductException, EmployeeException, DepartmentsEmployeesException {
        if (!ProductionWorkerDao.checkProduct(name)) {
            throw new ProductException("Can't find product");
        }

        int productId = ProductionWorkerDao.getProductId(name);
        int iProduced;

        try {
            iProduced = Integer.parseInt(produced);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid value of produced. Must be integer");
        }

        int depEmpId = ProductionWorkerDao.getDepartmentEmployeesId(ProductionWorkerDao.getEmployeeId(App.userId),
                ProductionWorkerDao.getDepartmentId(department));

        ProductionWorkerDao.addProducedProduct(name, produced);

        String request = "INSERT INTO \"Production\" (\"Product\", \"Produced\", \"DepartmentEmployees\", \"TheDate\") VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, productId);
        preparedStatement.setInt(2, iProduced);
        preparedStatement.setInt(3, depEmpId);
        preparedStatement.setDate(4, date);

        preparedStatement.executeUpdate();
    }

    public static int getProductId(String productName) throws SQLException, ProductException {
        String request = "SELECT \"id\" FROM \"Product\" WHERE \"Product\".\"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, productName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            throw new ProductException("Can't find product");
        }
    }

    public static int getQuantity(String name, String produced) throws SQLException, ProductException {
        int count;

        try {
            count = Integer.parseInt(produced);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid value of produced. Must be integer");
        }

        if (count <= 0) {
            throw new ProductException("Produced products can't be less than one");
        }

        String request = "SELECT \"Quantity\" FROM \"Product\" WHERE \"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return count + resultSet.getInt("Quantity");
        } else {
            throw new ProductException("Product not found");
        }
    }

    public static Boolean checkProduct(String name) throws SQLException {
        String request = "SELECT * FROM \"Product\" WHERE \"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }
    //------------------------------------------------------------------------------------------------------------------

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
    //------------------------------------------------------------------------------------------------------------------
}
