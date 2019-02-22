
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for x_formaat.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="x_formaat"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="tekst"/&gt;
 *     &lt;enumeration value="html"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "x_formaat")
@XmlEnum
public enum XFormaat {

    @XmlEnumValue("tekst")
    TEKST("tekst"),
    @XmlEnumValue("html")
    HTML("html");
    private final String value;

    XFormaat(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static XFormaat fromValue(String v) {
        for (XFormaat c: XFormaat.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
