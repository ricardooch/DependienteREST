package sv.edu.udb.beans;

public class Boleto {
	
	 private int id;
	 private int oferta;
	 private String codigo;
	 private String estado;
	 private int cliente;
	 private String empresa;

	 public int getId() {
	 return id;
	 }
	 public void setId(int id) {
	 this.id = id;
	 }
	
	 public int getOferta() {
	 return oferta;
	 }
	 public void setOferta(int oferta) {
	 this.oferta = oferta;
	 }
	 
	 public String getCodigo() {
	 return codigo;
	 }
	 public void setCodigo(String codigo) {
	 this.codigo = codigo;
	 }
	 
	 public String getEstado() {
	 return estado;
	 }
	 public void setEstado(String estado) {
	 this.estado = estado;
	 }
	 
	 public int getCliente() {
	 return cliente;
	 }
	 public void setCliente(int cliente) {
	 this.cliente = cliente;
	 }
	 
	 public String getEmpresa() {
	 return empresa;
	 }
	 public void setEmpresa(String empresa) {
	 this.empresa = empresa;
	 }
}