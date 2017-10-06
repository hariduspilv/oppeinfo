package ee.hois.soap.ekis.client;

import java.util.List;

import ee.hois.soap.ekis.client.generated.Content;

public class RegisterDirectiveRequest {

    private String qguid;
    private int ehisId;
    private String oisId;
    private String directiveType;
    private String title;
    private String itemCreator;
    private String createTime;
    private String manager;
    private List<Content> content;
    private int wdId;

    public String getQguid() {
        return qguid;
    }

    public void setQguid(String qguid) {
        this.qguid = qguid;
    }

    public int getEhisId() {
        return ehisId;
    }

    public void setEhisId(int ehisId) {
        this.ehisId = ehisId;
    }

    public String getOisId() {
        return oisId;
    }

    public void setOisId(String oisId) {
        this.oisId = oisId;
    }

    public String getDirectiveType() {
        return directiveType;
    }

    public void setDirectiveType(String directiveType) {
        this.directiveType = directiveType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemCreator() {
        return itemCreator;
    }

    public void setItemCreator(String itemCreator) {
        this.itemCreator = itemCreator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public int getWdId() {
        return wdId;
    }

    public void setWdId(int wdId) {
        this.wdId = wdId;
    }
}
