package br.com.zup.ot5.fase4.criacao_proposta.dominio;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Vencimento {
	
	@Id
	@Column(name = "VencimentoID")
	private String id;
	
	@Column(name = "Dia")
	private Integer dia;
	
	@Column(name = "DataDeCriacao")
	private LocalDateTime dataDeCriacao;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CartaoID")
	private Cartao cartao;

	@Deprecated public Vencimento() {}
	
	public Vencimento(String id, Integer dia, LocalDateTime dataDeCriacao) {
		this.id = id;
		this.dia = dia;
		this.dataDeCriacao = dataDeCriacao;
	}

	public String getId() {
		return id;
	}

	public Integer getDia() {
		return dia;
	}

	public LocalDateTime getDataDeCriacao() {
		return dataDeCriacao;
	}

	void associaCartao(Cartao cartao) {
		this.cartao = cartao;
	}
}
