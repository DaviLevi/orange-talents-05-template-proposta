package br.com.zup.ot5.fase4.criacao_proposta.acompanha_proposta;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Proposta;

@RestController
@RequestMapping("/propostas/{idProposta}")
public class AcompanhaPropostaController {

	@PersistenceContext
	private EntityManager em;
	
	@GetMapping
	public ResponseEntity<?> acompanha(@PathVariable Long idProposta) {
		
		List<Proposta> propostas = em.createQuery("SELECT p "
										   +      "FROM Proposta p "
										   +      "JOIN FETCH p.cartao c "
										   +      "JOIN FETCH c.vencimento v "
										   +      "WHERE p.id = :id", Proposta.class)
							.setParameter("id", idProposta)
							.getResultList();
		
		if(propostas.isEmpty())
			return ResponseEntity.notFound().build();
		
		Assert.state(propostas.size() <= 1, "O resultado da consulta de acompanhamento de propostas não deveria apresentar mais de um resultado!!");
		
		AcompanhaPropostaResponse resposta = new AcompanhaPropostaResponse(propostas.get(0));
		
		return ResponseEntity.ok(resposta);
		
	}
}
