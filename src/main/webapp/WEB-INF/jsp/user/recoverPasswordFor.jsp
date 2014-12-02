<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
            <h2>Solicitud para recuperación de contraseña</h2>

            <c:if test="${not empty recoverPasswordForError}">
                <div class="alert alert-error">
                  <strong>Error: </strong> ${recoverPasswordForError}
                </div>
            </c:if>

            <form action="../user/recoverPasswordFor" method="post" class="form-horizontal">

              <div class="control-group">
                <label class="control-label" for="email">Email</label>
                <div class="controls">
                  <input type="text" id="userEmail" name="userEmail">
                </div>
              </div>

              <div class="control-group">
                <div class="controls">
                  <button type="submit" class="btn btn-primary">Enviar Email</button>
                </div>
              </div>

            </form>

<%@ include file="../footer.jsp" %>