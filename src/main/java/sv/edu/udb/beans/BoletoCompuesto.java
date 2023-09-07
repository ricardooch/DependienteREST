package sv.edu.udb.beans;

import java.sql.Date;

public class BoletoCompuesto {
	
	 private int id;
	 private int oferta;
	 private String codigo;
	 private String estado;
	 private String nombreCliente;
	 private String apellidoCliente;
	 private String duiCliente;
	 private String tituloOferta;
	 private double precioNormal;
	 private double precioOfertado;
	 private Date fechaInicio;
	 private Date fechaFinal;
	 private Date fechaLimite;
	 private int estadoOferta;
	 private String nombreEmpresa;

//Getter y Setter para el atributo 'id'
public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}

// Getter y Setter para el atributo 'oferta'
public int getOferta() {
    return oferta;
}
public void setOferta(int oferta) {
    this.oferta = oferta;
}

// Getter y Setter para el atributo 'codigo'
public String getCodigo() {
    return codigo;
}
public void setCodigo(String codigo) {
    this.codigo = codigo;
}

// Getter y Setter para el atributo 'estado'
public String getEstado() {
    return estado;
}
public void setEstado(String estado) {
    this.estado = estado;
}

// Getter y Setter para el atributo 'nombreCliente'
public String getNombreCliente() {
    return nombreCliente;
}
public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
}

// Getter y Setter para el atributo 'apellidoCliente'
public String getApellidoCliente() {
    return apellidoCliente;
}
public void setApellidoCliente(String apellidoCliente) {
    this.apellidoCliente = apellidoCliente;
}

// Getter y Setter para el atributo 'duiCliente'
public String getDuiCliente() {
    return duiCliente;
}
public void setDuiCliente(String duiCliente) {
    this.duiCliente = duiCliente;
}

// Getter y Setter para el atributo 'tituloOferta'
public String getTituloOferta() {
    return tituloOferta;
}
public void setTituloOferta(String tituloOferta) {
    this.tituloOferta = tituloOferta;
}
//Getter y Setter para el atributo 'precioNormal'
public double getPrecioNormal() {
    return precioNormal;
}
public void setPrecioNormal(double precioNormal) {
    this.precioNormal = precioNormal;
}

// Getter y Setter para el atributo 'precioOfertado'
public double getPrecioOfertado() {
    return precioOfertado;
}
public void setPrecioOfertado(double precioOfertado) {
    this.precioOfertado = precioOfertado;
}

// Getter y Setter para el atributo 'fechaInicio'
public Date getFechaInicio() {
    return fechaInicio;
}
public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
}

// Getter y Setter para el atributo 'fechaFinal'
public Date getFechaFinal() {
    return fechaFinal;
}
public void setFechaFinal(Date fechaFinal) {
    this.fechaFinal = fechaFinal;
}

// Getter y Setter para el atributo 'fechaLimite'
public Date getFechaLimite() {
    return fechaLimite;
}
public void setFechaLimite(Date fechaLimite) {
    this.fechaLimite = fechaLimite;
}

// Getter y Setter para el atributo 'estadoOferta'
public int getEstadoOferta() {
    return estadoOferta;
}
public void setEstadoOferta(int estadoOferta) {
    this.estadoOferta = estadoOferta;
}

// Getter y Setter para el atributo 'nombreEmpresa'
public String getNombreEmpresa() {
    return nombreEmpresa;
}
public void setNombreEmpresa(String nombreEmpresa) {
    this.nombreEmpresa = nombreEmpresa;
}
}