<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Testimonials - CyberNova Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body class="admin-body">

<div class="admin-layout">

    <jsp:include page="includes/sidebar.jsp">
        <jsp:param name="activePage" value="testimonials"/>
    </jsp:include>

    <main class="admin-main">
        <div class="admin-topbar">
            <button class="admin-mobile-toggle" id="adminMenuToggle"><i class="fa-solid fa-bars"></i></button>
            <h1>Client Testimonials</h1>
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

            <div class="admin-stats-row">
                <div class="admin-stat-box">
                    <span class="admin-stat-label">Total Testimonials</span>
                    <span class="admin-stat-value">${totalRatings}</span>
                </div>
                <div class="admin-stat-box">
                    <span class="admin-stat-label">Average Rating</span>
                    <span class="admin-stat-value">${averageRating}/5</span>
                </div>
            </div>

            <div class="admin-section">
                <h2>Submitted Testimonials</h2>
                <p class="section-description">Manage customer testimonials and ratings</p>

                <div class="admin-table-container">
                    <c:if test="${empty testimonials}">
                        <div class="empty-state">
                            <p>No testimonials yet</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty testimonials}">
                        <table class="admin-table">
                            <thead>
                                <tr>
                                    <th>Customer</th>
                                    <th>Rating</th>
                                    <th>Comment</th>
                                    <th>Date</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${testimonials}" var="testimonial">
                                    <tr>
                                        <td>${testimonial.customerName}</td>
                                        <td>
                                            <span class="rating-display">
                                                <c:forEach begin="1" end="${testimonial.ratingValue}">
                                                    <i class="fa-solid fa-star" style="color: #FFB800;"></i>
                                                </c:forEach>
                                                <c:forEach begin="${testimonial.ratingValue + 1}" end="5">
                                                    <i class="fa-regular fa-star" style="color: #ccc;"></i>
                                                </c:forEach>
                                            </span>
                                        </td>
                                        <td class="comment-cell">${testimonial.comment}</td>
                                        <td>${testimonial.createdDate}</td>
                                        <td>
                                            <form method="post" action="${pageContext.request.contextPath}/admin/testimonials" style="display: inline;">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="ratingId" value="${testimonial.ratingId}">
                                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Delete this testimonial?')">
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
