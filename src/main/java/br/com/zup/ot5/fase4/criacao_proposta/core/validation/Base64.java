package br.com.zup.ot5.fase4.criacao_proposta.core.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;


@Pattern(regexp = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$", message = "O campo não esta codificado em base64")
@Documented
@Target({ElementType.FIELD})
@Constraint(validatedBy = { })
@Retention(RetentionPolicy.RUNTIME)
public @interface Base64 {
	
    String message() default "O campo não esta codificado em base64";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
