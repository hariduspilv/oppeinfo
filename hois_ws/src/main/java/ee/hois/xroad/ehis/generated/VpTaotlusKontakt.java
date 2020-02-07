
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="taotlejaIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="taotluseId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="nonResidentSissetulek" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}floatOrNothing" minOccurs="0"/&gt;
 *         &lt;element name="nonResidentFailid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}FailInfoDto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="lisatudIsikuteSissetulek" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}floatOrNothing" minOccurs="0"/&gt;
 *         &lt;element name="lisatudFailid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}FailInfoDto" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "taotlejaIsikukood",
    "taotluseId",
    "nonResidentSissetulek",
    "nonResidentFailid",
    "lisatudIsikuteSissetulek",
    "lisatudFailid"
})
@XmlRootElement(name = "vpTaotlusKontakt")
public class VpTaotlusKontakt {

    @XmlElement(required = true)
    protected String taotlejaIsikukood;
    protected long taotluseId;
    @XmlElementRef(name = "nonResidentSissetulek", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nonResidentSissetulek;
    @XmlElement(nillable = true)
    protected List<FailInfoDto> nonResidentFailid;
    @XmlElementRef(name = "lisatudIsikuteSissetulek", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lisatudIsikuteSissetulek;
    @XmlElement(nillable = true)
    protected List<FailInfoDto> lisatudFailid;

    /**
     * Gets the value of the taotlejaIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaotlejaIsikukood() {
        return taotlejaIsikukood;
    }

    /**
     * Sets the value of the taotlejaIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaotlejaIsikukood(String value) {
        this.taotlejaIsikukood = value;
    }

    /**
     * Gets the value of the taotluseId property.
     * 
     */
    public long getTaotluseId() {
        return taotluseId;
    }

    /**
     * Sets the value of the taotluseId property.
     * 
     */
    public void setTaotluseId(long value) {
        this.taotluseId = value;
    }

    /**
     * Gets the value of the nonResidentSissetulek property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNonResidentSissetulek() {
        return nonResidentSissetulek;
    }

    /**
     * Sets the value of the nonResidentSissetulek property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNonResidentSissetulek(JAXBElement<String> value) {
        this.nonResidentSissetulek = value;
    }

    /**
     * Gets the value of the nonResidentFailid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nonResidentFailid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNonResidentFailid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FailInfoDto }
     * 
     * 
     */
    public List<FailInfoDto> getNonResidentFailid() {
        if (nonResidentFailid == null) {
            nonResidentFailid = new ArrayList<FailInfoDto>();
        }
        return this.nonResidentFailid;
    }

    /**
     * Gets the value of the lisatudIsikuteSissetulek property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLisatudIsikuteSissetulek() {
        return lisatudIsikuteSissetulek;
    }

    /**
     * Sets the value of the lisatudIsikuteSissetulek property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLisatudIsikuteSissetulek(JAXBElement<String> value) {
        this.lisatudIsikuteSissetulek = value;
    }

    /**
     * Gets the value of the lisatudFailid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lisatudFailid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLisatudFailid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FailInfoDto }
     * 
     * 
     */
    public List<FailInfoDto> getLisatudFailid() {
        if (lisatudFailid == null) {
            lisatudFailid = new ArrayList<FailInfoDto>();
        }
        return this.lisatudFailid;
    }

}
