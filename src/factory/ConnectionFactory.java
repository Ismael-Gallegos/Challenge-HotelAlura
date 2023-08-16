package factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	public DataSource ds;
	
	public ConnectionFactory() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		
		cpds.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
		cpds.setUser("root");
		cpds.setPassword("2410.");
		cpds.setMaxPoolSize(15);
		
		this.ds = cpds;
		}
	public Connection conectaFactory() {
		try {
			return this.ds.getConnection();
		} catch (SQLException e) {
			System.out.println("Error detectado");
			throw new RuntimeException(e);
		}
	}
}
