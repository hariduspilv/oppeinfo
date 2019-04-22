
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRSurmSyndmusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RRSurmSyndmusType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="V"/&gt;
 *     &lt;enumeration value="M"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RRSurmSyndmusType")
@XmlEnum
public enum RRSurmSyndmusType {

    V,
    M;

    public String value() {
        return name();
    }

    public static RRSurmSyndmusType fromValue(String v) {
        return valueOf(v);
    }

}
