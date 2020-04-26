package com.example.yummyhealth.Model;

public class CartInfo {
    double CartItemsTotal;
    double CartTotal;
    double Discount;

    public CartInfo() {
    }

    public CartInfo(double cartItemsTotal, double cartTotal, double discount) {
        CartItemsTotal = cartItemsTotal;
        CartTotal = cartTotal;
        Discount = discount;
    }

    public double getCartItemsTotal() {
        return CartItemsTotal;
    }

    public void setCartItemsTotal(double cartItemsTotal) {
        CartItemsTotal = cartItemsTotal;
    }

    public double getCartTotal() {
        return CartTotal;
    }

    public void setCartTotal(double cartTotal) {
        CartTotal = cartTotal;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }
}
