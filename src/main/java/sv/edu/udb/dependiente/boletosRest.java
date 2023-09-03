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

import sv.edu.udb.model.Boleto;
import sv.edu.udb.model.BoletosDAO;
import sv.edu.udb.model.CategoriasDAO;
import sv.edu.udb.model.Usuario;
import sv.edu.udb.model.UsuariosDAO;

@Path("boletos")
public class boletosRest {

 BoletosDAO boletosDAO = new BoletosDAO();

 @GET
 @Produces(MediaType.APPLICATION_JSON)
 public Response getBoletos() throws SQLException{

 List<Boleto> boletos = boletosDAO.findAll();
 return Response.status(200)
		 .header("Access-Control-Allow-Origin", "*")
		 .entity(boletos)
		 .build();
 }

 @GET
 @Produces(MediaType.APPLICATION_JSON)
 @Path("{id}")
 public Response getBoletoById(@PathParam("id") int id) throws SQLException{

 Boleto boleto = boletosDAO.findById(id);
 if(boleto == null){
 return Response.status(404)
 .header("Access-Control-Allow-Origin", "*")
 .entity("Boleto no encontrado").build();
 }
 return Response.status(200)
 .header("Access-Control-Allow-Origin", "*")
 .entity(boleto)
 .build();
 }

 @POST
 @Produces(MediaType.APPLICATION_JSON)
 @Path("{id}")
 public Response updateBoleto(
 @PathParam("id") int id,
 @FormParam("oferta") int oferta,
 @FormParam("codigo") String codigo,
 @FormParam("estado") String estado,
 @FormParam("cliente") int cliente,
 @FormParam("empresa") String empresa
 ) throws SQLException{

 Boleto boleto = boletosDAO.findById(id);

 if(boleto == null){
 return Response.status(404)
 .header("Access-Control-Allow-Origin", "*")
 .entity("Boleto no corresponde a ninguna existencia")
.build();
 }

 boleto.setOferta(oferta);
 boleto.setCodigo(codigo);
 boleto.setEstado(estado);
 boleto.setCliente(cliente);
 boleto.setEmpresa(empresa);
 
 boletosDAO.update(boleto);

 return Response.status(200)
 .header("Access-Control-Allow-Origin", "*")
 .entity(boleto)
 .build();
 }
}