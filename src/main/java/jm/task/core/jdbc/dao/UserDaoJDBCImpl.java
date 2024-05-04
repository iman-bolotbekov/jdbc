package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new Util().jdbcTemplate();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            jdbcTemplate.execute("CREATE TABLE UserTest (\n" +
                    "      id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "      name VARCHAR(255) NOT NULL,\n" +
                    "      last_name VARCHAR(255) NOT NULL,\n" +
                    "      age TINYINT\n" +
                    ");");
            System.out.println("Table created");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    public void dropUsersTable() {
        try {
            jdbcTemplate.execute("DROP TABLE UserTest;");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            jdbcTemplate.update("INSERT INTO UserTest (name, last_name, age) VALUES(?,?,?)", name, lastName, age);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            jdbcTemplate.update("DELETE FROM UserTest WHERE id = ?", id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try {
            return jdbcTemplate.query("SELECT * FROM UserTest", new BeanPropertyRowMapper<>(User.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cleanUsersTable() {
        try {
            jdbcTemplate.execute("TRUNCATE TABLE UserTest");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
