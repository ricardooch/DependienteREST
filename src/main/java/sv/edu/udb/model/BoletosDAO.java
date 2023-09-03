package sv.edu.udb.model;
import java.sql.SQLException;
import java.util.ArrayList;
public class BoletosDAO extends AppConnection{

 public void insert(Boleto boleto) throws SQLException{
	 connect();
	 pstmt = conn.prepareStatement("insert into boletos oferta_boleto, codigo_boleto, estado_boleto, cliente_boleto, empresa_boleto values(?,?,?,?,?)");
	 pstmt.setInt(1, boleto.getOferta());
	 pstmt.setString(2, boleto.getCodigo());
	 pstmt.setString(3, boleto.getEstado());
	 pstmt.setInt(4, boleto.getCliente());
	 pstmt.setString(5, boleto.getEmpresa());
	 pstmt.execute();
	 close();
 }

public void update(Boleto boleto) throws SQLException{
	connect();
	pstmt = conn.prepareStatement("update boletos set oferta_boleto = ?, codigo_boleto = ?, estado_boleto = ?, cliente_boleto = ?, empresa_boleto = ? where id_boleto = ?");
	pstmt.setInt(1, boleto.getOferta());
	pstmt.setString(2, boleto.getCodigo());
	pstmt.setString(3, boleto.getEstado());
	pstmt.setInt(4, boleto.getCliente());
	pstmt.setString(5, boleto.getEmpresa());
	pstmt.setInt(6, boleto.getId());
	pstmt.execute();
	close();
}

public void delete(int id) throws SQLException{
connect();
pstmt = conn.prepareStatement("delete from boletos where id_boleto = ?");
pstmt.setInt(1, id);
pstmt.execute();
close();
}

public ArrayList<Boleto> findAll() throws SQLException{
connect();
stmt = conn.createStatement();
resultSet = stmt.executeQuery("select id_boleto, oferta_boleto, codigo_boleto, estado_boleto, cliente_boleto, empresa_boleto from boletos");
ArrayList<Boleto> boletos = new ArrayList();

while(resultSet.next()){
Boleto tmp = new Boleto();
tmp.setId(resultSet.getInt(1));
tmp.setOferta(resultSet.getInt(2));
tmp.setCodigo(resultSet.getString(3));
tmp.setEstado(resultSet.getString(4));
tmp.setCliente(resultSet.getInt(5));
tmp.setEmpresa(resultSet.getString(6));

boletos.add(tmp);
}

close();

return boletos;
}

public Boleto findById(int id) throws SQLException{

Boleto boleto = null;

connect();
pstmt = conn.prepareStatement("select id_boleto, oferta_boleto, codigo_boleto, estado_boleto, cliente_boleto, empresa_boleto from boletos where id_boleto = ?");
pstmt.setInt(1, id);
resultSet = pstmt.executeQuery();

while(resultSet.next()){
boleto = new Boleto();
boleto.setId(resultSet.getInt(1));
boleto.setOferta(resultSet.getInt(2));
boleto.setCodigo(resultSet.getString(3));
boleto.setEstado(resultSet.getString(4));
boleto.setCliente(resultSet.getInt(5));
boleto.setEmpresa(resultSet.getString(6));
}

close();
return boleto;
}
}
