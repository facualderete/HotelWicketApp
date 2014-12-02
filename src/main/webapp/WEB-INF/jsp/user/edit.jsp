<%@ include file="../header.jsp" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <h2>Editar perfil para ${userEdit.email}</h2>

            <form:form action="./edit" method="post" class="form-horizontal" commandName="registerForm" enctype="multipart/form-data">

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
        		<label class="control-label" for="noPhoto">Sin foto de perfil</label>
        		<div class="controls">
            	<form:checkbox path="noPhoto"></form:checkbox>
        		</div>
    		  </div>
              

              <div class="control-group">
                <div class="controls">
                  <button type="submit" class="btn btn-primary">Terminar edición</button>
                </div>
              </div>
            </form:form>

<%@ include file="../footer.jsp" %>