
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eeIsikukaartBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eeIsikukaartBean"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikuandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartIsikuandmed"/&gt;
 *         &lt;element name="oping" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartOping" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="tootamine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartTootamine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="taiendkoolitus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartTaiendkoolitus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="tasemeharidus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartTasemeharidus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="kvalifikatsioon" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartKvalifikatsioon" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eeIsikukaartBean", propOrder = {
    "isikuandmed",
    "oping",
    "tootamine",
    "taiendkoolitus",
    "tasemeharidus",
    "kvalifikatsioon"
})
public class EeIsikukaartBean {

    @XmlElement(required = true)
    protected EeIsikukaartIsikuandmed isikuandmed;
    protected List<EeIsikukaartOping> oping;
    protected List<EeIsikukaartTootamine> tootamine;
    protected List<EeIsikukaartTaiendkoolitus> taiendkoolitus;
    protected List<EeIsikukaartTasemeharidus> tasemeharidus;
    protected List<EeIsikukaartKvalifikatsioon> kvalifikatsioon;

    /**
     * Gets the value of the isikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link EeIsikukaartIsikuandmed }
     *     
     */
    public EeIsikukaartIsikuandmed getIsikuandmed() {
        return isikuandmed;
    }

    /**
     * Sets the value of the isikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link EeIsikukaartIsikuandmed }
     *     
     */
    public void setIsikuandmed(EeIsikukaartIsikuandmed value) {
        this.isikuandmed = value;
    }

    /**
     * Gets the value of the oping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EeIsikukaartOping }
     * 
     * 
     */
    public List<EeIsikukaartOping> getOping() {
        if (oping == null) {
            oping = new ArrayList<EeIsikukaartOping>();
        }
        return this.oping;
    }

    /**
     * Gets the value of the tootamine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tootamine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTootamine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EeIsikukaartTootamine }
     * 
     * 
     */
    public List<EeIsikukaartTootamine> getTootamine() {
        if (tootamine == null) {
            tootamine = new ArrayList<EeIsikukaartTootamine>();
        }
        return this.tootamine;
    }

    /**
     * Gets the value of the taiendkoolitus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taiendkoolitus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaiendkoolitus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EeIsikukaartTaiendkoolitus }
     * 
     * 
     */
    public List<EeIsikukaartTaiendkoolitus> getTaiendkoolitus() {
        if (taiendkoolitus == null) {
            taiendkoolitus = new ArrayList<EeIsikukaartTaiendkoolitus>();
        }
        return this.taiendkoolitus;
    }

    /**
     * Gets the value of the tasemeharidus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tasemeharidus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTasemeharidus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EeIsikukaartTasemeharidus }
     * 
     * 
     */
    public List<EeIsikukaartTasemeharidus> getTasemeharidus() {
        if (tasemeharidus == null) {
            tasemeharidus = new ArrayList<EeIsikukaartTasemeharidus>();
        }
        return this.tasemeharidus;
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
     * {@link EeIsikukaartKvalifikatsioon }
     * 
     * 
     */
    public List<EeIsikukaartKvalifikatsioon> getKvalifikatsioon() {
        if (kvalifikatsioon == null) {
            kvalifikatsioon = new ArrayList<EeIsikukaartKvalifikatsioon>();
        }
        return this.kvalifikatsioon;
    }

}
