package br.com.zup.ot5.fase4.criacao_proposta.bloqueio_cartao;

public class NotificaBloqueioRequest {

	private String sistemaResponsavel;
	
	public NotificaBloqueioRequest(String sistemaReponsavel) {
		this.sistemaResponsavel = sistemaReponsavel;
	}

	public String getSistemaResponsavel() {
		return sistemaResponsavel;
	}
}
