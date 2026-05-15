package com.cybernova.servlet;

import com.cybernova.dao.BlogDAO;
import com.cybernova.model.Blog;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/admin/blogs")
public class AdminBlogsServlet extends HttpServlet {

    private final BlogDAO blogDAO = new BlogDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Blog> blogs = blogDAO.findAllBlogs();
            request.setAttribute("blogs", blogs);

            String success = request.getParameter("success");
            String deleted = request.getParameter("deleted");
            if (success != null) request.setAttribute("successMessage", "Blog post created successfully.");
            if (deleted != null) request.setAttribute("successMessage", "Blog post deleted.");

            request.getRequestDispatcher("/admin/blogs.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                String title = request.getParameter("title");
                String category = request.getParameter("category");
                String content = request.getParameter("content");
                String author = request.getParameter("author");
                String dateParam = request.getParameter("publishedDate");

                if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
                    request.setAttribute("formError", "Title and content are required.");
                    List<Blog> blogs = blogDAO.findAllBlogs();
                    request.setAttribute("blogs", blogs);
                    request.getRequestDispatcher("/admin/blogs.jsp").forward(request, response);
                    return;
                }

                Blog blog = new Blog();
                blog.setTitle(title.trim());
                blog.setCategory(category != null ? category.trim() : "");
                blog.setContent(content.trim());
                blog.setAuthor(author != null ? author.trim() : "");
                blog.setPublishedDate(dateParam != null ? Date.valueOf(dateParam.trim()) : new Date(System.currentTimeMillis()));

                blogDAO.save(blog);
                response.sendRedirect(request.getContextPath() + "/admin/blogs?success=1");

            } else if ("delete".equals(action)) {
                String idParam = request.getParameter("blogId");
                if (idParam != null && !idParam.trim().isEmpty()) {
                    blogDAO.delete(Integer.parseInt(idParam.trim()));
                }
                response.sendRedirect(request.getContextPath() + "/admin/blogs?deleted=1");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
