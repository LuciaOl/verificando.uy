package model;

public class ColectiveNode extends Node{
    private String collectiveId;

    public ColectiveNode(String collectiveId, float factRating, String justification) {
        super(factRating, justification);
        this.collectiveId = collectiveId;
    }

    public String getCollectiveId() {
        return collectiveId;
    }

    public void setCollectiveId(String collectiveId) {
        this.collectiveId = collectiveId;
    }
}
