
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for pedagoogTaiendkoolitus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pedagoogTaiendkoolitus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="taiendOppeas" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klTaiendDoc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="taiendDocKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="klTaiendValdkond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="taiendkoolitus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="taiendkoolitusMahtH" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="taiendDocNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="onTaiendvalisriigis" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="taiendValisriigisSisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pedagoogTaiendkoolitus", propOrder = {
    "taiendOppeas",
    "klTaiendDoc",
    "taiendDocKp",
    "klTaiendValdkond",
    "taiendkoolitus",
    "taiendkoolitusMahtH",
    "taiendDocNr",
    "onTaiendvalisriigis",
    "taiendValisriigisSisu"
})
public class PedagoogTaiendkoolitus {

    @XmlElement(required = true)
    protected String taiendOppeas;
    @XmlElement(required = true)
    protected String klTaiendDoc;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar taiendDocKp;
    @XmlElement(required = true)
    protected String klTaiendValdkond;
    @XmlElement(required = true)
    protected String taiendkoolitus;
    @XmlElement(required = true)
    protected BigInteger taiendkoolitusMahtH;
    protected String taiendDocNr;
    protected String onTaiendvalisriigis;
    protected String taiendValisriigisSisu;

    /**
     * Gets the value of the taiendOppeas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaiendOppeas() {
        return taiendOppeas;
    }

    /**
     * Sets the value of the taiendOppeas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaiendOppeas(String value) {
        this.taiendOppeas = value;
    }

    /**
     * Gets the value of the klTaiendDoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlTaiendDoc() {
        return klTaiendDoc;
    }

    /**
     * Sets the value of the klTaiendDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlTaiendDoc(String value) {
        this.klTaiendDoc = value;
    }

    /**
     * Gets the value of the taiendDocKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTaiendDocKp() {
        return taiendDocKp;
    }

    /**
     * Sets the value of the taiendDocKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTaiendDocKp(XMLGregorianCalendar value) {
        this.taiendDocKp = value;
    }

    /**
     * Gets the value of the klTaiendValdkond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlTaiendValdkond() {
        return klTaiendValdkond;
    }

    /**
     * Sets the value of the klTaiendValdkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlTaiendValdkond(String value) {
        this.klTaiendValdkond = value;
    }

    /**
     * Gets the value of the taiendkoolitus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaiendkoolitus() {
        return taiendkoolitus;
    }

    /**
     * Sets the value of the taiendkoolitus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaiendkoolitus(String value) {
        this.taiendkoolitus = value;
    }

    /**
     * Gets the value of the taiendkoolitusMahtH property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTaiendkoolitusMahtH() {
        return taiendkoolitusMahtH;
    }

    /**
     * Sets the value of the taiendkoolitusMahtH property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTaiendkoolitusMahtH(BigInteger value) {
        this.taiendkoolitusMahtH = value;
    }

    /**
     * Gets the value of the taiendDocNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaiendDocNr() {
        return taiendDocNr;
    }

    /**
     * Sets the value of the taiendDocNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaiendDocNr(String value) {
        this.taiendDocNr = value;
    }

    /**
     * Gets the value of the onTaiendvalisriigis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnTaiendvalisriigis() {
        return onTaiendvalisriigis;
    }

    /**
     * Sets the value of the onTaiendvalisriigis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnTaiendvalisriigis(String value) {
        this.onTaiendvalisriigis = value;
    }

    /**
     * Gets the value of the taiendValisriigisSisu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaiendValisriigisSisu() {
        return taiendValisriigisSisu;
    }

    /**
     * Sets the value of the taiendValisriigisSisu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaiendValisriigisSisu(String value) {
        this.taiendValisriigisSisu = value;
    }

}
