
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for x_regkaardi_kehtivus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="x_regkaardi_kehtivus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="K"/&gt;
 *     &lt;enumeration value="M"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "x_regkaardi_kehtivus")
@XmlEnum
public enum XRegkaardiKehtivus {

    K,
    M;

    public String value() {
        return name();
    }

    public static XRegkaardiKehtivus fromValue(String v) {
        return valueOf(v);
    }

}
