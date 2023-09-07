package sv.edu.udb.model;

import java.sql.SQLException;
import java.util.ArrayList;
import sv.edu.udb.beans.Boleto;
import sv.edu.udb.beans.BoletoCompuesto;

public class BoletosDAO extends AppConnection {

	public void insert(Boleto boleto) throws SQLException {
		connect();
		pstmt = conn.prepareStatement(
				"insert into boletos oferta_boleto, codigo_boleto, estado_boleto, cliente_boleto, empresa_boleto values(?,?,?,?,?)");
		pstmt.setInt(1, boleto.getOferta());
		pstmt.setString(2, boleto.getCodigo());
		pstmt.setString(3, boleto.getEstado());
		pstmt.setInt(4, boleto.getCliente());
		pstmt.setString(5, boleto.getEmpresa());
		pstmt.execute();
		close();
	}

	public void updateEstado(Boleto boleto) throws SQLException {
		connect();
		pstmt = conn.prepareStatement("update boletos set estado_boleto = ? where id_boleto = ?");
		pstmt.setString(1, boleto.getEstado());
		pstmt.setInt(2, boleto.getId());
		pstmt.execute();
		close();
	}

	public void consultar(Boleto boleto) throws SQLException {
		connect();
		pstmt = conn.prepareStatement(
				"select * from boletos set oferta_boleto = ?, codigo_boleto = ?, estado_boleto = ?, cliente_boleto = ?, empresa_boleto = ? where id_boleto = ?");
		pstmt.setInt(1, boleto.getOferta());
		pstmt.setString(2, boleto.getCodigo());
		pstmt.setString(3, boleto.getEstado());
		pstmt.setInt(4, boleto.getCliente());
		pstmt.setString(5, boleto.getEmpresa());
		pstmt.setInt(6, boleto.getId());
		pstmt.execute();
		close();
	}

	public void delete(int id) throws SQLException {
		connect();
		pstmt = conn.prepareStatement("delete from boletos where id_boleto = ?");
		pstmt.setInt(1, id);
		pstmt.execute();
		close();
	}

	public ArrayList<Boleto> findAll() throws SQLException {
		connect();
		stmt = conn.createStatement();
		resultSet = stmt.executeQuery(
				"select id_boleto, oferta_boleto, codigo_boleto, estado_boleto, cliente_boleto, empresa_boleto from boletos");
		ArrayList<Boleto> boletos = new ArrayList();

		while (resultSet.next()) {
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

	public Boleto findSimpleById(int id) throws SQLException {

		Boleto boleto = null;

		connect();
		pstmt = conn.prepareStatement(
				"select id_boleto, oferta_boleto, codigo_boleto, estado_boleto, cliente_boleto, empresa_boleto from boletos where id_boleto = ?");
		pstmt.setInt(1, id);
		resultSet = pstmt.executeQuery();

		while (resultSet.next()) {
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

	public BoletoCompuesto consultarBoleto(String codigo, String dui) throws SQLException {
		BoletoCompuesto boleto = null;
		connect();

		pstmt = conn.prepareStatement(
				"SELECT b.id_boleto, b.oferta_boleto, b.codigo_boleto, b.estado_boleto, u.Nombre_Usuario AS nombre_cliente, u.Apellido_Usuario AS apellido_cliente, u.DUI_Usuario, "
						+ "oe.Titulo_Oferta AS titulo_oferta, oe.Precio_normal_Oferta, oe.Precio_ofertado_Oferta, oe.Fecha_inicio_Oferta, oe.Fecha_final_Oferta, oe.Fecha_limite_Oferta, oe.Estado_Oferta,"
						+ " e.nombre_empresa FROM boletos b INNER JOIN usuario u ON b.cliente_boleto = u.id_usuario INNER JOIN ofertas_empresa oe ON b.oferta_boleto = oe.id_Empresa_Oferta "
						+ "INNER JOIN empresa e ON b.empresa_boleto = e.codigo_empresa WHERE b.codigo_boleto = ? AND u.DUI_Usuario = ?;");
		pstmt.setString(1, codigo);
		pstmt.setString(2, dui);
		resultSet = pstmt.executeQuery();

		while (resultSet.next()) {

			boleto = new BoletoCompuesto();

			boleto.setId(resultSet.getInt(1));
			boleto.setOferta(resultSet.getInt(2));
			boleto.setCodigo(resultSet.getString(3));
			boleto.setEstado(resultSet.getString(4));
			boleto.setNombreCliente(resultSet.getString(5));
			boleto.setApellidoCliente(resultSet.getString(6));
			boleto.setDuiCliente(resultSet.getString(7));
			boleto.setTituloOferta(resultSet.getString(8));
			boleto.setPrecioNormal(resultSet.getDouble(9));
			boleto.setPrecioOfertado(resultSet.getDouble(10));
			boleto.setFechaInicio(resultSet.getDate(11));
			boleto.setFechaFinal(resultSet.getDate(12));
			boleto.setFechaLimite(resultSet.getDate(13));
			boleto.setEstadoOferta(resultSet.getInt(14));
			boleto.setNombreEmpresa(resultSet.getString(15));

		}

		close();
		return boleto;
	}
}
