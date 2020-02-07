
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for YeNo.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="YeNo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Jah"/&gt;
 *     &lt;enumeration value="Ei"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "YeNo")
@XmlEnum
public enum YeNo {

    @XmlEnumValue("Jah")
    JAH("Jah"),
    @XmlEnumValue("Ei")
    EI("Ei");
    private final String value;

    YeNo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static YeNo fromValue(String v) {
        for (YeNo c: YeNo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
