
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR456ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR456ResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://rr.x-road.eu/producer}XRoadResponseBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Veakood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Veatekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Isikud"&gt;
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
 *                             &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.MuudEesnimed" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Isik.MuudEesnimed.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Isik.MuudPerenimed" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Isik.MuudPerenimed.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Isik.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.IsikuStaatuseMuutumine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Isik.Surmaaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Isik.SynniRiigiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SynniRiigiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.IsikKoda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ValisriigiIsikukoodid"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Isik.ValisriigiIsikukood" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Isik.ValisriigiIsikukood.VrRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.ValisriigiIsikukood.VrIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="Isik.SaabEestisse" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Isik.LahkusEestist" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Isik.SaabKOVi" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Isik.Elamisluba" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ElamisloaTahtaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Isik.ViimatiPar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.RiigiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.RiigiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.MaakonnaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.MaakonnaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.VallaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.VallaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.KylaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.KylaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.VaikekohaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.VaikekohaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.TanavaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.TanavaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.NimiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.NimiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="Isik.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="Isik.ElukohtLoodi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.ElukohtMuudeti" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideRiigiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideRiigiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideMaakonnaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideMaakonnaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideVallaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideVallaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideKylaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideKylaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideVaikekohaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideVaikekohaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideTanavaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideTanavaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideNimiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideNimiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideMajanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideKorternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SidePostiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="Isik.SideADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isik.SideADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="Isik.Kontaktid" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="Isik.Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="Isik.Kontakt.KontaktiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="Isik.Kontakt.KontaktiTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
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
 *                   &lt;element name="Dok" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Dok.IsikIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.DokOsalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dok.KehtivAlates" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Dok.KehtivKuni" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Dok.ValjastamisKp" type="{http://rr.x-road.eu/producer}date"/&gt;
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
 *                             &lt;element name="Suhe.Suhetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.AsutusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.AsutusRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Alguskp" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Suhe.Lopukp" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Suhe.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.AlusDokKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.LoppAlusDokKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Loodi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Muudeti" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                             &lt;element name="Hooldusoigus.hoRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigus.hoLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigus.hoOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigus.hoSisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigus.hoAlgus" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Hooldusoigus.hoLopp" type="{http://rr.x-road.eu/producer}date"/&gt;
 *                             &lt;element name="Hooldusoigus.hoTeineIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigus.hoTeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigus.hoTeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigus.hoAsutusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigus.hoAsutusRegNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigus.hoPrimIsik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigus.hoLoodi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigus.hoMuudeti" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR456ResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikud",
    "dokumendid",
    "suhted",
    "hooldusoigused"
})
public class RR456ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood", required = true)
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst", required = true)
    protected String veatekst;
    @XmlElement(name = "Isikud", required = true)
    protected RR456ResponseType.Isikud isikud;
    @XmlElement(name = "Dokumendid", required = true)
    protected RR456ResponseType.Dokumendid dokumendid;
    @XmlElement(name = "Suhted", required = true)
    protected RR456ResponseType.Suhted suhted;
    @XmlElement(name = "Hooldusoigused", required = true)
    protected RR456ResponseType.Hooldusoigused hooldusoigused;

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
     *     {@link RR456ResponseType.Isikud }
     *     
     */
    public RR456ResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR456ResponseType.Isikud }
     *     
     */
    public void setIsikud(RR456ResponseType.Isikud value) {
        this.isikud = value;
    }

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RR456ResponseType.Dokumendid }
     *     
     */
    public RR456ResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR456ResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RR456ResponseType.Dokumendid value) {
        this.dokumendid = value;
    }

    /**
     * Gets the value of the suhted property.
     * 
     * @return
     *     possible object is
     *     {@link RR456ResponseType.Suhted }
     *     
     */
    public RR456ResponseType.Suhted getSuhted() {
        return suhted;
    }

    /**
     * Sets the value of the suhted property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR456ResponseType.Suhted }
     *     
     */
    public void setSuhted(RR456ResponseType.Suhted value) {
        this.suhted = value;
    }

    /**
     * Gets the value of the hooldusoigused property.
     * 
     * @return
     *     possible object is
     *     {@link RR456ResponseType.Hooldusoigused }
     *     
     */
    public RR456ResponseType.Hooldusoigused getHooldusoigused() {
        return hooldusoigused;
    }

    /**
     * Sets the value of the hooldusoigused property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR456ResponseType.Hooldusoigused }
     *     
     */
    public void setHooldusoigused(RR456ResponseType.Hooldusoigused value) {
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
     *         &lt;element name="Dok" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Dok.IsikIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.DokOsalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dok.KehtivAlates" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Dok.KehtivKuni" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Dok.ValjastamisKp" type="{http://rr.x-road.eu/producer}date"/&gt;
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
        "dok"
    })
    public static class Dokumendid {

        @XmlElement(name = "Dok")
        protected List<RR456ResponseType.Dokumendid.Dok> dok;

        /**
         * Gets the value of the dok property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dok property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDok().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR456ResponseType.Dokumendid.Dok }
         * 
         * 
         */
        public List<RR456ResponseType.Dokumendid.Dok> getDok() {
            if (dok == null) {
                dok = new ArrayList<RR456ResponseType.Dokumendid.Dok>();
            }
            return this.dok;
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
         *         &lt;element name="Dok.IsikIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.DokOsalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Seeria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dok.KehtivAlates" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Dok.KehtivKuni" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Dok.ValjastamisKp" type="{http://rr.x-road.eu/producer}date"/&gt;
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
            "dokIsikIsikukood",
            "dokDokIsikukood",
            "dokDokEesnimi",
            "dokDokPerenimi",
            "dokDokOsalus",
            "dokKood",
            "dokSeeria",
            "dokNumber",
            "dokStaatus",
            "dokKehtivAlates",
            "dokKehtivKuni",
            "dokValjastamisKp"
        })
        public static class Dok {

            @XmlElement(name = "Dok.IsikIsikukood", required = true)
            protected String dokIsikIsikukood;
            @XmlElement(name = "Dok.DokIsikukood", required = true)
            protected String dokDokIsikukood;
            @XmlElement(name = "Dok.DokEesnimi", required = true)
            protected String dokDokEesnimi;
            @XmlElement(name = "Dok.DokPerenimi", required = true)
            protected String dokDokPerenimi;
            @XmlElement(name = "Dok.DokOsalus", required = true)
            protected String dokDokOsalus;
            @XmlElement(name = "Dok.Kood", required = true)
            protected String dokKood;
            @XmlElement(name = "Dok.Seeria", required = true)
            protected String dokSeeria;
            @XmlElement(name = "Dok.Number", required = true)
            protected String dokNumber;
            @XmlElement(name = "Dok.Staatus", required = true)
            protected String dokStaatus;
            @XmlElement(name = "Dok.KehtivAlates", required = true)
            protected String dokKehtivAlates;
            @XmlElement(name = "Dok.KehtivKuni", required = true)
            protected String dokKehtivKuni;
            @XmlElement(name = "Dok.ValjastamisKp", required = true)
            protected String dokValjastamisKp;

            /**
             * Gets the value of the dokIsikIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokIsikIsikukood() {
                return dokIsikIsikukood;
            }

            /**
             * Sets the value of the dokIsikIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokIsikIsikukood(String value) {
                this.dokIsikIsikukood = value;
            }

            /**
             * Gets the value of the dokDokIsikukood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokIsikukood() {
                return dokDokIsikukood;
            }

            /**
             * Sets the value of the dokDokIsikukood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokIsikukood(String value) {
                this.dokDokIsikukood = value;
            }

            /**
             * Gets the value of the dokDokEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokEesnimi() {
                return dokDokEesnimi;
            }

            /**
             * Sets the value of the dokDokEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokEesnimi(String value) {
                this.dokDokEesnimi = value;
            }

            /**
             * Gets the value of the dokDokPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokPerenimi() {
                return dokDokPerenimi;
            }

            /**
             * Sets the value of the dokDokPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokPerenimi(String value) {
                this.dokDokPerenimi = value;
            }

            /**
             * Gets the value of the dokDokOsalus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokDokOsalus() {
                return dokDokOsalus;
            }

            /**
             * Sets the value of the dokDokOsalus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokDokOsalus(String value) {
                this.dokDokOsalus = value;
            }

            /**
             * Gets the value of the dokKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokKood() {
                return dokKood;
            }

            /**
             * Sets the value of the dokKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokKood(String value) {
                this.dokKood = value;
            }

            /**
             * Gets the value of the dokSeeria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokSeeria() {
                return dokSeeria;
            }

            /**
             * Sets the value of the dokSeeria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokSeeria(String value) {
                this.dokSeeria = value;
            }

            /**
             * Gets the value of the dokNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokNumber() {
                return dokNumber;
            }

            /**
             * Sets the value of the dokNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokNumber(String value) {
                this.dokNumber = value;
            }

            /**
             * Gets the value of the dokStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokStaatus() {
                return dokStaatus;
            }

            /**
             * Sets the value of the dokStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokStaatus(String value) {
                this.dokStaatus = value;
            }

            /**
             * Gets the value of the dokKehtivAlates property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokKehtivAlates() {
                return dokKehtivAlates;
            }

            /**
             * Sets the value of the dokKehtivAlates property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokKehtivAlates(String value) {
                this.dokKehtivAlates = value;
            }

            /**
             * Gets the value of the dokKehtivKuni property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokKehtivKuni() {
                return dokKehtivKuni;
            }

            /**
             * Sets the value of the dokKehtivKuni property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokKehtivKuni(String value) {
                this.dokKehtivKuni = value;
            }

            /**
             * Gets the value of the dokValjastamisKp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokValjastamisKp() {
                return dokValjastamisKp;
            }

            /**
             * Sets the value of the dokValjastamisKp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokValjastamisKp(String value) {
                this.dokValjastamisKp = value;
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
     *                   &lt;element name="Hooldusoigus.hoRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigus.hoLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigus.hoOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigus.hoSisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigus.hoAlgus" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Hooldusoigus.hoLopp" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Hooldusoigus.hoTeineIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigus.hoTeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigus.hoTeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigus.hoAsutusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigus.hoAsutusRegNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigus.hoPrimIsik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigus.hoLoodi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigus.hoMuudeti" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR456ResponseType.Hooldusoigused.Hooldusoigus> hooldusoigus;

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
         * {@link RR456ResponseType.Hooldusoigused.Hooldusoigus }
         * 
         * 
         */
        public List<RR456ResponseType.Hooldusoigused.Hooldusoigus> getHooldusoigus() {
            if (hooldusoigus == null) {
                hooldusoigus = new ArrayList<RR456ResponseType.Hooldusoigused.Hooldusoigus>();
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
         *         &lt;element name="Hooldusoigus.hoRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigus.hoLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigus.hoOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigus.hoSisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigus.hoAlgus" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Hooldusoigus.hoLopp" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Hooldusoigus.hoTeineIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigus.hoTeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigus.hoTeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigus.hoAsutusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigus.hoAsutusRegNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigus.hoPrimIsik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigus.hoLoodi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigus.hoMuudeti" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "hooldusoigusHoRoll",
            "hooldusoigusHoLiik",
            "hooldusoigusHoOlek",
            "hooldusoigusHoSisu",
            "hooldusoigusHoAlgus",
            "hooldusoigusHoLopp",
            "hooldusoigusHoTeineIK",
            "hooldusoigusHoTeineEesnimi",
            "hooldusoigusHoTeinePerenimi",
            "hooldusoigusHoAsutusNimi",
            "hooldusoigusHoAsutusRegNr",
            "hooldusoigusHoPrimIsik",
            "hooldusoigusHoLoodi",
            "hooldusoigusHoMuudeti"
        })
        public static class Hooldusoigus {

            @XmlElement(name = "Hooldusoigus.hoRoll", required = true)
            protected String hooldusoigusHoRoll;
            @XmlElement(name = "Hooldusoigus.hoLiik", required = true)
            protected String hooldusoigusHoLiik;
            @XmlElement(name = "Hooldusoigus.hoOlek", required = true)
            protected String hooldusoigusHoOlek;
            @XmlElement(name = "Hooldusoigus.hoSisu", required = true)
            protected String hooldusoigusHoSisu;
            @XmlElement(name = "Hooldusoigus.hoAlgus", required = true)
            protected String hooldusoigusHoAlgus;
            @XmlElement(name = "Hooldusoigus.hoLopp", required = true)
            protected String hooldusoigusHoLopp;
            @XmlElement(name = "Hooldusoigus.hoTeineIK", required = true)
            protected String hooldusoigusHoTeineIK;
            @XmlElement(name = "Hooldusoigus.hoTeineEesnimi", required = true)
            protected String hooldusoigusHoTeineEesnimi;
            @XmlElement(name = "Hooldusoigus.hoTeinePerenimi", required = true)
            protected String hooldusoigusHoTeinePerenimi;
            @XmlElement(name = "Hooldusoigus.hoAsutusNimi", required = true)
            protected String hooldusoigusHoAsutusNimi;
            @XmlElement(name = "Hooldusoigus.hoAsutusRegNr", required = true)
            protected String hooldusoigusHoAsutusRegNr;
            @XmlElement(name = "Hooldusoigus.hoPrimIsik", required = true)
            protected String hooldusoigusHoPrimIsik;
            @XmlElement(name = "Hooldusoigus.hoLoodi", required = true)
            protected String hooldusoigusHoLoodi;
            @XmlElement(name = "Hooldusoigus.hoMuudeti", required = true)
            protected String hooldusoigusHoMuudeti;

            /**
             * Gets the value of the hooldusoigusHoRoll property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoRoll() {
                return hooldusoigusHoRoll;
            }

            /**
             * Sets the value of the hooldusoigusHoRoll property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoRoll(String value) {
                this.hooldusoigusHoRoll = value;
            }

            /**
             * Gets the value of the hooldusoigusHoLiik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoLiik() {
                return hooldusoigusHoLiik;
            }

            /**
             * Sets the value of the hooldusoigusHoLiik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoLiik(String value) {
                this.hooldusoigusHoLiik = value;
            }

            /**
             * Gets the value of the hooldusoigusHoOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoOlek() {
                return hooldusoigusHoOlek;
            }

            /**
             * Sets the value of the hooldusoigusHoOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoOlek(String value) {
                this.hooldusoigusHoOlek = value;
            }

            /**
             * Gets the value of the hooldusoigusHoSisu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoSisu() {
                return hooldusoigusHoSisu;
            }

            /**
             * Sets the value of the hooldusoigusHoSisu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoSisu(String value) {
                this.hooldusoigusHoSisu = value;
            }

            /**
             * Gets the value of the hooldusoigusHoAlgus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoAlgus() {
                return hooldusoigusHoAlgus;
            }

            /**
             * Sets the value of the hooldusoigusHoAlgus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoAlgus(String value) {
                this.hooldusoigusHoAlgus = value;
            }

            /**
             * Gets the value of the hooldusoigusHoLopp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoLopp() {
                return hooldusoigusHoLopp;
            }

            /**
             * Sets the value of the hooldusoigusHoLopp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoLopp(String value) {
                this.hooldusoigusHoLopp = value;
            }

            /**
             * Gets the value of the hooldusoigusHoTeineIK property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoTeineIK() {
                return hooldusoigusHoTeineIK;
            }

            /**
             * Sets the value of the hooldusoigusHoTeineIK property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoTeineIK(String value) {
                this.hooldusoigusHoTeineIK = value;
            }

            /**
             * Gets the value of the hooldusoigusHoTeineEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoTeineEesnimi() {
                return hooldusoigusHoTeineEesnimi;
            }

            /**
             * Sets the value of the hooldusoigusHoTeineEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoTeineEesnimi(String value) {
                this.hooldusoigusHoTeineEesnimi = value;
            }

            /**
             * Gets the value of the hooldusoigusHoTeinePerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoTeinePerenimi() {
                return hooldusoigusHoTeinePerenimi;
            }

            /**
             * Sets the value of the hooldusoigusHoTeinePerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoTeinePerenimi(String value) {
                this.hooldusoigusHoTeinePerenimi = value;
            }

            /**
             * Gets the value of the hooldusoigusHoAsutusNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoAsutusNimi() {
                return hooldusoigusHoAsutusNimi;
            }

            /**
             * Sets the value of the hooldusoigusHoAsutusNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoAsutusNimi(String value) {
                this.hooldusoigusHoAsutusNimi = value;
            }

            /**
             * Gets the value of the hooldusoigusHoAsutusRegNr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoAsutusRegNr() {
                return hooldusoigusHoAsutusRegNr;
            }

            /**
             * Sets the value of the hooldusoigusHoAsutusRegNr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoAsutusRegNr(String value) {
                this.hooldusoigusHoAsutusRegNr = value;
            }

            /**
             * Gets the value of the hooldusoigusHoPrimIsik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoPrimIsik() {
                return hooldusoigusHoPrimIsik;
            }

            /**
             * Sets the value of the hooldusoigusHoPrimIsik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoPrimIsik(String value) {
                this.hooldusoigusHoPrimIsik = value;
            }

            /**
             * Gets the value of the hooldusoigusHoLoodi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoLoodi() {
                return hooldusoigusHoLoodi;
            }

            /**
             * Sets the value of the hooldusoigusHoLoodi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoLoodi(String value) {
                this.hooldusoigusHoLoodi = value;
            }

            /**
             * Gets the value of the hooldusoigusHoMuudeti property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusHoMuudeti() {
                return hooldusoigusHoMuudeti;
            }

            /**
             * Sets the value of the hooldusoigusHoMuudeti property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusHoMuudeti(String value) {
                this.hooldusoigusHoMuudeti = value;
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
     *         &lt;element name="Isik" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Isik.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.MuudEesnimed" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Isik.MuudEesnimed.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Isik.MuudPerenimed" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Isik.MuudPerenimed.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Isik.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.IsikuStaatuseMuutumine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Isik.Surmaaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Isik.SynniRiigiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SynniRiigiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.IsikKoda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ValisriigiIsikukoodid"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Isik.ValisriigiIsikukood" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Isik.ValisriigiIsikukood.VrRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.ValisriigiIsikukood.VrIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="Isik.SaabEestisse" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Isik.LahkusEestist" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Isik.SaabKOVi" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Isik.Elamisluba" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ElamisloaTahtaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Isik.ViimatiPar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.RiigiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.RiigiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.MaakonnaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.MaakonnaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.VallaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.VallaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.KylaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.KylaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.VaikekohaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.VaikekohaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.TanavaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.TanavaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.NimiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.NimiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="Isik.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="Isik.ElukohtLoodi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.ElukohtMuudeti" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideRiigiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideRiigiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideMaakonnaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideMaakonnaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideVallaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideVallaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideKylaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideKylaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideVaikekohaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideVaikekohaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideTanavaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideTanavaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideNimiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideNimiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideMajanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideKorternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SidePostiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="Isik.SideADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isik.SideADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="Isik.Kontaktid" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="Isik.Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="Isik.Kontakt.KontaktiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="Isik.Kontakt.KontaktiTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
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
        protected List<RR456ResponseType.Isikud.Isik> isik;

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
         * {@link RR456ResponseType.Isikud.Isik }
         * 
         * 
         */
        public List<RR456ResponseType.Isikud.Isik> getIsik() {
            if (isik == null) {
                isik = new ArrayList<RR456ResponseType.Isikud.Isik>();
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
         *         &lt;element name="Isik.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.MuudEesnimed" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Isik.MuudEesnimed.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Isik.MuudPerenimed" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Isik.MuudPerenimed.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="Isik.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.IsikuStaatuseMuutumine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Synniaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Isik.Surmaaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Isik.SynniRiigiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SynniRiigiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.IsikKoda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ValisriigiIsikukoodid"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Isik.ValisriigiIsikukood" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Isik.ValisriigiIsikukood.VrRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.ValisriigiIsikukood.VrIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
         *         &lt;element name="Isik.SaabEestisse" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Isik.LahkusEestist" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Isik.SaabKOVi" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Isik.Elamisluba" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ElamisloaTahtaeg" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Isik.ViimatiPar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.RiigiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.RiigiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.MaakonnaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.MaakonnaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.VallaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.VallaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.KylaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.KylaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.VaikekohaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.VaikekohaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.TanavaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.TanavaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.NimiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.NimiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Korternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *         &lt;element name="Isik.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *         &lt;element name="Isik.ElukohtLoodi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.ElukohtMuudeti" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideRiigiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideRiigiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideMaakonnaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideMaakonnaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideVallaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideVallaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideKylaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideKylaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideVaikekohaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideVaikekohaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideTanavaKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideTanavaNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideNimiKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideNimiNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideMajanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideKorternr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideAadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SidePostiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *         &lt;element name="Isik.SideADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isik.SideADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *         &lt;element name="Isik.Kontaktid" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="Isik.Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="Isik.Kontakt.KontaktiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="Isik.Kontakt.KontaktiTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikEesnimi",
            "isikPerenimi",
            "isikMuudEesnimed",
            "isikMuudPerenimed",
            "isikSugu",
            "isikIsikuStaatus",
            "isikIsikuStaatuseMuutumine",
            "isikKirjeStaatus",
            "isikSynniaeg",
            "isikSurmaaeg",
            "isikSynniRiigiKd",
            "isikSynniRiigiNm",
            "isikIsikKoda",
            "isikValisriigiIsikukoodid",
            "isikSaabEestisse",
            "isikLahkusEestist",
            "isikSaabKOVi",
            "isikElamisluba",
            "isikElamisloaTahtaeg",
            "isikViimatiPar",
            "isikRiigiKd",
            "isikRiigiNm",
            "isikMaakonnaKd",
            "isikMaakonnaNm",
            "isikVallaKd",
            "isikVallaNm",
            "isikKylaKd",
            "isikKylaNm",
            "isikVaikekohaKd",
            "isikVaikekohaNm",
            "isikTanavaKd",
            "isikTanavaNm",
            "isikNimiKd",
            "isikNimiNm",
            "isikMajanr",
            "isikKorternr",
            "isikAadress",
            "isikPostiindeks",
            "isikADSADRID",
            "isikADSOID",
            "isikADSKOODAADRESS",
            "isikADSADOBID",
            "isikElukohtLoodi",
            "isikElukohtMuudeti",
            "isikSideRiigiKd",
            "isikSideRiigiNm",
            "isikSideMaakonnaKd",
            "isikSideMaakonnaNm",
            "isikSideVallaKd",
            "isikSideVallaNm",
            "isikSideKylaKd",
            "isikSideKylaNm",
            "isikSideVaikekohaKd",
            "isikSideVaikekohaNm",
            "isikSideTanavaKd",
            "isikSideTanavaNm",
            "isikSideNimiKd",
            "isikSideNimiNm",
            "isikSideMajanr",
            "isikSideKorternr",
            "isikSideAadress",
            "isikSidePostiindeks",
            "isikSideADSADRID",
            "isikSideADSOID",
            "isikSideADSKOODAADRESS",
            "isikSideADSADOBID",
            "isikKontaktid"
        })
        public static class Isik {

            @XmlElement(name = "Isik.Isikukood", required = true)
            protected String isikIsikukood;
            @XmlElement(name = "Isik.Eesnimi", required = true)
            protected String isikEesnimi;
            @XmlElement(name = "Isik.Perenimi", required = true)
            protected String isikPerenimi;
            @XmlElement(name = "Isik.MuudEesnimed")
            protected RR456ResponseType.Isikud.Isik.IsikMuudEesnimed isikMuudEesnimed;
            @XmlElement(name = "Isik.MuudPerenimed")
            protected RR456ResponseType.Isikud.Isik.IsikMuudPerenimed isikMuudPerenimed;
            @XmlElement(name = "Isik.Sugu", required = true)
            protected String isikSugu;
            @XmlElement(name = "Isik.IsikuStaatus", required = true)
            protected String isikIsikuStaatus;
            @XmlElement(name = "Isik.IsikuStaatuseMuutumine", required = true)
            protected String isikIsikuStaatuseMuutumine;
            @XmlElement(name = "Isik.KirjeStaatus", required = true)
            protected String isikKirjeStaatus;
            @XmlElement(name = "Isik.Synniaeg", required = true)
            protected String isikSynniaeg;
            @XmlElement(name = "Isik.Surmaaeg", required = true)
            protected String isikSurmaaeg;
            @XmlElement(name = "Isik.SynniRiigiKd", required = true)
            protected String isikSynniRiigiKd;
            @XmlElement(name = "Isik.SynniRiigiNm", required = true)
            protected String isikSynniRiigiNm;
            @XmlElement(name = "Isik.IsikKoda", required = true)
            protected String isikIsikKoda;
            @XmlElement(name = "Isik.ValisriigiIsikukoodid", required = true)
            protected RR456ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid isikValisriigiIsikukoodid;
            @XmlElement(name = "Isik.SaabEestisse", required = true)
            protected String isikSaabEestisse;
            @XmlElement(name = "Isik.LahkusEestist", required = true)
            protected String isikLahkusEestist;
            @XmlElement(name = "Isik.SaabKOVi", required = true)
            protected String isikSaabKOVi;
            @XmlElement(name = "Isik.Elamisluba", required = true)
            protected String isikElamisluba;
            @XmlElement(name = "Isik.ElamisloaTahtaeg", required = true)
            protected String isikElamisloaTahtaeg;
            @XmlElement(name = "Isik.ViimatiPar", required = true)
            protected String isikViimatiPar;
            @XmlElement(name = "Isik.RiigiKd", required = true)
            protected String isikRiigiKd;
            @XmlElement(name = "Isik.RiigiNm", required = true)
            protected String isikRiigiNm;
            @XmlElement(name = "Isik.MaakonnaKd", required = true)
            protected String isikMaakonnaKd;
            @XmlElement(name = "Isik.MaakonnaNm", required = true)
            protected String isikMaakonnaNm;
            @XmlElement(name = "Isik.VallaKd", required = true)
            protected String isikVallaKd;
            @XmlElement(name = "Isik.VallaNm", required = true)
            protected String isikVallaNm;
            @XmlElement(name = "Isik.KylaKd", required = true)
            protected String isikKylaKd;
            @XmlElement(name = "Isik.KylaNm", required = true)
            protected String isikKylaNm;
            @XmlElement(name = "Isik.VaikekohaKd", required = true)
            protected String isikVaikekohaKd;
            @XmlElement(name = "Isik.VaikekohaNm", required = true)
            protected String isikVaikekohaNm;
            @XmlElement(name = "Isik.TanavaKd", required = true)
            protected String isikTanavaKd;
            @XmlElement(name = "Isik.TanavaNm", required = true)
            protected String isikTanavaNm;
            @XmlElement(name = "Isik.NimiKd", required = true)
            protected String isikNimiKd;
            @XmlElement(name = "Isik.NimiNm", required = true)
            protected String isikNimiNm;
            @XmlElement(name = "Isik.Majanr", required = true)
            protected String isikMajanr;
            @XmlElement(name = "Isik.Korternr", required = true)
            protected String isikKorternr;
            @XmlElement(name = "Isik.Aadress", required = true)
            protected String isikAadress;
            @XmlElement(name = "Isik.Postiindeks", required = true)
            protected String isikPostiindeks;
            @XmlElement(name = "Isik.ADS_ADR_ID", required = true)
            protected BigInteger isikADSADRID;
            @XmlElement(name = "Isik.ADS_OID", required = true)
            protected String isikADSOID;
            @XmlElement(name = "Isik.ADS_KOODAADRESS", required = true)
            protected String isikADSKOODAADRESS;
            @XmlElement(name = "Isik.ADS_ADOB_ID", required = true)
            protected BigInteger isikADSADOBID;
            @XmlElement(name = "Isik.ElukohtLoodi", required = true)
            protected String isikElukohtLoodi;
            @XmlElement(name = "Isik.ElukohtMuudeti", required = true)
            protected String isikElukohtMuudeti;
            @XmlElement(name = "Isik.SideRiigiKd", required = true)
            protected String isikSideRiigiKd;
            @XmlElement(name = "Isik.SideRiigiNm", required = true)
            protected String isikSideRiigiNm;
            @XmlElement(name = "Isik.SideMaakonnaKd", required = true)
            protected String isikSideMaakonnaKd;
            @XmlElement(name = "Isik.SideMaakonnaNm", required = true)
            protected String isikSideMaakonnaNm;
            @XmlElement(name = "Isik.SideVallaKd", required = true)
            protected String isikSideVallaKd;
            @XmlElement(name = "Isik.SideVallaNm", required = true)
            protected String isikSideVallaNm;
            @XmlElement(name = "Isik.SideKylaKd", required = true)
            protected String isikSideKylaKd;
            @XmlElement(name = "Isik.SideKylaNm", required = true)
            protected String isikSideKylaNm;
            @XmlElement(name = "Isik.SideVaikekohaKd", required = true)
            protected String isikSideVaikekohaKd;
            @XmlElement(name = "Isik.SideVaikekohaNm", required = true)
            protected String isikSideVaikekohaNm;
            @XmlElement(name = "Isik.SideTanavaKd", required = true)
            protected String isikSideTanavaKd;
            @XmlElement(name = "Isik.SideTanavaNm", required = true)
            protected String isikSideTanavaNm;
            @XmlElement(name = "Isik.SideNimiKd", required = true)
            protected String isikSideNimiKd;
            @XmlElement(name = "Isik.SideNimiNm", required = true)
            protected String isikSideNimiNm;
            @XmlElement(name = "Isik.SideMajanr", required = true)
            protected String isikSideMajanr;
            @XmlElement(name = "Isik.SideKorternr", required = true)
            protected String isikSideKorternr;
            @XmlElement(name = "Isik.SideAadress", required = true)
            protected String isikSideAadress;
            @XmlElement(name = "Isik.SidePostiindeks", required = true)
            protected String isikSidePostiindeks;
            @XmlElement(name = "Isik.SideADS_ADR_ID", required = true)
            protected BigInteger isikSideADSADRID;
            @XmlElement(name = "Isik.SideADS_OID", required = true)
            protected String isikSideADSOID;
            @XmlElement(name = "Isik.SideADS_KOODAADRESS", required = true)
            protected String isikSideADSKOODAADRESS;
            @XmlElement(name = "Isik.SideADS_ADOB_ID", required = true)
            protected BigInteger isikSideADSADOBID;
            @XmlElement(name = "Isik.Kontaktid")
            protected RR456ResponseType.Isikud.Isik.IsikKontaktid isikKontaktid;

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
             * Gets the value of the isikEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikEesnimi() {
                return isikEesnimi;
            }

            /**
             * Sets the value of the isikEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikEesnimi(String value) {
                this.isikEesnimi = value;
            }

            /**
             * Gets the value of the isikPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikPerenimi() {
                return isikPerenimi;
            }

            /**
             * Sets the value of the isikPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikPerenimi(String value) {
                this.isikPerenimi = value;
            }

            /**
             * Gets the value of the isikMuudEesnimed property.
             * 
             * @return
             *     possible object is
             *     {@link RR456ResponseType.Isikud.Isik.IsikMuudEesnimed }
             *     
             */
            public RR456ResponseType.Isikud.Isik.IsikMuudEesnimed getIsikMuudEesnimed() {
                return isikMuudEesnimed;
            }

            /**
             * Sets the value of the isikMuudEesnimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR456ResponseType.Isikud.Isik.IsikMuudEesnimed }
             *     
             */
            public void setIsikMuudEesnimed(RR456ResponseType.Isikud.Isik.IsikMuudEesnimed value) {
                this.isikMuudEesnimed = value;
            }

            /**
             * Gets the value of the isikMuudPerenimed property.
             * 
             * @return
             *     possible object is
             *     {@link RR456ResponseType.Isikud.Isik.IsikMuudPerenimed }
             *     
             */
            public RR456ResponseType.Isikud.Isik.IsikMuudPerenimed getIsikMuudPerenimed() {
                return isikMuudPerenimed;
            }

            /**
             * Sets the value of the isikMuudPerenimed property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR456ResponseType.Isikud.Isik.IsikMuudPerenimed }
             *     
             */
            public void setIsikMuudPerenimed(RR456ResponseType.Isikud.Isik.IsikMuudPerenimed value) {
                this.isikMuudPerenimed = value;
            }

            /**
             * Gets the value of the isikSugu property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSugu() {
                return isikSugu;
            }

            /**
             * Sets the value of the isikSugu property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSugu(String value) {
                this.isikSugu = value;
            }

            /**
             * Gets the value of the isikIsikuStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikuStaatus() {
                return isikIsikuStaatus;
            }

            /**
             * Sets the value of the isikIsikuStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikuStaatus(String value) {
                this.isikIsikuStaatus = value;
            }

            /**
             * Gets the value of the isikIsikuStaatuseMuutumine property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikuStaatuseMuutumine() {
                return isikIsikuStaatuseMuutumine;
            }

            /**
             * Sets the value of the isikIsikuStaatuseMuutumine property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikuStaatuseMuutumine(String value) {
                this.isikIsikuStaatuseMuutumine = value;
            }

            /**
             * Gets the value of the isikKirjeStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKirjeStaatus() {
                return isikKirjeStaatus;
            }

            /**
             * Sets the value of the isikKirjeStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKirjeStaatus(String value) {
                this.isikKirjeStaatus = value;
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
             * Gets the value of the isikSurmaaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSurmaaeg() {
                return isikSurmaaeg;
            }

            /**
             * Sets the value of the isikSurmaaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSurmaaeg(String value) {
                this.isikSurmaaeg = value;
            }

            /**
             * Gets the value of the isikSynniRiigiKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSynniRiigiKd() {
                return isikSynniRiigiKd;
            }

            /**
             * Sets the value of the isikSynniRiigiKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSynniRiigiKd(String value) {
                this.isikSynniRiigiKd = value;
            }

            /**
             * Gets the value of the isikSynniRiigiNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSynniRiigiNm() {
                return isikSynniRiigiNm;
            }

            /**
             * Sets the value of the isikSynniRiigiNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSynniRiigiNm(String value) {
                this.isikSynniRiigiNm = value;
            }

            /**
             * Gets the value of the isikIsikKoda property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikIsikKoda() {
                return isikIsikKoda;
            }

            /**
             * Sets the value of the isikIsikKoda property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikIsikKoda(String value) {
                this.isikIsikKoda = value;
            }

            /**
             * Gets the value of the isikValisriigiIsikukoodid property.
             * 
             * @return
             *     possible object is
             *     {@link RR456ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid }
             *     
             */
            public RR456ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid getIsikValisriigiIsikukoodid() {
                return isikValisriigiIsikukoodid;
            }

            /**
             * Sets the value of the isikValisriigiIsikukoodid property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR456ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid }
             *     
             */
            public void setIsikValisriigiIsikukoodid(RR456ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid value) {
                this.isikValisriigiIsikukoodid = value;
            }

            /**
             * Gets the value of the isikSaabEestisse property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSaabEestisse() {
                return isikSaabEestisse;
            }

            /**
             * Sets the value of the isikSaabEestisse property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSaabEestisse(String value) {
                this.isikSaabEestisse = value;
            }

            /**
             * Gets the value of the isikLahkusEestist property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikLahkusEestist() {
                return isikLahkusEestist;
            }

            /**
             * Sets the value of the isikLahkusEestist property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikLahkusEestist(String value) {
                this.isikLahkusEestist = value;
            }

            /**
             * Gets the value of the isikSaabKOVi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSaabKOVi() {
                return isikSaabKOVi;
            }

            /**
             * Sets the value of the isikSaabKOVi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSaabKOVi(String value) {
                this.isikSaabKOVi = value;
            }

            /**
             * Gets the value of the isikElamisluba property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElamisluba() {
                return isikElamisluba;
            }

            /**
             * Sets the value of the isikElamisluba property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElamisluba(String value) {
                this.isikElamisluba = value;
            }

            /**
             * Gets the value of the isikElamisloaTahtaeg property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElamisloaTahtaeg() {
                return isikElamisloaTahtaeg;
            }

            /**
             * Sets the value of the isikElamisloaTahtaeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElamisloaTahtaeg(String value) {
                this.isikElamisloaTahtaeg = value;
            }

            /**
             * Gets the value of the isikViimatiPar property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikViimatiPar() {
                return isikViimatiPar;
            }

            /**
             * Sets the value of the isikViimatiPar property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikViimatiPar(String value) {
                this.isikViimatiPar = value;
            }

            /**
             * Gets the value of the isikRiigiKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikRiigiKd() {
                return isikRiigiKd;
            }

            /**
             * Sets the value of the isikRiigiKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikRiigiKd(String value) {
                this.isikRiigiKd = value;
            }

            /**
             * Gets the value of the isikRiigiNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikRiigiNm() {
                return isikRiigiNm;
            }

            /**
             * Sets the value of the isikRiigiNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikRiigiNm(String value) {
                this.isikRiigiNm = value;
            }

            /**
             * Gets the value of the isikMaakonnaKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMaakonnaKd() {
                return isikMaakonnaKd;
            }

            /**
             * Sets the value of the isikMaakonnaKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMaakonnaKd(String value) {
                this.isikMaakonnaKd = value;
            }

            /**
             * Gets the value of the isikMaakonnaNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikMaakonnaNm() {
                return isikMaakonnaNm;
            }

            /**
             * Sets the value of the isikMaakonnaNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikMaakonnaNm(String value) {
                this.isikMaakonnaNm = value;
            }

            /**
             * Gets the value of the isikVallaKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVallaKd() {
                return isikVallaKd;
            }

            /**
             * Sets the value of the isikVallaKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVallaKd(String value) {
                this.isikVallaKd = value;
            }

            /**
             * Gets the value of the isikVallaNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVallaNm() {
                return isikVallaNm;
            }

            /**
             * Sets the value of the isikVallaNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVallaNm(String value) {
                this.isikVallaNm = value;
            }

            /**
             * Gets the value of the isikKylaKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKylaKd() {
                return isikKylaKd;
            }

            /**
             * Sets the value of the isikKylaKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKylaKd(String value) {
                this.isikKylaKd = value;
            }

            /**
             * Gets the value of the isikKylaNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikKylaNm() {
                return isikKylaNm;
            }

            /**
             * Sets the value of the isikKylaNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikKylaNm(String value) {
                this.isikKylaNm = value;
            }

            /**
             * Gets the value of the isikVaikekohaKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVaikekohaKd() {
                return isikVaikekohaKd;
            }

            /**
             * Sets the value of the isikVaikekohaKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVaikekohaKd(String value) {
                this.isikVaikekohaKd = value;
            }

            /**
             * Gets the value of the isikVaikekohaNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikVaikekohaNm() {
                return isikVaikekohaNm;
            }

            /**
             * Sets the value of the isikVaikekohaNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikVaikekohaNm(String value) {
                this.isikVaikekohaNm = value;
            }

            /**
             * Gets the value of the isikTanavaKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTanavaKd() {
                return isikTanavaKd;
            }

            /**
             * Sets the value of the isikTanavaKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTanavaKd(String value) {
                this.isikTanavaKd = value;
            }

            /**
             * Gets the value of the isikTanavaNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikTanavaNm() {
                return isikTanavaNm;
            }

            /**
             * Sets the value of the isikTanavaNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikTanavaNm(String value) {
                this.isikTanavaNm = value;
            }

            /**
             * Gets the value of the isikNimiKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikNimiKd() {
                return isikNimiKd;
            }

            /**
             * Sets the value of the isikNimiKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikNimiKd(String value) {
                this.isikNimiKd = value;
            }

            /**
             * Gets the value of the isikNimiNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikNimiNm() {
                return isikNimiNm;
            }

            /**
             * Sets the value of the isikNimiNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikNimiNm(String value) {
                this.isikNimiNm = value;
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
             * Gets the value of the isikAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikAadress() {
                return isikAadress;
            }

            /**
             * Sets the value of the isikAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikAadress(String value) {
                this.isikAadress = value;
            }

            /**
             * Gets the value of the isikPostiindeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikPostiindeks() {
                return isikPostiindeks;
            }

            /**
             * Sets the value of the isikPostiindeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikPostiindeks(String value) {
                this.isikPostiindeks = value;
            }

            /**
             * Gets the value of the isikADSADRID property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getIsikADSADRID() {
                return isikADSADRID;
            }

            /**
             * Sets the value of the isikADSADRID property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setIsikADSADRID(BigInteger value) {
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
             * Gets the value of the isikADSKOODAADRESS property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikADSKOODAADRESS() {
                return isikADSKOODAADRESS;
            }

            /**
             * Sets the value of the isikADSKOODAADRESS property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikADSKOODAADRESS(String value) {
                this.isikADSKOODAADRESS = value;
            }

            /**
             * Gets the value of the isikADSADOBID property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getIsikADSADOBID() {
                return isikADSADOBID;
            }

            /**
             * Sets the value of the isikADSADOBID property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setIsikADSADOBID(BigInteger value) {
                this.isikADSADOBID = value;
            }

            /**
             * Gets the value of the isikElukohtLoodi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElukohtLoodi() {
                return isikElukohtLoodi;
            }

            /**
             * Sets the value of the isikElukohtLoodi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElukohtLoodi(String value) {
                this.isikElukohtLoodi = value;
            }

            /**
             * Gets the value of the isikElukohtMuudeti property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikElukohtMuudeti() {
                return isikElukohtMuudeti;
            }

            /**
             * Sets the value of the isikElukohtMuudeti property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikElukohtMuudeti(String value) {
                this.isikElukohtMuudeti = value;
            }

            /**
             * Gets the value of the isikSideRiigiKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideRiigiKd() {
                return isikSideRiigiKd;
            }

            /**
             * Sets the value of the isikSideRiigiKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideRiigiKd(String value) {
                this.isikSideRiigiKd = value;
            }

            /**
             * Gets the value of the isikSideRiigiNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideRiigiNm() {
                return isikSideRiigiNm;
            }

            /**
             * Sets the value of the isikSideRiigiNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideRiigiNm(String value) {
                this.isikSideRiigiNm = value;
            }

            /**
             * Gets the value of the isikSideMaakonnaKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideMaakonnaKd() {
                return isikSideMaakonnaKd;
            }

            /**
             * Sets the value of the isikSideMaakonnaKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideMaakonnaKd(String value) {
                this.isikSideMaakonnaKd = value;
            }

            /**
             * Gets the value of the isikSideMaakonnaNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideMaakonnaNm() {
                return isikSideMaakonnaNm;
            }

            /**
             * Sets the value of the isikSideMaakonnaNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideMaakonnaNm(String value) {
                this.isikSideMaakonnaNm = value;
            }

            /**
             * Gets the value of the isikSideVallaKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideVallaKd() {
                return isikSideVallaKd;
            }

            /**
             * Sets the value of the isikSideVallaKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideVallaKd(String value) {
                this.isikSideVallaKd = value;
            }

            /**
             * Gets the value of the isikSideVallaNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideVallaNm() {
                return isikSideVallaNm;
            }

            /**
             * Sets the value of the isikSideVallaNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideVallaNm(String value) {
                this.isikSideVallaNm = value;
            }

            /**
             * Gets the value of the isikSideKylaKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideKylaKd() {
                return isikSideKylaKd;
            }

            /**
             * Sets the value of the isikSideKylaKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideKylaKd(String value) {
                this.isikSideKylaKd = value;
            }

            /**
             * Gets the value of the isikSideKylaNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideKylaNm() {
                return isikSideKylaNm;
            }

            /**
             * Sets the value of the isikSideKylaNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideKylaNm(String value) {
                this.isikSideKylaNm = value;
            }

            /**
             * Gets the value of the isikSideVaikekohaKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideVaikekohaKd() {
                return isikSideVaikekohaKd;
            }

            /**
             * Sets the value of the isikSideVaikekohaKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideVaikekohaKd(String value) {
                this.isikSideVaikekohaKd = value;
            }

            /**
             * Gets the value of the isikSideVaikekohaNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideVaikekohaNm() {
                return isikSideVaikekohaNm;
            }

            /**
             * Sets the value of the isikSideVaikekohaNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideVaikekohaNm(String value) {
                this.isikSideVaikekohaNm = value;
            }

            /**
             * Gets the value of the isikSideTanavaKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideTanavaKd() {
                return isikSideTanavaKd;
            }

            /**
             * Sets the value of the isikSideTanavaKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideTanavaKd(String value) {
                this.isikSideTanavaKd = value;
            }

            /**
             * Gets the value of the isikSideTanavaNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideTanavaNm() {
                return isikSideTanavaNm;
            }

            /**
             * Sets the value of the isikSideTanavaNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideTanavaNm(String value) {
                this.isikSideTanavaNm = value;
            }

            /**
             * Gets the value of the isikSideNimiKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideNimiKd() {
                return isikSideNimiKd;
            }

            /**
             * Sets the value of the isikSideNimiKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideNimiKd(String value) {
                this.isikSideNimiKd = value;
            }

            /**
             * Gets the value of the isikSideNimiNm property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideNimiNm() {
                return isikSideNimiNm;
            }

            /**
             * Sets the value of the isikSideNimiNm property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideNimiNm(String value) {
                this.isikSideNimiNm = value;
            }

            /**
             * Gets the value of the isikSideMajanr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideMajanr() {
                return isikSideMajanr;
            }

            /**
             * Sets the value of the isikSideMajanr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideMajanr(String value) {
                this.isikSideMajanr = value;
            }

            /**
             * Gets the value of the isikSideKorternr property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideKorternr() {
                return isikSideKorternr;
            }

            /**
             * Sets the value of the isikSideKorternr property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideKorternr(String value) {
                this.isikSideKorternr = value;
            }

            /**
             * Gets the value of the isikSideAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideAadress() {
                return isikSideAadress;
            }

            /**
             * Sets the value of the isikSideAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideAadress(String value) {
                this.isikSideAadress = value;
            }

            /**
             * Gets the value of the isikSidePostiindeks property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSidePostiindeks() {
                return isikSidePostiindeks;
            }

            /**
             * Sets the value of the isikSidePostiindeks property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSidePostiindeks(String value) {
                this.isikSidePostiindeks = value;
            }

            /**
             * Gets the value of the isikSideADSADRID property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getIsikSideADSADRID() {
                return isikSideADSADRID;
            }

            /**
             * Sets the value of the isikSideADSADRID property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setIsikSideADSADRID(BigInteger value) {
                this.isikSideADSADRID = value;
            }

            /**
             * Gets the value of the isikSideADSOID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideADSOID() {
                return isikSideADSOID;
            }

            /**
             * Sets the value of the isikSideADSOID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideADSOID(String value) {
                this.isikSideADSOID = value;
            }

            /**
             * Gets the value of the isikSideADSKOODAADRESS property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikSideADSKOODAADRESS() {
                return isikSideADSKOODAADRESS;
            }

            /**
             * Sets the value of the isikSideADSKOODAADRESS property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikSideADSKOODAADRESS(String value) {
                this.isikSideADSKOODAADRESS = value;
            }

            /**
             * Gets the value of the isikSideADSADOBID property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getIsikSideADSADOBID() {
                return isikSideADSADOBID;
            }

            /**
             * Sets the value of the isikSideADSADOBID property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setIsikSideADSADOBID(BigInteger value) {
                this.isikSideADSADOBID = value;
            }

            /**
             * Gets the value of the isikKontaktid property.
             * 
             * @return
             *     possible object is
             *     {@link RR456ResponseType.Isikud.Isik.IsikKontaktid }
             *     
             */
            public RR456ResponseType.Isikud.Isik.IsikKontaktid getIsikKontaktid() {
                return isikKontaktid;
            }

            /**
             * Sets the value of the isikKontaktid property.
             * 
             * @param value
             *     allowed object is
             *     {@link RR456ResponseType.Isikud.Isik.IsikKontaktid }
             *     
             */
            public void setIsikKontaktid(RR456ResponseType.Isikud.Isik.IsikKontaktid value) {
                this.isikKontaktid = value;
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
             *         &lt;element name="Isik.Kontakt" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Isik.Kontakt.KontaktiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.Kontakt.KontaktiTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "isikKontakt"
            })
            public static class IsikKontaktid {

                @XmlElement(name = "Isik.Kontakt")
                protected List<RR456ResponseType.Isikud.Isik.IsikKontaktid.IsikKontakt> isikKontakt;

                /**
                 * Gets the value of the isikKontakt property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the isikKontakt property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getIsikKontakt().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RR456ResponseType.Isikud.Isik.IsikKontaktid.IsikKontakt }
                 * 
                 * 
                 */
                public List<RR456ResponseType.Isikud.Isik.IsikKontaktid.IsikKontakt> getIsikKontakt() {
                    if (isikKontakt == null) {
                        isikKontakt = new ArrayList<RR456ResponseType.Isikud.Isik.IsikKontaktid.IsikKontakt>();
                    }
                    return this.isikKontakt;
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
                 *         &lt;element name="Isik.Kontakt.KontaktiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Isik.Kontakt.KontaktiTekst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "isikKontaktKontaktiLiik",
                    "isikKontaktKontaktiTekst"
                })
                public static class IsikKontakt {

                    @XmlElement(name = "Isik.Kontakt.KontaktiLiik", required = true)
                    protected String isikKontaktKontaktiLiik;
                    @XmlElement(name = "Isik.Kontakt.KontaktiTekst", required = true)
                    protected String isikKontaktKontaktiTekst;

                    /**
                     * Gets the value of the isikKontaktKontaktiLiik property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getIsikKontaktKontaktiLiik() {
                        return isikKontaktKontaktiLiik;
                    }

                    /**
                     * Sets the value of the isikKontaktKontaktiLiik property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setIsikKontaktKontaktiLiik(String value) {
                        this.isikKontaktKontaktiLiik = value;
                    }

                    /**
                     * Gets the value of the isikKontaktKontaktiTekst property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getIsikKontaktKontaktiTekst() {
                        return isikKontaktKontaktiTekst;
                    }

                    /**
                     * Sets the value of the isikKontaktKontaktiTekst property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setIsikKontaktKontaktiTekst(String value) {
                        this.isikKontaktKontaktiTekst = value;
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
             *         &lt;element name="Isik.MuudEesnimed.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
                "isikMuudEesnimedMeesnm"
            })
            public static class IsikMuudEesnimed {

                @XmlElement(name = "Isik.MuudEesnimed.Meesnm")
                protected List<String> isikMuudEesnimedMeesnm;

                /**
                 * Gets the value of the isikMuudEesnimedMeesnm property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the isikMuudEesnimedMeesnm property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getIsikMuudEesnimedMeesnm().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getIsikMuudEesnimedMeesnm() {
                    if (isikMuudEesnimedMeesnm == null) {
                        isikMuudEesnimedMeesnm = new ArrayList<String>();
                    }
                    return this.isikMuudEesnimedMeesnm;
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
             *         &lt;element name="Isik.MuudPerenimed.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
                "isikMuudPerenimedMperenm"
            })
            public static class IsikMuudPerenimed {

                @XmlElement(name = "Isik.MuudPerenimed.Mperenm")
                protected List<String> isikMuudPerenimedMperenm;

                /**
                 * Gets the value of the isikMuudPerenimedMperenm property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the isikMuudPerenimedMperenm property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getIsikMuudPerenimedMperenm().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getIsikMuudPerenimedMperenm() {
                    if (isikMuudPerenimedMperenm == null) {
                        isikMuudPerenimedMperenm = new ArrayList<String>();
                    }
                    return this.isikMuudPerenimedMperenm;
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
             *         &lt;element name="Isik.ValisriigiIsikukood" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="Isik.ValisriigiIsikukood.VrRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="Isik.ValisriigiIsikukood.VrIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "isikValisriigiIsikukood"
            })
            public static class IsikValisriigiIsikukoodid {

                @XmlElement(name = "Isik.ValisriigiIsikukood")
                protected List<RR456ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid.IsikValisriigiIsikukood> isikValisriigiIsikukood;

                /**
                 * Gets the value of the isikValisriigiIsikukood property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the isikValisriigiIsikukood property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getIsikValisriigiIsikukood().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RR456ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid.IsikValisriigiIsikukood }
                 * 
                 * 
                 */
                public List<RR456ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid.IsikValisriigiIsikukood> getIsikValisriigiIsikukood() {
                    if (isikValisriigiIsikukood == null) {
                        isikValisriigiIsikukood = new ArrayList<RR456ResponseType.Isikud.Isik.IsikValisriigiIsikukoodid.IsikValisriigiIsikukood>();
                    }
                    return this.isikValisriigiIsikukood;
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
                 *         &lt;element name="Isik.ValisriigiIsikukood.VrRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="Isik.ValisriigiIsikukood.VrIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "isikValisriigiIsikukoodVrRiik",
                    "isikValisriigiIsikukoodVrIK"
                })
                public static class IsikValisriigiIsikukood {

                    @XmlElement(name = "Isik.ValisriigiIsikukood.VrRiik", required = true)
                    protected String isikValisriigiIsikukoodVrRiik;
                    @XmlElement(name = "Isik.ValisriigiIsikukood.VrIK", required = true)
                    protected String isikValisriigiIsikukoodVrIK;

                    /**
                     * Gets the value of the isikValisriigiIsikukoodVrRiik property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getIsikValisriigiIsikukoodVrRiik() {
                        return isikValisriigiIsikukoodVrRiik;
                    }

                    /**
                     * Sets the value of the isikValisriigiIsikukoodVrRiik property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setIsikValisriigiIsikukoodVrRiik(String value) {
                        this.isikValisriigiIsikukoodVrRiik = value;
                    }

                    /**
                     * Gets the value of the isikValisriigiIsikukoodVrIK property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getIsikValisriigiIsikukoodVrIK() {
                        return isikValisriigiIsikukoodVrIK;
                    }

                    /**
                     * Sets the value of the isikValisriigiIsikukoodVrIK property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setIsikValisriigiIsikukoodVrIK(String value) {
                        this.isikValisriigiIsikukoodVrIK = value;
                    }

                }

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
     *                   &lt;element name="Suhe.Suhetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.AsutusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.AsutusRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Alguskp" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Suhe.Lopukp" type="{http://rr.x-road.eu/producer}date"/&gt;
     *                   &lt;element name="Suhe.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.AlusDokKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.LoppAlusDokKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Loodi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Muudeti" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR456ResponseType.Suhted.Suhe> suhe;

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
         * {@link RR456ResponseType.Suhted.Suhe }
         * 
         * 
         */
        public List<RR456ResponseType.Suhted.Suhe> getSuhe() {
            if (suhe == null) {
                suhe = new ArrayList<RR456ResponseType.Suhted.Suhe>();
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
         *         &lt;element name="Suhe.Suhetyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.AsutusNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.AsutusRegNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Alguskp" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Suhe.Lopukp" type="{http://rr.x-road.eu/producer}date"/&gt;
         *         &lt;element name="Suhe.IsikuIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.AlusDokKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.LoppAlusDokKd" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Loodi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Muudeti" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "suheSuhetyyp",
            "suheStaatus",
            "suheIsikukood",
            "suheEesnimi",
            "suhePerenimi",
            "suheAsutusNimi",
            "suheAsutusRegNumber",
            "suheAlguskp",
            "suheLopukp",
            "suheIsikuIsikukood",
            "suheAlusDokKd",
            "suheLoppAlusDokKd",
            "suheLoodi",
            "suheMuudeti"
        })
        public static class Suhe {

            @XmlElement(name = "Suhe.Suhetyyp", required = true)
            protected String suheSuhetyyp;
            @XmlElement(name = "Suhe.Staatus", required = true)
            protected String suheStaatus;
            @XmlElement(name = "Suhe.Isikukood", required = true)
            protected String suheIsikukood;
            @XmlElement(name = "Suhe.Eesnimi", required = true)
            protected String suheEesnimi;
            @XmlElement(name = "Suhe.Perenimi", required = true)
            protected String suhePerenimi;
            @XmlElement(name = "Suhe.AsutusNimi", required = true)
            protected String suheAsutusNimi;
            @XmlElement(name = "Suhe.AsutusRegNumber", required = true)
            protected String suheAsutusRegNumber;
            @XmlElement(name = "Suhe.Alguskp", required = true)
            protected String suheAlguskp;
            @XmlElement(name = "Suhe.Lopukp", required = true)
            protected String suheLopukp;
            @XmlElement(name = "Suhe.IsikuIsikukood", required = true)
            protected String suheIsikuIsikukood;
            @XmlElement(name = "Suhe.AlusDokKd", required = true)
            protected String suheAlusDokKd;
            @XmlElement(name = "Suhe.LoppAlusDokKd", required = true)
            protected String suheLoppAlusDokKd;
            @XmlElement(name = "Suhe.Loodi", required = true)
            protected String suheLoodi;
            @XmlElement(name = "Suhe.Muudeti", required = true)
            protected String suheMuudeti;

            /**
             * Gets the value of the suheSuhetyyp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheSuhetyyp() {
                return suheSuhetyyp;
            }

            /**
             * Sets the value of the suheSuhetyyp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheSuhetyyp(String value) {
                this.suheSuhetyyp = value;
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
             * Gets the value of the suheAsutusNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheAsutusNimi() {
                return suheAsutusNimi;
            }

            /**
             * Sets the value of the suheAsutusNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheAsutusNimi(String value) {
                this.suheAsutusNimi = value;
            }

            /**
             * Gets the value of the suheAsutusRegNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheAsutusRegNumber() {
                return suheAsutusRegNumber;
            }

            /**
             * Sets the value of the suheAsutusRegNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheAsutusRegNumber(String value) {
                this.suheAsutusRegNumber = value;
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

            /**
             * Gets the value of the suheAlusDokKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheAlusDokKd() {
                return suheAlusDokKd;
            }

            /**
             * Sets the value of the suheAlusDokKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheAlusDokKd(String value) {
                this.suheAlusDokKd = value;
            }

            /**
             * Gets the value of the suheLoppAlusDokKd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheLoppAlusDokKd() {
                return suheLoppAlusDokKd;
            }

            /**
             * Sets the value of the suheLoppAlusDokKd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheLoppAlusDokKd(String value) {
                this.suheLoppAlusDokKd = value;
            }

            /**
             * Gets the value of the suheLoodi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheLoodi() {
                return suheLoodi;
            }

            /**
             * Sets the value of the suheLoodi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheLoodi(String value) {
                this.suheLoodi = value;
            }

            /**
             * Gets the value of the suheMuudeti property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheMuudeti() {
                return suheMuudeti;
            }

            /**
             * Sets the value of the suheMuudeti property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheMuudeti(String value) {
                this.suheMuudeti = value;
            }

        }

    }

}
