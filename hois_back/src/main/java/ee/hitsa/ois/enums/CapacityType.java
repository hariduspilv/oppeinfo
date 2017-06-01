package ee.hitsa.ois.enums;


public enum CapacityType {

    MAHT_a("A"),
    MAHT_i("I"),
    MAHT_p("P"),
    MAHT_l("L");

    private final String id;

    private CapacityType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
