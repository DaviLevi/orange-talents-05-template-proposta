package br.com.zup.ot5.fase4.criacao_proposta.associa_carteira;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Carteira;

public interface AssociaCarteiraRequest {

	
	Carteira converterParaCarteira(Cartao cartaoAssociado, String id);
	String getEmail();
}
