package com.servicesinvestproducts.servicesinvestproducts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.Name;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class LDAPService {

    @Autowired
    private Environment env;

    @Autowired
    private ContextSource contextSource;

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void authenticate(final String username, final String password) {
        contextSource.getContext("cn=" + username + ",ou=users," + env.getRequiredProperty("ldap.partitionSuffix"), password);
    }

    public List<String> search(final String username) {
        return ldapTemplate.search(
                "ou=users",
                "cn=" + username,
                (AttributesMapper<String>) attrs -> (String) attrs
                        .get("cn")
                        .get());
    }

    public void create(final String username, final String password, String forename) {
        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "people")
                .add("uid", username)
                .build();
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues("objectclass", new String[]{"top", "person", "organizationalPerson", "inetOrgPerson"});
        context.setAttributeValue("cn", username);
        context.setAttributeValue("sn", forename);
        context.setAttributeValue("userPassword", passwordEncoder.encode(password) );

        ldapTemplate.bind(context);
    }

    public void modify(final String username, final String password) {
        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "users")
                .add("cn", username)
                .build();
        DirContextOperations context = ldapTemplate.lookupContext(dn);

        context.setAttributeValues("objectclass", new String[]{"top", "person", "organizationalPerson", "inetOrgPerson"});
        context.setAttributeValue("cn", username);
        context.setAttributeValue("sn", username);
        //context.setAttributeValue("userPassword", digestSHA(password));
        context.setAttributeValue("userPassword", passwordEncoder.encode(password) );

        ldapTemplate.modifyAttributes(context);
    }

    private String digestSHA(final String password) {
        String base64;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            base64 = Base64
                    .getEncoder()
                    .encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "{SHA}" + base64;
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) throws Exception{
        LdapTemplate ldapTemplate = new LdapTemplate();
        ldapTemplate.setContextSource(contextSource);
        return ldapTemplate;
    }

    @Bean
    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }
}