
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for oppejoudAmetikoht complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppejoudAmetikoht"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="onOppejoud" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="klAmetikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ametikohtMuu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klToosuhe" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="toosuheMuu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klLepinguLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lepingAlgKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lepingLoppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="koormus"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double"&gt;
 *               &lt;minInclusive value="0.0"/&gt;
 *               &lt;maxInclusive value="5.0"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="lepingOnLopetatud" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="struktNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="struktKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="toosuheKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ametikohtTapsustusEn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeained" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppeaine" maxOccurs="50" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppejoudAmetikoht", propOrder = {
    "onOppejoud",
    "klAmetikoht",
    "ametikohtMuu",
    "klToosuhe",
    "toosuheMuu",
    "klLepinguLiik",
    "lepingAlgKp",
    "lepingLoppKp",
    "koormus",
    "lepingOnLopetatud",
    "struktNimi",
    "struktKood",
    "toosuheKood",
    "ametikohtTapsustusEn",
    "oppeained"
})
public class OppejoudAmetikoht {

    protected String onOppejoud;
    @XmlElement(required = true)
    protected String klAmetikoht;
    protected String ametikohtMuu;
    @XmlElement(required = true)
    protected String klToosuhe;
    protected String toosuheMuu;
    @XmlElement(required = true)
    protected String klLepinguLiik;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lepingAlgKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lepingLoppKp;
    protected double koormus;
    protected String lepingOnLopetatud;
    protected String struktNimi;
    protected String struktKood;
    protected String toosuheKood;
    protected String ametikohtTapsustusEn;
    protected List<Oppeaine> oppeained;

    /**
     * Gets the value of the onOppejoud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnOppejoud() {
        return onOppejoud;
    }

    /**
     * Sets the value of the onOppejoud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnOppejoud(String value) {
        this.onOppejoud = value;
    }

    /**
     * Gets the value of the klAmetikoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlAmetikoht() {
        return klAmetikoht;
    }

    /**
     * Sets the value of the klAmetikoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlAmetikoht(String value) {
        this.klAmetikoht = value;
    }

    /**
     * Gets the value of the ametikohtMuu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmetikohtMuu() {
        return ametikohtMuu;
    }

    /**
     * Sets the value of the ametikohtMuu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmetikohtMuu(String value) {
        this.ametikohtMuu = value;
    }

    /**
     * Gets the value of the klToosuhe property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlToosuhe() {
        return klToosuhe;
    }

    /**
     * Sets the value of the klToosuhe property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlToosuhe(String value) {
        this.klToosuhe = value;
    }

    /**
     * Gets the value of the toosuheMuu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToosuheMuu() {
        return toosuheMuu;
    }

    /**
     * Sets the value of the toosuheMuu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToosuheMuu(String value) {
        this.toosuheMuu = value;
    }

    /**
     * Gets the value of the klLepinguLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlLepinguLiik() {
        return klLepinguLiik;
    }

    /**
     * Sets the value of the klLepinguLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlLepinguLiik(String value) {
        this.klLepinguLiik = value;
    }

    /**
     * Gets the value of the lepingAlgKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLepingAlgKp() {
        return lepingAlgKp;
    }

    /**
     * Sets the value of the lepingAlgKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLepingAlgKp(XMLGregorianCalendar value) {
        this.lepingAlgKp = value;
    }

    /**
     * Gets the value of the lepingLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLepingLoppKp() {
        return lepingLoppKp;
    }

    /**
     * Sets the value of the lepingLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLepingLoppKp(XMLGregorianCalendar value) {
        this.lepingLoppKp = value;
    }

    /**
     * Gets the value of the koormus property.
     * 
     */
    public double getKoormus() {
        return koormus;
    }

    /**
     * Sets the value of the koormus property.
     * 
     */
    public void setKoormus(double value) {
        this.koormus = value;
    }

    /**
     * Gets the value of the lepingOnLopetatud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLepingOnLopetatud() {
        return lepingOnLopetatud;
    }

    /**
     * Sets the value of the lepingOnLopetatud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLepingOnLopetatud(String value) {
        this.lepingOnLopetatud = value;
    }

    /**
     * Gets the value of the struktNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStruktNimi() {
        return struktNimi;
    }

    /**
     * Sets the value of the struktNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStruktNimi(String value) {
        this.struktNimi = value;
    }

    /**
     * Gets the value of the struktKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStruktKood() {
        return struktKood;
    }

    /**
     * Sets the value of the struktKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStruktKood(String value) {
        this.struktKood = value;
    }

    /**
     * Gets the value of the toosuheKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToosuheKood() {
        return toosuheKood;
    }

    /**
     * Sets the value of the toosuheKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToosuheKood(String value) {
        this.toosuheKood = value;
    }

    /**
     * Gets the value of the ametikohtTapsustusEn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmetikohtTapsustusEn() {
        return ametikohtTapsustusEn;
    }

    /**
     * Sets the value of the ametikohtTapsustusEn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmetikohtTapsustusEn(String value) {
        this.ametikohtTapsustusEn = value;
    }

    /**
     * Gets the value of the oppeained property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppeained property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppeained().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Oppeaine }
     * 
     * 
     */
    public List<Oppeaine> getOppeained() {
        if (oppeained == null) {
            oppeained = new ArrayList<Oppeaine>();
        }
        return this.oppeained;
    }

}
