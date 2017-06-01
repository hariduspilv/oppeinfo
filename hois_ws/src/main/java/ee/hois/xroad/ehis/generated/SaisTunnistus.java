
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for saisTunnistus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saisTunnistus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tunnistuseNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kuup" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="hinded" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}saisPohiHinded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saisTunnistus", propOrder = {
    "oppeasutuseKood",
    "oppeasutuseNimi",
    "tunnistuseNr",
    "kuup",
    "hinded"
})
public class SaisTunnistus {

    @XmlElement(required = true, nillable = true)
    protected String oppeasutuseKood;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutuseNimi;
    @XmlElement(required = true, nillable = true)
    protected String tunnistuseNr;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kuup;
    @XmlElement(required = true, nillable = true)
    protected SaisPohiHinded hinded;

    /**
     * Gets the value of the oppeasutuseKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseKood() {
        return oppeasutuseKood;
    }

    /**
     * Sets the value of the oppeasutuseKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseKood(String value) {
        this.oppeasutuseKood = value;
    }

    /**
     * Gets the value of the oppeasutuseNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseNimi() {
        return oppeasutuseNimi;
    }

    /**
     * Sets the value of the oppeasutuseNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseNimi(String value) {
        this.oppeasutuseNimi = value;
    }

    /**
     * Gets the value of the tunnistuseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTunnistuseNr() {
        return tunnistuseNr;
    }

    /**
     * Sets the value of the tunnistuseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTunnistuseNr(String value) {
        this.tunnistuseNr = value;
    }

    /**
     * Gets the value of the kuup property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKuup() {
        return kuup;
    }

    /**
     * Sets the value of the kuup property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKuup(XMLGregorianCalendar value) {
        this.kuup = value;
    }

    /**
     * Gets the value of the hinded property.
     * 
     * @return
     *     possible object is
     *     {@link SaisPohiHinded }
     *     
     */
    public SaisPohiHinded getHinded() {
        return hinded;
    }

    /**
     * Sets the value of the hinded property.
     * 
     * @param value
     *     allowed object is
     *     {@link SaisPohiHinded }
     *     
     */
    public void setHinded(SaisPohiHinded value) {
        this.hinded = value;
    }

}
