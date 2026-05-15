package com.cybernova.servlet;

import com.cybernova.dao.GalleryDAO;
import com.cybernova.model.Gallery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet("/admin/galleries")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class AdminGalleriesServlet extends HttpServlet {

    private final GalleryDAO galleryDAO = new GalleryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Gallery> galleries = galleryDAO.findAllGalleries();
            request.setAttribute("galleries", galleries);

            String success = request.getParameter("success");
            String deleted = request.getParameter("deleted");
            if (success != null) request.setAttribute("successMessage", "Gallery item added successfully.");
            if (deleted != null) request.setAttribute("successMessage", "Gallery item deleted.");

            request.getRequestDispatcher("/admin/galleries.jsp").forward(request, response);
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
                String caption = request.getParameter("caption");
                String category = request.getParameter("category");

                if (title == null || title.trim().isEmpty()) {
                    request.setAttribute("formError", "Title is required.");
                    List<Gallery> galleries = galleryDAO.findAllGalleries();
                    request.setAttribute("galleries", galleries);
                    request.getRequestDispatcher("/admin/galleries.jsp").forward(request, response);
                    return;
                }

                Part filePart = request.getPart("imageFile");
                byte[] imageData = null;
                String imageType = "image/jpeg";
                if (filePart != null && filePart.getSize() > 0) {
                    imageType = filePart.getContentType();
                    try (InputStream input = filePart.getInputStream()) {
                        imageData = input.readAllBytes();
                    }
                }

                Gallery gallery = new Gallery();
                gallery.setTitle(title.trim());
                gallery.setCaption(caption != null ? caption.trim() : "");
                gallery.setCategory(category != null ? category.trim() : "");
                gallery.setImageType(imageType);
                gallery.setImageData(imageData);

                galleryDAO.save(gallery);
                response.sendRedirect(request.getContextPath() + "/admin/galleries?success=1");

            } else if ("delete".equals(action)) {
                String idParam = request.getParameter("galleryId");
                if (idParam != null && !idParam.trim().isEmpty()) {
                    galleryDAO.delete(Integer.parseInt(idParam.trim()));
                }
                response.sendRedirect(request.getContextPath() + "/admin/galleries?deleted=1");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
