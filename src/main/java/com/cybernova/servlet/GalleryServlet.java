package com.cybernova.servlet;

import com.cybernova.dao.GalleryDAO;
import com.cybernova.model.Gallery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/gallery")
public class GalleryServlet extends HttpServlet {

    private final GalleryDAO galleryDAO = new GalleryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Gallery> allGalleries = galleryDAO.findAllGalleries();
            System.out.println("DEBUG: GalleryServlet found " + allGalleries.size() + " galleries");
            request.setAttribute("galleries", allGalleries);
        } catch (Exception loadError) {
            request.setAttribute("galleryError", "Could not load gallery items");
            System.out.println("DEBUG: GalleryServlet error: " + loadError.getMessage());
        }

        request.getRequestDispatcher("/gallery.jsp").forward(request, response);
    }
}
