package br.com.zup.ot5.fase4.criacao_proposta.associa_cartao;

import java.time.LocalDateTime;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Proposta;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Vencimento;

public class BuscaCartaoResponse {

//	{
//		  "id": "6083-1637-4301-3222",
//		  "emitidoEm": "2021-06-02T18:19:03.209235",
//		  "titular": "davide",
//		  "bloqueios": [],
//		  "avisos": [],
//		  "carteiras": [],
//		  "parcelas": [],
//		  "limite": 3491,
//		  "renegociacao": null,
//		  "vencimento": {
//		    "id": "2f53adbe-18be-445f-8803-07f7bcc1adfe",
//		    "dia": 30,
//		    "dataDeCriacao": "2021-06-02T18:19:03.209709"
//		  },
//		  "idProposta": "1"
//		}
	
	private String id;
	
	private String titular;
	
	private LocalDateTime emitidoEm;
	
	private Integer limite;
	
	private BuscaCartaoVencimentoResponse vencimento;
	
	public BuscaCartaoResponse(String id, String titular, LocalDateTime emitidoEm, Integer limite,
			BuscaCartaoVencimentoResponse vencimento) {
		this.id = id;
		this.titular = titular;
		this.emitidoEm = emitidoEm;
		this.limite = limite;
		this.vencimento = vencimento;
	}

	public Cartao paraCartao(Proposta proposta) {
		Vencimento vencimento = this.vencimento.paraVencimento();
		Cartao cartao = new Cartao(this.id, this.titular, this.emitidoEm, this.limite, vencimento, proposta);
		proposta.associaCartao(cartao);
		cartao.associaVencimento(vencimento);
		return cartao;
	}
	
}
