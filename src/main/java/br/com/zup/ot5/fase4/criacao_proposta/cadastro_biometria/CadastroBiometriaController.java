package br.com.zup.ot5.fase4.criacao_proposta.cadastro_biometria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Biometria;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;

@RestController
@RequestMapping("/cartoes/{idCartao}/biometrias")
public class CadastroBiometriaController {

	@PersistenceContext
	private EntityManager em;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastraBiometria(@PathVariable String idCartao, 
			@RequestBody @Valid CadastroBiometriaRequest requisicao, UriComponentsBuilder builder) {
		
		List<Cartao> cartoes = em.createQuery("SELECT c "
				                            + "FROM Cartao c "
				                            + "WHERE c.id = :id", Cartao.class)
								  .setParameter("id", idCartao)
								  .getResultList();
		
		if(cartoes.isEmpty())
			return ResponseEntity.notFound().build();
		
		Assert.state(cartoes.size() <= 1, "A consulta de busca de cartao para cadastro de biometria nao deveria retornar mais de 1 resultado!!");
		
		Cartao cartaoEncontrado = cartoes.get(0);
		
		Biometria biometria = requisicao.paraBiometria(cartaoEncontrado);
		
		em.persist(biometria);
		
		return ResponseEntity.created(builder.path("/cartoes/{idCartao}/biometrias/{idBiometria}")
				.buildAndExpand(cartaoEncontrado.getId(), biometria.getId()).toUri()).build();
		
	}
}
