package br.com.zup.ot5.fase4.criacao_proposta.dominio;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartaoBloqueiado", cascade = {CascadeType.MERGE})
	private Set<Bloqueio> bloqueios;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartaoAvisado", cascade = {CascadeType.MERGE})
	private Set<AvisoViagem> avisosViagem;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartaoAssociado", cascade = {CascadeType.MERGE})
	private Set<Carteira> carteiras = new HashSet<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PropostaID")
	@NotNull
	private Proposta proposta;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private StatusCartao statusCartao = StatusCartao.ATIVO;
	
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
	
	public boolean estaBloqueado() {
		return this.statusCartao == StatusCartao.BLOQUEADO;
	}
	
	public void bloqueia(String ipCliente, String userAgent) {
		Bloqueio bloqueio = new Bloqueio(this, ipCliente, userAgent);
		this.statusCartao = StatusCartao.BLOQUEADO;
		this.bloqueios.add(bloqueio);
	}
	
	public void adicionaAvisoViagem(AvisoViagem avisoViagem) {
		this.avisosViagem.add(avisoViagem);
	}
	
	public void associaCarteira(Carteira carteira) {
		this.carteiras.add(carteira);
	}
	
	public boolean jaPossuiUmaCarteiraDoTipo(TipoCarteira tipoRequisitado) {
		return this.carteiras.stream().anyMatch(carteira -> carteira.ehDoTipo(tipoRequisitado));
	}
}
