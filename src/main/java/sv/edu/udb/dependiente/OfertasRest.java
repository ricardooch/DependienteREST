package sv.edu.udb.dependiente;
import java.sql.Date;
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

import sv.edu.udb.model.Oferta;
import sv.edu.udb.model.OfertasDAO;

@Path("ofertas")
public class OfertasRest {

 OfertasDAO ofertasDAO = new OfertasDAO();

 @GET
 @Produces(MediaType.APPLICATION_JSON)
 public Response getBoletos() throws SQLException{

 List<Oferta> ofertas = ofertasDAO.findAll();
 return Response.status(200)
		 .header("Access-Control-Allow-Origin", "*")
		 .entity(ofertas)
		 .build();
 }

 @GET
 @Produces(MediaType.APPLICATION_JSON)
 @Path("{id}")
 public Response getOfertaById(@PathParam("id") int id) throws SQLException{

 Oferta oferta = ofertasDAO.findById(id);
 if(oferta == null){
 return Response.status(404)
 .header("Access-Control-Allow-Origin", "*")
 .entity("Oferta no encontrada").build();
 }
 return Response.status(200)
 .header("Access-Control-Allow-Origin", "*")
 .entity(oferta)
 .build();
 }

 @POST
 @Produces(MediaType.APPLICATION_JSON)
 @Path("{id}")
 public Response updateOferta(
 @PathParam("id") int id,
 @FormParam("titulo") String titulo,
 @FormParam("precioNormal") double precioNormal,
 @FormParam("precioOfertado") double precioOfertado,
 @FormParam("fechaInicio") Date fechaInicio,
 @FormParam("fechaFinal") Date fechaFinal,
 @FormParam("fechaLimite") Date fechaLimite,
 @FormParam("cantidadLimite") int cantidadLimite,
 @FormParam("descripcion") String descripcion,
 @FormParam("detalles") String detalles,
 @FormParam("estado") int estado,
 @FormParam("empresa") String empresa
 ) throws SQLException{

 Oferta oferta = ofertasDAO.findById(id);

 if(oferta == null){
 return Response.status(404)
 .header("Access-Control-Allow-Origin", "*")
 .entity("Oferta no corresponde a ninguna existencia")
.build();
 }

 oferta.setTitulo(titulo);
 oferta.setPrecioNormal(precioNormal);
 oferta.setPrecioOfertado(precioOfertado);
 oferta.setFechaInicio(fechaInicio);
 oferta.setFechaFinal(fechaFinal);
 oferta.setFechaLimite(fechaLimite);
 oferta.setCantidadLimite(cantidadLimite);
 oferta.setDescripcion(descripcion);
 oferta.setDetalles(detalles);
 oferta.setEstado(estado);
 oferta.setEmpresa(empresa);
 
 ofertasDAO.update(oferta);

 return Response.status(200)
 .header("Access-Control-Allow-Origin", "*")
 .entity(oferta)
 .build();
 }
}