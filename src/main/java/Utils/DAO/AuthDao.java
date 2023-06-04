package Utils.DAO;

import Utils.Exceptions.AuthException;
import Utils.Exceptions.UserException;
import Utils.LayerOfTypes.UserInfo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao {
    public static String passwordHashing(String password) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] bytes = sha1.digest(password.getBytes());

        StringBuilder builder = new StringBuilder();

        for (byte b : bytes)
            builder.append(String.format("%02X", b));

        return builder.toString();
    }

    public static UserInfo authentication(String login, String password) throws SQLException, NoSuchAlgorithmException, AuthException {
        String request = "SELECT \"id\", \"RoleId\", \"Blocked\", \"HashedPassword\" FROM \"Users\" WHERE \"Login\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        UserInfo userInfo = null;

        preparedStatement.setString(1, login);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            userInfo = new UserInfo(
                    resultSet.getInt("id"),
                    login,
                    resultSet.getString("HashedPassword"),
                    resultSet.getInt("RoleId"),
                    resultSet.getBoolean("Blocked"));
        }

        if (userInfo == null) {
            throw new AuthException("Incorrect password or login");
        }

        String hashedPassword = AuthDao.passwordHashing(password);

        if (!hashedPassword.equals(userInfo.getHashedPassword())) {
            throw new AuthException("Incorrect password");
        } else {
            return userInfo;
        }
    }

    public static void signUpUser(String login, String password) throws SQLException, NoSuchAlgorithmException, UserException {
        if (AuthDao.checkUserLogin(login)) {
            String hashedPassword = AuthDao.passwordHashing(password);

            String request = "INSERT INTO \"Users\" (\"Login\", \"HashedPassword\") VALUES (?, ?)";

            PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, hashedPassword);

            preparedStatement.executeUpdate();
        } else {
            throw new UserException("You can't take this login");
        }

    }

    public static Boolean checkUserLogin(String login) throws SQLException {
        String request = "SELECT \"Login\" FROM \"Users\" WHERE \"Login\" = ?";

        PreparedStatement preparedStatement = DaoFactory.getConnection().prepareStatement(request);

        preparedStatement.setString(1, login);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return false;
        } else {
            return true;
        }
    }
}
