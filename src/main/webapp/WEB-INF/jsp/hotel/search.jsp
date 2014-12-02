<%@ include file="../header.jsp" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<h2>Filtros</h2>

    <c:if test="${not empty searchError}">
      <div class="alert alert-error">
        <strong>Error: </strong> ${searchError}
      </div>
    </c:if>

    <form:form class="form-inline" action="./search"  method="get" commandName="searchHotelForm">

      <form:errors class="alert alert-error help-inline" path="*" />

	    <div class="row">
		    <div class="span11">
			    <form:input type="text" class="input-xlarge" placeholder="Nombre de hotel" path="name" />
			    <form:input type="text" class="input-large" placeholder="Ciudad" path="city" />
			    <form:input type="text" class="input-mini" placeholder="$ min." path="pricemin" />
                <form:input type="text" class="input-mini" placeholder="$ max." path="pricemax" />
                <button type="submit" class="btn btn-primary pull-right">Aplicar</button>
			</div>
	    </div>

	    <div class="row">
            <div class="span11">
                <label class="span2">Tipo</label>
                <label class="span2">Desde</label>
                <label class="span2">Hasta</label>
                <label class="span2">Desayuno</label>
            </div>
	    </div>

	    <div class="row">
            <div class="span11">
                <form:select class="span2" path="type">
                    <form:option value="" />
                    <c:forEach items="${typeList}" var="option">
                        <form:option value="${option.name}">${option.name}</form:option>
                    </c:forEach>
                </form:select>
                <form:select class="span2" path="categorymin">
                    <form:option value="" />
                    <c:forEach begin="1" end="5" var="i">
                        <form:option value="${i}">${i} Estrella</form:option>
                    </c:forEach>
                </form:select>
                <form:select class="span2" path="categorymax">
                    <form:option value="" />
                    <c:forEach begin="1" end="5" var="i">
                        <form:option value="${i}">${i} Estrella</form:option>
                    </c:forEach>
                </form:select>
                <form:select class="span2" path="breakfast">
                    <form:option value="" />
                    <form:option value="true">Incluído</form:option>
                    <form:option value="false">No incluído</form:option>
                </form:select>
            </div>
	    </div>
    </form:form>
    
<h2>Resultados</h2>

<table class="table table-striped">
    <thead>
        <tr>
            <th>Nombre</th>
            <th>Ciudad</th>
 			<th>Categoría</th>
 			<th>Precio</th>
            <th>Tipo</th>
            <th>Desayuno</th>
		</tr>
    </thead>
    <tbody>
        <c:forEach var="hotel" items="${hotelsList}">
                <tr>
                    <td><a href="./view?id=${hotel.id}">${hotel.name}</a></td>
                    <td>${hotel.destination.destination}</td>
                    <td>${hotel.category} Estrellas</td>
                    <td>$${hotel.price}</td>
                    <td>${hotel.type}</td>
                    <td>
                        <c:if test="${hotel.breakfast}">
                            Incluído
                        </c:if>
                        <c:if test="${!hotel.breakfast}">
                            No incluído
                        </c:if>
                    </td>
                </tr>        	   
     	</c:forEach>
    </tbody>
</table>



<%@ include file="../footer.jsp" %>