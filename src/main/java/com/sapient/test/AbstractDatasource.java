package com.sapient.test;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDatasource {

	/** The Constant CONNECTION_WAIT_TIMEOUT_SECS. */
	private static final int CONNECTION_WAIT_TIMEOUT_SECS = 300;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractDatasource.class);

	/** The Constant TEST_SQL. */
	private static final String TEST_SQL = "select 1 from dual";



	private final DataSource dataSource;

	/**
	 * Data source.
	 *
	 * @return the pool data source
	 */
	public AbstractDatasource(DBConfig config) {

		PoolProperties pooledDataSource = new PoolProperties();

		try {

			pooledDataSource.setDriverClassName(config.getDatabaseDriver());
			pooledDataSource.setUrl(config.getDatabaseUrl());
			pooledDataSource.setUsername(config.getDatabaseUsername());
			pooledDataSource.setInitialSize(Integer.parseInt(config.getInitialSize()));
			pooledDataSource.setMaxActive(Integer.parseInt(config.getMaxPoolSize()));
			pooledDataSource.setMinIdle(Integer.parseInt(config.getMinPoolSize()));
			pooledDataSource.setValidationQuery(TEST_SQL);
			pooledDataSource.setSuspectTimeout(CONNECTION_WAIT_TIMEOUT_SECS);
			pooledDataSource.setName(config.getPoolName());

			// derive password
			String password = config.getDatabasePassword();
			if (StringUtils.isEmpty(password)) {
				throw new SQLException("Null password");
			}
			pooledDataSource.setPassword(password);
		} catch (Exception e) {
			LOGGER.error("Exception setting up datasource", e);

			pooledDataSource.setMinIdle(0);
			pooledDataSource.setMaxActive(0);
			pooledDataSource.setInitialSize(0);

		}

		LOGGER.info("Setting up datasource for user:{} and databaseUrl:{}",
				config.getDatabaseUsername(), config.getDatabaseUrl());
		DataSource datasource = new DataSource();
		datasource.setPoolProperties(pooledDataSource);
		this.dataSource = datasource;
	}

	
	/**
	 * @return the dataSource
	 */
	protected DataSource getDataSource() {
		return dataSource;
	}

}
