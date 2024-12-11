package ecinema.booking.system.dto;

public class PromoUsageDto {

    private Long id;
    private Long userId;
    private Long promoCodeId;
    private String usageStatus;

    // Constructor
    public PromoUsageDto(Long id, Long userId, Long promoCodeId, String usageStatus) {
        this.id = id;
        this.userId = userId;
        this.promoCodeId = promoCodeId;
        this.usageStatus = usageStatus;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPromoCodeId() {
        return promoCodeId;
    }

    public void setPromoCodeId(Long promoCodeId) {
        this.promoCodeId = promoCodeId;
    }

    public String getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(String usageStatus) {
        this.usageStatus = usageStatus;
    }
}
