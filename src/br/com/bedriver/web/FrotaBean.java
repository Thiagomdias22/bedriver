package br.com.bedriver.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.bedriver.model.FrotaVeiculo;
import br.com.bedriver.rn.FrotaRN;
import br.com.bedriver.rn.UsuarioRN;

@ManagedBean(name = "frotaBean")
@ViewScoped
public class FrotaBean {

	private List<FrotaVeiculo> lista;
	private boolean mostraLista = true;
	private boolean mostraGrafico = false;
	private static final Logger logger = LogManager.getLogger(FrotaBean.class);

	public boolean isMostraLista() {
		return mostraLista;
	}

	public boolean isMostraGrafico() {
		return mostraGrafico;
	}

	public void setMostraGrafico(boolean mostraGrafico) {
		this.mostraGrafico = mostraGrafico;
		this.mostraLista = !mostraGrafico;
	}

	public void setMostraLista(boolean mostraLista) {
		this.mostraLista = mostraLista;
		this.mostraGrafico = !mostraLista;
	}

	public void setLista(List<FrotaVeiculo> lista) {
		this.lista = lista;
	}

	public List<FrotaVeiculo> getLista() {			
		
		if (this.lista == null) {
			FrotaRN frotaRN = new FrotaRN();
			this.lista = frotaRN.listar();
			if (this.lista == null) {
				logger.error("Erro ao listar frotas");
			} else {
				logger.info("Frotas listadas com sucesso");
			}
		}
		return this.lista;
	}
}