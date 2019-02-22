
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xbrlesindusv2_ettevote complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrlesindusv2_ettevote"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ettevotja_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="arinimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="staatus_tekstina_est" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="staatus_tekstina_eng" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="piirkond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="piirkond_tekstina_est" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="piirkond_tekstina_eng" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="majaasta_algus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="majaasta_lopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oigvorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oigvorm_tekstina_est" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oigvorm_tekstina_eng" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kapitali_summa" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="kapitali_valuuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kapitali_valuuta_tekstina_est" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kapitali_valuuta_tekstina_eng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xbrlesindusv2_ettevote", propOrder = {
    "ariregistriKood",
    "ettevotjaId",
    "arinimi",
    "liik",
    "staatus",
    "staatusTekstinaEst",
    "staatusTekstinaEng",
    "piirkond",
    "piirkondTekstinaEst",
    "piirkondTekstinaEng",
    "majaastaAlgus",
    "majaastaLopp",
    "oigvorm",
    "oigvormTekstinaEst",
    "oigvormTekstinaEng",
    "kapitaliSumma",
    "kapitaliValuuta",
    "kapitaliValuutaTekstinaEst",
    "kapitaliValuutaTekstinaEng"
})
public class Xbrlesindusv2Ettevote {

    @XmlElement(name = "ariregistri_kood", required = true, type = Integer.class, nillable = true)
    protected Integer ariregistriKood;
    @XmlElement(name = "ettevotja_id", required = true, nillable = true)
    protected BigInteger ettevotjaId;
    @XmlElement(required = true, nillable = true)
    protected String arinimi;
    @XmlElement(required = true)
    protected String liik;
    @XmlElement(required = true)
    protected String staatus;
    @XmlElement(name = "staatus_tekstina_est", required = true)
    protected String staatusTekstinaEst;
    @XmlElement(name = "staatus_tekstina_eng", required = true)
    protected String staatusTekstinaEng;
    @XmlElement(required = true)
    protected String piirkond;
    @XmlElement(name = "piirkond_tekstina_est", required = true)
    protected String piirkondTekstinaEst;
    @XmlElement(name = "piirkond_tekstina_eng", required = true)
    protected String piirkondTekstinaEng;
    @XmlElement(name = "majaasta_algus", required = true, nillable = true)
    protected String majaastaAlgus;
    @XmlElement(name = "majaasta_lopp", required = true, nillable = true)
    protected String majaastaLopp;
    @XmlElement(required = true)
    protected String oigvorm;
    @XmlElement(name = "oigvorm_tekstina_est", required = true)
    protected String oigvormTekstinaEst;
    @XmlElement(name = "oigvorm_tekstina_eng", required = true)
    protected String oigvormTekstinaEng;
    @XmlElementRef(name = "kapitali_summa", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<Float> kapitaliSumma;
    @XmlElementRef(name = "kapitali_valuuta", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> kapitaliValuuta;
    @XmlElementRef(name = "kapitali_valuuta_tekstina_est", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> kapitaliValuutaTekstinaEst;
    @XmlElementRef(name = "kapitali_valuuta_tekstina_eng", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> kapitaliValuutaTekstinaEng;

    /**
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAriregistriKood(Integer value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the ettevotjaId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEttevotjaId() {
        return ettevotjaId;
    }

    /**
     * Sets the value of the ettevotjaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEttevotjaId(BigInteger value) {
        this.ettevotjaId = value;
    }

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
     * Gets the value of the liik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLiik() {
        return liik;
    }

    /**
     * Sets the value of the liik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLiik(String value) {
        this.liik = value;
    }

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatus(String value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the staatusTekstinaEst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatusTekstinaEst() {
        return staatusTekstinaEst;
    }

    /**
     * Sets the value of the staatusTekstinaEst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatusTekstinaEst(String value) {
        this.staatusTekstinaEst = value;
    }

    /**
     * Gets the value of the staatusTekstinaEng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatusTekstinaEng() {
        return staatusTekstinaEng;
    }

    /**
     * Sets the value of the staatusTekstinaEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatusTekstinaEng(String value) {
        this.staatusTekstinaEng = value;
    }

    /**
     * Gets the value of the piirkond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiirkond() {
        return piirkond;
    }

    /**
     * Sets the value of the piirkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiirkond(String value) {
        this.piirkond = value;
    }

    /**
     * Gets the value of the piirkondTekstinaEst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiirkondTekstinaEst() {
        return piirkondTekstinaEst;
    }

    /**
     * Sets the value of the piirkondTekstinaEst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiirkondTekstinaEst(String value) {
        this.piirkondTekstinaEst = value;
    }

    /**
     * Gets the value of the piirkondTekstinaEng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiirkondTekstinaEng() {
        return piirkondTekstinaEng;
    }

    /**
     * Sets the value of the piirkondTekstinaEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiirkondTekstinaEng(String value) {
        this.piirkondTekstinaEng = value;
    }

    /**
     * Gets the value of the majaastaAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajaastaAlgus() {
        return majaastaAlgus;
    }

    /**
     * Sets the value of the majaastaAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajaastaAlgus(String value) {
        this.majaastaAlgus = value;
    }

    /**
     * Gets the value of the majaastaLopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajaastaLopp() {
        return majaastaLopp;
    }

    /**
     * Sets the value of the majaastaLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajaastaLopp(String value) {
        this.majaastaLopp = value;
    }

    /**
     * Gets the value of the oigvorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOigvorm() {
        return oigvorm;
    }

    /**
     * Sets the value of the oigvorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOigvorm(String value) {
        this.oigvorm = value;
    }

    /**
     * Gets the value of the oigvormTekstinaEst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOigvormTekstinaEst() {
        return oigvormTekstinaEst;
    }

    /**
     * Sets the value of the oigvormTekstinaEst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOigvormTekstinaEst(String value) {
        this.oigvormTekstinaEst = value;
    }

    /**
     * Gets the value of the oigvormTekstinaEng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOigvormTekstinaEng() {
        return oigvormTekstinaEng;
    }

    /**
     * Sets the value of the oigvormTekstinaEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOigvormTekstinaEng(String value) {
        this.oigvormTekstinaEng = value;
    }

    /**
     * Gets the value of the kapitaliSumma property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Float }{@code >}
     *     
     */
    public JAXBElement<Float> getKapitaliSumma() {
        return kapitaliSumma;
    }

    /**
     * Sets the value of the kapitaliSumma property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Float }{@code >}
     *     
     */
    public void setKapitaliSumma(JAXBElement<Float> value) {
        this.kapitaliSumma = value;
    }

    /**
     * Gets the value of the kapitaliValuuta property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKapitaliValuuta() {
        return kapitaliValuuta;
    }

    /**
     * Sets the value of the kapitaliValuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKapitaliValuuta(JAXBElement<String> value) {
        this.kapitaliValuuta = value;
    }

    /**
     * Gets the value of the kapitaliValuutaTekstinaEst property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKapitaliValuutaTekstinaEst() {
        return kapitaliValuutaTekstinaEst;
    }

    /**
     * Sets the value of the kapitaliValuutaTekstinaEst property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKapitaliValuutaTekstinaEst(JAXBElement<String> value) {
        this.kapitaliValuutaTekstinaEst = value;
    }

    /**
     * Gets the value of the kapitaliValuutaTekstinaEng property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKapitaliValuutaTekstinaEng() {
        return kapitaliValuutaTekstinaEng;
    }

    /**
     * Sets the value of the kapitaliValuutaTekstinaEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKapitaliValuutaTekstinaEng(JAXBElement<String> value) {
        this.kapitaliValuutaTekstinaEng = value;
    }

}
