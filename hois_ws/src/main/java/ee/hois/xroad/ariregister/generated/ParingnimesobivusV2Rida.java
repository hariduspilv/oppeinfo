
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringnimesobivus_v2_rida complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringnimesobivus_v2_rida"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tyyp" type="{http://arireg.x-road.eu/producer/}x_nimevastetyyp"/&gt;
 *         &lt;element name="tekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringnimesobivus_v2_rida", propOrder = {
    "tyyp",
    "tekst"
})
public class ParingnimesobivusV2Rida {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected XNimevastetyyp tyyp;
    @XmlElement(required = true)
    protected String tekst;

    /**
     * Gets the value of the tyyp property.
     * 
     * @return
     *     possible object is
     *     {@link XNimevastetyyp }
     *     
     */
    public XNimevastetyyp getTyyp() {
        return tyyp;
    }

    /**
     * Sets the value of the tyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XNimevastetyyp }
     *     
     */
    public void setTyyp(XNimevastetyyp value) {
        this.tyyp = value;
    }

    /**
     * Gets the value of the tekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTekst() {
        return tekst;
    }

    /**
     * Sets the value of the tekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTekst(String value) {
        this.tekst = value;
    }

}
