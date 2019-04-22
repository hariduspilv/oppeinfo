
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRAddressIdResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRAddressIdResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Aadress" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ADS_OID" type="{http://rr.x-road.eu/producer}AdsOidCode"/&gt;
 *                   &lt;element name="Riik" type="{http://rr.x-road.eu/producer}AddrComp"/&gt;
 *                   &lt;element name="Maakond" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
 *                   &lt;element name="LinnVald" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
 *                   &lt;element name="AsulaLinnaosa" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
 *                   &lt;element name="Vaikekoht" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
 *                   &lt;element name="TanavTalu" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
 *                   &lt;element name="Nimi" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
 *                   &lt;element name="Maja" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
 *                   &lt;element name="Korter" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
 *                   &lt;element name="PostiIndeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "RRAddressIdResponseType", propOrder = {
    "aadress"
})
public class RRAddressIdResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Aadress")
    protected List<RRAddressIdResponseType.Aadress> aadress;

    /**
     * Gets the value of the aadress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aadress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAadress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RRAddressIdResponseType.Aadress }
     * 
     * 
     */
    public List<RRAddressIdResponseType.Aadress> getAadress() {
        if (aadress == null) {
            aadress = new ArrayList<RRAddressIdResponseType.Aadress>();
        }
        return this.aadress;
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
     *         &lt;element name="ADS_OID" type="{http://rr.x-road.eu/producer}AdsOidCode"/&gt;
     *         &lt;element name="Riik" type="{http://rr.x-road.eu/producer}AddrComp"/&gt;
     *         &lt;element name="Maakond" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
     *         &lt;element name="LinnVald" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
     *         &lt;element name="AsulaLinnaosa" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
     *         &lt;element name="Vaikekoht" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
     *         &lt;element name="TanavTalu" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
     *         &lt;element name="Nimi" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
     *         &lt;element name="Maja" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
     *         &lt;element name="Korter" type="{http://rr.x-road.eu/producer}AddrComp" minOccurs="0"/&gt;
     *         &lt;element name="PostiIndeks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "adsoid",
        "riik",
        "maakond",
        "linnVald",
        "asulaLinnaosa",
        "vaikekoht",
        "tanavTalu",
        "nimi",
        "maja",
        "korter",
        "postiIndeks",
        "aadressTekstina"
    })
    public static class Aadress {

        @XmlElement(name = "ADS_OID", required = true)
        protected String adsoid;
        @XmlElement(name = "Riik", required = true)
        protected AddrComp riik;
        @XmlElement(name = "Maakond")
        protected AddrComp maakond;
        @XmlElement(name = "LinnVald")
        protected AddrComp linnVald;
        @XmlElement(name = "AsulaLinnaosa")
        protected AddrComp asulaLinnaosa;
        @XmlElement(name = "Vaikekoht")
        protected AddrComp vaikekoht;
        @XmlElement(name = "TanavTalu")
        protected AddrComp tanavTalu;
        @XmlElement(name = "Nimi")
        protected AddrComp nimi;
        @XmlElement(name = "Maja")
        protected AddrComp maja;
        @XmlElement(name = "Korter")
        protected AddrComp korter;
        @XmlElement(name = "PostiIndeks")
        protected String postiIndeks;
        @XmlElement(name = "AadressTekstina")
        protected String aadressTekstina;

        /**
         * Gets the value of the adsoid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getADSOID() {
            return adsoid;
        }

        /**
         * Sets the value of the adsoid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setADSOID(String value) {
            this.adsoid = value;
        }

        /**
         * Gets the value of the riik property.
         * 
         * @return
         *     possible object is
         *     {@link AddrComp }
         *     
         */
        public AddrComp getRiik() {
            return riik;
        }

        /**
         * Sets the value of the riik property.
         * 
         * @param value
         *     allowed object is
         *     {@link AddrComp }
         *     
         */
        public void setRiik(AddrComp value) {
            this.riik = value;
        }

        /**
         * Gets the value of the maakond property.
         * 
         * @return
         *     possible object is
         *     {@link AddrComp }
         *     
         */
        public AddrComp getMaakond() {
            return maakond;
        }

        /**
         * Sets the value of the maakond property.
         * 
         * @param value
         *     allowed object is
         *     {@link AddrComp }
         *     
         */
        public void setMaakond(AddrComp value) {
            this.maakond = value;
        }

        /**
         * Gets the value of the linnVald property.
         * 
         * @return
         *     possible object is
         *     {@link AddrComp }
         *     
         */
        public AddrComp getLinnVald() {
            return linnVald;
        }

        /**
         * Sets the value of the linnVald property.
         * 
         * @param value
         *     allowed object is
         *     {@link AddrComp }
         *     
         */
        public void setLinnVald(AddrComp value) {
            this.linnVald = value;
        }

        /**
         * Gets the value of the asulaLinnaosa property.
         * 
         * @return
         *     possible object is
         *     {@link AddrComp }
         *     
         */
        public AddrComp getAsulaLinnaosa() {
            return asulaLinnaosa;
        }

        /**
         * Sets the value of the asulaLinnaosa property.
         * 
         * @param value
         *     allowed object is
         *     {@link AddrComp }
         *     
         */
        public void setAsulaLinnaosa(AddrComp value) {
            this.asulaLinnaosa = value;
        }

        /**
         * Gets the value of the vaikekoht property.
         * 
         * @return
         *     possible object is
         *     {@link AddrComp }
         *     
         */
        public AddrComp getVaikekoht() {
            return vaikekoht;
        }

        /**
         * Sets the value of the vaikekoht property.
         * 
         * @param value
         *     allowed object is
         *     {@link AddrComp }
         *     
         */
        public void setVaikekoht(AddrComp value) {
            this.vaikekoht = value;
        }

        /**
         * Gets the value of the tanavTalu property.
         * 
         * @return
         *     possible object is
         *     {@link AddrComp }
         *     
         */
        public AddrComp getTanavTalu() {
            return tanavTalu;
        }

        /**
         * Sets the value of the tanavTalu property.
         * 
         * @param value
         *     allowed object is
         *     {@link AddrComp }
         *     
         */
        public void setTanavTalu(AddrComp value) {
            this.tanavTalu = value;
        }

        /**
         * Gets the value of the nimi property.
         * 
         * @return
         *     possible object is
         *     {@link AddrComp }
         *     
         */
        public AddrComp getNimi() {
            return nimi;
        }

        /**
         * Sets the value of the nimi property.
         * 
         * @param value
         *     allowed object is
         *     {@link AddrComp }
         *     
         */
        public void setNimi(AddrComp value) {
            this.nimi = value;
        }

        /**
         * Gets the value of the maja property.
         * 
         * @return
         *     possible object is
         *     {@link AddrComp }
         *     
         */
        public AddrComp getMaja() {
            return maja;
        }

        /**
         * Sets the value of the maja property.
         * 
         * @param value
         *     allowed object is
         *     {@link AddrComp }
         *     
         */
        public void setMaja(AddrComp value) {
            this.maja = value;
        }

        /**
         * Gets the value of the korter property.
         * 
         * @return
         *     possible object is
         *     {@link AddrComp }
         *     
         */
        public AddrComp getKorter() {
            return korter;
        }

        /**
         * Sets the value of the korter property.
         * 
         * @param value
         *     allowed object is
         *     {@link AddrComp }
         *     
         */
        public void setKorter(AddrComp value) {
            this.korter = value;
        }

        /**
         * Gets the value of the postiIndeks property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPostiIndeks() {
            return postiIndeks;
        }

        /**
         * Sets the value of the postiIndeks property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPostiIndeks(String value) {
            this.postiIndeks = value;
        }

        /**
         * Gets the value of the aadressTekstina property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAadressTekstina() {
            return aadressTekstina;
        }

        /**
         * Sets the value of the aadressTekstina property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAadressTekstina(String value) {
            this.aadressTekstina = value;
        }

    }

}
