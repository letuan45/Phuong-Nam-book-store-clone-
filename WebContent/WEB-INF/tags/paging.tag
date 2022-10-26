<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="org.springframework.util.StringUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="pagedListHolder" required="true"
	type="org.springframework.beans.support.PagedListHolder"%>
<%@ attribute name="pagedLink" required="true" type="java.lang.String"%>
<%@ attribute name="pageCount" required="true" type="java.lang.Integer"%>
<%@ attribute name="pageAt" required="true" type="java.lang.Integer"%>


<c:if test="${pageCount > 1}">
	<ul class="pagination float-right">
		<c:forEach begin="0"
			end="${pageCount - 1}" var="i">
			<c:choose>
				<c:when test="${pageAt == i}">
					<li class="page-item active"><a class="page-link" href="#">${i+1}</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link"
						href="<%=StringUtils.replace(pagedLink, "~", String.valueOf(jspContext.getAttribute("i")))%>">${i+1}</a>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
</c:if>