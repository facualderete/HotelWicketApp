<%@ include file="../header.jsp" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>

<h2> Hoteles en ${destination.destination}</h2>

    <c:if test="${admin}">
        <div class="span2">
            <form action="./editDestination" method="get">
              <input name="destinationId" id="destinationId" type="hidden" value="${destination.id}">
              <button type="submit" class="btn btn-danger">Editar destino</button>
            </form>
        </div>
    </c:if>

    <br>

    <div>
        <c:if test="${not empty destinationPic}">
            <img class="profile" src="${pageContext.request.contextPath}/bin/hotel/picture?pictureId=${destinationPic.id}">
        </c:if>
    </div>
    <br>
    <div>
        <p class="lead">${destination.details}</p>
    </div>

	<table class="table table-striped">
    <thead>
        <tr>
            <th>Nombre</th>
            <th>Categoría</th>
            <th>Precio</th>
            <th>Dirección</th>
            <th>Desayuno</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="h" items="${hotels}">
            <c:if test="${h.active || admin}">
                <tr>
                    <td><a href="../hotel/view?id=${h.id}">${h.name}</a></td>
                    <td>${h.category}</td>
               		<td>${h.price}</td>
                    <td>${h.address}</td>
                    <td>
                        <c:if test="${h.breakfast}">
                            Incluído
                        </c:if>
                        <c:if test="${!h.breakfast}">
                            No incluído
                        </c:if>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </tbody>
</table>

<%@ include file="../footer.jsp" %>