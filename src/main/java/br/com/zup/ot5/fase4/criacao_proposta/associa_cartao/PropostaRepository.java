package br.com.zup.ot5.fase4.criacao_proposta.associa_cartao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Proposta;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.StatusProposta;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long>{

	List<Proposta> findByStatusPropostaAndCartaoIsNull(StatusProposta status);
	
}
