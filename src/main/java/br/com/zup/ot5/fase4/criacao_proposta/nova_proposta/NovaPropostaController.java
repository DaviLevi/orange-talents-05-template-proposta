package br.com.zup.ot5.fase4.criacao_proposta.nova_proposta;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Proposta;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;

@RestController
@RequestMapping("/propostas")
public class NovaPropostaController {

	@PersistenceContext
	private EntityManager em;
	
	private final MeterRegistry meterRegistry;
	private Counter contadorPropostasCriadas;
	private Counter contadorPropostasCriadasComFalha;
	private Timer timerCriacaoProposta;
	
	@Autowired
	private AnalisadorFinanceiroProposta analisadorFinanceiro;
	
	public NovaPropostaController(MeterRegistry meterRegistry) {
	    this.meterRegistry = meterRegistry;    
	}
	
	@PostConstruct
	public void inicializaCalculadoresMetricas() {
		Collection<Tag> tags = new ArrayList<>();
		this.contadorPropostasCriadas = this.meterRegistry.counter("proposta_criada_com_sucesso", tags);
	    this.contadorPropostasCriadasComFalha = this.meterRegistry.counter("proposta_criada_com_falha", tags);
	    this.timerCriacaoProposta = this.meterRegistry.timer("proposta_criada_com_sucesso_timer", tags);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastra(@RequestBody @Valid NovaPropostaRequest novaProposta, UriComponentsBuilder builder) {
		
		final Long istanteInicioRequisicao = System.currentTimeMillis();
		
		if(novaProposta.jaExisteUmaPropostaCriadaParaMesmoSolicitante(em)) {
			this.contadorPropostasCriadasComFalha.increment();
			return ResponseEntity.unprocessableEntity().build();
		}
		
		Proposta proposta = novaProposta.paraProposta();
		
		em.persist(proposta);
		
		AnaliseFinanceiraResponse respostaAnalise = analisadorFinanceiro.analisa(proposta);
		
		if(respostaAnalise == null) {  // serviço indisponivel
			contadorPropostasCriadasComFalha.increment(); // incremento a metrica de propostas criada com falha
			throw new RuntimeException("Servico indisponivel");
		}
			
		proposta.anexaResultadoAnaliseFinanceira(respostaAnalise.getResultado());
		
		contadorPropostasCriadas.increment(); // incremento a metrica de propostas criada com sucesso
		
		final Long istanteFimRequisicao = System.currentTimeMillis();
		final Long duracaoRequisicaoEmMilisegundos = istanteFimRequisicao - istanteInicioRequisicao;
		
		this.timerCriacaoProposta.record(Duration.of(duracaoRequisicaoEmMilisegundos, ChronoUnit.MILLIS));
		
		return ResponseEntity.created(builder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri()).build();
	}
}
