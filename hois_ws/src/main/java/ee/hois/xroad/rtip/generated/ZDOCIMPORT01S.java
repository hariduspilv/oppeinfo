
package ee.hois.xroad.rtip.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZDOCIMPORT_01S complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZDOCIMPORT_01S"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DOC_NUMBER" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PSTNG_DATE" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="GL_ACCOUNT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="AMT_DOCCUR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double"&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="CURRENCY" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="5"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ITEM_TEXT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="50"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TRADE_ID" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="6"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ALLOC_NMBR" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="18"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TRANS_TYPE" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="3"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="COMM_ITEM" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="14"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="BUS_AREA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="FUNC_AREA" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="16"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="FUND" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="FUND_CENTER" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="16"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="COST_CENTER" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PRFT_CENTER" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TAX_CODE" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ORDER" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="12"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="GRANT" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZDOCIMPORT_01S", propOrder = {
    "docnumber",
    "pstngdate",
    "glaccount",
    "amtdoccur",
    "currency",
    "itemtext",
    "tradeid",
    "allocnmbr",
    "transtype",
    "commitem",
    "busarea",
    "funcarea",
    "fund",
    "fundcenter",
    "costcenter",
    "prftcenter",
    "taxcode",
    "order",
    "grant"
})
public class ZDOCIMPORT01S {

    @XmlElement(name = "DOC_NUMBER")
    protected String docnumber;
    @XmlElement(name = "PSTNG_DATE")
    protected String pstngdate;
    @XmlElement(name = "GL_ACCOUNT")
    protected String glaccount;
    @XmlElement(name = "AMT_DOCCUR")
    protected Double amtdoccur;
    @XmlElement(name = "CURRENCY")
    protected String currency;
    @XmlElement(name = "ITEM_TEXT")
    protected String itemtext;
    @XmlElement(name = "TRADE_ID")
    protected String tradeid;
    @XmlElement(name = "ALLOC_NMBR")
    protected String allocnmbr;
    @XmlElement(name = "TRANS_TYPE")
    protected String transtype;
    @XmlElement(name = "COMM_ITEM")
    protected String commitem;
    @XmlElement(name = "BUS_AREA")
    protected String busarea;
    @XmlElement(name = "FUNC_AREA")
    protected String funcarea;
    @XmlElement(name = "FUND")
    protected String fund;
    @XmlElement(name = "FUND_CENTER")
    protected String fundcenter;
    @XmlElement(name = "COST_CENTER")
    protected String costcenter;
    @XmlElement(name = "PRFT_CENTER")
    protected String prftcenter;
    @XmlElement(name = "TAX_CODE")
    protected String taxcode;
    @XmlElement(name = "ORDER")
    protected String order;
    @XmlElement(name = "GRANT")
    protected String grant;

    /**
     * Gets the value of the docnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOCNUMBER() {
        return docnumber;
    }

    /**
     * Sets the value of the docnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOCNUMBER(String value) {
        this.docnumber = value;
    }

    /**
     * Gets the value of the pstngdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPSTNGDATE() {
        return pstngdate;
    }

    /**
     * Sets the value of the pstngdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPSTNGDATE(String value) {
        this.pstngdate = value;
    }

    /**
     * Gets the value of the glaccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGLACCOUNT() {
        return glaccount;
    }

    /**
     * Sets the value of the glaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGLACCOUNT(String value) {
        this.glaccount = value;
    }

    /**
     * Gets the value of the amtdoccur property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAMTDOCCUR() {
        return amtdoccur;
    }

    /**
     * Sets the value of the amtdoccur property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAMTDOCCUR(Double value) {
        this.amtdoccur = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCURRENCY() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCURRENCY(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the itemtext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getITEMTEXT() {
        return itemtext;
    }

    /**
     * Sets the value of the itemtext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setITEMTEXT(String value) {
        this.itemtext = value;
    }

    /**
     * Gets the value of the tradeid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRADEID() {
        return tradeid;
    }

    /**
     * Sets the value of the tradeid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRADEID(String value) {
        this.tradeid = value;
    }

    /**
     * Gets the value of the allocnmbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getALLOCNMBR() {
        return allocnmbr;
    }

    /**
     * Sets the value of the allocnmbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setALLOCNMBR(String value) {
        this.allocnmbr = value;
    }

    /**
     * Gets the value of the transtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRANSTYPE() {
        return transtype;
    }

    /**
     * Sets the value of the transtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRANSTYPE(String value) {
        this.transtype = value;
    }

    /**
     * Gets the value of the commitem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMMITEM() {
        return commitem;
    }

    /**
     * Sets the value of the commitem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMMITEM(String value) {
        this.commitem = value;
    }

    /**
     * Gets the value of the busarea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUSAREA() {
        return busarea;
    }

    /**
     * Sets the value of the busarea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUSAREA(String value) {
        this.busarea = value;
    }

    /**
     * Gets the value of the funcarea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFUNCAREA() {
        return funcarea;
    }

    /**
     * Sets the value of the funcarea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFUNCAREA(String value) {
        this.funcarea = value;
    }

    /**
     * Gets the value of the fund property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFUND() {
        return fund;
    }

    /**
     * Sets the value of the fund property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFUND(String value) {
        this.fund = value;
    }

    /**
     * Gets the value of the fundcenter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFUNDCENTER() {
        return fundcenter;
    }

    /**
     * Sets the value of the fundcenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFUNDCENTER(String value) {
        this.fundcenter = value;
    }

    /**
     * Gets the value of the costcenter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOSTCENTER() {
        return costcenter;
    }

    /**
     * Sets the value of the costcenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOSTCENTER(String value) {
        this.costcenter = value;
    }

    /**
     * Gets the value of the prftcenter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRFTCENTER() {
        return prftcenter;
    }

    /**
     * Sets the value of the prftcenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRFTCENTER(String value) {
        this.prftcenter = value;
    }

    /**
     * Gets the value of the taxcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTAXCODE() {
        return taxcode;
    }

    /**
     * Sets the value of the taxcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTAXCODE(String value) {
        this.taxcode = value;
    }

    /**
     * Gets the value of the order property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORDER() {
        return order;
    }

    /**
     * Sets the value of the order property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORDER(String value) {
        this.order = value;
    }

    /**
     * Gets the value of the grant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGRANT() {
        return grant;
    }

    /**
     * Sets the value of the grant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGRANT(String value) {
        this.grant = value;
    }

}
