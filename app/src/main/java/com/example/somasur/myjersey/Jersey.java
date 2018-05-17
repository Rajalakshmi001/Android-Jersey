package com.example.somasur.myjersey;

public class Jersey {

    private String mPlayerName;
    private int mPlayerNumber;
    private boolean misRed;

    public Jersey() {
        mPlayerName = "ANDROID";
        mPlayerNumber = 17;
        misRed = true;
    }

    public Jersey(String PlayerName, int PlayerNumber, boolean isRed) {
        mPlayerName = PlayerName;
        mPlayerNumber = PlayerNumber;
        misRed = isRed;
    }

    public String getmPlayerName() {
        return mPlayerName;
    }

    public void setmPlayerName(String mPlayerName) {
        this.mPlayerName = mPlayerName;
    }

    public String getmPlayerNumber() {
        return mPlayerNumber + "";
    }

    public void setmPlayerNumber(int mPlayerNumber) {
        this.mPlayerNumber = mPlayerNumber;
    }

    public boolean isMisRed() {
        return misRed;
    }

    public void setMisRed(boolean misRed) {
        this.misRed = misRed;
    }

}
