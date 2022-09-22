package pos_java_jdbc.pos_java_jdbc;

import java.util.List;

import org.junit.Test;

import conexaojdbc.SingleConnection;
import dao.UserposDAO;
import junit.framework.Assert;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteBancoJsbc {
	
	@Test
	public void initSalvar() {
		UserposDAO userposDAO = new UserposDAO();
		Userposjava userposjava = new Userposjava();
		
		userposjava.setNome("Maria Dev");
		userposjava.setEmail("Mariafront@gmail.com");
		
		userposDAO.salvar(userposjava);
	}
	
	@Test
	public void initListar() {
		UserposDAO userposDAO = new UserposDAO();
		
		List<Userposjava> list;
		try {
			list = userposDAO.listar();
			
			for(Userposjava userposjava : list) {
				System.out.println(userposjava);
				System.out.println("-----------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initbuscar() {
		UserposDAO userposDAO = new UserposDAO();
		
		
		try {
			Userposjava userposjava = userposDAO.buscar(3L);

				System.out.println(userposjava);
				System.out.println("-----------------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initatualizar() {
		UserposDAO userposDAO = new UserposDAO();
		
		
		try {
			Userposjava userposjava = userposDAO.buscar(5L);
			userposjava.setNome("Novo Nome");
			userposDAO.atualizar(userposjava);

				System.out.println(userposjava);
				System.out.println("-----------------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initDeletar() {
		try {
			UserposDAO dao = new UserposDAO();
			dao.deletar(5L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeInsertTelefone() {
		Telefone telefone = new Telefone();
		telefone.setNumero("(83) 9 00008888");
		telefone.setTipo("Casa");
		telefone.setUsuario(23L);
		
		UserposDAO dao = new UserposDAO();
		dao.salvarTelefone(telefone);
		Assert.assertNotSame(telefone, dao);
	}
	
	@Test
	public void testCarregaTelefoneUser() {
		UserposDAO dao = new UserposDAO();
		
		List<BeanUserFone> beanUserFones = dao.listaUserFone(14L);
		
		for (BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone);
			System.out.println("---------------------------");
		}
	}
	
	@Test
	public void testeDeleteUserFone() {
		
		UserposDAO dao = new UserposDAO();
		dao.detetarFonesPorUser(13L);
	}
}
