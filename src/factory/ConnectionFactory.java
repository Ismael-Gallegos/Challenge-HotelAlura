package factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
    
    public DataSource ds;
    
    public ConnectionFactory() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        
        cpds.setJdbcUrl("jdbc:mysql://localhost/hotel_alura?useTimeZone=true&serverTimeZone=UTC");
        cpds.setUser("root");
        cpds.setPassword("2410.");
        cpds.setMaxPoolSize(15);
        
        this.ds = cpds;
    }
    
    // Método para obtener una conexión de la fábrica
    public Connection conectaFactory() {
        try {
            return this.ds.getConnection();
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
