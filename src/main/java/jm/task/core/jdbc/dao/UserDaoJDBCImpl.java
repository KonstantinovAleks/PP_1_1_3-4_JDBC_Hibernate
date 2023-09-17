package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users113 (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL, age INT(3) NOT NULL)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users113";
    private static final String INSERT_USER = "INSERT INTO users113 (name, lastName, age) VALUES (?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM users113 WHERE id = ?";
    private static final String QUERY_DATA = "SELECT * FROM users113";
    private static final String CLEAN_TABLE = "DELETE FROM users113";

    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(CREATE_TABLE);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(DROP_TABLE);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DATA)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));

                usersList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(CLEAN_TABLE);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
