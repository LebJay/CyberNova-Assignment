package com.cybernova.servlet;

import com.cybernova.dao.RatingDAO;
import com.cybernova.dao.ServiceRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HomeServlet extends HttpServlet {

    private final ServiceRequestDAO requestDAO = new ServiceRequestDAO();
    private final RatingDAO ratingDAO = new RatingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int totalRequests = requestDAO.countTotalRequests();
            int resolvedThreats = requestDAO.countResolvedRequests();
            int countriesServed = requestDAO.countDistinctCountries();
            int totalRatings = ratingDAO.countTotalRatings();

            request.setAttribute("totalRequests", totalRequests);
            request.setAttribute("resolvedThreats", resolvedThreats);
            request.setAttribute("countriesServed", countriesServed);
            request.setAttribute("totalRatings", totalRatings);

        } catch (Exception e) {
            getServletContext().log("Failed to load homepage stats", e);

            request.setAttribute("totalRequests", 0);
            request.setAttribute("resolvedThreats", 0);
            request.setAttribute("countriesServed", 0);
            request.setAttribute("totalRatings", 0);
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}