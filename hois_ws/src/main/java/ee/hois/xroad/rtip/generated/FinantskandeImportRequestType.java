
package ee.hois.xroad.rtip.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for finantskandeImportRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="finantskandeImportRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="I_CANCEL" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="I_COMP_CODE"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="I_DOCUMENT_TYPE" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="I_HEADER_TEXT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="25"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="T_DATA"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://rtk-v6.x-road.eu}Array"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZDOCIMPORT_01S" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="T_RETURN"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" type="{http://rtk-v6.x-road.eu}BAPIRET2" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "finantskandeImportRequestType", propOrder = {

})
public class FinantskandeImportRequestType {

    @XmlElement(name = "I_CANCEL")
    protected String icancel;
    @XmlElement(name = "I_COMP_CODE", required = true)
    protected String icompcode;
    @XmlElement(name = "I_DOCUMENT_TYPE")
    protected String idocumenttype;
    @XmlElement(name = "I_HEADER_TEXT")
    protected String iheadertext;
    @XmlElement(name = "T_DATA", required = true)
    protected FinantskandeImportRequestType.TDATA tdata;
    @XmlElement(name = "T_RETURN", required = true)
    protected FinantskandeImportRequestType.TRETURN treturn;

    /**
     * Gets the value of the icancel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getICANCEL() {
        return icancel;
    }

    /**
     * Sets the value of the icancel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setICANCEL(String value) {
        this.icancel = value;
    }

    /**
     * Gets the value of the icompcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getICOMPCODE() {
        return icompcode;
    }

    /**
     * Sets the value of the icompcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setICOMPCODE(String value) {
        this.icompcode = value;
    }

    /**
     * Gets the value of the idocumenttype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDOCUMENTTYPE() {
        return idocumenttype;
    }

    /**
     * Sets the value of the idocumenttype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDOCUMENTTYPE(String value) {
        this.idocumenttype = value;
    }

    /**
     * Gets the value of the iheadertext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIHEADERTEXT() {
        return iheadertext;
    }

    /**
     * Sets the value of the iheadertext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIHEADERTEXT(String value) {
        this.iheadertext = value;
    }

    /**
     * Gets the value of the tdata property.
     * 
     * @return
     *     possible object is
     *     {@link FinantskandeImportRequestType.TDATA }
     *     
     */
    public FinantskandeImportRequestType.TDATA getTDATA() {
        return tdata;
    }

    /**
     * Sets the value of the tdata property.
     * 
     * @param value
     *     allowed object is
     *     {@link FinantskandeImportRequestType.TDATA }
     *     
     */
    public void setTDATA(FinantskandeImportRequestType.TDATA value) {
        this.tdata = value;
    }

    /**
     * Gets the value of the treturn property.
     * 
     * @return
     *     possible object is
     *     {@link FinantskandeImportRequestType.TRETURN }
     *     
     */
    public FinantskandeImportRequestType.TRETURN getTRETURN() {
        return treturn;
    }

    /**
     * Sets the value of the treturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link FinantskandeImportRequestType.TRETURN }
     *     
     */
    public void setTRETURN(FinantskandeImportRequestType.TRETURN value) {
        this.treturn = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://rtk-v6.x-road.eu}Array"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZDOCIMPORT_01S" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class TDATA
        extends Array
    {


    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="item" type="{http://rtk-v6.x-road.eu}BAPIRET2" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "item"
    })
    public static class TRETURN {

        protected List<BAPIRET2> item;

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BAPIRET2 }
         * 
         * 
         */
        public List<BAPIRET2> getItem() {
            if (item == null) {
                item = new ArrayList<BAPIRET2>();
            }
            return this.item;
        }

    }

}
