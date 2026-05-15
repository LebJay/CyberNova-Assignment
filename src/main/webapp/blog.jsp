<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="includes/header.jsp">
    <jsp:param name="pageTitle" value="Blog"/>
</jsp:include>

<section class="page-hero">
    <div class="container">
        <h1>Technical Blog</h1>
        <p>Insights, analysis, and thought leadership on cybersecurity trends and best practices</p>
    </div>
</section>

<section class="section">
    <div class="container">
        <c:if test="${blogError ne null}">
            <div class="alert alert-error">${blogError}</div>
        </c:if>

        <c:if test="${not empty blogs}">
            <div class="blog-grid">
                <c:forEach items="${blogs}" var="blog">
                    <article class="blog-card">
                        <div class="blog-meta">
                            <span class="blog-date"><fmt:formatDate value="${blog.publishedDate}" pattern="dd MMMM yyyy" /></span>
                            <c:if test="${not empty blog.category}">
                                <span class="blog-category">${blog.category}</span>
                            </c:if>
                        </div>
                        <h3 class="blog-title">${blog.title}</h3>
                        <c:if test="${not empty blog.author}">
                            <span class="blog-author">By ${blog.author}</span>
                        </c:if>
                        <p class="blog-excerpt">${blog.content}</p>
                    </article>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty blogs}">
            <div class="empty-state">
                <p>No blog posts available at this time. Check back soon!</p>
            </div>
        </c:if>

    </div>
</section>

<jsp:include page="includes/footer.jsp"/>
