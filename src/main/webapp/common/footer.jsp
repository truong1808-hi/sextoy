<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="messages" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<footer class="main-footer">
    <div class="container">
        <p>&copy; 2025 VT88Store - <fmt:message key="footer.storeName" />. | Hotline: 0965 629 698</p>
        <div class="footer-links">
            <%-- (ĐÃ SỬA) Link trỏ đến Servlet Pattern (/privacy và /terms) --%>
            <a href="${pageContext.request.contextPath}/privacy"><fmt:message key="footer.policy" /></a>
            <a href="${pageContext.request.contextPath}/terms"><fmt:message key="footer.terms" /></a>
        </div>
    </div>
</footer>

<div class="modal-overlay" id="languageModal">
    <div class="language-modal new-lang-style">
        <div class="modal-header">
            <h3><fmt:message key="lang.select" /></h3>
        </div>
         <div class="modal-body lang-selector-body">
            <label class="language-option" data-lang-code="vi">
                <input type="radio" name="language-selector" value="vi" ${currentLang == 'vi' ? 'checked' : ''}>
                <span class="lang-text"><fmt:message key="lang.vi" /></span>
            </label>
            <label class="language-option" data-lang-code="en">
                <input type="radio" name="language-selector" value="en" ${currentLang == 'en' ? 'checked' : ''}>
                <span class="lang-text"><fmt:message key="lang.en" /></span>
            </label>
        </div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</body>
</html>