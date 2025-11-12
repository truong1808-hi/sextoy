<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- (ĐÃ SỬA) Dùng đúng URI của Jakarta EE 10+ --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="messages" />

<%-- Đọc đúng key "jakarta." từ session --%>
<c:set var="currentLang" value="${not empty sessionScope['jakarta.servlet.jsp.jstl.fmt.locale.session'] ? sessionScope['jakarta.servlet.jsp.jstl.fmt.locale.session'].language : 'vi'}" />
<html lang="${currentLang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="site.title" /></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<%-- (CODE MỚI) Thanh thông báo toàn cục --%>
<c:if test="${not empty sessionScope.globalMessage}">
    <div class="global-message-bar ${sessionScope.globalMessageType == 'error' ? 'error' : 'warning'}">
        <div class="container" style="display: flex; justify-content: center; align-items: center;">
            ${sessionScope.globalMessage}
            
            <%-- Nếu có link (đến trang profile), hiển thị link --%>
            <c:if test="${not empty sessionScope.globalMessageLink}">
                <a href="${sessionScope.globalMessageLink}">Cập nhật ngay</a>
            </c:if>
        </div>
    </div>
    
    <%-- Xóa thông báo khỏi session sau khi đã hiển thị --%>
    <c:remove var="globalMessage" scope="session"/>
    <c:remove var="globalMessageType" scope="session"/>
    <c:remove var="globalMessageLink" scope="session"/>
</c:if>
<%-- (KẾT THÚC CODE MỚI) --%>


<div class="contact-widget" id="contactWidget">
    <button class="contact-btn primary-contact-btn" id="contactToggleBtn" aria-label="Hỗ trợ khách hàng">
        <i class="fas fa-headset"></i>
    </button>
    <div class="contact-dropdown-menu">
        <a href="https://zalo.me/0965629698" target="_blank" class="contact-dropdown-item zalo-btn" aria-label="Liên hệ qua Zalo">
            <i class="fab fa-whatsapp"></i>
            <span class="tooltip-text">Zalo</span>
        </a>
        <a href="https://m.me/khanh.shyz309" target="_blank" class="contact-dropdown-item messenger-btn" aria-label="Liên hệ qua Messenger">
            <i class="fab fa-facebook-messenger"></i>
            <span class="tooltip-text">Messenger</span>
        </a>
        <a href="mailto:support@vt88store.com" class="contact-dropdown-item email-btn" aria-label="Gửi Email">
            <i class="fas fa-envelope"></i>
            <span class="tooltip-text">Email</span>
        </a>
    </div>
</div>

<header class="main-header">
    <div class="container header-container">
        <div class="header-left">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/home">
                    <img src="${pageContext.request.contextPath}/assets/image/logo.png" alt="VT88Store Logo">
                    <span class="store-name">VT88Store</span>
                 </a>
            </div>
            <nav class="main-nav">
                <ul>
                    <li class="dropdown">
                        <a href="#"><fmt:message key="nav.categories" /> <i class="fas fa-chevron-down"></i></a>
                        <div class="dropdown-content">
                            <%-- Tải động danh mục từ CSDL --%>
                            <c:forEach items="${listCategories}" var="category">
                                <a href="#">${category.tenDanhMuc}</a>
                            </c:forEach>
                        </div>
                    </li>

                    <li class="dropdown" id="filterDropdown">
                        <a href="javascript:void(0)">
                            <i class="fas fa-filter"></i> <fmt:message key="nav.filter" /> <i class="fas fa-chevron-down"></i>
                        </a>
                        
                        <div class="dropdown-content filter-content">
                            <div class="filter-section">
                                <h4><i class="fas fa-child"></i> <fmt:message key="filter.by_age" /></h4>
                                <label><input type="checkbox" name="age" value="0-3"> 0 - 3 tuổi</label>
                                <label><input type="checkbox" name="age" value="4-6"> 4 - 6 tuổi</label>
                                <label><input type="checkbox" name="age" value="7-9"> 7 - 9 tuổi</label>
                                <label><input type="checkbox" name="age" value="10+"> 10+ tuổi</label>
                            </div>
                            
                            <div class="filter-section filter-section-price">
                                <h4><i class="fas fa-tags"></i> <fmt:message key="filter.by_price" /></h4>
                                <label><input type="checkbox" name="price" value="under-100k"> Dưới 100.000₫</label>
                                <label><input type="checkbox" name="price" value="100k-300k"> 100.000₫ - 300.000₫</label>
                                <label><input type="checkbox" name="price" value="300k-700k"> 300.000₫ - 700.000₫</label>
                                <label><input type="checkbox" name="price" value="over-700k"> Trên 700.000₫</label>
                                
                                <div class="price-range-input">
                                    <input type="number" placeholder="<fmt:message key='filter.from'/>" min="0" step="10000" id="min-price-filter">
                                     <span>-</span>
                                    <input type="number" placeholder="<fmt:message key='filter.to'/>" min="0" step="10000" id="max-price-filter">
                                   <button class="btn-primary btn-apply"><i class="fas fa-check"></i> <fmt:message key="filter.apply_short"/></button>
                                </div>
                            </div>
                            
                            <div class="filter-apply">
                                <button class="btn-primary"><fmt:message key="filter.apply" /></button>
                            </div>
                        </div>
                    </li>
                </ul>
            </nav>
        </div>
     
        <div class="search-bar-container">
            <form action="#" class="search-form">
                <input type="text" placeholder="<fmt:message key="nav.search_placeholder" />">
                <button type="submit" aria-label="Tìm kiếm"><i class="fas fa-search"></i></button>
            </form>
        </div>
        
 
        <div class="utility-icon-group">
            <div class="cart-wrapper cart-icon" id="cartToggle" aria-label="Giỏ hàng">
                <i class="fas fa-shopping-cart"></i>
                <span class="cart-badge" id="cart-count">0</span>
                 <div class="mini-cart" id="mini-cart">
                    
                    <%-- TRẠNG THÁI GIỎ HÀNG RỖNG --%>
                    <div class="cart-empty-state active-state" id="empty-state">
                        <i class="fas fa-box-open" style="font-size:3rem; color: #ccc;"></i>
                        <p><fmt:message key="cart.empty" /></p>
                        <a href="#featured-products" class="btn-primary" style="margin-top: 15px; width: 100%; text-decoration: none;"><fmt:message key="cart.continue_shopping" /></a>
                    </div>
                    
                    <%-- TRẠNG THÁI GIỎ HÀNG ĐẦY --%>
                    <div class="cart-filled-state" id="filled-state" style="display: none;">
                        <h4 class="mini-cart-title"><fmt:message key="cart.title"/> (<span id="filled-item-count">0</span>)</h4>
                        <div class="mini-cart-items-list">
                        </div>
                        <div class="mini-cart-summary">
                            <span>Tổng cộng:</span>
                            <span class="total-price">0đ</span>
                        </div>
                        <div class="mini-cart-actions">
                            <a href="cart.html" class="btn-primary" style="text-decoration: none;"><fmt:message key="cart.checkout"/></a>
                            <a href="cart.html" class="btn-secondary" style="text-decoration: none;"><fmt:message key="cart.view_cart"/></a>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="utility-icons">
                <c:if test="${sessionScope.acc == null}">
                     <a href="${pageContext.request.contextPath}/login" aria-label="Tài khoản"><i class="fas fa-user"></i></a>
                </c:if>
                <c:if test="${sessionScope.acc != null}">
                    <%-- (ĐÃ SỬA) Thêm link đến trang profile --%>
                    <a href="${pageContext.request.contextPath}/profile" aria-label="Tài khoản"><i class="fas fa-user" style="color: var(--primary-color);"></i></a>
                     <a href="${pageContext.request.contextPath}/logout" aria-label="Đăng xuất"><i class="fas fa-sign-out-alt"></i></a>
                </c:if>
                
                <a href="#" class="btn-lang-modal" id="open-lang-modal" aria-label="Thay đổi ngôn ngữ">
                    <c:if test="${currentLang == 'en'}">
                        <img src="https://flagcdn.com/w20/us.png" alt="US Flag" class="flag-icon-header">
                    </c:if>
                    <c:if test="${currentLang != 'en'}">
                        <img src="https://flagcdn.com/w20/vn.png" alt="Cờ Việt Nam" class="flag-icon-header">
                    </c:if>
                </a>
            </div>
        </div>
    </div>
</header>