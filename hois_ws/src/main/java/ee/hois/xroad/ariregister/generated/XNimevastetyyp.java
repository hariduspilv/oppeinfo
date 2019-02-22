
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for x_nimevastetyyp.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="x_nimevastetyyp"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="T"/&gt;
 *     &lt;enumeration value="S"/&gt;
 *     &lt;enumeration value="K"/&gt;
 *     &lt;enumeration value="P"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "x_nimevastetyyp")
@XmlEnum
public enum XNimevastetyyp {

    T,
    S,
    K,
    P;

    public String value() {
        return name();
    }

    public static XNimevastetyyp fromValue(String v) {
        return valueOf(v);
    }

}
