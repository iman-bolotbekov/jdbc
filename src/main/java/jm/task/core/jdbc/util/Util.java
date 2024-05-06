package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class Util {
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/mysql");
        dataSource.setUser("mysql");
        dataSource.setPassword("mysql");
        return dataSource;
    }
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
