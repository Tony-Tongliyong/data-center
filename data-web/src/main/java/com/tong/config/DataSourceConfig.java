package com.tong.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;


/**
 * @author: tongly
 * @contact: 18158190830@163.com
 * @file: DataSourceConfig
 * @time: 2019/3/26 14:00
 * @desc:
 */
@Configuration
@MapperScan(basePackages = "com.tong.mybatis.mapper" ,sqlSessionFactoryRef = "datasourceSqlSessionFactory")
@Slf4j
public class DataSourceConfig {

    private static String MAPPER_LOCATION = "classpath*:com/tong/mybatis/xml/*.xml" ;

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix="datasource.druid")
    public DataSource primaryDataSource() {
        log.info("datasource初始化");
        return new DruidDataSource();
    }

    @Primary
    @Bean(name = "dataSourceTransactionManager")
    public DataSourceTransactionManager primaryTransaction(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "datasourceSqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DataSourceConfig.MAPPER_LOCATION));
        factoryBean.setTypeAliasesPackage("com.tong.mybatis.mapper");
        return factoryBean.getObject();
    }
    @Primary
    @Bean(name = "datasourceSqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("datasourceSqlSessionFactory") SqlSessionFactory sqlSessionFactory)  {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}