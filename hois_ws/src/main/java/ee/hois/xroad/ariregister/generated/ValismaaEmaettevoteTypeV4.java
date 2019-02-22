
package ee.hois.xroad.ariregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for valismaa_emaettevoteType_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="valismaa_emaettevoteType_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="arinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="arinimi_seesytlevas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oigusliku_vormi_markus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="registrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asukoht" type="{http://arireg.x-road.eu/producer/}aadressType_v4" minOccurs="0"/&gt;
 *         &lt;element name="seaduste_alus_riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegutsemise_maa_markus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="maj_aru_markus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="markused" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "valismaa_emaettevoteType_v4", propOrder = {
    "arinimi",
    "arinimiSeesytlevas",
    "oiguslikVorm",
    "oiguslikuVormiMarkus",
    "registrikood",
    "asukoht",
    "seadusteAlusRiik",
    "tegutsemiseMaaMarkus",
    "majAruMarkus",
    "markused"
})
public class ValismaaEmaettevoteTypeV4 {

    protected String arinimi;
    @XmlElement(name = "arinimi_seesytlevas")
    protected String arinimiSeesytlevas;
    @XmlElement(name = "oiguslik_vorm")
    protected String oiguslikVorm;
    @XmlElement(name = "oigusliku_vormi_markus")
    protected String oiguslikuVormiMarkus;
    protected String registrikood;
    protected AadressTypeV4 asukoht;
    @XmlElement(name = "seaduste_alus_riik")
    protected String seadusteAlusRiik;
    @XmlElement(name = "tegutsemise_maa_markus")
    protected String tegutsemiseMaaMarkus;
    @XmlElement(name = "maj_aru_markus")
    protected String majAruMarkus;
    @XmlElement(nillable = true)
    protected List<String> markused;

    /**
     * Gets the value of the arinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArinimi() {
        return arinimi;
    }

    /**
     * Sets the value of the arinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArinimi(String value) {
        this.arinimi = value;
    }

    /**
     * Gets the value of the arinimiSeesytlevas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArinimiSeesytlevas() {
        return arinimiSeesytlevas;
    }

    /**
     * Sets the value of the arinimiSeesytlevas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArinimiSeesytlevas(String value) {
        this.arinimiSeesytlevas = value;
    }

    /**
     * Gets the value of the oiguslikVorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikVorm() {
        return oiguslikVorm;
    }

    /**
     * Sets the value of the oiguslikVorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikVorm(String value) {
        this.oiguslikVorm = value;
    }

    /**
     * Gets the value of the oiguslikuVormiMarkus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikuVormiMarkus() {
        return oiguslikuVormiMarkus;
    }

    /**
     * Sets the value of the oiguslikuVormiMarkus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikuVormiMarkus(String value) {
        this.oiguslikuVormiMarkus = value;
    }

    /**
     * Gets the value of the registrikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrikood() {
        return registrikood;
    }

    /**
     * Sets the value of the registrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrikood(String value) {
        this.registrikood = value;
    }

    /**
     * Gets the value of the asukoht property.
     * 
     * @return
     *     possible object is
     *     {@link AadressTypeV4 }
     *     
     */
    public AadressTypeV4 getAsukoht() {
        return asukoht;
    }

    /**
     * Sets the value of the asukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link AadressTypeV4 }
     *     
     */
    public void setAsukoht(AadressTypeV4 value) {
        this.asukoht = value;
    }

    /**
     * Gets the value of the seadusteAlusRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeadusteAlusRiik() {
        return seadusteAlusRiik;
    }

    /**
     * Sets the value of the seadusteAlusRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeadusteAlusRiik(String value) {
        this.seadusteAlusRiik = value;
    }

    /**
     * Gets the value of the tegutsemiseMaaMarkus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegutsemiseMaaMarkus() {
        return tegutsemiseMaaMarkus;
    }

    /**
     * Sets the value of the tegutsemiseMaaMarkus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegutsemiseMaaMarkus(String value) {
        this.tegutsemiseMaaMarkus = value;
    }

    /**
     * Gets the value of the majAruMarkus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajAruMarkus() {
        return majAruMarkus;
    }

    /**
     * Sets the value of the majAruMarkus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajAruMarkus(String value) {
        this.majAruMarkus = value;
    }

    /**
     * Gets the value of the markused property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the markused property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMarkused().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMarkused() {
        if (markused == null) {
            markused = new ArrayList<String>();
        }
        return this.markused;
    }

}
