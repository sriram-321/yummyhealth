package com.example.yummyhealth.Model;

public class OrderItem
{
    String Phone_Number;
    double Latitude;
    double Longitude;
    String CartTotalAmount;
    String Status;
    String Customer_UID;
    String Distance;

    public OrderItem()
    {

    }
    public OrderItem(String phone_number, double latitude, double longitude, String cartTotal,String status, String customer_UID, String distance)
    {
        Phone_Number = phone_number;
        Latitude = latitude;
        Longitude = longitude;
        CartTotalAmount = cartTotal;
        Status = status;
        Customer_UID = customer_UID;
        Distance = distance;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_number) {
        Phone_Number = phone_number;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getCartTotalAmount() {
        return CartTotalAmount;
    }

    public void setCartTotal(String cartTotal) {
        this.CartTotalAmount = cartTotal;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCustomer_UID() {
        return Customer_UID;
    }

    public void setCustomer_UID(String customer_uid) {
        Customer_UID = customer_uid;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }
}
