package com.cybernova.servlet;

import com.cybernova.dao.GalleryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/gallery-image")
public class GalleryImageServlet extends HttpServlet {

    private final GalleryDAO galleryDAO = new GalleryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing gallery id");
            return;
        }

        try {
            int id = Integer.parseInt(idParam.trim());
            byte[] imageData = galleryDAO.getImageData(id);

            if (imageData == null || imageData.length == 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
                return;
            }

            response.setContentType("image/jpeg");
            response.setContentLength(imageData.length);
            try (OutputStream out = response.getOutputStream()) {
                out.write(imageData);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid gallery id");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
