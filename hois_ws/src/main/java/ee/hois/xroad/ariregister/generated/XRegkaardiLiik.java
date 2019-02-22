
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for x_regkaardi_liik.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="x_regkaardi_liik"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="R"/&gt;
 *     &lt;enumeration value="K"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "x_regkaardi_liik")
@XmlEnum
public enum XRegkaardiLiik {

    R,
    K;

    public String value() {
        return name();
    }

    public static XRegkaardiLiik fromValue(String v) {
        return valueOf(v);
    }

}
