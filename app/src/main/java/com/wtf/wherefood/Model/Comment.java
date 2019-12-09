package com.wtf.wherefood.Model;

import java.util.ArrayList;
import java.util.List;

public class Comment {

    public int getCommentiD() {
        return commentiD;
    }

    public void setCommentiD(int commentiD) {
        this.commentiD = commentiD;
    }

    public int getSurveyPoint() {
        return SurveyPoint;
    }

    public void setSurveyPoint(int surveyPoint) {
        SurveyPoint = surveyPoint;
    }

    public Double getDatetime_comment() {
        return datetime_comment;
    }

    public void setDatetime_comment(Double datetime_comment) {
        this.datetime_comment = datetime_comment;
    }

    private  int SurveyPoint;
    private  Double datetime_comment;
    private  int commentiD;

    public Comment(String userAccount, String content, int commentiD) {
        UserAccount = userAccount;
        Content = content;
        this.commentiD = commentiD;
        picturelink = new ArrayList<>();
    }

    public Comment(String userAccount, String content) {
        UserAccount = userAccount;
        Content = content;
        picturelink = new ArrayList<>();
    }

    public Comment()
    {}

    public List<String> getPicturelink() {
        return picturelink;
    }

    public void setPicturelink(List<String> picturelink) {
        this.picturelink = picturelink;
    }

    private List<String> picturelink;


    private  String UserAccount;
    private  String Content;

    public String getUserAccount() {
        return UserAccount;
    }

    public void setUserAccount(String userAccount) {
        UserAccount = userAccount;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
    public  void addLink(String link)
    {
        picturelink.add(link);
    }
}
