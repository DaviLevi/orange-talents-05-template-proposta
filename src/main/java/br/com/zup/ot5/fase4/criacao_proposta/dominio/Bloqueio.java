package br.com.zup.ot5.fase4.criacao_proposta.dominio;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Bloqueio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BloqueioID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CartaoID", nullable = false)
	@NotNull
	@Valid
	private Cartao cartaoBloqueiado;
	
	@Column(name = "Status")
	@Enumerated(EnumType.STRING)
	@NotNull
	private StatusBloqueio statusBloqueio = StatusBloqueio.ATIVO;
	
	@CreationTimestamp
	@Column(name = "DataHoraBloqueio")
	private LocalDateTime dataHoraBloqueio;
	
	@NotBlank
	private String ipUsuario;
	
	@NotBlank
	private String userAgent;

	@Deprecated public Bloqueio() {}

	public Bloqueio(@NotNull @Valid Cartao cartaoBloqueiado,
			@NotBlank String ipUsuario, @NotBlank String userAgent) {
		this.cartaoBloqueiado = cartaoBloqueiado;
		this.ipUsuario = ipUsuario;
		this.userAgent = userAgent;
	}
	
	public boolean foiCriadoDepoisDe(Bloqueio outroBloqueio) {
		return this.dataHoraBloqueio.isAfter(outroBloqueio.dataHoraBloqueio);
	}
	
	public boolean isAtivo() {
		return this.statusBloqueio == StatusBloqueio.ATIVO;
	}
}
