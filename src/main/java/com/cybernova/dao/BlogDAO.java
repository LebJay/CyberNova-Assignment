package com.cybernova.dao;

import com.cybernova.model.Blog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BlogDAO {

    public void save(Blog blog) throws Exception {
        String insertQuery = "INSERT INTO blog (title, category, content, author, published_date) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection database = DatabaseConnection.openConnection();
             PreparedStatement statement = database.prepareStatement(insertQuery)) {

            statement.setString(1, blog.getTitle());
            statement.setString(2, blog.getCategory());
            statement.setString(3, blog.getContent());
            statement.setString(4, blog.getAuthor());
            statement.setDate(5, blog.getPublishedDate());

            statement.executeUpdate();
        }
    }

    public void update(Blog blog) throws Exception {
        String updateQuery = "UPDATE blog SET title = ?, category = ?, content = ?, author = ?, "
                + "published_date = ?, updated_date = CURRENT_TIMESTAMP WHERE blog_id = ?";

        try (Connection database = DatabaseConnection.openConnection();
             PreparedStatement statement = database.prepareStatement(updateQuery)) {

            statement.setString(1, blog.getTitle());
            statement.setString(2, blog.getCategory());
            statement.setString(3, blog.getContent());
            statement.setString(4, blog.getAuthor());
            statement.setDate(5, blog.getPublishedDate());
            statement.setInt(6, blog.getBlogId());

            statement.executeUpdate();
        }
    }

    public void delete(int blogId) throws Exception {
        String deleteQuery = "DELETE FROM blog WHERE blog_id = ?";

        try (Connection database = DatabaseConnection.openConnection();
             PreparedStatement statement = database.prepareStatement(deleteQuery)) {

            statement.setInt(1, blogId);
            statement.executeUpdate();
        }
    }

    public Blog findById(int blogId) throws Exception {
        String selectQuery = "SELECT * FROM blog WHERE blog_id = ?";

        try (Connection database = DatabaseConnection.openConnection();
             PreparedStatement statement = database.prepareStatement(selectQuery)) {

            statement.setInt(1, blogId);
            try (ResultSet row = statement.executeQuery()) {
                if (row.next()) {
                    return buildBlogFromRow(row);
                }
            }
        }
        return null;
    }

    public List<Blog> findAllBlogs() throws Exception {
        String selectQuery = "SELECT * FROM blog ORDER BY published_date DESC";
        List<Blog> allBlogs = new ArrayList<>();

        try (Connection database = DatabaseConnection.openConnection();
             Statement statement = database.createStatement();
             ResultSet rows = statement.executeQuery(selectQuery)) {

            while (rows.next()) {
                allBlogs.add(buildBlogFromRow(rows));
            }
        }
        return allBlogs;
    }

    public List<Blog> findByCategory(String category) throws Exception {
        String selectQuery = "SELECT * FROM blog WHERE category = ? ORDER BY published_date DESC";
        List<Blog> blogsByCategory = new ArrayList<>();

        try (Connection database = DatabaseConnection.openConnection();
             PreparedStatement statement = database.prepareStatement(selectQuery)) {

            statement.setString(1, category);
            try (ResultSet rows = statement.executeQuery()) {
                while (rows.next()) {
                    blogsByCategory.add(buildBlogFromRow(rows));
                }
            }
        }
        return blogsByCategory;
    }

    private Blog buildBlogFromRow(ResultSet row) throws Exception {
        Blog blog = new Blog();
        blog.setBlogId(row.getInt("blog_id"));
        blog.setTitle(row.getString("title"));
        blog.setCategory(row.getString("category"));
        blog.setContent(row.getString("content"));
        blog.setAuthor(row.getString("author"));
        blog.setPublishedDate(row.getDate("published_date"));
        blog.setCreatedDate(row.getTimestamp("created_date"));
        blog.setUpdatedDate(row.getTimestamp("updated_date"));
        return blog;
    }
}
