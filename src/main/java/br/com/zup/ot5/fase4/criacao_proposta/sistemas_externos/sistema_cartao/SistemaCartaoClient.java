package br.com.zup.ot5.fase4.criacao_proposta.sistemas_externos.sistema_cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.ot5.fase4.criacao_proposta.associa_cartao.BuscaCartaoResponse;

@FeignClient(url = "http://${proposta.services.sistemaCartao.host}:${proposta.services.sistemaCartao.port}/api/cartoes", name = "SistemaCartao")
public interface SistemaCartaoClient {

    @RequestMapping(method = RequestMethod.GET, path = "?idProposta={idProposta}")
    BuscaCartaoResponse buscaCartaoGeradoPara(@PathVariable("idProposta") Long idProposta);

}