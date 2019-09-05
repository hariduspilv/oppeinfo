package ee.hitsa.ois.web.dto;

public class RoomAutocompleteResult extends OccupiedAutocompleteResult {

    private Long seats;

    public RoomAutocompleteResult(Long id, String buildingCode, String roomCode) {
        super(id, nameEt(buildingCode, roomCode, null), nameEn(buildingCode, roomCode, null));
    }

    public RoomAutocompleteResult(Long id, String buildingCode, String roomCode, Long seats) {
        super(id, nameEt(buildingCode, roomCode, seats), nameEn(buildingCode, roomCode, seats));
        this.seats = seats;
    }

    public Long getSeats() {
        return seats;
    }

    public void setSeats(Long seats) {
        this.seats = seats;
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