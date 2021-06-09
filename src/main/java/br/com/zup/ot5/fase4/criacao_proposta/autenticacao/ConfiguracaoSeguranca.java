package br.com.zup.ot5.fase4.criacao_proposta.autenticacao;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/propostas/**").hasAuthority("SCOPE_propostas:leitura")
                                .antMatchers(HttpMethod.GET, "/cartoes/**").hasAuthority("SCOPE_cartoes:leitura")
                                .antMatchers(HttpMethod.POST, "/cartoes/**").hasAuthority("SCOPE_cartoes:escrita")
                                .antMatchers(HttpMethod.PUT, "/cartoes/**").hasAuthority("SCOPE_cartoes:escrita")
                                .antMatchers(HttpMethod.POST, "/propostas/**").hasAuthority("SCOPE_propostas:escrita")
                                .antMatchers(HttpMethod.GET, "/actuator/prometheus").permitAll()
                                .anyRequest().authenticated()
                )
            .sessionManagement()
            	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

}

