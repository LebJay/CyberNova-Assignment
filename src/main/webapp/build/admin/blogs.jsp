<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blogs - CyberNova Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body class="admin-body">

<div class="admin-layout">

    <jsp:include page="includes/sidebar.jsp">
        <jsp:param name="activePage" value="blogs"/>
    </jsp:include>

    <main class="admin-main">
        <div class="admin-topbar">
            <button class="admin-mobile-toggle" id="adminMenuToggle"><i class="fa-solid fa-bars"></i></button>
            <h1>Blog Posts</h1>
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
                <h2>Create New Blog Post</h2>

                <form method="post" action="${pageContext.request.contextPath}/admin/blogs" class="admin-form">
                    <input type="hidden" name="action" value="create">

                    <div class="form-group">
                        <label for="title">Title <span class="required">*</span></label>
                        <input type="text" id="title" name="title" required placeholder="Enter blog post title">
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="category">Category</label>
                            <input type="text" id="category" name="category" placeholder="e.g., AI & Security">
                        </div>
                        <div class="form-group">
                            <label for="author">Author</label>
                            <input type="text" id="author" name="author" placeholder="e.g., CyberNova Team">
                        </div>
                        <div class="form-group">
                            <label for="publishedDate">Published Date</label>
                            <input type="date" id="publishedDate" name="publishedDate">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="content">Content <span class="required">*</span></label>
                        <textarea id="content" name="content" rows="10" required placeholder="Write your blog post content here"></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="fa-solid fa-plus"></i> Publish Blog Post
                    </button>
                </form>
            </div>

            <div class="admin-section">
                <h2>Published Posts</h2>
                <p class="section-description">Manage your blog posts</p>

                <div class="admin-table-container">
                    <c:if test="${empty blogs}">
                        <div class="empty-state">
                            <p>No blog posts yet</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty blogs}">
                        <table class="admin-table">
                            <thead>
                                <tr>
                                    <th>Title</th>
                                    <th>Category</th>
                                    <th>Author</th>
                                    <th>Published Date</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${blogs}" var="blog">
                                    <tr>
                                        <td>${blog.title}</td>
                                        <td>${blog.category}</td>
                                        <td>${blog.author}</td>
                                        <td>${blog.publishedDate}</td>
                                        <td>
                                            <form method="post" action="${pageContext.request.contextPath}/admin/blogs" style="display: inline;">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="blogId" value="${blog.blogId}">
                                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Delete this blog post?')">
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
