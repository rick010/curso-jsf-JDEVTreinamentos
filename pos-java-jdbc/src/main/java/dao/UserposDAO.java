package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class UserposDAO {
	
	private Connection connection;
	
	public UserposDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(Userposjava userposjava) {
		try {
		String sql = "insert into userposjava (nome, email) values (?,?)";
		PreparedStatement insert = connection.prepareStatement(sql);
		insert.setString(1, userposjava.getNome());
		insert.setString(2, userposjava.getEmail());
		insert.execute();
		connection.commit();//salva no banco
		
		} catch (Exception e) {
			try {
				connection.rollback();// reverte operação
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public void salvarTelefone(Telefone telefone) {
		try {
			String sql = "INSERT INTO public.telefoneuser"
					+ "(numero, tipo, usuariopessoa) VALUES (?, ?, ?);";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
			insert.execute();
			connection.commit();//salva no banco
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public List<Userposjava> listar() throws Exception {
		List<Userposjava> list = new ArrayList<>();
		String sql = "select * from userposjava";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			Userposjava userposjava = new Userposjava();
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));
			
			list.add(userposjava);
		}
		
		return list;
	}
	
	public Userposjava buscar(Long id) throws Exception {
		
		String sql = "select * from userposjava where id = " + id;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		Userposjava userposjava = new Userposjava();
		while (resultado.next()) {
			
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));
		}
			
		
		
		return userposjava;
	}
	
	public List<BeanUserFone> listaUserFone (Long idUser) {
		
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
		
		String sql = "select nome, email, numero from telefoneuser as fone \r\n"
				+ "inner join userposjava as userp\r\n"
				+ "on fone.usuariopessoa = userp.id\r\n"
				+ "where userp.id = "+idUser;
		try {
			
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				BeanUserFone userFone = new BeanUserFone();
				
				userFone.setEmail(resultSet.getString("email"));
				userFone.setNome(resultSet.getString("nome"));
				userFone.setNumeroTelefone(resultSet.getString("numero"));
				
				beanUserFones.add(userFone);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanUserFones;
	}
	
	public void atualizar(Userposjava user) {
		String sql = "Update userposjava Set nome = ? Where id = " + user.getId();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getNome());
			
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();// reverte operação
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public void deletar(Long id) {
		try {
			String sql = "delete from userposjava where id = " + id;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	public void detetarFonesPorUser(Long idUser) {
		try {
			String sqlFone = "delete from telefoneuser where usuariopessoa =" + idUser;
			String sqlUser = "delete from userposjava where id=" + idUser;
			
			PreparedStatement statement = connection.prepareStatement(sqlFone);
			statement.execute();
			connection.commit();
			
			statement = connection.prepareStatement(sqlUser);
			statement.execute();
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
