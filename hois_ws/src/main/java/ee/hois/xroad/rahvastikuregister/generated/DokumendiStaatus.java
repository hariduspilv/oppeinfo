
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dokumendi_staatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dokumendi_staatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="D1"/&gt;
 *     &lt;enumeration value="D2"/&gt;
 *     &lt;enumeration value="D3"/&gt;
 *     &lt;enumeration value="D6"/&gt;
 *     &lt;enumeration value="D10"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "dokumendi_staatus")
@XmlEnum
public enum DokumendiStaatus {

    @XmlEnumValue("D1")
    D_1("D1"),
    @XmlEnumValue("D2")
    D_2("D2"),
    @XmlEnumValue("D3")
    D_3("D3"),
    @XmlEnumValue("D6")
    D_6("D6"),
    @XmlEnumValue("D10")
    D_10("D10");
    private final String value;

    DokumendiStaatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DokumendiStaatus fromValue(String v) {
        for (DokumendiStaatus c: DokumendiStaatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
