<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="messages" />

<jsp:include page="/common/header.jsp" />

<main>
    <section class="policy-content">
        <div class="container">
            <h1><fmt:message key="terms.h1" /></h1>
            <p class="last-updated"><fmt:message key="terms.last_updated" /></p>
            
            <h2><fmt:message key="terms.section1.h2" /></h2>
            <p><fmt:message key="terms.section1.p1" /></p>
            
            <h2><fmt:message key="terms.section2.h2" /></h2>
            <ul>
                <li><fmt:message key="terms.section2.li1" /></li>
                <li><fmt:message key="terms.section2.li2" /></li>
                <li><fmt:message key="terms.section2.li3" /></li>
            </ul>
            
            <h2><fmt:message key="terms.section3.h2" /></h2>
            <ul>
                <li><fmt:message key="terms.section3.li1" /></li>
                <li><fmt:message key="terms.section3.li2" /></li>
                <li><fmt:message key="terms.section3.li3" /></li>
            </ul>
            
            <h2><fmt:message key="terms.section4.h2" /></h2>
            <p><fmt:message key="terms.section4.p1" /></p>

            <h2><fmt:message key="terms.section5.h2" /></h2>
            <p><fmt:message key="terms.section5.p1" /></p>
        </div>
    </section>
</main>

<jsp:include page="/common/footer.jsp" />