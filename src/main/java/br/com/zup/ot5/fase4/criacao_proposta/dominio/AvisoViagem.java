package br.com.zup.ot5.fase4.criacao_proposta.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class AvisoViagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AvisoViagemID")
	private Long id;
	
	@Column(name = "DestinoViagem")
	@NotNull
	private String destinoViagem;
	
	@Column(name = "DataTerminoViagem")
	@NotNull
	@FutureOrPresent
	private LocalDate dataTerminoViagem;
	
	@Column(name = "DataHoraCadastro")
	@CreationTimestamp
	private LocalDateTime dataHoraCadastro;
	
	@Column(name = "IpCliente")
	@NotBlank
	private String ipCliente;
	
	@Column(name = "UserAgent")
	@NotBlank
	private String userAgent;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "CartaoID")
	@NotNull
	@Valid
	private Cartao cartaoAvisado;

	@Deprecated public AvisoViagem() {}

	public AvisoViagem(@NotNull String destinoViagem, @NotNull @FutureOrPresent LocalDate dataTerminoViagem,
			@NotBlank String ipCliente, @NotBlank String userAgent, @NotNull @Valid Cartao cartaoAvisado) {
		super();
		this.destinoViagem = destinoViagem;
		this.dataTerminoViagem = dataTerminoViagem;
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
		this.cartaoAvisado = cartaoAvisado;
	}
	
}
