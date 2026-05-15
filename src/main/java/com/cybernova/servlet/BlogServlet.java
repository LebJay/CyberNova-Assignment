package com.cybernova.servlet;

import com.cybernova.dao.BlogDAO;
import com.cybernova.model.Blog;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/blog")
public class BlogServlet extends HttpServlet {

    private final BlogDAO blogDAO = new BlogDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Blog> allBlogs = blogDAO.findAllBlogs();
            System.out.println("DEBUG: BlogServlet found " + allBlogs.size() + " blogs");
            request.setAttribute("blogs", allBlogs);
        } catch (Exception loadError) {
            request.setAttribute("blogError", "Could not load blog posts");
            System.out.println("DEBUG: BlogServlet error: " + loadError.getMessage());
        }

        request.getRequestDispatcher("/blog.jsp").forward(request, response);
    }
}
