<%@ taglib tagdir="/WEB-INF/tags" prefix="customTag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ attribute name="page" required="true" rtexprvalue="true"%>
<%@ attribute name="nbPage" required="true" type="java.lang.Integer"%>
<%@ attribute name="length" required="true" type="java.lang.Integer"%>

<ul class="pagination">
	<c:if test="${page > 1}">
		<customTag:link href="dashboard" page="${page}" length="${length}"
			previous="true" li="default"></customTag:link>
	</c:if>

	<c:forEach var="i" begin="${page > 5 ? page - 5 : 1}"
		end="${page + 5 <= nbPage ? page + 5 : nbPage}">

		<c:choose>
			<c:when test="${page == i}">
				<customTag:link href="dashboard" page="${i}" length="${length}"
					text="${i}" li="active" />
			</c:when>
			<c:otherwise>
				<customTag:link href="dashboard" page="${i}" length="${length}"
					text="${i}" li="default" />
			</c:otherwise>
		</c:choose>


	</c:forEach>

	<c:if test="${page < nbPage}">
		<customTag:link href="dashboard" page="${page}" length="${length}"
			next="true" li="default" />
	</c:if>
</ul>

<div class="btn-group btn-group-sm pull-right" role="group">

	<c:forEach items="${lengths}" var="i">
		<c:choose>
			<c:when test="${length == i}">
				<customTag:link href="dashboard" page="1" length="${i}" text="${i}"
					button="primary" />
			</c:when>
			<c:otherwise>
				<customTag:link href="dashboard" page="1" length="${i}" text="${i}"
					button="default" />
			</c:otherwise>
		</c:choose>
	</c:forEach>
</div>