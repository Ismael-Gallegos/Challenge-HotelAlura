package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import factory.ConnectionFactory;

public class Users {
    
    private String Nombre;
    private String Contrasena;
    
    public Users(String Nombre, String Contrasena) {
        this.Nombre = Nombre;
        this.Contrasena = Contrasena;
    }
    
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        this.Contrasena = contrasena;
    }
    
    /**
     * Valida si un usuario y contraseña son válidos.
     *
     * @param nombre     El nombre de usuario.
     * @param contrasena La contraseña del usuario.
     * @return True si el usuario y la contraseña son válidos, False si no lo son.
     */
    public static boolean validarUsuario(String nombre, String contrasena) {
        ConnectionFactory confa = new ConnectionFactory();
        Connection connection = null;
        PreparedStatement pstatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = confa.conectaFactory();
            // Selecciona todos los campos de la tabla Usuarios donde el Nombre y la Contraseña coinciden.
            pstatement = connection.prepareStatement("SELECT * FROM Usuarios WHERE Nombre=? AND Contrasena=?");
            pstatement.setString(1, nombre);
            pstatement.setString(2, contrasena);
            
            resultSet = pstatement.executeQuery();
            // Si hay un resultado en el conjunto de resultados, significa que el usuario es válido.
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (pstatement != null) pstatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }
}
