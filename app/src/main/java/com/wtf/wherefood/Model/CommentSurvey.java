package com.wtf.wherefood.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class CommentSurvey {
    /*

    "FoodID"                : "2",
    "UserID"            : "1",
    "CommentToken"          : "1",
    "CommentContent"        : "Đây là nội dung commnt 2",
    "SurveyPoint"           : 1,
    "datetime_comment"        : 123123123123
     */

    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(String foodID) {
        FoodID = foodID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getCommentContent() {
        return CommentContent;
    }

    public void setCommentContent(String commentContent) {
        CommentContent = commentContent;
    }

    public int getSurveyPoint() {
        return SurveyPoint;
    }

    public void setSurveyPoint(int surveyPoint) {
        SurveyPoint = surveyPoint;
    }

    public long getDatetime_comment() {
        return datetime_comment;
    }

    public void setDatetime_comment(long datetime_comment) {
        this.datetime_comment = datetime_comment;
    }

    public String getCommentToken() {
        return CommentToken;
    }

    public void setCommentToken(String commentToken) {
        CommentToken = commentToken;
    }

    public CommentSurvey(String foodID, String userID, String commentContent, int surveyPoint) {
        FoodID = foodID;
        UserID = userID;
        CommentContent = commentContent;
        SurveyPoint = surveyPoint;
        Calendar calendar = Calendar.getInstance();
        //Returns current time in millis
        datetime_comment = calendar.getTimeInMillis();
        this.CommentToken = "token_cmt_" +datetime_comment;
    }
    @SerializedName("FoodID")
    @Expose
    private String FoodID;
    @SerializedName("UserID")
    @Expose
    private String UserID;
    @SerializedName("CommentToken")
    @Expose
    private String CommentToken;
    @SerializedName("CommentContent")
    @Expose
    private String CommentContent;
    @SerializedName("SurveyPoint")
    @Expose
    private int SurveyPoint;
    @SerializedName("datetime_comment")
    @Expose
    private long datetime_comment;

    @Override
    public String toString() {
        return "{" +
                "    \"FoodID\"        \t: \""+FoodID+"\",\n" +
                "    \"UserID\"            : \""+UserID+"\",\n" +
                "    \"CommentToken\"  \t: \""+CommentToken+"\",\n" +
                "    \"CommentContent\"\t: \""+CommentContent+"\",\n" +
                "    \"SurveyPoint\"   \t: "+SurveyPoint+",\n" +
                "    \"datetime_comment\"\t: "+datetime_comment+"\n" +
                "}";
    }



}
