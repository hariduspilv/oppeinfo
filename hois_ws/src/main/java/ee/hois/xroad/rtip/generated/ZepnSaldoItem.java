
package ee.hois.xroad.rtip.generated;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ZepnSaldoItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZepnSaldoItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KUNNR" type="{http://rtk-v6.x-road.eu}char10"/&gt;
 *         &lt;element name="NAME1" type="{http://rtk-v6.x-road.eu}char30"/&gt;
 *         &lt;element name="VBELN" type="{http://rtk-v6.x-road.eu}char10"/&gt;
 *         &lt;element name="KIDNO" type="{http://rtk-v6.x-road.eu}char30"/&gt;
 *         &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="BLDAT" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="ZALDT" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="DMBTR" type="{http://rtk-v6.x-road.eu}curr13.2"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZepnSaldoItem", propOrder = {
    "kunnr",
    "name1",
    "vbeln",
    "kidno",
    "budat",
    "bldat",
    "zaldt",
    "dmbtr"
})
public class ZepnSaldoItem {

    @XmlElement(name = "KUNNR", required = true)
    protected String kunnr;
    @XmlElement(name = "NAME1", required = true)
    protected String name1;
    @XmlElement(name = "VBELN", required = true)
    protected String vbeln;
    @XmlElement(name = "KIDNO", required = true)
    protected String kidno;
    @XmlElement(name = "BUDAT", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate budat;
    @XmlElement(name = "BLDAT", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate bldat;
    @XmlElement(name = "ZALDT", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate zaldt;
    @XmlElement(name = "DMBTR", required = true)
    protected BigDecimal dmbtr;

    /**
     * Gets the value of the kunnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNNR() {
        return kunnr;
    }

    /**
     * Sets the value of the kunnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNNR(String value) {
        this.kunnr = value;
    }

    /**
     * Gets the value of the name1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAME1() {
        return name1;
    }

    /**
     * Sets the value of the name1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAME1(String value) {
        this.name1 = value;
    }

    /**
     * Gets the value of the vbeln property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBELN() {
        return vbeln;
    }

    /**
     * Sets the value of the vbeln property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBELN(String value) {
        this.vbeln = value;
    }

    /**
     * Gets the value of the kidno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKIDNO() {
        return kidno;
    }

    /**
     * Sets the value of the kidno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKIDNO(String value) {
        this.kidno = value;
    }

    /**
     * Gets the value of the budat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getBUDAT() {
        return budat;
    }

    /**
     * Sets the value of the budat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUDAT(LocalDate value) {
        this.budat = value;
    }

    /**
     * Gets the value of the bldat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getBLDAT() {
        return bldat;
    }

    /**
     * Sets the value of the bldat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLDAT(LocalDate value) {
        this.bldat = value;
    }

    /**
     * Gets the value of the zaldt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getZALDT() {
        return zaldt;
    }

    /**
     * Sets the value of the zaldt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZALDT(LocalDate value) {
        this.zaldt = value;
    }

    /**
     * Gets the value of the dmbtr property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDMBTR() {
        return dmbtr;
    }

    /**
     * Sets the value of the dmbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDMBTR(BigDecimal value) {
        this.dmbtr = value;
    }

}
