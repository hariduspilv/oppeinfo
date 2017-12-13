
package ee.hois.xroad.rtip.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ZHR_PPA_POHIANDMED_0002 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZHR_PPA_POHIANDMED_0002"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ISIKUKOOD" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="11"/&gt;
 *               &lt;pattern value="\d*"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PEREKONNANIMI" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="EESNIMI" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SUGU" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="5"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SYNNIKUUPAEV" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="SYNNIKOHT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="40"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PEREKONNASEIS" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="6"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="FOTO_INTRANETTI" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="FOTO_VALISVEEBI" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AMET_INTRANETTI" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AMET_VALISVEEBI" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SYNNIKP_INTRANETTI" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="MUUDETUD" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_ALATES" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="KEHTIV_KUNI" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZHR_PPA_POHIANDMED_0002", propOrder = {
    "isikukood",
    "perekonnanimi",
    "eesnimi",
    "sugu",
    "synnikuupaev",
    "synnikoht",
    "perekonnaseis",
    "fotointranetti",
    "fotovalisveebi",
    "ametintranetti",
    "ametvalisveebi",
    "synnikpintranetti",
    "muudetud",
    "kehtivalates",
    "kehtivkuni"
})
public class ZHRPPAPOHIANDMED0002 {

    @XmlElement(name = "ISIKUKOOD")
    protected String isikukood;
    @XmlElement(name = "PEREKONNANIMI")
    protected String perekonnanimi;
    @XmlElement(name = "EESNIMI")
    protected String eesnimi;
    @XmlElement(name = "SUGU")
    protected String sugu;
    @XmlElement(name = "SYNNIKUUPAEV", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate synnikuupaev;
    @XmlElement(name = "SYNNIKOHT")
    protected String synnikoht;
    @XmlElement(name = "PEREKONNASEIS")
    protected String perekonnaseis;
    @XmlElement(name = "FOTO_INTRANETTI")
    protected String fotointranetti;
    @XmlElement(name = "FOTO_VALISVEEBI")
    protected String fotovalisveebi;
    @XmlElement(name = "AMET_INTRANETTI")
    protected String ametintranetti;
    @XmlElement(name = "AMET_VALISVEEBI")
    protected String ametvalisveebi;
    @XmlElement(name = "SYNNIKP_INTRANETTI")
    protected String synnikpintranetti;
    @XmlElement(name = "MUUDETUD", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate muudetud;
    @XmlElement(name = "KEHTIV_ALATES", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivalates;
    @XmlElement(name = "KEHTIV_KUNI", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtivkuni;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISIKUKOOD() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISIKUKOOD(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the perekonnanimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPEREKONNANIMI() {
        return perekonnanimi;
    }

    /**
     * Sets the value of the perekonnanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPEREKONNANIMI(String value) {
        this.perekonnanimi = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEESNIMI() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEESNIMI(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the sugu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUGU() {
        return sugu;
    }

    /**
     * Sets the value of the sugu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUGU(String value) {
        this.sugu = value;
    }

    /**
     * Gets the value of the synnikuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getSYNNIKUUPAEV() {
        return synnikuupaev;
    }

    /**
     * Sets the value of the synnikuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSYNNIKUUPAEV(LocalDate value) {
        this.synnikuupaev = value;
    }

    /**
     * Gets the value of the synnikoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSYNNIKOHT() {
        return synnikoht;
    }

    /**
     * Sets the value of the synnikoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSYNNIKOHT(String value) {
        this.synnikoht = value;
    }

    /**
     * Gets the value of the perekonnaseis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPEREKONNASEIS() {
        return perekonnaseis;
    }

    /**
     * Sets the value of the perekonnaseis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPEREKONNASEIS(String value) {
        this.perekonnaseis = value;
    }

    /**
     * Gets the value of the fotointranetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFOTOINTRANETTI() {
        return fotointranetti;
    }

    /**
     * Sets the value of the fotointranetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFOTOINTRANETTI(String value) {
        this.fotointranetti = value;
    }

    /**
     * Gets the value of the fotovalisveebi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFOTOVALISVEEBI() {
        return fotovalisveebi;
    }

    /**
     * Sets the value of the fotovalisveebi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFOTOVALISVEEBI(String value) {
        this.fotovalisveebi = value;
    }

    /**
     * Gets the value of the ametintranetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAMETINTRANETTI() {
        return ametintranetti;
    }

    /**
     * Sets the value of the ametintranetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMETINTRANETTI(String value) {
        this.ametintranetti = value;
    }

    /**
     * Gets the value of the ametvalisveebi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAMETVALISVEEBI() {
        return ametvalisveebi;
    }

    /**
     * Sets the value of the ametvalisveebi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAMETVALISVEEBI(String value) {
        this.ametvalisveebi = value;
    }

    /**
     * Gets the value of the synnikpintranetti property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSYNNIKPINTRANETTI() {
        return synnikpintranetti;
    }

    /**
     * Sets the value of the synnikpintranetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSYNNIKPINTRANETTI(String value) {
        this.synnikpintranetti = value;
    }

    /**
     * Gets the value of the muudetud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getMUUDETUD() {
        return muudetud;
    }

    /**
     * Sets the value of the muudetud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUUDETUD(LocalDate value) {
        this.muudetud = value;
    }

    /**
     * Gets the value of the kehtivalates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKEHTIVALATES() {
        return kehtivalates;
    }

    /**
     * Sets the value of the kehtivalates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEHTIVALATES(LocalDate value) {
        this.kehtivalates = value;
    }

    /**
     * Gets the value of the kehtivkuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKEHTIVKUNI() {
        return kehtivkuni;
    }

    /**
     * Sets the value of the kehtivkuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKEHTIVKUNI(LocalDate value) {
        this.kehtivkuni = value;
    }

}
