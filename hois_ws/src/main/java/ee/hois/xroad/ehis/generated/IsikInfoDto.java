
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for isikInfoDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="isikInfoDto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="synniaeg" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}dateOrNothing" minOccurs="0"/&gt;
 *         &lt;element name="sugulusaste" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}sugulusaste"/&gt;
 *         &lt;element name="arvestatudPereliikmeks" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="omandabHaridust" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="rahvastikuRegistrist" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="emtaRegistrist" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="emtaMitteResident" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "isikInfoDto", propOrder = {
    "isikukood",
    "eesnimi",
    "perenimi",
    "synniaeg",
    "sugulusaste",
    "arvestatudPereliikmeks",
    "omandabHaridust",
    "rahvastikuRegistrist",
    "emtaRegistrist",
    "emtaMitteResident"
})
public class IsikInfoDto {

    @XmlElementRef(name = "isikukood", type = JAXBElement.class, required = false)
    protected JAXBElement<String> isikukood;
    @XmlElement(required = true)
    protected String eesnimi;
    @XmlElement(required = true)
    protected String perenimi;
    @XmlElementRef(name = "synniaeg", type = JAXBElement.class, required = false)
    protected JAXBElement<String> synniaeg;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Sugulusaste sugulusaste;
    protected boolean arvestatudPereliikmeks;
    protected boolean omandabHaridust;
    protected boolean rahvastikuRegistrist;
    protected boolean emtaRegistrist;
    protected Boolean emtaMitteResident;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIsikukood(JAXBElement<String> value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
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
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the perenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerenimi() {
        return perenimi;
    }

    /**
     * Sets the value of the perenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerenimi(String value) {
        this.perenimi = value;
    }

    /**
     * Gets the value of the synniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSynniaeg() {
        return synniaeg;
    }

    /**
     * Sets the value of the synniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSynniaeg(JAXBElement<String> value) {
        this.synniaeg = value;
    }

    /**
     * Gets the value of the sugulusaste property.
     * 
     * @return
     *     possible object is
     *     {@link Sugulusaste }
     *     
     */
    public Sugulusaste getSugulusaste() {
        return sugulusaste;
    }

    /**
     * Sets the value of the sugulusaste property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sugulusaste }
     *     
     */
    public void setSugulusaste(Sugulusaste value) {
        this.sugulusaste = value;
    }

    /**
     * Gets the value of the arvestatudPereliikmeks property.
     * 
     */
    public boolean isArvestatudPereliikmeks() {
        return arvestatudPereliikmeks;
    }

    /**
     * Sets the value of the arvestatudPereliikmeks property.
     * 
     */
    public void setArvestatudPereliikmeks(boolean value) {
        this.arvestatudPereliikmeks = value;
    }

    /**
     * Gets the value of the omandabHaridust property.
     * 
     */
    public boolean isOmandabHaridust() {
        return omandabHaridust;
    }

    /**
     * Sets the value of the omandabHaridust property.
     * 
     */
    public void setOmandabHaridust(boolean value) {
        this.omandabHaridust = value;
    }

    /**
     * Gets the value of the rahvastikuRegistrist property.
     * 
     */
    public boolean isRahvastikuRegistrist() {
        return rahvastikuRegistrist;
    }

    /**
     * Sets the value of the rahvastikuRegistrist property.
     * 
     */
    public void setRahvastikuRegistrist(boolean value) {
        this.rahvastikuRegistrist = value;
    }

    /**
     * Gets the value of the emtaRegistrist property.
     * 
     */
    public boolean isEmtaRegistrist() {
        return emtaRegistrist;
    }

    /**
     * Sets the value of the emtaRegistrist property.
     * 
     */
    public void setEmtaRegistrist(boolean value) {
        this.emtaRegistrist = value;
    }

    /**
     * Gets the value of the emtaMitteResident property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEmtaMitteResident() {
        return emtaMitteResident;
    }

    /**
     * Sets the value of the emtaMitteResident property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEmtaMitteResident(Boolean value) {
        this.emtaMitteResident = value;
    }

}
