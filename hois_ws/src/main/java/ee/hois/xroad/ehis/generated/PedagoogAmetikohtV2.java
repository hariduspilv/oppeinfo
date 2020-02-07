
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukoodid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eisAmetikohadParingList" minOccurs="0"/&gt;
 *         &lt;element name="koolId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kooliaste" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeaine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "isikukoodid",
    "koolId",
    "kooliaste",
    "oppeaine"
})
@XmlRootElement(name = "pedagoogAmetikohtV2")
public class PedagoogAmetikohtV2 {

    protected EisAmetikohadParingList isikukoodid;
    protected BigInteger koolId;
    protected String kooliaste;
    protected String oppeaine;

    /**
     * Gets the value of the isikukoodid property.
     * 
     * @return
     *     possible object is
     *     {@link EisAmetikohadParingList }
     *     
     */
    public EisAmetikohadParingList getIsikukoodid() {
        return isikukoodid;
    }

    /**
     * Sets the value of the isikukoodid property.
     * 
     * @param value
     *     allowed object is
     *     {@link EisAmetikohadParingList }
     *     
     */
    public void setIsikukoodid(EisAmetikohadParingList value) {
        this.isikukoodid = value;
    }

    /**
     * Gets the value of the koolId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKoolId() {
        return koolId;
    }

    /**
     * Sets the value of the koolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKoolId(BigInteger value) {
        this.koolId = value;
    }

    /**
     * Gets the value of the kooliaste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKooliaste() {
        return kooliaste;
    }

    /**
     * Sets the value of the kooliaste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKooliaste(String value) {
        this.kooliaste = value;
    }

    /**
     * Gets the value of the oppeaine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeaine() {
        return oppeaine;
    }

    /**
     * Sets the value of the oppeaine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeaine(String value) {
        this.oppeaine = value;
    }

}
