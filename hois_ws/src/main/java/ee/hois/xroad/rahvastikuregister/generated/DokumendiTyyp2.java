
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dokumendiTyyp2.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dokumendiTyyp2"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="D1"/&gt;
 *     &lt;enumeration value="D67"/&gt;
 *     &lt;enumeration value="D50"/&gt;
 *     &lt;enumeration value="D27"/&gt;
 *     &lt;enumeration value="L9"/&gt;
 *     &lt;enumeration value="L69"/&gt;
 *     &lt;enumeration value="D149"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "dokumendiTyyp2")
@XmlEnum
public enum DokumendiTyyp2 {

    @XmlEnumValue("D1")
    D_1("D1"),
    @XmlEnumValue("D67")
    D_67("D67"),
    @XmlEnumValue("D50")
    D_50("D50"),
    @XmlEnumValue("D27")
    D_27("D27"),
    @XmlEnumValue("L9")
    L_9("L9"),
    @XmlEnumValue("L69")
    L_69("L69"),
    @XmlEnumValue("D149")
    D_149("D149");
    private final String value;

    DokumendiTyyp2(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DokumendiTyyp2 fromValue(String v) {
        for (DokumendiTyyp2 c: DokumendiTyyp2 .values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
