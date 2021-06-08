package br.com.zup.ot5.fase4.criacao_proposta.aviso_viagem;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.ot5.fase4.criacao_proposta.core.exception_handling.Erro;
import br.com.zup.ot5.fase4.criacao_proposta.core.exception_handling.Erro.PropriedadeInvalida;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.AvisoViagem;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;

@RestController
@RequestMapping("/cartoes/{idCartao}/avisos-viagem")
public class AvisoViagemController {

	@PersistenceContext
	private EntityManager manager;
	
	private final String LOCALHOST_IPV4 = "127.0.0.1";
	private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> avisaViagem(HttpServletRequest servletRequest, 
			@PathVariable(required = false) String idCartao, @RequestBody @Valid AvisoViagemRequest requisicao) {
		
		if(idCartao == null) { // a variavel de path veio invalida
			Erro erro = new Erro(400, "Dados inválidos", 
					"Path da requisição inválida. Por favor, verifique o conteudo e envie novamente", 
					List.of(new PropriedadeInvalida("idCartao", "A propriedade de path deve vir valida e preenchida. Ex : [String]")));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}
		
		Cartao cartao = manager.find(Cartao.class, idCartao);
		
		if(cartao == null) // o cartao nao foi encontrado
			return ResponseEntity.notFound().build();
		
		String ipCliente = getClientIp(servletRequest);
		String userAgent = servletRequest.getHeader("User-Agent");
		
		AvisoViagem avisoViagem = requisicao.converteParaAvisoViagem(ipCliente, userAgent, cartao);
		
		cartao.adicionaAvisoViagem(avisoViagem);
		
		manager.merge(cartao);
		
		return ResponseEntity.ok().build();
		
	}
	
	private String getClientIp(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-Forwarded-For");
		if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		
		if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		
		if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if(LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
				try {
					InetAddress inetAddress = InetAddress.getLocalHost();
					ipAddress = inetAddress.getHostAddress();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(!StringUtils.isEmpty(ipAddress) 
				&& ipAddress.length() > 15
				&& ipAddress.indexOf(",") > 0) {
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
		}
		
		return ipAddress;
	}
}