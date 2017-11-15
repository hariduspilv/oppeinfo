
package ee.hois.soap.dds.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ee.hois.soap.dds.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DataFileDigestId_QNAME = new QName("", "Id");
    private final static QName _DataFileDigestDigestType_QNAME = new QName("", "DigestType");
    private final static QName _DataFileDigestDigestValue_QNAME = new QName("", "DigestValue");
    private final static QName _DataFileDigestMimeType_QNAME = new QName("", "MimeType");
    private final static QName _SignatureModuleName_QNAME = new QName("", "Name");
    private final static QName _SignatureModuleType_QNAME = new QName("", "Type");
    private final static QName _SignatureModuleLocation_QNAME = new QName("", "Location");
    private final static QName _SignatureModuleContentType_QNAME = new QName("", "ContentType");
    private final static QName _SignatureModuleContent_QNAME = new QName("", "Content");
    private final static QName _DataFileDataFilename_QNAME = new QName("", "Filename");
    private final static QName _DataFileDataDfData_QNAME = new QName("", "DfData");
    private final static QName _SignedDocInfoFormat_QNAME = new QName("", "Format");
    private final static QName _SignedDocInfoVersion_QNAME = new QName("", "Version");
    private final static QName _SignatureInfoStatus_QNAME = new QName("", "Status");
    private final static QName _SignatureInfoError_QNAME = new QName("", "Error");
    private final static QName _SignatureInfoSigningTime_QNAME = new QName("", "SigningTime");
    private final static QName _SignatureInfoSignatureProductionPlace_QNAME = new QName("", "SignatureProductionPlace");
    private final static QName _SignatureInfoSigner_QNAME = new QName("", "Signer");
    private final static QName _SignatureInfoConfirmation_QNAME = new QName("", "Confirmation");
    private final static QName _SignatureInfoCRLInfo_QNAME = new QName("", "CRLInfo");
    private final static QName _ErrorCategory_QNAME = new QName("", "Category");
    private final static QName _ErrorDescription_QNAME = new QName("", "Description");
    private final static QName _CRLInfoIssuer_QNAME = new QName("", "Issuer");
    private final static QName _CRLInfoLastUpdate_QNAME = new QName("", "LastUpdate");
    private final static QName _CRLInfoNextUpdate_QNAME = new QName("", "NextUpdate");
    private final static QName _RevokedInfoSerialNumber_QNAME = new QName("", "SerialNumber");
    private final static QName _RevokedInfoRevocationDate_QNAME = new QName("", "RevocationDate");
    private final static QName _TstInfoCreationTime_QNAME = new QName("", "CreationTime");
    private final static QName _TstInfoPolicy_QNAME = new QName("", "Policy");
    private final static QName _TstInfoErrorBound_QNAME = new QName("", "ErrorBound");
    private final static QName _TstInfoTSA_QNAME = new QName("", "TSA");
    private final static QName _TstInfoCertificate_QNAME = new QName("", "Certificate");
    private final static QName _ConfirmationInfoResponderID_QNAME = new QName("", "ResponderID");
    private final static QName _ConfirmationInfoProducedAt_QNAME = new QName("", "ProducedAt");
    private final static QName _ConfirmationInfoResponderCertificate_QNAME = new QName("", "ResponderCertificate");
    private final static QName _SignerInfoCommonName_QNAME = new QName("", "CommonName");
    private final static QName _SignerInfoIDCode_QNAME = new QName("", "IDCode");
    private final static QName _CertificateInfoSubject_QNAME = new QName("", "Subject");
    private final static QName _CertificateInfoValidFrom_QNAME = new QName("", "ValidFrom");
    private final static QName _CertificateInfoValidTo_QNAME = new QName("", "ValidTo");
    private final static QName _CertificateInfoIssuerSerial_QNAME = new QName("", "IssuerSerial");
    private final static QName _CertificatePolicyOID_QNAME = new QName("", "OID");
    private final static QName _CertificatePolicyURL_QNAME = new QName("", "URL");
    private final static QName _SignatureProductionPlaceCity_QNAME = new QName("", "City");
    private final static QName _SignatureProductionPlaceStateOrProvince_QNAME = new QName("", "StateOrProvince");
    private final static QName _SignatureProductionPlacePostalCode_QNAME = new QName("", "PostalCode");
    private final static QName _SignatureProductionPlaceCountryName_QNAME = new QName("", "CountryName");
    private final static QName _SignerRoleRole_QNAME = new QName("", "Role");
    private final static QName _DataFileAttributeValue_QNAME = new QName("", "Value");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ee.hois.soap.dds.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DataFileAttribute }
     * 
     */
    public DataFileAttribute createDataFileAttribute() {
        return new DataFileAttribute();
    }

    /**
     * Create an instance of {@link DataFileInfo }
     * 
     */
    public DataFileInfo createDataFileInfo() {
        return new DataFileInfo();
    }

    /**
     * Create an instance of {@link SignerRole }
     * 
     */
    public SignerRole createSignerRole() {
        return new SignerRole();
    }

    /**
     * Create an instance of {@link SignatureProductionPlace }
     * 
     */
    public SignatureProductionPlace createSignatureProductionPlace() {
        return new SignatureProductionPlace();
    }

    /**
     * Create an instance of {@link CertificatePolicy }
     * 
     */
    public CertificatePolicy createCertificatePolicy() {
        return new CertificatePolicy();
    }

    /**
     * Create an instance of {@link CertificateInfo }
     * 
     */
    public CertificateInfo createCertificateInfo() {
        return new CertificateInfo();
    }

    /**
     * Create an instance of {@link SignerInfo }
     * 
     */
    public SignerInfo createSignerInfo() {
        return new SignerInfo();
    }

    /**
     * Create an instance of {@link ConfirmationInfo }
     * 
     */
    public ConfirmationInfo createConfirmationInfo() {
        return new ConfirmationInfo();
    }

    /**
     * Create an instance of {@link TstInfo }
     * 
     */
    public TstInfo createTstInfo() {
        return new TstInfo();
    }

    /**
     * Create an instance of {@link RevokedInfo }
     * 
     */
    public RevokedInfo createRevokedInfo() {
        return new RevokedInfo();
    }

    /**
     * Create an instance of {@link CRLInfo }
     * 
     */
    public CRLInfo createCRLInfo() {
        return new CRLInfo();
    }

    /**
     * Create an instance of {@link Error }
     * 
     */
    public Error createError() {
        return new Error();
    }

    /**
     * Create an instance of {@link SignatureInfo }
     * 
     */
    public SignatureInfo createSignatureInfo() {
        return new SignatureInfo();
    }

    /**
     * Create an instance of {@link SignedDocInfo }
     * 
     */
    public SignedDocInfo createSignedDocInfo() {
        return new SignedDocInfo();
    }

    /**
     * Create an instance of {@link DataFileData }
     * 
     */
    public DataFileData createDataFileData() {
        return new DataFileData();
    }

    /**
     * Create an instance of {@link SignatureModule }
     * 
     */
    public SignatureModule createSignatureModule() {
        return new SignatureModule();
    }

    /**
     * Create an instance of {@link SignatureModulesArray }
     * 
     */
    public SignatureModulesArray createSignatureModulesArray() {
        return new SignatureModulesArray();
    }

    /**
     * Create an instance of {@link DataFileDigest }
     * 
     */
    public DataFileDigest createDataFileDigest() {
        return new DataFileDigest();
    }

    /**
     * Create an instance of {@link DataFileDigestList }
     * 
     */
    public DataFileDigestList createDataFileDigestList() {
        return new DataFileDigestList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Id", scope = DataFileDigest.class)
    public JAXBElement<String> createDataFileDigestId(String value) {
        return new JAXBElement<String>(_DataFileDigestId_QNAME, String.class, DataFileDigest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DigestType", scope = DataFileDigest.class)
    public JAXBElement<String> createDataFileDigestDigestType(String value) {
        return new JAXBElement<String>(_DataFileDigestDigestType_QNAME, String.class, DataFileDigest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DigestValue", scope = DataFileDigest.class)
    public JAXBElement<String> createDataFileDigestDigestValue(String value) {
        return new JAXBElement<String>(_DataFileDigestDigestValue_QNAME, String.class, DataFileDigest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MimeType", scope = DataFileDigest.class)
    public JAXBElement<String> createDataFileDigestMimeType(String value) {
        return new JAXBElement<String>(_DataFileDigestMimeType_QNAME, String.class, DataFileDigest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name", scope = SignatureModule.class)
    public JAXBElement<String> createSignatureModuleName(String value) {
        return new JAXBElement<String>(_SignatureModuleName_QNAME, String.class, SignatureModule.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Type", scope = SignatureModule.class)
    public JAXBElement<String> createSignatureModuleType(String value) {
        return new JAXBElement<String>(_SignatureModuleType_QNAME, String.class, SignatureModule.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Location", scope = SignatureModule.class)
    public JAXBElement<String> createSignatureModuleLocation(String value) {
        return new JAXBElement<String>(_SignatureModuleLocation_QNAME, String.class, SignatureModule.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ContentType", scope = SignatureModule.class)
    public JAXBElement<String> createSignatureModuleContentType(String value) {
        return new JAXBElement<String>(_SignatureModuleContentType_QNAME, String.class, SignatureModule.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Content", scope = SignatureModule.class)
    public JAXBElement<String> createSignatureModuleContent(String value) {
        return new JAXBElement<String>(_SignatureModuleContent_QNAME, String.class, SignatureModule.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Id", scope = DataFileData.class)
    public JAXBElement<String> createDataFileDataId(String value) {
        return new JAXBElement<String>(_DataFileDigestId_QNAME, String.class, DataFileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Filename", scope = DataFileData.class)
    public JAXBElement<String> createDataFileDataFilename(String value) {
        return new JAXBElement<String>(_DataFileDataFilename_QNAME, String.class, DataFileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MimeType", scope = DataFileData.class)
    public JAXBElement<String> createDataFileDataMimeType(String value) {
        return new JAXBElement<String>(_DataFileDigestMimeType_QNAME, String.class, DataFileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ContentType", scope = DataFileData.class)
    public JAXBElement<String> createDataFileDataContentType(String value) {
        return new JAXBElement<String>(_SignatureModuleContentType_QNAME, String.class, DataFileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DigestType", scope = DataFileData.class)
    public JAXBElement<String> createDataFileDataDigestType(String value) {
        return new JAXBElement<String>(_DataFileDigestDigestType_QNAME, String.class, DataFileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DigestValue", scope = DataFileData.class)
    public JAXBElement<String> createDataFileDataDigestValue(String value) {
        return new JAXBElement<String>(_DataFileDigestDigestValue_QNAME, String.class, DataFileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DfData", scope = DataFileData.class)
    public JAXBElement<String> createDataFileDataDfData(String value) {
        return new JAXBElement<String>(_DataFileDataDfData_QNAME, String.class, DataFileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Format", scope = SignedDocInfo.class)
    public JAXBElement<String> createSignedDocInfoFormat(String value) {
        return new JAXBElement<String>(_SignedDocInfoFormat_QNAME, String.class, SignedDocInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Version", scope = SignedDocInfo.class)
    public JAXBElement<String> createSignedDocInfoVersion(String value) {
        return new JAXBElement<String>(_SignedDocInfoVersion_QNAME, String.class, SignedDocInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Id", scope = SignatureInfo.class)
    public JAXBElement<String> createSignatureInfoId(String value) {
        return new JAXBElement<String>(_DataFileDigestId_QNAME, String.class, SignatureInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Status", scope = SignatureInfo.class)
    public JAXBElement<String> createSignatureInfoStatus(String value) {
        return new JAXBElement<String>(_SignatureInfoStatus_QNAME, String.class, SignatureInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Error }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Error", scope = SignatureInfo.class)
    public JAXBElement<Error> createSignatureInfoError(Error value) {
        return new JAXBElement<Error>(_SignatureInfoError_QNAME, Error.class, SignatureInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SigningTime", scope = SignatureInfo.class)
    public JAXBElement<XMLGregorianCalendar> createSignatureInfoSigningTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_SignatureInfoSigningTime_QNAME, XMLGregorianCalendar.class, SignatureInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignatureProductionPlace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SignatureProductionPlace", scope = SignatureInfo.class)
    public JAXBElement<SignatureProductionPlace> createSignatureInfoSignatureProductionPlace(SignatureProductionPlace value) {
        return new JAXBElement<SignatureProductionPlace>(_SignatureInfoSignatureProductionPlace_QNAME, SignatureProductionPlace.class, SignatureInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignerInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Signer", scope = SignatureInfo.class)
    public JAXBElement<SignerInfo> createSignatureInfoSigner(SignerInfo value) {
        return new JAXBElement<SignerInfo>(_SignatureInfoSigner_QNAME, SignerInfo.class, SignatureInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfirmationInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Confirmation", scope = SignatureInfo.class)
    public JAXBElement<ConfirmationInfo> createSignatureInfoConfirmation(ConfirmationInfo value) {
        return new JAXBElement<ConfirmationInfo>(_SignatureInfoConfirmation_QNAME, ConfirmationInfo.class, SignatureInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CRLInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CRLInfo", scope = SignatureInfo.class)
    public JAXBElement<CRLInfo> createSignatureInfoCRLInfo(CRLInfo value) {
        return new JAXBElement<CRLInfo>(_SignatureInfoCRLInfo_QNAME, CRLInfo.class, SignatureInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Category", scope = Error.class)
    public JAXBElement<String> createErrorCategory(String value) {
        return new JAXBElement<String>(_ErrorCategory_QNAME, String.class, Error.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = Error.class)
    public JAXBElement<String> createErrorDescription(String value) {
        return new JAXBElement<String>(_ErrorDescription_QNAME, String.class, Error.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Issuer", scope = CRLInfo.class)
    public JAXBElement<String> createCRLInfoIssuer(String value) {
        return new JAXBElement<String>(_CRLInfoIssuer_QNAME, String.class, CRLInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "LastUpdate", scope = CRLInfo.class)
    public JAXBElement<XMLGregorianCalendar> createCRLInfoLastUpdate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CRLInfoLastUpdate_QNAME, XMLGregorianCalendar.class, CRLInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NextUpdate", scope = CRLInfo.class)
    public JAXBElement<XMLGregorianCalendar> createCRLInfoNextUpdate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CRLInfoNextUpdate_QNAME, XMLGregorianCalendar.class, CRLInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SerialNumber", scope = RevokedInfo.class)
    public JAXBElement<String> createRevokedInfoSerialNumber(String value) {
        return new JAXBElement<String>(_RevokedInfoSerialNumber_QNAME, String.class, RevokedInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "RevocationDate", scope = RevokedInfo.class)
    public JAXBElement<XMLGregorianCalendar> createRevokedInfoRevocationDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_RevokedInfoRevocationDate_QNAME, XMLGregorianCalendar.class, RevokedInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Id", scope = TstInfo.class)
    public JAXBElement<String> createTstInfoId(String value) {
        return new JAXBElement<String>(_DataFileDigestId_QNAME, String.class, TstInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Type", scope = TstInfo.class)
    public JAXBElement<String> createTstInfoType(String value) {
        return new JAXBElement<String>(_SignatureModuleType_QNAME, String.class, TstInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SerialNumber", scope = TstInfo.class)
    public JAXBElement<String> createTstInfoSerialNumber(String value) {
        return new JAXBElement<String>(_RevokedInfoSerialNumber_QNAME, String.class, TstInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CreationTime", scope = TstInfo.class)
    public JAXBElement<XMLGregorianCalendar> createTstInfoCreationTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TstInfoCreationTime_QNAME, XMLGregorianCalendar.class, TstInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Policy", scope = TstInfo.class)
    public JAXBElement<String> createTstInfoPolicy(String value) {
        return new JAXBElement<String>(_TstInfoPolicy_QNAME, String.class, TstInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ErrorBound", scope = TstInfo.class)
    public JAXBElement<String> createTstInfoErrorBound(String value) {
        return new JAXBElement<String>(_TstInfoErrorBound_QNAME, String.class, TstInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "TSA", scope = TstInfo.class)
    public JAXBElement<String> createTstInfoTSA(String value) {
        return new JAXBElement<String>(_TstInfoTSA_QNAME, String.class, TstInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertificateInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Certificate", scope = TstInfo.class)
    public JAXBElement<CertificateInfo> createTstInfoCertificate(CertificateInfo value) {
        return new JAXBElement<CertificateInfo>(_TstInfoCertificate_QNAME, CertificateInfo.class, TstInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResponderID", scope = ConfirmationInfo.class)
    public JAXBElement<String> createConfirmationInfoResponderID(String value) {
        return new JAXBElement<String>(_ConfirmationInfoResponderID_QNAME, String.class, ConfirmationInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ProducedAt", scope = ConfirmationInfo.class)
    public JAXBElement<String> createConfirmationInfoProducedAt(String value) {
        return new JAXBElement<String>(_ConfirmationInfoProducedAt_QNAME, String.class, ConfirmationInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertificateInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResponderCertificate", scope = ConfirmationInfo.class)
    public JAXBElement<CertificateInfo> createConfirmationInfoResponderCertificate(CertificateInfo value) {
        return new JAXBElement<CertificateInfo>(_ConfirmationInfoResponderCertificate_QNAME, CertificateInfo.class, ConfirmationInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CommonName", scope = SignerInfo.class)
    public JAXBElement<String> createSignerInfoCommonName(String value) {
        return new JAXBElement<String>(_SignerInfoCommonName_QNAME, String.class, SignerInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IDCode", scope = SignerInfo.class)
    public JAXBElement<String> createSignerInfoIDCode(String value) {
        return new JAXBElement<String>(_SignerInfoIDCode_QNAME, String.class, SignerInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertificateInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Certificate", scope = SignerInfo.class)
    public JAXBElement<CertificateInfo> createSignerInfoCertificate(CertificateInfo value) {
        return new JAXBElement<CertificateInfo>(_TstInfoCertificate_QNAME, CertificateInfo.class, SignerInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Issuer", scope = CertificateInfo.class)
    public JAXBElement<String> createCertificateInfoIssuer(String value) {
        return new JAXBElement<String>(_CRLInfoIssuer_QNAME, String.class, CertificateInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Subject", scope = CertificateInfo.class)
    public JAXBElement<String> createCertificateInfoSubject(String value) {
        return new JAXBElement<String>(_CertificateInfoSubject_QNAME, String.class, CertificateInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ValidFrom", scope = CertificateInfo.class)
    public JAXBElement<XMLGregorianCalendar> createCertificateInfoValidFrom(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CertificateInfoValidFrom_QNAME, XMLGregorianCalendar.class, CertificateInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ValidTo", scope = CertificateInfo.class)
    public JAXBElement<XMLGregorianCalendar> createCertificateInfoValidTo(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CertificateInfoValidTo_QNAME, XMLGregorianCalendar.class, CertificateInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IssuerSerial", scope = CertificateInfo.class)
    public JAXBElement<String> createCertificateInfoIssuerSerial(String value) {
        return new JAXBElement<String>(_CertificateInfoIssuerSerial_QNAME, String.class, CertificateInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "OID", scope = CertificatePolicy.class)
    public JAXBElement<String> createCertificatePolicyOID(String value) {
        return new JAXBElement<String>(_CertificatePolicyOID_QNAME, String.class, CertificatePolicy.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "URL", scope = CertificatePolicy.class)
    public JAXBElement<String> createCertificatePolicyURL(String value) {
        return new JAXBElement<String>(_CertificatePolicyURL_QNAME, String.class, CertificatePolicy.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = CertificatePolicy.class)
    public JAXBElement<String> createCertificatePolicyDescription(String value) {
        return new JAXBElement<String>(_ErrorDescription_QNAME, String.class, CertificatePolicy.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "City", scope = SignatureProductionPlace.class)
    public JAXBElement<String> createSignatureProductionPlaceCity(String value) {
        return new JAXBElement<String>(_SignatureProductionPlaceCity_QNAME, String.class, SignatureProductionPlace.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StateOrProvince", scope = SignatureProductionPlace.class)
    public JAXBElement<String> createSignatureProductionPlaceStateOrProvince(String value) {
        return new JAXBElement<String>(_SignatureProductionPlaceStateOrProvince_QNAME, String.class, SignatureProductionPlace.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PostalCode", scope = SignatureProductionPlace.class)
    public JAXBElement<String> createSignatureProductionPlacePostalCode(String value) {
        return new JAXBElement<String>(_SignatureProductionPlacePostalCode_QNAME, String.class, SignatureProductionPlace.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CountryName", scope = SignatureProductionPlace.class)
    public JAXBElement<String> createSignatureProductionPlaceCountryName(String value) {
        return new JAXBElement<String>(_SignatureProductionPlaceCountryName_QNAME, String.class, SignatureProductionPlace.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Role", scope = SignerRole.class)
    public JAXBElement<String> createSignerRoleRole(String value) {
        return new JAXBElement<String>(_SignerRoleRole_QNAME, String.class, SignerRole.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Id", scope = DataFileInfo.class)
    public JAXBElement<String> createDataFileInfoId(String value) {
        return new JAXBElement<String>(_DataFileDigestId_QNAME, String.class, DataFileInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Filename", scope = DataFileInfo.class)
    public JAXBElement<String> createDataFileInfoFilename(String value) {
        return new JAXBElement<String>(_DataFileDataFilename_QNAME, String.class, DataFileInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MimeType", scope = DataFileInfo.class)
    public JAXBElement<String> createDataFileInfoMimeType(String value) {
        return new JAXBElement<String>(_DataFileDigestMimeType_QNAME, String.class, DataFileInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ContentType", scope = DataFileInfo.class)
    public JAXBElement<String> createDataFileInfoContentType(String value) {
        return new JAXBElement<String>(_SignatureModuleContentType_QNAME, String.class, DataFileInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DigestType", scope = DataFileInfo.class)
    public JAXBElement<String> createDataFileInfoDigestType(String value) {
        return new JAXBElement<String>(_DataFileDigestDigestType_QNAME, String.class, DataFileInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DigestValue", scope = DataFileInfo.class)
    public JAXBElement<String> createDataFileInfoDigestValue(String value) {
        return new JAXBElement<String>(_DataFileDigestDigestValue_QNAME, String.class, DataFileInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name", scope = DataFileAttribute.class)
    public JAXBElement<String> createDataFileAttributeName(String value) {
        return new JAXBElement<String>(_SignatureModuleName_QNAME, String.class, DataFileAttribute.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Value", scope = DataFileAttribute.class)
    public JAXBElement<String> createDataFileAttributeValue(String value) {
        return new JAXBElement<String>(_DataFileAttributeValue_QNAME, String.class, DataFileAttribute.class, value);
    }

}
