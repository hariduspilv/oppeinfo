package ee.hitsa.ois.web.dto.boardingschool;

import ee.hitsa.ois.web.commandobject.boardingschool.DormitoryForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class DormitoryDto extends DormitoryForm {

    private AutocompleteResult roomObject;
    private Boolean expired; 

    public AutocompleteResult getRoomObject() {
        return roomObject;
    }

    public void setRoomObject(AutocompleteResult roomObject) {
        this.roomObject = roomObject;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

}
