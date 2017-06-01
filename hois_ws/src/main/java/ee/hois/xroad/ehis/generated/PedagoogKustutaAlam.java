
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pedagoogKustutaAlam complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pedagoogKustutaAlam"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="taiendkoolitus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoogTaiendkoolitusId" maxOccurs="100" minOccurs="0"/&gt;
 *         &lt;element name="tasemekoolitus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoogTasemekoolitusId" maxOccurs="100" minOccurs="0"/&gt;
 *         &lt;element name="ametijark" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoogAmetijarkId" maxOccurs="100" minOccurs="0"/&gt;
 *         &lt;element name="riigikeel" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}pedagoogRiigikeelId" maxOccurs="100" minOccurs="0"/&gt;
 *         &lt;element name="klValisprojekt" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="100" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pedagoogKustutaAlam", propOrder = {
    "isikukood",
    "taiendkoolitus",
    "tasemekoolitus",
    "ametijark",
    "riigikeel",
    "klValisprojekt"
})
public class PedagoogKustutaAlam {

    @XmlElement(required = true)
    protected String isikukood;
    protected List<PedagoogTaiendkoolitusId> taiendkoolitus;
    protected List<PedagoogTasemekoolitusId> tasemekoolitus;
    protected List<PedagoogAmetijarkId> ametijark;
    protected List<PedagoogRiigikeelId> riigikeel;
    protected List<String> klValisprojekt;

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
     * {@link PedagoogTaiendkoolitusId }
     * 
     * 
     */
    public List<PedagoogTaiendkoolitusId> getTaiendkoolitus() {
        if (taiendkoolitus == null) {
            taiendkoolitus = new ArrayList<PedagoogTaiendkoolitusId>();
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
     * {@link PedagoogTasemekoolitusId }
     * 
     * 
     */
    public List<PedagoogTasemekoolitusId> getTasemekoolitus() {
        if (tasemekoolitus == null) {
            tasemekoolitus = new ArrayList<PedagoogTasemekoolitusId>();
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
     * {@link PedagoogAmetijarkId }
     * 
     * 
     */
    public List<PedagoogAmetijarkId> getAmetijark() {
        if (ametijark == null) {
            ametijark = new ArrayList<PedagoogAmetijarkId>();
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
     * {@link PedagoogRiigikeelId }
     * 
     * 
     */
    public List<PedagoogRiigikeelId> getRiigikeel() {
        if (riigikeel == null) {
            riigikeel = new ArrayList<PedagoogRiigikeelId>();
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

}
