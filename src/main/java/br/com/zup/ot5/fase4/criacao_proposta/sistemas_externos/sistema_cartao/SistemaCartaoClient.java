package br.com.zup.ot5.fase4.criacao_proposta.sistemas_externos.sistema_cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.ot5.fase4.criacao_proposta.associa_cartao.BuscaCartaoResponse;
import br.com.zup.ot5.fase4.criacao_proposta.aviso_viagem.NotificaAvisoViagemRequest;
import br.com.zup.ot5.fase4.criacao_proposta.bloqueio_cartao.NotificaBloqueioRequest;

@FeignClient(url = "http://${proposta.services.sistemaCartao.host}:${proposta.services.sistemaCartao.port}/api/cartoes", name = "SistemaCartao")
public interface SistemaCartaoClient {

    @RequestMapping(method = RequestMethod.GET, path = "?idProposta={idProposta}")
    BuscaCartaoResponse buscaCartaoGeradoPara(@PathVariable("idProposta") Long idProposta);
    
    @RequestMapping(method = RequestMethod.POST, path = "/{idCartao}/bloqueios")
    void notificaBloqueioDoCartao(@PathVariable String idCartao, NotificaBloqueioRequest requisicao);
    
    @RequestMapping(method = RequestMethod.POST, path = "/{idCartao}/avisos")
    void notificaAvisoViagem(@PathVariable String idCartao, NotificaAvisoViagemRequest requisicao);

}