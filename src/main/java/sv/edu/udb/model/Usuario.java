package sv.edu.udb.model;
public class Usuario {

 private int id;
 private String nombre;
 private String apellido;
 private int telefono;
 private String correo;
 private int dui;
 private String password;
 private boolean verificacion;
 private int categoria;
 
 public int getId() {
 return id;
 }
 public void setId(int id) {
 this.id = id;
 }

 public String getNombre() {
 return nombre;
 }
 public void setNombre(String nombre) {
 this.nombre = nombre;
 }

 public String getApellido() {
 return apellido;
 }
 public void setApellido(String apellido) {
 this.apellido = apellido;
 }
 
 public int getTelefono() {
 return telefono;
 }
 public void setTelefono(int telefono) {
 this.telefono = telefono;
 }
 
 public String getCorreo() {
 return correo;
 }
 public void setCorreo(String correo) {
 this.correo = correo;
 }
 
 public int getDui() {
 return dui;
 }
 public void setDui(int dui) {
 this.dui = dui;
 }
 
 public String getPassword() {
 return password;
 }
 public void setPassword(String password) {
 this.password = password;
 }
 
 public boolean getVerificacion() {
 return verificacion;
 }
 public void setVerificacion(boolean verificacion) {
 this.verificacion = verificacion;
 }
 
 public int getCategoria() {
 return categoria;
 }
 public void setCategoria(int categoria) {
 this.categoria = categoria;
 }
 }