package br.com.zup.ot5.fase4.criacao_proposta.dominio;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Cartao {
	
	@Id
	@NotBlank
	@Column(name = "CartaoID")
	private String id;
	
	@NotBlank
	@Column(name = "Titular")
	private String titular;
	
	@NotNull
	@Column(name = "EmitidoEm")
	private LocalDateTime emitidoEm;
	
	@NotNull
	@Column(name = "Limite")
	private Integer limite;
	
	@OneToOne(mappedBy = "cartao", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Vencimento vencimento;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PropostaID")
	@NotNull
	private Proposta proposta;
	
	@Deprecated public Cartao(){}
	

	public Cartao(@NotBlank String id, @NotBlank String titular, @NotNull LocalDateTime emitidoEm, @NotNull Integer limite, @NotNull @Valid Vencimento vencimento,
			@Valid Proposta proposta) {
		this.id = id;
		this.titular = titular;
		this.emitidoEm = emitidoEm;
		this.limite = limite;
		this.vencimento = vencimento;
		this.proposta = proposta;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getEmitidoEm() {
		return emitidoEm;
	}

	public Integer getLimite() {
		return limite;
	}

	public Vencimento getVencimento() {
		return vencimento;
	}

	public Proposta getProposta() {
		return proposta;
	}

	public String getTitular() {
		return titular;
	}
	
	void associaProposta(Proposta proposta) {
		this.proposta = proposta;
	}
	
	public void associaVencimento(Vencimento vencimento) {
		this.vencimento = vencimento;
		vencimento.associaCartao(this);
	}
}
