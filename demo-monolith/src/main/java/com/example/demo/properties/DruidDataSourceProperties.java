package com.example.demo.properties;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;

@Component
@ConfigurationProperties(prefix = DruidDataSourceProperties.CONFIG_PROPERTIES_PREFIX)
@Validated
public class DruidDataSourceProperties {

	protected final static String CONFIG_PROPERTIES_PREFIX = "druid.datasource";

	/**
	 * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
	 */
	private int initialSize;

	/**
	 * 最小连接池数量
	 */
	private int minIdle;

	/**
	 * 最大连接池数量
	 */
	private int maxActive;

	/**
	 * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
	 */
	private long maxWaitMillis;

	private long minEvictableIdleTimeMillis;

	/**
	 * 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
	 */
	private String validationQuery;

	/**
	 * 单位：秒，检测连接是否有效的超时时间。<br />
	 * 底层调用jdbc Statement对象的void setQueryTimeout(int seconds)方法
	 */
	private int validationQueryTimeout;

	/**
	 * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
	 */
	private boolean testOnBorrow;

	/**
	 * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
	 */
	private boolean testOnReturn;

	/**
	 * 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
	 */
	private boolean testWhileIdle;

	/**
	 * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
	 */
	private boolean poolPreparedStatements;

	/**
	 * 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
	 */
	private int maxPoolPreparedStatementPerConnectionSize;

	/**
	 * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有： <br />
	 * 监控统计用的filter:stat <br />
	 * 日志用的filter:log4j <br />
	 * 防御sql注入的filter:wall <br />
	 */
	private String filters;

	private String connectionProperties;

	private boolean useGlobalDataSourceStat;

	/**
	 * 类型是List<com.alibaba.druid.filter.Filter>，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系
	 */
	private List<Filter> proxyFilters;

	/**
	 * 物理连接初始化的时候执行的sql
	 */
	private Collection<? extends Object> connectionInitSqls;

	private void test() throws SQLException {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setInitialSize(initialSize);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxActive(maxActive);

		dataSource.setMaxWait(maxWaitMillis);
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setValidationQueryTimeout(validationQueryTimeout);

		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setTestOnReturn(testOnReturn);

		dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		dataSource.setFilters(filters);
		dataSource.setConnectionProperties(connectionProperties);
		dataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
		dataSource.setProxyFilters(proxyFilters);
		dataSource.setConnectionInitSqls(connectionInitSqls);

	}

}
