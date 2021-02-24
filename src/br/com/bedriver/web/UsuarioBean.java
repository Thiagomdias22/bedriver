package br.com.bedriver.web;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.bedriver.model.Usuario;
import br.com.bedriver.rn.UsuarioRN;
import br.com.bedriver.util.Utils;

@ManagedBean(name = "usuarioBean")
@RequestScoped
public class UsuarioBean {

	private Usuario usuario = new Usuario();
	private String confirmarSenha;
	private List<Usuario> lista;
	private String destinoSalvar;

	public String salvar() {
		
		FacesContext context = FacesContext.getCurrentInstance();

		String errorMsg = checkErrors();
		
		if(!errorMsg.equals("")) {
			FacesMessage facesMessage = new FacesMessage();
			facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
			facesMessage.setSummary("Aviso:");
			facesMessage.setDetail(errorMsg);
			context.addMessage("InvalidsInputs", facesMessage);
			return null;
		}

		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		
		this.usuario.setSenha(bcpe.encode(this.usuario.getSenha()));
		this.usuario.setPermissao("ROLE_USUARIO");
		this.usuario.setAtivo(true);

		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.salvar(this.usuario);

		return "/index";
	}
	
	public String checkErrors() {
		
		String detailsErrors = "";
		
		String nome = this.usuario.getNome();
		String email = this.usuario.getEmail();
		String senha = this.usuario.getSenha();
		String confirmSenha = this.confirmarSenha;
		
		UsuarioRN usuarioRN = new UsuarioRN();
		
		String regexSenha = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
		
		if (!senha.matches(regexSenha) || !confirmSenha.matches(regexSenha)) {
			detailsErrors = "Senha inv�lida, informe pelo menos: " 
					+ "8 ou mais caracteres, "
					+ "letras mai�sculas e min�sculas, " 
					+ "n�meros, " 
					+ "caracteres especiais.";
		}

		if (!senha.equals(confirmSenha)) {
			detailsErrors = "A senha n�o foi confirmada corretamente.";
		}

		if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
			detailsErrors = "Informe um e-mail v�lido.";
		}else if(usuarioRN.buscarPorLogin(email) != null){
			detailsErrors = "E-mail j� utilizado.";
		}
		
		if (nome.equals("")) {
			detailsErrors = "Informe um nome.";
		} else if(nome.length() < 5){
			detailsErrors = "Informe um nome com mais de 5 letras.";
		}
		
		return detailsErrors;
	}

	public String excluir(String email) {
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.excluir(usuarioRN.buscarPorLogin(email));
		this.lista = null;
		return null;
	}

	public List<Usuario> getLista() {
		if (this.lista == null) {
			UsuarioRN usuarioRN = new UsuarioRN();
			this.lista = usuarioRN.listar();
		}
		return this.lista;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}

	public String getNameUser(HttpServletRequest request) {
		String email = request.getRemoteUser();
		if (email == null) {
			return "";
		}
		UsuarioRN usuarioRN = new UsuarioRN();
		String nome = usuarioRN.buscarPorLogin(email).getNome();
		String nomeSplited[] = nome.split(" ");

		return nomeSplited[0] + " " + nomeSplited[1];
	}
}
