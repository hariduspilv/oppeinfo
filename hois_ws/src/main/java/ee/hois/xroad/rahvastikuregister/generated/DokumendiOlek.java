
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dokumendi_olek.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dokumendi_olek"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="kehtiv"/&gt;
 *     &lt;enumeration value="kehtetu"/&gt;
 *     &lt;enumeration value="tyhistatud"/&gt;
 *     &lt;enumeration value="tyhine"/&gt;
 *     &lt;enumeration value="lopp"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "dokumendi_olek")
@XmlEnum
public enum DokumendiOlek {

    @XmlEnumValue("kehtiv")
    KEHTIV("kehtiv"),
    @XmlEnumValue("kehtetu")
    KEHTETU("kehtetu"),
    @XmlEnumValue("tyhistatud")
    TYHISTATUD("tyhistatud"),
    @XmlEnumValue("tyhine")
    TYHINE("tyhine"),
    @XmlEnumValue("lopp")
    LOPP("lopp");
    private final String value;

    DokumendiOlek(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DokumendiOlek fromValue(String v) {
        for (DokumendiOlek c: DokumendiOlek.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
