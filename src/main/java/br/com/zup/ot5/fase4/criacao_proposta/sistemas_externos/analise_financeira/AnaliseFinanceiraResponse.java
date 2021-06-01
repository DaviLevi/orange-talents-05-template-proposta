package br.com.zup.ot5.fase4.criacao_proposta.sistemas_externos.analise_financeira;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class AnaliseFinanceiraResponse {
	
	private String documento;
	
	private String nome;
	
	private Long idProposta;

	@JsonFormat(shape = Shape.STRING)
	private ResultadoAnaliseFinanceira resultadoSolicitacao;
	
	public AnaliseFinanceiraResponse(String documento, String nome, Long idProposta, ResultadoAnaliseFinanceira resultadoSolicitacao) {
		this.documento = documento;
		this.nome = nome;
		this.idProposta = idProposta;
		this.resultadoSolicitacao = resultadoSolicitacao;
	}

	public ResultadoAnaliseFinanceira getResultado() {
		return resultadoSolicitacao;
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public Long getIdProposta() {
		return idProposta;
	}
}
