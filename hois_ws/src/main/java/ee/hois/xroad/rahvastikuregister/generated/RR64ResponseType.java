
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR64ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR64ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Isikud"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Isikuandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Isikuandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Synniriik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Aadressiliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKPostiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.IsikuElukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.ElukAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SAadressiliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SARiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SAMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SAVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SAAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SAPostiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SATanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SAMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SAKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Sideaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SideaadressAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.AlaealisedLapsed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EestiKodAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.MuudKodakondsused" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.KustutamiseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.KustutamisePohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.AlusdokKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.AlusdokNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.AlusdokNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.AlusdokAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR64ResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikud"
})
public class RR64ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood")
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Isikud", required = true)
    protected RR64ResponseType.Isikud isikud;

    /**
     * Gets the value of the veakood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVeakood() {
        return veakood;
    }

    /**
     * Sets the value of the veakood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVeakood(BigInteger value) {
        this.veakood = value;
    }

    /**
     * Gets the value of the veatekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVeatekst() {
        return veatekst;
    }

    /**
     * Sets the value of the veatekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVeatekst(String value) {
        this.veatekst = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link RR64ResponseType.Isikud }
     *     
     */
    public RR64ResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR64ResponseType.Isikud }
     *     
     */
    public void setIsikud(RR64ResponseType.Isikud value) {
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
     *         &lt;element name="Isikuandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikuandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Synniriik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Aadressiliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKPostiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.IsikuElukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.ElukAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SAadressiliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SARiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SAMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SAVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SAAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SAPostiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SATanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SAMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SAKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Sideaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SideaadressAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.AlaealisedLapsed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EestiKodAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.MuudKodakondsused" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.KustutamiseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.KustutamisePohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.AlusdokKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.AlusdokNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.AlusdokNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.AlusdokAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "isikuandmed"
    })
    public static class Isikud {

        @XmlElement(name = "Isikuandmed")
        protected List<RR64ResponseType.Isikud.Isikuandmed> isikuandmed;

        /**
         * Gets the value of the isikuandmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the isikuandmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIsikuandmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR64ResponseType.Isikud.Isikuandmed }
         * 
         * 
         */
        public List<RR64ResponseType.Isikud.Isikuandmed> getIsikuandmed() {
            if (isikuandmed == null) {
                isikuandmed = new ArrayList<RR64ResponseType.Isikud.Isikuandmed>();
            }
            return this.isikuandmed;
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
         *         &lt;element name="Isikuandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Synniriik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Aadressiliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKPostiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKTanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.IsikuElukoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.ElukAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SAadressiliik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SARiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SAMaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SAVald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SAAsula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SAPostiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SATanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SAMaja" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SAKorter" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Sideaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SideaadressAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.AlaealisedLapsed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EestiKodAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.MuudKodakondsused" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.KustutamiseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.KustutamisePohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.AlusdokKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.AlusdokNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.AlusdokNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.AlusdokAsutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikuandmedIsikukood",
            "isikuandmedEesnimi",
            "isikuandmedPerenimi",
            "isikuandmedTeovoime",
            "isikuandmedSynniriik",
            "isikuandmedAadressiliik",
            "isikuandmedEKRiik",
            "isikuandmedEKMaakond",
            "isikuandmedEKVald",
            "isikuandmedEKAsula",
            "isikuandmedEKPostiindeks",
            "isikuandmedEKTanav",
            "isikuandmedEKMaja",
            "isikuandmedEKKorter",
            "isikuandmedIsikuElukoht",
            "isikuandmedElukAlates",
            "isikuandmedADSADRID",
            "isikuandmedSAadressiliik",
            "isikuandmedSARiik",
            "isikuandmedSAMaakond",
            "isikuandmedSAVald",
            "isikuandmedSAAsula",
            "isikuandmedSAPostiindeks",
            "isikuandmedSATanav",
            "isikuandmedSAMaja",
            "isikuandmedSAKorter",
            "isikuandmedSideaadress",
            "isikuandmedSideaadressAlates",
            "isikuandmedAlaealisedLapsed",
            "isikuandmedEestiKodAlates",
            "isikuandmedMuudKodakondsused",
            "isikuandmedKustutamiseKood",
            "isikuandmedKustutamisePohjus",
            "isikuandmedAlusdokKpv",
            "isikuandmedAlusdokNimi",
            "isikuandmedAlusdokNumber",
            "isikuandmedAlusdokAsutus"
        })
        public static class Isikuandmed {

            @XmlElement(name = "Isikuandmed.Isikukood", required = true)
            protected String isikuandmedIsikukood;
            @XmlElement(name = "Isikuandmed.Eesnimi", required = true)
            protected String isikuandmedEesnimi;
            @XmlElement(name = "Isikuandmed.Perenimi", required = true)
            protected String isikuandmedPerenimi;
            @XmlElement(name = "Isikuandmed.Teovoime", required = true)
            protected String isikuandmedTeovoime;
            @XmlElement(name = "Isikuandmed.Synniriik", required = true)
            protected String isikuandmedSynniriik;
            @XmlElement(name = "Isikuandmed.Aadressiliik", required = true)
            protected String isikuandmedAadressiliik;
            @XmlElement(name = "Isikuandmed.EKRiik", required = true)
            protected String isikuandmedEKRiik;
            @XmlElement(name = "Isikuandmed.EKMaakond", required = true)
            protected String isikuandmedEKMaakond;
            @XmlElement(name = "Isikuandmed.EKVald", required = true)
            protected String isikuandmedEKVald;
            @XmlElement(name = "Isikuandmed.EKAsula", required = true)
            protected String isikuandmedEKAsula;
            @XmlElement(name = "Isikuandmed.EKPostiindeks", required = true)
            protected String isikuandmedEKPostiindeks;
            @XmlElement(name = "Isikuandmed.EKTanav", required = true)
            protected String isikuandmedEKTanav;
            @XmlElement(name = "Isikuandmed.EKMaja", required = true)
            protected String isikuandmedEKMaja;
            @XmlElement(name = "Isikuandmed.EKKorter", required = true)
            protected String isikuandmedEKKorter;
            @XmlElement(name = "Isikuandmed.IsikuElukoht", required = true)
            protected String isikuandmedIsikuElukoht;
            @XmlElement(name = "Isikuandmed.ElukAlates", required = true)
            protected String isikuandmedElukAlates;
            @XmlElement(name = "Isikuandmed.ADS_ADR_ID", required = true)
            protected String isikuandmedADSADRID;
            @XmlElement(name = "Isikuandmed.SAadressiliik", required = true)
            protected String isikuandmedSAadressiliik;
            @XmlElement(name = "Isikuandmed.SARiik", required = true)
            protected String isikuandmedSARiik;
            @XmlElement(name = "Isikuandmed.SAMaakond", required = true)
            protected String isikuandmedSAMaakond;
            @XmlElement(name = "Isikuandmed.SAVald", required = true)
            protected String isikuandmedSAVald;
            @XmlElement(name = "Isikuandmed.SAAsula", required = true)
            protected String isikuandmedSAAsula;
            @XmlElement(name = "Isikuandmed.SAPostiindeks", required = true)
            protected String isikuandmedSAPostiindeks;
            @XmlElement(name = "Isikuandmed.SATanav", required = true)
            protected String isikuandmedSATanav;
            @XmlElement(name = "Isikuandmed.SAMaja", required = true)
            protected String isikuandmedSAMaja;
            @XmlElement(name = "Isikuandmed.SAKorter", required = true)
            protected String isikuandmedSAKorter;
            @XmlElement(name = "Isikuandmed.Sideaadress", required = true)
            protected String isikuandmedSideaadress;
            @XmlElement(name = "Isikuandmed.SideaadressAlates", required = true)
            protected String isikuandmedSideaadressAlates;
            @XmlElement(name = "Isikuandmed.AlaealisedLapsed", required = true)
            protected String isikuandmedAlaealisedLapsed;
            @XmlElement(name = "Isikuandmed.EestiKodAlates", required = true)
            protected String isikuandmedEestiKodAlates;
            @XmlElement(name = "Isikuandmed.MuudKodakondsused", required = true)
            protected String isikuandmedMuudKodakondsused;
            @XmlElement(name = "Isikuandmed.KustutamiseKood", required = true)
            protected String isikuandmedKustutamiseKood;
            @XmlElement(name = "Isikuandmed.KustutamisePohjus", required = true)
            protected String isikuandmedKustutamisePohjus;
            @XmlElement(name = "Isikuandmed.AlusdokKpv", required = true)
            protected String isikuandmedAlusdokKpv;
            @XmlElement(name = "Isikuandmed.AlusdokNimi", required = true)
            protected String isikuandmedAlusdokNimi;
            @XmlElement(name = "Isikuandmed.AlusdokNumber", required = true)
            protected String isikuandmedAlusdokNumber;
            @XmlElement(name = "Isikuandmed.AlusdokAsutus", required = true)
            protected String isikuandmedAlusdokAsutus;

            /**
             * Gets the value of the isikuandmedIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedIsikukood() {
                return isikuandmedIsikukood;
            }

            /**
             * Sets the value of the isikuandmedIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedIsikukood(String value) {
                this.isikuandmedIsikukood = value;
            }

            /**
             * Gets the value of the isikuandmedEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEesnimi() {
                return isikuandmedEesnimi;
            }

            /**
             * Sets the value of the isikuandmedEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEesnimi(String value) {
                this.isikuandmedEesnimi = value;
            }

            /**
             * Gets the value of the isikuandmedPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedPerenimi() {
                return isikuandmedPerenimi;
            }

            /**
             * Sets the value of the isikuandmedPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedPerenimi(String value) {
                this.isikuandmedPerenimi = value;
            }

            /**
             * Gets the value of the isikuandmedTeovoime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedTeovoime() {
                return isikuandmedTeovoime;
            }

            /**
             * Sets the value of the isikuandmedTeovoime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedTeovoime(String value) {
                this.isikuandmedTeovoime = value;
            }

            /**
             * Gets the value of the isikuandmedSynniriik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSynniriik() {
                return isikuandmedSynniriik;
            }

            /**
             * Sets the value of the isikuandmedSynniriik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSynniriik(String value) {
                this.isikuandmedSynniriik = value;
            }

            /**
             * Gets the value of the isikuandmedAadressiliik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedAadressiliik() {
                return isikuandmedAadressiliik;
            }

            /**
             * Sets the value of the isikuandmedAadressiliik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedAadressiliik(String value) {
                this.isikuandmedAadressiliik = value;
            }

            /**
             * Gets the value of the isikuandmedEKRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKRiik() {
                return isikuandmedEKRiik;
            }

            /**
             * Sets the value of the isikuandmedEKRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKRiik(String value) {
                this.isikuandmedEKRiik = value;
            }

            /**
             * Gets the value of the isikuandmedEKMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKMaakond() {
                return isikuandmedEKMaakond;
            }

            /**
             * Sets the value of the isikuandmedEKMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKMaakond(String value) {
                this.isikuandmedEKMaakond = value;
            }

            /**
             * Gets the value of the isikuandmedEKVald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKVald() {
                return isikuandmedEKVald;
            }

            /**
             * Sets the value of the isikuandmedEKVald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKVald(String value) {
                this.isikuandmedEKVald = value;
            }

            /**
             * Gets the value of the isikuandmedEKAsula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKAsula() {
                return isikuandmedEKAsula;
            }

            /**
             * Sets the value of the isikuandmedEKAsula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKAsula(String value) {
                this.isikuandmedEKAsula = value;
            }

            /**
             * Gets the value of the isikuandmedEKPostiindeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKPostiindeks() {
                return isikuandmedEKPostiindeks;
            }

            /**
             * Sets the value of the isikuandmedEKPostiindeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKPostiindeks(String value) {
                this.isikuandmedEKPostiindeks = value;
            }

            /**
             * Gets the value of the isikuandmedEKTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKTanav() {
                return isikuandmedEKTanav;
            }

            /**
             * Sets the value of the isikuandmedEKTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKTanav(String value) {
                this.isikuandmedEKTanav = value;
            }

            /**
             * Gets the value of the isikuandmedEKMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKMaja() {
                return isikuandmedEKMaja;
            }

            /**
             * Sets the value of the isikuandmedEKMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKMaja(String value) {
                this.isikuandmedEKMaja = value;
            }

            /**
             * Gets the value of the isikuandmedEKKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKKorter() {
                return isikuandmedEKKorter;
            }

            /**
             * Sets the value of the isikuandmedEKKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKKorter(String value) {
                this.isikuandmedEKKorter = value;
            }

            /**
             * Gets the value of the isikuandmedIsikuElukoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedIsikuElukoht() {
                return isikuandmedIsikuElukoht;
            }

            /**
             * Sets the value of the isikuandmedIsikuElukoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedIsikuElukoht(String value) {
                this.isikuandmedIsikuElukoht = value;
            }

            /**
             * Gets the value of the isikuandmedElukAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedElukAlates() {
                return isikuandmedElukAlates;
            }

            /**
             * Sets the value of the isikuandmedElukAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedElukAlates(String value) {
                this.isikuandmedElukAlates = value;
            }

            /**
             * Gets the value of the isikuandmedADSADRID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedADSADRID() {
                return isikuandmedADSADRID;
            }

            /**
             * Sets the value of the isikuandmedADSADRID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedADSADRID(String value) {
                this.isikuandmedADSADRID = value;
            }

            /**
             * Gets the value of the isikuandmedSAadressiliik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSAadressiliik() {
                return isikuandmedSAadressiliik;
            }

            /**
             * Sets the value of the isikuandmedSAadressiliik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSAadressiliik(String value) {
                this.isikuandmedSAadressiliik = value;
            }

            /**
             * Gets the value of the isikuandmedSARiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSARiik() {
                return isikuandmedSARiik;
            }

            /**
             * Sets the value of the isikuandmedSARiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSARiik(String value) {
                this.isikuandmedSARiik = value;
            }

            /**
             * Gets the value of the isikuandmedSAMaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSAMaakond() {
                return isikuandmedSAMaakond;
            }

            /**
             * Sets the value of the isikuandmedSAMaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSAMaakond(String value) {
                this.isikuandmedSAMaakond = value;
            }

            /**
             * Gets the value of the isikuandmedSAVald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSAVald() {
                return isikuandmedSAVald;
            }

            /**
             * Sets the value of the isikuandmedSAVald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSAVald(String value) {
                this.isikuandmedSAVald = value;
            }

            /**
             * Gets the value of the isikuandmedSAAsula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSAAsula() {
                return isikuandmedSAAsula;
            }

            /**
             * Sets the value of the isikuandmedSAAsula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSAAsula(String value) {
                this.isikuandmedSAAsula = value;
            }

            /**
             * Gets the value of the isikuandmedSAPostiindeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSAPostiindeks() {
                return isikuandmedSAPostiindeks;
            }

            /**
             * Sets the value of the isikuandmedSAPostiindeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSAPostiindeks(String value) {
                this.isikuandmedSAPostiindeks = value;
            }

            /**
             * Gets the value of the isikuandmedSATanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSATanav() {
                return isikuandmedSATanav;
            }

            /**
             * Sets the value of the isikuandmedSATanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSATanav(String value) {
                this.isikuandmedSATanav = value;
            }

            /**
             * Gets the value of the isikuandmedSAMaja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSAMaja() {
                return isikuandmedSAMaja;
            }

            /**
             * Sets the value of the isikuandmedSAMaja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSAMaja(String value) {
                this.isikuandmedSAMaja = value;
            }

            /**
             * Gets the value of the isikuandmedSAKorter property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSAKorter() {
                return isikuandmedSAKorter;
            }

            /**
             * Sets the value of the isikuandmedSAKorter property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSAKorter(String value) {
                this.isikuandmedSAKorter = value;
            }

            /**
             * Gets the value of the isikuandmedSideaadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSideaadress() {
                return isikuandmedSideaadress;
            }

            /**
             * Sets the value of the isikuandmedSideaadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSideaadress(String value) {
                this.isikuandmedSideaadress = value;
            }

            /**
             * Gets the value of the isikuandmedSideaadressAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSideaadressAlates() {
                return isikuandmedSideaadressAlates;
            }

            /**
             * Sets the value of the isikuandmedSideaadressAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSideaadressAlates(String value) {
                this.isikuandmedSideaadressAlates = value;
            }

            /**
             * Gets the value of the isikuandmedAlaealisedLapsed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedAlaealisedLapsed() {
                return isikuandmedAlaealisedLapsed;
            }

            /**
             * Sets the value of the isikuandmedAlaealisedLapsed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedAlaealisedLapsed(String value) {
                this.isikuandmedAlaealisedLapsed = value;
            }

            /**
             * Gets the value of the isikuandmedEestiKodAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEestiKodAlates() {
                return isikuandmedEestiKodAlates;
            }

            /**
             * Sets the value of the isikuandmedEestiKodAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEestiKodAlates(String value) {
                this.isikuandmedEestiKodAlates = value;
            }

            /**
             * Gets the value of the isikuandmedMuudKodakondsused property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedMuudKodakondsused() {
                return isikuandmedMuudKodakondsused;
            }

            /**
             * Sets the value of the isikuandmedMuudKodakondsused property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedMuudKodakondsused(String value) {
                this.isikuandmedMuudKodakondsused = value;
            }

            /**
             * Gets the value of the isikuandmedKustutamiseKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedKustutamiseKood() {
                return isikuandmedKustutamiseKood;
            }

            /**
             * Sets the value of the isikuandmedKustutamiseKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedKustutamiseKood(String value) {
                this.isikuandmedKustutamiseKood = value;
            }

            /**
             * Gets the value of the isikuandmedKustutamisePohjus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedKustutamisePohjus() {
                return isikuandmedKustutamisePohjus;
            }

            /**
             * Sets the value of the isikuandmedKustutamisePohjus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedKustutamisePohjus(String value) {
                this.isikuandmedKustutamisePohjus = value;
            }

            /**
             * Gets the value of the isikuandmedAlusdokKpv property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedAlusdokKpv() {
                return isikuandmedAlusdokKpv;
            }

            /**
             * Sets the value of the isikuandmedAlusdokKpv property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedAlusdokKpv(String value) {
                this.isikuandmedAlusdokKpv = value;
            }

            /**
             * Gets the value of the isikuandmedAlusdokNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedAlusdokNimi() {
                return isikuandmedAlusdokNimi;
            }

            /**
             * Sets the value of the isikuandmedAlusdokNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedAlusdokNimi(String value) {
                this.isikuandmedAlusdokNimi = value;
            }

            /**
             * Gets the value of the isikuandmedAlusdokNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedAlusdokNumber() {
                return isikuandmedAlusdokNumber;
            }

            /**
             * Sets the value of the isikuandmedAlusdokNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedAlusdokNumber(String value) {
                this.isikuandmedAlusdokNumber = value;
            }

            /**
             * Gets the value of the isikuandmedAlusdokAsutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedAlusdokAsutus() {
                return isikuandmedAlusdokAsutus;
            }

            /**
             * Sets the value of the isikuandmedAlusdokAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedAlusdokAsutus(String value) {
                this.isikuandmedAlusdokAsutus = value;
            }

        }

    }

}
