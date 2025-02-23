package br.com.zup.ot5.fase4.criacao_proposta.nova_proposta;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.com.zup.ot5.fase4.criacao_proposta.core.validation.CpfOuCnpj;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Proposta;

public class NovaPropostaRequest {

	@NotBlank
	@CpfOuCnpj
	private String documento;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String endereco;
	
	@PositiveOrZero
	@NotNull
	private BigDecimal salario;

	public NovaPropostaRequest(@NotBlank String documento, @NotBlank String nome, @NotBlank @Email String email,
			@NotBlank String endereco, @PositiveOrZero @NotNull BigDecimal salario) {
		this.documento = documento;
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.salario = salario;
	}
	
	public Proposta paraProposta() {
		return new Proposta(this.documento, this.nome, this.email, this.endereco, this.salario);
	}
	
	public boolean jaExisteUmaPropostaCriadaParaMesmoSolicitante(EntityManager manager) {
		try {
			manager.createQuery("select p from Proposta p where p.documento = :Pdocumento", Proposta.class)
			   .setParameter("Pdocumento", this.documento)
			   .getSingleResult();
			return true;
		}catch(NoResultException exc) {
			
			return false;
		}
	}
}
