package ecinema.booking.system.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "promo_codes")
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Double discountPercentage;

    @Enumerated(EnumType.STRING)
    private IsActive isActive;

    public enum IsActive {
        ACTIVE,
        INACTIVE
    }

    @OneToMany(mappedBy = "promoCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PromoUsage> usages;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public IsActive getIsActive() {
        return isActive;
    }

    public void setIsActive(IsActive isActive) {
        this.isActive = isActive;
    }

    public List<PromoUsage> getUsages() {
        return usages;
    }

    public void setUsages(List<PromoUsage> usages) {
        this.usages = usages;
    }
}
