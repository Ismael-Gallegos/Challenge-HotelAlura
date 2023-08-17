package Modelo;

import java.time.LocalDate;

public class Reservaciones {
	
	private Integer id;
	private LocalDate fechaE;
	private LocalDate fechaS;
	private String valor;
	private String formaPago;
	
	public Reservaciones(LocalDate fechaE, LocalDate fechaS, String valor, String formaPago) {
		super();
		this.fechaE = fechaE;
		this.fechaS = fechaS;
		this.valor = valor;
		this.formaPago = formaPago;
	}
	
	public void setId(int id) {
	    this.id = id;
	}

	public LocalDate getFechaE() {
		return fechaE;
	}

	public void setFechaE(LocalDate fechaE) {
		this.fechaE = fechaE;
	}

	public LocalDate getFechaS() {
		return fechaS;
	}

	public void setFechaS(LocalDate fechaS) {
		this.fechaS = fechaS;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
}
