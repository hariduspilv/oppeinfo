
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for enum_earveEemaldaKlient_v1Response_Klient_Staatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="enum_earveEemaldaKlient_v1Response_Klient_Staatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="OK"/&gt;
 *     &lt;enumeration value="SE"/&gt;
 *     &lt;enumeration value="MR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enum_earveEemaldaKlient_v1Response_Klient_Staatus")
@XmlEnum
public enum EnumEarveEemaldaKlientV1ResponseKlientStaatus {

    OK,
    SE,
    MR;

    public String value() {
        return name();
    }

    public static EnumEarveEemaldaKlientV1ResponseKlientStaatus fromValue(String v) {
        return valueOf(v);
    }

}
