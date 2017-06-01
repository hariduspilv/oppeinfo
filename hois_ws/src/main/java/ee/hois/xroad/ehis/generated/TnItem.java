
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tnItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tnItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klOkLiik" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klOpperuhm" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="klKategooria" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="klEkTase" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="oppijaArv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="tunnistusArv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kuni8" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kuni26" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kuni80" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kuni240" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="yle240" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kokku" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tnItem", propOrder = {
    "klOkLiik",
    "nimetus",
    "klOpperuhm",
    "klKategooria",
    "klEkTase",
    "oppijaArv",
    "tunnistusArv",
    "kuni8",
    "kuni26",
    "kuni80",
    "kuni240",
    "yle240",
    "kokku"
})
public class TnItem {

    protected BigInteger klOkLiik;
    protected String nimetus;
    protected BigInteger klOpperuhm;
    protected BigInteger klKategooria;
    protected BigInteger klEkTase;
    protected BigInteger oppijaArv;
    protected BigInteger tunnistusArv;
    protected BigInteger kuni8;
    protected BigInteger kuni26;
    protected BigInteger kuni80;
    protected BigInteger kuni240;
    protected BigInteger yle240;
    protected BigInteger kokku;

    /**
     * Gets the value of the klOkLiik property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlOkLiik() {
        return klOkLiik;
    }

    /**
     * Sets the value of the klOkLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlOkLiik(BigInteger value) {
        this.klOkLiik = value;
    }

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetus(String value) {
        this.nimetus = value;
    }

    /**
     * Gets the value of the klOpperuhm property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlOpperuhm() {
        return klOpperuhm;
    }

    /**
     * Sets the value of the klOpperuhm property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlOpperuhm(BigInteger value) {
        this.klOpperuhm = value;
    }

    /**
     * Gets the value of the klKategooria property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlKategooria() {
        return klKategooria;
    }

    /**
     * Sets the value of the klKategooria property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlKategooria(BigInteger value) {
        this.klKategooria = value;
    }

    /**
     * Gets the value of the klEkTase property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlEkTase() {
        return klEkTase;
    }

    /**
     * Sets the value of the klEkTase property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlEkTase(BigInteger value) {
        this.klEkTase = value;
    }

    /**
     * Gets the value of the oppijaArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppijaArv() {
        return oppijaArv;
    }

    /**
     * Sets the value of the oppijaArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppijaArv(BigInteger value) {
        this.oppijaArv = value;
    }

    /**
     * Gets the value of the tunnistusArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTunnistusArv() {
        return tunnistusArv;
    }

    /**
     * Sets the value of the tunnistusArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTunnistusArv(BigInteger value) {
        this.tunnistusArv = value;
    }

    /**
     * Gets the value of the kuni8 property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKuni8() {
        return kuni8;
    }

    /**
     * Sets the value of the kuni8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKuni8(BigInteger value) {
        this.kuni8 = value;
    }

    /**
     * Gets the value of the kuni26 property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKuni26() {
        return kuni26;
    }

    /**
     * Sets the value of the kuni26 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKuni26(BigInteger value) {
        this.kuni26 = value;
    }

    /**
     * Gets the value of the kuni80 property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKuni80() {
        return kuni80;
    }

    /**
     * Sets the value of the kuni80 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKuni80(BigInteger value) {
        this.kuni80 = value;
    }

    /**
     * Gets the value of the kuni240 property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKuni240() {
        return kuni240;
    }

    /**
     * Sets the value of the kuni240 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKuni240(BigInteger value) {
        this.kuni240 = value;
    }

    /**
     * Gets the value of the yle240 property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getYle240() {
        return yle240;
    }

    /**
     * Sets the value of the yle240 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setYle240(BigInteger value) {
        this.yle240 = value;
    }

    /**
     * Gets the value of the kokku property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKokku() {
        return kokku;
    }

    /**
     * Sets the value of the kokku property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKokku(BigInteger value) {
        this.kokku = value;
    }

}
