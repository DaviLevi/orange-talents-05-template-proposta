package br.com.zup.ot5.fase4.criacao_proposta.sistemas_externos.analise_financeira;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Proposta;
import feign.FeignException;

@Component
public class AnalisadorFinanceiroProposta {

	@Autowired
	private AnalisadorFinanceiroClient api;
	
	public AnaliseFinanceiraResponse analisa(@Valid Proposta proposta) {
		try {
			AnaliseFinanceiraResponse resposta = api.analisa(new AnaliseFinanceiraRequest(proposta.getDocumento(), proposta.getNome(), proposta.getId()));
			return resposta;
		}catch(FeignException e) {
			if(e.status() == 422)
				return new AnaliseFinanceiraResponse(proposta.getDocumento(), proposta.getDocumento(), proposta.getId(), ResultadoAnaliseFinanceira.COM_RESTRICAO);
		}
		return null;
	}
}
