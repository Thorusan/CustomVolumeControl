package com.example.ales.customvolumecontrol;

public class ColorRGB {

    private int redColor;
    private int greenColor;
    private int blueColor;

    public ColorRGB(int redColor, int greenColor, int blueColor) {
        this.redColor = redColor;
        this.greenColor = greenColor;
        this.blueColor = blueColor;

    }


    public int getRedColor() {
        return redColor;
    }

    public void setRedColor(int redColor) {
        this.redColor = redColor;
    }

    public int getGreenColor() {
        return greenColor;
    }

    public void setGreenColor(int greenColor) {
        this.greenColor = greenColor;
    }

    public int getBlueColor() {
        return blueColor;
    }

    public void setBlueColor(int blueColor) {
        this.blueColor = blueColor;
    }
}
