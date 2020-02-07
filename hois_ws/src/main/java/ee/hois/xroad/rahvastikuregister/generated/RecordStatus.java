
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecordStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RecordStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="K"/&gt;
 *     &lt;enumeration value="A"/&gt;
 *     &lt;enumeration value="O"/&gt;
 *     &lt;enumeration value="V"/&gt;
 *     &lt;enumeration value="S"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RecordStatus")
@XmlEnum
public enum RecordStatus {

    K,
    A,
    O,
    V,
    S;

    public String value() {
        return name();
    }

    public static RecordStatus fromValue(String v) {
        return valueOf(v);
    }

}
