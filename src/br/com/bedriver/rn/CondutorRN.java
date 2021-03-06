package br.com.bedriver.rn;

import java.util.List;

import br.com.bedriver.dao.intefaces.CondutorDAO;

import br.com.bedriver.model.CondutoresHab;
import br.com.bedriver.util.DAOFactory;

public class CondutorRN {
	private CondutorDAO condutorDAO;

	public CondutorRN() {
		this.condutorDAO = DAOFactory.criarCondutorDAO();
	}

	public List<CondutoresHab> listar() {
		return this.condutorDAO.listar();
	}

}
