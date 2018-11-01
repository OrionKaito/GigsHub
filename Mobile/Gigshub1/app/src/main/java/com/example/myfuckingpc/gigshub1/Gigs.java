package com.example.myfuckingpc.gigshub1;

public class Gigs {
    private String gigsTitle, gigsLocation;
    private float gigsStar;
    private int gigsImage;
    private int gigsNumber;


    public Gigs(String gigsTitle, String gigsLocation, float gigsStar, int gigsImage, int gigsNumber) {
        this.gigsTitle = gigsTitle;
        this.gigsLocation = gigsLocation;
        this.gigsStar = gigsStar;
        this.gigsImage = gigsImage;
        this.gigsNumber = gigsNumber;
    }

    public int getGigsNumber() {
        return gigsNumber;
    }

    public void setGigsNumber(int gigsNumber) {
        this.gigsNumber = gigsNumber;
    }

    public String getGigsTitle() {
        return gigsTitle;
    }

    public void setGigsTitle(String gigsTitle) {
        this.gigsTitle = gigsTitle;
    }

    public String getGigsLocation() {
        return gigsLocation;
    }

    public void setGigsLocation(String gigsLocation) {
        this.gigsLocation = gigsLocation;
    }

    public float getGigsStar() {
        return gigsStar;
    }

    public void setGigsStar(float gigsStar) {
        this.gigsStar = gigsStar;
    }

    public int getGigsImage() {
        return gigsImage;
    }

    public void setGigsImage(int gigsImage) {
        this.gigsImage = gigsImage;
    }
}
