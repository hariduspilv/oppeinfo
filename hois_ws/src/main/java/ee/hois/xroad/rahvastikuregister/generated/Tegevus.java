
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Tegevus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Tegevus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="V"/&gt;
 *     &lt;enumeration value="C"/&gt;
 *     &lt;enumeration value="M"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Tegevus")
@XmlEnum
public enum Tegevus {

    V,
    C,
    M;

    public String value() {
        return name();
    }

    public static Tegevus fromValue(String v) {
        return valueOf(v);
    }

}
