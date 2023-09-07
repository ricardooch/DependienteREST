package sv.edu.udb.model;
import java.sql.SQLException;
import java.util.ArrayList;

import sv.edu.udb.beans.Categoria;
public class CategoriasDAO extends AppConnection{

 public void insert(Categoria categoria) throws SQLException{
 connect();
 pstmt = conn.prepareStatement("insert into usuario_categoria (Nombre_Categoria) values(?)");
 pstmt.setString(1, categoria.getName());
 pstmt.execute();
 close();
 }

 public void update(Categoria categoria) throws SQLException{
 connect();
 pstmt = conn.prepareStatement("update usuario_categoria set Nombre_Categoria = ? where Id_Categoria = ?");
		pstmt.setString(1, categoria.getName());
pstmt.execute();
close();
}

public void delete(int id) throws SQLException{
connect();
pstmt = conn.prepareStatement("delete from usuario_categoria where id = ?");
pstmt.setInt(1, id);
pstmt.execute();
close();
}

public ArrayList<Categoria> findAll() throws SQLException{
connect();
stmt = conn.createStatement();
resultSet = stmt.executeQuery("select Id_Categoria, Nombre_Categoria from usuario_categoria");
ArrayList<Categoria> categorias = new ArrayList();

while(resultSet.next()){
Categoria tmp = new Categoria();
tmp.setId(resultSet.getInt(1));
tmp.setName(resultSet.getString(2));

categorias.add(tmp);
}

close();

return categorias;
}

public Categoria findById(int id) throws SQLException{

Categoria categoria = null;

connect();
pstmt = conn.prepareStatement("select Id_Categoria, Nombre_Categoria from usuario_categoria where Id_Categoria = ?");
pstmt.setInt(1, id);
resultSet = pstmt.executeQuery();

while(resultSet.next()){
categoria = new Categoria();
categoria.setId(resultSet.getInt(1));
categoria.setName(resultSet.getString(2));
}

close();
return categoria;
}
}
