
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for suhte_staatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="suhte_staatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SU1"/&gt;
 *     &lt;enumeration value="SU2"/&gt;
 *     &lt;enumeration value="SU3"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "suhte_staatus")
@XmlEnum
public enum SuhteStaatus {

    @XmlEnumValue("SU1")
    SU_1("SU1"),
    @XmlEnumValue("SU2")
    SU_2("SU2"),
    @XmlEnumValue("SU3")
    SU_3("SU3");
    private final String value;

    SuhteStaatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SuhteStaatus fromValue(String v) {
        for (SuhteStaatus c: SuhteStaatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
