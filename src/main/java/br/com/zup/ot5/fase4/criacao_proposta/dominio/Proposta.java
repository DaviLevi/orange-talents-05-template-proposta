package br.com.zup.ot5.fase4.criacao_proposta.dominio;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.com.zup.ot5.fase4.criacao_proposta.core.validation.CpfOuCnpj;
import br.com.zup.ot5.fase4.criacao_proposta.sistemas_externos.analise_financeira.ResultadoAnaliseFinanceira;

@Entity
public class Proposta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CpfOuCnpj
	@NotBlank
	@Column(unique = true)
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
	
	@Enumerated(EnumType.STRING)
	private StatusProposta status;

	@Deprecated protected Proposta() {}
	
	public Proposta(@NotBlank String documento, @NotBlank String nome, @NotBlank @Email String email,
			@NotBlank String endereco, @PositiveOrZero @NotNull BigDecimal salario) {
		this.documento = documento;
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.salario = salario;
	}
	
	
	public Long getId() {
		return this.id;
	}
	
	public void anexaResultadoAnaliseFinanceira(ResultadoAnaliseFinanceira resultado) {
		this.status = resultado.normaliza();
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}
	
	
	
}
