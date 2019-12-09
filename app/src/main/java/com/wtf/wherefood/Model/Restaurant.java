package com.wtf.wherefood.Model;

public class Restaurant {
    public String getRestaurantID() {
        return RestaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        RestaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return RestaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        RestaurantAddress = restaurantAddress;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public Restaurant(String restaurantID, String restaurantName, String restaurantAddress, String longitude, String latitude) {
        RestaurantID = restaurantID;
        RestaurantName = restaurantName;
        RestaurantAddress = restaurantAddress;
        Longitude = longitude;
        Latitude = latitude;
    }

    String RestaurantID ;
    String RestaurantName ;
    String RestaurantAddress;
    String Longitude ;
    String Latitude ;
}
