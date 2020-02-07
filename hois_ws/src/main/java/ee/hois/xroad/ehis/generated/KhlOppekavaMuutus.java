
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlOppekavaMuutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlOppekavaMuutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="uusOppekava" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="oppeasutusFil" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *           &lt;element name="toimumiskoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *           &lt;element name="opibValismaal" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlOppekavaMuutus", propOrder = {
    "muutusKp",
    "uusOppekava",
    "oppeasutusFil",
    "toimumiskoht",
    "opibValismaal"
})
public class KhlOppekavaMuutus {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger uusOppekava;
    protected String oppeasutusFil;
    protected String toimumiskoht;
    protected String opibValismaal;

    /**
     * Gets the value of the muutusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuutusKp() {
        return muutusKp;
    }

    /**
     * Sets the value of the muutusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuutusKp(XMLGregorianCalendar value) {
        this.muutusKp = value;
    }

    /**
     * Gets the value of the uusOppekava property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUusOppekava() {
        return uusOppekava;
    }

    /**
     * Sets the value of the uusOppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUusOppekava(BigInteger value) {
        this.uusOppekava = value;
    }

    /**
     * Gets the value of the oppeasutusFil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutusFil() {
        return oppeasutusFil;
    }

    /**
     * Sets the value of the oppeasutusFil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutusFil(String value) {
        this.oppeasutusFil = value;
    }

    /**
     * Gets the value of the toimumiskoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToimumiskoht() {
        return toimumiskoht;
    }

    /**
     * Sets the value of the toimumiskoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToimumiskoht(String value) {
        this.toimumiskoht = value;
    }

    /**
     * Gets the value of the opibValismaal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpibValismaal() {
        return opibValismaal;
    }

    /**
     * Sets the value of the opibValismaal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpibValismaal(String value) {
        this.opibValismaal = value;
    }

}
