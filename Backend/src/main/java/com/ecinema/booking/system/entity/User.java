package ecinema.booking.system.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String billingAddress;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    @Enumerated(EnumType.STRING)
    private PromoStatus promoStatus;
    
    public enum PromoStatus {
        SUBSCRIBED,
        UNSUBSCRIBED
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER,
        ADMIN
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PaymentCard> paymentCards;
        
        public User() {}
    
        public User(Long id, String firstname, String lastname, String email, String password, String billingAddress, Status status, Role role, List<PaymentCard> paymentCards, PromoStatus promoStatus) {
            this.id = id;
    
            this.firstname = firstname;
    
            this.lastname = lastname;
    
            this.email = email;
    
            this.password = password;
    
            this.billingAddress = billingAddress;
    
            this.status = status;
    
            this.role = role;
            
            this.promoStatus = promoStatus;
    
            this.paymentCards = paymentCards;
        }
    
        public Long getId() {
            return id;
        }
    
        public String getFirstname() {
            return firstname;
        }
    
        public String getLastname() {
            return lastname;
        }
    
        public String getEmail() {
            return email;
        }
    
        public String getPassword() {
            return password;
        }
    
        public String getBillingAddress() {
            return billingAddress;
        }
    
        public Status getStatus() {
            return status;
        }
    
        public Role getRole() {
            return role;
        }
    
        public List<PaymentCard> getPaymentCards() {
            return paymentCards;
        }
    
       public void setId(Long id) {
            this.id = id;
        }
    
        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }
    
        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
    
        public void setEmail(String email) {
            this.email = email;
        }
    
        public void setPassword(String password) {
            this.password = password;
        }
    
        public void setBillingAddress(String billingAddress) {
            this.billingAddress = billingAddress;
        }
    
        public void setStatus(Status status) {
            this.status = status;
        }
    
        public void setRole(Role role) {
            this.role = role;
        }
    
        public void setPaymentCards(List<PaymentCard> paymentCards) {
            this.paymentCards = paymentCards;
        }
    
        public void setPromoStatus(PromoStatus promoStatus){
            this.promoStatus = promoStatus;
        }

        public PromoStatus getPromoStatus(){
           return promoStatus;
        }
}