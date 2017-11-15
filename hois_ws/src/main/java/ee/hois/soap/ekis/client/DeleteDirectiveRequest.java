package ee.hois.soap.ekis.client;

public class DeleteDirectiveRequest {

    private String qguid;
    private String oisId;
    private int wdId;

    public String getQguid() {
        return qguid;
    }

    public void setQguid(String qguid) {
        this.qguid = qguid;
    }

    public String getOisId() {
        return oisId;
    }

    public void setOisId(String oisId) {
        this.oisId = oisId;
    }

    public int getWdId() {
        return wdId;
    }

    public void setWdId(int wdId) {
        this.wdId = wdId;
    }
}
