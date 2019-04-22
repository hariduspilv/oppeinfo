
package ee.hois.xroad.rahvastikuregister.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR415ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR415ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikud" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Elamisoigus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ElukohaAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ElukohaLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ElukohaOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Vallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Kylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Kyla" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Vaikekohtkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Tanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Tanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Nimikd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ADS_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ADS_ADOB_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineElukohaAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineElukohaLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineMaakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineVallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineKylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineKyla" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineVaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineVaikekohtkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineTanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineTanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineNimikd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineMajanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineKorternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineADS_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.EndineADS_ADOB_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR415ResponseType", propOrder = {
    "isikud"
})
public class RR415ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Isikud")
    protected RR415ResponseType.Isikud isikud;

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR415ResponseType.Isikud }
     *     
     */
    public RR415ResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR415ResponseType.Isikud }
     *     
     */
    public void setIsikud(RR415ResponseType.Isikud value) {
        this.isikud = value;
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
     *         &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Elamisoigus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ElukohaAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ElukohaLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ElukohaOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Vallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Kylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Kyla" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Vaikekohtkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Tanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Tanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Nimikd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ADS_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ADS_ADOB_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineElukohaAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineElukohaLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineMaakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineVallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineKylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineKyla" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineVaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineVaikekohtkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineTanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineTanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineNimikd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineMajanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineKorternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineADS_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.EndineADS_ADOB_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "isik"
    })
    public static class Isikud {

        @XmlElement(name = "Isik")
        protected List<RR415ResponseType.Isikud.Isik> isik;

        /**
         * Gets the value of the isik property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the isik property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIsik().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR415ResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR415ResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR415ResponseType.Isikud.Isik>();
            }
            return this.isik;
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
         *         &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.cPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.cEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.cRiikKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.cRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Elamisoigus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ElukohaAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ElukohaLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ElukohaOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Maakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Maakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Vallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Vald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Kylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Kyla" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Vaikekohtkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Vaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Tanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Tanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Nimikd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ADS_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ADS_ADOB_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineElukohaAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineElukohaLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineMaakondkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineVallakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineKylakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineKyla" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineVaikekoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineVaikekohtkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineTanavakd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineTanavanm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineNimikd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineMajanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineKorternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineADS_Koodaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.EndineADS_ADOB_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikIsikukood",
            "isikCPerenimi",
            "isikCEesnimi",
            "isikSynniaeg",
            "isikCRiikKood",
            "isikCRiik",
            "isikTeovoime",
            "isikElamisoigus",
            "isikElukohaAlgus",
            "isikElukohaLopp",
            "isikElukohaOlek",
            "isikRiik",
            "isikMaakondkd",
            "isikMaakond",
            "isikVallakd",
            "isikVald",
            "isikKylakd",
            "isikKyla",
            "isikVaikekohtkd",
            "isikVaikekoht",
            "isikTanavakd",
            "isikTanavanm",
            "isikNimikd",
            "isikNimi",
            "isikMajanr",
            "isikKorternr",
            "isikADSADRID",
            "isikADSOID",
            "isikADSKoodaadress",
            "isikADSADOBOID",
            "isikEndineElukohaAlgus",
            "isikEndineElukohaLopp",
            "isikEndineRiik",
            "isikEndineMaakondkd",
            "isikEndineMaakond",
            "isikEndineVallakd",
            "isikEndineVald",
            "isikEndineKylakd",
            "isikEndineKyla",
            "isikEndineVaikekoht",
            "isikEndineVaikekohtkd",
            "isikEndineTanavakd",
            "isikEndineTanavanm",
            "isikEndineNimikd",
            "isikEndineNimi",
            "isikEndineMajanr",
            "isikEndineKorternr",
            "isikEndineADSADRID",
            "isikEndineADSOID",
            "isikEndineADSKoodaadress",
            "isikEndineADSADOBOID"
        })
        public static class Isik {

            @XmlElement(name = "Isik.Isikukood", required = true)
            protected String isikIsikukood;
            @XmlElement(name = "Isik.cPerenimi", required = true)
            protected String isikCPerenimi;
            @XmlElement(name = "Isik.cEesnimi", required = true)
            protected String isikCEesnimi;
            @XmlElement(name = "Isik.Synniaeg", required = true)
            protected String isikSynniaeg;
            @XmlElement(name = "Isik.cRiikKood", required = true)
            protected String isikCRiikKood;
            @XmlElement(name = "Isik.cRiik", required = true)
            protected String isikCRiik;
            @XmlElement(name = "Isik.Teovoime", required = true)
            protected String isikTeovoime;
            @XmlElement(name = "Isik.Elamisoigus", required = true)
            protected String isikElamisoigus;
            @XmlElement(name = "Isik.ElukohaAlgus", required = true)
            protected String isikElukohaAlgus;
            @XmlElement(name = "Isik.ElukohaLopp", required = true)
            protected String isikElukohaLopp;
            @XmlElement(name = "Isik.ElukohaOlek", required = true)
            protected String isikElukohaOlek;
            @XmlElement(name = "Isik.Riik", required = true)
            protected String isikRiik;
            @XmlElement(name = "Isik.Maakondkd", required = true)
            protected String isikMaakondkd;
            @XmlElement(name = "Isik.Maakond", required = true)
            protected String isikMaakond;
            @XmlElement(name = "Isik.Vallakd", required = true)
            protected String isikVallakd;
            @XmlElement(name = "Isik.Vald", required = true)
            protected String isikVald;
            @XmlElement(name = "Isik.Kylakd", required = true)
            protected String isikKylakd;
            @XmlElement(name = "Isik.Kyla", required = true)
            protected String isikKyla;
            @XmlElement(name = "Isik.Vaikekohtkd", required = true)
            protected String isikVaikekohtkd;
            @XmlElement(name = "Isik.Vaikekoht", required = true)
            protected String isikVaikekoht;
            @XmlElement(name = "Isik.Tanavakd", required = true)
            protected String isikTanavakd;
            @XmlElement(name = "Isik.Tanavanm", required = true)
            protected String isikTanavanm;
            @XmlElement(name = "Isik.Nimikd", required = true)
            protected String isikNimikd;
            @XmlElement(name = "Isik.Nimi", required = true)
            protected String isikNimi;
            @XmlElement(name = "Isik.Majanr", required = true)
            protected String isikMajanr;
            @XmlElement(name = "Isik.Korternr", required = true)
            protected String isikKorternr;
            @XmlElement(name = "Isik.ADS_ADR_ID", required = true)
            protected String isikADSADRID;
            @XmlElement(name = "Isik.ADS_OID", required = true)
            protected String isikADSOID;
            @XmlElement(name = "Isik.ADS_Koodaadress", required = true)
            protected String isikADSKoodaadress;
            @XmlElement(name = "Isik.ADS_ADOB_OID", required = true)
            protected String isikADSADOBOID;
            @XmlElement(name = "Isik.EndineElukohaAlgus", required = true)
            protected String isikEndineElukohaAlgus;
            @XmlElement(name = "Isik.EndineElukohaLopp", required = true)
            protected String isikEndineElukohaLopp;
            @XmlElement(name = "Isik.EndineRiik", required = true)
            protected String isikEndineRiik;
            @XmlElement(name = "Isik.EndineMaakondkd", required = true)
            protected String isikEndineMaakondkd;
            @XmlElement(name = "Isik.EndineMaakond", required = true)
            protected String isikEndineMaakond;
            @XmlElement(name = "Isik.EndineVallakd", required = true)
            protected String isikEndineVallakd;
            @XmlElement(name = "Isik.EndineVald", required = true)
            protected String isikEndineVald;
            @XmlElement(name = "Isik.EndineKylakd", required = true)
            protected String isikEndineKylakd;
            @XmlElement(name = "Isik.EndineKyla", required = true)
            protected String isikEndineKyla;
            @XmlElement(name = "Isik.EndineVaikekoht", required = true)
            protected String isikEndineVaikekoht;
            @XmlElement(name = "Isik.EndineVaikekohtkd", required = true)
            protected String isikEndineVaikekohtkd;
            @XmlElement(name = "Isik.EndineTanavakd", required = true)
            protected String isikEndineTanavakd;
            @XmlElement(name = "Isik.EndineTanavanm", required = true)
            protected String isikEndineTanavanm;
            @XmlElement(name = "Isik.EndineNimikd", required = true)
            protected String isikEndineNimikd;
            @XmlElement(name = "Isik.EndineNimi", required = true)
            protected String isikEndineNimi;
            @XmlElement(name = "Isik.EndineMajanr", required = true)
            protected String isikEndineMajanr;
            @XmlElement(name = "Isik.EndineKorternr", required = true)
            protected String isikEndineKorternr;
            @XmlElement(name = "Isik.EndineADS_ADR_ID", required = true)
            protected String isikEndineADSADRID;
            @XmlElement(name = "Isik.EndineADS_OID", required = true)
            protected String isikEndineADSOID;
            @XmlElement(name = "Isik.EndineADS_Koodaadress", required = true)
            protected String isikEndineADSKoodaadress;
            @XmlElement(name = "Isik.EndineADS_ADOB_OID", required = true)
            protected String isikEndineADSADOBOID;

            /**
             * Gets the value of the isikIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikukood() {
                return isikIsikukood;
            }

            /**
             * Sets the value of the isikIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikukood(String value) {
                this.isikIsikukood = value;
            }

            /**
             * Gets the value of the isikCPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikCPerenimi() {
                return isikCPerenimi;
            }

            /**
             * Sets the value of the isikCPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikCPerenimi(String value) {
                this.isikCPerenimi = value;
            }

            /**
             * Gets the value of the isikCEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikCEesnimi() {
                return isikCEesnimi;
            }

            /**
             * Sets the value of the isikCEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikCEesnimi(String value) {
                this.isikCEesnimi = value;
            }

            /**
             * Gets the value of the isikSynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSynniaeg() {
                return isikSynniaeg;
            }

            /**
             * Sets the value of the isikSynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSynniaeg(String value) {
                this.isikSynniaeg = value;
            }

            /**
             * Gets the value of the isikCRiikKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikCRiikKood() {
                return isikCRiikKood;
            }

            /**
             * Sets the value of the isikCRiikKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikCRiikKood(String value) {
                this.isikCRiikKood = value;
            }

            /**
             * Gets the value of the isikCRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikCRiik() {
                return isikCRiik;
            }

            /**
             * Sets the value of the isikCRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikCRiik(String value) {
                this.isikCRiik = value;
            }

            /**
             * Gets the value of the isikTeovoime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTeovoime() {
                return isikTeovoime;
            }

            /**
             * Sets the value of the isikTeovoime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTeovoime(String value) {
                this.isikTeovoime = value;
            }

            /**
             * Gets the value of the isikElamisoigus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElamisoigus() {
                return isikElamisoigus;
            }

            /**
             * Sets the value of the isikElamisoigus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElamisoigus(String value) {
                this.isikElamisoigus = value;
            }

            /**
             * Gets the value of the isikElukohaAlgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElukohaAlgus() {
                return isikElukohaAlgus;
            }

            /**
             * Sets the value of the isikElukohaAlgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElukohaAlgus(String value) {
                this.isikElukohaAlgus = value;
            }

            /**
             * Gets the value of the isikElukohaLopp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElukohaLopp() {
                return isikElukohaLopp;
            }

            /**
             * Sets the value of the isikElukohaLopp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElukohaLopp(String value) {
                this.isikElukohaLopp = value;
            }

            /**
             * Gets the value of the isikElukohaOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElukohaOlek() {
                return isikElukohaOlek;
            }

            /**
             * Sets the value of the isikElukohaOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElukohaOlek(String value) {
                this.isikElukohaOlek = value;
            }

            /**
             * Gets the value of the isikRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikRiik() {
                return isikRiik;
            }

            /**
             * Sets the value of the isikRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikRiik(String value) {
                this.isikRiik = value;
            }

            /**
             * Gets the value of the isikMaakondkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMaakondkd() {
                return isikMaakondkd;
            }

            /**
             * Sets the value of the isikMaakondkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMaakondkd(String value) {
                this.isikMaakondkd = value;
            }

            /**
             * Gets the value of the isikMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMaakond() {
                return isikMaakond;
            }

            /**
             * Sets the value of the isikMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMaakond(String value) {
                this.isikMaakond = value;
            }

            /**
             * Gets the value of the isikVallakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVallakd() {
                return isikVallakd;
            }

            /**
             * Sets the value of the isikVallakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVallakd(String value) {
                this.isikVallakd = value;
            }

            /**
             * Gets the value of the isikVald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVald() {
                return isikVald;
            }

            /**
             * Sets the value of the isikVald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVald(String value) {
                this.isikVald = value;
            }

            /**
             * Gets the value of the isikKylakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKylakd() {
                return isikKylakd;
            }

            /**
             * Sets the value of the isikKylakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKylakd(String value) {
                this.isikKylakd = value;
            }

            /**
             * Gets the value of the isikKyla property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKyla() {
                return isikKyla;
            }

            /**
             * Sets the value of the isikKyla property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKyla(String value) {
                this.isikKyla = value;
            }

            /**
             * Gets the value of the isikVaikekohtkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVaikekohtkd() {
                return isikVaikekohtkd;
            }

            /**
             * Sets the value of the isikVaikekohtkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVaikekohtkd(String value) {
                this.isikVaikekohtkd = value;
            }

            /**
             * Gets the value of the isikVaikekoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVaikekoht() {
                return isikVaikekoht;
            }

            /**
             * Sets the value of the isikVaikekoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVaikekoht(String value) {
                this.isikVaikekoht = value;
            }

            /**
             * Gets the value of the isikTanavakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTanavakd() {
                return isikTanavakd;
            }

            /**
             * Sets the value of the isikTanavakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTanavakd(String value) {
                this.isikTanavakd = value;
            }

            /**
             * Gets the value of the isikTanavanm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTanavanm() {
                return isikTanavanm;
            }

            /**
             * Sets the value of the isikTanavanm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTanavanm(String value) {
                this.isikTanavanm = value;
            }

            /**
             * Gets the value of the isikNimikd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikNimikd() {
                return isikNimikd;
            }

            /**
             * Sets the value of the isikNimikd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikNimikd(String value) {
                this.isikNimikd = value;
            }

            /**
             * Gets the value of the isikNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikNimi() {
                return isikNimi;
            }

            /**
             * Sets the value of the isikNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikNimi(String value) {
                this.isikNimi = value;
            }

            /**
             * Gets the value of the isikMajanr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMajanr() {
                return isikMajanr;
            }

            /**
             * Sets the value of the isikMajanr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMajanr(String value) {
                this.isikMajanr = value;
            }

            /**
             * Gets the value of the isikKorternr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKorternr() {
                return isikKorternr;
            }

            /**
             * Sets the value of the isikKorternr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKorternr(String value) {
                this.isikKorternr = value;
            }

            /**
             * Gets the value of the isikADSADRID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikADSADRID() {
                return isikADSADRID;
            }

            /**
             * Sets the value of the isikADSADRID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikADSADRID(String value) {
                this.isikADSADRID = value;
            }

            /**
             * Gets the value of the isikADSOID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikADSOID() {
                return isikADSOID;
            }

            /**
             * Sets the value of the isikADSOID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikADSOID(String value) {
                this.isikADSOID = value;
            }

            /**
             * Gets the value of the isikADSKoodaadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikADSKoodaadress() {
                return isikADSKoodaadress;
            }

            /**
             * Sets the value of the isikADSKoodaadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikADSKoodaadress(String value) {
                this.isikADSKoodaadress = value;
            }

            /**
             * Gets the value of the isikADSADOBOID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikADSADOBOID() {
                return isikADSADOBOID;
            }

            /**
             * Sets the value of the isikADSADOBOID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikADSADOBOID(String value) {
                this.isikADSADOBOID = value;
            }

            /**
             * Gets the value of the isikEndineElukohaAlgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineElukohaAlgus() {
                return isikEndineElukohaAlgus;
            }

            /**
             * Sets the value of the isikEndineElukohaAlgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineElukohaAlgus(String value) {
                this.isikEndineElukohaAlgus = value;
            }

            /**
             * Gets the value of the isikEndineElukohaLopp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineElukohaLopp() {
                return isikEndineElukohaLopp;
            }

            /**
             * Sets the value of the isikEndineElukohaLopp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineElukohaLopp(String value) {
                this.isikEndineElukohaLopp = value;
            }

            /**
             * Gets the value of the isikEndineRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineRiik() {
                return isikEndineRiik;
            }

            /**
             * Sets the value of the isikEndineRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineRiik(String value) {
                this.isikEndineRiik = value;
            }

            /**
             * Gets the value of the isikEndineMaakondkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineMaakondkd() {
                return isikEndineMaakondkd;
            }

            /**
             * Sets the value of the isikEndineMaakondkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineMaakondkd(String value) {
                this.isikEndineMaakondkd = value;
            }

            /**
             * Gets the value of the isikEndineMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineMaakond() {
                return isikEndineMaakond;
            }

            /**
             * Sets the value of the isikEndineMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineMaakond(String value) {
                this.isikEndineMaakond = value;
            }

            /**
             * Gets the value of the isikEndineVallakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineVallakd() {
                return isikEndineVallakd;
            }

            /**
             * Sets the value of the isikEndineVallakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineVallakd(String value) {
                this.isikEndineVallakd = value;
            }

            /**
             * Gets the value of the isikEndineVald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineVald() {
                return isikEndineVald;
            }

            /**
             * Sets the value of the isikEndineVald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineVald(String value) {
                this.isikEndineVald = value;
            }

            /**
             * Gets the value of the isikEndineKylakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineKylakd() {
                return isikEndineKylakd;
            }

            /**
             * Sets the value of the isikEndineKylakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineKylakd(String value) {
                this.isikEndineKylakd = value;
            }

            /**
             * Gets the value of the isikEndineKyla property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineKyla() {
                return isikEndineKyla;
            }

            /**
             * Sets the value of the isikEndineKyla property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineKyla(String value) {
                this.isikEndineKyla = value;
            }

            /**
             * Gets the value of the isikEndineVaikekoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineVaikekoht() {
                return isikEndineVaikekoht;
            }

            /**
             * Sets the value of the isikEndineVaikekoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineVaikekoht(String value) {
                this.isikEndineVaikekoht = value;
            }

            /**
             * Gets the value of the isikEndineVaikekohtkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineVaikekohtkd() {
                return isikEndineVaikekohtkd;
            }

            /**
             * Sets the value of the isikEndineVaikekohtkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineVaikekohtkd(String value) {
                this.isikEndineVaikekohtkd = value;
            }

            /**
             * Gets the value of the isikEndineTanavakd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineTanavakd() {
                return isikEndineTanavakd;
            }

            /**
             * Sets the value of the isikEndineTanavakd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineTanavakd(String value) {
                this.isikEndineTanavakd = value;
            }

            /**
             * Gets the value of the isikEndineTanavanm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineTanavanm() {
                return isikEndineTanavanm;
            }

            /**
             * Sets the value of the isikEndineTanavanm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineTanavanm(String value) {
                this.isikEndineTanavanm = value;
            }

            /**
             * Gets the value of the isikEndineNimikd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineNimikd() {
                return isikEndineNimikd;
            }

            /**
             * Sets the value of the isikEndineNimikd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineNimikd(String value) {
                this.isikEndineNimikd = value;
            }

            /**
             * Gets the value of the isikEndineNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineNimi() {
                return isikEndineNimi;
            }

            /**
             * Sets the value of the isikEndineNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineNimi(String value) {
                this.isikEndineNimi = value;
            }

            /**
             * Gets the value of the isikEndineMajanr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineMajanr() {
                return isikEndineMajanr;
            }

            /**
             * Sets the value of the isikEndineMajanr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineMajanr(String value) {
                this.isikEndineMajanr = value;
            }

            /**
             * Gets the value of the isikEndineKorternr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineKorternr() {
                return isikEndineKorternr;
            }

            /**
             * Sets the value of the isikEndineKorternr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineKorternr(String value) {
                this.isikEndineKorternr = value;
            }

            /**
             * Gets the value of the isikEndineADSADRID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineADSADRID() {
                return isikEndineADSADRID;
            }

            /**
             * Sets the value of the isikEndineADSADRID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineADSADRID(String value) {
                this.isikEndineADSADRID = value;
            }

            /**
             * Gets the value of the isikEndineADSOID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineADSOID() {
                return isikEndineADSOID;
            }

            /**
             * Sets the value of the isikEndineADSOID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineADSOID(String value) {
                this.isikEndineADSOID = value;
            }

            /**
             * Gets the value of the isikEndineADSKoodaadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineADSKoodaadress() {
                return isikEndineADSKoodaadress;
            }

            /**
             * Sets the value of the isikEndineADSKoodaadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineADSKoodaadress(String value) {
                this.isikEndineADSKoodaadress = value;
            }

            /**
             * Gets the value of the isikEndineADSADOBOID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEndineADSADOBOID() {
                return isikEndineADSADOBOID;
            }

            /**
             * Sets the value of the isikEndineADSADOBOID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEndineADSADOBOID(String value) {
                this.isikEndineADSADOBOID = value;
            }

        }

    }

}
