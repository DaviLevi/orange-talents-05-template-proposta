package br.com.zup.ot5.fase4.criacao_proposta.dominio;

public enum TipoCarteira {

	PAYPAL("Paypal"), SAMSUNG_PAY("Samsung Pay");
	
	private String descricao;
	
	private TipoCarteira(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
	
}
