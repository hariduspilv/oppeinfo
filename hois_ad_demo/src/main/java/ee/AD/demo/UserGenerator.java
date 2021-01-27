package ee.AD.demo;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserGenerator {
    
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
	private static final int UF_PASSWD_NOTREQD = 0x0020;
	private static final int UF_NORMAL_ACCOUNT = 0x0200;
	private static final int UF_DONT_EXPIRE_PASSWD = 0x10000;
	private static final int UF_ACCOUNT_ENABLE = 0x0001;
	private static final int UF_ACCOUNT_DISABLE = 0x0002;
	private static final int ACCOUNT_DISABLE = 66050;
	
	@Value("${hois.school.ad_url}")
	private String url;
	@Value("${hois.school.ad_domain}")
    private String domain;
	@Value("${hois.school.ad_base}")
    private String base;
	@Value("${hois.school.ad_disabled_base}")
    private String disabledBase;
	@Value("${hois.school.ad_idcode_field}")
    private String idcodeAttribute;
	@Value("${hois.admin.user}")
    private String adminUser;
    @Value("${hois.admin.password}")
    private String adminPassword;

	public void createUser(String username, String idcodeOrUniqueCode, StudentDto student, DirContext dir){
        NamingEnumeration<SearchResult> enabledUser = null;
        NamingEnumeration<SearchResult> disabledUser = null;
        try {
            SearchControls ctls = new SearchControls();
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = { idcodeAttribute };
            ctls.setReturningAttributes(attrIDs);

            enabledUser = dir.search(base, "sAMAccountName=" + username, ctls);
            disabledUser = dir.search(disabledBase, "sAMAccountName=" + username, ctls);
            if (!enabledUser.hasMoreElements() && !disabledUser.hasMoreElements()) {
                setUserCridentials(username, idcodeOrUniqueCode, student, dir);
            } else {
                if (enabledUser.hasMore()) {
                    SearchResult result = enabledUser.next();
                    changeUserData(result.getNameInNamespace(), username, student, dir);
                    log.info(Messages.USER_ENABLED);
                }
                if (disabledUser.hasMore()) {
                    SearchResult result = disabledUser.next();
                    enableUser(result.getNameInNamespace(), username, student, dir);
                }
            }
        } catch (Exception e) {
            log.error("Cannot get user from LDAP server", e);
        } finally {
            if (enabledUser != null) {
                try {
                    enabledUser.close();
                } catch (NamingException e) {
                    log.error("Cannot close LDAP search result NamingEnumeration", e);
                }
            }
            if (disabledUser != null) {
                try {
                    disabledUser.close();
                } catch (NamingException e) {
                    log.error("Cannot close LDAP search result NamingEnumeration", e);
                }
            }
            if (dir != null) {
                try {
                    dir.close();
                } catch (NamingException e) {
                    log.error("Cannot close LDAP DirContext", e);
                }
            }
        }
	    
	}

    private String getCurriculumFolder(String structuralUnit, String merCode, String studentType) {
        if ("K".equals(studentType)) {
            return "välistudeng";
        } else if ("Arhitektuuriinstituut".equals(structuralUnit)) {
	        return "arhitektuur";
	    } else if ("Ehitusinstituut".equals(structuralUnit)) {
	        return "ehitus";
	    } else if ("Logistikainstituut".equals(structuralUnit)) {
            return "logistika";
        } else if ("Tehnoloogia ja ringmajanduse instituut".equals(structuralUnit) && !"80954".equals(merCode)) {
            return "ringmajandus";
        } else if ("Teenusmajanduse instituut".equals(structuralUnit)) {
            return "teenusmajandus";
        } else if ("Tehnikainstituut".equals(structuralUnit)) {
            return "tehnika";
        } else if ("80954".equals(merCode)) {
            return "tekstiil";
        }
        return "Tudeng yldine";
	}
	
	private void setUserCridentials(String username, String idcodeOrUniqueCode, StudentDto student, DirContext dir) {
	    /**
	     * Following attributes are set automatically:
	     * primaryGroupID
	     * objectCategory
	     * distinguishedName
	     * sAMAccountType
	     * memberOf
	     */
	    BasicAttributes entry = new BasicAttributes();
        entry.put(new BasicAttribute("cn", username));
        entry.put(new BasicAttribute(idcodeAttribute, idcodeOrUniqueCode));
        entry.put(new BasicAttribute("sAMAccountName", username));
        if (student.getCurriculumVersionCode() != null) {
            entry.put(new BasicAttribute("description", student.getCurriculumVersionCode()));
        }
        entry.put(new BasicAttribute("DisplayName", (new StringBuilder()).append(student.getFirstname()).append(student.getFirstname() != null ? " " : "").append(student.getLastname()).toString()));
        entry.put(new BasicAttribute("givenName", student.getFirstname()));
        if (student.getEmail() != null) {
            entry.put(new BasicAttribute("mail", student.getEmail()));
            entry.put(new BasicAttribute("userPrincipalName", student.getEmail()));
        }
        Attribute oc = new BasicAttribute("objectClass");
        oc.add("top");
        oc.add("person");
        oc.add("organizationalPerson");
        oc.add("user");
        entry.put(oc);
        if (StringUtils.hasText(student.getPersonalEmail())) {
            entry.put(new BasicAttribute("otherMailbox", student.getPersonalEmail()));
        }
        if (StringUtils.hasText(student.getIdcode())) {
            entry.put(new BasicAttribute("physicalDeliveryOfficeName", student.getIdcode()));
            entry.put(new BasicAttribute("pager", student.getIdcode()));
        }
        entry.put(new BasicAttribute("sn", student.getLastname()));
        if (StringUtils.hasText(student.getPhone())) {
            entry.put(new BasicAttribute("telephoneNumber", student.getPhone()));
        }
        entry.put(new BasicAttribute("userAccountControl", Integer.toString(
                UF_NORMAL_ACCOUNT + UF_DONT_EXPIRE_PASSWD)));
        String completeUserName = "CN=" + username + "," + "OU=" + 
                getCurriculumFolder(student.getSchoolDepartment(), student.getCurriculumMerCode(), student.getType()) 
                + ",OU=Tudengid,OU=Kasutajad" 
                + "," + base;
        try {
            // PASSWORD
            String newQuotedPassword = "\"" + idcodeOrUniqueCode + "\"";
            char unicodePwd[] = newQuotedPassword.toCharArray();
            byte newUnicodePassword[] = new byte[unicodePwd.length * 2];
            for (int i = 0; i < unicodePwd.length; i++) {
                newUnicodePassword[i * 2 + 1] = (byte) (unicodePwd[i] >>> 8);
                newUnicodePassword[i * 2 + 0] = (byte) (unicodePwd[i] & 0xff);
            }
            entry.put(new BasicAttribute("unicodePwd", newUnicodePassword));
            
            log.info(Messages.CREATE_SUCCESS);
            dir.createSubcontext(completeUserName, entry);
        } catch(NamingException e) {
            log.error("Cannot create a new account {}", username);
        } finally {
            // add to groups
            changeUserData(completeUserName, username, student, dir);
        }
	}
	
	public void addUserToGroup(String completeUsername, String groupName, DirContext dir) {
	    String fullGroupName = "CN=" + groupName + ",OU=Tudengid,OU=Kasutajad," + base;
	    ModificationItem[] mods = new ModificationItem[1];
	    mods[0] =new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute("member",completeUsername));
	    try {
            dir.modifyAttributes(fullGroupName, mods);
            log.info(Messages.GROUP_ADD, fullGroupName);
        } catch (NamingException e) {
            log.error(Messages.GROUP_ADD_FAILED, fullGroupName);
        }
	}
	
	public void addUserToUpperStructuralUnitGroup(String completeUsername, String groupName, DirContext dir) {
        String fullGroupName = "CN=" + groupName + ",OU=Grupid," + base;
        ModificationItem[] mods = new ModificationItem[1];
        mods[0] =new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute("member",completeUsername));
        try {
            dir.modifyAttributes(fullGroupName, mods);
            log.info(Messages.GROUP_ADD, fullGroupName);
        } catch (NamingException e) {
            log.error(Messages.GROUP_ADD_FAILED, fullGroupName);
        }
    }

	public DirContext createContext() {
		Hashtable<String, String> env = new Hashtable<>();
		 if (System.getProperty("https.proxyHost") != null && System.getProperty("https.proxyPort") != null) {
	            env.put("java.naming.ldap.factory.socket", "ee.hitsa.ois.service.security.CustomSocketFactory");          
	            CustomSocketFactory.setTunnelHost(System.getProperty("https.proxyHost"));
	            CustomSocketFactory.setTunnelPort(Integer.parseInt(System.getProperty("https.proxyPort")));
	            CustomSocketFactory.setIsSecured(true);
	        } else if (System.getProperty("http.proxyHost") != null && System.getProperty("http.proxyPort") != null) {
	            env.put("java.naming.ldap.factory.socket", "ee.hitsa.ois.service.security.CustomSocketFactory");          
	            CustomSocketFactory.setTunnelHost(System.getProperty("http.proxyHost"));
	            CustomSocketFactory.setTunnelPort(Integer.parseInt(System.getProperty("http.proxyPort")));
	        }
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldaps://" + url);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, adminUser + domain);
		env.put(Context.SECURITY_CREDENTIALS, adminPassword);
		DirContext dir;
		try {
		    dir = new InitialDirContext(env);
        } catch (@SuppressWarnings("unused") AuthenticationException e) {
            log.info("Invalid LDAP user credentials - username: {}", adminUser);
            return null;
        } catch (NamingException e) {
            log.error("Cannot connect to LDAP server", e);
            return null;
        }
        return dir;
	}
	
	/**
	 * Disable user
	 * Move to disabled directory
	 * Remove user from all groups except "User" group
	 * 
	 * @param userDn
	 * @param username
	 * @param dir
	 * @throws Exception
	 */
	public void disableUser(String username, DirContext dir) throws Exception {
	    NamingEnumeration<SearchResult> enabledUser = null;
        try {
            SearchControls ctls = new SearchControls();
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = { idcodeAttribute };
            ctls.setReturningAttributes(attrIDs);

            enabledUser = dir.search(base, "sAMAccountName=" + username, ctls);
            if (enabledUser.hasMore()) {
                SearchResult result = enabledUser.next();
                String newUserLocation = "CN=" + username + "," + disabledBase;
                ModificationItem[] mods = new ModificationItem[1];
                mods[0] =new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl", Integer.toString(ACCOUNT_DISABLE)));
                removeUserFromAllGroups(username, result.getNameInNamespace(), dir);
                dir.modifyAttributes(result.getNameInNamespace(), mods);
                dir.rename(result.getNameInNamespace(), newUserLocation);
                log.info(Messages.DELETE_SUCCESS);
            } else {
                log.info(Messages.CANNOT_DISABLE_ERROR, "user might already be disabled or user hasnt been created");
            }
        } catch (NamingException e) {
            log.error("Cannot get user ID-code from LDAP server", e);
        } finally {
            if (enabledUser != null) {
                try {
                    enabledUser.close();
                } catch (NamingException e) {
                    log.error("Cannot close LDAP search result NamingEnumeration", e);
                }
            }
            if (dir != null) {
                try {
                    dir.close();
                } catch (NamingException e) {
                    log.error("Cannot close LDAP DirContext", e);
                }
            }
        }
    }
	
	/**
	 * Set new values from student object and move student to correct directory
	 * 
	 * @param userDn
	 * @param student
	 * @param dir
	 * @throws Exception
	 */
	public void enableUser(String userDn, String username, StudentDto student, DirContext dir) throws Exception {
	    // Enable user
	    ModificationItem[] mods = new ModificationItem[2];
        mods[0] =new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userAccountControl", Integer.toString(UF_NORMAL_ACCOUNT + UF_DONT_EXPIRE_PASSWD)));
        mods[1] =new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("description", student.getCurriculumVersionCode()));
        dir.modifyAttributes(userDn, mods);
        // Move user to correct curriculum folder
        String newUserLocation = "CN=" + username + "," + "OU=" + 
                getCurriculumFolder(student.getSchoolDepartment(), student.getCurriculumMerCode(), student.getType()) 
                + ",OU=Tudengid,OU=Kasutajad," 
                + base;
        changeUserData(userDn, username, student, dir);
        dir.rename(userDn, newUserLocation);
        log.info(Messages.USER_ENABLED);
    }
	
	/**
	 * User is already enabled, change its groups and basic data
	 * Do not change user position
	 * 
	 * @param nameInNamespace
	 * @param username
	 * @param student
	 * @param dir
	 */
	private void changeUserData(String userDn, String username, StudentDto student, DirContext dir) {
	    //Get the attribute of user's "memberOf"
	    List<String> groupDn = getUserGroups(username, dir);
        
	    // adding to Tudeng_pilvekonto group
        if (!groupDn.stream().anyMatch(p -> p.contains("Tudeng_pilvekonto"))) {
            addUserToGroup(userDn, "Tudeng_pilvekonto", dir);
        }
        
        // adding to tudengid_adobe group
        if (!groupDn.stream().anyMatch(p -> p.contains("tudengid_adobe")) && "80954".equals(student.getCurriculumMerCode())) {
            addUserToGroup(userDn, "tudengid_adobe", dir);
        }
        
        // adding to structural unit specific group
        String userUpperGroup = getGroupName(student.getSchoolDepartment(), student.getCurriculumMerCode());
        if (userUpperGroup != null && !groupDn.stream().anyMatch(p -> p.contains(userUpperGroup))) {
            addUserToUpperStructuralUnitGroup(userDn, userUpperGroup, dir);
        }
    }
	
	private String getGroupName(String structuralUnit, String merCode) {
        if ("Arhitektuuriinstituut".equals(structuralUnit)) {
            return "Tudengid Arhitektuuriinstituut";
        } else if ("Ehitusinstituut".equals(structuralUnit)) {
            return "Tudengid Ehitusinstituut";
        } else if ("Logistikainstituut".equals(structuralUnit)) {
            return "Tudengid Logistikainstituut";
        } else if ("Tehnoloogia ja ringmajanduse instituut".equals(structuralUnit) && !"80954".equals(merCode)) {
            return "Tudengid Ringmajanduseinstituut";
        } else if ("Teenusmajanduse instituut".equals(structuralUnit)) {
            return "Tudengid Teenusmajandus";
        } else if ("Tehnikainstituut".equals(structuralUnit)) {
            return "Tudengid Tehnikainstituut";
        } else if ("80954".equals(merCode)) {
            return "Tudengid Rõivainstituut";
        }
        return null;
    }
	
	private List<String> getUserGroups(String username, DirContext dir) {
	    SearchControls ctls = new SearchControls();
        ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attrIDs = { "memberOf" };
        ctls.setReturningAttributes(attrIDs);
        List<String> groupDn = new ArrayList<>();
        try {
            NamingEnumeration<SearchResult> user = dir.search(base, "sAMAccountName=" + username, ctls);
            if (user.hasMore()) {
                SearchResult result = user.next();
                Attribute attribute = result.getAttributes().get("memberOf");
                if (attribute != null) {
                    groupDn = (List<String>) Collections.list(attribute.getAll());
                } else {
                    log.error("Cannot get user groups from LDAP server (missing attribute: {})", "memberOf");
                }
            }
        } catch (NamingException e) {
            log.error("Cannot find user with name {} (group search)", username, e);
        }
        return groupDn;
	}
	
	private void removeUserFromAllGroups(String username, String userDn, DirContext dir) {
	    List<String> userGroups = getUserGroups(username, dir);
	    for (String fullGroupName : userGroups) {
	        ModificationItem[] mods = new ModificationItem[1];
	        mods[0] =new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute("member", userDn));
	        try {
	            dir.modifyAttributes(fullGroupName, mods);
	            log.error(Messages.GROUP_REMOVE, fullGroupName);
	        } catch (NamingException e) {
	            log.error(Messages.GROUP_REMOVE_FAILED, fullGroupName);
	        }
	    }
	}

}
