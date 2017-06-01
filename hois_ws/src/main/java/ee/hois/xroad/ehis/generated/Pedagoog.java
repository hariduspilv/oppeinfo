
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pedagoog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pedagoog"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="onSiseriikProj" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="kommentaarSise" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ametikoht" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoogAmetikohtType" maxOccurs="100" minOccurs="0"/&gt;
 *         &lt;element name="taiendkoolitus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoogTaiendkoolitus" maxOccurs="100" minOccurs="0"/&gt;
 *         &lt;element name="tasemekoolitus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoogTasemekoolitus" maxOccurs="100" minOccurs="0"/&gt;
 *         &lt;element name="ametijark" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoogAmetijark" maxOccurs="100" minOccurs="0"/&gt;
 *         &lt;element name="riigikeel" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoogRiigikeel" maxOccurs="100" minOccurs="0"/&gt;
 *         &lt;element name="klValisprojekt" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="100" minOccurs="0"/&gt;
 *         &lt;element name="klEmakeel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pedagoog", propOrder = {
    "isikukood",
    "onSiseriikProj",
    "kommentaarSise",
    "ametikoht",
    "taiendkoolitus",
    "tasemekoolitus",
    "ametijark",
    "riigikeel",
    "klValisprojekt",
    "klEmakeel"
})
public class Pedagoog {

    @XmlElement(required = true)
    protected String isikukood;
    protected String onSiseriikProj;
    protected String kommentaarSise;
    protected List<PedagoogAmetikohtType> ametikoht;
    protected List<PedagoogTaiendkoolitus> taiendkoolitus;
    protected List<PedagoogTasemekoolitus> tasemekoolitus;
    protected List<PedagoogAmetijark> ametijark;
    protected List<PedagoogRiigikeel> riigikeel;
    protected List<String> klValisprojekt;
    protected String klEmakeel;

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
     * Gets the value of the onSiseriikProj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnSiseriikProj() {
        return onSiseriikProj;
    }

    /**
     * Sets the value of the onSiseriikProj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnSiseriikProj(String value) {
        this.onSiseriikProj = value;
    }

    /**
     * Gets the value of the kommentaarSise property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKommentaarSise() {
        return kommentaarSise;
    }

    /**
     * Sets the value of the kommentaarSise property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKommentaarSise(String value) {
        this.kommentaarSise = value;
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
     * {@link PedagoogAmetikohtType }
     * 
     * 
     */
    public List<PedagoogAmetikohtType> getAmetikoht() {
        if (ametikoht == null) {
            ametikoht = new ArrayList<PedagoogAmetikohtType>();
        }
        return this.ametikoht;
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
     * {@link PedagoogTaiendkoolitus }
     * 
     * 
     */
    public List<PedagoogTaiendkoolitus> getTaiendkoolitus() {
        if (taiendkoolitus == null) {
            taiendkoolitus = new ArrayList<PedagoogTaiendkoolitus>();
        }
        return this.taiendkoolitus;
    }

    /**
     * Gets the value of the tasemekoolitus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tasemekoolitus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTasemekoolitus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PedagoogTasemekoolitus }
     * 
     * 
     */
    public List<PedagoogTasemekoolitus> getTasemekoolitus() {
        if (tasemekoolitus == null) {
            tasemekoolitus = new ArrayList<PedagoogTasemekoolitus>();
        }
        return this.tasemekoolitus;
    }

    /**
     * Gets the value of the ametijark property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ametijark property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAmetijark().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PedagoogAmetijark }
     * 
     * 
     */
    public List<PedagoogAmetijark> getAmetijark() {
        if (ametijark == null) {
            ametijark = new ArrayList<PedagoogAmetijark>();
        }
        return this.ametijark;
    }

    /**
     * Gets the value of the riigikeel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the riigikeel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRiigikeel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PedagoogRiigikeel }
     * 
     * 
     */
    public List<PedagoogRiigikeel> getRiigikeel() {
        if (riigikeel == null) {
            riigikeel = new ArrayList<PedagoogRiigikeel>();
        }
        return this.riigikeel;
    }

    /**
     * Gets the value of the klValisprojekt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the klValisprojekt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKlValisprojekt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKlValisprojekt() {
        if (klValisprojekt == null) {
            klValisprojekt = new ArrayList<String>();
        }
        return this.klValisprojekt;
    }

    /**
     * Gets the value of the klEmakeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlEmakeel() {
        return klEmakeel;
    }

    /**
     * Sets the value of the klEmakeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlEmakeel(String value) {
        this.klEmakeel = value;
    }

}
