package model;

public abstract class Node {
    private float factRating;
    private String justification;

    public Node(){}

    public Node(float factRating, String justification){
        this.factRating = factRating;
        this.justification = justification;
    }

    public float getFactRating() {
        return factRating;
    }

    public void setFactRating(float factRating) {
        this.factRating = factRating;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
}
