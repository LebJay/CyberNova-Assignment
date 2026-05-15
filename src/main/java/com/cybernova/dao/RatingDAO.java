package com.cybernova.dao;

import com.cybernova.model.Rating;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO {

    // Save new rating as not approved
    public void saveNewRating(Rating rating) throws Exception {
        String sql = "INSERT INTO rating (customer_name, rating_value, comment, approved) VALUES (?, ?, ?, FALSE)";
        try (Connection con = DatabaseConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rating.getCustomerName());
            ps.setInt(2, rating.getRatingValue());
            ps.setString(3, rating.getComment());
            ps.executeUpdate();
        }
    }

    // Public view – only approved testimonials
    public List<Rating> findApprovedRatings() throws Exception {
        String sql = "SELECT rating_id, customer_name, rating_value, comment, created_date " +
                "FROM rating WHERE approved = TRUE ORDER BY created_date DESC";
        List<Rating> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.openConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Rating r = new Rating();
                r.setRatingId(rs.getInt("rating_id"));
                r.setCustomerName(rs.getString("customer_name"));
                r.setRatingValue(rs.getInt("rating_value"));
                r.setComment(rs.getString("comment"));
                r.setCreatedDate(rs.getTimestamp("created_date"));
                list.add(r);
            }
        }
        return list;
    }

    // Admin view – all ratings
    public List<Rating> findAllRatings() throws Exception {
        String sql = "SELECT rating_id, customer_name, rating_value, comment, approved, created_date " +
                "FROM rating ORDER BY created_date DESC";
        List<Rating> list = new ArrayList<>();
        try (Connection con = DatabaseConnection.openConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Rating r = new Rating();
                r.setRatingId(rs.getInt("rating_id"));
                r.setCustomerName(rs.getString("customer_name"));
                r.setRatingValue(rs.getInt("rating_value"));
                r.setComment(rs.getString("comment"));
                r.setApproved(rs.getBoolean("approved"));
                r.setCreatedDate(rs.getTimestamp("created_date"));
                list.add(r);
            }
        }
        return list;
    }

    public void approve(int ratingId) throws Exception {
        String sql = "UPDATE rating SET approved = TRUE WHERE rating_id = ?";
        try (Connection con = DatabaseConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ratingId);
            ps.executeUpdate();
        }
    }

    public void delete(int ratingId) throws Exception {
        String sql = "DELETE FROM rating WHERE rating_id = ?";
        try (Connection con = DatabaseConnection.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ratingId);
            ps.executeUpdate();
        }
    }

    public double calculateAverageRating() throws Exception {
        String sql = "SELECT AVG(rating_value) AS avg_value FROM rating WHERE approved = TRUE";
        try (Connection con = DatabaseConnection.openConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return Math.round(rs.getDouble("avg_value") * 10.0) / 10.0;
            }
        }
        return 0.0;
    }

    public int countTotalRatings() throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM rating";
        try (Connection con = DatabaseConnection.openConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}
