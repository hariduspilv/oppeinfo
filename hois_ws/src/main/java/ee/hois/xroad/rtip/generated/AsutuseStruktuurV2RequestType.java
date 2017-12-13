
package ee.hois.xroad.rtip.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for asutuseStruktuurV2RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="asutuseStruktuurV2RequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="KEHTIV_ALATES" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_KUNI" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="ONLINE" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="STRUKTUURI_ALGUS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="8"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "asutuseStruktuurV2RequestType", propOrder = {

})
public class AsutuseStruktuurV2RequestType {

    @XmlElement(name = "KEHTIV_ALATES", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivalates;
    @XmlElement(name = "KEHTIV_KUNI", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivkuni;
    @XmlElement(name = "ONLINE")
    protected String online;
    @XmlElement(name = "STRUKTUURI_ALGUS")
    protected String struktuurialgus;

    /**
     * Gets the value of the kehtivalates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKEHTIVALATES() {
        return kehtivalates;
    }

    /**
     * Sets the value of the kehtivalates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEHTIVALATES(LocalDate value) {
        this.kehtivalates = value;
    }

    /**
     * Gets the value of the kehtivkuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKEHTIVKUNI() {
        return kehtivkuni;
    }

    /**
     * Sets the value of the kehtivkuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEHTIVKUNI(LocalDate value) {
        this.kehtivkuni = value;
    }

    /**
     * Gets the value of the online property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getONLINE() {
        return online;
    }

    /**
     * Sets the value of the online property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setONLINE(String value) {
        this.online = value;
    }

    /**
     * Gets the value of the struktuurialgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTRUKTUURIALGUS() {
        return struktuurialgus;
    }

    /**
     * Sets the value of the struktuurialgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTRUKTUURIALGUS(String value) {
        this.struktuurialgus = value;
    }

}
