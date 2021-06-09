package br.com.zup.ot5.fase4.criacao_proposta.dominio;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import br.com.zup.ot5.fase4.criacao_proposta.nova_proposta.ResultadoAnaliseFinanceira;

@Entity
public class Proposta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PropostaID")
	private Long id;
	
	@NotBlank
	@Column(name = "Documento", unique = true)
	private String documento;
	
	@NotBlank
	@Column(name = "Nome")
	private String nome;
	
	@NotBlank
	@Email
	@Column(name = "Email")
	private String email;
	
	@NotBlank
	@Column(name = "Endereco")
	private String endereco;
	
	@PositiveOrZero
	@NotNull
	@Column(name = "Salario")
	private BigDecimal salario;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "Status")
	private StatusProposta statusProposta;

	@OneToOne(mappedBy = "proposta", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Cartao cartao;
	
	@Deprecated protected Proposta() {}
	
	public Proposta(@NotBlank String documento, @NotBlank String nome, @NotBlank @Email String email,
			@NotBlank String endereco, @PositiveOrZero @NotNull BigDecimal salario) {
		Assert.state(documento.length() != 60, "A proposta nao pode ser construida com um documento em formato BCrypt!");
		this.documento = new BCryptPasswordEncoder().encode(documento);
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.salario = salario;
	}
	
	
	public Long getId() {
		return this.id;
	}
	
	public void anexaResultadoAnaliseFinanceira(ResultadoAnaliseFinanceira resultado) {
		this.statusProposta = resultado.normaliza();
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}
	
	public boolean isElegivel() {
		return this.statusProposta == StatusProposta.ELEGIVEL;
	}

	public StatusProposta getStatus() {
		return statusProposta;
	}

	public Cartao getCartao() {
		return cartao;
	}
	
	public void associaCartao(Cartao cartao) {
		this.cartao = cartao;
		cartao.associaProposta(this);
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
}
