
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR409isikEpiletResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR409isikEpiletResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KOVKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DokNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DokNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="veakood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR409isikEpiletResponseType", propOrder = {
    "isikukood",
    "kovKood",
    "dokNimi",
    "dokNumber",
    "eesnimi",
    "perenimi",
    "veakood",
    "veatekst"
})
public class RR409IsikEpiletResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikukood", required = true)
    protected String isikukood;
    @XmlElement(name = "KOVKood", required = true)
    protected String kovKood;
    @XmlElement(name = "DokNimi", required = true)
    protected String dokNimi;
    @XmlElement(name = "DokNumber", required = true)
    protected String dokNumber;
    @XmlElement(name = "Eesnimi", required = true)
    protected String eesnimi;
    @XmlElement(name = "Perenimi", required = true)
    protected String perenimi;
    protected int veakood;
    @XmlElement(required = true)
    protected String veatekst;

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
     * Gets the value of the kovKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKOVKood() {
        return kovKood;
    }

    /**
     * Sets the value of the kovKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKOVKood(String value) {
        this.kovKood = value;
    }

    /**
     * Gets the value of the dokNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokNimi() {
        return dokNimi;
    }

    /**
     * Sets the value of the dokNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokNimi(String value) {
        this.dokNimi = value;
    }

    /**
     * Gets the value of the dokNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokNumber() {
        return dokNumber;
    }

    /**
     * Sets the value of the dokNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokNumber(String value) {
        this.dokNumber = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the perenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerenimi() {
        return perenimi;
    }

    /**
     * Sets the value of the perenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerenimi(String value) {
        this.perenimi = value;
    }

    /**
     * Gets the value of the veakood property.
     * 
     */
    public int getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     */
    public void setVeakood(int value) {
        this.veakood = value;
    }

    /**
     * Gets the value of the veatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeatekst() {
        return veatekst;
    }

    /**
     * Sets the value of the veatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeatekst(String value) {
        this.veatekst = value;
    }

}
