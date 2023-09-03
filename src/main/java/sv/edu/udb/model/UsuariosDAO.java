package sv.edu.udb.model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class UsuariosDAO extends AppConnection{

 public void insert(Usuario usuario) throws SQLException{
 connect();
 pstmt = conn.prepareStatement("insert into usuario (Nombre_Usuario,Apellido_Usuario,Telefono_Usuario,Correo_Usuario,DUI_Usuario,Contrasena_Usuario,Verifcacion_Usuario,Categoria_Usuario) values(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
 pstmt.setString(1, usuario.getNombre());
 pstmt.setString(2, usuario.getApellido());
 pstmt.setInt(3, usuario.getTelefono());
 pstmt.setString(4, usuario.getCorreo());
 pstmt.setInt(5, usuario.getDui());
 pstmt.setString(6, usuario.getPassword());
 pstmt.setBoolean(7, usuario.getVerificacion());
 pstmt.setInt(8, usuario.getCategoria());
 pstmt.executeUpdate();

 //obteniendo el ultimo id generado
 ResultSet keys= pstmt.getGeneratedKeys();
 keys.next();
 int id = keys.getInt(1);

 usuario.setId(id);
 close();
 }

 public void update(Usuario usuario) throws SQLException{
	 connect();
	 pstmt = conn.prepareStatement("update usuario set Nombre_Usuario = ?, Apellido_Usuario = ?, Telefono_Usuario = ?, Correo_Usuario = ?, DUI_Usuario = ?, Contrasena_Usuario = ?, Verifcacion_Usuario = ?, Categoria_Usuario = ? where Id_Usuario = ?");
	 pstmt.setString(1, usuario.getNombre());
	 pstmt.setString(2, usuario.getApellido());
	 pstmt.setInt(3, usuario.getTelefono());
	 pstmt.setString(4, usuario.getCorreo());
	 pstmt.setInt(5, usuario.getDui());
	 pstmt.setString(6, usuario.getPassword());
	 pstmt.setBoolean(7, usuario.getVerificacion());
	 pstmt.setInt(8, usuario.getCategoria());
	 pstmt.setInt(9, usuario.getId());
	 pstmt.executeUpdate();
	 close();
	 }

	 public void delete(int id) throws SQLException{
	 connect();
	 pstmt = conn.prepareStatement("delete from usuario where Id_Usuario = ?");
	 pstmt.setInt(1, id);
	 pstmt.execute();
	 close();
	 }

	 public ArrayList<Usuario> findAll() throws SQLException{
	 connect();
	 stmt = conn.createStatement();
	 resultSet = stmt.executeQuery("select Id_Usuario,Nombre_Usuario,Apellido_Usuario,Telefono_Usuario,Correo_Usuario,DUI_Usuario,Contrasena_Usuario,Verifcacion_Usuario,Categoria_Usuario from usuario");
	 ArrayList<Usuario> usuarios = new ArrayList();

	 while(resultSet.next()){
	 Usuario tmp = new Usuario();
	 tmp.setId(resultSet.getInt(1));
	 tmp.setNombre(resultSet.getString(2));
	 tmp.setApellido(resultSet.getString(3));
	 tmp.setTelefono(resultSet.getInt(4));
	 tmp.setCorreo(resultSet.getString(5));
	 tmp.setDui(resultSet.getInt(6));
	 tmp.setPassword(resultSet.getString(7));
	 tmp.setVerificacion(resultSet.getBoolean(8));
	 tmp.setCategoria(resultSet.getInt(9));
	 usuarios.add(tmp);
	 }

	 close();

	 return usuarios;
	 }

	 public Usuario findById(int id) throws SQLException{

		 Usuario usuario = null;

		 connect();
		 pstmt = conn.prepareStatement("select Id_Usuario,Nombre_Usuario,Apellido_Usuario,Telefono_Usuario,Correo_Usuario,DUI_Usuario,Contrasena_Usuario,Verifcacion_Usuario,Categoria_Usuario from usuario where Id_Usuario = ?");
		 pstmt.setInt(1, id);

		 resultSet = pstmt.executeQuery();

		 while(resultSet.next()){
		 usuario = new Usuario();
		 usuario.setId(resultSet.getInt(1));
		 usuario.setNombre(resultSet.getString(2));
		 usuario.setApellido(resultSet.getString(3));
		 usuario.setTelefono(resultSet.getInt(4));
		 usuario.setCorreo(resultSet.getString(5));
		 usuario.setDui(resultSet.getInt(6));
		 usuario.setPassword(resultSet.getString(7));
		 usuario.setVerificacion(resultSet.getBoolean(8));
		 usuario.setCategoria(resultSet.getInt(9));
		 }

		 close();
		 return usuario;
		 }
		}