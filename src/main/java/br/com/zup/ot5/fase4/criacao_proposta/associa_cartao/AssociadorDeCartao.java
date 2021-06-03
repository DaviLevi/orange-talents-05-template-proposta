package br.com.zup.ot5.fase4.criacao_proposta.associa_cartao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Proposta;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.StatusProposta;
import br.com.zup.ot5.fase4.criacao_proposta.sistemas_externos.sistema_cartao.SistemaCartaoClient;
import feign.FeignException;

@Component
@EnableScheduling
public class AssociadorDeCartao{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private SistemaCartaoClient sistemaCartaoClient;
	
	@Autowired
	private PropostaRepository repositorioProposta;
	
	private Logger logger = LogManager.getLogger(AssociadorDeCartao.class);
	
	@Scheduled(fixedDelay = 10000)
	@Transactional
	public void associaCartoesLegiveisAteOMomento () {
		
		List<Proposta> propostasElegiveisSemCartao = repositorioProposta.findByStatusPropostaAndCartaoIsNull(StatusProposta.ELEGIVEL);
		
		if(propostasElegiveisSemCartao.size() > 0) {
			logger.info("Foram encontradas propostas elegiveis para associacao");
			
			// associando as propostas aos cartoes disponiveis até o momento
			propostasElegiveisSemCartao.forEach(proposta -> {
				logger.info("Tentando associar Proposta titular={} documento={}", proposta.getNome(), proposta.getDocumento());
				associaCartaoAPropostaSeTiver(proposta);
			});
			
		}else {
			logger.info("Nenhuma proposta elegivel até o momento");
		}
	}
	
	private void associaCartaoAPropostaSeTiver(Proposta proposta) {
		try {
			BuscaCartaoResponse resposta = sistemaCartaoClient.buscaCartaoGeradoPara(proposta.getId());
			Cartao cartaoEncontrado = resposta.paraCartao(proposta);
			
			manager.persist(cartaoEncontrado);
			manager.merge(proposta);
			
			logger.info("Cartao id={} nome={} associado com sucesso a Proposta documento={} nome={} status={}", 
					cartaoEncontrado.getId(), cartaoEncontrado.getTitular() ,proposta.getDocumento(), proposta.getStatus());
		}catch(FeignException e) {
			logger.info("Nenhum Cartao encontrado para Proposta documento={} status={}", proposta.getDocumento(), proposta.getStatus());
		}
		
	}

}
