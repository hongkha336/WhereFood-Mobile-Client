package com.wfteam.wherefood.Model;

public class Food {
    public String getFoodID() {
        return FoodID;
    }

    public void setFoodID(String foodID) {
        FoodID = foodID;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getPictureToken() {
        return PictureToken;
    }

    public void setPictureToken(String pictureToken) {
        PictureToken = pictureToken;
    }

    public int getPrices() {
        return Prices;
    }

    public void setPrices(int prices) {
        Prices = prices;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getLongDescriptioon() {
        return LongDescriptioon;
    }

    public void setLongDescriptioon(String longDescriptioon) {
        LongDescriptioon = longDescriptioon;
    }

    public double getAvgSurvey() {
        return AvgSurvey;
    }

    public void setAvgSurvey(double avgSurvey) {
        AvgSurvey = avgSurvey;
    }

    public String getRestaurantID() {
        return RestaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        RestaurantID = restaurantID;
    }

    public Food(String foodID, String foodName, String pictureToken, int prices, String shortDescription, String longDescriptioon, double avgSurvey, String restaurantID, String plink) {
        FoodID = foodID;
        FoodName = foodName;
        PictureToken = pictureToken;
        Prices = prices;
        ShortDescription = shortDescription;
        LongDescriptioon = longDescriptioon;
        AvgSurvey = avgSurvey;
        RestaurantID = restaurantID;
        FirstPermarkLink = plink;
    }

    private  String FoodID;
    private  String FoodName;
    private  String PictureToken;
    private  int Prices;
    private  String ShortDescription;
    private  String LongDescriptioon;
    private  double AvgSurvey;
    private  String RestaurantID;
    private  String FirstPermarkLink;
    public String getFirstPermarkLink() {
        return FirstPermarkLink;
    }

    public void setFirstPermarkLink(String firstPermarkLink) {
        FirstPermarkLink = firstPermarkLink;
    }


}
