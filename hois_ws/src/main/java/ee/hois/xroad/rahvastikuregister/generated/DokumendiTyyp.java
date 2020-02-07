
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dokumendiTyyp.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dokumendiTyyp"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ABIELU"/&gt;
 *     &lt;enumeration value="LAHUTUS"/&gt;
 *     &lt;enumeration value="SYND"/&gt;
 *     &lt;enumeration value="SURM"/&gt;
 *     &lt;enumeration value="EESTKOSTE"/&gt;
 *     &lt;enumeration value="LAPSKOHUS"/&gt;
 *     &lt;enumeration value="MUUDOKID"/&gt;
 *     &lt;enumeration value="NIMI"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "dokumendiTyyp")
@XmlEnum
public enum DokumendiTyyp {

    ABIELU,
    LAHUTUS,
    SYND,
    SURM,
    EESTKOSTE,
    LAPSKOHUS,
    MUUDOKID,
    NIMI;

    public String value() {
        return name();
    }

    public static DokumendiTyyp fromValue(String v) {
        return valueOf(v);
    }

}
