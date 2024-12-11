package ecinema.booking.system.dto;

public class PromoCodeDto {

    private Long id;
    private String code;
    private Double discountPercentage;
    private String isActive;

    // Constructor
    public PromoCodeDto(Long id, String code, Double discountPercentage, String isActive) {
        this.id = id;
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.isActive = isActive;
    }

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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
    
}
