package ee.hitsa.ois.service.security;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsInteger;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.lang.invoke.MethodHandles;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LdapService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EntityManager em;

    public String getIdCode(Long schoolId, String username, String password) {
        SchoolLdapParams params = schoolLdapParams(schoolId);

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldaps://" + params.getUrl() + ":" + params.getPort());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, username + params.getDomain());
        env.put(Context.SECURITY_CREDENTIALS, password);

        DirContext ctx;
        try {
            ctx = new InitialDirContext(env);
        } catch (@SuppressWarnings("unused") AuthenticationException e) {
            log.info("Invalid LDAP user credentials: {}", username);
            return null;
        } catch (NamingException e) {
            log.error("Cannot connect to LDAP server", e);
            return null;
        }

        String idcode = null;
        NamingEnumeration<SearchResult> answer = null;
        try {
            SearchControls ctls = new SearchControls();
            ctls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            String[] attrIDs = { params.getIdcodeAttribute() };
            ctls.setReturningAttributes(attrIDs);

            answer = ctx.search(params.getBase(), "sAMAccountName=" + username, ctls);
            while (answer.hasMore()) {
                SearchResult result = answer.next();
                idcode = (String) result.getAttributes().get(params.getIdcodeAttribute()).get();
            }
        } catch (NamingException e) {
            log.error("Cannot get user ID-code from LDAP server", e);
        } finally {
            if (answer != null) {
                try {
                    answer.close();
                } catch (NamingException e) {
                    log.error("Cannot close LDAP search result NamingEnumeration", e);
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    log.error("Cannot close LDAP DirContext", e);
                }
            }
        }
        return idcode;
    }

    private SchoolLdapParams schoolLdapParams(Long schoolId) {
        Object data = em.createNativeQuery("select s.ad_url, s.ad_port, s.ad_domain, s.ad_base, s.ad_idcode_field " +
                "from school s where s.id = ?1")
                .setParameter(1, schoolId)
                .getSingleResult();

        SchoolLdapParams params = new SchoolLdapParams();
        params.setUrl(resultAsString(data, 0));
        params.setPort(resultAsInteger(data, 1));
        params.setDomain(resultAsString(data, 2));
        params.setBase(resultAsString(data, 3));
        params.setIdcodeAttribute(resultAsString(data, 4));
        return params;
    }

    static class SchoolLdapParams {

        private String url;
        private Integer port;
        private String domain;
        private String base;
        private String idcodeAttribute;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public String getIdcodeAttribute() {
            return idcodeAttribute;
        }

        public void setIdcodeAttribute(String idcodeAttribute) {
            this.idcodeAttribute = idcodeAttribute;
        }
    }
}