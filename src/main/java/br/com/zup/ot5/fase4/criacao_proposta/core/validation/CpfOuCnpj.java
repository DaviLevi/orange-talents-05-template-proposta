package br.com.zup.ot5.fase4.criacao_proposta.core.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR)
@ReportAsSingleViolation
@Documented
@Target({ElementType.FIELD})
@Constraint(validatedBy = { })
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfOuCnpj {
    String message() default "O documento não está em um formato valido. Exemplo : [CPF, CNPJ]";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}