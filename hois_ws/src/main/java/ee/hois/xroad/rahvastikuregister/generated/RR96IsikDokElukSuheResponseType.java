
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR96IsikDokElukSuheResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR96IsikDokElukSuheResponseType"&gt;
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
 *                             &lt;element name="Isikuandmed.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Synniperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Kodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Perekonnaseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.ElukRegpe" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SaabEestisse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.ViimatiPar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.IsikuSeisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Andmed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKRIIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKmaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKvald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EKasula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Amet" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Eriala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.KoviSaabus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Syriikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Syriiginimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.ElukAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Dokumendid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Dokumendiandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Dokumendiandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokKontr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.AsutusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Sisestatud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokSyndKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.IsikIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Teised_IsikIskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Teised_DokIskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Teised_DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Teised_DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Teised_Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokVanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokVanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Suhted"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Suhe" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Lopukp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Elukohad"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Elukohaandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Elukohaandmed.Riigikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Riiginimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.MaakonnaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.VallaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.KylaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.KylaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Korterinr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Algusekuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Lopukuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Kontaktid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Kontaktandmed" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Kontaktandmed.Sideaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Kontaktandmed.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Kontaktandmed.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Kontaktandmed.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Kontaktandmed.Lopukp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Kontaktandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Hooldusoigused"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Hooldusoigused.hoRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoSisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoTeineIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoTeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoTeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoOlekStat" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoPrimIsik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR96IsikDokElukSuheResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikud",
    "dokumendid",
    "suhted",
    "elukohad",
    "kontaktid",
    "hooldusoigused"
})
public class RR96IsikDokElukSuheResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood")
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Isikud", required = true)
    protected RR96IsikDokElukSuheResponseType.Isikud isikud;
    @XmlElement(name = "Dokumendid", required = true)
    protected RR96IsikDokElukSuheResponseType.Dokumendid dokumendid;
    @XmlElement(name = "Suhted", required = true)
    protected RR96IsikDokElukSuheResponseType.Suhted suhted;
    @XmlElement(name = "Elukohad", required = true)
    protected RR96IsikDokElukSuheResponseType.Elukohad elukohad;
    @XmlElement(name = "Kontaktid", required = true)
    protected RR96IsikDokElukSuheResponseType.Kontaktid kontaktid;
    @XmlElement(name = "Hooldusoigused", required = true)
    protected RR96IsikDokElukSuheResponseType.Hooldusoigused hooldusoigused;

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
     *     {@link RR96IsikDokElukSuheResponseType.Isikud }
     *     
     */
    public RR96IsikDokElukSuheResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR96IsikDokElukSuheResponseType.Isikud }
     *     
     */
    public void setIsikud(RR96IsikDokElukSuheResponseType.Isikud value) {
        this.isikud = value;
    }

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RR96IsikDokElukSuheResponseType.Dokumendid }
     *     
     */
    public RR96IsikDokElukSuheResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR96IsikDokElukSuheResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RR96IsikDokElukSuheResponseType.Dokumendid value) {
        this.dokumendid = value;
    }

    /**
     * Gets the value of the suhted property.
     * 
     * @return
     *     possible object is
     *     {@link RR96IsikDokElukSuheResponseType.Suhted }
     *     
     */
    public RR96IsikDokElukSuheResponseType.Suhted getSuhted() {
        return suhted;
    }

    /**
     * Sets the value of the suhted property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR96IsikDokElukSuheResponseType.Suhted }
     *     
     */
    public void setSuhted(RR96IsikDokElukSuheResponseType.Suhted value) {
        this.suhted = value;
    }

    /**
     * Gets the value of the elukohad property.
     * 
     * @return
     *     possible object is
     *     {@link RR96IsikDokElukSuheResponseType.Elukohad }
     *     
     */
    public RR96IsikDokElukSuheResponseType.Elukohad getElukohad() {
        return elukohad;
    }

    /**
     * Sets the value of the elukohad property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR96IsikDokElukSuheResponseType.Elukohad }
     *     
     */
    public void setElukohad(RR96IsikDokElukSuheResponseType.Elukohad value) {
        this.elukohad = value;
    }

    /**
     * Gets the value of the kontaktid property.
     * 
     * @return
     *     possible object is
     *     {@link RR96IsikDokElukSuheResponseType.Kontaktid }
     *     
     */
    public RR96IsikDokElukSuheResponseType.Kontaktid getKontaktid() {
        return kontaktid;
    }

    /**
     * Sets the value of the kontaktid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR96IsikDokElukSuheResponseType.Kontaktid }
     *     
     */
    public void setKontaktid(RR96IsikDokElukSuheResponseType.Kontaktid value) {
        this.kontaktid = value;
    }

    /**
     * Gets the value of the hooldusoigused property.
     * 
     * @return
     *     possible object is
     *     {@link RR96IsikDokElukSuheResponseType.Hooldusoigused }
     *     
     */
    public RR96IsikDokElukSuheResponseType.Hooldusoigused getHooldusoigused() {
        return hooldusoigused;
    }

    /**
     * Sets the value of the hooldusoigused property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR96IsikDokElukSuheResponseType.Hooldusoigused }
     *     
     */
    public void setHooldusoigused(RR96IsikDokElukSuheResponseType.Hooldusoigused value) {
        this.hooldusoigused = value;
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
     *         &lt;element name="Dokumendiandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Dokumendiandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokKontr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.AsutusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Sisestatud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokSyndKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.IsikIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Teised_IsikIskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Teised_DokIskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Teised_DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Teised_DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Teised_Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokVanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokVanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "dokumendiandmed"
    })
    public static class Dokumendid {

        @XmlElement(name = "Dokumendiandmed")
        protected List<RR96IsikDokElukSuheResponseType.Dokumendid.Dokumendiandmed> dokumendiandmed;

        /**
         * Gets the value of the dokumendiandmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dokumendiandmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDokumendiandmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR96IsikDokElukSuheResponseType.Dokumendid.Dokumendiandmed }
         * 
         * 
         */
        public List<RR96IsikDokElukSuheResponseType.Dokumendid.Dokumendiandmed> getDokumendiandmed() {
            if (dokumendiandmed == null) {
                dokumendiandmed = new ArrayList<RR96IsikDokElukSuheResponseType.Dokumendid.Dokumendiandmed>();
            }
            return this.dokumendiandmed;
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
         *         &lt;element name="Dokumendiandmed.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokKontr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.AsutusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Sisestatud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokSyndKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.IsikIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Teised_IsikIskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Teised_DokIskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Teised_DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Teised_DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Teised_Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokVanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokVanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokumendiandmedKood",
            "dokumendiandmedNimetus",
            "dokumendiandmedSeeria",
            "dokumendiandmedNumber",
            "dokumendiandmedDokStaatus",
            "dokumendiandmedDokKontr",
            "dokumendiandmedAsutusRiik",
            "dokumendiandmedAsutus",
            "dokumendiandmedDokValjastamisKuup",
            "dokumendiandmedSisestatud",
            "dokumendiandmedDokSyndKuup",
            "dokumendiandmedKehtivAlates",
            "dokumendiandmedKehtivKuni",
            "dokumendiandmedIsikIsikukood",
            "dokumendiandmedDokIsikukood",
            "dokumendiandmedDokPerekonnanimi",
            "dokumendiandmedDokEesnimi",
            "dokumendiandmedDokOsalus",
            "dokumendiandmedTeisedIsikIskood",
            "dokumendiandmedTeisedDokIskood",
            "dokumendiandmedTeisedDokPerenimi",
            "dokumendiandmedTeisedDokEesnimi",
            "dokumendiandmedTeisedDokOsalus",
            "dokumendiandmedDokVanaEesnimi",
            "dokumendiandmedDokVanaPerenimi"
        })
        public static class Dokumendiandmed {

            @XmlElement(name = "Dokumendiandmed.Kood", required = true)
            protected String dokumendiandmedKood;
            @XmlElement(name = "Dokumendiandmed.Nimetus", required = true)
            protected String dokumendiandmedNimetus;
            @XmlElement(name = "Dokumendiandmed.Seeria", required = true)
            protected String dokumendiandmedSeeria;
            @XmlElement(name = "Dokumendiandmed.Number", required = true)
            protected String dokumendiandmedNumber;
            @XmlElement(name = "Dokumendiandmed.DokStaatus", required = true)
            protected String dokumendiandmedDokStaatus;
            @XmlElement(name = "Dokumendiandmed.DokKontr", required = true)
            protected String dokumendiandmedDokKontr;
            @XmlElement(name = "Dokumendiandmed.AsutusRiik", required = true)
            protected String dokumendiandmedAsutusRiik;
            @XmlElement(name = "Dokumendiandmed.Asutus", required = true)
            protected String dokumendiandmedAsutus;
            @XmlElement(name = "Dokumendiandmed.DokValjastamisKuup", required = true)
            protected String dokumendiandmedDokValjastamisKuup;
            @XmlElement(name = "Dokumendiandmed.Sisestatud", required = true)
            protected String dokumendiandmedSisestatud;
            @XmlElement(name = "Dokumendiandmed.DokSyndKuup", required = true)
            protected String dokumendiandmedDokSyndKuup;
            @XmlElement(name = "Dokumendiandmed.KehtivAlates", required = true)
            protected String dokumendiandmedKehtivAlates;
            @XmlElement(name = "Dokumendiandmed.KehtivKuni", required = true)
            protected String dokumendiandmedKehtivKuni;
            @XmlElement(name = "Dokumendiandmed.IsikIsikukood", required = true)
            protected String dokumendiandmedIsikIsikukood;
            @XmlElement(name = "Dokumendiandmed.DokIsikukood", required = true)
            protected String dokumendiandmedDokIsikukood;
            @XmlElement(name = "Dokumendiandmed.DokPerekonnanimi", required = true)
            protected String dokumendiandmedDokPerekonnanimi;
            @XmlElement(name = "Dokumendiandmed.DokEesnimi", required = true)
            protected String dokumendiandmedDokEesnimi;
            @XmlElement(name = "Dokumendiandmed.Dok_osalus", required = true)
            protected String dokumendiandmedDokOsalus;
            @XmlElement(name = "Dokumendiandmed.Teised_IsikIskood", required = true)
            protected String dokumendiandmedTeisedIsikIskood;
            @XmlElement(name = "Dokumendiandmed.Teised_DokIskood", required = true)
            protected String dokumendiandmedTeisedDokIskood;
            @XmlElement(name = "Dokumendiandmed.Teised_DokPerenimi", required = true)
            protected String dokumendiandmedTeisedDokPerenimi;
            @XmlElement(name = "Dokumendiandmed.Teised_DokEesnimi", required = true)
            protected String dokumendiandmedTeisedDokEesnimi;
            @XmlElement(name = "Dokumendiandmed.Teised_Dok_osalus", required = true)
            protected String dokumendiandmedTeisedDokOsalus;
            @XmlElement(name = "Dokumendiandmed.DokVanaEesnimi", required = true)
            protected String dokumendiandmedDokVanaEesnimi;
            @XmlElement(name = "Dokumendiandmed.DokVanaPerenimi", required = true)
            protected String dokumendiandmedDokVanaPerenimi;

            /**
             * Gets the value of the dokumendiandmedKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedKood() {
                return dokumendiandmedKood;
            }

            /**
             * Sets the value of the dokumendiandmedKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedKood(String value) {
                this.dokumendiandmedKood = value;
            }

            /**
             * Gets the value of the dokumendiandmedNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedNimetus() {
                return dokumendiandmedNimetus;
            }

            /**
             * Sets the value of the dokumendiandmedNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedNimetus(String value) {
                this.dokumendiandmedNimetus = value;
            }

            /**
             * Gets the value of the dokumendiandmedSeeria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedSeeria() {
                return dokumendiandmedSeeria;
            }

            /**
             * Sets the value of the dokumendiandmedSeeria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedSeeria(String value) {
                this.dokumendiandmedSeeria = value;
            }

            /**
             * Gets the value of the dokumendiandmedNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedNumber() {
                return dokumendiandmedNumber;
            }

            /**
             * Sets the value of the dokumendiandmedNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedNumber(String value) {
                this.dokumendiandmedNumber = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokStaatus() {
                return dokumendiandmedDokStaatus;
            }

            /**
             * Sets the value of the dokumendiandmedDokStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokStaatus(String value) {
                this.dokumendiandmedDokStaatus = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokKontr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokKontr() {
                return dokumendiandmedDokKontr;
            }

            /**
             * Sets the value of the dokumendiandmedDokKontr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokKontr(String value) {
                this.dokumendiandmedDokKontr = value;
            }

            /**
             * Gets the value of the dokumendiandmedAsutusRiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedAsutusRiik() {
                return dokumendiandmedAsutusRiik;
            }

            /**
             * Sets the value of the dokumendiandmedAsutusRiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedAsutusRiik(String value) {
                this.dokumendiandmedAsutusRiik = value;
            }

            /**
             * Gets the value of the dokumendiandmedAsutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedAsutus() {
                return dokumendiandmedAsutus;
            }

            /**
             * Sets the value of the dokumendiandmedAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedAsutus(String value) {
                this.dokumendiandmedAsutus = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokValjastamisKuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokValjastamisKuup() {
                return dokumendiandmedDokValjastamisKuup;
            }

            /**
             * Sets the value of the dokumendiandmedDokValjastamisKuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokValjastamisKuup(String value) {
                this.dokumendiandmedDokValjastamisKuup = value;
            }

            /**
             * Gets the value of the dokumendiandmedSisestatud property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedSisestatud() {
                return dokumendiandmedSisestatud;
            }

            /**
             * Sets the value of the dokumendiandmedSisestatud property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedSisestatud(String value) {
                this.dokumendiandmedSisestatud = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokSyndKuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokSyndKuup() {
                return dokumendiandmedDokSyndKuup;
            }

            /**
             * Sets the value of the dokumendiandmedDokSyndKuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokSyndKuup(String value) {
                this.dokumendiandmedDokSyndKuup = value;
            }

            /**
             * Gets the value of the dokumendiandmedKehtivAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedKehtivAlates() {
                return dokumendiandmedKehtivAlates;
            }

            /**
             * Sets the value of the dokumendiandmedKehtivAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedKehtivAlates(String value) {
                this.dokumendiandmedKehtivAlates = value;
            }

            /**
             * Gets the value of the dokumendiandmedKehtivKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedKehtivKuni() {
                return dokumendiandmedKehtivKuni;
            }

            /**
             * Sets the value of the dokumendiandmedKehtivKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedKehtivKuni(String value) {
                this.dokumendiandmedKehtivKuni = value;
            }

            /**
             * Gets the value of the dokumendiandmedIsikIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedIsikIsikukood() {
                return dokumendiandmedIsikIsikukood;
            }

            /**
             * Sets the value of the dokumendiandmedIsikIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedIsikIsikukood(String value) {
                this.dokumendiandmedIsikIsikukood = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokIsikukood() {
                return dokumendiandmedDokIsikukood;
            }

            /**
             * Sets the value of the dokumendiandmedDokIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokIsikukood(String value) {
                this.dokumendiandmedDokIsikukood = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokPerekonnanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokPerekonnanimi() {
                return dokumendiandmedDokPerekonnanimi;
            }

            /**
             * Sets the value of the dokumendiandmedDokPerekonnanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokPerekonnanimi(String value) {
                this.dokumendiandmedDokPerekonnanimi = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokEesnimi() {
                return dokumendiandmedDokEesnimi;
            }

            /**
             * Sets the value of the dokumendiandmedDokEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokEesnimi(String value) {
                this.dokumendiandmedDokEesnimi = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokOsalus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokOsalus() {
                return dokumendiandmedDokOsalus;
            }

            /**
             * Sets the value of the dokumendiandmedDokOsalus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokOsalus(String value) {
                this.dokumendiandmedDokOsalus = value;
            }

            /**
             * Gets the value of the dokumendiandmedTeisedIsikIskood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedTeisedIsikIskood() {
                return dokumendiandmedTeisedIsikIskood;
            }

            /**
             * Sets the value of the dokumendiandmedTeisedIsikIskood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedTeisedIsikIskood(String value) {
                this.dokumendiandmedTeisedIsikIskood = value;
            }

            /**
             * Gets the value of the dokumendiandmedTeisedDokIskood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedTeisedDokIskood() {
                return dokumendiandmedTeisedDokIskood;
            }

            /**
             * Sets the value of the dokumendiandmedTeisedDokIskood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedTeisedDokIskood(String value) {
                this.dokumendiandmedTeisedDokIskood = value;
            }

            /**
             * Gets the value of the dokumendiandmedTeisedDokPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedTeisedDokPerenimi() {
                return dokumendiandmedTeisedDokPerenimi;
            }

            /**
             * Sets the value of the dokumendiandmedTeisedDokPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedTeisedDokPerenimi(String value) {
                this.dokumendiandmedTeisedDokPerenimi = value;
            }

            /**
             * Gets the value of the dokumendiandmedTeisedDokEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedTeisedDokEesnimi() {
                return dokumendiandmedTeisedDokEesnimi;
            }

            /**
             * Sets the value of the dokumendiandmedTeisedDokEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedTeisedDokEesnimi(String value) {
                this.dokumendiandmedTeisedDokEesnimi = value;
            }

            /**
             * Gets the value of the dokumendiandmedTeisedDokOsalus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedTeisedDokOsalus() {
                return dokumendiandmedTeisedDokOsalus;
            }

            /**
             * Sets the value of the dokumendiandmedTeisedDokOsalus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedTeisedDokOsalus(String value) {
                this.dokumendiandmedTeisedDokOsalus = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokVanaEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokVanaEesnimi() {
                return dokumendiandmedDokVanaEesnimi;
            }

            /**
             * Sets the value of the dokumendiandmedDokVanaEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokVanaEesnimi(String value) {
                this.dokumendiandmedDokVanaEesnimi = value;
            }

            /**
             * Gets the value of the dokumendiandmedDokVanaPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokVanaPerenimi() {
                return dokumendiandmedDokVanaPerenimi;
            }

            /**
             * Sets the value of the dokumendiandmedDokVanaPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokVanaPerenimi(String value) {
                this.dokumendiandmedDokVanaPerenimi = value;
            }

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
     *         &lt;element name="Elukohaandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Elukohaandmed.Riigikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Riiginimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.MaakonnaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.VallaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.KylaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.KylaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Korterinr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Algusekuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Lopukuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "elukohaandmed"
    })
    public static class Elukohad {

        @XmlElement(name = "Elukohaandmed")
        protected List<RR96IsikDokElukSuheResponseType.Elukohad.Elukohaandmed> elukohaandmed;

        /**
         * Gets the value of the elukohaandmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the elukohaandmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getElukohaandmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR96IsikDokElukSuheResponseType.Elukohad.Elukohaandmed }
         * 
         * 
         */
        public List<RR96IsikDokElukSuheResponseType.Elukohad.Elukohaandmed> getElukohaandmed() {
            if (elukohaandmed == null) {
                elukohaandmed = new ArrayList<RR96IsikDokElukSuheResponseType.Elukohad.Elukohaandmed>();
            }
            return this.elukohaandmed;
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
         *         &lt;element name="Elukohaandmed.Riigikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Riiginimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.MaakonnaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.MaakonnaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.VallaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.VallaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.KylaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.KylaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Korterinr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Algusekuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Lopukuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "elukohaandmedRiigikood",
            "elukohaandmedRiiginimetus",
            "elukohaandmedMaakonnaKood",
            "elukohaandmedMaakonnaNimetus",
            "elukohaandmedVallaKood",
            "elukohaandmedVallaNimetus",
            "elukohaandmedKylaKood",
            "elukohaandmedKylaNimetus",
            "elukohaandmedTanav",
            "elukohaandmedMajanr",
            "elukohaandmedKorterinr",
            "elukohaandmedAadressTekstina",
            "elukohaandmedPostiindeks",
            "elukohaandmedAlgusekuup",
            "elukohaandmedLopukuup",
            "elukohaandmedAadressiLiik",
            "elukohaandmedIsikuIsikukood"
        })
        public static class Elukohaandmed {

            @XmlElement(name = "Elukohaandmed.Riigikood", required = true)
            protected String elukohaandmedRiigikood;
            @XmlElement(name = "Elukohaandmed.Riiginimetus", required = true)
            protected String elukohaandmedRiiginimetus;
            @XmlElement(name = "Elukohaandmed.MaakonnaKood", required = true)
            protected String elukohaandmedMaakonnaKood;
            @XmlElement(name = "Elukohaandmed.MaakonnaNimetus", required = true)
            protected String elukohaandmedMaakonnaNimetus;
            @XmlElement(name = "Elukohaandmed.VallaKood", required = true)
            protected String elukohaandmedVallaKood;
            @XmlElement(name = "Elukohaandmed.VallaNimetus", required = true)
            protected String elukohaandmedVallaNimetus;
            @XmlElement(name = "Elukohaandmed.KylaKood", required = true)
            protected String elukohaandmedKylaKood;
            @XmlElement(name = "Elukohaandmed.KylaNimetus", required = true)
            protected String elukohaandmedKylaNimetus;
            @XmlElement(name = "Elukohaandmed.Tanav", required = true)
            protected String elukohaandmedTanav;
            @XmlElement(name = "Elukohaandmed.Majanr", required = true)
            protected String elukohaandmedMajanr;
            @XmlElement(name = "Elukohaandmed.Korterinr", required = true)
            protected String elukohaandmedKorterinr;
            @XmlElement(name = "Elukohaandmed.AadressTekstina", required = true)
            protected String elukohaandmedAadressTekstina;
            @XmlElement(name = "Elukohaandmed.Postiindeks", required = true)
            protected String elukohaandmedPostiindeks;
            @XmlElement(name = "Elukohaandmed.Algusekuup", required = true)
            protected String elukohaandmedAlgusekuup;
            @XmlElement(name = "Elukohaandmed.Lopukuup", required = true)
            protected String elukohaandmedLopukuup;
            @XmlElement(name = "Elukohaandmed.AadressiLiik", required = true)
            protected String elukohaandmedAadressiLiik;
            @XmlElement(name = "Elukohaandmed.IsikuIsikukood", required = true)
            protected String elukohaandmedIsikuIsikukood;

            /**
             * Gets the value of the elukohaandmedRiigikood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedRiigikood() {
                return elukohaandmedRiigikood;
            }

            /**
             * Sets the value of the elukohaandmedRiigikood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedRiigikood(String value) {
                this.elukohaandmedRiigikood = value;
            }

            /**
             * Gets the value of the elukohaandmedRiiginimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedRiiginimetus() {
                return elukohaandmedRiiginimetus;
            }

            /**
             * Sets the value of the elukohaandmedRiiginimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedRiiginimetus(String value) {
                this.elukohaandmedRiiginimetus = value;
            }

            /**
             * Gets the value of the elukohaandmedMaakonnaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedMaakonnaKood() {
                return elukohaandmedMaakonnaKood;
            }

            /**
             * Sets the value of the elukohaandmedMaakonnaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedMaakonnaKood(String value) {
                this.elukohaandmedMaakonnaKood = value;
            }

            /**
             * Gets the value of the elukohaandmedMaakonnaNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedMaakonnaNimetus() {
                return elukohaandmedMaakonnaNimetus;
            }

            /**
             * Sets the value of the elukohaandmedMaakonnaNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedMaakonnaNimetus(String value) {
                this.elukohaandmedMaakonnaNimetus = value;
            }

            /**
             * Gets the value of the elukohaandmedVallaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedVallaKood() {
                return elukohaandmedVallaKood;
            }

            /**
             * Sets the value of the elukohaandmedVallaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedVallaKood(String value) {
                this.elukohaandmedVallaKood = value;
            }

            /**
             * Gets the value of the elukohaandmedVallaNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedVallaNimetus() {
                return elukohaandmedVallaNimetus;
            }

            /**
             * Sets the value of the elukohaandmedVallaNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedVallaNimetus(String value) {
                this.elukohaandmedVallaNimetus = value;
            }

            /**
             * Gets the value of the elukohaandmedKylaKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedKylaKood() {
                return elukohaandmedKylaKood;
            }

            /**
             * Sets the value of the elukohaandmedKylaKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedKylaKood(String value) {
                this.elukohaandmedKylaKood = value;
            }

            /**
             * Gets the value of the elukohaandmedKylaNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedKylaNimetus() {
                return elukohaandmedKylaNimetus;
            }

            /**
             * Sets the value of the elukohaandmedKylaNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedKylaNimetus(String value) {
                this.elukohaandmedKylaNimetus = value;
            }

            /**
             * Gets the value of the elukohaandmedTanav property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedTanav() {
                return elukohaandmedTanav;
            }

            /**
             * Sets the value of the elukohaandmedTanav property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedTanav(String value) {
                this.elukohaandmedTanav = value;
            }

            /**
             * Gets the value of the elukohaandmedMajanr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedMajanr() {
                return elukohaandmedMajanr;
            }

            /**
             * Sets the value of the elukohaandmedMajanr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedMajanr(String value) {
                this.elukohaandmedMajanr = value;
            }

            /**
             * Gets the value of the elukohaandmedKorterinr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedKorterinr() {
                return elukohaandmedKorterinr;
            }

            /**
             * Sets the value of the elukohaandmedKorterinr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedKorterinr(String value) {
                this.elukohaandmedKorterinr = value;
            }

            /**
             * Gets the value of the elukohaandmedAadressTekstina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedAadressTekstina() {
                return elukohaandmedAadressTekstina;
            }

            /**
             * Sets the value of the elukohaandmedAadressTekstina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedAadressTekstina(String value) {
                this.elukohaandmedAadressTekstina = value;
            }

            /**
             * Gets the value of the elukohaandmedPostiindeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedPostiindeks() {
                return elukohaandmedPostiindeks;
            }

            /**
             * Sets the value of the elukohaandmedPostiindeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedPostiindeks(String value) {
                this.elukohaandmedPostiindeks = value;
            }

            /**
             * Gets the value of the elukohaandmedAlgusekuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedAlgusekuup() {
                return elukohaandmedAlgusekuup;
            }

            /**
             * Sets the value of the elukohaandmedAlgusekuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedAlgusekuup(String value) {
                this.elukohaandmedAlgusekuup = value;
            }

            /**
             * Gets the value of the elukohaandmedLopukuup property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedLopukuup() {
                return elukohaandmedLopukuup;
            }

            /**
             * Sets the value of the elukohaandmedLopukuup property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedLopukuup(String value) {
                this.elukohaandmedLopukuup = value;
            }

            /**
             * Gets the value of the elukohaandmedAadressiLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedAadressiLiik() {
                return elukohaandmedAadressiLiik;
            }

            /**
             * Sets the value of the elukohaandmedAadressiLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedAadressiLiik(String value) {
                this.elukohaandmedAadressiLiik = value;
            }

            /**
             * Gets the value of the elukohaandmedIsikuIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedIsikuIsikukood() {
                return elukohaandmedIsikuIsikukood;
            }

            /**
             * Sets the value of the elukohaandmedIsikuIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedIsikuIsikukood(String value) {
                this.elukohaandmedIsikuIsikukood = value;
            }

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
     *         &lt;element name="Hooldusoigus" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Hooldusoigused.hoRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoSisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoTeineIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoTeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoTeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoOlekStat" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoPrimIsik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "hooldusoigus"
    })
    public static class Hooldusoigused {

        @XmlElement(name = "Hooldusoigus")
        protected List<RR96IsikDokElukSuheResponseType.Hooldusoigused.Hooldusoigus> hooldusoigus;

        /**
         * Gets the value of the hooldusoigus property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hooldusoigus property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHooldusoigus().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR96IsikDokElukSuheResponseType.Hooldusoigused.Hooldusoigus }
         * 
         * 
         */
        public List<RR96IsikDokElukSuheResponseType.Hooldusoigused.Hooldusoigus> getHooldusoigus() {
            if (hooldusoigus == null) {
                hooldusoigus = new ArrayList<RR96IsikDokElukSuheResponseType.Hooldusoigused.Hooldusoigus>();
            }
            return this.hooldusoigus;
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
         *         &lt;element name="Hooldusoigused.hoRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoSisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoTeineIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoTeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoTeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoOlekStat" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoPrimIsik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "hooldusoigusedHoRoll",
            "hooldusoigusedHoLiik",
            "hooldusoigusedHoSisu",
            "hooldusoigusedHoTeineIK",
            "hooldusoigusedHoTeineEesnimi",
            "hooldusoigusedHoTeinePerenimi",
            "hooldusoigusedHoOlekStat",
            "hooldusoigusedHoAlgus",
            "hooldusoigusedHoLopp",
            "hooldusoigusedHoPrimIsik"
        })
        public static class Hooldusoigus {

            @XmlElement(name = "Hooldusoigused.hoRoll", required = true)
            protected String hooldusoigusedHoRoll;
            @XmlElement(name = "Hooldusoigused.hoLiik", required = true)
            protected String hooldusoigusedHoLiik;
            @XmlElement(name = "Hooldusoigused.hoSisu", required = true)
            protected String hooldusoigusedHoSisu;
            @XmlElement(name = "Hooldusoigused.hoTeineIK", required = true)
            protected String hooldusoigusedHoTeineIK;
            @XmlElement(name = "Hooldusoigused.hoTeineEesnimi", required = true)
            protected String hooldusoigusedHoTeineEesnimi;
            @XmlElement(name = "Hooldusoigused.hoTeinePerenimi", required = true)
            protected String hooldusoigusedHoTeinePerenimi;
            @XmlElement(name = "Hooldusoigused.hoOlekStat", required = true)
            protected String hooldusoigusedHoOlekStat;
            @XmlElement(name = "Hooldusoigused.hoAlgus", required = true)
            protected String hooldusoigusedHoAlgus;
            @XmlElement(name = "Hooldusoigused.hoLopp", required = true)
            protected String hooldusoigusedHoLopp;
            @XmlElement(name = "Hooldusoigused.hoPrimIsik", required = true)
            protected String hooldusoigusedHoPrimIsik;

            /**
             * Gets the value of the hooldusoigusedHoRoll property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoRoll() {
                return hooldusoigusedHoRoll;
            }

            /**
             * Sets the value of the hooldusoigusedHoRoll property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoRoll(String value) {
                this.hooldusoigusedHoRoll = value;
            }

            /**
             * Gets the value of the hooldusoigusedHoLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoLiik() {
                return hooldusoigusedHoLiik;
            }

            /**
             * Sets the value of the hooldusoigusedHoLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoLiik(String value) {
                this.hooldusoigusedHoLiik = value;
            }

            /**
             * Gets the value of the hooldusoigusedHoSisu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoSisu() {
                return hooldusoigusedHoSisu;
            }

            /**
             * Sets the value of the hooldusoigusedHoSisu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoSisu(String value) {
                this.hooldusoigusedHoSisu = value;
            }

            /**
             * Gets the value of the hooldusoigusedHoTeineIK property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoTeineIK() {
                return hooldusoigusedHoTeineIK;
            }

            /**
             * Sets the value of the hooldusoigusedHoTeineIK property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoTeineIK(String value) {
                this.hooldusoigusedHoTeineIK = value;
            }

            /**
             * Gets the value of the hooldusoigusedHoTeineEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoTeineEesnimi() {
                return hooldusoigusedHoTeineEesnimi;
            }

            /**
             * Sets the value of the hooldusoigusedHoTeineEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoTeineEesnimi(String value) {
                this.hooldusoigusedHoTeineEesnimi = value;
            }

            /**
             * Gets the value of the hooldusoigusedHoTeinePerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoTeinePerenimi() {
                return hooldusoigusedHoTeinePerenimi;
            }

            /**
             * Sets the value of the hooldusoigusedHoTeinePerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoTeinePerenimi(String value) {
                this.hooldusoigusedHoTeinePerenimi = value;
            }

            /**
             * Gets the value of the hooldusoigusedHoOlekStat property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoOlekStat() {
                return hooldusoigusedHoOlekStat;
            }

            /**
             * Sets the value of the hooldusoigusedHoOlekStat property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoOlekStat(String value) {
                this.hooldusoigusedHoOlekStat = value;
            }

            /**
             * Gets the value of the hooldusoigusedHoAlgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoAlgus() {
                return hooldusoigusedHoAlgus;
            }

            /**
             * Sets the value of the hooldusoigusedHoAlgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoAlgus(String value) {
                this.hooldusoigusedHoAlgus = value;
            }

            /**
             * Gets the value of the hooldusoigusedHoLopp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoLopp() {
                return hooldusoigusedHoLopp;
            }

            /**
             * Sets the value of the hooldusoigusedHoLopp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoLopp(String value) {
                this.hooldusoigusedHoLopp = value;
            }

            /**
             * Gets the value of the hooldusoigusedHoPrimIsik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoPrimIsik() {
                return hooldusoigusedHoPrimIsik;
            }

            /**
             * Sets the value of the hooldusoigusedHoPrimIsik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoPrimIsik(String value) {
                this.hooldusoigusedHoPrimIsik = value;
            }

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
     *         &lt;element name="Isikuandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isikuandmed.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Synniperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Kodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Perekonnaseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.ElukRegpe" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SaabEestisse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.ViimatiPar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.IsikuSeisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Andmed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKRIIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKmaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKvald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EKasula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Amet" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Eriala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.KoviSaabus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Syriikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Syriiginimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.ElukAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR96IsikDokElukSuheResponseType.Isikud.Isikuandmed> isikuandmed;

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
         * {@link RR96IsikDokElukSuheResponseType.Isikud.Isikuandmed }
         * 
         * 
         */
        public List<RR96IsikDokElukSuheResponseType.Isikud.Isikuandmed> getIsikuandmed() {
            if (isikuandmed == null) {
                isikuandmed = new ArrayList<RR96IsikDokElukSuheResponseType.Isikud.Isikuandmed>();
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
         *         &lt;element name="Isikuandmed.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Synniperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Kodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Perekonnaseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.ElukRegpe" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SaabEestisse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.ViimatiPar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.IsikuSeisund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Andmed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKRIIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKmaakond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKvald" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EKasula" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Amet" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Eriala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.KoviSaabus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Syriikkd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Syriiginimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.ElukAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikuandmedMeesnm",
            "isikuandmedPerenimi",
            "isikuandmedMperenm",
            "isikuandmedSynniperenimi",
            "isikuandmedSugu",
            "isikuandmedIsikuStaatus",
            "isikuandmedSynniaeg",
            "isikuandmedSurmaaeg",
            "isikuandmedKodakondsuskood",
            "isikuandmedKodakondsustekstina",
            "isikuandmedSynnikoht",
            "isikuandmedPerekonnaseis",
            "isikuandmedElukRegpe",
            "isikuandmedTeovoime",
            "isikuandmedKirjeStaatus",
            "isikuandmedSaabEestisse",
            "isikuandmedViimatiPar",
            "isikuandmedIsikuSeisund",
            "isikuandmedAndmed",
            "isikuandmedEmakeel",
            "isikuandmedRahvus",
            "isikuandmedHaridus",
            "isikuandmedEKRIIK",
            "isikuandmedEKmaakond",
            "isikuandmedEKvald",
            "isikuandmedEKasula",
            "isikuandmedTegevusala",
            "isikuandmedAmet",
            "isikuandmedEriala",
            "isikuandmedIsanimi",
            "isikuandmedKoviSaabus",
            "isikuandmedSyriikkd",
            "isikuandmedSyriiginimi",
            "isikuandmedElukAlates"
        })
        public static class Isikuandmed {

            @XmlElement(name = "Isikuandmed.Isikukood", required = true)
            protected String isikuandmedIsikukood;
            @XmlElement(name = "Isikuandmed.Eesnimi", required = true)
            protected String isikuandmedEesnimi;
            @XmlElement(name = "Isikuandmed.Meesnm", required = true)
            protected String isikuandmedMeesnm;
            @XmlElement(name = "Isikuandmed.Perenimi", required = true)
            protected String isikuandmedPerenimi;
            @XmlElement(name = "Isikuandmed.Mperenm", required = true)
            protected String isikuandmedMperenm;
            @XmlElement(name = "Isikuandmed.Synniperenimi", required = true)
            protected String isikuandmedSynniperenimi;
            @XmlElement(name = "Isikuandmed.Sugu", required = true)
            protected String isikuandmedSugu;
            @XmlElement(name = "Isikuandmed.IsikuStaatus", required = true)
            protected String isikuandmedIsikuStaatus;
            @XmlElement(name = "Isikuandmed.Synniaeg", required = true)
            protected String isikuandmedSynniaeg;
            @XmlElement(name = "Isikuandmed.Surmaaeg", required = true)
            protected String isikuandmedSurmaaeg;
            @XmlElement(name = "Isikuandmed.Kodakondsuskood", required = true)
            protected String isikuandmedKodakondsuskood;
            @XmlElement(name = "Isikuandmed.Kodakondsustekstina", required = true)
            protected String isikuandmedKodakondsustekstina;
            @XmlElement(name = "Isikuandmed.Synnikoht", required = true)
            protected String isikuandmedSynnikoht;
            @XmlElement(name = "Isikuandmed.Perekonnaseis", required = true)
            protected String isikuandmedPerekonnaseis;
            @XmlElement(name = "Isikuandmed.ElukRegpe", required = true)
            protected String isikuandmedElukRegpe;
            @XmlElement(name = "Isikuandmed.Teovoime", required = true)
            protected String isikuandmedTeovoime;
            @XmlElement(name = "Isikuandmed.KirjeStaatus", required = true)
            protected String isikuandmedKirjeStaatus;
            @XmlElement(name = "Isikuandmed.SaabEestisse", required = true)
            protected String isikuandmedSaabEestisse;
            @XmlElement(name = "Isikuandmed.ViimatiPar", required = true)
            protected String isikuandmedViimatiPar;
            @XmlElement(name = "Isikuandmed.IsikuSeisund", required = true)
            protected String isikuandmedIsikuSeisund;
            @XmlElement(name = "Isikuandmed.Andmed", required = true)
            protected String isikuandmedAndmed;
            @XmlElement(name = "Isikuandmed.Emakeel", required = true)
            protected String isikuandmedEmakeel;
            @XmlElement(name = "Isikuandmed.Rahvus", required = true)
            protected String isikuandmedRahvus;
            @XmlElement(name = "Isikuandmed.Haridus", required = true)
            protected String isikuandmedHaridus;
            @XmlElement(name = "Isikuandmed.EKRIIK", required = true)
            protected String isikuandmedEKRIIK;
            @XmlElement(name = "Isikuandmed.EKmaakond", required = true)
            protected String isikuandmedEKmaakond;
            @XmlElement(name = "Isikuandmed.EKvald", required = true)
            protected String isikuandmedEKvald;
            @XmlElement(name = "Isikuandmed.EKasula", required = true)
            protected String isikuandmedEKasula;
            @XmlElement(name = "Isikuandmed.Tegevusala", required = true)
            protected String isikuandmedTegevusala;
            @XmlElement(name = "Isikuandmed.Amet", required = true)
            protected String isikuandmedAmet;
            @XmlElement(name = "Isikuandmed.Eriala", required = true)
            protected String isikuandmedEriala;
            @XmlElement(name = "Isikuandmed.Isanimi", required = true)
            protected String isikuandmedIsanimi;
            @XmlElement(name = "Isikuandmed.KoviSaabus", required = true)
            protected String isikuandmedKoviSaabus;
            @XmlElement(name = "Isikuandmed.Syriikkd", required = true)
            protected String isikuandmedSyriikkd;
            @XmlElement(name = "Isikuandmed.Syriiginimi", required = true)
            protected String isikuandmedSyriiginimi;
            @XmlElement(name = "Isikuandmed.ElukAlates", required = true)
            protected String isikuandmedElukAlates;

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
             * Gets the value of the isikuandmedMeesnm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedMeesnm() {
                return isikuandmedMeesnm;
            }

            /**
             * Sets the value of the isikuandmedMeesnm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedMeesnm(String value) {
                this.isikuandmedMeesnm = value;
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
             * Gets the value of the isikuandmedMperenm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedMperenm() {
                return isikuandmedMperenm;
            }

            /**
             * Sets the value of the isikuandmedMperenm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedMperenm(String value) {
                this.isikuandmedMperenm = value;
            }

            /**
             * Gets the value of the isikuandmedSynniperenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSynniperenimi() {
                return isikuandmedSynniperenimi;
            }

            /**
             * Sets the value of the isikuandmedSynniperenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSynniperenimi(String value) {
                this.isikuandmedSynniperenimi = value;
            }

            /**
             * Gets the value of the isikuandmedSugu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSugu() {
                return isikuandmedSugu;
            }

            /**
             * Sets the value of the isikuandmedSugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSugu(String value) {
                this.isikuandmedSugu = value;
            }

            /**
             * Gets the value of the isikuandmedIsikuStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedIsikuStaatus() {
                return isikuandmedIsikuStaatus;
            }

            /**
             * Sets the value of the isikuandmedIsikuStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedIsikuStaatus(String value) {
                this.isikuandmedIsikuStaatus = value;
            }

            /**
             * Gets the value of the isikuandmedSynniaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSynniaeg() {
                return isikuandmedSynniaeg;
            }

            /**
             * Sets the value of the isikuandmedSynniaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSynniaeg(String value) {
                this.isikuandmedSynniaeg = value;
            }

            /**
             * Gets the value of the isikuandmedSurmaaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSurmaaeg() {
                return isikuandmedSurmaaeg;
            }

            /**
             * Sets the value of the isikuandmedSurmaaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSurmaaeg(String value) {
                this.isikuandmedSurmaaeg = value;
            }

            /**
             * Gets the value of the isikuandmedKodakondsuskood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedKodakondsuskood() {
                return isikuandmedKodakondsuskood;
            }

            /**
             * Sets the value of the isikuandmedKodakondsuskood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedKodakondsuskood(String value) {
                this.isikuandmedKodakondsuskood = value;
            }

            /**
             * Gets the value of the isikuandmedKodakondsustekstina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedKodakondsustekstina() {
                return isikuandmedKodakondsustekstina;
            }

            /**
             * Sets the value of the isikuandmedKodakondsustekstina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedKodakondsustekstina(String value) {
                this.isikuandmedKodakondsustekstina = value;
            }

            /**
             * Gets the value of the isikuandmedSynnikoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSynnikoht() {
                return isikuandmedSynnikoht;
            }

            /**
             * Sets the value of the isikuandmedSynnikoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSynnikoht(String value) {
                this.isikuandmedSynnikoht = value;
            }

            /**
             * Gets the value of the isikuandmedPerekonnaseis property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedPerekonnaseis() {
                return isikuandmedPerekonnaseis;
            }

            /**
             * Sets the value of the isikuandmedPerekonnaseis property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedPerekonnaseis(String value) {
                this.isikuandmedPerekonnaseis = value;
            }

            /**
             * Gets the value of the isikuandmedElukRegpe property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedElukRegpe() {
                return isikuandmedElukRegpe;
            }

            /**
             * Sets the value of the isikuandmedElukRegpe property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedElukRegpe(String value) {
                this.isikuandmedElukRegpe = value;
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
             * Gets the value of the isikuandmedKirjeStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedKirjeStaatus() {
                return isikuandmedKirjeStaatus;
            }

            /**
             * Sets the value of the isikuandmedKirjeStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedKirjeStaatus(String value) {
                this.isikuandmedKirjeStaatus = value;
            }

            /**
             * Gets the value of the isikuandmedSaabEestisse property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSaabEestisse() {
                return isikuandmedSaabEestisse;
            }

            /**
             * Sets the value of the isikuandmedSaabEestisse property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSaabEestisse(String value) {
                this.isikuandmedSaabEestisse = value;
            }

            /**
             * Gets the value of the isikuandmedViimatiPar property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedViimatiPar() {
                return isikuandmedViimatiPar;
            }

            /**
             * Sets the value of the isikuandmedViimatiPar property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedViimatiPar(String value) {
                this.isikuandmedViimatiPar = value;
            }

            /**
             * Gets the value of the isikuandmedIsikuSeisund property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedIsikuSeisund() {
                return isikuandmedIsikuSeisund;
            }

            /**
             * Sets the value of the isikuandmedIsikuSeisund property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedIsikuSeisund(String value) {
                this.isikuandmedIsikuSeisund = value;
            }

            /**
             * Gets the value of the isikuandmedAndmed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedAndmed() {
                return isikuandmedAndmed;
            }

            /**
             * Sets the value of the isikuandmedAndmed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedAndmed(String value) {
                this.isikuandmedAndmed = value;
            }

            /**
             * Gets the value of the isikuandmedEmakeel property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEmakeel() {
                return isikuandmedEmakeel;
            }

            /**
             * Sets the value of the isikuandmedEmakeel property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEmakeel(String value) {
                this.isikuandmedEmakeel = value;
            }

            /**
             * Gets the value of the isikuandmedRahvus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedRahvus() {
                return isikuandmedRahvus;
            }

            /**
             * Sets the value of the isikuandmedRahvus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedRahvus(String value) {
                this.isikuandmedRahvus = value;
            }

            /**
             * Gets the value of the isikuandmedHaridus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedHaridus() {
                return isikuandmedHaridus;
            }

            /**
             * Sets the value of the isikuandmedHaridus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedHaridus(String value) {
                this.isikuandmedHaridus = value;
            }

            /**
             * Gets the value of the isikuandmedEKRIIK property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKRIIK() {
                return isikuandmedEKRIIK;
            }

            /**
             * Sets the value of the isikuandmedEKRIIK property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKRIIK(String value) {
                this.isikuandmedEKRIIK = value;
            }

            /**
             * Gets the value of the isikuandmedEKmaakond property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKmaakond() {
                return isikuandmedEKmaakond;
            }

            /**
             * Sets the value of the isikuandmedEKmaakond property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKmaakond(String value) {
                this.isikuandmedEKmaakond = value;
            }

            /**
             * Gets the value of the isikuandmedEKvald property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKvald() {
                return isikuandmedEKvald;
            }

            /**
             * Sets the value of the isikuandmedEKvald property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKvald(String value) {
                this.isikuandmedEKvald = value;
            }

            /**
             * Gets the value of the isikuandmedEKasula property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEKasula() {
                return isikuandmedEKasula;
            }

            /**
             * Sets the value of the isikuandmedEKasula property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEKasula(String value) {
                this.isikuandmedEKasula = value;
            }

            /**
             * Gets the value of the isikuandmedTegevusala property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedTegevusala() {
                return isikuandmedTegevusala;
            }

            /**
             * Sets the value of the isikuandmedTegevusala property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedTegevusala(String value) {
                this.isikuandmedTegevusala = value;
            }

            /**
             * Gets the value of the isikuandmedAmet property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedAmet() {
                return isikuandmedAmet;
            }

            /**
             * Sets the value of the isikuandmedAmet property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedAmet(String value) {
                this.isikuandmedAmet = value;
            }

            /**
             * Gets the value of the isikuandmedEriala property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEriala() {
                return isikuandmedEriala;
            }

            /**
             * Sets the value of the isikuandmedEriala property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEriala(String value) {
                this.isikuandmedEriala = value;
            }

            /**
             * Gets the value of the isikuandmedIsanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedIsanimi() {
                return isikuandmedIsanimi;
            }

            /**
             * Sets the value of the isikuandmedIsanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedIsanimi(String value) {
                this.isikuandmedIsanimi = value;
            }

            /**
             * Gets the value of the isikuandmedKoviSaabus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedKoviSaabus() {
                return isikuandmedKoviSaabus;
            }

            /**
             * Sets the value of the isikuandmedKoviSaabus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedKoviSaabus(String value) {
                this.isikuandmedKoviSaabus = value;
            }

            /**
             * Gets the value of the isikuandmedSyriikkd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSyriikkd() {
                return isikuandmedSyriikkd;
            }

            /**
             * Sets the value of the isikuandmedSyriikkd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSyriikkd(String value) {
                this.isikuandmedSyriikkd = value;
            }

            /**
             * Gets the value of the isikuandmedSyriiginimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSyriiginimi() {
                return isikuandmedSyriiginimi;
            }

            /**
             * Sets the value of the isikuandmedSyriiginimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSyriiginimi(String value) {
                this.isikuandmedSyriiginimi = value;
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
     *         &lt;element name="Kontaktandmed" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Kontaktandmed.Sideaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Kontaktandmed.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Kontaktandmed.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Kontaktandmed.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Kontaktandmed.Lopukp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Kontaktandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "kontaktandmed"
    })
    public static class Kontaktid {

        @XmlElement(name = "Kontaktandmed")
        protected List<RR96IsikDokElukSuheResponseType.Kontaktid.Kontaktandmed> kontaktandmed;

        /**
         * Gets the value of the kontaktandmed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kontaktandmed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKontaktandmed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR96IsikDokElukSuheResponseType.Kontaktid.Kontaktandmed }
         * 
         * 
         */
        public List<RR96IsikDokElukSuheResponseType.Kontaktid.Kontaktandmed> getKontaktandmed() {
            if (kontaktandmed == null) {
                kontaktandmed = new ArrayList<RR96IsikDokElukSuheResponseType.Kontaktid.Kontaktandmed>();
            }
            return this.kontaktandmed;
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
         *         &lt;element name="Kontaktandmed.Sideaadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Kontaktandmed.Liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Kontaktandmed.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Kontaktandmed.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Kontaktandmed.Lopukp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Kontaktandmed.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "kontaktandmedSideaadress",
            "kontaktandmedLiik",
            "kontaktandmedStaatus",
            "kontaktandmedAlguskp",
            "kontaktandmedLopukp",
            "kontaktandmedIsikuIsikukood"
        })
        public static class Kontaktandmed {

            @XmlElement(name = "Kontaktandmed.Sideaadress", required = true)
            protected String kontaktandmedSideaadress;
            @XmlElement(name = "Kontaktandmed.Liik", required = true)
            protected String kontaktandmedLiik;
            @XmlElement(name = "Kontaktandmed.Staatus", required = true)
            protected String kontaktandmedStaatus;
            @XmlElement(name = "Kontaktandmed.Alguskp", required = true)
            protected String kontaktandmedAlguskp;
            @XmlElement(name = "Kontaktandmed.Lopukp", required = true)
            protected String kontaktandmedLopukp;
            @XmlElement(name = "Kontaktandmed.IsikuIsikukood", required = true)
            protected String kontaktandmedIsikuIsikukood;

            /**
             * Gets the value of the kontaktandmedSideaadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktandmedSideaadress() {
                return kontaktandmedSideaadress;
            }

            /**
             * Sets the value of the kontaktandmedSideaadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktandmedSideaadress(String value) {
                this.kontaktandmedSideaadress = value;
            }

            /**
             * Gets the value of the kontaktandmedLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktandmedLiik() {
                return kontaktandmedLiik;
            }

            /**
             * Sets the value of the kontaktandmedLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktandmedLiik(String value) {
                this.kontaktandmedLiik = value;
            }

            /**
             * Gets the value of the kontaktandmedStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktandmedStaatus() {
                return kontaktandmedStaatus;
            }

            /**
             * Sets the value of the kontaktandmedStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktandmedStaatus(String value) {
                this.kontaktandmedStaatus = value;
            }

            /**
             * Gets the value of the kontaktandmedAlguskp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktandmedAlguskp() {
                return kontaktandmedAlguskp;
            }

            /**
             * Sets the value of the kontaktandmedAlguskp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktandmedAlguskp(String value) {
                this.kontaktandmedAlguskp = value;
            }

            /**
             * Gets the value of the kontaktandmedLopukp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktandmedLopukp() {
                return kontaktandmedLopukp;
            }

            /**
             * Sets the value of the kontaktandmedLopukp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktandmedLopukp(String value) {
                this.kontaktandmedLopukp = value;
            }

            /**
             * Gets the value of the kontaktandmedIsikuIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktandmedIsikuIsikukood() {
                return kontaktandmedIsikuIsikukood;
            }

            /**
             * Sets the value of the kontaktandmedIsikuIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktandmedIsikuIsikukood(String value) {
                this.kontaktandmedIsikuIsikukood = value;
            }

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
     *         &lt;element name="Suhe" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Lopukp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "suhe"
    })
    public static class Suhted {

        @XmlElement(name = "Suhe")
        protected List<RR96IsikDokElukSuheResponseType.Suhted.Suhe> suhe;

        /**
         * Gets the value of the suhe property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the suhe property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSuhe().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR96IsikDokElukSuheResponseType.Suhted.Suhe }
         * 
         * 
         */
        public List<RR96IsikDokElukSuheResponseType.Suhted.Suhe> getSuhe() {
            if (suhe == null) {
                suhe = new ArrayList<RR96IsikDokElukSuheResponseType.Suhted.Suhe>();
            }
            return this.suhe;
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
         *         &lt;element name="Suhe.Suhtetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Eesti_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Lopukp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "suheSuhtetyyp",
            "suheIsikukood",
            "suhePerenimi",
            "suheEesnimi",
            "suheIsanimi",
            "suheEestiAadress",
            "suheSynnikoht",
            "suheIsikuStaatus",
            "suheStaatus",
            "suheAlguskp",
            "suheLopukp",
            "suheIsikuIsikukood"
        })
        public static class Suhe {

            @XmlElement(name = "Suhe.Suhtetyyp", required = true)
            protected String suheSuhtetyyp;
            @XmlElement(name = "Suhe.Isikukood", required = true)
            protected String suheIsikukood;
            @XmlElement(name = "Suhe.Perenimi", required = true)
            protected String suhePerenimi;
            @XmlElement(name = "Suhe.Eesnimi", required = true)
            protected String suheEesnimi;
            @XmlElement(name = "Suhe.Isanimi", required = true)
            protected String suheIsanimi;
            @XmlElement(name = "Suhe.Eesti_aadress", required = true)
            protected String suheEestiAadress;
            @XmlElement(name = "Suhe.Synnikoht", required = true)
            protected String suheSynnikoht;
            @XmlElement(name = "Suhe.IsikuStaatus", required = true)
            protected String suheIsikuStaatus;
            @XmlElement(name = "Suhe.Staatus", required = true)
            protected String suheStaatus;
            @XmlElement(name = "Suhe.Alguskp", required = true)
            protected String suheAlguskp;
            @XmlElement(name = "Suhe.Lopukp", required = true)
            protected String suheLopukp;
            @XmlElement(name = "Suhe.IsikuIsikukood", required = true)
            protected String suheIsikuIsikukood;

            /**
             * Gets the value of the suheSuhtetyyp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSuhtetyyp() {
                return suheSuhtetyyp;
            }

            /**
             * Sets the value of the suheSuhtetyyp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSuhtetyyp(String value) {
                this.suheSuhtetyyp = value;
            }

            /**
             * Gets the value of the suheIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheIsikukood() {
                return suheIsikukood;
            }

            /**
             * Sets the value of the suheIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheIsikukood(String value) {
                this.suheIsikukood = value;
            }

            /**
             * Gets the value of the suhePerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuhePerenimi() {
                return suhePerenimi;
            }

            /**
             * Sets the value of the suhePerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuhePerenimi(String value) {
                this.suhePerenimi = value;
            }

            /**
             * Gets the value of the suheEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheEesnimi() {
                return suheEesnimi;
            }

            /**
             * Sets the value of the suheEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheEesnimi(String value) {
                this.suheEesnimi = value;
            }

            /**
             * Gets the value of the suheIsanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheIsanimi() {
                return suheIsanimi;
            }

            /**
             * Sets the value of the suheIsanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheIsanimi(String value) {
                this.suheIsanimi = value;
            }

            /**
             * Gets the value of the suheEestiAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheEestiAadress() {
                return suheEestiAadress;
            }

            /**
             * Sets the value of the suheEestiAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheEestiAadress(String value) {
                this.suheEestiAadress = value;
            }

            /**
             * Gets the value of the suheSynnikoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSynnikoht() {
                return suheSynnikoht;
            }

            /**
             * Sets the value of the suheSynnikoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSynnikoht(String value) {
                this.suheSynnikoht = value;
            }

            /**
             * Gets the value of the suheIsikuStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheIsikuStaatus() {
                return suheIsikuStaatus;
            }

            /**
             * Sets the value of the suheIsikuStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheIsikuStaatus(String value) {
                this.suheIsikuStaatus = value;
            }

            /**
             * Gets the value of the suheStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheStaatus() {
                return suheStaatus;
            }

            /**
             * Sets the value of the suheStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheStaatus(String value) {
                this.suheStaatus = value;
            }

            /**
             * Gets the value of the suheAlguskp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheAlguskp() {
                return suheAlguskp;
            }

            /**
             * Sets the value of the suheAlguskp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheAlguskp(String value) {
                this.suheAlguskp = value;
            }

            /**
             * Gets the value of the suheLopukp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheLopukp() {
                return suheLopukp;
            }

            /**
             * Sets the value of the suheLopukp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheLopukp(String value) {
                this.suheLopukp = value;
            }

            /**
             * Gets the value of the suheIsikuIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheIsikuIsikukood() {
                return suheIsikuIsikukood;
            }

            /**
             * Sets the value of the suheIsikuIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheIsikuIsikukood(String value) {
                this.suheIsikuIsikukood = value;
            }

        }

    }

}
