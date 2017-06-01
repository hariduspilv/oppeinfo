
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppejoud complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppejoud"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="koolId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *           &lt;element name="isikuandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppejoudIsikuandmed"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="telefon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ametikoht" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppejoudAmetikoht" maxOccurs="50" minOccurs="0"/&gt;
 *         &lt;element name="kvalifikatsioon" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppejoudKvalifikatsioon" maxOccurs="50" minOccurs="0"/&gt;
 *         &lt;element name="lyhiajalineMobiilsus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppejoudLyhiajalineMobiilsus" maxOccurs="50" minOccurs="0"/&gt;
 *         &lt;element name="teaduseriala" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="50" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppejoud", propOrder = {
    "koolId",
    "isikukood",
    "isikuandmed",
    "telefon",
    "email",
    "ametikoht",
    "kvalifikatsioon",
    "lyhiajalineMobiilsus",
    "teaduseriala"
})
public class Oppejoud {

    @XmlElement(required = true)
    protected BigInteger koolId;
    protected String isikukood;
    protected OppejoudIsikuandmed isikuandmed;
    protected String telefon;
    protected String email;
    protected List<OppejoudAmetikoht> ametikoht;
    protected List<OppejoudKvalifikatsioon> kvalifikatsioon;
    @XmlElement(nillable = true)
    protected List<OppejoudLyhiajalineMobiilsus> lyhiajalineMobiilsus;
    protected List<String> teaduseriala;

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
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the isikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link OppejoudIsikuandmed }
     *     
     */
    public OppejoudIsikuandmed getIsikuandmed() {
        return isikuandmed;
    }

    /**
     * Sets the value of the isikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link OppejoudIsikuandmed }
     *     
     */
    public void setIsikuandmed(OppejoudIsikuandmed value) {
        this.isikuandmed = value;
    }

    /**
     * Gets the value of the telefon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Sets the value of the telefon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefon(String value) {
        this.telefon = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the ametikoht property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ametikoht property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAmetikoht().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OppejoudAmetikoht }
     * 
     * 
     */
    public List<OppejoudAmetikoht> getAmetikoht() {
        if (ametikoht == null) {
            ametikoht = new ArrayList<OppejoudAmetikoht>();
        }
        return this.ametikoht;
    }

    /**
     * Gets the value of the kvalifikatsioon property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kvalifikatsioon property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKvalifikatsioon().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OppejoudKvalifikatsioon }
     * 
     * 
     */
    public List<OppejoudKvalifikatsioon> getKvalifikatsioon() {
        if (kvalifikatsioon == null) {
            kvalifikatsioon = new ArrayList<OppejoudKvalifikatsioon>();
        }
        return this.kvalifikatsioon;
    }

    /**
     * Gets the value of the lyhiajalineMobiilsus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lyhiajalineMobiilsus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLyhiajalineMobiilsus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OppejoudLyhiajalineMobiilsus }
     * 
     * 
     */
    public List<OppejoudLyhiajalineMobiilsus> getLyhiajalineMobiilsus() {
        if (lyhiajalineMobiilsus == null) {
            lyhiajalineMobiilsus = new ArrayList<OppejoudLyhiajalineMobiilsus>();
        }
        return this.lyhiajalineMobiilsus;
    }

    /**
     * Gets the value of the teaduseriala property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the teaduseriala property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTeaduseriala().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTeaduseriala() {
        if (teaduseriala == null) {
            teaduseriala = new ArrayList<String>();
        }
        return this.teaduseriala;
    }

}
