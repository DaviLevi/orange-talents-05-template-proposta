package br.com.zup.ot5.fase4.criacao_proposta.nova_proposta;

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
