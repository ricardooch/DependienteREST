package sv.edu.udb.dependiente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
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

import sv.edu.udb.beans.Boleto;
import sv.edu.udb.beans.BoletoCompuesto;
import sv.edu.udb.beans.Usuario;
import sv.edu.udb.model.BoletosDAO;
import sv.edu.udb.model.CategoriasDAO;
import sv.edu.udb.model.UsuariosDAO;

@Path("boletos")
public class boletosRest {

	BoletosDAO boletosDAO = new BoletosDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBoletos() throws SQLException {

		List<Boleto> boletos = boletosDAO.findAll();
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(boletos).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateBoleto(@PathParam("id") int id) throws SQLException {

		Boleto boleto = boletosDAO.findSimpleById(id);

		if (boleto == null) {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.entity("Boleto no corresponde a ninguna existencia").build();
		}
		boleto.setEstado("canjeado");

		boletosDAO.updateEstado(boleto);

		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("¡Boleto canjeado!").build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("consultar")
	public Response consultarBoleto(@FormParam("codigo") String codigo, @FormParam("dui") String dui)
			throws SQLException {
		// Consultar el boleto
		BoletoCompuesto boleto = boletosDAO.consultarBoleto(codigo, dui);

		if (boleto == null) {
			return Response.status(404).header("Access-Control-Allow-Origin", "*")
					.entity("Boleto no corresponde a ninguna existencia").build();
		}

		// Validar el estado del boleto
		if ("canjeado".equalsIgnoreCase(boleto.getEstado())) {
			return Response.status(400) // Código 400 para indicar un error de solicitud inválida
					.header("Access-Control-Allow-Origin", "*").entity("El boleto ya ha sido canjeado").build();
		}

		// Obtener la fecha actual
		LocalDate fechaActual = LocalDate.now();
		Date fechaLimiteDate = boleto.getFechaLimite();
		Date fechaFinalDate = boleto.getFechaFinal();

		// Validar la fecha límite
		LocalDate fechaLimite = LocalDate.parse(fechaLimiteDate.toString());
		if (fechaLimite.isBefore(fechaActual)) {
			return Response.status(400).header("Access-Control-Allow-Origin", "*")
					.entity("La fecha límite para canjear el boleto ha pasado").build();
		}

		// Validar la fecha final
		LocalDate fechaFinal = LocalDate.parse(fechaFinalDate.toString());
		if (fechaFinal.isBefore(fechaActual)) {
			return Response.status(400).header("Access-Control-Allow-Origin", "*")
					.entity("La fecha final del boleto ha pasado").build();
		}

		// Si todas las validaciones pasan, retornar el boleto
		return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(boleto).build();
	}
}