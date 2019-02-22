
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for x_evk_syndmus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="x_evk_syndmus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ESMAREG"/&gt;
 *     &lt;enumeration value="KAPMUUT"/&gt;
 *     &lt;enumeration value="POHMUUT"/&gt;
 *     &lt;enumeration value="ARINMUUT"/&gt;
 *     &lt;enumeration value="ETVMUUT"/&gt;
 *     &lt;enumeration value="YHIN"/&gt;
 *     &lt;enumeration value="JAGUN"/&gt;
 *     &lt;enumeration value="REGKUST"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "x_evk_syndmus")
@XmlEnum
public enum XEvkSyndmus {


    /**
     * esmaregistreerimine
     * 
     */
    ESMAREG,

    /**
     * aktsia- v�i osakapitali suurendamine v�i v�hendamine
     * 
     */
    KAPMUUT,

    /**
     * p�hikirja muutmine
     * 
     */
    POHMUUT,

    /**
     * �rinime muutmine
     * 
     */
    ARINMUUT,

    /**
     * ettev�tlusvormi muutmine
     * 
     */
    ETVMUUT,

    /**
     * �hinemine
     * 
     */
    YHIN,

    /**
     * jagunemine
     * 
     */
    JAGUN,

    /**
     * registrist kustutamine
     * 
     */
    REGKUST;

    public String value() {
        return name();
    }

    public static XEvkSyndmus fromValue(String v) {
        return valueOf(v);
    }

}
