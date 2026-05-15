<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Galleries - CyberNova Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body class="admin-body">

<div class="admin-layout">

    <jsp:include page="includes/sidebar.jsp">
        <jsp:param name="activePage" value="galleries"/>
    </jsp:include>

    <main class="admin-main">
        <div class="admin-topbar">
            <button class="admin-mobile-toggle" id="adminMenuToggle"><i class="fa-solid fa-bars"></i></button>
            <h1>Gallery Items</h1>
            <div class="admin-topbar-right">
                <span class="admin-user">Welcome, Admin</span>
                <span class="admin-user-icon"><i class="fa-solid fa-circle-user"></i></span>
            </div>
        </div>

        <div class="admin-content">

            <c:if test="${successMessage ne null}">
                <div class="alert alert-success">
                    ${successMessage}
                </div>
            </c:if>

            <c:if test="${formError ne null}">
                <div class="alert alert-error">
                    ${formError}
                </div>
            </c:if>

            <div class="admin-section">
                <h2>Add Gallery Item</h2>

                <form method="post" action="${pageContext.request.contextPath}/admin/galleries" class="admin-form" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="create">

                    <div class="form-group">
                        <label for="title">Title <span class="required">*</span></label>
                        <input type="text" id="title" name="title" required placeholder="Enter gallery item title">
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="category">Category</label>
                            <input type="text" id="category" name="category" placeholder="e.g., Cyber Awareness Workshops">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="caption">Caption</label>
                        <textarea id="caption" name="caption" rows="3" placeholder="Brief description of the gallery item"></textarea>
                    </div>

                    <div class="form-group">
                        <label for="imageFile">Image</label>
                        <input type="file" id="imageFile" name="imageFile" accept="image/*" class="admin-form form-file">
                        <small class="text-muted-sm">Upload an image to display on the website gallery (max 5MB)</small>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="fa-solid fa-plus"></i> Add Gallery Item
                    </button>
                </form>
            </div>

            <div class="admin-section">
                <h2>Gallery Items</h2>
                <p class="section-description">Manage your gallery items</p>

                <div class="admin-table-container">
                    <c:if test="${empty galleries}">
                        <div class="empty-state">
                            <p>No gallery items yet</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty galleries}">
                        <table class="admin-table">
                            <thead>
                                <tr>
                                    <th>Title</th>
                                    <th>Category</th>
                                    <th>Caption</th>
                                    <th>Date Added</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${galleries}" var="gallery">
                                    <tr>
                                        <td>${gallery.title}</td>
                                        <td>${gallery.category}</td>
                                        <td class="comment-cell">${gallery.caption}</td>
                                        <td>${gallery.createdDate}</td>
                                        <td>
                                            <form method="post" action="${pageContext.request.contextPath}/admin/galleries" style="display: inline;">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="galleryId" value="${gallery.galleryId}">
                                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Delete this gallery item?')">
                                                    <i class="fa-solid fa-trash"></i> Delete
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>

        </div>
    </main>

</div>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    document.getElementById('adminMenuToggle').addEventListener('click', function() {
        document.getElementById('adminSidebar').classList.toggle('show');
        document.getElementById('adminOverlay').classList.toggle('show');
    });

    document.getElementById('adminOverlay').addEventListener('click', function() {
        document.getElementById('adminSidebar').classList.remove('show');
        document.getElementById('adminOverlay').classList.remove('show');
    });
</script>

</body>
</html>
