package br.com.zup.ot5.fase4.criacao_proposta.sistemas_externos.analise_financeira;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.StatusProposta;

public enum ResultadoAnaliseFinanceira {
	COM_RESTRICAO{
		public StatusProposta normaliza() {
			return StatusProposta.NAO_ELEGIVEL;
		}
	}, 
	
	SEM_RESTRICAO{
		public StatusProposta normaliza() {
			return StatusProposta.ELEGIVEL;
		}
	};
	
	
	public abstract StatusProposta normaliza();
}
