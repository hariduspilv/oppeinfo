
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for detailandmed_v5_ettevotja complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_ettevotja"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ettevotja_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kmkr_number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="yldandmed" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_yldandmed" minOccurs="0"/&gt;
 *         &lt;element name="isikuandmed" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_isikuandmed" minOccurs="0"/&gt;
 *         &lt;element name="kommertspandiandmed" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_kommertspandiandmed" minOccurs="0"/&gt;
 *         &lt;element name="menetluses_avaldused" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_dokumendid" minOccurs="0"/&gt;
 *         &lt;element name="maarused" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_maarused" minOccurs="0"/&gt;
 *         &lt;element name="registrikaardid" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_registrikaardid" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_ettevotja", propOrder = {
    "ariregistriKood",
    "ettevotjaId",
    "nimi",
    "kmkrNumber",
    "yldandmed",
    "isikuandmed",
    "kommertspandiandmed",
    "menetlusesAvaldused",
    "maarused",
    "registrikaardid"
})
public class DetailandmedV5Ettevotja {

    @XmlElement(name = "ariregistri_kood", required = true)
    protected BigInteger ariregistriKood;
    @XmlElement(name = "ettevotja_id", required = true)
    protected BigInteger ettevotjaId;
    @XmlElement(required = true)
    protected String nimi;
    @XmlElement(name = "kmkr_number")
    protected String kmkrNumber;
    protected DetailandmedV5Yldandmed yldandmed;
    protected DetailandmedV5Isikuandmed isikuandmed;
    protected DetailandmedV5Kommertspandiandmed kommertspandiandmed;
    @XmlElement(name = "menetluses_avaldused")
    protected DetailandmedV5Dokumendid menetlusesAvaldused;
    protected DetailandmedV5Maarused maarused;
    protected DetailandmedV5Registrikaardid registrikaardid;

    /**
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAriregistriKood(BigInteger value) {
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
     * Gets the value of the nimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Sets the value of the nimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimi(String value) {
        this.nimi = value;
    }

    /**
     * Gets the value of the kmkrNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKmkrNumber() {
        return kmkrNumber;
    }

    /**
     * Sets the value of the kmkrNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKmkrNumber(String value) {
        this.kmkrNumber = value;
    }

    /**
     * Gets the value of the yldandmed property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Yldandmed }
     *     
     */
    public DetailandmedV5Yldandmed getYldandmed() {
        return yldandmed;
    }

    /**
     * Sets the value of the yldandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Yldandmed }
     *     
     */
    public void setYldandmed(DetailandmedV5Yldandmed value) {
        this.yldandmed = value;
    }

    /**
     * Gets the value of the isikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Isikuandmed }
     *     
     */
    public DetailandmedV5Isikuandmed getIsikuandmed() {
        return isikuandmed;
    }

    /**
     * Sets the value of the isikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Isikuandmed }
     *     
     */
    public void setIsikuandmed(DetailandmedV5Isikuandmed value) {
        this.isikuandmed = value;
    }

    /**
     * Gets the value of the kommertspandiandmed property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Kommertspandiandmed }
     *     
     */
    public DetailandmedV5Kommertspandiandmed getKommertspandiandmed() {
        return kommertspandiandmed;
    }

    /**
     * Sets the value of the kommertspandiandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Kommertspandiandmed }
     *     
     */
    public void setKommertspandiandmed(DetailandmedV5Kommertspandiandmed value) {
        this.kommertspandiandmed = value;
    }

    /**
     * Gets the value of the menetlusesAvaldused property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Dokumendid }
     *     
     */
    public DetailandmedV5Dokumendid getMenetlusesAvaldused() {
        return menetlusesAvaldused;
    }

    /**
     * Sets the value of the menetlusesAvaldused property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Dokumendid }
     *     
     */
    public void setMenetlusesAvaldused(DetailandmedV5Dokumendid value) {
        this.menetlusesAvaldused = value;
    }

    /**
     * Gets the value of the maarused property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Maarused }
     *     
     */
    public DetailandmedV5Maarused getMaarused() {
        return maarused;
    }

    /**
     * Sets the value of the maarused property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Maarused }
     *     
     */
    public void setMaarused(DetailandmedV5Maarused value) {
        this.maarused = value;
    }

    /**
     * Gets the value of the registrikaardid property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5Registrikaardid }
     *     
     */
    public DetailandmedV5Registrikaardid getRegistrikaardid() {
        return registrikaardid;
    }

    /**
     * Sets the value of the registrikaardid property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5Registrikaardid }
     *     
     */
    public void setRegistrikaardid(DetailandmedV5Registrikaardid value) {
        this.registrikaardid = value;
    }

}
