package it.unipi.dii.lsmsdb.lsmsdb_project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "analytics")
public class Analytics {

    @Id
    private String date; // Es. "2025-02-02"

    @Field("user_metrics") private UserMetrics userMetrics;
    @Field("booking_metrics") private BookingMetrics bookingMetrics;
    @Field("financial_metrics") private FinancialMetrics financialMetrics;
    @Field("efficiency_metrics") private EfficiencyMetrics efficiencyMetrics;

    // --- Inner Classes ---
    public static class UserMetrics {
        @Field("total_users") private int totalUsers;
        @Field("new_users_today") private int newUsersToday;
        @Field("active_drivers_today") private int activeDriversToday;

        public int getTotalUsers() { return totalUsers; }
        public void setTotalUsers(int totalUsers) { this.totalUsers = totalUsers; }
        public int getNewUsersToday() { return newUsersToday; }
        public void setNewUsersToday(int newUsersToday) { this.newUsersToday = newUsersToday; }
        public int getActiveDriversToday() { return activeDriversToday; }
        public void setActiveDriversToday(int activeDriversToday) { this.activeDriversToday = activeDriversToday; }
    }

    public static class BookingMetrics {
        @Field("total_bookings_created") private int totalBookingsCreated;
        @Field("rides_completed") private int ridesCompleted;
        @Field("cancellation_rate") private String cancellationRate;
        @Field("seat_occupancy_rate") private String seatOccupancyRate;

        public int getTotalBookingsCreated() { return totalBookingsCreated; }
        public void setTotalBookingsCreated(int totalBookingsCreated) { this.totalBookingsCreated = totalBookingsCreated; }
        public int getRidesCompleted() { return ridesCompleted; }
        public void setRidesCompleted(int ridesCompleted) { this.ridesCompleted = ridesCompleted; }
        public String getCancellationRate() { return cancellationRate; }
        public void setCancellationRate(String cancellationRate) { this.cancellationRate = cancellationRate; }
        public String getSeatOccupancyRate() { return seatOccupancyRate; }
        public void setSeatOccupancyRate(String seatOccupancyRate) { this.seatOccupancyRate = seatOccupancyRate; }
    }

    public static class FinancialMetrics {
        @Field("gross_merchandise_value") private double grossMerchandiseValue;
        @Field("platform_revenue") private double platformRevenue;
        @Field("avg_payment_per_ride") private double avgPaymentPerRide;

        public double getGrossMerchandiseValue() { return grossMerchandiseValue; }
        public void setGrossMerchandiseValue(double grossMerchandiseValue) { this.grossMerchandiseValue = grossMerchandiseValue; }
        public double getPlatformRevenue() { return platformRevenue; }
        public void setPlatformRevenue(double platformRevenue) { this.platformRevenue = platformRevenue; }
        public double getAvgPaymentPerRide() { return avgPaymentPerRide; }
        public void setAvgPaymentPerRide(double avgPaymentPerRide) { this.avgPaymentPerRide = avgPaymentPerRide; }
    }

    public static class EfficiencyMetrics {
        @Field("avg_time_to_be_booked_mins") private double avgTimeToBeBookedMins;
        @Field("avg_negotiation_discount") private String avgNegotiationDiscount;

        public double getAvgTimeToBeBookedMins() { return avgTimeToBeBookedMins; }
        public void setAvgTimeToBeBookedMins(double avgTimeToBeBookedMins) { this.avgTimeToBeBookedMins = avgTimeToBeBookedMins; }
        public String getAvgNegotiationDiscount() { return avgNegotiationDiscount; }
        public void setAvgNegotiationDiscount(String avgNegotiationDiscount) { this.avgNegotiationDiscount = avgNegotiationDiscount; }
    }

    // --- Main Getters/Setters ---
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public UserMetrics getUserMetrics() { return userMetrics; }
    public void setUserMetrics(UserMetrics userMetrics) { this.userMetrics = userMetrics; }
    public BookingMetrics getBookingMetrics() { return bookingMetrics; }
    public void setBookingMetrics(BookingMetrics bookingMetrics) { this.bookingMetrics = bookingMetrics; }
    public FinancialMetrics getFinancialMetrics() { return financialMetrics; }
    public void setFinancialMetrics(FinancialMetrics financialMetrics) { this.financialMetrics = financialMetrics; }
    public EfficiencyMetrics getEfficiencyMetrics() { return efficiencyMetrics; }
    public void setEfficiencyMetrics(EfficiencyMetrics efficiencyMetrics) { this.efficiencyMetrics = efficiencyMetrics; }
}