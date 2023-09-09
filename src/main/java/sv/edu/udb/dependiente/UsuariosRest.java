package sv.edu.udb.dependiente;

import java.sql.SQLException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sv.edu.udb.beans.ResultadoLogin;
import sv.edu.udb.beans.Usuario;
import sv.edu.udb.model.UsuariosDAO;

@Path("usuarios")
public class UsuariosRest {

	UsuariosDAO usuariosDAO = new UsuariosDAO();

	// Consultar usuario por ID
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getConceptosById(@PathParam("id") int id) throws SQLException {

		Usuario usuario = usuariosDAO.findById(id);
		if (usuario == null) {
			return Response.status(404).header("Access-Control-Allow-Origin", "*").entity("Usuario no encontrado")
					.build();
		}
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(usuario).build();
	}
	
	// Modificar contraseña
	@POST
	@Path("/updatePass")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePass(@FormParam("anterior") String anterior, @FormParam("correo") String correo, @FormParam("password") String password) {
	    try {
	        boolean resultado = usuariosDAO.updatePass(anterior, password, correo);

	        if (resultado) {
	            return Response.status(200)
	                .header("Access-Control-Allow-Origin", "*")
	                .entity("Contraseña cambiada exitosamente!")
	                .build();
	        } else {
	            return Response.status(401)
	                .header("Access-Control-Allow-Origin", "*")
	                .entity("La contraseña anterior es incorrecta o no se pudo cambiar la contraseña.")
	                .build();
	        }
	    } catch (SQLException e) {
	        // Manejo de excepciones en caso de error en la base de datos
	        return Response.status(500)
	            .header("Access-Control-Allow-Origin", "*")
	            .entity("Error en la base de datos")
	            .build();
	    }
	}

	// Validar credenciales en el login
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUsuario(@FormParam("correo") String correo, @FormParam("password") String password) {
		try {
			ResultadoLogin resultado = usuariosDAO.login(correo, password);

			if (resultado.isAutenticado()) {
				String nombreApellido = resultado.getNombreApellido();
				return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(nombreApellido).build();
			} else {
				// Las credenciales son incorrectas
				return Response.status(401).header("Access-Control-Allow-Origin", "*")
						.entity("Inicio de sesión fallido: Credenciales incorrectas").build();
			}
		} catch (SQLException e) {
			// Manejo de excepciones en caso de error en la base de datos
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("Error en la base de datos")
					.build();
		}
	}
	
	// Recuperar contraseña por correo
	@POST
    @Path("/newPass")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePassword(@FormParam("correo") String correo) {
        try {
            boolean resultado = usuariosDAO.sendEmail(correo);

            if (resultado) {
                return Response.status(200)
                        .header("Access-Control-Allow-Origin", "*")
                        .entity("Email con nueva contraseña enviada correctamente!")
                        .build();
            } else {
                return Response.status(401)
                        .header("Access-Control-Allow-Origin", "*")
                        .entity("Ocurrió un error y no se pudo cambiar la contraseña")
                        .build();
            }
        } catch (SQLException e) {
            // Manejo de excepciones en caso de error en la base de datos
            return Response.status(500)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Error en la base de datos")
                    .build();
        }
    }
}