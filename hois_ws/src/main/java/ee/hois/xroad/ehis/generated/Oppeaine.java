
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppeaine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppeaine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klOppekeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aineKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="okKood" type="{http://www.w3.org/2001/XMLSchema}integer" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="maht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppeaine", propOrder = {
    "nimetus",
    "klOppekeel",
    "aineKood",
    "okKood",
    "maht"
})
public class Oppeaine {

    @XmlElement(required = true)
    protected String nimetus;
    @XmlElement(required = true)
    protected String klOppekeel;
    protected String aineKood;
    protected List<BigInteger> okKood;
    protected String maht;

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetus(String value) {
        this.nimetus = value;
    }

    /**
     * Gets the value of the klOppekeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppekeel() {
        return klOppekeel;
    }

    /**
     * Sets the value of the klOppekeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppekeel(String value) {
        this.klOppekeel = value;
    }

    /**
     * Gets the value of the aineKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAineKood() {
        return aineKood;
    }

    /**
     * Sets the value of the aineKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAineKood(String value) {
        this.aineKood = value;
    }

    /**
     * Gets the value of the okKood property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the okKood property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOkKood().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getOkKood() {
        if (okKood == null) {
            okKood = new ArrayList<BigInteger>();
        }
        return this.okKood;
    }

    /**
     * Gets the value of the maht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaht() {
        return maht;
    }

    /**
     * Sets the value of the maht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaht(String value) {
        this.maht = value;
    }

}
