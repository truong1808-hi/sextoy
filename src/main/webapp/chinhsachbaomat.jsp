<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="messages" />

<jsp:include page="/common/header.jsp" />

<main>
    <section class="policy-content">
        <div class="container">
            <h1><fmt:message key="policy.h1" /></h1>
            <p class="last-updated"><fmt:message key="policy.last_updated" /></p>
            
            <h2><fmt:message key="policy.section1.h2" /></h2>
            <p><fmt:message key="policy.section1.p1" /></p>
            <ul>
                <li><fmt:message key="policy.section1.li1" /></li>
                <li><fmt:message key="policy.section1.li2" /></li>
                <li><fmt:message key="policy.section1.li3" /></li>
            </ul>
            
            <h2><fmt:message key="policy.section2.h2" /></h2>
            <p><fmt:message key="policy.section2.p1" /></p>
            <ul>
                <li><fmt:message key="policy.section2.li1" /></li>
                <li><fmt:message key="policy.section2.li2" /></li>
                <li><fmt:message key="policy.section2.li3" /></li>
                <li><fmt:message key="policy.section2.li4" /></li>
            </ul>
            
            <h2><fmt:message key="policy.section3.h2" /></h2>
            <p><fmt:message key="policy.section3.p1" /></p>
            <ul>
                <li><fmt:message key="policy.section3.li1" /></li>
                <li><fmt:message key="policy.section3.li2" /></li>
                <li><fmt:message key="policy.section3.li3" /></li>
            </ul>
            
            <h2><fmt:message key="policy.section4.h2" /></h2>
            <p><fmt:message key="policy.section4.p1" /></p>
        </div>
    </section>
</main>

<jsp:include page="/common/footer.jsp" />