package bg.softuni.Spring.ecommerce.app.model.dto;

public class AnalyticsResponseDto {

    private Long placedOrders;
    private Long shippedOrders;
    private Long deliveredOrders;
    private Long currentMonthOrders;
    private Long previousMonthOrders;
    private Long currentMonthEarnings;
    private Long previousMonthEarnings;

    public Long getPlacedOrders() {
        return placedOrders;
    }

    public AnalyticsResponseDto setPlacedOrders(Long placedOrders) {
        this.placedOrders = placedOrders;
        return this;
    }

    public Long getShippedOrders() {
        return shippedOrders;
    }

    public AnalyticsResponseDto setShippedOrders(Long shippedOrders) {
        this.shippedOrders = shippedOrders;
        return this;
    }

    public Long getDeliveredOrders() {
        return deliveredOrders;
    }

    public AnalyticsResponseDto setDeliveredOrders(Long deliveredOrders) {
        this.deliveredOrders = deliveredOrders;
        return this;
    }

    public Long getCurrentMonthOrders() {
        return currentMonthOrders;
    }

    public AnalyticsResponseDto setCurrentMonthOrders(Long currentMonthOrders) {
        this.currentMonthOrders = currentMonthOrders;
        return this;
    }

    public Long getPreviousMonthOrders() {
        return previousMonthOrders;
    }

    public AnalyticsResponseDto setPreviousMonthOrders(Long previousMonthOrders) {
        this.previousMonthOrders = previousMonthOrders;
        return this;
    }

    public Long getCurrentMonthEarnings() {
        return currentMonthEarnings;
    }

    public AnalyticsResponseDto setCurrentMonthEarnings(Long currentMonthEarnings) {
        this.currentMonthEarnings = currentMonthEarnings;
        return this;
    }

    public Long getPreviousMonthEarnings() {
        return previousMonthEarnings;
    }

    public AnalyticsResponseDto setPreviousMonthEarnings(Long previousMonthEarnings) {
        this.previousMonthEarnings = previousMonthEarnings;
        return this;
    }
}
