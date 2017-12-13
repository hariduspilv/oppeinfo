package ee.hitsa.ois.util;

import ee.hitsa.ois.domain.Enterprise;

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
        return name + " (" + regCode + ")";
    }
}
