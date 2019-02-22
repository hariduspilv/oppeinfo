
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for detailandmed_v5_isikuandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_isikuandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kaardile_kantud_isikud" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_kaardile_kantud_isikud"/&gt;
 *         &lt;element name="esindusoiguse_normaalregulatsioonid" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_esindusoiguse_normaalregulatsioonid" minOccurs="0"/&gt;
 *         &lt;element name="esindusoiguse_eritingimused" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_esindusoiguse_eritingimused" minOccurs="0"/&gt;
 *         &lt;element name="osapandid_tingimuslikud_voorandamised" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_osa_pandid" minOccurs="0"/&gt;
 *         &lt;element name="kaardivalised_isikud" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_kaardivalised_isikud"/&gt;
 *         &lt;element name="hooneyhistu_liikmed" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_hooneyhistu_liikmed"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_isikuandmed", propOrder = {
    "kaardileKantudIsikud",
    "esindusoiguseNormaalregulatsioonid",
    "esindusoiguseEritingimused",
    "osapandidTingimuslikudVoorandamised",
    "kaardivalisedIsikud",
    "hooneyhistuLiikmed"
})
public class DetailandmedV5Isikuandmed {

    @XmlElement(name = "kaardile_kantud_isikud", required = true)
    protected DetailandmedV5KaardileKantudIsikud kaardileKantudIsikud;
    @XmlElement(name = "esindusoiguse_normaalregulatsioonid")
    protected DetailandmedV5EsindusoiguseNormaalregulatsioonid esindusoiguseNormaalregulatsioonid;
    @XmlElement(name = "esindusoiguse_eritingimused")
    protected DetailandmedV5EsindusoiguseEritingimused esindusoiguseEritingimused;
    @XmlElement(name = "osapandid_tingimuslikud_voorandamised")
    protected DetailandmedV5OsaPandid osapandidTingimuslikudVoorandamised;
    @XmlElement(name = "kaardivalised_isikud", required = true)
    protected DetailandmedV5KaardivalisedIsikud kaardivalisedIsikud;
    @XmlElement(name = "hooneyhistu_liikmed", required = true)
    protected DetailandmedV5HooneyhistuLiikmed hooneyhistuLiikmed;

    /**
     * Gets the value of the kaardileKantudIsikud property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5KaardileKantudIsikud }
     *     
     */
    public DetailandmedV5KaardileKantudIsikud getKaardileKantudIsikud() {
        return kaardileKantudIsikud;
    }

    /**
     * Sets the value of the kaardileKantudIsikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5KaardileKantudIsikud }
     *     
     */
    public void setKaardileKantudIsikud(DetailandmedV5KaardileKantudIsikud value) {
        this.kaardileKantudIsikud = value;
    }

    /**
     * Gets the value of the esindusoiguseNormaalregulatsioonid property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5EsindusoiguseNormaalregulatsioonid }
     *     
     */
    public DetailandmedV5EsindusoiguseNormaalregulatsioonid getEsindusoiguseNormaalregulatsioonid() {
        return esindusoiguseNormaalregulatsioonid;
    }

    /**
     * Sets the value of the esindusoiguseNormaalregulatsioonid property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5EsindusoiguseNormaalregulatsioonid }
     *     
     */
    public void setEsindusoiguseNormaalregulatsioonid(DetailandmedV5EsindusoiguseNormaalregulatsioonid value) {
        this.esindusoiguseNormaalregulatsioonid = value;
    }

    /**
     * Gets the value of the esindusoiguseEritingimused property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5EsindusoiguseEritingimused }
     *     
     */
    public DetailandmedV5EsindusoiguseEritingimused getEsindusoiguseEritingimused() {
        return esindusoiguseEritingimused;
    }

    /**
     * Sets the value of the esindusoiguseEritingimused property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5EsindusoiguseEritingimused }
     *     
     */
    public void setEsindusoiguseEritingimused(DetailandmedV5EsindusoiguseEritingimused value) {
        this.esindusoiguseEritingimused = value;
    }

    /**
     * Gets the value of the osapandidTingimuslikudVoorandamised property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5OsaPandid }
     *     
     */
    public DetailandmedV5OsaPandid getOsapandidTingimuslikudVoorandamised() {
        return osapandidTingimuslikudVoorandamised;
    }

    /**
     * Sets the value of the osapandidTingimuslikudVoorandamised property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5OsaPandid }
     *     
     */
    public void setOsapandidTingimuslikudVoorandamised(DetailandmedV5OsaPandid value) {
        this.osapandidTingimuslikudVoorandamised = value;
    }

    /**
     * Gets the value of the kaardivalisedIsikud property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5KaardivalisedIsikud }
     *     
     */
    public DetailandmedV5KaardivalisedIsikud getKaardivalisedIsikud() {
        return kaardivalisedIsikud;
    }

    /**
     * Sets the value of the kaardivalisedIsikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5KaardivalisedIsikud }
     *     
     */
    public void setKaardivalisedIsikud(DetailandmedV5KaardivalisedIsikud value) {
        this.kaardivalisedIsikud = value;
    }

    /**
     * Gets the value of the hooneyhistuLiikmed property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5HooneyhistuLiikmed }
     *     
     */
    public DetailandmedV5HooneyhistuLiikmed getHooneyhistuLiikmed() {
        return hooneyhistuLiikmed;
    }

    /**
     * Sets the value of the hooneyhistuLiikmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5HooneyhistuLiikmed }
     *     
     */
    public void setHooneyhistuLiikmed(DetailandmedV5HooneyhistuLiikmed value) {
        this.hooneyhistuLiikmed = value;
    }

}
