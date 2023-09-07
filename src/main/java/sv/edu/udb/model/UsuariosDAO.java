package sv.edu.udb.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import sv.edu.udb.beans.ResultadoLogin;
import sv.edu.udb.beans.Usuario;

public class UsuariosDAO extends AppConnection {

	public void insert(Usuario usuario) throws SQLException {
		connect();
		pstmt = conn.prepareStatement(
				"insert into usuario (Nombre_Usuario,Apellido_Usuario,Telefono_Usuario,Correo_Usuario,DUI_Usuario,Contrasena_Usuario,Categoria_Usuario) values(?,?,?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, usuario.getNombre());
		pstmt.setString(2, usuario.getApellido());
		pstmt.setInt(3, usuario.getTelefono());
		pstmt.setString(4, usuario.getCorreo());
		pstmt.setInt(5, usuario.getDui());
		pstmt.setString(6, usuario.getPassword());
		pstmt.setInt(7, usuario.getCategoria());
		pstmt.executeUpdate();

		// obteniendo el ultimo id generado
		ResultSet keys = pstmt.getGeneratedKeys();
		keys.next();
		int id = keys.getInt(1);

		usuario.setId(id);
		close();
	}
	
	public boolean sendEmail(String recipient) throws SQLException  {
	    // Genera una contrase�a aleatoria de 8 caracteres
	    String newPassword = generarPass(8);

	    // Aqu� puedes agregar l�gica para actualizar la contrase�a del usuario en tu base de datos
	    // Por ejemplo, si est�s utilizando una clase UsuarioDAO, podr�as llamar a un m�todo como:
	    // usuarioDAO.updatePassword(recipient, newPassword);
	    boolean exito = newPass(newPassword, recipient);
	    
	    if(exito)
	    {
	    	// Configura las propiedades del servidor SMTP simulado (FakeSMTP)
		    Properties properties = new Properties();
		    properties.setProperty("mail.smtp.host", "localhost"); // Direcci�n del servidor FakeSMTP
		    properties.setProperty("mail.smtp.port", "25"); // Puerto predeterminado de FakeSMTP

		    // Crea una sesi�n de correo
		    Session session = Session.getDefaultInstance(properties);

		    try {
		        // Crea un mensaje de correo
		        MimeMessage message = new MimeMessage(session);
		        message.setFrom(new InternetAddress("ejemplodependiente@gmail.com")); // Remitente (cambia por tu direcci�n de correo)
		        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
		        message.setSubject("Contrase�a temporal generada");
		        message.setText("Tu nueva contrase�a es: " + newPassword);

		        // Env�a el mensaje
		        Transport.send(message);

		        System.out.println("Correo enviado exitosamente a: " + recipient);

		        // Si todo fue exitoso, retornamos true
		        return true;
		    } catch (MessagingException e) {
		        e.printStackTrace();
		        System.err.println("Error al enviar el correo: " + e.getMessage());

		        // Si ocurri� un error, retornamos false
		        return false;
		    }
	    }else {
	    	return false;
	    }
	}

	public static String generarPass(int length) {
	    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    StringBuilder password = new StringBuilder();

	    for (int i = 0; i < length; i++) {
	        int index = (int) (Math.random() * characters.length());
	        password.append(characters.charAt(index));
	    }

	    return password.toString();
	}
	
	public boolean newPass(String password, String correo) throws SQLException {
	    boolean success = false;

	    connect();

	    // Primero, verifica si existe el usuario con ese correo
	    pstmt = conn.prepareStatement(
	            "SELECT * FROM usuario WHERE Correo_Usuario = ?");
	    pstmt.setString(1, correo);
	    ResultSet rs = pstmt.executeQuery();

	    if (rs.next()) {
	        // La consulta devolvi� resultados, lo que significa que el usuario existe
	        // Ahora, procedemos a cambiar la contrase�a
	        pstmt = conn.prepareStatement(
	                "UPDATE usuario SET Contrasena_Usuario = ? WHERE Correo_Usuario = ?");
	        pstmt.setString(1, password);
	        pstmt.setString(2, correo);

	        int rowsAffected = pstmt.executeUpdate();

	        if (rowsAffected > 0) {
	            success = true; // Se actualiz� al menos una fila, por lo tanto, consideramos la operaci�n exitosa.
	        }
	    }
	    return success;
	}
	
	public boolean updatePass(String anterior, String password, String correo) throws SQLException {
	    boolean success = false;

	    connect();

	    // Primero, verifica si la contrase�a anterior coincide con la contrase�a actual del usuario
	    pstmt = conn.prepareStatement(
	            "SELECT * FROM usuario WHERE Correo_Usuario = ? AND Contrasena_Usuario = ?");
	    pstmt.setString(1, correo);
	    pstmt.setString(2, anterior);
	    ResultSet rs = pstmt.executeQuery();

	    if (rs.next()) {
	        // La consulta devolvi� resultados, lo que significa que la contrase�a anterior es correcta
	        // Ahora, procedemos a cambiar la contrase�a
	        pstmt = conn.prepareStatement(
	                "UPDATE usuario SET Contrasena_Usuario = ? WHERE Correo_Usuario = ?");
	        pstmt.setString(1, password);
	        pstmt.setString(2, correo);

	        int rowsAffected = pstmt.executeUpdate();

	        if (rowsAffected > 0) {
	            success = true; // Se actualiz� al menos una fila, por lo tanto, consideramos la operaci�n exitosa.
	        }
	    }

	    close();

	    return success;
	}

	public void update(Usuario usuario) throws SQLException {
		connect();
		pstmt = conn.prepareStatement(
				"update usuario set Nombre_Usuario = ?, Apellido_Usuario = ?, Telefono_Usuario = ?, Correo_Usuario = ?, DUI_Usuario = ?, Contrasena_Usuario = ?, Categoria_Usuario = ? where Id_Usuario = ?");
		pstmt.setString(1, usuario.getNombre());
		pstmt.setString(2, usuario.getApellido());
		pstmt.setInt(3, usuario.getTelefono());
		pstmt.setString(4, usuario.getCorreo());
		pstmt.setInt(5, usuario.getDui());
		pstmt.setString(6, usuario.getPassword());
		pstmt.setInt(7, usuario.getCategoria());
		pstmt.setInt(8, usuario.getId());
		pstmt.executeUpdate();
		close();
	}

	public ResultadoLogin login(String correo, String contrasena) throws SQLException {
		connect();
		ResultSet rs = null;
		boolean autenticado = false;
		String nombreApellido = null;

		try {
			pstmt = conn.prepareStatement(
					"SELECT Nombre_Usuario, Apellido_Usuario FROM usuario WHERE Correo_Usuario = ? AND Contrasena_Usuario = ? AND Categoria_Usuario = 4");
			pstmt.setString(1, correo);
			pstmt.setString(2, contrasena);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// Las credenciales son correctas
				autenticado = true;
				nombreApellido = rs.getString("Nombre_Usuario") + " " + rs.getString("Apellido_Usuario");
			}
		} finally {
			// Cierra recursos
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return new ResultadoLogin(autenticado, nombreApellido);
	}

	public void delete(int id) throws SQLException {
		connect();
		pstmt = conn.prepareStatement("delete from usuario where Id_Usuario = ?");
		pstmt.setInt(1, id);
		pstmt.execute();
		close();
	}

	public ArrayList<Usuario> findAll() throws SQLException {
		connect();
		stmt = conn.createStatement();
		resultSet = stmt.executeQuery(
				"select Id_Usuario,Nombre_Usuario,Apellido_Usuario,Telefono_Usuario,Correo_Usuario,DUI_Usuario,Contrasena_Usuario,Categoria_Usuario from usuario");
		ArrayList<Usuario> usuarios = new ArrayList();

		while (resultSet.next()) {
			Usuario tmp = new Usuario();
			tmp.setId(resultSet.getInt(1));
			tmp.setNombre(resultSet.getString(2));
			tmp.setApellido(resultSet.getString(3));
			tmp.setTelefono(resultSet.getInt(4));
			tmp.setCorreo(resultSet.getString(5));
			tmp.setDui(resultSet.getInt(6));
			tmp.setPassword(resultSet.getString(7));
			tmp.setCategoria(resultSet.getInt(8));
			usuarios.add(tmp);
		}

		close();

		return usuarios;
	}

	public Usuario findById(int id) throws SQLException {
		Usuario usuario = null;
		connect();
		pstmt = conn.prepareStatement(
				"select Id_Usuario,Nombre_Usuario,Apellido_Usuario,Telefono_Usuario,Correo_Usuario,DUI_Usuario,Contrasena_Usuario,Categoria_Usuario from usuario where Id_Usuario = ?");
		pstmt.setInt(1, id);

		resultSet = pstmt.executeQuery();

		while (resultSet.next()) {
			usuario = new Usuario();
			usuario.setId(resultSet.getInt(1));
			usuario.setNombre(resultSet.getString(2));
			usuario.setApellido(resultSet.getString(3));
			usuario.setTelefono(resultSet.getInt(4));
			usuario.setCorreo(resultSet.getString(5));
			usuario.setDui(resultSet.getInt(6));
			usuario.setPassword(resultSet.getString(7));
			usuario.setCategoria(resultSet.getInt(8));
		}

		close();
		return usuario;
	}
}