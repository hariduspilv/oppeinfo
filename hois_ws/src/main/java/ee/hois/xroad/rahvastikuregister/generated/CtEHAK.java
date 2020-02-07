
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ctEHAK complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ctEHAK"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EhakTyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IsikuteArv" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="AlamEHAKud" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="EHAK" type="{http://rr.x-road.eu/producer}ctEHAK" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctEHAK", propOrder = {
    "ehakTyyp",
    "kood",
    "nimetus",
    "isikuteArv",
    "alamEHAKud"
})
public class CtEHAK {

    @XmlElement(name = "EhakTyyp", required = true)
    protected String ehakTyyp;
    @XmlElement(name = "Kood", required = true)
    protected String kood;
    @XmlElement(name = "Nimetus", required = true)
    protected String nimetus;
    @XmlElement(name = "IsikuteArv", required = true)
    protected BigInteger isikuteArv;
    @XmlElement(name = "AlamEHAKud")
    protected CtEHAK.AlamEHAKud alamEHAKud;

    /**
     * Gets the value of the ehakTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEhakTyyp() {
        return ehakTyyp;
    }

    /**
     * Sets the value of the ehakTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEhakTyyp(String value) {
        this.ehakTyyp = value;
    }

    /**
     * Gets the value of the kood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKood() {
        return kood;
    }

    /**
     * Sets the value of the kood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKood(String value) {
        this.kood = value;
    }

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
     * Gets the value of the isikuteArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIsikuteArv() {
        return isikuteArv;
    }

    /**
     * Sets the value of the isikuteArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIsikuteArv(BigInteger value) {
        this.isikuteArv = value;
    }

    /**
     * Gets the value of the alamEHAKud property.
     * 
     * @return
     *     possible object is
     *     {@link CtEHAK.AlamEHAKud }
     *     
     */
    public CtEHAK.AlamEHAKud getAlamEHAKud() {
        return alamEHAKud;
    }

    /**
     * Sets the value of the alamEHAKud property.
     * 
     * @param value
     *     allowed object is
     *     {@link CtEHAK.AlamEHAKud }
     *     
     */
    public void setAlamEHAKud(CtEHAK.AlamEHAKud value) {
        this.alamEHAKud = value;
    }


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
     *         &lt;element name="EHAK" type="{http://rr.x-road.eu/producer}ctEHAK" maxOccurs="unbounded"/&gt;
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
        "ehak"
    })
    public static class AlamEHAKud {

        @XmlElement(name = "EHAK", required = true)
        protected List<CtEHAK> ehak;

        /**
         * Gets the value of the ehak property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ehak property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEHAK().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CtEHAK }
         * 
         * 
         */
        public List<CtEHAK> getEHAK() {
            if (ehak == null) {
                ehak = new ArrayList<CtEHAK>();
            }
            return this.ehak;
        }

    }

}
