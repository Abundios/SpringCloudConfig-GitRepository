package com.investproducts.appinvestproducts.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.support.LdapContextSource;
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{

    private static Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private Environment env;

    public SecurityConfiguration(){
        super();
        LOGGER.info("Load...");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.ldapAuthentication()
                .userDnPatterns(env.getRequiredProperty("ldap.userdnpatterns"))
                .groupSearchBase(env.getRequiredProperty("ldap.groupsearchbase"))
                .contextSource()
                .url(env.getRequiredProperty("ldap.url"))
                .managerDn(env.getRequiredProperty("ldap.userdn"))
                .managerPassword(env.getRequiredProperty("ldap.userpwd"))
                .and()
                .passwordCompare()
                //.passwordEncoder(new LdapShaPasswordEncoder())
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPassword");

        auth.eraseCredentials(false);

    }


    @Override
    protected void configure (HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                .antMatchers("/principal").fullyAuthenticated()
                .antMatchers("/").permitAll()
                .and()
                .formLogin();
    }

    @Bean
    public LdapContextSource contextSource() throws Exception{
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(env.getRequiredProperty("ldap.url"));
        contextSource.setBase(env.getRequiredProperty("ldap.rootbase"));
        contextSource.setUserDn(env.getRequiredProperty("ldap.userdn"));
        contextSource.setPassword(env.getRequiredProperty("ldap.userpwd"));
        contextSource.afterPropertiesSet();
        return contextSource;
    }

}

