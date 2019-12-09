package com.wtf.wherefood.Model;

public class MetaComment {
    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

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

    public String getPictureLink() {
        return PictureLink;
    }

    public void setPictureLink(String pictureLink) {
        PictureLink = pictureLink;
    }

    public MetaComment(int commentID, String userAccount, String content, String pictureLink) {
        this.commentID = commentID;
        UserAccount = userAccount;
        Content = content;
        PictureLink = pictureLink;
    }

    private int commentID;
    private String UserAccount;
    private String Content;
    private String PictureLink;

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

}
