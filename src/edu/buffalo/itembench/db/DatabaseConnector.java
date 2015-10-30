/**
 * 
 */
package edu.buffalo.itembench.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author pketki
 * 
 */
public class DatabaseConnector {

	private String driver;
	private String connectionURL;
	private String user;
	private String password;
	private String dbName;
	private Connection connection;

	public DatabaseConnector(Properties props) {
		super();
		driver = props.getProperty("driver");
		connectionURL = props.getProperty("url");
		user = props.getProperty("user");
		password = props.getProperty("password");
		dbName = props.getProperty("dbName");
	}

	public Connection getConnection() throws DbException {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(connectionURL + dbName,
					user, password);
		} catch (ClassNotFoundException e) {
			throw new DbException("Incorrect Database Driver");
		} catch (SQLException e) {
			throw new DbException("Unable to connect to DB");
		}

		return connection;
	}

	public ResultSet query(String query) throws DbException {
		ResultSet resultSet = null;
		try {
			// TODO: Use statement instead?
			PreparedStatement statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			throw new DbException("Invalid query or unable to connect to DB");
		}
		return resultSet;
	}

	public void closeConnection() throws DbException {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new DbException("Unable to close connection");
		}
		connection = null;
	}

}
