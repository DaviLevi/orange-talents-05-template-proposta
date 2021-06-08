package br.com.zup.ot5.fase4.criacao_proposta.associa_carteira;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.ot5.fase4.criacao_proposta.core.exception_handling.Erro;
import br.com.zup.ot5.fase4.criacao_proposta.core.exception_handling.Erro.PropriedadeInvalida;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Carteira;
import br.com.zup.ot5.fase4.criacao_proposta.sistemas_externos.sistema_cartao.SistemaCartaoClient;
import feign.FeignException;

@RestController
@RequestMapping("/cartoes/{idCartao}/carteiras")
public class AssociaCarteiraController {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private SistemaCartaoClient cartaoApiLegado;
	
	
	@PostMapping(path = "/paypal")
	@Transactional
	public ResponseEntity<?> associaCartaoAoPaypal(@PathVariable(required = false) String idCartao, 
			@RequestBody @Valid AssociaCarteiraPaypalRequest requisicao, UriComponentsBuilder uriBuilder) {
		
		return associaCarteira(idCartao, requisicao, TipoCarteiraLegadoRequest.PAYPAL, uriBuilder);
	}

	
	@PostMapping(path = "/samsung-pay")
	@Transactional
	public ResponseEntity<?> associaCartaoAoSamsungPay(@PathVariable(required = false) String idCartao, 
			@RequestBody @Valid AssociaCarteiraSamsungPayRequest requisicao, UriComponentsBuilder uriBuilder) {
		
		return associaCarteira(idCartao, requisicao, TipoCarteiraLegadoRequest.SAMSUNG_PAY, uriBuilder);
	}
	
	private ResponseEntity<?> associaCarteira(String idCartao, AssociaCarteiraRequest requisicao, 
			TipoCarteiraLegadoRequest tipoCarteiraRequisitada, UriComponentsBuilder uriBuilder) {
		if(idCartao == null) {
			Erro erro = new Erro(400, "Dados inválidos", 
					"Path da requisição inválida. Por favor, verifique o conteudo e envie novamente", 
					List.of(new PropriedadeInvalida("idCartao", "A propriedade de path nao pode vir nula")));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}
		
		Cartao cartao = manager.find(Cartao.class, idCartao);
		
		if(cartao == null)
			return ResponseEntity.notFound().build();
		
		if(cartao.jaPossuiUmaCarteiraDoTipo(tipoCarteiraRequisitada.paraTipoCarteira())) { // verifica se já possui uma carteira do mesmo tipo ligada ao cartao
			String templateErro = "O cartão ja possui uma carteira do tipo %s associada a ele.";
			Erro erro = new Erro(422, "Entidade não processável", String.format(templateErro, tipoCarteiraRequisitada.paraTipoCarteira()));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
		}
		
		
		try { // notifica o sistema legado de cartoes
 			NotificaAssociacaoCarteiraResponse resposta = cartaoApiLegado.notificaAssociacaoCarteira(idCartao , new NotificaAssociacaoCarteiraRequest(requisicao.getEmail(), tipoCarteiraRequisitada));
			
			Carteira carteira = requisicao.converterParaCarteira(cartao, resposta.getId());
			cartao.associaCarteira(carteira);
			manager.merge(cartao);
			
			return ResponseEntity.created(uriBuilder.path("/cartoes/{idCartao}/carteiras/{idCarteira}")
					.buildAndExpand(idCartao, resposta.getId()).toUri()).build();
			
		}catch(FeignException e) {
			Erro erro = new Erro(422, "Entidade não processável", "Não foi possivel notificar a associacao de carteira.");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
		}
	}
}
