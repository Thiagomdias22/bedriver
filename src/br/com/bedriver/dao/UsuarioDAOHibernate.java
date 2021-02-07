package br.com.bedriver.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.bedriver.dao.intefaces.UsuarioDAO;
import br.com.bedriver.model.Usuario;

public class UsuarioDAOHibernate implements UsuarioDAO {

	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	public void salvar(Usuario usuario) {
		this.session.save(usuario);
	}

	public void atualizar(Usuario usuario) {
		
	}

	public void excluir(Usuario usuario) {
		this.session.delete(usuario);
	}

	public Usuario carregar(Integer codigo) {
		return (Usuario) this.session.get(Usuario.class, codigo);
	}

	public List<Usuario> listar() {
		return this.session.createCriteria(Usuario.class).list();
	}

	public Usuario buscarPorLogin(String login) {
		String hql = "select u from usuarios u where u.email = :email";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("login", login);
		return (Usuario) consulta.uniqueResult();
	}
}
