
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR404_isikResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR404_isikResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ttIsikud404"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="item" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="ttIsikud404.cIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cMPerenimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cMEesnimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSynniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSurmKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cTeoVoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cIsStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cKirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKMaak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKVkoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKAlgKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKVallaKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cEKAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideAdrRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideAdrMaak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideAdrVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideAdrAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideAdrVkoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideAdrTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideAdrNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideAdrMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideAdrKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideAdrIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideEpost" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ttIsikud404.cSideTelefon" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR404_isikResponseType", propOrder = {
    "ttIsikud404"
})
public class RR404IsikResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(required = true)
    protected RR404IsikResponseType.TtIsikud404 ttIsikud404;

    /**
     * Gets the value of the ttIsikud404 property.
     * 
     * @return
     *     possible object is
     *     {@link RR404IsikResponseType.TtIsikud404 }
     *     
     */
    public RR404IsikResponseType.TtIsikud404 getTtIsikud404() {
        return ttIsikud404;
    }

    /**
     * Sets the value of the ttIsikud404 property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR404IsikResponseType.TtIsikud404 }
     *     
     */
    public void setTtIsikud404(RR404IsikResponseType.TtIsikud404 value) {
        this.ttIsikud404 = value;
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
     *         &lt;element name="item" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="ttIsikud404.cIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cMPerenimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cMEesnimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSynniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSurmKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cTeoVoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cIsStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cKirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKMaak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKVkoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKAlgKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKVallaKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cEKAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideAdrRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideAdrMaak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideAdrVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideAdrAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideAdrVkoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideAdrTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideAdrNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideAdrMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideAdrKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideAdrIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideEpost" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ttIsikud404.cSideTelefon" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
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
    @XmlType(name = "", propOrder = {
        "item"
    })
    public static class TtIsikud404 {

        protected List<RR404IsikResponseType.TtIsikud404 .Item> item;

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
         * {@link RR404IsikResponseType.TtIsikud404 .Item }
         * 
         * 
         */
        public List<RR404IsikResponseType.TtIsikud404 .Item> getItem() {
            if (item == null) {
                item = new ArrayList<RR404IsikResponseType.TtIsikud404 .Item>();
            }
            return this.item;
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
         *         &lt;element name="ttIsikud404.cIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cMPerenimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cMEesnimed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cIsanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSynniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSurmKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cTeoVoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cIsStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cKirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKMaak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKVkoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKAlgKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKVallaKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cEKAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideAdrRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideAdrMaak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideAdrVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideAdrAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideAdrVkoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideAdrTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideAdrNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideAdrMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideAdrKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideAdrIndeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideEpost" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ttIsikud404.cSideTelefon" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "ttIsikud404CIsikukood",
            "ttIsikud404CPerenimi",
            "ttIsikud404CEesnimi",
            "ttIsikud404CMPerenimed",
            "ttIsikud404CMEesnimed",
            "ttIsikud404CRiikKood",
            "ttIsikud404CRiik",
            "ttIsikud404CIsanimi",
            "ttIsikud404CSugu",
            "ttIsikud404CSynniaeg",
            "ttIsikud404CSurmKpv",
            "ttIsikud404CTeoVoime",
            "ttIsikud404CIsStaatus",
            "ttIsikud404CKirjeStaatus",
            "ttIsikud404CEKRiik",
            "ttIsikud404CEKMaak",
            "ttIsikud404CEKVald",
            "ttIsikud404CEKAsula",
            "ttIsikud404CEKVkoht",
            "ttIsikud404CEKTanav",
            "ttIsikud404CEKNimi",
            "ttIsikud404CEKMaja",
            "ttIsikud404CEKKorter",
            "ttIsikud404CEKIndeks",
            "ttIsikud404CEKAlgKpv",
            "ttIsikud404CEKVallaKpv",
            "ttIsikud404CEKAadress",
            "ttIsikud404CSideAdrRiik",
            "ttIsikud404CSideAdrMaak",
            "ttIsikud404CSideAdrVald",
            "ttIsikud404CSideAdrAsula",
            "ttIsikud404CSideAdrVkoht",
            "ttIsikud404CSideAdrTanav",
            "ttIsikud404CSideAdrNimi",
            "ttIsikud404CSideAdrMaja",
            "ttIsikud404CSideAdrKorter",
            "ttIsikud404CSideAdrIndeks",
            "ttIsikud404CSideEpost",
            "ttIsikud404CSideTelefon"
        })
        public static class Item {

            @XmlElement(name = "ttIsikud404.cIsikukood", required = true)
            protected String ttIsikud404CIsikukood;
            @XmlElement(name = "ttIsikud404.cPerenimi", required = true)
            protected String ttIsikud404CPerenimi;
            @XmlElement(name = "ttIsikud404.cEesnimi", required = true)
            protected String ttIsikud404CEesnimi;
            @XmlElement(name = "ttIsikud404.cMPerenimed", required = true)
            protected String ttIsikud404CMPerenimed;
            @XmlElement(name = "ttIsikud404.cMEesnimed", required = true)
            protected String ttIsikud404CMEesnimed;
            @XmlElement(name = "ttIsikud404.cRiikKood", required = true)
            protected String ttIsikud404CRiikKood;
            @XmlElement(name = "ttIsikud404.cRiik", required = true)
            protected String ttIsikud404CRiik;
            @XmlElement(name = "ttIsikud404.cIsanimi", required = true)
            protected String ttIsikud404CIsanimi;
            @XmlElement(name = "ttIsikud404.cSugu", required = true)
            protected String ttIsikud404CSugu;
            @XmlElement(name = "ttIsikud404.cSynniaeg", required = true)
            protected String ttIsikud404CSynniaeg;
            @XmlElement(name = "ttIsikud404.cSurmKpv", required = true)
            protected String ttIsikud404CSurmKpv;
            @XmlElement(name = "ttIsikud404.cTeoVoime", required = true)
            protected String ttIsikud404CTeoVoime;
            @XmlElement(name = "ttIsikud404.cIsStaatus", required = true)
            protected String ttIsikud404CIsStaatus;
            @XmlElement(name = "ttIsikud404.cKirjeStaatus", required = true)
            protected String ttIsikud404CKirjeStaatus;
            @XmlElement(name = "ttIsikud404.cEKRiik", required = true)
            protected String ttIsikud404CEKRiik;
            @XmlElement(name = "ttIsikud404.cEKMaak", required = true)
            protected String ttIsikud404CEKMaak;
            @XmlElement(name = "ttIsikud404.cEKVald", required = true)
            protected String ttIsikud404CEKVald;
            @XmlElement(name = "ttIsikud404.cEKAsula", required = true)
            protected String ttIsikud404CEKAsula;
            @XmlElement(name = "ttIsikud404.cEKVkoht", required = true)
            protected String ttIsikud404CEKVkoht;
            @XmlElement(name = "ttIsikud404.cEKTanav", required = true)
            protected String ttIsikud404CEKTanav;
            @XmlElement(name = "ttIsikud404.cEKNimi", required = true)
            protected String ttIsikud404CEKNimi;
            @XmlElement(name = "ttIsikud404.cEKMaja", required = true)
            protected String ttIsikud404CEKMaja;
            @XmlElement(name = "ttIsikud404.cEKKorter", required = true)
            protected String ttIsikud404CEKKorter;
            @XmlElement(name = "ttIsikud404.cEKIndeks", required = true)
            protected String ttIsikud404CEKIndeks;
            @XmlElement(name = "ttIsikud404.cEKAlgKpv", required = true)
            protected String ttIsikud404CEKAlgKpv;
            @XmlElement(name = "ttIsikud404.cEKVallaKpv", required = true)
            protected String ttIsikud404CEKVallaKpv;
            @XmlElement(name = "ttIsikud404.cEKAadress", required = true)
            protected String ttIsikud404CEKAadress;
            @XmlElement(name = "ttIsikud404.cSideAdrRiik", required = true)
            protected String ttIsikud404CSideAdrRiik;
            @XmlElement(name = "ttIsikud404.cSideAdrMaak", required = true)
            protected String ttIsikud404CSideAdrMaak;
            @XmlElement(name = "ttIsikud404.cSideAdrVald", required = true)
            protected String ttIsikud404CSideAdrVald;
            @XmlElement(name = "ttIsikud404.cSideAdrAsula", required = true)
            protected String ttIsikud404CSideAdrAsula;
            @XmlElement(name = "ttIsikud404.cSideAdrVkoht", required = true)
            protected String ttIsikud404CSideAdrVkoht;
            @XmlElement(name = "ttIsikud404.cSideAdrTanav", required = true)
            protected String ttIsikud404CSideAdrTanav;
            @XmlElement(name = "ttIsikud404.cSideAdrNimi", required = true)
            protected String ttIsikud404CSideAdrNimi;
            @XmlElement(name = "ttIsikud404.cSideAdrMaja", required = true)
            protected String ttIsikud404CSideAdrMaja;
            @XmlElement(name = "ttIsikud404.cSideAdrKorter", required = true)
            protected String ttIsikud404CSideAdrKorter;
            @XmlElement(name = "ttIsikud404.cSideAdrIndeks", required = true)
            protected String ttIsikud404CSideAdrIndeks;
            @XmlElement(name = "ttIsikud404.cSideEpost", required = true)
            protected String ttIsikud404CSideEpost;
            @XmlElement(name = "ttIsikud404.cSideTelefon", required = true)
            protected String ttIsikud404CSideTelefon;

            /**
             * Gets the value of the ttIsikud404CIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CIsikukood() {
                return ttIsikud404CIsikukood;
            }

            /**
             * Sets the value of the ttIsikud404CIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CIsikukood(String value) {
                this.ttIsikud404CIsikukood = value;
            }

            /**
             * Gets the value of the ttIsikud404CPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CPerenimi() {
                return ttIsikud404CPerenimi;
            }

            /**
             * Sets the value of the ttIsikud404CPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CPerenimi(String value) {
                this.ttIsikud404CPerenimi = value;
            }

            /**
             * Gets the value of the ttIsikud404CEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEesnimi() {
                return ttIsikud404CEesnimi;
            }

            /**
             * Sets the value of the ttIsikud404CEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEesnimi(String value) {
                this.ttIsikud404CEesnimi = value;
            }

            /**
             * Gets the value of the ttIsikud404CMPerenimed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CMPerenimed() {
                return ttIsikud404CMPerenimed;
            }

            /**
             * Sets the value of the ttIsikud404CMPerenimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CMPerenimed(String value) {
                this.ttIsikud404CMPerenimed = value;
            }

            /**
             * Gets the value of the ttIsikud404CMEesnimed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CMEesnimed() {
                return ttIsikud404CMEesnimed;
            }

            /**
             * Sets the value of the ttIsikud404CMEesnimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CMEesnimed(String value) {
                this.ttIsikud404CMEesnimed = value;
            }

            /**
             * Gets the value of the ttIsikud404CRiikKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CRiikKood() {
                return ttIsikud404CRiikKood;
            }

            /**
             * Sets the value of the ttIsikud404CRiikKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CRiikKood(String value) {
                this.ttIsikud404CRiikKood = value;
            }

            /**
             * Gets the value of the ttIsikud404CRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CRiik() {
                return ttIsikud404CRiik;
            }

            /**
             * Sets the value of the ttIsikud404CRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CRiik(String value) {
                this.ttIsikud404CRiik = value;
            }

            /**
             * Gets the value of the ttIsikud404CIsanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CIsanimi() {
                return ttIsikud404CIsanimi;
            }

            /**
             * Sets the value of the ttIsikud404CIsanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CIsanimi(String value) {
                this.ttIsikud404CIsanimi = value;
            }

            /**
             * Gets the value of the ttIsikud404CSugu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSugu() {
                return ttIsikud404CSugu;
            }

            /**
             * Sets the value of the ttIsikud404CSugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSugu(String value) {
                this.ttIsikud404CSugu = value;
            }

            /**
             * Gets the value of the ttIsikud404CSynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSynniaeg() {
                return ttIsikud404CSynniaeg;
            }

            /**
             * Sets the value of the ttIsikud404CSynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSynniaeg(String value) {
                this.ttIsikud404CSynniaeg = value;
            }

            /**
             * Gets the value of the ttIsikud404CSurmKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSurmKpv() {
                return ttIsikud404CSurmKpv;
            }

            /**
             * Sets the value of the ttIsikud404CSurmKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSurmKpv(String value) {
                this.ttIsikud404CSurmKpv = value;
            }

            /**
             * Gets the value of the ttIsikud404CTeoVoime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CTeoVoime() {
                return ttIsikud404CTeoVoime;
            }

            /**
             * Sets the value of the ttIsikud404CTeoVoime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CTeoVoime(String value) {
                this.ttIsikud404CTeoVoime = value;
            }

            /**
             * Gets the value of the ttIsikud404CIsStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CIsStaatus() {
                return ttIsikud404CIsStaatus;
            }

            /**
             * Sets the value of the ttIsikud404CIsStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CIsStaatus(String value) {
                this.ttIsikud404CIsStaatus = value;
            }

            /**
             * Gets the value of the ttIsikud404CKirjeStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CKirjeStaatus() {
                return ttIsikud404CKirjeStaatus;
            }

            /**
             * Sets the value of the ttIsikud404CKirjeStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CKirjeStaatus(String value) {
                this.ttIsikud404CKirjeStaatus = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKRiik() {
                return ttIsikud404CEKRiik;
            }

            /**
             * Sets the value of the ttIsikud404CEKRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKRiik(String value) {
                this.ttIsikud404CEKRiik = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKMaak property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKMaak() {
                return ttIsikud404CEKMaak;
            }

            /**
             * Sets the value of the ttIsikud404CEKMaak property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKMaak(String value) {
                this.ttIsikud404CEKMaak = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKVald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKVald() {
                return ttIsikud404CEKVald;
            }

            /**
             * Sets the value of the ttIsikud404CEKVald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKVald(String value) {
                this.ttIsikud404CEKVald = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKAsula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKAsula() {
                return ttIsikud404CEKAsula;
            }

            /**
             * Sets the value of the ttIsikud404CEKAsula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKAsula(String value) {
                this.ttIsikud404CEKAsula = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKVkoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKVkoht() {
                return ttIsikud404CEKVkoht;
            }

            /**
             * Sets the value of the ttIsikud404CEKVkoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKVkoht(String value) {
                this.ttIsikud404CEKVkoht = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKTanav() {
                return ttIsikud404CEKTanav;
            }

            /**
             * Sets the value of the ttIsikud404CEKTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKTanav(String value) {
                this.ttIsikud404CEKTanav = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKNimi() {
                return ttIsikud404CEKNimi;
            }

            /**
             * Sets the value of the ttIsikud404CEKNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKNimi(String value) {
                this.ttIsikud404CEKNimi = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKMaja() {
                return ttIsikud404CEKMaja;
            }

            /**
             * Sets the value of the ttIsikud404CEKMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKMaja(String value) {
                this.ttIsikud404CEKMaja = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKKorter() {
                return ttIsikud404CEKKorter;
            }

            /**
             * Sets the value of the ttIsikud404CEKKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKKorter(String value) {
                this.ttIsikud404CEKKorter = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKIndeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKIndeks() {
                return ttIsikud404CEKIndeks;
            }

            /**
             * Sets the value of the ttIsikud404CEKIndeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKIndeks(String value) {
                this.ttIsikud404CEKIndeks = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKAlgKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKAlgKpv() {
                return ttIsikud404CEKAlgKpv;
            }

            /**
             * Sets the value of the ttIsikud404CEKAlgKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKAlgKpv(String value) {
                this.ttIsikud404CEKAlgKpv = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKVallaKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKVallaKpv() {
                return ttIsikud404CEKVallaKpv;
            }

            /**
             * Sets the value of the ttIsikud404CEKVallaKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKVallaKpv(String value) {
                this.ttIsikud404CEKVallaKpv = value;
            }

            /**
             * Gets the value of the ttIsikud404CEKAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CEKAadress() {
                return ttIsikud404CEKAadress;
            }

            /**
             * Sets the value of the ttIsikud404CEKAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CEKAadress(String value) {
                this.ttIsikud404CEKAadress = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideAdrRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideAdrRiik() {
                return ttIsikud404CSideAdrRiik;
            }

            /**
             * Sets the value of the ttIsikud404CSideAdrRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideAdrRiik(String value) {
                this.ttIsikud404CSideAdrRiik = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideAdrMaak property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideAdrMaak() {
                return ttIsikud404CSideAdrMaak;
            }

            /**
             * Sets the value of the ttIsikud404CSideAdrMaak property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideAdrMaak(String value) {
                this.ttIsikud404CSideAdrMaak = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideAdrVald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideAdrVald() {
                return ttIsikud404CSideAdrVald;
            }

            /**
             * Sets the value of the ttIsikud404CSideAdrVald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideAdrVald(String value) {
                this.ttIsikud404CSideAdrVald = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideAdrAsula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideAdrAsula() {
                return ttIsikud404CSideAdrAsula;
            }

            /**
             * Sets the value of the ttIsikud404CSideAdrAsula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideAdrAsula(String value) {
                this.ttIsikud404CSideAdrAsula = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideAdrVkoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideAdrVkoht() {
                return ttIsikud404CSideAdrVkoht;
            }

            /**
             * Sets the value of the ttIsikud404CSideAdrVkoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideAdrVkoht(String value) {
                this.ttIsikud404CSideAdrVkoht = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideAdrTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideAdrTanav() {
                return ttIsikud404CSideAdrTanav;
            }

            /**
             * Sets the value of the ttIsikud404CSideAdrTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideAdrTanav(String value) {
                this.ttIsikud404CSideAdrTanav = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideAdrNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideAdrNimi() {
                return ttIsikud404CSideAdrNimi;
            }

            /**
             * Sets the value of the ttIsikud404CSideAdrNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideAdrNimi(String value) {
                this.ttIsikud404CSideAdrNimi = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideAdrMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideAdrMaja() {
                return ttIsikud404CSideAdrMaja;
            }

            /**
             * Sets the value of the ttIsikud404CSideAdrMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideAdrMaja(String value) {
                this.ttIsikud404CSideAdrMaja = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideAdrKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideAdrKorter() {
                return ttIsikud404CSideAdrKorter;
            }

            /**
             * Sets the value of the ttIsikud404CSideAdrKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideAdrKorter(String value) {
                this.ttIsikud404CSideAdrKorter = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideAdrIndeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideAdrIndeks() {
                return ttIsikud404CSideAdrIndeks;
            }

            /**
             * Sets the value of the ttIsikud404CSideAdrIndeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideAdrIndeks(String value) {
                this.ttIsikud404CSideAdrIndeks = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideEpost property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideEpost() {
                return ttIsikud404CSideEpost;
            }

            /**
             * Sets the value of the ttIsikud404CSideEpost property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideEpost(String value) {
                this.ttIsikud404CSideEpost = value;
            }

            /**
             * Gets the value of the ttIsikud404CSideTelefon property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTtIsikud404CSideTelefon() {
                return ttIsikud404CSideTelefon;
            }

            /**
             * Sets the value of the ttIsikud404CSideTelefon property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTtIsikud404CSideTelefon(String value) {
                this.ttIsikud404CSideTelefon = value;
            }

        }

    }

}
