package rawe.gordon.com.business.network.responses.pojo;

import java.io.Serializable;

/**
 * Created by gordon on 16/5/10.
 */
public class UserModel implements Serializable {

    private String id;

    private String deliveryAddresses;

    private String phone;

    private String username;

    private String thumbnail;

    private String email;

    private String address;

    private String creditCardNumber;

    private String password;

    private String gender;

    private String orders;


    public UserModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(String deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }
}
