
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for suhte_tyyp.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="suhte_tyyp"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="R1"/&gt;
 *     &lt;enumeration value="R2"/&gt;
 *     &lt;enumeration value="R3"/&gt;
 *     &lt;enumeration value="R4"/&gt;
 *     &lt;enumeration value="R5"/&gt;
 *     &lt;enumeration value="R6"/&gt;
 *     &lt;enumeration value="R11"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "suhte_tyyp")
@XmlEnum
public enum SuhteTyyp {

    @XmlEnumValue("R1")
    R_1("R1"),
    @XmlEnumValue("R2")
    R_2("R2"),
    @XmlEnumValue("R3")
    R_3("R3"),
    @XmlEnumValue("R4")
    R_4("R4"),
    @XmlEnumValue("R5")
    R_5("R5"),
    @XmlEnumValue("R6")
    R_6("R6"),
    @XmlEnumValue("R11")
    R_11("R11");
    private final String value;

    SuhteTyyp(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SuhteTyyp fromValue(String v) {
        for (SuhteTyyp c: SuhteTyyp.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
