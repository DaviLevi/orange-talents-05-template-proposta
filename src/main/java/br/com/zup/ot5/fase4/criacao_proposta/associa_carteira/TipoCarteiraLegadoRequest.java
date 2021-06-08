package br.com.zup.ot5.fase4.criacao_proposta.associa_carteira;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.TipoCarteira;

public enum TipoCarteiraLegadoRequest {

	PAYPAL{
		@Override
		public TipoCarteira paraTipoCarteira() {
			return TipoCarteira.PAYPAL;
		}
	};
	
	public abstract TipoCarteira paraTipoCarteira();
}
