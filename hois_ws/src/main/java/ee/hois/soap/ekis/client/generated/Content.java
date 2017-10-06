
package ee.hois.soap.ekis.client.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for content complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="content"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id_code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="first_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="last_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="start_date" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="end_date" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="load" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="curricula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="form" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="group" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="finsource" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outerschool" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lang" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kudos" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="degree" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="eap" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "content", propOrder = {
    "idCode",
    "firstName",
    "lastName",
    "startDate",
    "endDate",
    "reason",
    "load",
    "curricula",
    "form",
    "group",
    "finsource",
    "outerschool",
    "lang",
    "kudos",
    "degree",
    "eap"
})
public class Content {

    @XmlElement(name = "id_code", required = true)
    protected String idCode;
    @XmlElement(name = "first_name", required = true)
    protected String firstName;
    @XmlElement(name = "last_name", required = true)
    protected String lastName;
    @XmlElement(name = "start_date", required = true)
    protected String startDate;
    @XmlElement(name = "end_date", required = true)
    protected String endDate;
    @XmlElement(required = true)
    protected String reason;
    @XmlElement(required = true)
    protected String load;
    @XmlElement(required = true)
    protected String curricula;
    @XmlElement(required = true)
    protected String form;
    @XmlElement(required = true)
    protected String group;
    @XmlElement(required = true)
    protected String finsource;
    @XmlElement(required = true)
    protected String outerschool;
    @XmlElement(required = true)
    protected String lang;
    @XmlElement(required = true)
    protected String kudos;
    @XmlElement(required = true)
    protected String degree;
    @XmlElement(required = true)
    protected String eap;

    /**
     * Gets the value of the idCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCode() {
        return idCode;
    }

    /**
     * Sets the value of the idCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCode(String value) {
        this.idCode = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(String value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDate(String value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the load property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoad() {
        return load;
    }

    /**
     * Sets the value of the load property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoad(String value) {
        this.load = value;
    }

    /**
     * Gets the value of the curricula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurricula() {
        return curricula;
    }

    /**
     * Sets the value of the curricula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurricula(String value) {
        this.curricula = value;
    }

    /**
     * Gets the value of the form property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForm() {
        return form;
    }

    /**
     * Sets the value of the form property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForm(String value) {
        this.form = value;
    }

    /**
     * Gets the value of the group property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the value of the group property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroup(String value) {
        this.group = value;
    }

    /**
     * Gets the value of the finsource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinsource() {
        return finsource;
    }

    /**
     * Sets the value of the finsource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinsource(String value) {
        this.finsource = value;
    }

    /**
     * Gets the value of the outerschool property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOuterschool() {
        return outerschool;
    }

    /**
     * Sets the value of the outerschool property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOuterschool(String value) {
        this.outerschool = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the kudos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKudos() {
        return kudos;
    }

    /**
     * Sets the value of the kudos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKudos(String value) {
        this.kudos = value;
    }

    /**
     * Gets the value of the degree property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Sets the value of the degree property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDegree(String value) {
        this.degree = value;
    }

    /**
     * Gets the value of the eap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEap() {
        return eap;
    }

    /**
     * Sets the value of the eap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEap(String value) {
        this.eap = value;
    }

}
