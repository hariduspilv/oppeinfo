
package ee.hois.xroad.rtip.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Z_EMPLOEESRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Z_EMPLOEESRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="COMPANYCODE" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="DATE_FROM" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="DATE_TO" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="LAHKUJAD"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZLEAVERS" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="PUUDUMINE"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZABSENCE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TOOTAJAD"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZEMPLOEE1" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "Z_EMPLOEESRequestType", namespace = "http://rtk-v6.x-road.eu", propOrder = {

})
public class ZEMPLOEESRequestType {

    @XmlElement(name = "COMPANYCODE")
    protected String companycode;
    @XmlElement(name = "DATE_FROM")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datefrom;
    @XmlElement(name = "DATE_TO")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateto;
    @XmlElement(name = "LAHKUJAD", required = true)
    protected ZEMPLOEESRequestType.LAHKUJAD lahkujad;
    @XmlElement(name = "PUUDUMINE", required = true)
    protected ZEMPLOEESRequestType.PUUDUMINE puudumine;
    @XmlElement(name = "TOOTAJAD", required = true)
    protected ZEMPLOEESRequestType.TOOTAJAD tootajad;

    /**
     * Gets the value of the companycode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCOMPANYCODE() {
        return companycode;
    }

    /**
     * Sets the value of the companycode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCOMPANYCODE(String value) {
        this.companycode = value;
    }

    /**
     * Gets the value of the datefrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATEFROM() {
        return datefrom;
    }

    /**
     * Sets the value of the datefrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATEFROM(XMLGregorianCalendar value) {
        this.datefrom = value;
    }

    /**
     * Gets the value of the dateto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATETO() {
        return dateto;
    }

    /**
     * Sets the value of the dateto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATETO(XMLGregorianCalendar value) {
        this.dateto = value;
    }

    /**
     * Gets the value of the lahkujad property.
     * 
     * @return
     *     possible object is
     *     {@link ZEMPLOEESRequestType.LAHKUJAD }
     *     
     */
    public ZEMPLOEESRequestType.LAHKUJAD getLAHKUJAD() {
        return lahkujad;
    }

    /**
     * Sets the value of the lahkujad property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZEMPLOEESRequestType.LAHKUJAD }
     *     
     */
    public void setLAHKUJAD(ZEMPLOEESRequestType.LAHKUJAD value) {
        this.lahkujad = value;
    }

    /**
     * Gets the value of the puudumine property.
     * 
     * @return
     *     possible object is
     *     {@link ZEMPLOEESRequestType.PUUDUMINE }
     *     
     */
    public ZEMPLOEESRequestType.PUUDUMINE getPUUDUMINE() {
        return puudumine;
    }

    /**
     * Sets the value of the puudumine property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZEMPLOEESRequestType.PUUDUMINE }
     *     
     */
    public void setPUUDUMINE(ZEMPLOEESRequestType.PUUDUMINE value) {
        this.puudumine = value;
    }

    /**
     * Gets the value of the tootajad property.
     * 
     * @return
     *     possible object is
     *     {@link ZEMPLOEESRequestType.TOOTAJAD }
     *     
     */
    public ZEMPLOEESRequestType.TOOTAJAD getTOOTAJAD() {
        return tootajad;
    }

    /**
     * Sets the value of the tootajad property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZEMPLOEESRequestType.TOOTAJAD }
     *     
     */
    public void setTOOTAJAD(ZEMPLOEESRequestType.TOOTAJAD value) {
        this.tootajad = value;
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
     *         &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZLEAVERS" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    public static class LAHKUJAD {

        protected List<ZLEAVERS> item;

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
         * {@link ZLEAVERS }
         * 
         * 
         */
        public List<ZLEAVERS> getItem() {
            if (item == null) {
                item = new ArrayList<ZLEAVERS>();
            }
            return this.item;
        }

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
     *         &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZABSENCE" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    public static class PUUDUMINE {

        protected List<ZABSENCE> item;

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
         * {@link ZABSENCE }
         * 
         * 
         */
        public List<ZABSENCE> getItem() {
            if (item == null) {
                item = new ArrayList<ZABSENCE>();
            }
            return this.item;
        }

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
     *         &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZEMPLOEE1" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    public static class TOOTAJAD {

        protected List<ZEMPLOEE1> item;

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
         * {@link ZEMPLOEE1 }
         * 
         * 
         */
        public List<ZEMPLOEE1> getItem() {
            if (item == null) {
                item = new ArrayList<ZEMPLOEE1>();
            }
            return this.item;
        }

    }

}
