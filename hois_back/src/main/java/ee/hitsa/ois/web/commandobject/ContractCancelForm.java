package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

public class ContractCancelForm extends VersionedCommand {
	
	private String cancelDesc;
	private String cancelReason;
	private LocalDate canceled;
	
	public String getCancelDesc() {
		return cancelDesc;
	}
	public void setCancelDesc(String cancelDesc) {
		this.cancelDesc = cancelDesc;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public LocalDate getCanceled() {
		return canceled;
	}
	public void setCanceled(LocalDate canceled) {
		this.canceled = canceled;
	}

}
