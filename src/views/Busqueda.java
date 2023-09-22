package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Modelo.RegHuespedes;
import Modelo.Reservaciones;
import controller.HuespedesController;
import controller.ReservacionesController;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	private ReservacionesController reservacionesControl;
	private HuespedesController huespedControl;
	private ReservasView reservasVista;
	String reservacion;
	String huespedes;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		this.reservasVista = new ReservasView();
		this.reservacionesControl = new ReservacionesController();
		this.huespedControl = new HuespedesController();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mostrarTablaReservaciones();
		
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		mostrarTablaHuespedes();
		
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//AGREGADO
				limpiarTabla();
				if (txtBuscar.getText().equals("")) {
					mostrarTablaReservaciones();
					mostrarTablaHuespedes();
				} else {
					mostrarTablaReservaciones();
					mostrarTablaHuespedes();
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
		
			public void mouseClicked(MouseEvent e) {
				int filaReservaciones = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();
				
				if (filaReservaciones >= 0) {
					actualizarReservaciones();
					mostrarTablaReservaciones();
					mostrarTablaHuespedes();
				} else if (filaHuespedes >= 0) {
					actualizarHuespedes();
					mostrarTablaReservaciones();
					mostrarTablaHuespedes();
				}
			}
			
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        int filaReservas = tbReservas.getSelectedRow();
		        int filaHuespedes = tbHuespedes.getSelectedRow();

		        if (filaReservas >= 0) {
		            reservacion = tbReservas.getValueAt(filaReservas, 0).toString();
		            int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la reservación?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

		            if (confirmar == JOptionPane.YES_OPTION) {
		                String valor = tbReservas.getValueAt(filaReservas, 0).toString();
		                reservacionesControl.Eliminar(Integer.valueOf(valor));
		                JOptionPane.showMessageDialog(contentPane, "Registro eliminado correctamente");
		                limpiarTabla();
		                mostrarTablaReservaciones();
						mostrarTablaHuespedes();
		                
		            }
		        } else if(filaHuespedes >= 0) {
		        	huespedes = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
		        	int confirmaHuesp = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el Registro de este Huesped?");
		        	
		        	if (confirmaHuesp == JOptionPane.YES_OPTION) {
		        		String valor = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
		        		huespedControl.Eliminar(Integer.valueOf(valor));
		        		JOptionPane.showMessageDialog(contentPane, "Registro de Huesped eliminado");
		        		limpiarTabla();
		        		mostrarTablaReservaciones();
						mostrarTablaHuespedes();
		        	}
		        }else {
		        	JOptionPane.showMessageDialog(null, "Error al eliminar el registro del Huesped");
		        }
		    }
		});

		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	// RESERVACIONES CRUD
	private List<Reservaciones> mostrarReservaciones() {
		return this.reservacionesControl.buscar();
	}
	
	private List<Reservaciones> buscarIdReservaciones() {
		return this.reservacionesControl.buscarId(txtBuscar.getText()); // Llama al método de búsqueda en reservacionesControl
	}
	
	// Método para mostrar las reservaciones en la tabla
	private void mostrarTablaReservaciones() {
	    List<Reservaciones> reservaciones = buscarIdReservaciones();
	    modelo.setRowCount(0); // Limpia las filas existentes en el modelo de la tabla
	    try {
	        for (Reservaciones reserv : reservaciones) {
	        	 // Agregar una nueva fila con los valores de cada atributo de la reservación
	            modelo.addRow(new Object[]{
	            		reserv.getId(),         // Agregar el ID de la reservación a la columna
	                    reserv.getFechaE(),     // Agregar la fecha de entrada a la columna
	                    reserv.getFechaS(),     // Agregar la fecha de salida a la columna
	                    reserv.getValor(),      // Agregar el valor a la columna
	                    reserv.getFormaPago()   // Agregar la forma de pago a la columna
	                });
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Manejo de excepciones: imprime la traza de la excepción para depuración
	    }
	}
	
		private void mostrarTablaReservacionesId() {
	    List<Reservaciones> reservaciones = mostrarReservaciones();
	    try {
	        for (Reservaciones reserv : reservaciones) {
	            modelo.addRow(new Object[]{
	                reserv.getId(),         // Agregar el ID de la reservación a la columna
	                reserv.getFechaE(),     // Agregar la fecha de entrada a la columna
	                reserv.getFechaS(),     // Agregar la fecha de salida a la columna
	                reserv.getValor(),      // Agregar el valor a la columna
	                reserv.getFormaPago()   // Agregar la forma de pago a la columna
	            });
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Manejo de excepciones: imprime la traza de la excepción para depuración
	    }
	}
	
		// Método para Actualizar las reservaciones en la tabla
		private void actualizarReservaciones() {
		    // Obtiene el valor de la celda seleccionada en la tabla de reservaciones
		    Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
		            .ifPresent(fila -> {
		                LocalDate fechaE = null;
		                LocalDate fechaS = null;
		                
		                try {
		                    // Formato de fecha esperado en la tabla
		                    DateTimeFormatter fechaFormato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		                    // Intenta analizar las fechas de la celda seleccionada
		                    fechaE = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString(), fechaFormato);
		                    fechaS = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString(), fechaFormato);
		                } catch (DateTimeException e) {
		                    e.printStackTrace(); // Manejo de excepciones: imprime la traza de la excepción para depuración.
		                    // Asigna valores predeterminados en caso de excepción al analizar las fechas
		                    fechaE = LocalDate.of(2023, 1, 1); // Fecha predeterminada de inicio
		                    fechaS = LocalDate.of(2030, 1, 1); // Fecha predeterminada de finalización
		                }		                
		                
		                this.reservasVista.limpiarValor(); // Limpia el valor en la interfaz gráfica		                
		                
		                String valor = calcularValorReservasciones(fechaE, fechaS); // Calcula el valor de la reserva utilizando las fechas analizadas.
		                String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4); // Obtiene la forma de pago desde la tabla.
		                Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString()); // Obtiene el ID desde la tabla y lo convierte a un entero
		                
		                // Evita que se altere el valor de Id.
		                if (tbReservas.getSelectedColumn() == 0) {
		                    JOptionPane.showMessageDialog(this, "No se puede editar los ID");
		                } else { 		                    
		                    this.reservacionesControl.actualizarReservaciones(fechaE, fechaS, valor, formaPago, id); // Actualiza las reservaciones con los nuevos valores		                    
		                    JOptionPane.showMessageDialog(this, "Registro modificado con éxito"); 
		                }
		            });
		}
 
		
		public String calcularValorReservasciones(LocalDate fechaE, LocalDate fechaS) {
		    if (fechaE != null && fechaS != null) {
		        double dias = (double) ChronoUnit.DAYS.between(fechaE, fechaS);
		        double noche = 250;
		        double valor = dias * noche;
		        return Double.toString(valor); 
		    } else {
		        return "";
			}
		}

	
		// HUESPEDES CRUD
		private List<RegHuespedes> mostrarHuespedes() {
			return this.huespedControl.mostrarHuespedes();
		}
		
		private List<RegHuespedes> buscarHuespedesID() {
			return this.huespedControl.buscarHuespedes(txtBuscar.getText());
		}
		
		private void mostrarTablaHuespedes() {
			List<RegHuespedes> huespedes = mostrarHuespedes();
		    modeloHuesped.setRowCount(0); // Limpia las filas existentes en el modelo de la tabla
		    try {
		        for (RegHuespedes huespedes1 : huespedes) {
		        	 // Agregar una nueva fila con los valores de cada atributo de la reservación
		            modeloHuesped.addRow(new Object[]{
		            		huespedes1.getId(),
		            		huespedes1.getNombre(),
		            		huespedes1.getApellido(),
		            		huespedes1.getFechaNacimiento(),
		            		huespedes1.getNacionalidad(),
		            		huespedes1.getTelefono(),
		            		huespedes1.getIdReserva()
		                });
		        }
		    } catch (Exception e) {
		        e.printStackTrace(); // Manejo de excepciones: imprime la traza de la excepción para depuración
		    }
		}
		
		private void mostrarTablaHuespedesID() {
			List<RegHuespedes> huespedes = buscarHuespedesID();
		    modeloHuesped.setRowCount(0); // Limpia las filas existentes en el modelo de la tabla
		    try {
		        for (RegHuespedes huespedes1 : huespedes) {
		        	 // Agregar una nueva fila con los valores de cada atributo de la reservación
		        	modeloHuesped.addRow(new Object[]{
		            		huespedes1.getId(),
		            		huespedes1.getNombre(),
		            		huespedes1.getApellido(),
		            		huespedes1.getFechaNacimiento(),
		            		huespedes1.getNacionalidad(),
		            		huespedes1.getTelefono(),
		            		huespedes1.getIdReserva()
		                });
		        }
		    } catch (Exception e) {
		        e.printStackTrace(); // Manejo de excepciones: imprime la traza de la excepción para depuración
		    }
		}
		
		private void actualizarHuespedes() {
		    Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
		    .ifPresentOrElse(filaHuespedes -> {
		        String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
		        String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
		        LocalDate fechaNacimiento = LocalDate.parse(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString());
		        String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
		        String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
		        Integer idReserva = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
		        Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(),
		        		0).toString());
		        
		        if (tbHuespedes.getSelectedColumn() == 0 || tbHuespedes.getSelectedColumn() == 6) {
		            JOptionPane.showMessageDialog(this, "No es posible modificar el ID");
		        } else {
		            this.huespedControl.ActualizarHuesp(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
		            JOptionPane.showMessageDialog(this, String.format("Registro actualizado con éxito"));
		        }
		        
		    }, () -> JOptionPane.showInternalMessageDialog(this, "Verifica en actualizarHuespedes Busqueda"));
		}
		
		private void limpiarTabla() {
	    ((DefaultTableModel) tbHuespedes.getModel()).setRowCount(0);
	    ((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	}
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
