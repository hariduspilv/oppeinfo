package ee.hitsa.ois.util;

import org.apache.commons.lang3.StringUtils;

import ee.hitsa.ois.domain.enterprise.Enterprise;

public abstract class EnterpriseUtil {

    /**
     * @param enterprise
     * @return "enterprise.name (enterprise.regCode)"
     */
    public static String getName(Enterprise enterprise) {
        return getName(enterprise.getName(), enterprise.getRegCode());
    }

    /**
     * @param name
     * @param regCode
     * @return "name (regCode)"
     */
    public static String getName(String name, String regCode) {
        if (StringUtils.isEmpty(regCode)) {
            return name;
        }
        return name + " (" + regCode + ")";
    }
}
