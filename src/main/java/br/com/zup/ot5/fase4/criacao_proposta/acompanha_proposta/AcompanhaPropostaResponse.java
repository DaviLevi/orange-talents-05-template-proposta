package br.com.zup.ot5.fase4.criacao_proposta.acompanha_proposta;

import java.math.BigDecimal;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Proposta;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.StatusProposta;

public class AcompanhaPropostaResponse {

	private Long id;
	
	private String documento;
	
	private String nome;
	
	private String email;
	
	private String endereco;
	
	private BigDecimal salario;
	
	private StatusProposta statusProposta;

	private AcompanhaPropostaCartaoResponse cartao;

	public AcompanhaPropostaResponse(Long id, String documento, String nome, String email, String endereco,
			BigDecimal salario, StatusProposta statusProposta, AcompanhaPropostaCartaoResponse cartao) {
		this.id = id;
		this.documento = documento;
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.salario = salario;
		this.statusProposta = statusProposta;
		this.cartao = cartao;
	}

	public AcompanhaPropostaResponse(Proposta proposta) {
		this.id = proposta.getId();
		this.documento = proposta.getDocumento();
		this.nome = proposta.getNome();
		this.email = proposta.getEmail();
		this.endereco = proposta.getEndereco();
		this.salario = proposta.getSalario();
		this.statusProposta = proposta.getStatus();
		this.cartao = new AcompanhaPropostaCartaoResponse(proposta.getCartao());
	}

	public Long getId() {
		return id;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getEndereco() {
		return endereco;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public StatusProposta getStatusProposta() {
		return statusProposta;
	}

	public AcompanhaPropostaCartaoResponse getCartao() {
		return cartao;
	}
	
}
