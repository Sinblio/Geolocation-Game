package com.example.campuscontagion.interfaces;

public interface IView {

    void appendTextBox(String content);
    String getPWBoxData();
    String getUserBoxData();
    void replaceTextBox(String content);
    void setLoginAccepted(Boolean status);
    void goToTheLobby();
    void goToManageAccount();
    void setVerifiedUserID(int id);
}
