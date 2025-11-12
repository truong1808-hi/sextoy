<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="messages" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/common/header.jsp" />

<main>
    <%-- Slider ảnh thay thế cho banner cũ --%>
    <div class="container">
        <section class="hero-slider">
            <div class="slider-container">
                <div class="slider-wrapper">
                    <div class="slide active"><img src="${pageContext.request.contextPath}/assets/image/MainPage1.png" alt="Banner 1"></div>
                    <div class="slide"><img src="${pageContext.request.contextPath}/assets/image/MainPage2.png" alt="Banner 2"></div>
                    <div class="slide"><img src="${pageContext.request.contextPath}/assets/image/MainPage3.png" alt="Banner 3"></div>
                    <div class="slide"><img src="${pageContext.request.contextPath}/assets/image/MainPage4.png" alt="Banner 4"></div>
                </div>
                <button class="slider-nav prev" id="prevSlide" aria-label="Ảnh trước"><i class="fas fa-chevron-left"></i></button>
                <button class="slider-nav next" id="nextSlide" aria-label="Ảnh kế tiếp"><i class="fas fa-chevron-right"></i></button>
                <div class="slider-dots" id="sliderDots"></div>
            </div>
        </section>
    </div>
    <section class="featured-products" id="featured-products">
        <div class="container">
            <h2><fmt:message key="section.featured" /></h2>
            <div class="product-grid">
                <%-- Tích hợp 4 sản phẩm mới từ giaodien.html/giaodien2.html --%>
                <div class="product-card">
                    <div class="product-image">
                         <span class="badge new">MỚI</span>
                        <img src="${pageContext.request.contextPath}/assets/image/lego1.png" alt="Bộ xếp hình Lego">
                    </div>
                    <h3><fmt:message key="product.lego_classic.name" /></h3>
                    <p class="price current-price"><fmt:message key="product.lego_classic.price" /></p>
                    <a href="#" class="btn-secondary"><fmt:message key="product.add_to_cart" /></a>
                </div>
                <div class="product-card">
                    <div class="product-image">
                         <span class="badge sale">-20%</span>
                        <img src="${pageContext.request.contextPath}/assets/image/mohinh1.png" alt="Mô hình đồ chơi">
                    </div>
                    <h3><fmt:message key="product.elsa_doll.name" /></h3>
                    <p class="price old-price"><fmt:message key="product.elsa_doll.old_price" /></p>
                    <p class="price current-price"><fmt:message key="product.elsa_doll.new_price" /></p>
                    <a href="#" class="btn-secondary"><fmt:message key="product.add_to_cart" /></a>
                </div>
                <div class="product-card">
                    <div class="product-image">
                        <img src="${pageContext.request.contextPath}/assets/image/GauBong1.png" alt="Gấu bông">
                    </div>
                    <h3><fmt:message key="product.wood_stack.name" /></h3>
                    <p class="price current-price"><fmt:message key="product.wood_stack.price" /></p>
                     <a href="#" class="btn-secondary"><fmt:message key="product.add_to_cart" /></a>
                </div>
                <div class="product-card">
                    <div class="product-image">
                        <span class="badge new">HOT</span>
                         <img src="${pageContext.request.contextPath}/assets/image/PhuongTienDoChoi1.png" alt="Phương tiện đồ chơi">
                    </div>
                    <h3><fmt:message key="product.supercar.name" /></h3>
                    <p class="price current-price"><fmt:message key="product.supercar.price" /></p>
                     <a href="#" class="btn-secondary"><fmt:message key="product.add_to_cart" /></a>
                </div>
            </div>
        </div>
    </section>
</main>

<%-- Nhúng tệp JS cho slider --%>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>

<jsp:include page="/common/footer.jsp" />