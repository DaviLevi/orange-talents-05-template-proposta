package br.com.zup.ot5.fase4.criacao_proposta.core.exception_handling;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.zup.ot5.fase4.criacao_proposta.core.exception_handling.Erro.PropriedadeInvalida;


@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
			
		List<PropriedadeInvalida> propriedadesInvalidas = ex.getBindingResult().getAllErrors().stream().map(oe -> {
			String propriedade = oe.getObjectName();
			String descricao = messageSource.getMessage(oe, LocaleContextHolder.getLocale());
			if (oe instanceof FieldError) {
				propriedade = ((FieldError)oe).getField();
			}
			return new PropriedadeInvalida(propriedade, descricao);
		}).collect(Collectors.toList());
		
		Erro erro = new Erro(status.value(), "Dados inválidos", 
				"Corpo da requisição inválido. Por favor, verifique o conteudo e envie novamente",
				propriedadesInvalidas);
		
		return handleExceptionInternal(ex, erro, headers, status, request);
	}
	
}
