<%@ include file="../header.jsp" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>



<h2>Hoteles más comentados</h2>

<c:if test="${admin}">
    <div>
        <form action="./add" method="get">
          <button type="submit" class="btn btn-success">Agregar nuevo hotel</button>
        </form>
    </div>
</c:if>

<table class="table table-striped">
    <thead>
        <tr>
            <th>Nombre</th>
            <th>Categoría</th>
 			<th>Ciudad</th>
 			<th>Comentarios</th>
		</tr>
    </thead>
    <tbody>
        <c:forEach var="hotel" items="${hotelsList}">
                <tr>
                    <td><a href="./view?id=${hotel.id}">${hotel.name}</a></td>
                    <td>${hotel.category} Estrellas</td>
                    <td>${hotel.destination.destination}</td>
                   	<td>${hotel.amountOfComments}</td>
                </tr>        	   
     	</c:forEach>
    </tbody>
</table>

<form class="form-inline" action="./search" method="get">
<button type="submit" class="btn btn-primary pull-right">Buscar Hotel</button>
<br>
<h2> Destinos </h2>
	<dl>
		<c:forEach var="destination" items="${destinations}" >
			<dt><a href="../hotel/destination?destinationId=${destination.id}">${destination.destination}</a></dt>
		</c:forEach>
	</dl>

<%@ include file="../footer.jsp" %>