package model;

public class ExpertsNode extends Node{
    private String expertId;
    private String expertArea;

    public ExpertsNode(String expertId, String expertArea, float factRating, String justification) {
        super(factRating, justification);
        this.expertId = expertId;
        this.expertArea = expertArea;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getExpertArea() {
        return expertArea;
    }

    public void setExpertArea(String expertArea) {
        this.expertArea = expertArea;
    }
}
