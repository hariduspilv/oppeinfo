
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood" minOccurs="0"/&gt;
 *         &lt;element name="format" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisAttachmentFormat" minOccurs="0"/&gt;
 *         &lt;element name="andmeplokk" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="andmekirje" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="valjundiTyyp" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "isikukood",
    "format",
    "andmeplokk",
    "andmekirje",
    "valjundiTyyp"
})
@XmlRootElement(name = "eeIsikukaart")
public class EeIsikukaart {

    protected String isikukood;
    @XmlElement(defaultValue = "html")
    protected String format;
    protected List<String> andmeplokk;
    protected List<String> andmekirje;
    protected List<String> valjundiTyyp;

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
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormat(String value) {
        this.format = value;
    }

    /**
     * Gets the value of the andmeplokk property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the andmeplokk property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAndmeplokk().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAndmeplokk() {
        if (andmeplokk == null) {
            andmeplokk = new ArrayList<String>();
        }
        return this.andmeplokk;
    }

    /**
     * Gets the value of the andmekirje property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the andmekirje property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAndmekirje().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAndmekirje() {
        if (andmekirje == null) {
            andmekirje = new ArrayList<String>();
        }
        return this.andmekirje;
    }

    /**
     * Gets the value of the valjundiTyyp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valjundiTyyp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValjundiTyyp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getValjundiTyyp() {
        if (valjundiTyyp == null) {
            valjundiTyyp = new ArrayList<String>();
        }
        return this.valjundiTyyp;
    }

}
