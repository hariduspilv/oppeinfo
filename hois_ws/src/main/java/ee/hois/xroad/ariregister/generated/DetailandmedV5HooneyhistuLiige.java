
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_hooneyhistu_liige complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_hooneyhistu_liige"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kirje_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="liikmesuse_nr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pindala" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="maksumaara_lugeja" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="maksumaara_nimetaja" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="algus_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="lopp_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="liikmed" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_hy_liikmed" minOccurs="0"/&gt;
 *         &lt;element name="kitsendused" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_hy_kitsendused" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_hooneyhistu_liige", propOrder = {
    "kirjeId",
    "liikmesuseNr",
    "pindala",
    "maksumaaraLugeja",
    "maksumaaraNimetaja",
    "algusKpv",
    "loppKpv",
    "liikmed",
    "kitsendused"
})
public class DetailandmedV5HooneyhistuLiige {

    @XmlElement(name = "kirje_id")
    protected BigInteger kirjeId;
    @XmlElement(name = "liikmesuse_nr")
    protected String liikmesuseNr;
    @XmlElementRef(name = "pindala", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<BigDecimal> pindala;
    @XmlElement(name = "maksumaara_lugeja")
    protected BigInteger maksumaaraLugeja;
    @XmlElement(name = "maksumaara_nimetaja")
    protected BigInteger maksumaaraNimetaja;
    @XmlElement(name = "algus_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKpv;
    @XmlElement(name = "lopp_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKpv;
    protected DetailandmedV5HyLiikmed liikmed;
    protected DetailandmedV5HyKitsendused kitsendused;

    /**
     * Gets the value of the kirjeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKirjeId() {
        return kirjeId;
    }

    /**
     * Sets the value of the kirjeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKirjeId(BigInteger value) {
        this.kirjeId = value;
    }

    /**
     * Gets the value of the liikmesuseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiikmesuseNr() {
        return liikmesuseNr;
    }

    /**
     * Sets the value of the liikmesuseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiikmesuseNr(String value) {
        this.liikmesuseNr = value;
    }

    /**
     * Gets the value of the pindala property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getPindala() {
        return pindala;
    }

    /**
     * Sets the value of the pindala property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setPindala(JAXBElement<BigDecimal> value) {
        this.pindala = value;
    }

    /**
     * Gets the value of the maksumaaraLugeja property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaksumaaraLugeja() {
        return maksumaaraLugeja;
    }

    /**
     * Sets the value of the maksumaaraLugeja property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaksumaaraLugeja(BigInteger value) {
        this.maksumaaraLugeja = value;
    }

    /**
     * Gets the value of the maksumaaraNimetaja property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaksumaaraNimetaja() {
        return maksumaaraNimetaja;
    }

    /**
     * Sets the value of the maksumaaraNimetaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaksumaaraNimetaja(BigInteger value) {
        this.maksumaaraNimetaja = value;
    }

    /**
     * Gets the value of the algusKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlgusKpv() {
        return algusKpv;
    }

    /**
     * Sets the value of the algusKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlgusKpv(XMLGregorianCalendar value) {
        this.algusKpv = value;
    }

    /**
     * Gets the value of the loppKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoppKpv() {
        return loppKpv;
    }

    /**
     * Sets the value of the loppKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoppKpv(XMLGregorianCalendar value) {
        this.loppKpv = value;
    }

    /**
     * Gets the value of the liikmed property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5HyLiikmed }
     *     
     */
    public DetailandmedV5HyLiikmed getLiikmed() {
        return liikmed;
    }

    /**
     * Sets the value of the liikmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5HyLiikmed }
     *     
     */
    public void setLiikmed(DetailandmedV5HyLiikmed value) {
        this.liikmed = value;
    }

    /**
     * Gets the value of the kitsendused property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5HyKitsendused }
     *     
     */
    public DetailandmedV5HyKitsendused getKitsendused() {
        return kitsendused;
    }

    /**
     * Sets the value of the kitsendused property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5HyKitsendused }
     *     
     */
    public void setKitsendused(DetailandmedV5HyKitsendused value) {
        this.kitsendused = value;
    }

}
