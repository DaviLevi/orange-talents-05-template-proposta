package br.com.zup.ot5.fase4.criacao_proposta.core.exception_handling;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Erro {

	private Integer status;
	private String titulo;
	private String descricao;
	
	@JsonInclude(value = Include.NON_NULL)
	private List<PropriedadeInvalida> propriedadesInvalidas;
	
	public Erro(Integer status, String titulo, String descricao) {
		super();
		this.status = status;
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	public Erro(Integer status, String titulo, String descricao, List<PropriedadeInvalida> propriedadesInvalidas) {
		super();
		this.status = status;
		this.titulo = titulo;
		this.descricao = descricao;
		this.propriedadesInvalidas = propriedadesInvalidas;
	}

	public Integer getStatus() {
		return status;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public List<PropriedadeInvalida> getPropriedadesInvalidas() {
		return propriedadesInvalidas;
	}

	public static class PropriedadeInvalida{
		private String propriedade;
		private String descricao;
		
		public PropriedadeInvalida(String propriedade,
				String descricao) {
			this.propriedade = propriedade;
			this.descricao = descricao;
		}

		public String getPropriedade() {
			return propriedade;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
}
