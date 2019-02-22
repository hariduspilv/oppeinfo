
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for EVapiMenetlusinfo_v1_Menetlus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiMenetlusinfo_v1_Menetlus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="menetluse_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="seotud_menetluse_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="esit_kp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="menetluse_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="menetluse_olek" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_reg_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVapiMenetlusinfo_v1_Menetlus", propOrder = {
    "menetluseId",
    "seotudMenetluseId",
    "esitKp",
    "menetluseLiik",
    "menetluseOlek",
    "ettevotjaRegKood",
    "ettevotjaNimi"
})
public class EVapiMenetlusinfoV1Menetlus {

    @XmlElement(name = "menetluse_id", required = true)
    protected BigInteger menetluseId;
    @XmlElement(name = "seotud_menetluse_id")
    protected BigInteger seotudMenetluseId;
    @XmlElement(name = "esit_kp", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esitKp;
    @XmlElement(name = "menetluse_liik")
    protected String menetluseLiik;
    @XmlElement(name = "menetluse_olek")
    protected String menetluseOlek;
    @XmlElement(name = "ettevotja_reg_kood")
    protected String ettevotjaRegKood;
    @XmlElement(name = "ettevotja_nimi")
    protected String ettevotjaNimi;

    /**
     * Gets the value of the menetluseId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMenetluseId() {
        return menetluseId;
    }

    /**
     * Sets the value of the menetluseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMenetluseId(BigInteger value) {
        this.menetluseId = value;
    }

    /**
     * Gets the value of the seotudMenetluseId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSeotudMenetluseId() {
        return seotudMenetluseId;
    }

    /**
     * Sets the value of the seotudMenetluseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSeotudMenetluseId(BigInteger value) {
        this.seotudMenetluseId = value;
    }

    /**
     * Gets the value of the esitKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitKp() {
        return esitKp;
    }

    /**
     * Sets the value of the esitKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitKp(XMLGregorianCalendar value) {
        this.esitKp = value;
    }

    /**
     * Gets the value of the menetluseLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenetluseLiik() {
        return menetluseLiik;
    }

    /**
     * Sets the value of the menetluseLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenetluseLiik(String value) {
        this.menetluseLiik = value;
    }

    /**
     * Gets the value of the menetluseOlek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenetluseOlek() {
        return menetluseOlek;
    }

    /**
     * Sets the value of the menetluseOlek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenetluseOlek(String value) {
        this.menetluseOlek = value;
    }

    /**
     * Gets the value of the ettevotjaRegKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaRegKood() {
        return ettevotjaRegKood;
    }

    /**
     * Sets the value of the ettevotjaRegKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaRegKood(String value) {
        this.ettevotjaRegKood = value;
    }

    /**
     * Gets the value of the ettevotjaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaNimi() {
        return ettevotjaNimi;
    }

    /**
     * Sets the value of the ettevotjaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaNimi(String value) {
        this.ettevotjaNimi = value;
    }

}
