<%@ include file="../header.jsp" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<h2>Registrar nuevo hotel</h2>

<form:form action="./add" commandName="addHotelForm" method="post" class="form-horizontal" enctype="multipart/form-data">

    <div class="control-group">
        <label class="control-label" for="name">Nombre</label>
        <div class="controls">
            <form:input type="text" path="name" />
            <form:errors class="alert alert-error help-inline" path="name" />
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="category">Categoría</label>
        <div class="controls">
            <form:input type="text" path="category" />
            <form:errors class="alert alert-error help-inline" path="category" />
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="type">Tipo</label>
        <div class="controls">
            <form:select class="span2" path="type">
                <c:forEach items="${typeList}" var="option">
                    <form:option value="${option.name}">${option.name}</form:option>
                </c:forEach>
            </form:select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="price">Precio</label>
        <div class="controls">
            <form:input type="text" path="price" />
            <form:errors class="alert alert-error help-inline" path="price" />
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="city">Ciudad</label>
        <div class="controls">
            <form:select class="span2" path="destinationId">
                <c:forEach var="d" items="${destinations}">
                    <form:option value="${d.id}">${d.destination}</form:option>
                </c:forEach>
            </form:select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="address">Dirección</label>
        <div class="controls">
            <form:input type="text" path="address" />
            <form:errors class="alert alert-error help-inline" path="address" />
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="phone">Teléfono</label>
        <div class="controls">
            <form:input type="text" path="phone" />
            <form:errors class="alert alert-error help-inline" path="phone" />
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="website">Sitio Web</label>
        <div class="controls">
            <form:input type="text" path="website" />
            <form:errors class="alert alert-error help-inline" path="website" />
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="breakfast">Desayuno incluído</label>
        <div class="controls">
            <form:checkbox path="breakfast"></form:checkbox>
        </div>
    </div>

    <div class="control-group">
        <div class="controls">
            <button type="submit" class="btn btn-primary">Registrar</button>
        </div>
    </div>

</form:form>
<%@ include file="../footer.jsp" %>