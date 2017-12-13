
package ee.hois.xroad.rtip.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Z_EESISTUMINEResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Z_EESISTUMINEResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
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
 *         &lt;element name="TOOTAJAD"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZEMPL_EES" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlType(name = "Z_EESISTUMINEResponseType", propOrder = {

})
public class ZEESISTUMINEResponseType {

    @XmlElement(name = "LAHKUJAD", required = true)
    protected ZEESISTUMINEResponseType.LAHKUJAD lahkujad;
    @XmlElement(name = "TOOTAJAD", required = true)
    protected ZEESISTUMINEResponseType.TOOTAJAD tootajad;

    /**
     * Gets the value of the lahkujad property.
     * 
     * @return
     *     possible object is
     *     {@link ZEESISTUMINEResponseType.LAHKUJAD }
     *     
     */
    public ZEESISTUMINEResponseType.LAHKUJAD getLAHKUJAD() {
        return lahkujad;
    }

    /**
     * Sets the value of the lahkujad property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZEESISTUMINEResponseType.LAHKUJAD }
     *     
     */
    public void setLAHKUJAD(ZEESISTUMINEResponseType.LAHKUJAD value) {
        this.lahkujad = value;
    }

    /**
     * Gets the value of the tootajad property.
     * 
     * @return
     *     possible object is
     *     {@link ZEESISTUMINEResponseType.TOOTAJAD }
     *     
     */
    public ZEESISTUMINEResponseType.TOOTAJAD getTOOTAJAD() {
        return tootajad;
    }

    /**
     * Sets the value of the tootajad property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZEESISTUMINEResponseType.TOOTAJAD }
     *     
     */
    public void setTOOTAJAD(ZEESISTUMINEResponseType.TOOTAJAD value) {
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
     *         &lt;element name="item" type="{http://rtk-v6.x-road.eu}ZEMPL_EES" maxOccurs="unbounded" minOccurs="0"/&gt;
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

        protected List<ZEMPLEES> item;

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
         * {@link ZEMPLEES }
         * 
         * 
         */
        public List<ZEMPLEES> getItem() {
            if (item == null) {
                item = new ArrayList<ZEMPLEES>();
            }
            return this.item;
        }

    }

}
