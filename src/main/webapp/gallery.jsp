<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/header.jsp">
    <jsp:param name="pageTitle" value="Gallery"/>
</jsp:include>

<section class="page-hero">
    <div class="container">
        <h1>Gallery</h1>
        <p>Highlights from our training workshops, events, and client engagements</p>
    </div>
</section>

<section class="section">
    <div class="container">

        <c:if test="${galleryError ne null}">
            <div class="alert alert-error">${galleryError}</div>
        </c:if>

        <c:if test="${empty galleries}">
            <div class="empty-state">
                <p>No gallery items available at this time. Check back soon!</p>
            </div>
        </c:if>

        <c:if test="${not empty galleries}">
            <div class="gallery-grid">
                <c:forEach items="${galleries}" var="gallery">
                    <div class="gallery-item">
                        <div class="gallery-image-wrap">
                            <c:choose>
                                <c:when test="${gallery.imageType ne null and gallery.imageType ne 'placeholder'}">
                                    <img src="${pageContext.request.contextPath}/gallery-image?id=${gallery.galleryId}" alt="${gallery.title}" class="gallery-image">
                                </c:when>
                                <c:otherwise>
                                    <div class="gallery-placeholder" style="background: linear-gradient(135deg, #00B4D8, #0077B6)">
                                        <span><i class="fa-solid fa-image"></i></span>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="gallery-info">
                            <h3 class="gallery-title">${gallery.title}</h3>
                            <c:if test="${not empty gallery.category}">
                                <span class="gallery-category">${gallery.category}</span>
                            </c:if>
                            <p class="gallery-caption">${gallery.caption}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

    </div>
</section>

<jsp:include page="includes/footer.jsp"/>
