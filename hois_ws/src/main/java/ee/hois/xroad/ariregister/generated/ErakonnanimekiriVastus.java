
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for erakonnanimekiri_vastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="erakonnanimekiri_vastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esit_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lehekylgede_arv" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="liikmed" type="{http://arireg.x-road.eu/producer/}erakonnanimekiri_liikmed"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "erakonnanimekiri_vastus", propOrder = {
    "kood",
    "nimi",
    "esitKpv",
    "lehekylgedeArv",
    "liikmed"
})
public class ErakonnanimekiriVastus {

    @XmlElement(required = true)
    protected BigInteger kood;
    @XmlElement(required = true)
    protected String nimi;
    @XmlElement(name = "esit_kpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esitKpv;
    @XmlElement(name = "lehekylgede_arv", required = true)
    protected BigInteger lehekylgedeArv;
    @XmlElement(required = true)
    protected ErakonnanimekiriLiikmed liikmed;

    /**
     * Gets the value of the kood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKood() {
        return kood;
    }

    /**
     * Sets the value of the kood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKood(BigInteger value) {
        this.kood = value;
    }

    /**
     * Gets the value of the nimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Sets the value of the nimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimi(String value) {
        this.nimi = value;
    }

    /**
     * Gets the value of the esitKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitKpv() {
        return esitKpv;
    }

    /**
     * Sets the value of the esitKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitKpv(XMLGregorianCalendar value) {
        this.esitKpv = value;
    }

    /**
     * Gets the value of the lehekylgedeArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLehekylgedeArv() {
        return lehekylgedeArv;
    }

    /**
     * Sets the value of the lehekylgedeArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLehekylgedeArv(BigInteger value) {
        this.lehekylgedeArv = value;
    }

    /**
     * Gets the value of the liikmed property.
     * 
     * @return
     *     possible object is
     *     {@link ErakonnanimekiriLiikmed }
     *     
     */
    public ErakonnanimekiriLiikmed getLiikmed() {
        return liikmed;
    }

    /**
     * Sets the value of the liikmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErakonnanimekiriLiikmed }
     *     
     */
    public void setLiikmed(ErakonnanimekiriLiikmed value) {
        this.liikmed = value;
    }

}
