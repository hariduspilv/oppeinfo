
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RIAKontaktType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RIAKontaktType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="T"/&gt;
 *     &lt;enumeration value="E"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RIAKontaktType")
@XmlEnum
public enum RIAKontaktType {

    T,
    E;

    public String value() {
        return name();
    }

    public static RIAKontaktType fromValue(String v) {
        return valueOf(v);
    }

}
