package sv.edu.udb.model;
import java.sql.SQLException;
import java.util.ArrayList;

import sv.edu.udb.beans.Oferta;
public class OfertasDAO extends AppConnection{

public void update(Oferta oferta) throws SQLException{
	connect();
	pstmt = conn.prepareStatement("update ofertas_empresa set Titulo_Oferta = ?, Precio_normal_Oferta = ?, Precio_ofertado_Oferta = ?, Fecha_inicio_Oferta = ?, Fecha_final_Oferta = ?, Fecha_limite_Oferta = ?, Cantidad_limite_Oferta = ?, Descripcion_Oferta = ?, Detalles_Oferta = ?, Estado_Oferta = ?, Empresa_Oferta = ? where id_Empresa_Oferta = ?");
	pstmt.setString(1, oferta.getTitulo());
	pstmt.setDouble(2, oferta.getPrecioNormal());
	pstmt.setDouble(3, oferta.getPrecioOfertado());
	pstmt.setDate(4, oferta.getFechaInicio());
	pstmt.setDate(5, oferta.getFechaFinal());
	pstmt.setDate(6, oferta.getFechaLimite());
	pstmt.setInt(7, oferta.getCantidadLimite());
	pstmt.setString(8, oferta.getDescripcion());
	pstmt.setString(9, oferta.getDetalles());
	pstmt.setInt(10, oferta.getEstado());
	pstmt.setString(11, oferta.getEmpresa());
	pstmt.setInt(12, oferta.getId());
	pstmt.execute();
	close();
}

public void delete(int id) throws SQLException{
connect();
pstmt = conn.prepareStatement("delete from ofertas_empresa where id_Empresa_Oferta = ?");
pstmt.setInt(1, id);
pstmt.execute();
close();
}

public ArrayList<Oferta> findAll() throws SQLException{
connect();
stmt = conn.createStatement();
resultSet = stmt.executeQuery("select id_Empresa_Oferta, Titulo_Oferta, Precio_normal_Oferta, Precio_ofertado_Oferta, Fecha_inicio_Oferta, Fecha_final_Oferta, Fecha_limite_Oferta, Cantidad_limite_Oferta, Descripcion_Oferta, Detalles_Oferta, Estado_Oferta, Empresa_Oferta from ofertas_empresa");
ArrayList<Oferta> ofertas = new ArrayList();

while(resultSet.next()){
Oferta tmp = new Oferta();
tmp.setId(resultSet.getInt(1));
tmp.setTitulo(resultSet.getString(2));
tmp.setPrecioNormal(resultSet.getDouble(3));
tmp.setPrecioOfertado(resultSet.getDouble(4));
tmp.setFechaInicio(resultSet.getDate(5));
tmp.setFechaFinal(resultSet.getDate(6));
tmp.setFechaLimite(resultSet.getDate(7));
tmp.setCantidadLimite(resultSet.getInt(8));
tmp.setDescripcion(resultSet.getString(9));
tmp.setDetalles(resultSet.getString(10));
tmp.setEstado(resultSet.getInt(11));
tmp.setEmpresa(resultSet.getString(12));

ofertas.add(tmp);
}

close();

return ofertas;
}

public Oferta findById(int id) throws SQLException{

Oferta oferta = null;

connect();
pstmt = conn.prepareStatement("select id_Empresa_Oferta, Titulo_Oferta, Precio_normal_Oferta, Precio_ofertado_Oferta, Fecha_inicio_Oferta, Fecha_final_Oferta, Fecha_limite_Oferta, Cantidad_limite_Oferta, Descripcion_Oferta, Detalles_Oferta, Estado_Oferta, Empresa_Oferta from ofertas_empresa where id_Empresa_Oferta = ?");
pstmt.setInt(1, id);
resultSet = pstmt.executeQuery();

while(resultSet.next()){
oferta = new Oferta();
oferta.setId(resultSet.getInt(1));
oferta.setTitulo(resultSet.getString(2));
oferta.setPrecioNormal(resultSet.getDouble(3));
oferta.setPrecioOfertado(resultSet.getDouble(4));
oferta.setFechaInicio(resultSet.getDate(5));
oferta.setFechaFinal(resultSet.getDate(6));
oferta.setFechaLimite(resultSet.getDate(7));
oferta.setCantidadLimite(resultSet.getInt(8));
oferta.setDescripcion(resultSet.getString(9));
oferta.setDetalles(resultSet.getString(10));
oferta.setEstado(resultSet.getInt(11));
oferta.setEmpresa(resultSet.getString(12));
}

close();
return oferta;
}
}
