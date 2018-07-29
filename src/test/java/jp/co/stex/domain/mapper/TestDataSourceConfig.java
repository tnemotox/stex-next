package jp.co.stex.domain.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;

/**
 * <p>試験用データベースに接続するためのデータソースです。</p>
 *
 * @author t.nemoto.x
 */
public class TestDataSourceConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/stex-test";
    private static final String USERNAME = "stex-test";
    private static final String PASSWORD = "stex";

    /**
     * <p> データソースを取得する。</p>
     *
     * @return データソース
     */
    @Bean
    public DataSource dataSource() {
        String url = System.getenv("TEST_DB_URL");
        if (ObjectUtils.isEmpty(url)) {
            url = URL;
        }
        return new DriverManagerDataSource(url, USERNAME, PASSWORD);
    }

    /**
     * <p>トランザクションマネージャを取得する。</p>
     *
     * @return トランザクションマネージャ
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * <p>sqlSessionを取得する。</p>
     *
     * @return sqlSession
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sessionFactory.getObject();
    }
}
