<%@ include file="../header.jsp" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <h2>Editar comentario para ${hotel.name}</h2>

            <form:form action="./edit" method="post" class="form-horizontal" commandName="addCommentForm">

              <input name="hotelId" id="hotelId" type="hidden" value="${hotel.id}">
              <input name="commentId" id="commentId" type="hidden" value="${comment.id}">

              <div class="control-group">
                <label class="control-label" for="fromDate">Desde</label>
                <div class="controls">
                  <form:input class="datepicker" type="text" path="fromDate" readonly="true" />
                  <form:errors class="alert alert-error help-inline" path="fromDate" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="toDate">Hasta</label>
                <div class="controls">
                  <form:input class="datepicker" type="text" path="toDate" readonly="true" />
                  <form:errors class="alert alert-error help-inline" path="toDate" />
             </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="reason">Razon de viaje</label>
                <div class="controls">
                	<form:select path="reason">
                        <form:option value="">-</form:option>
						<c:forEach items="${reasonList}" var="option">
                            <form:option value="${option.name}">${option.name}</form:option>
                        </c:forEach>
					</form:select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="companions">Acompañantes</label>
                <div class="controls">
                	<form:select path="companions">
                        <form:option value="">-</form:option>
						<c:forEach items="${companionList}" var="option">
                            <form:option value="${option.name}">${option.name}</form:option>
                        </c:forEach>
					</form:select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="hygiene">Higiene</label>
                <div class="controls">
                	<form:select path="hygiene">
                	    <form:option value="0">No opina</form:option>
						<c:forEach begin="1" end="10" var="i">
                            <form:option value="${i}">${i}</form:option>
                        </c:forEach>
					</form:select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="facilities">Instalaciones</label>
                <div class="controls">
                	<form:select path="facilities">
                        <form:option value="0">No opina</form:option>
						<c:forEach begin="1" end="10" var="i">
                            <form:option value="${i}">${i}</form:option>
                        </c:forEach>
					</form:select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="service">Servicios</label>
                <div class="controls">
                	<form:select path="service">
                        <form:option value="0">No opina</form:option>
						<c:forEach begin="1" end="10" var="i">
                            <form:option value="${i}">${i}</form:option>
                        </c:forEach>
					</form:select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="location">Ubicacion</label>
                <div class="controls">
                	<form:select path="location">
                        <form:option value="0">No opina</form:option>
						<c:forEach begin="1" end="10" var="i">
                            <form:option value="${i}">${i}</form:option>
                        </c:forEach>
					</form:select>
                </div>
              </div>
              <div class="control-group">
                  <label class="control-label" for="price">Precio</label>
                  <div class="controls">
                    <form:select path="price">
                          <form:option value="0">No opina</form:option>
                        <c:forEach begin="1" end="10" var="i">
                              <form:option value="${i}">${i}</form:option>
                          </c:forEach>
                    </form:select>
                  </div>
                </div>
              <div class="control-group">
                <label class="control-label" for="comfort">Confort para el descanso</label>
                <div class="controls">
                    <form:select path="comfort">
                        <form:option value="0">No opina</form:option>
                        <c:forEach begin="1" end="10" var="i">
                            <form:option value="${i}">${i}</form:option>
                        </c:forEach>
                    </form:select>
                </div>
              </div>

              <div class="control-group">
                <label class="control-label" for="details">Escriba su comentario</label>
                <div class="controls">
                  <form:textarea  path="details" rows="3"></form:textarea>
                  <form:errors class="alert alert-error help-inline" path="details" />
                </div>
              </div>

              <div class="control-group">
                <div class="controls">
                  <button type="submit" class="btn btn-primary">Terminar edición</button>
                </div>
              </div>
            </form:form>

<%@ include file="../footer.jsp" %>