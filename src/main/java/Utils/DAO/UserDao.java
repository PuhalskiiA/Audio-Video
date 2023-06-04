package Utils.DAO;

import Utils.Exceptions.CharacteristicException;
import Utils.Exceptions.DepartmentException;
import Utils.Exceptions.ProductException;
import Utils.Exceptions.UserException;
import Utils.LayerOfTypes.Cart;
import Utils.LayerOfTypes.Characteristic;
import Utils.LayerOfTypes.Deal;
import Utils.LayerOfTypes.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.puhalskii.coursebd.App;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private static ObservableList<Product> listOfProducts = FXCollections.observableArrayList();

    //My account-get-info
    //------------------------------------------------------------------------------------------------------------------
    public static String getUserInfo() throws SQLException, UserException {
        int userId = DirectorDao.getUserId(App.userLogin);

        String request = "SELECT COUNT(*) AS count FROM \"Order\" WHERE \"UserId\" = ? AND \"Order\".\"Status\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, "Closed");

        ResultSet resultSet = preparedStatement.executeQuery();

        int count = 0;

        if (resultSet.next()) {
            count = resultSet.getInt("count");
        }

        String text = "Login: " + App.userLogin +
                "\nCount of deals: " + count;

        return text;
    }
    //------------------------------------------------------------------------------------------------------------------

    //My account-deals
    //------------------------------------------------------------------------------------------------------------------
    public static ObservableList<Deal> getBoughtProducts() throws SQLException, UserException {
        ObservableList<Deal> deals = FXCollections.observableArrayList();

        String request = "SELECT \"Order\".\"id\" AS id, \"Product\".\"Name\" AS pName, \"Product\".\"Price\" AS price, " +
                "\"OrderDetails\".\"Quantity\" AS quantity, \"Order\".\"TotalAmount\" AS amount, \"Description\"," +
                "\"Order\".\"TheDate\" AS theDate, \"Type\".\"Name\" AS tName, \"Subtype\".\"Name\" AS sName " +
                "FROM \"Type\" " +
                "INNER JOIN \"Subtype\" ON \"Subtype\".\"TypeId\" = \"Type\".\"id\" " +
                "INNER JOIN \"Product\" ON \"Product\".\"Subtype\" = \"Subtype\".\"id\" " +
                "INNER JOIN \"OrderDetails\" ON \"Product\".\"id\" = \"OrderDetails\".\"Product\" " +
                "INNER JOIN \"Order\" ON \"Order\".\"id\" = \"OrderDetails\".\"Order\" " +
                "AND \"Order\".\"UserId\" = ? AND \"Order\".\"Status\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, App.userId);
        preparedStatement.setString(2, "Closed");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            deals.add(new Deal(
                    resultSet.getInt("id"),
                    resultSet.getString("pName"),
                    resultSet.getString("tName"),
                    resultSet.getString("sName"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("price"),
                    resultSet.getDouble("amount"),
                    resultSet.getDate("theDate"),
                    resultSet.getString("Description")
            ));
        }

        if (deals.isEmpty()) {
            return null;
        } else {
            return deals;
        }
    }

    public static void addComment(int orderId, String comment) throws SQLException {
        String request = "INSERT INTO \"Comment\" (\"OrderId\", \"Comment\") VALUES (?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, orderId);
        preparedStatement.setString(2, comment);

        preparedStatement.executeUpdate();
    }
    //------------------------------------------------------------------------------------------------------------------

    //Assortment
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

    public static void updateQuantity(String name, int quantity) throws SQLException {
        String request = "UPDATE \"Product\" SET \"Quantity\" = ? " +
                "WHERE \"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, quantity);
        preparedStatement.setString(2, name);

        preparedStatement.executeUpdate();
    }

    public static int getOrderId(String status, Date date) throws SQLException {
        String request = "SELECT \"Order\".\"id\" AS orderId FROM \"Order\" " +
                "INNER JOIN \"Users\" ON \"Users\".\"id\" = \"Order\".\"UserId\" " +
                "WHERE \"Users\".\"Login\" = ? AND \"Order\".\"Status\" = ? AND \"Order\".\"TheDate\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, App.userLogin);
        preparedStatement.setString(2, status);
        preparedStatement.setDate(3, date);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("orderId");
        } else {
            return 0;
        }
    }

    public static void updateOrderTotalAmount(double amount, Date date, int orderId) throws SQLException {
        String request = "UPDATE \"Order\" SET \"TotalAmount\" = ? " +
                "WHERE \"Order\".\"id\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setDouble(1, amount);
        preparedStatement.setInt(2, orderId);

        preparedStatement.executeUpdate();
    }

    public static void addOrder(double totalAmount, Date date) throws SQLException {
        String request = "INSERT INTO \"Order\" (\"UserId\", \"TotalAmount\", \"TheDate\") " +
                "VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, App.userId);
        preparedStatement.setDouble(2, totalAmount);
        preparedStatement.setDate(3, date);

        preparedStatement.executeUpdate();
    }

    public static void addOrderDetail(String productName, int quantity, int orderId) throws SQLException, ProductException {
        int productId = DirectorDao.getProductId(productName);

        String request = "INSERT INTO \"OrderDetails\" (\"Product\", \"Quantity\", \"Order\") " +
                "VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, productId);
        preparedStatement.setInt(2, quantity);
        preparedStatement.setInt(3, orderId);

        preparedStatement.executeUpdate();
    }
    //------------------------------------------------------------------------------------------------------------------

    //Cart
    //--------------------------------------------------------------------------------------------------------------
    public static ObservableList<Cart> getCart(Date date, String status) throws SQLException {
        ObservableList<Cart> cart = FXCollections.observableArrayList();

        String request = "SELECT \"Product\".\"Name\" AS pName, \"Type\".\"Name\" AS tName, \"Subtype\".\"Name\" AS sName, " +
                "\"OrderDetails\".\"Quantity\" as quantity, \"Product\".\"Price\" AS price, \"Order\".\"TotalAmount\" AS amount " +
                "FROM \"Order\" " +
                "INNER JOIN \"OrderDetails\" ON \"OrderDetails\".\"Order\" = \"Order\".\"id\" " +
                "INNER JOIN \"Product\" ON \"OrderDetails\".\"Product\" = \"Product\".\"id\" " +
                "INNER JOIN \"Subtype\" ON \"Subtype\".\"id\" = \"Product\".\"Subtype\"\n" +
                "INNER JOIN \"Type\" ON \"Subtype\".\"TypeId\" = \"Type\".\"id\" " +
                "WHERE \"Order\".\"TheDate\" = ? AND \"Order\".\"Status\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setDate(1, date);
        preparedStatement.setString(2, status);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            cart.add(new Cart(
                    resultSet.getString("pName"),
                    resultSet.getString("tName"),
                    resultSet.getString("sName"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("Price"),
                    resultSet.getDouble("amount"))
            );
        }

        if (cart.isEmpty()) {
            return null;
        } else {
            return cart;
        }
    }

    public static void deleteCartItem(String product, int quantity) throws SQLException, ProductException {
        int productId = DirectorDao.getProductId(product);
        int orderId = UserDao.getOrderId("Waiting", new Date(System.currentTimeMillis()));

        String request = "DELETE FROM \"OrderDetails\" WHERE \"OrderDetails\".\"Product\" = ? " +
                "AND \"OrderDetails\".\"Quantity\" = ? AND \"OrderDetails\".\"Order\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, productId);
        preparedStatement.setInt(2, quantity);
        preparedStatement.setInt(3, orderId);

        preparedStatement.executeUpdate();
    }

    public static Product getProduct(String product) throws SQLException {
        String request = "SELECT \"Name\", \"Quantity\" FROM \"Product\" WHERE \"Name\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, product);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Product(
                    resultSet.getString("Name"),
                    "",
                    "",
                    resultSet.getInt("Quantity"),
                    0.0,
                    ""
                    );
        } else {
            return null;
        }
    }

    public static void updateStatus() throws SQLException {
        int orderId = UserDao.getOrderId("Waiting", new Date(System.currentTimeMillis()));

        String request = "UPDATE \"Order\" SET \"Status\" = ? WHERE \"id\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, "Closed");
        preparedStatement.setInt(2, orderId);

        preparedStatement.executeUpdate();
    }
    //--------------------------------------------------------------------------------------------------------------
}
