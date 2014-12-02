<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
	    <title>Hotel App</title>
	    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="../../bootstrap/css/bootstrap.css" />
    </head>
	<body>
	<div class="row">
	    <div class="container">
            <h2>Nuevo usuario</h2>

            <c:if test="${not empty registerError}">
                <div class="alert alert-error">
                  <strong>Error: </strong> ${registerError}
                </div>
            </c:if>

            <form:form action="./register" commandName="registerForm" method="post" class="form-horizontal" enctype="multipart/form-data">

              <div class="control-group">
                <label class="control-label" for="name">Nombre</label>
                <div class="controls">
                  <form:input type="text" path="name" />
                  <form:errors class="alert alert-error help-inline" path="name" />
                </div>
              </div>

              <div class="control-group">
                <label class="control-label" for="lastname">Apellido</label>
                <div class="controls">
                    <form:input type="text" path="lastname" />
                    <form:errors class="alert alert-error help-inline" path="lastname" />
                </div>
              </div>

              <div class="control-group">
                <label class="control-label" for="description">Descripción</label>
                <div class="controls">
                    <form:textarea rows="3" path="description"></form:textarea>
                    <form:errors class="alert alert-error help-inline" path="description" />
                </div>
              </div>

              <div class="control-group">
                <label class="control-label" for="email">Email</label>
                <div class="controls">
                  <form:input type="text" path="email" />
                  <form:errors class="alert alert-error help-inline" path="email" />
                </div>
              </div>

              <div class="control-group">
                <label class="control-label" for="password">Contraseña</label>
                <div class="controls">
                  <form:input type="password" path="password" />
                  <form:errors class="alert alert-error help-inline" path="password" />
                </div>
              </div>

              <div class="control-group">
                <label class="control-label" for="password2">Repita contraseña</label>
                <div class="controls">
                    <form:input type="password" path="password2" />
                    <form:errors class="alert alert-error help-inline" path="password2" />
                </div>
              </div>

              <div class="control-group">
                <label class="control-label" for="picture">Foto de perfil</label>
                <div class="controls">
                    <form:input type="file" path="picture" />
                </div>
              </div>

              <div class="control-group">
                <div class="controls">
                  <button type="submit" class="btn btn-primary">Registrar</button>
                </div>
              </div>

	        </form:form>





            <%@ include file="../footer.jsp" %>