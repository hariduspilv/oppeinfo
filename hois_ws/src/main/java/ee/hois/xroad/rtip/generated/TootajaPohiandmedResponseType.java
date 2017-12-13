
package ee.hois.xroad.rtip.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tootajaPohiandmedResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tootajaPohiandmedResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="RETURN"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" type="{http://rtk-v6.x-road.eu}BAPIRET2A" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="TOOTAJA_POHIANDMED" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZHR_PPA_TOOTAJA_POHIANDMED" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "tootajaPohiandmedResponseType", propOrder = {

})
public class TootajaPohiandmedResponseType {

    @XmlElement(name = "RETURN", required = true)
    protected TootajaPohiandmedResponseType.RETURN _return;
    @XmlElement(name = "TOOTAJA_POHIANDMED")
    protected TootajaPohiandmedResponseType.TOOTAJAPOHIANDMED tootajapohiandmed;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link TootajaPohiandmedResponseType.RETURN }
     *     
     */
    public TootajaPohiandmedResponseType.RETURN getRETURN() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link TootajaPohiandmedResponseType.RETURN }
     *     
     */
    public void setRETURN(TootajaPohiandmedResponseType.RETURN value) {
        this._return = value;
    }

    /**
     * Gets the value of the tootajapohiandmed property.
     * 
     * @return
     *     possible object is
     *     {@link TootajaPohiandmedResponseType.TOOTAJAPOHIANDMED }
     *     
     */
    public TootajaPohiandmedResponseType.TOOTAJAPOHIANDMED getTOOTAJAPOHIANDMED() {
        return tootajapohiandmed;
    }

    /**
     * Sets the value of the tootajapohiandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link TootajaPohiandmedResponseType.TOOTAJAPOHIANDMED }
     *     
     */
    public void setTOOTAJAPOHIANDMED(TootajaPohiandmedResponseType.TOOTAJAPOHIANDMED value) {
        this.tootajapohiandmed = value;
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
     *         &lt;element name="item" type="{http://rtk-v6.x-road.eu}BAPIRET2A" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    public static class RETURN {

        protected List<BAPIRET2A> item;

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
         * {@link BAPIRET2A }
         * 
         * 
         */
        public List<BAPIRET2A> getItem() {
            if (item == null) {
                item = new ArrayList<BAPIRET2A>();
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
     *         &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZHR_PPA_TOOTAJA_POHIANDMED" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    public static class TOOTAJAPOHIANDMED {

        protected List<ZHRPPATOOTAJAPOHIANDMED> item;

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
         * {@link ZHRPPATOOTAJAPOHIANDMED }
         * 
         * 
         */
        public List<ZHRPPATOOTAJAPOHIANDMED> getItem() {
            if (item == null) {
                item = new ArrayList<ZHRPPATOOTAJAPOHIANDMED>();
            }
            return this.item;
        }

    }

}
