
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Omandi jagunemine - massiivi elemendi tüüp
 * 
 * <p>Java class for omandiOsadType_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="omandiOsadType_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tegevus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sissemaksu_summa" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="sissemaksu_valuuta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="omandiliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hy_liikmesus_number" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="hy_liikmesus_pindala" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="hy_liikmesus_maksumaar_lugeja" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="hy_liikmesus_maksumaar_nimetaja" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="isikud" type="{http://arireg.x-road.eu/producer/}isikType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="osa_kitsendused" type="{http://arireg.x-road.eu/producer/}osa_kitsendusedType_v4" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "omandiOsadType_v4", propOrder = {
    "tegevus",
    "liik",
    "sissemaksuSumma",
    "sissemaksuValuuta",
    "omandiliik",
    "hyLiikmesusNumber",
    "hyLiikmesusPindala",
    "hyLiikmesusMaksumaarLugeja",
    "hyLiikmesusMaksumaarNimetaja",
    "isikud",
    "osaKitsendused"
})
public class OmandiOsadTypeV4 {

    @XmlElement(required = true)
    protected String tegevus;
    @XmlElement(required = true)
    protected String liik;
    @XmlElement(name = "sissemaksu_summa", required = true)
    protected BigDecimal sissemaksuSumma;
    @XmlElement(name = "sissemaksu_valuuta", required = true)
    protected String sissemaksuValuuta;
    @XmlElement(required = true)
    protected String omandiliik;
    @XmlElement(name = "hy_liikmesus_number")
    protected Integer hyLiikmesusNumber;
    @XmlElement(name = "hy_liikmesus_pindala")
    protected BigDecimal hyLiikmesusPindala;
    @XmlElement(name = "hy_liikmesus_maksumaar_lugeja")
    protected Integer hyLiikmesusMaksumaarLugeja;
    @XmlElement(name = "hy_liikmesus_maksumaar_nimetaja")
    protected Integer hyLiikmesusMaksumaarNimetaja;
    protected List<IsikTypeV4> isikud;
    @XmlElement(name = "osa_kitsendused", nillable = true)
    protected List<OsaKitsendusedTypeV4> osaKitsendused;

    /**
     * Gets the value of the tegevus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegevus() {
        return tegevus;
    }

    /**
     * Sets the value of the tegevus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegevus(String value) {
        this.tegevus = value;
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
     * Gets the value of the sissemaksuSumma property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSissemaksuSumma() {
        return sissemaksuSumma;
    }

    /**
     * Sets the value of the sissemaksuSumma property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSissemaksuSumma(BigDecimal value) {
        this.sissemaksuSumma = value;
    }

    /**
     * Gets the value of the sissemaksuValuuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSissemaksuValuuta() {
        return sissemaksuValuuta;
    }

    /**
     * Sets the value of the sissemaksuValuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSissemaksuValuuta(String value) {
        this.sissemaksuValuuta = value;
    }

    /**
     * Gets the value of the omandiliik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmandiliik() {
        return omandiliik;
    }

    /**
     * Sets the value of the omandiliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmandiliik(String value) {
        this.omandiliik = value;
    }

    /**
     * Gets the value of the hyLiikmesusNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHyLiikmesusNumber() {
        return hyLiikmesusNumber;
    }

    /**
     * Sets the value of the hyLiikmesusNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHyLiikmesusNumber(Integer value) {
        this.hyLiikmesusNumber = value;
    }

    /**
     * Gets the value of the hyLiikmesusPindala property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHyLiikmesusPindala() {
        return hyLiikmesusPindala;
    }

    /**
     * Sets the value of the hyLiikmesusPindala property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHyLiikmesusPindala(BigDecimal value) {
        this.hyLiikmesusPindala = value;
    }

    /**
     * Gets the value of the hyLiikmesusMaksumaarLugeja property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHyLiikmesusMaksumaarLugeja() {
        return hyLiikmesusMaksumaarLugeja;
    }

    /**
     * Sets the value of the hyLiikmesusMaksumaarLugeja property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHyLiikmesusMaksumaarLugeja(Integer value) {
        this.hyLiikmesusMaksumaarLugeja = value;
    }

    /**
     * Gets the value of the hyLiikmesusMaksumaarNimetaja property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHyLiikmesusMaksumaarNimetaja() {
        return hyLiikmesusMaksumaarNimetaja;
    }

    /**
     * Sets the value of the hyLiikmesusMaksumaarNimetaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHyLiikmesusMaksumaarNimetaja(Integer value) {
        this.hyLiikmesusMaksumaarNimetaja = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the isikud property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIsikud().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IsikTypeV4 }
     * 
     * 
     */
    public List<IsikTypeV4> getIsikud() {
        if (isikud == null) {
            isikud = new ArrayList<IsikTypeV4>();
        }
        return this.isikud;
    }

    /**
     * Gets the value of the osaKitsendused property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the osaKitsendused property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOsaKitsendused().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OsaKitsendusedTypeV4 }
     * 
     * 
     */
    public List<OsaKitsendusedTypeV4> getOsaKitsendused() {
        if (osaKitsendused == null) {
            osaKitsendused = new ArrayList<OsaKitsendusedTypeV4>();
        }
        return this.osaKitsendused;
    }

}
