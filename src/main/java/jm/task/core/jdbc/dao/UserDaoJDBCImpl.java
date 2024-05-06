package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private Connection connection;

    public UserDaoJDBCImpl() {
        jdbcTemplate = new Util().jdbcTemplate();
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        try {
            jdbcTemplate.execute((ConnectionCallback<Object>) connection -> connection.createStatement().execute("CREATE TABLE usertest (\n" +
                    "      id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "      name VARCHAR(255) NOT NULL,\n" +
                    "      last_name VARCHAR(255) NOT NULL,\n" +
                    "      age TINYINT\n" +
                    ");"));
            System.out.println("Table created");
            connection.commit(); // Commit the transaction
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(); // Rollback if an exception occurs
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try {
            jdbcTemplate.execute((ConnectionCallback<Object>) connection -> connection.createStatement().execute("DROP TABLE usertest;"));
            connection.commit();
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement("INSERT INTO usertest (name, last_name, age) VALUES(?,?,?)");
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setByte(3, age);
                return ps;
            });
            connection.commit();
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try {
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement("DELETE FROM usertest WHERE id = ?");
                ps.setLong(1, id);
                return ps;
            });
            connection.commit();
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        try {
            List<User> users = jdbcTemplate.query("SELECT * FROM usertest", new BeanPropertyRowMapper<>(User.class));
            connection.commit();
            return users;
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public void cleanUsersTable() {
        try {
            jdbcTemplate.execute((ConnectionCallback<Object>) connection -> connection.createStatement().execute("TRUNCATE usertest"));
            connection.commit();
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}