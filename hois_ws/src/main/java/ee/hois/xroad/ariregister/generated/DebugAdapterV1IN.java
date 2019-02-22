
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for debugAdapter_v1_IN complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="debugAdapter_v1_IN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dt_string" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dt_boolean" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="dt_base64Binary" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *         &lt;element name="dt_hexBinary" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="dt_float" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="dt_double" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="dt_decimal" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="dt_integer" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="dt_duration" type="{http://www.w3.org/2001/XMLSchema}duration"/&gt;
 *         &lt;element name="dt_dateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="dt_time" type="{http://www.w3.org/2001/XMLSchema}time"/&gt;
 *         &lt;element name="dt_date" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="dt_string_multi" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dt_string_multiholder" type="{http://arireg.x-road.eu/producer/}debugAdapterStringMultiHolder"/&gt;
 *         &lt;element name="dt_obj_multiholder" type="{http://arireg.x-road.eu/producer/}debugAdapterObjMultiHolder"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "debugAdapter_v1_IN", propOrder = {
    "dtString",
    "dtBoolean",
    "dtBase64Binary",
    "dtHexBinary",
    "dtFloat",
    "dtDouble",
    "dtDecimal",
    "dtInteger",
    "dtDuration",
    "dtDateTime",
    "dtTime",
    "dtDate",
    "dtStringMulti",
    "dtStringMultiholder",
    "dtObjMultiholder"
})
public class DebugAdapterV1IN {

    @XmlElement(name = "dt_string", required = true)
    protected String dtString;
    @XmlElement(name = "dt_boolean")
    protected boolean dtBoolean;
    @XmlElement(name = "dt_base64Binary", required = true)
    protected byte[] dtBase64Binary;
    @XmlElement(name = "dt_hexBinary", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] dtHexBinary;
    @XmlElement(name = "dt_float")
    protected float dtFloat;
    @XmlElement(name = "dt_double")
    protected double dtDouble;
    @XmlElement(name = "dt_decimal", required = true)
    protected BigDecimal dtDecimal;
    @XmlElement(name = "dt_integer", required = true)
    protected BigInteger dtInteger;
    @XmlElement(name = "dt_duration", required = true)
    protected Duration dtDuration;
    @XmlElement(name = "dt_dateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtDateTime;
    @XmlElement(name = "dt_time", required = true)
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar dtTime;
    @XmlElement(name = "dt_date", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dtDate;
    @XmlElement(name = "dt_string_multi")
    protected List<String> dtStringMulti;
    @XmlElement(name = "dt_string_multiholder", required = true)
    protected DebugAdapterStringMultiHolder dtStringMultiholder;
    @XmlElement(name = "dt_obj_multiholder", required = true)
    protected DebugAdapterObjMultiHolder dtObjMultiholder;

    /**
     * Gets the value of the dtString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtString() {
        return dtString;
    }

    /**
     * Sets the value of the dtString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtString(String value) {
        this.dtString = value;
    }

    /**
     * Gets the value of the dtBoolean property.
     * 
     */
    public boolean isDtBoolean() {
        return dtBoolean;
    }

    /**
     * Sets the value of the dtBoolean property.
     * 
     */
    public void setDtBoolean(boolean value) {
        this.dtBoolean = value;
    }

    /**
     * Gets the value of the dtBase64Binary property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDtBase64Binary() {
        return dtBase64Binary;
    }

    /**
     * Sets the value of the dtBase64Binary property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDtBase64Binary(byte[] value) {
        this.dtBase64Binary = value;
    }

    /**
     * Gets the value of the dtHexBinary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getDtHexBinary() {
        return dtHexBinary;
    }

    /**
     * Sets the value of the dtHexBinary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtHexBinary(byte[] value) {
        this.dtHexBinary = value;
    }

    /**
     * Gets the value of the dtFloat property.
     * 
     */
    public float getDtFloat() {
        return dtFloat;
    }

    /**
     * Sets the value of the dtFloat property.
     * 
     */
    public void setDtFloat(float value) {
        this.dtFloat = value;
    }

    /**
     * Gets the value of the dtDouble property.
     * 
     */
    public double getDtDouble() {
        return dtDouble;
    }

    /**
     * Sets the value of the dtDouble property.
     * 
     */
    public void setDtDouble(double value) {
        this.dtDouble = value;
    }

    /**
     * Gets the value of the dtDecimal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDtDecimal() {
        return dtDecimal;
    }

    /**
     * Sets the value of the dtDecimal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDtDecimal(BigDecimal value) {
        this.dtDecimal = value;
    }

    /**
     * Gets the value of the dtInteger property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDtInteger() {
        return dtInteger;
    }

    /**
     * Sets the value of the dtInteger property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDtInteger(BigInteger value) {
        this.dtInteger = value;
    }

    /**
     * Gets the value of the dtDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDtDuration() {
        return dtDuration;
    }

    /**
     * Sets the value of the dtDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDtDuration(Duration value) {
        this.dtDuration = value;
    }

    /**
     * Gets the value of the dtDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtDateTime() {
        return dtDateTime;
    }

    /**
     * Sets the value of the dtDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtDateTime(XMLGregorianCalendar value) {
        this.dtDateTime = value;
    }

    /**
     * Gets the value of the dtTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtTime() {
        return dtTime;
    }

    /**
     * Sets the value of the dtTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtTime(XMLGregorianCalendar value) {
        this.dtTime = value;
    }

    /**
     * Gets the value of the dtDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtDate() {
        return dtDate;
    }

    /**
     * Sets the value of the dtDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtDate(XMLGregorianCalendar value) {
        this.dtDate = value;
    }

    /**
     * Gets the value of the dtStringMulti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dtStringMulti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDtStringMulti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDtStringMulti() {
        if (dtStringMulti == null) {
            dtStringMulti = new ArrayList<String>();
        }
        return this.dtStringMulti;
    }

    /**
     * Gets the value of the dtStringMultiholder property.
     * 
     * @return
     *     possible object is
     *     {@link DebugAdapterStringMultiHolder }
     *     
     */
    public DebugAdapterStringMultiHolder getDtStringMultiholder() {
        return dtStringMultiholder;
    }

    /**
     * Sets the value of the dtStringMultiholder property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebugAdapterStringMultiHolder }
     *     
     */
    public void setDtStringMultiholder(DebugAdapterStringMultiHolder value) {
        this.dtStringMultiholder = value;
    }

    /**
     * Gets the value of the dtObjMultiholder property.
     * 
     * @return
     *     possible object is
     *     {@link DebugAdapterObjMultiHolder }
     *     
     */
    public DebugAdapterObjMultiHolder getDtObjMultiholder() {
        return dtObjMultiholder;
    }

    /**
     * Sets the value of the dtObjMultiholder property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebugAdapterObjMultiHolder }
     *     
     */
    public void setDtObjMultiholder(DebugAdapterObjMultiHolder value) {
        this.dtObjMultiholder = value;
    }

}
