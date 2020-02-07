
package ee.hois.xroad.rahvastikuregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR448ResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR448ResponseType"&gt;
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
 *                             &lt;element name="Isikuandmed.ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.EndisedKirjed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Topeltisik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Synniperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.IsikuPohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.LapsendamiseTunnus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.PohiKodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.PohiKodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Kodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SynnikohaRiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SynnikohaRiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Surmakoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SurmakohaRiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SurmakohaRiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Perekonnaseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.SaabEestisse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.ViimatiPar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Isikuandmed.KoviSaabus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                             &lt;element name="Dokumendiandmed.AsutusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Sisestatud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokSyndKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Markus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.IsikID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokVanaPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.DokVanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.TeineID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Teised_IsikIskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Teised_DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Teised_DokVanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Teised_DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Teised_DokVanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Dokumendiandmed.Teised_Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                             &lt;element name="Suhe.TeineID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Elukoha_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.ADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.ADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.Lopukp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Suhe.IsikID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                             &lt;element name="Elukohaandmed.VkohaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Korterinr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="Elukohaandmed.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.ADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.ADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *                             &lt;element name="Elukohaandmed.Algusekuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.Lopukuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.AadressiStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.AadressiAlguseAlus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Elukohaandmed.AadressiLopuAlus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                             &lt;element name="Kontaktandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *         &lt;element name="Hooldusoigus"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Hooldusoigused" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Hooldusoigused.hoRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoSisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoTeineID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoTeineIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoTeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoTeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.hoLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Hooldusoigused.IsikID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "RR448ResponseType", propOrder = {
    "veakood",
    "veatekst",
    "isikud",
    "dokumendid",
    "suhted",
    "elukohad",
    "kontaktid",
    "hooldusoigus"
})
public class RR448ResponseType
    extends XRoadResponseBaseType
{

    @XmlElement(name = "Veakood")
    protected BigInteger veakood;
    @XmlElement(name = "Veatekst")
    protected String veatekst;
    @XmlElement(name = "Isikud", required = true)
    protected RR448ResponseType.Isikud isikud;
    @XmlElement(name = "Dokumendid", required = true)
    protected RR448ResponseType.Dokumendid dokumendid;
    @XmlElement(name = "Suhted", required = true)
    protected RR448ResponseType.Suhted suhted;
    @XmlElement(name = "Elukohad", required = true)
    protected RR448ResponseType.Elukohad elukohad;
    @XmlElement(name = "Kontaktid", required = true)
    protected RR448ResponseType.Kontaktid kontaktid;
    @XmlElement(name = "Hooldusoigus", required = true)
    protected RR448ResponseType.Hooldusoigus hooldusoigus;

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
     *     {@link RR448ResponseType.Isikud }
     *     
     */
    public RR448ResponseType.Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR448ResponseType.Isikud }
     *     
     */
    public void setIsikud(RR448ResponseType.Isikud value) {
        this.isikud = value;
    }

    /**
     * Gets the value of the dokumendid property.
     * 
     * @return
     *     possible object is
     *     {@link RR448ResponseType.Dokumendid }
     *     
     */
    public RR448ResponseType.Dokumendid getDokumendid() {
        return dokumendid;
    }

    /**
     * Sets the value of the dokumendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR448ResponseType.Dokumendid }
     *     
     */
    public void setDokumendid(RR448ResponseType.Dokumendid value) {
        this.dokumendid = value;
    }

    /**
     * Gets the value of the suhted property.
     * 
     * @return
     *     possible object is
     *     {@link RR448ResponseType.Suhted }
     *     
     */
    public RR448ResponseType.Suhted getSuhted() {
        return suhted;
    }

    /**
     * Sets the value of the suhted property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR448ResponseType.Suhted }
     *     
     */
    public void setSuhted(RR448ResponseType.Suhted value) {
        this.suhted = value;
    }

    /**
     * Gets the value of the elukohad property.
     * 
     * @return
     *     possible object is
     *     {@link RR448ResponseType.Elukohad }
     *     
     */
    public RR448ResponseType.Elukohad getElukohad() {
        return elukohad;
    }

    /**
     * Sets the value of the elukohad property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR448ResponseType.Elukohad }
     *     
     */
    public void setElukohad(RR448ResponseType.Elukohad value) {
        this.elukohad = value;
    }

    /**
     * Gets the value of the kontaktid property.
     * 
     * @return
     *     possible object is
     *     {@link RR448ResponseType.Kontaktid }
     *     
     */
    public RR448ResponseType.Kontaktid getKontaktid() {
        return kontaktid;
    }

    /**
     * Sets the value of the kontaktid property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR448ResponseType.Kontaktid }
     *     
     */
    public void setKontaktid(RR448ResponseType.Kontaktid value) {
        this.kontaktid = value;
    }

    /**
     * Gets the value of the hooldusoigus property.
     * 
     * @return
     *     possible object is
     *     {@link RR448ResponseType.Hooldusoigus }
     *     
     */
    public RR448ResponseType.Hooldusoigus getHooldusoigus() {
        return hooldusoigus;
    }

    /**
     * Sets the value of the hooldusoigus property.
     * 
     * @param value
     *     allowed object is
     *     {@link RR448ResponseType.Hooldusoigus }
     *     
     */
    public void setHooldusoigus(RR448ResponseType.Hooldusoigus value) {
        this.hooldusoigus = value;
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
     *                   &lt;element name="Dokumendiandmed.AsutusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Sisestatud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokSyndKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Markus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.IsikID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokVanaPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.DokVanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.TeineID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Teised_IsikIskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Teised_DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Teised_DokVanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Teised_DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Teised_DokVanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Dokumendiandmed.Teised_Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR448ResponseType.Dokumendid.Dokumendiandmed> dokumendiandmed;

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
         * {@link RR448ResponseType.Dokumendid.Dokumendiandmed }
         * 
         * 
         */
        public List<RR448ResponseType.Dokumendid.Dokumendiandmed> getDokumendiandmed() {
            if (dokumendiandmed == null) {
                dokumendiandmed = new ArrayList<RR448ResponseType.Dokumendid.Dokumendiandmed>();
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
         *         &lt;element name="Dokumendiandmed.AsutusRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokValjastamisKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Sisestatud" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokSyndKuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.KehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.KehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Markus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokIsikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.IsikID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokVanaPerekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.DokVanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.TeineID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Teised_IsikIskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Teised_DokPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Teised_DokVanaPerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Teised_DokEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Teised_DokVanaEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Dokumendiandmed.Teised_Dok_osalus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "dokumendiandmedAsutusRiik",
            "dokumendiandmedAsutus",
            "dokumendiandmedDokValjastamisKuup",
            "dokumendiandmedSisestatud",
            "dokumendiandmedDokSyndKuup",
            "dokumendiandmedKehtivAlates",
            "dokumendiandmedKehtivKuni",
            "dokumendiandmedMarkus",
            "dokumendiandmedDokIsikukood",
            "dokumendiandmedIsikID",
            "dokumendiandmedDokPerekonnanimi",
            "dokumendiandmedDokVanaPerekonnanimi",
            "dokumendiandmedDokEesnimi",
            "dokumendiandmedDokVanaEesnimi",
            "dokumendiandmedDokOsalus",
            "dokumendiandmedTeineID",
            "dokumendiandmedTeisedIsikIskood",
            "dokumendiandmedTeisedDokPerenimi",
            "dokumendiandmedTeisedDokVanaPerenimi",
            "dokumendiandmedTeisedDokEesnimi",
            "dokumendiandmedTeisedDokVanaEesnimi",
            "dokumendiandmedTeisedDokOsalus"
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
            @XmlElement(name = "Dokumendiandmed.Markus", required = true)
            protected String dokumendiandmedMarkus;
            @XmlElement(name = "Dokumendiandmed.DokIsikukood", required = true)
            protected String dokumendiandmedDokIsikukood;
            @XmlElement(name = "Dokumendiandmed.IsikID", required = true)
            protected String dokumendiandmedIsikID;
            @XmlElement(name = "Dokumendiandmed.DokPerekonnanimi", required = true)
            protected String dokumendiandmedDokPerekonnanimi;
            @XmlElement(name = "Dokumendiandmed.DokVanaPerekonnanimi", required = true)
            protected String dokumendiandmedDokVanaPerekonnanimi;
            @XmlElement(name = "Dokumendiandmed.DokEesnimi", required = true)
            protected String dokumendiandmedDokEesnimi;
            @XmlElement(name = "Dokumendiandmed.DokVanaEesnimi", required = true)
            protected String dokumendiandmedDokVanaEesnimi;
            @XmlElement(name = "Dokumendiandmed.Dok_osalus", required = true)
            protected String dokumendiandmedDokOsalus;
            @XmlElement(name = "Dokumendiandmed.TeineID", required = true)
            protected String dokumendiandmedTeineID;
            @XmlElement(name = "Dokumendiandmed.Teised_IsikIskood", required = true)
            protected String dokumendiandmedTeisedIsikIskood;
            @XmlElement(name = "Dokumendiandmed.Teised_DokPerenimi", required = true)
            protected String dokumendiandmedTeisedDokPerenimi;
            @XmlElement(name = "Dokumendiandmed.Teised_DokVanaPerenimi", required = true)
            protected String dokumendiandmedTeisedDokVanaPerenimi;
            @XmlElement(name = "Dokumendiandmed.Teised_DokEesnimi", required = true)
            protected String dokumendiandmedTeisedDokEesnimi;
            @XmlElement(name = "Dokumendiandmed.Teised_DokVanaEesnimi", required = true)
            protected String dokumendiandmedTeisedDokVanaEesnimi;
            @XmlElement(name = "Dokumendiandmed.Teised_Dok_osalus", required = true)
            protected String dokumendiandmedTeisedDokOsalus;

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
             * Gets the value of the dokumendiandmedMarkus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedMarkus() {
                return dokumendiandmedMarkus;
            }

            /**
             * Sets the value of the dokumendiandmedMarkus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedMarkus(String value) {
                this.dokumendiandmedMarkus = value;
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
             * Gets the value of the dokumendiandmedIsikID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedIsikID() {
                return dokumendiandmedIsikID;
            }

            /**
             * Sets the value of the dokumendiandmedIsikID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedIsikID(String value) {
                this.dokumendiandmedIsikID = value;
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
             * Gets the value of the dokumendiandmedDokVanaPerekonnanimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedDokVanaPerekonnanimi() {
                return dokumendiandmedDokVanaPerekonnanimi;
            }

            /**
             * Sets the value of the dokumendiandmedDokVanaPerekonnanimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedDokVanaPerekonnanimi(String value) {
                this.dokumendiandmedDokVanaPerekonnanimi = value;
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
             * Gets the value of the dokumendiandmedTeineID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedTeineID() {
                return dokumendiandmedTeineID;
            }

            /**
             * Sets the value of the dokumendiandmedTeineID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedTeineID(String value) {
                this.dokumendiandmedTeineID = value;
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
             * Gets the value of the dokumendiandmedTeisedDokVanaPerenimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedTeisedDokVanaPerenimi() {
                return dokumendiandmedTeisedDokVanaPerenimi;
            }

            /**
             * Sets the value of the dokumendiandmedTeisedDokVanaPerenimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedTeisedDokVanaPerenimi(String value) {
                this.dokumendiandmedTeisedDokVanaPerenimi = value;
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
             * Gets the value of the dokumendiandmedTeisedDokVanaEesnimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDokumendiandmedTeisedDokVanaEesnimi() {
                return dokumendiandmedTeisedDokVanaEesnimi;
            }

            /**
             * Sets the value of the dokumendiandmedTeisedDokVanaEesnimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDokumendiandmedTeisedDokVanaEesnimi(String value) {
                this.dokumendiandmedTeisedDokVanaEesnimi = value;
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
     *                   &lt;element name="Elukohaandmed.VkohaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Korterinr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="Elukohaandmed.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.ADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.ADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
     *                   &lt;element name="Elukohaandmed.Algusekuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.Lopukuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.AadressiStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.AadressiAlguseAlus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Elukohaandmed.AadressiLopuAlus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR448ResponseType.Elukohad.Elukohaandmed> elukohaandmed;

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
         * {@link RR448ResponseType.Elukohad.Elukohaandmed }
         * 
         * 
         */
        public List<RR448ResponseType.Elukohad.Elukohaandmed> getElukohaandmed() {
            if (elukohaandmed == null) {
                elukohaandmed = new ArrayList<RR448ResponseType.Elukohad.Elukohaandmed>();
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
         *         &lt;element name="Elukohaandmed.VkohaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Tanav" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Majanr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Korterinr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.AadressTekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Postiindeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *         &lt;element name="Elukohaandmed.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.ADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.ADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
         *         &lt;element name="Elukohaandmed.Algusekuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.Lopukuup" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.AadressiLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.AadressiStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.AadressiAlguseAlus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Elukohaandmed.AadressiLopuAlus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "elukohaandmedVkohaNimetus",
            "elukohaandmedTanav",
            "elukohaandmedNimi",
            "elukohaandmedMajanr",
            "elukohaandmedKorterinr",
            "elukohaandmedAadressTekstina",
            "elukohaandmedPostiindeks",
            "elukohaandmedADSADRID",
            "elukohaandmedADSOID",
            "elukohaandmedADSKOODAADRESS",
            "elukohaandmedADSADOBID",
            "elukohaandmedAlgusekuup",
            "elukohaandmedLopukuup",
            "elukohaandmedAadressiLiik",
            "elukohaandmedAadressiStaatus",
            "elukohaandmedAadressiAlguseAlus",
            "elukohaandmedAadressiLopuAlus"
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
            @XmlElement(name = "Elukohaandmed.VkohaNimetus", required = true)
            protected String elukohaandmedVkohaNimetus;
            @XmlElement(name = "Elukohaandmed.Tanav", required = true)
            protected String elukohaandmedTanav;
            @XmlElement(name = "Elukohaandmed.Nimi", required = true)
            protected String elukohaandmedNimi;
            @XmlElement(name = "Elukohaandmed.Majanr", required = true)
            protected String elukohaandmedMajanr;
            @XmlElement(name = "Elukohaandmed.Korterinr", required = true)
            protected String elukohaandmedKorterinr;
            @XmlElement(name = "Elukohaandmed.AadressTekstina", required = true)
            protected String elukohaandmedAadressTekstina;
            @XmlElement(name = "Elukohaandmed.Postiindeks", required = true)
            protected String elukohaandmedPostiindeks;
            @XmlElement(name = "Elukohaandmed.ADS_ADR_ID", required = true)
            protected BigInteger elukohaandmedADSADRID;
            @XmlElement(name = "Elukohaandmed.ADS_OID", required = true)
            protected String elukohaandmedADSOID;
            @XmlElement(name = "Elukohaandmed.ADS_KOODAADRESS", required = true)
            protected String elukohaandmedADSKOODAADRESS;
            @XmlElement(name = "Elukohaandmed.ADS_ADOB_ID", required = true)
            protected BigInteger elukohaandmedADSADOBID;
            @XmlElement(name = "Elukohaandmed.Algusekuup", required = true)
            protected String elukohaandmedAlgusekuup;
            @XmlElement(name = "Elukohaandmed.Lopukuup", required = true)
            protected String elukohaandmedLopukuup;
            @XmlElement(name = "Elukohaandmed.AadressiLiik", required = true)
            protected String elukohaandmedAadressiLiik;
            @XmlElement(name = "Elukohaandmed.AadressiStaatus", required = true)
            protected String elukohaandmedAadressiStaatus;
            @XmlElement(name = "Elukohaandmed.AadressiAlguseAlus", required = true)
            protected String elukohaandmedAadressiAlguseAlus;
            @XmlElement(name = "Elukohaandmed.AadressiLopuAlus", required = true)
            protected String elukohaandmedAadressiLopuAlus;

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
             * Gets the value of the elukohaandmedVkohaNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedVkohaNimetus() {
                return elukohaandmedVkohaNimetus;
            }

            /**
             * Sets the value of the elukohaandmedVkohaNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedVkohaNimetus(String value) {
                this.elukohaandmedVkohaNimetus = value;
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
             * Gets the value of the elukohaandmedNimi property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedNimi() {
                return elukohaandmedNimi;
            }

            /**
             * Sets the value of the elukohaandmedNimi property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedNimi(String value) {
                this.elukohaandmedNimi = value;
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
             * Gets the value of the elukohaandmedADSADRID property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getElukohaandmedADSADRID() {
                return elukohaandmedADSADRID;
            }

            /**
             * Sets the value of the elukohaandmedADSADRID property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setElukohaandmedADSADRID(BigInteger value) {
                this.elukohaandmedADSADRID = value;
            }

            /**
             * Gets the value of the elukohaandmedADSOID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedADSOID() {
                return elukohaandmedADSOID;
            }

            /**
             * Sets the value of the elukohaandmedADSOID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedADSOID(String value) {
                this.elukohaandmedADSOID = value;
            }

            /**
             * Gets the value of the elukohaandmedADSKOODAADRESS property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedADSKOODAADRESS() {
                return elukohaandmedADSKOODAADRESS;
            }

            /**
             * Sets the value of the elukohaandmedADSKOODAADRESS property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedADSKOODAADRESS(String value) {
                this.elukohaandmedADSKOODAADRESS = value;
            }

            /**
             * Gets the value of the elukohaandmedADSADOBID property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getElukohaandmedADSADOBID() {
                return elukohaandmedADSADOBID;
            }

            /**
             * Sets the value of the elukohaandmedADSADOBID property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setElukohaandmedADSADOBID(BigInteger value) {
                this.elukohaandmedADSADOBID = value;
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
             * Gets the value of the elukohaandmedAadressiStaatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedAadressiStaatus() {
                return elukohaandmedAadressiStaatus;
            }

            /**
             * Sets the value of the elukohaandmedAadressiStaatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedAadressiStaatus(String value) {
                this.elukohaandmedAadressiStaatus = value;
            }

            /**
             * Gets the value of the elukohaandmedAadressiAlguseAlus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedAadressiAlguseAlus() {
                return elukohaandmedAadressiAlguseAlus;
            }

            /**
             * Sets the value of the elukohaandmedAadressiAlguseAlus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedAadressiAlguseAlus(String value) {
                this.elukohaandmedAadressiAlguseAlus = value;
            }

            /**
             * Gets the value of the elukohaandmedAadressiLopuAlus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getElukohaandmedAadressiLopuAlus() {
                return elukohaandmedAadressiLopuAlus;
            }

            /**
             * Sets the value of the elukohaandmedAadressiLopuAlus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setElukohaandmedAadressiLopuAlus(String value) {
                this.elukohaandmedAadressiLopuAlus = value;
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
     *         &lt;element name="Hooldusoigused" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Hooldusoigused.hoRoll" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoSisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoTeineID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoTeineIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoTeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoTeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.hoLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Hooldusoigused.IsikID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "hooldusoigused"
    })
    public static class Hooldusoigus {

        @XmlElement(name = "Hooldusoigused")
        protected List<RR448ResponseType.Hooldusoigus.Hooldusoigused> hooldusoigused;

        /**
         * Gets the value of the hooldusoigused property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hooldusoigused property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHooldusoigused().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RR448ResponseType.Hooldusoigus.Hooldusoigused }
         * 
         * 
         */
        public List<RR448ResponseType.Hooldusoigus.Hooldusoigused> getHooldusoigused() {
            if (hooldusoigused == null) {
                hooldusoigused = new ArrayList<RR448ResponseType.Hooldusoigus.Hooldusoigused>();
            }
            return this.hooldusoigused;
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
         *         &lt;element name="Hooldusoigused.hoTeineID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoTeineIK" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoTeineEesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoTeinePerenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoOlek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoAlgus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.hoLopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Hooldusoigused.IsikID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "hooldusoigusedHoTeineID",
            "hooldusoigusedHoTeineIK",
            "hooldusoigusedHoTeineEesnimi",
            "hooldusoigusedHoTeinePerenimi",
            "hooldusoigusedHoOlek",
            "hooldusoigusedHoAlgus",
            "hooldusoigusedHoLopp",
            "hooldusoigusedIsikID"
        })
        public static class Hooldusoigused {

            @XmlElement(name = "Hooldusoigused.hoRoll", required = true)
            protected String hooldusoigusedHoRoll;
            @XmlElement(name = "Hooldusoigused.hoLiik", required = true)
            protected String hooldusoigusedHoLiik;
            @XmlElement(name = "Hooldusoigused.hoSisu", required = true)
            protected String hooldusoigusedHoSisu;
            @XmlElement(name = "Hooldusoigused.hoTeineID", required = true)
            protected String hooldusoigusedHoTeineID;
            @XmlElement(name = "Hooldusoigused.hoTeineIK", required = true)
            protected String hooldusoigusedHoTeineIK;
            @XmlElement(name = "Hooldusoigused.hoTeineEesnimi", required = true)
            protected String hooldusoigusedHoTeineEesnimi;
            @XmlElement(name = "Hooldusoigused.hoTeinePerenimi", required = true)
            protected String hooldusoigusedHoTeinePerenimi;
            @XmlElement(name = "Hooldusoigused.hoOlek", required = true)
            protected String hooldusoigusedHoOlek;
            @XmlElement(name = "Hooldusoigused.hoAlgus", required = true)
            protected String hooldusoigusedHoAlgus;
            @XmlElement(name = "Hooldusoigused.hoLopp", required = true)
            protected String hooldusoigusedHoLopp;
            @XmlElement(name = "Hooldusoigused.IsikID", required = true)
            protected String hooldusoigusedIsikID;

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
             * Gets the value of the hooldusoigusedHoTeineID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoTeineID() {
                return hooldusoigusedHoTeineID;
            }

            /**
             * Sets the value of the hooldusoigusedHoTeineID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoTeineID(String value) {
                this.hooldusoigusedHoTeineID = value;
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
             * Gets the value of the hooldusoigusedHoOlek property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedHoOlek() {
                return hooldusoigusedHoOlek;
            }

            /**
             * Sets the value of the hooldusoigusedHoOlek property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedHoOlek(String value) {
                this.hooldusoigusedHoOlek = value;
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
             * Gets the value of the hooldusoigusedIsikID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHooldusoigusedIsikID() {
                return hooldusoigusedIsikID;
            }

            /**
             * Sets the value of the hooldusoigusedIsikID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHooldusoigusedIsikID(String value) {
                this.hooldusoigusedIsikID = value;
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
     *                   &lt;element name="Isikuandmed.ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.EndisedKirjed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Topeltisik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Synniperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.IsikuPohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.LapsendamiseTunnus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.PohiKodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.PohiKodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Kodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SynnikohaRiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SynnikohaRiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Surmakoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SurmakohaRiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SurmakohaRiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Perekonnaseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.SaabEestisse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.ViimatiPar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Isikuandmed.KoviSaabus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR448ResponseType.Isikud.Isikuandmed> isikuandmed;

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
         * {@link RR448ResponseType.Isikud.Isikuandmed }
         * 
         * 
         */
        public List<RR448ResponseType.Isikud.Isikuandmed> getIsikuandmed() {
            if (isikuandmed == null) {
                isikuandmed = new ArrayList<RR448ResponseType.Isikud.Isikuandmed>();
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
         *         &lt;element name="Isikuandmed.ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.EndisedKirjed" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Topeltisik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Meesnm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Mperenm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Synniperenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Sugu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.KirjeStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.IsikuPohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Synniaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Surmaaeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.LapsendamiseTunnus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.PohiKodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.PohiKodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Kodakondsuskood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Kodakondsustekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SynnikohaRiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SynnikohaRiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Surmakoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SurmakohaRiigiKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SurmakohaRiigiNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Perekonnaseis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Teovoime" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.SaabEestisse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.ViimatiPar" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Emakeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Rahvus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Haridus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Tegevusala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Isikuandmed.KoviSaabus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "isikuandmedID",
            "isikuandmedEndisedKirjed",
            "isikuandmedTopeltisik",
            "isikuandmedEesnimi",
            "isikuandmedMeesnm",
            "isikuandmedPerenimi",
            "isikuandmedMperenm",
            "isikuandmedSynniperenimi",
            "isikuandmedSugu",
            "isikuandmedIsikuStaatus",
            "isikuandmedKirjeStaatus",
            "isikuandmedIsikuPohjus",
            "isikuandmedSynniaeg",
            "isikuandmedSurmaaeg",
            "isikuandmedLapsendamiseTunnus",
            "isikuandmedPohiKodakondsuskood",
            "isikuandmedPohiKodakondsustekstina",
            "isikuandmedKodakondsuskood",
            "isikuandmedKodakondsustekstina",
            "isikuandmedSynnikoht",
            "isikuandmedSynnikohaRiigiKood",
            "isikuandmedSynnikohaRiigiNimetus",
            "isikuandmedSurmakoht",
            "isikuandmedSurmakohaRiigiKood",
            "isikuandmedSurmakohaRiigiNimetus",
            "isikuandmedPerekonnaseis",
            "isikuandmedTeovoime",
            "isikuandmedSaabEestisse",
            "isikuandmedViimatiPar",
            "isikuandmedEmakeel",
            "isikuandmedRahvus",
            "isikuandmedHaridus",
            "isikuandmedTegevusala",
            "isikuandmedIsanimi",
            "isikuandmedKoviSaabus"
        })
        public static class Isikuandmed {

            @XmlElement(name = "Isikuandmed.Isikukood", required = true)
            protected String isikuandmedIsikukood;
            @XmlElement(name = "Isikuandmed.ID", required = true)
            protected String isikuandmedID;
            @XmlElement(name = "Isikuandmed.EndisedKirjed", required = true)
            protected String isikuandmedEndisedKirjed;
            @XmlElement(name = "Isikuandmed.Topeltisik", required = true)
            protected String isikuandmedTopeltisik;
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
            @XmlElement(name = "Isikuandmed.KirjeStaatus", required = true)
            protected String isikuandmedKirjeStaatus;
            @XmlElement(name = "Isikuandmed.IsikuPohjus", required = true)
            protected String isikuandmedIsikuPohjus;
            @XmlElement(name = "Isikuandmed.Synniaeg", required = true)
            protected String isikuandmedSynniaeg;
            @XmlElement(name = "Isikuandmed.Surmaaeg", required = true)
            protected String isikuandmedSurmaaeg;
            @XmlElement(name = "Isikuandmed.LapsendamiseTunnus", required = true)
            protected String isikuandmedLapsendamiseTunnus;
            @XmlElement(name = "Isikuandmed.PohiKodakondsuskood", required = true)
            protected String isikuandmedPohiKodakondsuskood;
            @XmlElement(name = "Isikuandmed.PohiKodakondsustekstina", required = true)
            protected String isikuandmedPohiKodakondsustekstina;
            @XmlElement(name = "Isikuandmed.Kodakondsuskood", required = true)
            protected String isikuandmedKodakondsuskood;
            @XmlElement(name = "Isikuandmed.Kodakondsustekstina", required = true)
            protected String isikuandmedKodakondsustekstina;
            @XmlElement(name = "Isikuandmed.Synnikoht", required = true)
            protected String isikuandmedSynnikoht;
            @XmlElement(name = "Isikuandmed.SynnikohaRiigiKood", required = true)
            protected String isikuandmedSynnikohaRiigiKood;
            @XmlElement(name = "Isikuandmed.SynnikohaRiigiNimetus", required = true)
            protected String isikuandmedSynnikohaRiigiNimetus;
            @XmlElement(name = "Isikuandmed.Surmakoht", required = true)
            protected String isikuandmedSurmakoht;
            @XmlElement(name = "Isikuandmed.SurmakohaRiigiKood", required = true)
            protected String isikuandmedSurmakohaRiigiKood;
            @XmlElement(name = "Isikuandmed.SurmakohaRiigiNimetus", required = true)
            protected String isikuandmedSurmakohaRiigiNimetus;
            @XmlElement(name = "Isikuandmed.Perekonnaseis", required = true)
            protected String isikuandmedPerekonnaseis;
            @XmlElement(name = "Isikuandmed.Teovoime", required = true)
            protected String isikuandmedTeovoime;
            @XmlElement(name = "Isikuandmed.SaabEestisse", required = true)
            protected String isikuandmedSaabEestisse;
            @XmlElement(name = "Isikuandmed.ViimatiPar", required = true)
            protected String isikuandmedViimatiPar;
            @XmlElement(name = "Isikuandmed.Emakeel", required = true)
            protected String isikuandmedEmakeel;
            @XmlElement(name = "Isikuandmed.Rahvus", required = true)
            protected String isikuandmedRahvus;
            @XmlElement(name = "Isikuandmed.Haridus", required = true)
            protected String isikuandmedHaridus;
            @XmlElement(name = "Isikuandmed.Tegevusala", required = true)
            protected String isikuandmedTegevusala;
            @XmlElement(name = "Isikuandmed.Isanimi", required = true)
            protected String isikuandmedIsanimi;
            @XmlElement(name = "Isikuandmed.KoviSaabus", required = true)
            protected String isikuandmedKoviSaabus;

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
             * Gets the value of the isikuandmedID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedID() {
                return isikuandmedID;
            }

            /**
             * Sets the value of the isikuandmedID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedID(String value) {
                this.isikuandmedID = value;
            }

            /**
             * Gets the value of the isikuandmedEndisedKirjed property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedEndisedKirjed() {
                return isikuandmedEndisedKirjed;
            }

            /**
             * Sets the value of the isikuandmedEndisedKirjed property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedEndisedKirjed(String value) {
                this.isikuandmedEndisedKirjed = value;
            }

            /**
             * Gets the value of the isikuandmedTopeltisik property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedTopeltisik() {
                return isikuandmedTopeltisik;
            }

            /**
             * Sets the value of the isikuandmedTopeltisik property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedTopeltisik(String value) {
                this.isikuandmedTopeltisik = value;
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
             * Gets the value of the isikuandmedIsikuPohjus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedIsikuPohjus() {
                return isikuandmedIsikuPohjus;
            }

            /**
             * Sets the value of the isikuandmedIsikuPohjus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedIsikuPohjus(String value) {
                this.isikuandmedIsikuPohjus = value;
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
             * Gets the value of the isikuandmedLapsendamiseTunnus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedLapsendamiseTunnus() {
                return isikuandmedLapsendamiseTunnus;
            }

            /**
             * Sets the value of the isikuandmedLapsendamiseTunnus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedLapsendamiseTunnus(String value) {
                this.isikuandmedLapsendamiseTunnus = value;
            }

            /**
             * Gets the value of the isikuandmedPohiKodakondsuskood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedPohiKodakondsuskood() {
                return isikuandmedPohiKodakondsuskood;
            }

            /**
             * Sets the value of the isikuandmedPohiKodakondsuskood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedPohiKodakondsuskood(String value) {
                this.isikuandmedPohiKodakondsuskood = value;
            }

            /**
             * Gets the value of the isikuandmedPohiKodakondsustekstina property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedPohiKodakondsustekstina() {
                return isikuandmedPohiKodakondsustekstina;
            }

            /**
             * Sets the value of the isikuandmedPohiKodakondsustekstina property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedPohiKodakondsustekstina(String value) {
                this.isikuandmedPohiKodakondsustekstina = value;
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
             * Gets the value of the isikuandmedSynnikohaRiigiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSynnikohaRiigiKood() {
                return isikuandmedSynnikohaRiigiKood;
            }

            /**
             * Sets the value of the isikuandmedSynnikohaRiigiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSynnikohaRiigiKood(String value) {
                this.isikuandmedSynnikohaRiigiKood = value;
            }

            /**
             * Gets the value of the isikuandmedSynnikohaRiigiNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSynnikohaRiigiNimetus() {
                return isikuandmedSynnikohaRiigiNimetus;
            }

            /**
             * Sets the value of the isikuandmedSynnikohaRiigiNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSynnikohaRiigiNimetus(String value) {
                this.isikuandmedSynnikohaRiigiNimetus = value;
            }

            /**
             * Gets the value of the isikuandmedSurmakoht property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSurmakoht() {
                return isikuandmedSurmakoht;
            }

            /**
             * Sets the value of the isikuandmedSurmakoht property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSurmakoht(String value) {
                this.isikuandmedSurmakoht = value;
            }

            /**
             * Gets the value of the isikuandmedSurmakohaRiigiKood property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSurmakohaRiigiKood() {
                return isikuandmedSurmakohaRiigiKood;
            }

            /**
             * Sets the value of the isikuandmedSurmakohaRiigiKood property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSurmakohaRiigiKood(String value) {
                this.isikuandmedSurmakohaRiigiKood = value;
            }

            /**
             * Gets the value of the isikuandmedSurmakohaRiigiNimetus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIsikuandmedSurmakohaRiigiNimetus() {
                return isikuandmedSurmakohaRiigiNimetus;
            }

            /**
             * Sets the value of the isikuandmedSurmakohaRiigiNimetus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIsikuandmedSurmakohaRiigiNimetus(String value) {
                this.isikuandmedSurmakohaRiigiNimetus = value;
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
     *                   &lt;element name="Kontaktandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR448ResponseType.Kontaktid.Kontaktandmed> kontaktandmed;

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
         * {@link RR448ResponseType.Kontaktid.Kontaktandmed }
         * 
         * 
         */
        public List<RR448ResponseType.Kontaktid.Kontaktandmed> getKontaktandmed() {
            if (kontaktandmed == null) {
                kontaktandmed = new ArrayList<RR448ResponseType.Kontaktid.Kontaktandmed>();
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
         *         &lt;element name="Kontaktandmed.Asutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "kontaktandmedAsutus"
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
            @XmlElement(name = "Kontaktandmed.Asutus", required = true)
            protected String kontaktandmedAsutus;

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
             * Gets the value of the kontaktandmedAsutus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKontaktandmedAsutus() {
                return kontaktandmedAsutus;
            }

            /**
             * Sets the value of the kontaktandmedAsutus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKontaktandmedAsutus(String value) {
                this.kontaktandmedAsutus = value;
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
     *                   &lt;element name="Suhe.TeineID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Elukoha_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.ADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.ADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.Lopukp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Suhe.IsikID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        protected List<RR448ResponseType.Suhted.Suhe> suhe;

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
         * {@link RR448ResponseType.Suhted.Suhe }
         * 
         * 
         */
        public List<RR448ResponseType.Suhted.Suhe> getSuhe() {
            if (suhe == null) {
                suhe = new ArrayList<RR448ResponseType.Suhted.Suhe>();
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
         *         &lt;element name="Suhe.TeineID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Isanimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Elukoha_aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.ADS_ADR_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.ADS_OID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.ADS_KOODAADRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.ADS_ADOB_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Synnikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.IsikuStaatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Alguskp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.Lopukp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Suhe.IsikID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "suheTeineID",
            "suheIsikukood",
            "suhePerenimi",
            "suheEesnimi",
            "suheIsanimi",
            "suheElukohaAadress",
            "suheADSADRID",
            "suheADSOID",
            "suheADSKOODAADRESS",
            "suheADSADOBID",
            "suheSynnikoht",
            "suheIsikuStaatus",
            "suheStaatus",
            "suheAlguskp",
            "suheLopukp",
            "suheIsikID"
        })
        public static class Suhe {

            @XmlElement(name = "Suhe.Suhtetyyp", required = true)
            protected String suheSuhtetyyp;
            @XmlElement(name = "Suhe.TeineID", required = true)
            protected String suheTeineID;
            @XmlElement(name = "Suhe.Isikukood", required = true)
            protected String suheIsikukood;
            @XmlElement(name = "Suhe.Perenimi", required = true)
            protected String suhePerenimi;
            @XmlElement(name = "Suhe.Eesnimi", required = true)
            protected String suheEesnimi;
            @XmlElement(name = "Suhe.Isanimi", required = true)
            protected String suheIsanimi;
            @XmlElement(name = "Suhe.Elukoha_aadress", required = true)
            protected String suheElukohaAadress;
            @XmlElement(name = "Suhe.ADS_ADR_ID", required = true)
            protected String suheADSADRID;
            @XmlElement(name = "Suhe.ADS_OID", required = true)
            protected String suheADSOID;
            @XmlElement(name = "Suhe.ADS_KOODAADRESS", required = true)
            protected String suheADSKOODAADRESS;
            @XmlElement(name = "Suhe.ADS_ADOB_ID", required = true)
            protected String suheADSADOBID;
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
            @XmlElement(name = "Suhe.IsikID", required = true)
            protected String suheIsikID;

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
             * Gets the value of the suheTeineID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheTeineID() {
                return suheTeineID;
            }

            /**
             * Sets the value of the suheTeineID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheTeineID(String value) {
                this.suheTeineID = value;
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
             * Gets the value of the suheElukohaAadress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheElukohaAadress() {
                return suheElukohaAadress;
            }

            /**
             * Sets the value of the suheElukohaAadress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheElukohaAadress(String value) {
                this.suheElukohaAadress = value;
            }

            /**
             * Gets the value of the suheADSADRID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheADSADRID() {
                return suheADSADRID;
            }

            /**
             * Sets the value of the suheADSADRID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheADSADRID(String value) {
                this.suheADSADRID = value;
            }

            /**
             * Gets the value of the suheADSOID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheADSOID() {
                return suheADSOID;
            }

            /**
             * Sets the value of the suheADSOID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheADSOID(String value) {
                this.suheADSOID = value;
            }

            /**
             * Gets the value of the suheADSKOODAADRESS property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheADSKOODAADRESS() {
                return suheADSKOODAADRESS;
            }

            /**
             * Sets the value of the suheADSKOODAADRESS property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheADSKOODAADRESS(String value) {
                this.suheADSKOODAADRESS = value;
            }

            /**
             * Gets the value of the suheADSADOBID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheADSADOBID() {
                return suheADSADOBID;
            }

            /**
             * Sets the value of the suheADSADOBID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheADSADOBID(String value) {
                this.suheADSADOBID = value;
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
             * Gets the value of the suheIsikID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuheIsikID() {
                return suheIsikID;
            }

            /**
             * Sets the value of the suheIsikID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuheIsikID(String value) {
                this.suheIsikID = value;
            }

        }

    }

}
