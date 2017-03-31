package ee.hitsa.ois.enums;

public enum ApplicationType {

    AVALDUS_LIIK_AKAD(DirectiveType.KASKKIRI_AKAD),
    AVALDUS_LIIK_AKADK(DirectiveType.KASKKIRI_AKADK),
    AVALDUS_LIIK_EKSMAT(DirectiveType.KASKKIRI_EKSMAT),
    AVALDUS_LIIK_VALIS(DirectiveType.KASKKIRI_VALIS),
    AVALDUS_LIIK_OKAVA(DirectiveType.KASKKIRI_OKAVA),
    AVALDUS_LIIK_OVORM(DirectiveType.KASKKIRI_OVORM),
    AVALDUS_LIIK_FINM(DirectiveType.KASKKIRI_FINM);

    private final DirectiveType directiveType;

    ApplicationType(DirectiveType directiveType) {
        this.directiveType = directiveType;
    }

    public DirectiveType directiveType() {
        return directiveType;
    }
}
