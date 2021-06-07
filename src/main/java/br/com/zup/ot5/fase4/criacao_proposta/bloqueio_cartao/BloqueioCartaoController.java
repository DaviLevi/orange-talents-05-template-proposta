package br.com.zup.ot5.fase4.criacao_proposta.bloqueio_cartao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.ot5.fase4.criacao_proposta.core.exception_handling.Erro;
import br.com.zup.ot5.fase4.criacao_proposta.core.exception_handling.Erro.PropriedadeInvalida;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;

@RestController
@RequestMapping("/cartoes/{idCartao}/bloqueia")
public class BloqueioCartaoController {

	@PersistenceContext
	private EntityManager manager;
	
	private final String LOCALHOST_IPV4 = "127.0.0.1";
	private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
	
	@PutMapping
	@Transactional
	public ResponseEntity<?> bloquear(HttpServletRequest servletRequest, @PathVariable(required = false) String idCartao) {
		
		if(idCartao == null) {
			Erro erro = new Erro(400, "Dados inválidos", 
					"Path da requisição inválida. Por favor, verifique o conteudo e envie novamente", 
					List.of(new PropriedadeInvalida("idCartao", "A propriedade de path nao pode vir nula")));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		}
		
		Cartao cartao = manager.find(Cartao.class, idCartao);
		
		if(cartao == null)
			return ResponseEntity.notFound().build();
		
		Cartao cartaoEncontrado = cartao;
		
		if(cartaoEncontrado.estaBloqueiado()) {
			Erro erro = new Erro(422, "Entidade não processável", "O cartão ja está bloqueiado.");
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
		}
		
		String ipCliente = getClientIp(servletRequest);
		String userAgent = servletRequest.getHeader("User-Agent");
		
		cartaoEncontrado.bloqueia(ipCliente, userAgent);
		
		manager.merge(cartaoEncontrado);
		
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
