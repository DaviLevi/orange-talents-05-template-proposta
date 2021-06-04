package br.com.zup.ot5.fase4.criacao_proposta.sistemas_externos.analise_financeira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.zup.ot5.fase4.criacao_proposta.nova_proposta.AnaliseFinanceiraRequest;
import br.com.zup.ot5.fase4.criacao_proposta.nova_proposta.AnaliseFinanceiraResponse;

@FeignClient(url = "http://${proposta.services.analiseFinanceira.host}:${proposta.services.analiseFinanceira.port}/api/solicitacao", name = "AnaliseFinanceira")
public interface AnalisadorFinanceiroClient {

    @PostMapping
    AnaliseFinanceiraResponse analisa(AnaliseFinanceiraRequest requisicao);

}