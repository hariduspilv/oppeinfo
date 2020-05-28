package ee.hitsa.ois.report.certificate;

import java.util.Arrays;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.StudentUtil;

public class CertificateRtfReport {

    public static final String TEMPLATE_NAME = "certificates/certificate.fo";
    public static final String TEMPLATE_NAME_SOOR_VOCATIONAL = "certificates/certificate.soor.vocational.fo";
    public static final String TEMPLATE_NAME_SOOR_HIGHER = "certificates/certificate.soor.higher.fo";
    
    private final Certificate certificate;
    private final String content;
    private final Boolean contentEditable;
    
    public CertificateRtfReport(Certificate certificate) {
        this.certificate = certificate;
        this.contentEditable = Boolean.valueOf(isContentEditable(EnumUtil.valueOf(CertificateType.class, certificate.getType())));
        Boolean higher = Boolean.valueOf(StudentUtil.isHigher(certificate.getStudent()));
        this.content = convertHtmlToXslFo(certificate.getContent(), EnumUtil.valueOf(CertificateType.class, certificate.getType()), higher);
    }
    
    /**
     * \n -> \r
     * p -> fo:block
     * b -> fo:wrapper font-weigth="bold"
     * 
     * @param html
     * @return
     */
    private static String convertHtmlToXslFo(String html, CertificateType type, Boolean higher) {
        boolean isHigher = higher != null && higher.booleanValue();
        String result = html;
        if (isContentEditable(type)) {
            result = result.replaceAll("\n", "\r");
        } else {
            result = result
                    .replaceAll("[\n|\r]", "")
                    .replaceAll("<br>", "\r")
                    .replaceAll("<p>", "<fo:block space-after=\"15pt\">").replaceAll("</p>", "</fo:block>")
                    .replaceAll("<b>", "<fo:wrapper font-weight=\"bold\">").replaceAll("</b>", "</fo:wrapper>")
                    ;
        }
        
        if (CertificateType.TOEND_LIIK_SOOR.equals(type)) {
            if (isHigher) {
                result = result
                        .replaceAll("<table class=\"widerTable\">", "<fo:block><fo:leader/></fo:block>"
                                + "<fo:table table-layout=\"fixed\" width=\"80%\" border=\"solid 0.1mm black\">"
                                + "<fo:table-column column-width=\"0.34in\"/>"
                                + "<fo:table-column column-width=\"2.0in\"/>"
                                + "<fo:table-column column-width=\"0.8in\"/>"
                                + "<fo:table-column column-width=\"0.7in\"/>"
                                + "<fo:table-column column-width=\"0.2in\"/>"
                                + "<fo:table-column column-width=\"0.9in\"/>"
                                + "<fo:table-column column-width=\"0.84in\"/>"
                                + "<fo:table-column/>")
                        .replaceAll("<table class=\"widerTable2\">", "<fo:block><fo:leader/></fo:block>"
                                + "<fo:table table-layout=\"fixed\" width=\"80%\" border=\"solid 0.1mm black\">"
                                + "<fo:table-column column-width=\"1.0in\"/>"
                                + "<fo:table-column column-width=\"1.5in\"/>"
                                + "<fo:table-column/>").replaceAll("</table>", "</fo:table><fo:block><fo:leader/></fo:block>")
                        .replaceAll("<th>", "<fo:table-cell margin=\"0.02in\" display-align=\"center\" padding-bottom=\"16pt\" border=\"solid 0.1mm black\"><fo:block>").replaceAll("</th>", "</fo:block></fo:table-cell>")
                        .replaceAll("<th colspan=\"(\\d+)\">", "<fo:table-cell number-columns-spanned=\"$1\" margin=\"0.02in\" display-align=\"center\" border=\"solid 0.1mm black\"><fo:block>")
                        .replaceAll("</th>", "<fo:retrieve-table-marker retrieve-class-name=\"continued\" \r\n" + 
                                "            retrieve-position-within-table=\"first-starting\" \r\n" + 
                                "            retrieve-boundary-within-table=\"table-fragment\"/></fo:block></fo:table-cell>")
                        .replaceAll("<td style=\"text-align: center;\">", "<fo:table-cell margin=\"0.02in\" display-align=\"center\" border=\"solid 0.1mm black\"><fo:block>")
                        .replaceAll("<td>", "<fo:table-cell margin=\"0.02in\" display-align=\"center\" border=\"solid 0.1mm black\"><fo:block>").replaceAll("</td>", "</fo:block></fo:table-cell>")
                        ;
            } else {
                result = result
                        .replaceAll("<table class=\"widerTable\">", "<fo:block><fo:leader/></fo:block>"
                                + "<fo:table table-layout=\"fixed\" width=\"80%\">"
                                + "<fo:table-column column-width=\"0.39in\"/>"
                                + "<fo:table-column column-width=\"3.65in\"/>"
                                + "<fo:table-column column-width=\"1.33in\"/>"
                                + "<fo:table-column column-width=\"0.35in\"/>"
                                + "<fo:table-column/>"
                                + "<fo:table-column column-width=\"0.9in\"/>"
                                + "<fo:table-column column-width=\"1.3in\"/>").replaceAll("</table>", "</fo:table><fo:block><fo:leader/></fo:block><fo:block><fo:leader/></fo:block>")
                        .replaceAll("<th>", "<fo:table-cell margin=\"0.02in\" display-align=\"center\"><fo:block>").replaceAll("</th>", "</fo:block></fo:table-cell>")
                        .replaceAll("<th colspan=\"(\\d+)\">", "<fo:table-cell number-columns-spanned=\"$1\" margin=\"0.02in\" display-align=\"center\"><fo:block>")
                        .replaceAll("</th>", "</fo:block></fo:table-cell>")
                        .replaceAll("<td style=\"text-align: (left|right|center|justify);\">", "<fo:table-cell margin=\"0.02in\" display-align=\"center\" text-align=\"$1\"><fo:block>")
                        .replaceAll("<td>", "<fo:table-cell margin=\"0.02in\" display-align=\"center\"><fo:block>").replaceAll("</td>", "</fo:block></fo:table-cell>")
                        ;
            }
            result = result
                    .replaceAll("<td style=\"vertical-align: top;\">", "<fo:table-cell margin=\"0.02in\" display-align=\"before\"><fo:block>")
                    .replaceAll("<thead>", "<fo:table-body text-align=\"center\" font-weight=\"bold\">").replaceAll("</thead>", "</fo:table-body>")
                    .replaceAll("<tbody>", "<fo:table-body>").replaceAll("</tbody>", "</fo:table-body>")
                    .replaceAll("<tr>", "<fo:table-row keep-together.within-page=\"always\">").replaceAll("</tr>", "</fo:table-row>")
                    ;
        }
        return result;
    }
    
    private static boolean isContentEditable(CertificateType type) {
        return Arrays.asList(CertificateType.TOEND_LIIK_KONTAKT, CertificateType.TOEND_LIIK_MUU, CertificateType.TOEND_LIIK_SESS).contains(type);
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public String getContent() {
        return content;
    }

    public Boolean getContentEditable() {
        return contentEditable;
    }
    
}
