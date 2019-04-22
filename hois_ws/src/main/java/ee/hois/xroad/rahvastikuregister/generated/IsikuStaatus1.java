
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for isiku_staatus1.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="isiku_staatus1"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="E"/&gt;
 *     &lt;enumeration value="S"/&gt;
 *     &lt;enumeration value="P"/&gt;
 *     &lt;enumeration value="K"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "isiku_staatus1")
@XmlEnum
public enum IsikuStaatus1 {

    E,
    S,
    P,
    K;

    public String value() {
        return name();
    }

    public static IsikuStaatus1 fromValue(String v) {
        return valueOf(v);
    }

}
