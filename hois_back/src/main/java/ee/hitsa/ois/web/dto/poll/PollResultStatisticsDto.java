package ee.hitsa.ois.web.dto.poll;

import ee.hitsa.ois.service.poll.PollAsyncService.PollStatisticsRequest;
import ee.hitsa.ois.web.commandobject.poll.PollResultStatisticsCommand;

public class PollResultStatisticsDto extends PollResultStatisticsCommand {
    
    
    
    public PollResultStatisticsDto(PollResultStatisticsCommand command) {
        this.command = command;
    }
    
    public PollResultStatisticsDto() {}
    
    private PollResultStatisticsCommand command;
    
    private String hash;
    
    private byte[] file;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public static PollResultStatisticsDto of(PollStatisticsRequest request) {
        PollResultStatisticsDto dto = new PollResultStatisticsDto();
        dto.setHash(request.getRequestHash());
        return dto;
    }

    public PollResultStatisticsCommand getCommand() {
        return command;
    }

    public void setCommand(PollResultStatisticsCommand command) {
        this.command = command;
    }

}
