package com.cybernova.dao;

import com.cybernova.model.Gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GalleryDAO {

    public void save(Gallery gallery) throws Exception {
        String insertQuery = "INSERT INTO gallery (title, caption, category, image_data, image_type) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection database = DatabaseConnection.openConnection();
             PreparedStatement statement = database.prepareStatement(insertQuery)) {

            statement.setString(1, gallery.getTitle());
            statement.setString(2, gallery.getCaption());
            statement.setString(3, gallery.getCategory());
            statement.setBytes(4, gallery.getImageData());
            statement.setString(5, gallery.getImageType());

            statement.executeUpdate();
        }
    }

    public void delete(int galleryId) throws Exception {
        String deleteQuery = "DELETE FROM gallery WHERE gallery_id = ?";

        try (Connection database = DatabaseConnection.openConnection();
             PreparedStatement statement = database.prepareStatement(deleteQuery)) {

            statement.setInt(1, galleryId);
            statement.executeUpdate();
        }
    }

    public Gallery findById(int galleryId) throws Exception {
        String selectQuery = "SELECT * FROM gallery WHERE gallery_id = ?";

        try (Connection database = DatabaseConnection.openConnection();
             PreparedStatement statement = database.prepareStatement(selectQuery)) {

            statement.setInt(1, galleryId);
            try (ResultSet row = statement.executeQuery()) {
                if (row.next()) {
                    return buildGalleryFromRow(row);
                }
            }
        }
        return null;
    }

    public List<Gallery> findAllGalleries() throws Exception {
        String selectQuery = "SELECT * FROM gallery ORDER BY created_date DESC";
        List<Gallery> allGalleries = new ArrayList<>();

        try (Connection database = DatabaseConnection.openConnection();
             Statement statement = database.createStatement();
             ResultSet rows = statement.executeQuery(selectQuery)) {

            while (rows.next()) {
                allGalleries.add(buildGalleryFromRow(rows));
            }
        }
        return allGalleries;
    }

    public List<Gallery> findByCategory(String category) throws Exception {
        String selectQuery = "SELECT * FROM gallery WHERE category = ? ORDER BY created_date DESC";
        List<Gallery> galleriesByCategory = new ArrayList<>();

        try (Connection database = DatabaseConnection.openConnection();
             PreparedStatement statement = database.prepareStatement(selectQuery)) {

            statement.setString(1, category);
            try (ResultSet rows = statement.executeQuery()) {
                while (rows.next()) {
                    galleriesByCategory.add(buildGalleryFromRow(rows));
                }
            }
        }
        return galleriesByCategory;
    }

    public byte[] getImageData(int galleryId) throws Exception {
        String query = "SELECT image_data FROM gallery WHERE gallery_id = ?";
        try (Connection database = DatabaseConnection.openConnection();
             PreparedStatement statement = database.prepareStatement(query)) {
            statement.setInt(1, galleryId);
            try (ResultSet rows = statement.executeQuery()) {
                if (rows.next()) {
                    return rows.getBytes("image_data");
                }
            }
        }
        return null;
    }

    private Gallery buildGalleryFromRow(ResultSet row) throws Exception {
        Gallery gallery = new Gallery();
        gallery.setGalleryId(row.getInt("gallery_id"));
        gallery.setTitle(row.getString("title"));
        gallery.setCaption(row.getString("caption"));
        gallery.setCategory(row.getString("category"));
        gallery.setImageType(row.getString("image_type"));
        gallery.setCreatedDate(row.getTimestamp("created_date"));
        return gallery;
    }
}
