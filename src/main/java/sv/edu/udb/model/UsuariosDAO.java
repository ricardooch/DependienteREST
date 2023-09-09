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
	
	// Función para enviar email al recuperar contraseña
	public boolean sendEmail(String recipient) throws SQLException  {
		
	    // Genera una contraseña aleatoria de 8 caracteres
	    String newPassword = generarPass(8);
	    
	    // Verifica si el correo ingresado pertenece a un usuario dependiente de sucursal
	    // y si existe procede a cambiarle la contraseña
	    boolean exito = newPass(newPassword, recipient);
	    
	    if(exito)
	    {
	    	// Configura las propiedades del servidor SMTP simulado (FakeSMTP)
		    Properties properties = new Properties();
		    properties.setProperty("mail.smtp.host", "localhost"); // Dirección del servidor FakeSMTP
		    properties.setProperty("mail.smtp.port", "25"); // Puerto predeterminado de FakeSMTP

		    // Crea una sesión de correo
		    Session session = Session.getDefaultInstance(properties);

		    try {
		        // Crea un mensaje de correo
		        MimeMessage message = new MimeMessage(session);
		        message.setFrom(new InternetAddress("ejemplodependiente@gmail.com")); // Remitente (cambia por tu dirección de correo)
		        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
		        message.setSubject("Contraseña temporal generada");
		        message.setText("Tu nueva contraseña es: " + newPassword);

		        // Envía el mensaje
		        Transport.send(message);

		        System.out.println("Correo enviado exitosamente a: " + recipient);

		        // Si todo fue exitoso, retornamos true
		        return true;
		    } catch (MessagingException e) {
		        e.printStackTrace();
		        System.err.println("Error al enviar el correo: " + e.getMessage());

		        // Si ocurrió un error, retornamos false
		        return false;
		    }
	    }else {
	    	return false;
	    }
	}

	// Función para generar una contraseña aleatoria
	public static String generarPass(int length) {
	    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    StringBuilder password = new StringBuilder();

	    for (int i = 0; i < length; i++) {
	        int index = (int) (Math.random() * characters.length());
	        password.append(characters.charAt(index));
	    }

	    return password.toString();
	}
	
	// Función para verificar si existe el usuario y cambiarle la contraseña
	public boolean newPass(String password, String correo) throws SQLException {
	    boolean success = false;

	    connect();

	    // Primero, verifica si existe el usuario con ese correo
	    pstmt = conn.prepareStatement(
	            "SELECT * FROM usuario WHERE Correo_Usuario = ?");
	    pstmt.setString(1, correo);
	    ResultSet rs = pstmt.executeQuery();

	    if (rs.next()) {
	        // La consulta devolvió resultados, lo que significa que el usuario existe
	        // Ahora, procedemos a cambiar la contraseña
	        pstmt = conn.prepareStatement(
	                "UPDATE usuario SET Contrasena_Usuario = ? WHERE Correo_Usuario = ?");
	        pstmt.setString(1, password);
	        pstmt.setString(2, correo);

	        int rowsAffected = pstmt.executeUpdate();

	        if (rowsAffected > 0) {
	            success = true; // Se actualizó al menos una fila, por lo tanto, consideramos la operación exitosa.
	        }
	    }
	    return success;
	}

	// Modificar contraseña dentro del sistema
	public boolean updatePass(String anterior, String password, String correo) throws SQLException {
	    boolean success = false;

	    connect();

	    // Primero, verifica si la contraseña anterior coincide con la contraseña actual del usuario
	    pstmt = conn.prepareStatement(
	            "SELECT * FROM usuario WHERE Correo_Usuario = ? AND Contrasena_Usuario = ?");
	    pstmt.setString(1, correo);
	    pstmt.setString(2, anterior);
	    ResultSet rs = pstmt.executeQuery();

	    if (rs.next()) {
	        // La consulta devolvió resultados, lo que significa que la contraseña anterior es correcta
	        // Ahora, procedemos a cambiar la contraseña
	        pstmt = conn.prepareStatement(
	                "UPDATE usuario SET Contrasena_Usuario = ? WHERE Correo_Usuario = ?");
	        pstmt.setString(1, password);
	        pstmt.setString(2, correo);

	        int rowsAffected = pstmt.executeUpdate();

	        if (rowsAffected > 0) {
	            success = true; // Se actualizó al menos una fila, por lo tanto, consideramos la operación exitosa.
	        }
	    }

	    close();

	    return success;
	}

	// Verifica las credenciales ingresadas en el login
	public ResultadoLogin login(String correo, String contrasena) throws SQLException {
		connect();
		ResultSet rs = null;
		boolean autenticado = false;
		String nombreApellido = null;

		try {
			pstmt = conn.prepareStatement("SELECT Nombre_Usuario, Apellido_Usuario FROM usuario WHERE Correo_Usuario = ? AND Contrasena_Usuario = ? AND Categoria_Usuario = 4");
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

	// Consultar información de usuario por ID
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