package Utils.DAO;

import Utils.Exceptions.UserException;
import Utils.LayerOfTypes.Employee;
import ru.puhalskii.coursebd.App;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao {
    public static String getAccountInformation() throws SQLException, UserException {
        String request = "SELECT \"Name\", \"Surname\", \"MiddleName\", \"Salary\", \"Login\", \"TelephoneNumber\", " +
                "\"Birthday\", \"HiringDate\" " +
                "FROM \"Employee\" " +
                "INNER JOIN \"Users\" ON \"Users\".\"id\" = \"Employee\".\"UserId\"" +
                "WHERE \"UserId\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setInt(1, App.userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            employee = new Employee(
                    resultSet.getString("Name"),
                    resultSet.getString("Surname"),
                    resultSet.getString("MiddleName"),
                    resultSet.getString("Login"),
                    resultSet.getDouble("Salary"),
                    resultSet.getString("TelephoneNumber"),
                    resultSet.getString("Birthday"),
                    resultSet.getString("HiringDate")
            );
        }

        return "Name: " + employee.getName() + "\nSurname: " + employee.getSurname() +
                "\nMiddle name: " + employee.getMiddleName() + "\nSalary: " + employee.getSalary() +
                "\nTelephone number: " + employee.getTelephoneNumber() + "\nBirthday: " + employee.getBirthday() +
                "\nHiring date " + employee.getHiringDate() + "\nLogin: " + App.userLogin;
    }

    public static void updatePassword(String newPassword) throws SQLException, NoSuchAlgorithmException {
        String request = "UPDATE \"Users\" SET \"HashedPassword\" = ? WHERE \"Login\" = ?";
        String hashedPassword = AuthDao.passwordHashing(newPassword);

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, hashedPassword);
        preparedStatement.setString(2, App.userLogin);

        preparedStatement.executeUpdate();
    }
}
