package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Modelo.Users; // Importar la clase Users para acceder a sus métodos
import views.Login; // Importar la clase Login para interactuar con la interfaz de usuario
import views.MenuUsuario; // Importar la clase MenuUsuario para mostrar la ventana del menú

public class UserController implements ActionListener {
    
    private Login lvista; // Referencia a la ventana de inicio de sesión

    public UserController(Login lvista) {
        this.lvista = lvista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nombre = lvista.getNombre(); // Obtener el nombre de usuario desde la vista
        String contrasena = lvista.getContrasena(); // Obtener la contraseña desde la vista
        
        if (Users.validarUsuario(nombre, contrasena)) {
            // Si el usuario y la contraseña son válidos
            MenuUsuario menu = new MenuUsuario(); // Crear la ventana del menú
            menu.setVisible(true); // Mostrar la ventana del menú
            lvista.dispose(); // Cerrar la ventana de inicio de sesión
        } else {
            // Si el usuario y la contraseña no son válidos
            JOptionPane.showMessageDialog(lvista, "Usuario y/o Contraseña inválidos, intente nuevamente");
            // Mostrar un mensaje de error utilizando un cuadro de diálogo
        }
    }
}

