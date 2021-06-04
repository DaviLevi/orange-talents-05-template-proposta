package br.com.zup.ot5.fase4.criacao_proposta.dominio;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String fingerprint;

	@CreationTimestamp
	private LocalDateTime dataCadastro;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CartaoID")
	@NotNull
	private Cartao cartao;
	
	@Deprecated Biometria(){}


	public Biometria(@NotBlank String fingerprint, @Valid @NotNull Cartao cartao) {
		this.fingerprint = fingerprint;
		this.cartao = cartao;
	}
	
	public Long getId() {
		return this.id;
	}
	
}
