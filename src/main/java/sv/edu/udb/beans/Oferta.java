package sv.edu.udb.beans;

import java.sql.Date;

public class Oferta {
	
	 private int id;
	 private String titulo;
	 private double precioNormal;
	 private double precioOfertado;
	 private Date fechaInicio;
	 private Date fechaFinal;
	 private Date fechaLimite;
	 private int cantidadLimite;
	 private String descripcion;
	 private String detalles;
	 private int estado;
	 private String empresa;

	    public int getId() {
	        return id;
	    }
	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getTitulo() {
	        return titulo;
	    }
	    public void setTitulo(String titulo) {
	        this.titulo = titulo;
	    }

	    public double getPrecioNormal() {
	        return precioNormal;
	    }
	    public void setPrecioNormal(double precioNormal) {
	        this.precioNormal = precioNormal;
	    }

	    public double getPrecioOfertado() {
	        return precioOfertado;
	    }
	    public void setPrecioOfertado(double precioOfertado) {
	        this.precioOfertado = precioOfertado;
	    }

	    public Date getFechaInicio() {
	        return fechaInicio;
	    }
	    public void setFechaInicio(Date fechaInicio) {
	        this.fechaInicio = fechaInicio;
	    }

	    public Date getFechaFinal() {
	        return fechaFinal;
	    }
	    public void setFechaFinal(Date fechaFinal) {
	        this.fechaFinal = fechaFinal;
	    }

	    public Date getFechaLimite() {
	        return fechaLimite;
	    }
	    public void setFechaLimite(Date fechaLimite) {
	        this.fechaLimite = fechaLimite;
	    }

	    public int getCantidadLimite() {
	        return cantidadLimite;
	    }
	    public void setCantidadLimite(int cantidadLimite) {
	        this.cantidadLimite = cantidadLimite;
	    }

	    public String getDescripcion() {
	        return descripcion;
	    }
	    public void setDescripcion(String descripcion) {
	        this.descripcion = descripcion;
	    }

	    public String getDetalles() {
	        return detalles;
	    }
	    public void setDetalles(String detalles) {
	        this.detalles = detalles;
	    }

	    public int getEstado() {
	        return estado;
	    }
	    public void setEstado(int estado) {
	        this.estado = estado;
	    }

	    public String getEmpresa() {
	        return empresa;
	    }
	    public void setEmpresa(String empresa) {
	        this.empresa = empresa;
	    }
}