<%@ include file="../header.jsp" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<h2>Agregar foto a ${hotel.name}</h2>

<form:form action="./addPic" commandName="addHotelPicForm" method="post" class="form-horizontal" enctype="multipart/form-data">

    <div class="control-group">
        <label class="control-label" for="picture">Agregar nueva foto</label>
        <div class="controls">
            <form:input type="file" path="picture" />
            <form:errors class="alert alert-error help-inline" path="picture" />
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="isMain">Establecer como principal</label>
        <div class="controls">
            <form:checkbox path="isMain"></form:checkbox>
        </div>
    </div>

    <div class="control-group">
        <div class="controls">
            <button type="submit" class="btn btn-primary">Guardar cambios</button>
        </div>
    </div>

    <input name="hotelId" id="hotelId" type="hidden" value="${hotel.id}">

</form:form>

<form action="../hotel/view" method="get" class="navbar-form pull-left">
   <input name="id" id="id" type="hidden" value="${hotel.id}">
   <button type="submit" class="btn">Volver</button>
</form>

<%@ include file="../footer.jsp" %>