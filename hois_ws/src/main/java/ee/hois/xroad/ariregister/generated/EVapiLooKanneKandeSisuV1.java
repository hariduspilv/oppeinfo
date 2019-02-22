
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EVapiLooKanneKandeSisu_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiLooKanneKandeSisu_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="uus_arinimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="asukoht" type="{http://arireg.x-road.eu/producer/}EVapiLooKanneAadress_v1"/&gt;
 *         &lt;element name="pohitegevusala" type="{http://arireg.x-road.eu/producer/}EVapiLooKanneTegevusala_v1"/&gt;
 *         &lt;element name="kapital" type="{http://arireg.x-road.eu/producer/}EVapiLooKanneKapital_v1"/&gt;
 *         &lt;element name="isikud" type="{http://arireg.x-road.eu/producer/}EVapiLooKanneIsik_v1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="sidevahendid" type="{http://arireg.x-road.eu/producer/}EVapiLooKanneSidevahend_v1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiLooKanneKandeSisu_v1", propOrder = {
    "uusArinimi",
    "asukoht",
    "pohitegevusala",
    "kapital",
    "isikud",
    "sidevahendid"
})
public class EVapiLooKanneKandeSisuV1 {

    @XmlElement(name = "uus_arinimi", required = true)
    protected String uusArinimi;
    @XmlElement(required = true)
    protected EVapiLooKanneAadressV1 asukoht;
    @XmlElement(required = true)
    protected EVapiLooKanneTegevusalaV1 pohitegevusala;
    @XmlElement(required = true)
    protected EVapiLooKanneKapitalV1 kapital;
    protected List<EVapiLooKanneIsikV1> isikud;
    protected List<EVapiLooKanneSidevahendV1> sidevahendid;

    /**
     * Gets the value of the uusArinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUusArinimi() {
        return uusArinimi;
    }

    /**
     * Sets the value of the uusArinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUusArinimi(String value) {
        this.uusArinimi = value;
    }

    /**
     * Gets the value of the asukoht property.
     * 
     * @return
     *     possible object is
     *     {@link EVapiLooKanneAadressV1 }
     *     
     */
    public EVapiLooKanneAadressV1 getAsukoht() {
        return asukoht;
    }

    /**
     * Sets the value of the asukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link EVapiLooKanneAadressV1 }
     *     
     */
    public void setAsukoht(EVapiLooKanneAadressV1 value) {
        this.asukoht = value;
    }

    /**
     * Gets the value of the pohitegevusala property.
     * 
     * @return
     *     possible object is
     *     {@link EVapiLooKanneTegevusalaV1 }
     *     
     */
    public EVapiLooKanneTegevusalaV1 getPohitegevusala() {
        return pohitegevusala;
    }

    /**
     * Sets the value of the pohitegevusala property.
     * 
     * @param value
     *     allowed object is
     *     {@link EVapiLooKanneTegevusalaV1 }
     *     
     */
    public void setPohitegevusala(EVapiLooKanneTegevusalaV1 value) {
        this.pohitegevusala = value;
    }

    /**
     * Gets the value of the kapital property.
     * 
     * @return
     *     possible object is
     *     {@link EVapiLooKanneKapitalV1 }
     *     
     */
    public EVapiLooKanneKapitalV1 getKapital() {
        return kapital;
    }

    /**
     * Sets the value of the kapital property.
     * 
     * @param value
     *     allowed object is
     *     {@link EVapiLooKanneKapitalV1 }
     *     
     */
    public void setKapital(EVapiLooKanneKapitalV1 value) {
        this.kapital = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the isikud property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIsikud().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EVapiLooKanneIsikV1 }
     * 
     * 
     */
    public List<EVapiLooKanneIsikV1> getIsikud() {
        if (isikud == null) {
            isikud = new ArrayList<EVapiLooKanneIsikV1>();
        }
        return this.isikud;
    }

    /**
     * Gets the value of the sidevahendid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sidevahendid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSidevahendid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EVapiLooKanneSidevahendV1 }
     * 
     * 
     */
    public List<EVapiLooKanneSidevahendV1> getSidevahendid() {
        if (sidevahendid == null) {
            sidevahendid = new ArrayList<EVapiLooKanneSidevahendV1>();
        }
        return this.sidevahendid;
    }

}
