package br.com.bedriver.util;

import br.com.bedriver.dao.CondutorDAOHibernate;
import br.com.bedriver.dao.FrotaDAOHibernate;
import br.com.bedriver.dao.InfracaoDAOHIbernate;
import br.com.bedriver.dao.intefaces.CondutorDAO;
import br.com.bedriver.dao.intefaces.FrotaDAO;
import br.com.bedriver.dao.intefaces.InfracaoDAO;


public class DAOFactory {

	public static FrotaDAO criarFrotaDAO() {
		FrotaDAOHibernate frotaDAO = new FrotaDAOHibernate();
		frotaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());		
		return frotaDAO;
	}
	
	public static CondutorDAO criarCondutorDAO() {
		CondutorDAOHibernate condutorDAO = new CondutorDAOHibernate();
		condutorDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());		
		return condutorDAO;
	}
	
	public static InfracaoDAO criarInfracaoDAO() {
		InfracaoDAOHIbernate infracaoDAO = new InfracaoDAOHIbernate();
		infracaoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());		
		return infracaoDAO;
	}

}