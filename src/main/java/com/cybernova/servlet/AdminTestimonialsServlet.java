package com.cybernova.servlet;

import com.cybernova.dao.RatingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/testimonials")
public class AdminTestimonialsServlet extends HttpServlet {

    private final RatingDAO ratingDAO = new RatingDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            req.setAttribute("testimonials", ratingDAO.findAllRatings());
            req.setAttribute("averageRating", ratingDAO.calculateAverageRating());
            req.setAttribute("totalRatings", ratingDAO.countTotalRatings());
            req.getRequestDispatcher("/admin/testimonials.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        try {
            if ("approve".equals(action)) {
                ratingDAO.approve(Integer.parseInt(req.getParameter("ratingId")));
                resp.sendRedirect(req.getContextPath() + "/admin/testimonials?approved=1");
            } else if ("delete".equals(action)) {
                ratingDAO.delete(Integer.parseInt(req.getParameter("ratingId")));
                resp.sendRedirect(req.getContextPath() + "/admin/testimonials?deleted=1");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
