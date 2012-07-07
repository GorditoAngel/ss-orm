import java.sql.*;
import java.io.*;
import java.util.*;

public class TelefoneDAO {

	private Connection conexao;

	public TelefoneDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public boolean insert(Telefone telefone) {

		PreparedStatement stmt = conexao.prepareStatement("insert into Telefone (id,id_cliente,numero) values (?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
		stmt.setInteger(1, telefone.getId());
		stmt.setInteger(2, telefone.getId_cliente());
		stmt.setString(3, telefone.getNumero());
		int linhas = stmt.executeUpdate();

		ResultSet rs = stmt.getGeneratedKeys();
		if(rs != null && rs.next()) {
			telefone.setId(rs.getInteger(1));
		}

		rs.close();
		stmt.close();
		return linhas > 0;

	}

	public boolean update(Telefone telefone) {

		PreparedStatement stmt = conexao.prepareStatement("update Telefone set id_cliente = ?, numero = ? where id = ? ");
		stmt.setInteger(1, telefone.getId_cliente());
		stmt.setString(2, telefone.getNumero());
		stmt.setInteger(3, telefone.getId());
		int linhas = stmt.executeUpdate();

		stmt.close();
		return linhas > 0;

	}

	public boolean delete(Telefone telefone) {

		PreparedStatement stmt = conexao.prepareStatement("delete from Telefone where id = ? ");
		stmt.setInteger(1, telefone.getId());
		int linhas = stmt.executeUpdate();

		stmt.close();
		return linhas > 0;

	}

	public List<Telefone> listAll() {

		PreparedStatement stmt = conexao.prepareStatement("select * from Telefone ");
		ResultSet rs = stmt.executeQuery();

		while(rs.next()) {
			Telefone telefone = new Telefone();
			telefone.setId(rs.getInteger(1));
			telefone.setId_cliente(rs.getInteger(2));
			telefone.setNumero(rs.getString(3));
		}

		rs.close();
		stmt.close();
		return linhas > 0;

	}

	public Telefone getById(Integer id) {

		PreparedStatement stmt = conexao.prepareStatement("select * from Telefone where id = ?");
		stmt.setInteger(1, telefone.getId());
		ResultSet rs = stmt.executeQuery();

		Telefone telefone = null;
		if(rs.next()) {
			telefone = new Telefone();
			telefone.setId(rs.getInteger(1));
			telefone.setId_cliente(rs.getInteger(2));
			telefone.setNumero(rs.getString(3));
		}

		rs.close();
		stmt.close();
		return linhas > 0;

	}
}