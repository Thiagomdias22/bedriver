package br.com.bedriver.web;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.bedriver.model.Usuario;
import br.com.bedriver.rn.UsuarioRN;
import br.com.bedriver.util.DAOException;
import br.com.bedriver.util.Utils;
import net.bytebuddy.utility.RandomString;

@ManagedBean(name = "alterarSenhaBean")
@ViewScoped
public class AlterarSenhaBean {

	private Usuario usuario;
	private String novaSenha;
	private String confirmarNovaSenha;
	private String email;
	private boolean emailSend = false;

	public void recuperarSenha() {

		this.setEmailSend(true);
		String token = RandomString.make(45);

		String resetPasswordLink = "http://localhost:8080/bedriver/public/resetar_senha.jsf?token=" + token;

		try {
			UsuarioRN usuarioRN = new UsuarioRN();
			usuarioRN.updateResetPasswordToken(token, email);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		ExecutorService emailExecutor = Executors.newCachedThreadPool();
		emailExecutor.execute(new Runnable() {
			@Override
			public void run() {
				Utils.sendEmail(email, 
						"Recupera��o de Senha", resetPasswordLink);
			}
		});

	}
	
	public String salvarNovaSenha() {

		FacesContext context = FacesContext.getCurrentInstance();

		if (!this.getNovaSenha().equals(this.getConfirmarNovaSenha())) {

			this.setNovaSenha("");
			this.setConfirmarNovaSenha("");

			FacesMessage facesMessage = new FacesMessage();
			facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
			facesMessage.setSummary("Aviso:");
			facesMessage.setDetail("As senhas n�o conferem, tente novamente.");
			context.addMessage("MessageId", facesMessage);
			return null;
		}

		this.usuario.setSenha(this.novaSenha);
		this.usuario.setResetPasswordToken(null);

		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.salvar(this.usuario);

		return "/public/home";
	}

	public boolean isEmailSend() {
		return emailSend;
	}

	public void setEmailSend(boolean emailSend) {
		this.emailSend = emailSend;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void validaToken(String token) {

		UsuarioRN usuarioRN = new UsuarioRN();
		Usuario usuario = usuarioRN.get(token);
		
		if (usuario == null) {
			return;
		}

		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmarNovaSenha() {
		return confirmarNovaSenha;
	}

	public void setConfirmarNovaSenha(String confirmarNovaSenha) {
		this.confirmarNovaSenha = confirmarNovaSenha;
	}
}