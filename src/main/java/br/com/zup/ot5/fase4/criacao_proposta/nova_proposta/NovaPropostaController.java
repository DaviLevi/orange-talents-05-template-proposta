package br.com.zup.ot5.fase4.criacao_proposta.nova_proposta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Proposta;

@RestController
@RequestMapping("/propostas")
public class NovaPropostaController {

	@PersistenceContext
	private EntityManager em;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastra(@RequestBody @Valid NovaPropostaRequest requisicao, UriComponentsBuilder builder) {
		Proposta proposta = requisicao.paraProposta();
		em.persist(proposta);
		return ResponseEntity.created(builder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri()).build();
	}
	
}
