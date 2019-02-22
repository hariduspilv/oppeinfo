
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringliht_tasuta_ettevote complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringliht_tasuta_ettevote"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="evnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kehtetud_nimed" type="{http://arireg.x-road.eu/producer/}paringliht_tasuta_kehtetudnimed" minOccurs="0"/&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringliht_tasuta_ettevote", propOrder = {
    "evnimi",
    "kehtetudNimed",
    "ariregistriKood"
})
public class ParinglihtTasutaEttevote {

    @XmlElement(required = true)
    protected String evnimi;
    @XmlElement(name = "kehtetud_nimed")
    protected ParinglihtTasutaKehtetudnimed kehtetudNimed;
    @XmlElement(name = "ariregistri_kood")
    protected String ariregistriKood;

    /**
     * Gets the value of the evnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvnimi() {
        return evnimi;
    }

    /**
     * Sets the value of the evnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvnimi(String value) {
        this.evnimi = value;
    }

    /**
     * Gets the value of the kehtetudNimed property.
     * 
     * @return
     *     possible object is
     *     {@link ParinglihtTasutaKehtetudnimed }
     *     
     */
    public ParinglihtTasutaKehtetudnimed getKehtetudNimed() {
        return kehtetudNimed;
    }

    /**
     * Sets the value of the kehtetudNimed property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParinglihtTasutaKehtetudnimed }
     *     
     */
    public void setKehtetudNimed(ParinglihtTasutaKehtetudnimed value) {
        this.kehtetudNimed = value;
    }

    /**
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAriregistriKood(String value) {
        this.ariregistriKood = value;
    }

}
