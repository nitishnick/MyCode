package com.sapient.test;

public class DBConfig {
	
	/** The database driver. */
	private String databaseDriver;

	/** The database password. */
	private String databasePassword;

	/** The database url. */
	private String databaseUrl;

	/** The database username. */
	private String databaseUsername;

	private String encryptionKey;

	/** The initial size. */
	private String initialSize;

	/** The max pool size. */
	private String maxPoolSize;

	/** The min pool size. */
	private String minPoolSize;

	/** The pool name. */
	private String poolName;

	/**
	 * @return the databaseDriver
	 */
	public String getDatabaseDriver() {
		return databaseDriver;
	}

	/**
	 * @param databaseDriver the databaseDriver to set
	 */
	public void setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
	}

	/**
	 * @return the databasePassword
	 */
	public String getDatabasePassword() {
		return databasePassword;
	}

	/**
	 * @param databasePassword the databasePassword to set
	 */
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	/**
	 * @return the databaseUrl
	 */
	public String getDatabaseUrl() {
		return databaseUrl;
	}

	/**
	 * @param databaseUrl the databaseUrl to set
	 */
	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	/**
	 * @return the databaseUsername
	 */
	public String getDatabaseUsername() {
		return databaseUsername;
	}

	/**
	 * @param databaseUsername the databaseUsername to set
	 */
	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	/**
	 * @return the encryptionKey
	 */
	public String getEncryptionKey() {
		return encryptionKey;
	}

	/**
	 * @param encryptionKey the encryptionKey to set
	 */
	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	/**
	 * @return the initialSize
	 */
	public String getInitialSize() {
		return initialSize;
	}

	/**
	 * @param initialSize the initialSize to set
	 */
	public void setInitialSize(String initialSize) {
		this.initialSize = initialSize;
	}

	/**
	 * @return the maxPoolSize
	 */
	public String getMaxPoolSize() {
		return maxPoolSize;
	}

	/**
	 * @param maxPoolSize the maxPoolSize to set
	 */
	public void setMaxPoolSize(String maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	/**
	 * @return the minPoolSize
	 */
	public String getMinPoolSize() {
		return minPoolSize;
	}

	/**
	 * @param minPoolSize the minPoolSize to set
	 */
	public void setMinPoolSize(String minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	/**
	 * @return the poolName
	 */
	public String getPoolName() {
		return poolName;
	}

	/**
	 * @param poolName the poolName to set
	 */
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

}
