package sv.edu.udb.dependiente;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import sv.edu.udb.beans.ResultadoLogin;
import sv.edu.udb.beans.Usuario;
import sv.edu.udb.model.CategoriasDAO;
import sv.edu.udb.model.UsuariosDAO;

@Path("usuarios")
public class UsuariosRest {

	UsuariosDAO usuariosDAO = new UsuariosDAO();
	CategoriasDAO categoriasDAO = new CategoriasDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConceptos() throws SQLException {

		List<Usuario> usuarios = usuariosDAO.findAll();
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(usuarios).build();
	}

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

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertUsuario(@FormParam("nombre") String nombre, @FormParam("apellido") String apellido,
			@FormParam("telefono") int telefono, @FormParam("correo") String correo, @FormParam("dui") int dui,
			@FormParam("password") String password, @FormParam("categoria") int categoria_id) throws SQLException {

		Usuario usuario = new Usuario();

		if (categoriasDAO.findById(categoria_id) == null) {
			return Response.status(400).header("Access-Control-Allow-Origin", "*")
					.entity("Categoria no corresponde a ninguna existencia").build();
		}

		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setTelefono(telefono);
		usuario.setCorreo(correo);
		usuario.setDui(dui);
		usuario.setPassword(password);
		usuario.setCategoria(categoria_id);
		usuariosDAO.insert(usuario);

		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(usuario).build();
	}
	
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

	// @DELETE
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("delete/{id}")
	public Response eliminarUsuario(@PathParam("id") int id) throws SQLException {
		Usuario usuario = usuariosDAO.findById(id);
		if (usuario == null) {
			return Response.status(400).entity("Usuario no corresponde a ninguna existencia")
					.header("Access-Control-Allow-Origin", "*").build();
		}

		usuariosDAO.delete(id);

		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(usuario).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateUsuario(@PathParam("id") int id, @FormParam("nombre") String nombre,
			@FormParam("apellido") String apellido, @FormParam("telefono") int telefono,
			@FormParam("correo") String correo, @FormParam("dui") int dui, @FormParam("password") String password,
			@FormParam("categoria") int categoria_id) throws SQLException {

		Usuario usuario = usuariosDAO.findById(id);

		if (usuario == null) {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.entity("Usuario no corresponde a ninguna existencia").build();
		}

		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setTelefono(telefono);
		usuario.setCorreo(correo);
		usuario.setDui(dui);
		usuario.setPassword(password);
		usuario.setCategoria(categoria_id);
		usuariosDAO.update(usuario);

		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(usuario).build();
	}
}