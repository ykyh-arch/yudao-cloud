package cn.iocoder.yudao.framework.mybatis.config;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.util.collection.SetUtils;
import cn.iocoder.yudao.framework.mybatis.core.enums.SqlConstants;
import cn.iocoder.yudao.framework.mybatis.core.util.JdbcUtils;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Set;

/**
 * 当 IdType 为 {@link IdType#NONE} 时，根据 PRIMARY 数据源所使用的数据库，自动设置
 *
 * @author 芋道源码
 */
@Slf4j
public class IdTypeEnvironmentPostProcessor implements EnvironmentPostProcessor {

    // org.springframework.boot.env.EnvironmentPostProcessor 是 Spring Boot 中的一个接口，用于在 Spring 应用程序启动时对 Spring 的 Environment 进行后处理。
    // Environment 代表了 Spring 应用程序的环境配置，包括配置属性、配置文件等信息。EnvironmentPostProcessor 接口允许开发者在 Spring Boot 应用程序启动时动态地修改 Environment 中的属性值，或者添加新的属性。
    // 主要作用包括：
    // 动态修改应用程序的配置属性：开发者可以实现自定义的 EnvironmentPostProcessor 来在应用程序启动时修改配置属性，例如根据环境变量、外部配置文件等动态地修改应用程序的配置信息。
    // 添加额外的配置属性：通过实现 EnvironmentPostProcessor 接口，开发者可以向 Environment 中添加额外的配置属性，这些属性可以在应用程序其他地方使用。
    // 总之，EnvironmentPostProcessor 接口为开发者提供了在 Spring Boot 应用程序启动时对环境配置进行处理的扩展点，能够帮助开发者动态地修改和扩展应用程序的配置信息。

    private static final String ID_TYPE_KEY = "mybatis-plus.global-config.db-config.id-type";

    private static final String DATASOURCE_DYNAMIC_KEY = "spring.datasource.dynamic";

    private static final String QUARTZ_JOB_STORE_DRIVER_KEY = "spring.quartz.properties.org.quartz.jobStore.driverDelegateClass";

    private static final Set<DbType> INPUT_ID_TYPES = SetUtils.asSet(DbType.ORACLE, DbType.ORACLE_12C,
            DbType.POSTGRE_SQL, DbType.KINGBASE_ES, DbType.DB2, DbType.H2);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 如果获取不到 DbType，则不进行处理
        DbType dbType = getDbType(environment);
        if (dbType == null) {
            return;
        }

        // 设置 Quartz JobStore 对应的 Driver
        // TODO 芋艿：暂时没有找到特别合适的地方，先放在这里
        setJobStoreDriverIfPresent(environment, dbType);

        // 初始化 SQL 静态变量
        SqlConstants.init(dbType);

        // 如果非 NONE，则不进行处理
        IdType idType = getIdType(environment);
        if (idType != IdType.NONE) {
            return;
        }
        // 情况一，用户输入 ID，适合 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库
        if (INPUT_ID_TYPES.contains(dbType)) {
            setIdType(environment, IdType.INPUT);
            return;
        }
        // 情况二，自增 ID，适合 MySQL 等直接自增的数据库
        setIdType(environment, IdType.AUTO);
    }

    public IdType getIdType(ConfigurableEnvironment environment) {
        return environment.getProperty(ID_TYPE_KEY, IdType.class);
    }

    public void setIdType(ConfigurableEnvironment environment, IdType idType) {
        environment.getSystemProperties().put(ID_TYPE_KEY, idType);
        log.info("[setIdType][修改 MyBatis Plus 的 idType 为({})]", idType);
    }

    public void setJobStoreDriverIfPresent(ConfigurableEnvironment environment, DbType dbType) {
        String driverClass = environment.getProperty(QUARTZ_JOB_STORE_DRIVER_KEY);
        if (StrUtil.isNotEmpty(driverClass)) {
            return;
        }
        // 根据 dbType 类型，获取对应的 driverClass
        switch (dbType) {
            case POSTGRE_SQL:
                driverClass = "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate";
                break;
            case ORACLE:
            case ORACLE_12C:
                driverClass = "org.quartz.impl.jdbcjobstore.oracle.OracleDelegate";
                break;
            case SQL_SERVER:
            case SQL_SERVER2005:
                driverClass = "org.quartz.impl.jdbcjobstore.MSSQLDelegate";
                break;
        }
        // 设置 driverClass 变量
        if (StrUtil.isNotEmpty(driverClass)) {
            environment.getSystemProperties().put(QUARTZ_JOB_STORE_DRIVER_KEY, driverClass);
        }
    }

    public static DbType getDbType(ConfigurableEnvironment environment) {
        String primary = environment.getProperty(DATASOURCE_DYNAMIC_KEY + "." + "primary");
        if (StrUtil.isEmpty(primary)) {
            return null;
        }
        String url = environment.getProperty(DATASOURCE_DYNAMIC_KEY + ".datasource." + primary + ".url");
        if (StrUtil.isEmpty(url)) {
            return null;
        }
        return JdbcUtils.getDbType(url);
    }

}
