package query.genarate.gettext.config;

import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Getter
public class JDBC {
    JdbcTemplate j;
    NamedParameterJdbcTemplate n;

    public JDBC() {
        t();
        n();
    }

    public void t() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/text");
        dataSource.setUsername("root");
        dataSource.setPassword("mvmvmv99");
        j = new JdbcTemplate(dataSource);
    }
    public void n() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/text");
        dataSource.setUsername("root");
        dataSource.setPassword("mvmvmv99");
        n= new NamedParameterJdbcTemplate(dataSource);
    }
}
