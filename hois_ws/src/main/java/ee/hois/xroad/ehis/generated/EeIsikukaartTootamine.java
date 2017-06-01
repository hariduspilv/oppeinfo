
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for eeIsikukaartTootamine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eeIsikukaartTootamine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutusId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ametikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ametikohtAlgus" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="ametikohtLopp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="onTunniandja" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="onOppejoud" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kehtiv" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="taitmiseViis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ametikohtKoormus" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="tooleping" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ametikohtKvalVastavus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ametijark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekava" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartOppekava" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="oppeaine" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartOppeaine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="haridustase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lapsehooldusPuhkus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eeIsikukaartTootamine", propOrder = {
    "liik",
    "oppeasutus",
    "oppeasutusId",
    "ametikoht",
    "ametikohtAlgus",
    "ametikohtLopp",
    "onTunniandja",
    "onOppejoud",
    "kehtiv",
    "taitmiseViis",
    "ametikohtKoormus",
    "tooleping",
    "ametikohtKvalVastavus",
    "ametijark",
    "oppekava",
    "oppeaine",
    "haridustase",
    "lapsehooldusPuhkus"
})
public class EeIsikukaartTootamine {

    @XmlElement(required = true)
    protected String liik;
    @XmlElement(required = true)
    protected String oppeasutus;
    @XmlElement(required = true)
    protected BigInteger oppeasutusId;
    @XmlElement(required = true)
    protected String ametikoht;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar ametikohtAlgus;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar ametikohtLopp;
    protected BigInteger onTunniandja;
    protected BigInteger onOppejoud;
    @XmlElement(required = true)
    protected BigInteger kehtiv;
    protected String taitmiseViis;
    protected Double ametikohtKoormus;
    protected String tooleping;
    protected String ametikohtKvalVastavus;
    protected String ametijark;
    protected List<EeIsikukaartOppekava> oppekava;
    protected List<EeIsikukaartOppeaine> oppeaine;
    protected String haridustase;
    protected String lapsehooldusPuhkus;

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
     * Gets the value of the oppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutus() {
        return oppeasutus;
    }

    /**
     * Sets the value of the oppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutus(String value) {
        this.oppeasutus = value;
    }

    /**
     * Gets the value of the oppeasutusId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppeasutusId() {
        return oppeasutusId;
    }

    /**
     * Sets the value of the oppeasutusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppeasutusId(BigInteger value) {
        this.oppeasutusId = value;
    }

    /**
     * Gets the value of the ametikoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmetikoht() {
        return ametikoht;
    }

    /**
     * Sets the value of the ametikoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmetikoht(String value) {
        this.ametikoht = value;
    }

    /**
     * Gets the value of the ametikohtAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAmetikohtAlgus() {
        return ametikohtAlgus;
    }

    /**
     * Sets the value of the ametikohtAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAmetikohtAlgus(XMLGregorianCalendar value) {
        this.ametikohtAlgus = value;
    }

    /**
     * Gets the value of the ametikohtLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAmetikohtLopp() {
        return ametikohtLopp;
    }

    /**
     * Sets the value of the ametikohtLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAmetikohtLopp(XMLGregorianCalendar value) {
        this.ametikohtLopp = value;
    }

    /**
     * Gets the value of the onTunniandja property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOnTunniandja() {
        return onTunniandja;
    }

    /**
     * Sets the value of the onTunniandja property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOnTunniandja(BigInteger value) {
        this.onTunniandja = value;
    }

    /**
     * Gets the value of the onOppejoud property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOnOppejoud() {
        return onOppejoud;
    }

    /**
     * Sets the value of the onOppejoud property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOnOppejoud(BigInteger value) {
        this.onOppejoud = value;
    }

    /**
     * Gets the value of the kehtiv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKehtiv() {
        return kehtiv;
    }

    /**
     * Sets the value of the kehtiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKehtiv(BigInteger value) {
        this.kehtiv = value;
    }

    /**
     * Gets the value of the taitmiseViis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaitmiseViis() {
        return taitmiseViis;
    }

    /**
     * Sets the value of the taitmiseViis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaitmiseViis(String value) {
        this.taitmiseViis = value;
    }

    /**
     * Gets the value of the ametikohtKoormus property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAmetikohtKoormus() {
        return ametikohtKoormus;
    }

    /**
     * Sets the value of the ametikohtKoormus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAmetikohtKoormus(Double value) {
        this.ametikohtKoormus = value;
    }

    /**
     * Gets the value of the tooleping property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTooleping() {
        return tooleping;
    }

    /**
     * Sets the value of the tooleping property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTooleping(String value) {
        this.tooleping = value;
    }

    /**
     * Gets the value of the ametikohtKvalVastavus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmetikohtKvalVastavus() {
        return ametikohtKvalVastavus;
    }

    /**
     * Sets the value of the ametikohtKvalVastavus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmetikohtKvalVastavus(String value) {
        this.ametikohtKvalVastavus = value;
    }

    /**
     * Gets the value of the ametijark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmetijark() {
        return ametijark;
    }

    /**
     * Sets the value of the ametijark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmetijark(String value) {
        this.ametijark = value;
    }

    /**
     * Gets the value of the oppekava property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppekava property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppekava().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EeIsikukaartOppekava }
     * 
     * 
     */
    public List<EeIsikukaartOppekava> getOppekava() {
        if (oppekava == null) {
            oppekava = new ArrayList<EeIsikukaartOppekava>();
        }
        return this.oppekava;
    }

    /**
     * Gets the value of the oppeaine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oppeaine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOppeaine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EeIsikukaartOppeaine }
     * 
     * 
     */
    public List<EeIsikukaartOppeaine> getOppeaine() {
        if (oppeaine == null) {
            oppeaine = new ArrayList<EeIsikukaartOppeaine>();
        }
        return this.oppeaine;
    }

    /**
     * Gets the value of the haridustase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaridustase() {
        return haridustase;
    }

    /**
     * Sets the value of the haridustase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaridustase(String value) {
        this.haridustase = value;
    }

    /**
     * Gets the value of the lapsehooldusPuhkus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLapsehooldusPuhkus() {
        return lapsehooldusPuhkus;
    }

    /**
     * Sets the value of the lapsehooldusPuhkus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLapsehooldusPuhkus(String value) {
        this.lapsehooldusPuhkus = value;
    }

}
