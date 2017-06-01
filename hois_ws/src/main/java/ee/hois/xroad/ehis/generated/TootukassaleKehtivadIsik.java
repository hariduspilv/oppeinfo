
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tootukassaleKehtivadIsik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tootukassaleKehtivadIsik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="oppimine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkOppimine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="akadPuhkus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkAkadPuhkus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="viga" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}tkViga" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tootukassaleKehtivadIsik", propOrder = {
    "isikukood",
    "oppimine",
    "akadPuhkus",
    "viga"
})
public class TootukassaleKehtivadIsik {

    @XmlElement(required = true)
    protected String isikukood;
    protected List<TkOppimine> oppimine;
    protected List<TkAkadPuhkus> akadPuhkus;
    protected List<TkViga> viga;

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
     * Gets the value of the oppimine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppimine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppimine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TkOppimine }
     * 
     * 
     */
    public List<TkOppimine> getOppimine() {
        if (oppimine == null) {
            oppimine = new ArrayList<TkOppimine>();
        }
        return this.oppimine;
    }

    /**
     * Gets the value of the akadPuhkus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the akadPuhkus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAkadPuhkus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TkAkadPuhkus }
     * 
     * 
     */
    public List<TkAkadPuhkus> getAkadPuhkus() {
        if (akadPuhkus == null) {
            akadPuhkus = new ArrayList<TkAkadPuhkus>();
        }
        return this.akadPuhkus;
    }

    /**
     * Gets the value of the viga property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the viga property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getViga().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TkViga }
     * 
     * 
     */
    public List<TkViga> getViga() {
        if (viga == null) {
            viga = new ArrayList<TkViga>();
        }
        return this.viga;
    }

}
