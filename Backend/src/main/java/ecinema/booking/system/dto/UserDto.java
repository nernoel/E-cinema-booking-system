package ecinema.booking.system.dto;

import ecinema.booking.system.entity.PaymentCard;
import ecinema.booking.system.entity.User;
import java.util.List;

public class UserDto {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String billingAddress;
    
    private String password;

    private User.Status status;

    private User.Role role;

    private User.PromoStatus promoStatus;

    private List<PaymentCard> paymentCards;

    // No-args constructor
    public UserDto() {}

    // All-args constructor
    public UserDto(Long id, String firstname, String lastname, String email,
                   String billingAddress, String password, User.Status status,
                   User.Role role, User.PromoStatus promoStatus, List<PaymentCard> paymentCards) {

        this.id = id;

        this.firstname = firstname;

        this.lastname = lastname
        ;
        this.email = email;

        this.billingAddress = billingAddress;

        this.password = password;

        this.status = status;

        this.role = role;

        this.paymentCards = paymentCards;

        this.promoStatus = promoStatus;
    }

   
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User.Status getStatus() {
        return status;
    }

    public void setStatus(User.Status status) {
        this.status = status;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public List<PaymentCard> getPaymentCards() {
        return paymentCards;
    }

    public void setPaymentCards(List<PaymentCard> paymentCards) {
        this.paymentCards = paymentCards;
    }

    public User.PromoStatus getPromoStatus(){
        return promoStatus;
    }

    public void setPromoStatus(User.PromoStatus promoStatus){
        this.promoStatus = promoStatus;
    }
}