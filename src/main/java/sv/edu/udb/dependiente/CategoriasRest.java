package sv.edu.udb.dependiente;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sv.edu.udb.model.Categoria;
import sv.edu.udb.model.CategoriasDAO;
@Path("categorias")
public class CategoriasRest {

 @GET
 @Produces(MediaType.APPLICATION_JSON)
 public Response getCategorias() throws SQLException{
 CategoriasDAO categoriasDAO = new CategoriasDAO();
 List<Categoria> categorias = categoriasDAO.findAll();

 return Response.status(200)
		 .header("Access-Control-Allow-Origin", "*")
		 .entity(categorias)
		 .build();
 }
 @GET
 @Path("{id}")
 @Produces(MediaType.APPLICATION_JSON)
 public Response getByCategoriasId(@PathParam("id") int id) throws
SQLException{
 CategoriasDAO categoriasDAO = new CategoriasDAO();
 Categoria categoria = categoriasDAO.findById(id);

 if(categoria == null){
 return Response.status(404).build();
 }

 return Response.status(200)
		 .header("Access-Control-Allow-Origin", "*")
		 .entity(categoria)
		 .build();
 }
}