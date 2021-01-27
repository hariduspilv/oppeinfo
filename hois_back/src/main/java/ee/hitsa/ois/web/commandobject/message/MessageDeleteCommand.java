package ee.hitsa.ois.web.commandobject.message;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class MessageDeleteCommand {

    @NotNull
    private Boolean receiver;
    @NotEmpty
    private Set<Long> ids;

    public Boolean getReceiver() {
        return receiver;
    }

    public void setReceiver(Boolean receiver) {
        this.receiver = receiver;
    }

    public Set<Long> getIds() {
        return ids;
    }

    public void setIds(Set<Long> ids) {
        this.ids = ids;
    }
}
