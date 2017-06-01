
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sugulusaste.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sugulusaste"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="EMA"/&gt;
 *     &lt;enumeration value="ISA"/&gt;
 *     &lt;enumeration value="ODE"/&gt;
 *     &lt;enumeration value="VEND"/&gt;
 *     &lt;enumeration value="POOLODE"/&gt;
 *     &lt;enumeration value="POOLVEND"/&gt;
 *     &lt;enumeration value="ABIKAASA"/&gt;
 *     &lt;enumeration value="LAPS"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "sugulusaste")
@XmlEnum
public enum Sugulusaste {

    EMA,
    ISA,
    ODE,
    VEND,
    POOLODE,
    POOLVEND,
    ABIKAASA,
    LAPS;

    public String value() {
        return name();
    }

    public static Sugulusaste fromValue(String v) {
        return valueOf(v);
    }

}
