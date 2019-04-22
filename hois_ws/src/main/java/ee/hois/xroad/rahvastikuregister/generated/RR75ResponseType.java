
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR75ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR75ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="outIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outMK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outMKnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outVK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outVKnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outAK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outAKnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outAadrTXT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outinfo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="poiVeaKood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="pocVeaTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR75ResponseType", propOrder = {
    "outIsikukood",
    "outPerenimi",
    "outEesnimi",
    "outMK",
    "outMKnimi",
    "outVK",
    "outVKnimi",
    "outAK",
    "outAKnimi",
    "outTanav",
    "outMaja",
    "outKorter",
    "outAadrTXT",
    "outinfo",
    "poiVeaKood",
    "pocVeaTekst"
})
public class RR75ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected String outIsikukood;
    @XmlElement(required = true)
    protected String outPerenimi;
    @XmlElement(required = true)
    protected String outEesnimi;
    @XmlElement(required = true)
    protected String outMK;
    @XmlElement(required = true)
    protected String outMKnimi;
    @XmlElement(required = true)
    protected String outVK;
    @XmlElement(required = true)
    protected String outVKnimi;
    @XmlElement(required = true)
    protected String outAK;
    @XmlElement(required = true)
    protected String outAKnimi;
    @XmlElement(required = true)
    protected String outTanav;
    @XmlElement(required = true)
    protected String outMaja;
    @XmlElement(required = true)
    protected String outKorter;
    @XmlElement(required = true)
    protected String outAadrTXT;
    @XmlElement(required = true)
    protected String outinfo;
    @XmlElement(required = true)
    protected BigInteger poiVeaKood;
    @XmlElement(required = true)
    protected String pocVeaTekst;

    /**
     * Gets the value of the outIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutIsikukood() {
        return outIsikukood;
    }

    /**
     * Sets the value of the outIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutIsikukood(String value) {
        this.outIsikukood = value;
    }

    /**
     * Gets the value of the outPerenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutPerenimi() {
        return outPerenimi;
    }

    /**
     * Sets the value of the outPerenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutPerenimi(String value) {
        this.outPerenimi = value;
    }

    /**
     * Gets the value of the outEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutEesnimi() {
        return outEesnimi;
    }

    /**
     * Sets the value of the outEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutEesnimi(String value) {
        this.outEesnimi = value;
    }

    /**
     * Gets the value of the outMK property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutMK() {
        return outMK;
    }

    /**
     * Sets the value of the outMK property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutMK(String value) {
        this.outMK = value;
    }

    /**
     * Gets the value of the outMKnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutMKnimi() {
        return outMKnimi;
    }

    /**
     * Sets the value of the outMKnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutMKnimi(String value) {
        this.outMKnimi = value;
    }

    /**
     * Gets the value of the outVK property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutVK() {
        return outVK;
    }

    /**
     * Sets the value of the outVK property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutVK(String value) {
        this.outVK = value;
    }

    /**
     * Gets the value of the outVKnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutVKnimi() {
        return outVKnimi;
    }

    /**
     * Sets the value of the outVKnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutVKnimi(String value) {
        this.outVKnimi = value;
    }

    /**
     * Gets the value of the outAK property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutAK() {
        return outAK;
    }

    /**
     * Sets the value of the outAK property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutAK(String value) {
        this.outAK = value;
    }

    /**
     * Gets the value of the outAKnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutAKnimi() {
        return outAKnimi;
    }

    /**
     * Sets the value of the outAKnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutAKnimi(String value) {
        this.outAKnimi = value;
    }

    /**
     * Gets the value of the outTanav property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutTanav() {
        return outTanav;
    }

    /**
     * Sets the value of the outTanav property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutTanav(String value) {
        this.outTanav = value;
    }

    /**
     * Gets the value of the outMaja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutMaja() {
        return outMaja;
    }

    /**
     * Sets the value of the outMaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutMaja(String value) {
        this.outMaja = value;
    }

    /**
     * Gets the value of the outKorter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutKorter() {
        return outKorter;
    }

    /**
     * Sets the value of the outKorter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutKorter(String value) {
        this.outKorter = value;
    }

    /**
     * Gets the value of the outAadrTXT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutAadrTXT() {
        return outAadrTXT;
    }

    /**
     * Sets the value of the outAadrTXT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutAadrTXT(String value) {
        this.outAadrTXT = value;
    }

    /**
     * Gets the value of the outinfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutinfo() {
        return outinfo;
    }

    /**
     * Sets the value of the outinfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutinfo(String value) {
        this.outinfo = value;
    }

    /**
     * Gets the value of the poiVeaKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPoiVeaKood() {
        return poiVeaKood;
    }

    /**
     * Sets the value of the poiVeaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPoiVeaKood(BigInteger value) {
        this.poiVeaKood = value;
    }

    /**
     * Gets the value of the pocVeaTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPocVeaTekst() {
        return pocVeaTekst;
    }

    /**
     * Sets the value of the pocVeaTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPocVeaTekst(String value) {
        this.pocVeaTekst = value;
    }

}
