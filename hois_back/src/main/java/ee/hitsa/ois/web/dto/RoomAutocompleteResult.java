package ee.hitsa.ois.web.dto;

public class RoomAutocompleteResult extends OccupiedAutocompleteResult {

    private Long buildingId;
    private Long seats;

    public RoomAutocompleteResult(Long id, Long buildingId, String buildingCode, String roomCode) {
        super(id, nameEt(buildingCode, roomCode, null), nameEn(buildingCode, roomCode, null));
        this.buildingId = buildingId;
    }

    public RoomAutocompleteResult(Long id, Long buildingId, String buildingCode, String roomCode, Long seats) {
        super(id, nameEt(buildingCode, roomCode, seats), nameEn(buildingCode, roomCode, seats));
        this.buildingId = buildingId;
        this.seats = seats;
    }

    public Long getSeats() {
        return seats;
    }

    public void setSeats(Long seats) {
        this.seats = seats;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    private static String nameEt(String buildingCode, String roomCode, Long seats) {
        return seats != null ? buildingCode + " - " + roomCode + " (kohti " + seats.toString() + ")"
                : buildingCode + " - " + roomCode;
    }

    private static String nameEn(String buildingCode, String roomCode, Long seats) {
        return seats != null ? buildingCode + " - " + roomCode + " (seats " + seats.toString() + ")"
                : buildingCode + " - " + roomCode;
    }

}