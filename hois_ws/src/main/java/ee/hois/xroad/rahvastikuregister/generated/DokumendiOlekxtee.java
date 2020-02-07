
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dokumendi_olekxtee.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dokumendi_olekxtee"&gt;
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
@XmlType(name = "dokumendi_olekxtee")
@XmlEnum
public enum DokumendiOlekxtee {

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

    DokumendiOlekxtee(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DokumendiOlekxtee fromValue(String v) {
        for (DokumendiOlekxtee c: DokumendiOlekxtee.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
