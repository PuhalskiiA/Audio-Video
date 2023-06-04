package Utils.DAO;

import Utils.Exceptions.*;
import Utils.LayerOfTypes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DirectorDao {
    //For director-to-employee check info
    //------------------------------------------------------------------------------------------------------------------
    private static ObservableList<Employee> listOfEmployee = FXCollections.observableArrayList();
    //------------------------------------------------------------------------------------------------------------------

    //For director-to-employee check info
    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<Employee> getEmployees(String name, String surname) throws SQLException, UserException {
        String request = "SELECT \"Salary\", \"Login\", \"MiddleName\", \"TelephoneNumber\", \"Birthday\", \"HiringDate\" " +
                "FROM \"Employee\" " +
                "INNER JOIN \"Users\" ON \"Users\".\"id\" = \"Employee\".\"UserId\"" +
                "WHERE \"Name\" = ? AND \"Surname\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);

        ResultSet resultSet = preparedStatement.executeQuery();

        listOfEmployee.clear();

        while (resultSet.next()) {
            listOfEmployee.add(new Employee(name, surname,
                    resultSet.getString("MiddleName"),
                    resultSet.getString("Login"),
                    resultSet.getDouble("Salary"),
                    resultSet.getString("TelephoneNumber"),
                    resultSet.getString("Birthday"),
                    resultSet.getString("HiringDate")));
        }

        if (listOfEmployee.isEmpty()) {
            return null;
        } else {
            return listOfEmployee;
        }
    }

    public static ObservableList<Employee> getListOfEmployee() {
        return listOfEmployee;
    }

    public static ObservableList<Employee> getAllEmployees() throws SQLException {
        String request = "SELECT \"Name\", \"Surname\", \"Salary\", \"Login\", \"MiddleName\", \"TelephoneNumber\", \"Birthday\", \"HiringDate\" " +
                "FROM \"Employee\" " +
                "INNER JOIN \"Users\" ON \"Users\".\"id\" = \"Employee\".\"UserId\"";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        ResultSet resultSet = preparedStatement.executeQuery();

        listOfEmployee.clear();

        while (resultSet.next()) {
            listOfEmployee.add(new Employee(
                    resultSet.getString("Name"),
                    resultSet.getString("Surname"),
                    resultSet.getString("MiddleName"),
                    resultSet.getString("Login"),
                    resultSet.getDouble("Salary"),
                    resultSet.getString("TelephoneNumber"),
                    resultSet.getString("Birthday"),
                    resultSet.getString("HiringDate")));
        }

        if (listOfEmployee.isEmpty()) {
            return null;
        } else {
            return listOfEmployee;
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    //For director-to-employee hiring
    //------------------------------------------------------------------------------------------------------------------
    public static void addEmployee(String name, String surname, String middleName, String telephoneNumber,
                                   String birthday, String hiringDate, String salary, String login) throws SQLException, UserException, EmployeeException {

        if (DirectorDao.checkForEmployee(DirectorDao.getUserId(login))) {
            throw new EmployeeException("Employee already exist");
        }

        Date dBirthday;
        Date dHiringDate;

        try {
            dBirthday = Date.valueOf(birthday);
            dHiringDate = Date.valueOf(hiringDate);
        } catch (IllegalArgumentException e) {
            throw new NumberFormatException("Date is incorrect");
        }

        double dSalary;

        try{
            dSalary = Double.parseDouble(salary);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid value of salary. Must be number");
        }

        String request = "INSERT INTO \"Employee\" (\"Name\", \"Surname\", \"MiddleName\", \"UserId\", \"Salary\", " +
                "\"TelephoneNumber\", \"Birthday\", \"HiringDate\") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, middleName);
        preparedStatement.setInt(4, DirectorDao.getUserId(login));
        preparedStatement.setDouble(5, dSalary);
        preparedStatement.setString(6, telephoneNumber);
        preparedStatement.setDate(7, dBirthday);
        preparedStatement.setDate(8, dHiringDate);

        preparedStatement.executeUpdate();
    }

    public static Boolean checkForEmployee(int userId) throws SQLException {
        String request = "SELECT \"Name\" FROM \"Employee\" WHERE \"UserId\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public static Boolean checkForEmployee(String name, String surname) throws SQLException {
        String request = "SELECT \"Name\" FROM \"Employee\" WHERE \"Name\" = ? AND \"Surname\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public static int getRoleId(String role) throws SQLException, RoleException {
        String request = "SELECT \"id\" FROM \"Role\" WHERE \"Name\" = ?";

        PreparedStatement preparedStatement1 = DaoFactory.getConnection().prepareStatement(request);
        preparedStatement1.setString(1, role);
        ResultSet resultSet = preparedStatement1.executeQuery();

        int roleId = 0;

        if (resultSet.next()) {
            roleId = resultSet.getInt("id");
        }

        if (roleId == 0) {
            throw new RoleException("Can't find role");
        }

        return roleId;
    }

    public static ObservableList<Role> getRoles() throws SQLException {
        ObservableList<Role> roles = FXCollections.observableArrayList();

        String request = "SELECT \"Name\" FROM \"Role\" WHERE \"Name\" != 'User'";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            roles.add(new Role(
                    resultSet.getString("Name")
            ));
        }

        if (roles.isEmpty()) {
            return null;
        } else {
            return roles;
        }
    }

    public static int getUserId(String userLogin) throws SQLException, UserException {
        String request = "SELECT \"id\" FROM \"Users\" WHERE \"Login\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, userLogin);

        ResultSet resultSet = preparedStatement.executeQuery();

        int userId = 0;

        if (resultSet.next()) {
            userId = resultSet.getInt("id");
        }

        if (userId == 0) {
            throw new UserException("Can't find user");
        }

        return userId;
    }

    public static void updateRoleOnUser(int userId, String role) throws SQLException, RoleException {
        int roleId = DirectorDao.getRoleId(role);

        String request = "UPDATE \"Users\" SET \"RoleId\" = ? WHERE \"id\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, roleId);
        preparedStatement.setInt(2, userId);

        preparedStatement.executeUpdate();
    }
    //------------------------------------------------------------------------------------------------------------------

    //For director-to-employee dismissal
    //------------------------------------------------------------------------------------------------------------------
    public static void deleteEmployee(String login) throws SQLException, UserException, EmployeeException {
        if (!DirectorDao.checkForEmployee(DirectorDao.getUserId(login))) {
            throw new EmployeeException("Employee not found");
        }

        String request = "DELETE FROM \"Employee\" WHERE \"UserId\" IN (" +
                "SELECT \"UserId\" FROM \"Employee\" " +
                "INNER JOIN \"Users\" ON \"Users\".\"id\" = \"Employee\".\"UserId\" " +
                "AND \"Users\".\"Login\" = ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, login);

        preparedStatement.executeUpdate();
    }

    public static void deleteEmpToDepByEmp(String login) throws SQLException, UserException, EmployeeException {
        int employeeId = DirectorDao.getEmployeeId(DirectorDao.getUserId(login));

        String request = "DELETE FROM \"DepartmentsEmployees\" WHERE \"EmployeeId\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, employeeId);

        preparedStatement.executeUpdate();
    }
    //------------------------------------------------------------------------------------------------------------------

    //For director-to-employee change salary
    //------------------------------------------------------------------------------------------------------------------
    public static void updateSalary(String salary, String login) throws SQLException, UserException, EmployeeException {
        double dSalary;

        try{
            dSalary = Double.parseDouble(salary);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid value of salary. Must be number");
        }

        if (!DirectorDao.checkForEmployee(DirectorDao.getUserId(login))) {
            throw new EmployeeException("Employee not found");
        }

        String request = "UPDATE \"Employee\" SET \"Salary\" = ? WHERE \"UserId\" IN (" +
                "SELECT \"UserId\" FROM \"Employee\"" +
                "INNER JOIN \"Users\" ON \"Users\".\"id\" = \"Employee\".\"UserId\" " +
                "AND \"Login\" = ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setDouble(1, dSalary);
        preparedStatement.setString(2, login);

        preparedStatement.executeUpdate();
    }
    //------------------------------------------------------------------------------------------------------------------

    //For director-to-employee emp-to-dep add and delete
    //------------------------------------------------------------------------------------------------------------------
    public static void addEmpToDep(String login, String department) throws SQLException, UserException, DepartmentException, EmployeeException {
        if (DirectorDao.getDepartmentRole(department) != DirectorDao.getUserRole(login)) {
            throw new UserException("Department role is different from user role");
        }

        int employeeId = DirectorDao.getEmployeeId(DirectorDao.getUserId(login));
        int departmentId = DirectorDao.getDepartmentId(department);

        String request = "INSERT INTO \"DepartmentsEmployees\" (\"EmployeeId\", \"DepartmentId\") VALUES (?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, employeeId);
        preparedStatement.setInt(2, departmentId);

        preparedStatement.executeUpdate();
    }

    public static void deleteEmpToDep(String login, String department) throws SQLException, UserException, DepartmentException, EmployeeException {
        int employeeId = DirectorDao.getEmployeeId(DirectorDao.getUserId(login));
        int departmentId = DirectorDao.getDepartmentId(department);

        String request = "DELETE FROM \"DepartmentsEmployees\" WHERE \"EmployeeId\" = ? AND \"DepartmentId\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, employeeId);
        preparedStatement.setInt(2, departmentId);

        preparedStatement.executeUpdate();
    }

    public static int getDepartmentId(String department) throws SQLException, DepartmentException {
        String request = "SELECT \"id\" FROM \"Department\" WHERE \"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, department);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            throw new DepartmentException("Department not found");
        }
    }

    public static int getEmployeeId(int userId) throws SQLException, EmployeeException {
        String request = "SELECT \"id\" FROM \"Employee\" WHERE \"UserId\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            throw new EmployeeException("Employee not found");
        }
    }

    public static Boolean checkEmpToDep(String login, String department) throws SQLException, UserException, EmployeeException, DepartmentException {
        int employeeId = DirectorDao.getEmployeeId(DirectorDao.getUserId(login));
        int departmentId = DirectorDao.getDepartmentId(department);

        String request = "SELECT * FROM \"DepartmentsEmployees\" WHERE \"EmployeeId\" = ? AND \"DepartmentId\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, employeeId);
        preparedStatement.setInt(2, departmentId);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public static ObservableList<Department> getDepartments() throws SQLException {
        ObservableList<Department> departments = FXCollections.observableArrayList();

        String request = "SELECT \"Name\" FROM \"Department\"";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

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

    public static int getDepartmentRole(String depName) throws SQLException, DepartmentException {
        String request = "SELECT \"Role\" FROM \"Department\" WHERE \"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, depName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("Role");
        } else {
            throw new DepartmentException("Department not found");
        }
    }

    public static int getUserRole(String login) throws SQLException, UserException {
        String request = "SELECT \"RoleId\" FROM \"Users\" WHERE \"Login\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, login);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("RoleId");
        } else {
            throw new UserException("User not found");
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    //Add and delete new department
    //------------------------------------------------------------------------------------------------------------------
    public static void addNewDepartment(String name, String role) throws SQLException, DepartmentException, RoleException {
        if (DirectorDao.checkDepartment(name)) {
            throw new DepartmentException("Department already exists");
        }

        int roleId = DirectorDao.getRoleId(role);

        String request = "INSERT INTO \"Department\" (\"Name\", \"Role\") VALUES (?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, roleId);

        preparedStatement.executeUpdate();
    }

    public static void deleteDepartment(String name, String role) throws SQLException, DepartmentException, RoleException {
        if (!DirectorDao.checkDepartment(name)) {
            throw new DepartmentException("Can't find department");
        }

        int roleId = DirectorDao.getRoleId(role);

        DirectorDao.deleteEmpToDepByDep(name);

        String request = "DELETE FROM \"Department\" WHERE \"Name\" = ? AND \"Role\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, roleId);

        preparedStatement.executeUpdate();
    }

    public static Boolean checkDepartment(String name) throws SQLException {
        String request = "SELECT * FROM \"Department\" WHERE \"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public static void deleteEmpToDepByDep(String department) throws SQLException, DepartmentException {
        int departmentId = DirectorDao.getDepartmentId(department);

        String request = "DELETE FROM \"DepartmentsEmployees\" WHERE \"DepartmentId\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, departmentId);

        preparedStatement.executeUpdate();
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

    //Get information about product
    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<Characteristic> getCharacteristics(String productName) throws SQLException {
        ObservableList<Characteristic> listOfCharacteristics = FXCollections.observableArrayList();

        String request = "SELECT * FROM \"Characteristic\" " +
                "INNER JOIN \"ProductDetail\" ON \"ProductDetail\".\"CharacteristicId\" = \"Characteristic\".\"id\" " +
                "INNER JOIN \"Product\" ON \"ProductDetail\".\"ProductId\" = \"Product\".\"id\" " +
                "WHERE \"Product\".\"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, productName);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            listOfCharacteristics.add(new Characteristic(
                    resultSet.getString("Name"),
                    resultSet.getString("Description")
            ));
        }

        if (listOfCharacteristics.isEmpty()) {
            return null;
        } else {
            return listOfCharacteristics;
        }
    }


    //------------------------------------------------------------------------------------------------------------------

    //Add, delete, get characteristics
    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<Characteristic> getCharacteristics() throws SQLException {
        ObservableList<Characteristic> listOfCharacteristics = FXCollections.observableArrayList();

        String request = "SELECT * FROM \"Characteristic\"";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            listOfCharacteristics.add(new Characteristic(
                    resultSet.getString("Name"),
                    resultSet.getString("Description")
            ));
        }

        if (listOfCharacteristics.isEmpty()) {
            return null;
        } else {
            return listOfCharacteristics;
        }
    }

    public static void addCharacteristic(String name, String description) throws SQLException, CharacteristicException {
        if (DirectorDao.checkCharacteristic(name, description)) {
            throw new CharacteristicException("Characteristic already exist");
        }
        String request = "INSERT INTO \"Characteristic\" (\"Name\", \"Description\") VALUES (?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);

        preparedStatement.executeUpdate();
    }

    public static void deleteCharacteristic(String name, String description) throws SQLException, CharacteristicException {
        if (!DirectorDao.checkCharacteristic(name, description)) {
            throw new CharacteristicException("Characteristic not found");
        }

        String request = "DELETE FROM \"Characteristic\" WHERE \"Name\" = ? AND \"Description\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);

        preparedStatement.executeUpdate();
    }

    public static Boolean checkCharacteristic(String name, String description) throws SQLException {
        String request = "SELECT * FROM \"Characteristic\" WHERE \"Name\" = ? AND \"Description\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }
    //------------------------------------------------------------------------------------------------------------------

    //Add, delete, get product details
    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<ProductDetail> getProductDetail() throws SQLException {
        ObservableList<ProductDetail> listOfProductDetails = FXCollections.observableArrayList();

        String request = "SELECT \"Product\".\"Name\" AS pName, \"Characteristic\".\"Name\" AS cName," +
                " \"Characteristic\".\"Description\" AS cDescription, \"Type\".\"Name\" AS tName, \"Subtype\".\"Name\" AS sName " +
                "FROM \"ProductDetail\"" +
                "INNER JOIN \"Characteristic\" ON \"Characteristic\".\"id\" = \"ProductDetail\".\"CharacteristicId\"" +
                "INNER JOIN \"Product\" ON \"Product\".\"id\"= \"ProductDetail\".\"ProductId\"" +
                "INNER JOIN \"Subtype\" ON \"Subtype\".\"id\" = \"Product\".\"Subtype\"" +
                "INNER JOIN \"Type\" ON \"Type\".\"id\" = \"Subtype\".\"TypeId\"";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            listOfProductDetails.add(new ProductDetail(
                    resultSet.getString("pName"),
                    resultSet.getString("cName"),
                    resultSet.getString("tName"),
                    resultSet.getString("sName"),
                    resultSet.getString("cDescription")
            ));
        }

        if (listOfProductDetails.isEmpty()) {
            return null;
        } else {
            return listOfProductDetails;
        }
    }

    public static void addProductDetail(String productName, String characteristicName, String description) throws SQLException, ProductDetailException, ProductException, CharacteristicException {
        if (DirectorDao.checkProductDetail(productName, characteristicName)) {
            throw new ProductDetailException("Detail already exist");
        }

        int productId = DirectorDao.getProductId(productName);
        int characteristicId = DirectorDao.getCharacteristicId(characteristicName, description);

        String request = "INSERT INTO \"ProductDetail\" (\"ProductId\", \"CharacteristicId\") VALUES (?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, productId);
        preparedStatement.setInt(2, characteristicId);

        preparedStatement.executeUpdate();
    }

    public static void deleteProductDetail(String productName, String characteristicName, String description) throws SQLException, ProductDetailException, ProductException, CharacteristicException {
        if (!DirectorDao.checkProductDetail(productName, characteristicName)) {
            throw new ProductDetailException("Can't find product detail");
        }

        int productId = DirectorDao.getProductId(productName);
        int characteristicId = DirectorDao.getCharacteristicId(characteristicName, description);

        String request = "DELETE FROM \"ProductDetail\" WHERE \"ProductId\" = ? AND \"CharacteristicId\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, productId);
        preparedStatement.setInt(2, characteristicId);

        preparedStatement.executeUpdate();
    }

    public static Boolean checkProductDetail(String productName, String characteristicName) throws SQLException {
        String request = "SELECT * FROM \"ProductDetail\"" +
                "INNER JOIN \"Characteristic\" ON \"Characteristic\".id = \"ProductDetail\".\"CharacteristicId\" " +
                "INNER JOIN \"Product\" ON \"Product\".\"id\" = \"ProductDetail\".\"ProductId\" " +
                "AND \"Product\".\"Name\" = ? AND \"Characteristic\".\"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, productName);
        preparedStatement.setString(2, characteristicName);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
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

    public static int getCharacteristicId(String characteristicName, String description) throws SQLException, CharacteristicException {
        String request = "SELECT \"id\" FROM \"Characteristic\" " +
                "WHERE \"Characteristic\".\"Name\" = ? AND \"Characteristic\".\"Description\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, characteristicName);
        preparedStatement.setString(2, description);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            throw new CharacteristicException("Can't find characteristic");
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    //Add, delete, get types
    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<Type> getTypes() throws SQLException {
        ObservableList<Type> listOfTypes = FXCollections.observableArrayList();

        String request = "SELECT \"Name\" FROM \"Type\"";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            listOfTypes.add(new Type(
                    resultSet.getString("Name")
            ));
        }

        if (listOfTypes.isEmpty()) {
            return null;
        } else {
            return listOfTypes;
        }
    }

    public static void addType(String name) throws SQLException, TypeException {
        if (DirectorDao.checkType(name)) {
            throw new TypeException("Type already exist");
        }

        String request = "INSERT INTO \"Type\" (\"Name\") VALUES (?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        preparedStatement.executeUpdate();
    }

    public static void deleteType(String name) throws SQLException, TypeException {
        if (!DirectorDao.checkType(name)) {
            throw new TypeException("Can't find type");
        }

        String request = "DELETE FROM \"Type\" WHERE \"Type\".\"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        preparedStatement.executeUpdate();
    }

    public static Boolean checkType(String name) throws SQLException {
        String request = "SELECT * FROM \"Type\" WHERE \"Type\".\"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }
    //------------------------------------------------------------------------------------------------------------------

    //Add, delete, get subtypes
    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<Subtype> getSubtypes() throws SQLException {
        ObservableList<Subtype> listOfSubtypes = FXCollections.observableArrayList();

        String request = "SELECT \"Type\".\"Name\" AS tName, \"Subtype\".\"Name\" AS sName FROM \"Subtype\" " +
                "INNER JOIN \"Type\" ON \"Type\".\"id\" = \"Subtype\".\"TypeId\"";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            listOfSubtypes.add(new Subtype(
                    resultSet.getString("tName"),
                    resultSet.getString("sName")
            ));
        }

        if (listOfSubtypes.isEmpty()) {
            return null;
        } else {
            return listOfSubtypes;
        }
    }

    public static void addSubtype(String typeName, String subtypeName) throws SQLException, SubtypeException, TypeException {
        if (DirectorDao.checkSubtype(typeName, subtypeName)) {
            throw new SubtypeException("Subtype already exist");
        }

        int typeId = DirectorDao.getTypeId(typeName);

        String request = "INSERT INTO \"Subtype\" (\"TypeId\", \"Name\") VALUES (?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, typeId);
        preparedStatement.setString(2, subtypeName);

        preparedStatement.executeUpdate();
    }

    public static void deleteSubtype(String typeName, String subtypeName) throws SQLException, SubtypeException {
        if (!DirectorDao.checkSubtype(typeName, subtypeName)) {
            throw new SubtypeException("Can't find subtype");
        }

        String request = "DELETE FROM \"Subtype\" WHERE \"Subtype\".\"id\" IN (" +
                "SELECT \"Subtype\".\"id\" FROM \"Subtype\" " +
                "INNER JOIN \"Type\" ON \"Subtype\".\"TypeId\" = \"Type\".\"id\" " +
                "WHERE \"Type\".\"Name\" = ? AND \"Subtype\".\"Name\" = ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, typeName);
        preparedStatement.setString(2, subtypeName);

        preparedStatement.executeUpdate();
    }

    public static int getTypeId(String name) throws SQLException, TypeException {
        String request = "SELECT \"id\" FROM \"Type\" " +
                "WHERE \"Type\".\"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            throw new TypeException("Can't find type");
        }
    }

    public static Boolean checkSubtype(String typeName, String subtypeName) throws SQLException {
        String request = "SELECT * FROM \"Subtype\" " +
                "INNER JOIN \"Type\" ON \"Type\".\"id\" = \"Subtype\".\"TypeId\" " +
                "WHERE \"Type\".\"Name\" = ? AND \"Subtype\".\"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, typeName);
        preparedStatement.setString(2, subtypeName);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }
    //------------------------------------------------------------------------------------------------------------------

    //Add, delete, update product
    //------------------------------------------------------------------------------------------------------------------
    public static void addProduct(String name, String subtype, String price, String quantity, String description) throws SQLException, ProductException, SubtypeException, TypeException {
        if (DirectorDao.checkProduct(name)) {
            throw new ProductException("Product already exist");
        }

        double dPrice;
        int iQuantity;

        try {
            iQuantity = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Quantity must be integer");
        }

        try {
            dPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Price must be number");
        }

        int subtypeId = DirectorDao.getSubtypeId(subtype);

        String request = "INSERT INTO \"Product\" (\"Name\", \"Subtype\", \"Quantity\", \"Price\", \"Description\") " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, subtypeId);
        preparedStatement.setInt(3, iQuantity);
        preparedStatement.setDouble(4, dPrice);
        preparedStatement.setString(5, description);

        preparedStatement.executeUpdate();
    }

    public static void deleteProduct(String name) throws SQLException, ProductException {
        if (!DirectorDao.checkProduct(name)) {
            throw new ProductException("Can't find product");
        }

        String request = "DELETE FROM \"Product\" WHERE \"Product\".\"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        preparedStatement.executeUpdate();
    }

    public static void updateProduct(String name, String subtype, String price, String quantity, String description) throws SQLException, ProductException, SubtypeException, TypeException {
        if (!DirectorDao.checkProduct(name)) {
            throw new ProductException("Can't find product");
        }

        int iQuantity = Integer.parseInt(quantity);
        double dPrice = Double.parseDouble(price);

        int subtypeId = DirectorDao.getSubtypeId(subtype);

        String request = "UPDATE \"Product\" SET \"Subtype\" = ?, \"Price\" = ?, \"Quantity\" = ?, " +
                "\"Description\" = ? WHERE \"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, subtypeId);
        preparedStatement.setDouble(2, dPrice);
        preparedStatement.setInt(3, iQuantity);
        preparedStatement.setString(4, description);
        preparedStatement.setString(5, name);

        preparedStatement.executeUpdate();
    }

    public static Boolean checkProduct(String name) throws SQLException {
        String request = "SELECT * FROM \"Product\" WHERE \"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public static int getSubtypeId(String name) throws SQLException, SubtypeException {
        String request = "SELECT \"id\" FROM \"Subtype\" " +
                "WHERE \"Subtype\".\"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            throw new SubtypeException("Can't find subtype");
        }
    }

    public static ObservableList<String> getSubtypesByType(String type) throws SQLException {
        ObservableList<String> subtypes = FXCollections.observableArrayList();

        String request = "SELECT \"Subtype\".\"Name\" AS name FROM \"Subtype\" " +
                "INNER JOIN \"Type\" ON \"Type\".\"id\" = \"Subtype\".\"TypeId\" " +
                "WHERE \"Type\".\"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, type);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            subtypes.add(
                    resultSet.getString("name")
            );
        }

        if (subtypes.isEmpty()) {
            return null;
        } else {
            return subtypes;
        }
    }

    public static ObservableList<String> getSubtypesAll() throws SQLException {
        ObservableList<String> subtypes = FXCollections.observableArrayList();

        String request = "SELECT \"Name\" FROM \"Subtype\"";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            subtypes.add(
                    resultSet.getString("Name")
            );
        }

        if (subtypes.isEmpty()) {
            return null;
        } else {
            return subtypes;
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    //Check reports
    //------------------------------------------------------------------------------------------------------------------
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
                "WHERE \"Reports\".\"TheDate\" > ? AND \"Reports\".\"TheDate\" < ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setDate(1, dateStart);
        preparedStatement.setDate(2, dateEnd);

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
    //------------------------------------------------------------------------------------------------------------------
    //Check standards
    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<Standard> getStandards() throws SQLException {
        ObservableList<Standard> listOfStandards = FXCollections.observableArrayList();

        String request = "SELECT \"Standard\", \"Role\".\"Name\" AS role, \"DateStart\", \"DateEnd\" " +
                "FROM \"Standards\" " +
                "INNER JOIN \"Role\" ON \"Role\".\"id\" = \"Standards\".\"Role\"";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

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
    //------------------------------------------------------------------------------------------------------------------
    //New standard
    //------------------------------------------------------------------------------------------------------------------
    public static void addStandard(String standard, String role, Date start, Date end) throws SQLException, RoleException {
        int roleId = DirectorDao.getRoleId(role);

        String request = "INSERT INTO \"Standards\" (\"Standard\", \"Role\", \"DateStart\", \"DateEnd\") " +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, standard);
        preparedStatement.setInt(2, roleId);
        preparedStatement.setDate(3, start);
        preparedStatement.setDate(4, end);

        preparedStatement.executeUpdate();
    }
    //------------------------------------------------------------------------------------------------------------------

}
