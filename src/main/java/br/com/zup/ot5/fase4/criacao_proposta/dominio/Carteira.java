package br.com.zup.ot5.fase4.criacao_proposta.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira {

	@Id
	@Column(name = "CarteiraID")
	@NotBlank
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CartaoID", nullable = false)
	@NotNull
	@Valid
	private Cartao cartaoAssociado;
	
	@Column(name = "TipoCarteira")
	@Enumerated(EnumType.STRING)
	@NotNull
	private TipoCarteira tipoCarteira;
	
	@NotBlank
	@Email
	private String email;

	@Deprecated public Carteira() {}

	public Carteira(@NotBlank String id, @NotNull @Valid Cartao cartaoAssociado, @NotNull TipoCarteira tipoCarteira,
			@NotBlank @Email String email) {
		super();
		this.id = id;
		this.cartaoAssociado = cartaoAssociado;
		this.tipoCarteira = tipoCarteira;
		this.email = email;
	}
	
	public boolean ehDoTipo(TipoCarteira tipo) {
		return tipoCarteira == tipo;
	}
}
