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
            <h2>Modificar contraseña para ${email}</h2>

            <c:if test="${not empty resetPasswordError}">
                <div class="alert alert-error">
                  <strong>Error: </strong> ${resetPasswordError}
                </div>
            </c:if>

            <form action="../user/resetPassword" method="post" class="form-horizontal">

                <input type="hidden" id="email" name="email" value="${email}">

                <div class="control-group">
                  <label class="control-label" for="password1">Contraseña</label>
                  <div class="controls">
                    <input type="password" id="password1" name="password1">
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" for="password2">Repita contraseña</label>
                  <div class="controls">
                      <input type="password" id="password2" name="password2">
                  </div>
                </div>

              <div class="control-group">
                <div class="controls">
                  <button type="submit" class="btn btn-primary">Cambiar contraseña</button>
                </div>
              </div>

            </form>

<%@ include file="../footer.jsp" %>