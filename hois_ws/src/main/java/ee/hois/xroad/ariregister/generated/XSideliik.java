
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for x_sideliik.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="x_sideliik"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="EMAIL"/&gt;
 *     &lt;enumeration value="FAX"/&gt;
 *     &lt;enumeration value="WWW"/&gt;
 *     &lt;enumeration value="MOB"/&gt;
 *     &lt;enumeration value="MOD"/&gt;
 *     &lt;enumeration value="MUU"/&gt;
 *     &lt;enumeration value="TEL"/&gt;
 *     &lt;enumeration value="TELEX"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "x_sideliik")
@XmlEnum
public enum XSideliik {

    EMAIL,
    FAX,
    WWW,
    MOB,
    MOD,
    MUU,
    TEL,
    TELEX;

    public String value() {
        return name();
    }

    public static XSideliik fromValue(String v) {
        return valueOf(v);
    }

}
