package com.example.myfuckingpc.gigshub1;

public class Gigs {
    private String gigsTitle, gigsLocation, gigsHosted;
    private float gigsStar;
    private int gigsImage;
    private int gigsNumber;


    public Gigs(String gigsTitle, String gigsLocation, String gigsHosted, float gigsStar, int gigsImage, int gigsNumber) {
        this.gigsTitle = gigsTitle;
        this.gigsLocation = gigsLocation;
        this.gigsHosted = gigsHosted;
        this.gigsStar = gigsStar;
        this.gigsImage = gigsImage;
        this.gigsNumber = gigsNumber;
    }

    public String getGigsHosted() {
        return gigsHosted;
    }

    public void setGigsHosted(String gigsHosted) {
        this.gigsHosted = gigsHosted;
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
