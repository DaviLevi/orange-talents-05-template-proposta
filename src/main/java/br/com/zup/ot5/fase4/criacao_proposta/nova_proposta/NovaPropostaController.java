package br.com.zup.ot5.fase4.criacao_proposta.nova_proposta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Proposta;
import br.com.zup.ot5.fase4.criacao_proposta.sistemas_externos.analise_financeira.AnalisadorFinanceiroProposta;
import br.com.zup.ot5.fase4.criacao_proposta.sistemas_externos.analise_financeira.AnaliseFinanceiraResponse;

@RestController
@RequestMapping("/propostas")
public class NovaPropostaController {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private AnalisadorFinanceiroProposta analisadorFinanceiro;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastra(@RequestBody @Valid NovaPropostaRequest novaProposta, UriComponentsBuilder builder) {
		
		if(novaProposta.jaExisteUmaPropostaCriadaParaMesmoSolicitante(em)) 
			return ResponseEntity.unprocessableEntity().build();
		
		Proposta proposta = novaProposta.paraProposta();
		
		em.persist(proposta);
		
		AnaliseFinanceiraResponse respostaAnalise = analisadorFinanceiro.analisa(proposta);
		
		if(respostaAnalise == null) // serviço indisponivel
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		
		proposta.anexaResultadoAnaliseFinanceira(respostaAnalise.getResultado());
		
		return ResponseEntity.created(builder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri()).build();
	}
}
