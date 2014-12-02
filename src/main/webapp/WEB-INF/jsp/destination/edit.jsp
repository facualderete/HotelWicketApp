<%@ include file="../header.jsp" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>

<h2>Editar destino ${destination.destination}</h2>

            <form:form action="./editDestination" method="post" class="form-horizontal" commandName="destinationForm" enctype="multipart/form-data">

              <div class="control-group">
                <label class="control-label" for="details">Detalles</label>
                <div class="controls">
                    <form:textarea rows="3" path="details"></form:textarea>
                    <form:errors class="alert alert-error help-inline" path="details" />
                </div>
              </div>
              
              <div class="control-group">
                <label class="control-label" for="picture">Foto del destino</label>
                <div class="controls">
                    <form:input type="file" path="picture" />
                </div>
              </div>

              <div class="control-group">
                <label class="control-label" for="noPhoto">Sin foto</label>
                <div class="controls">
                <form:checkbox path="noPhoto"></form:checkbox>
                </div>
              </div>
              
              <div class="control-group">
                <div class="controls">
                  <input name="destinationId" id="destinationId" type="hidden" value="${destination.id}">
                  <button type="submit" class="btn btn-primary">Terminar edición</button>
                </div>
              </div>
            </form:form>

<%@ include file="../footer.jsp" %>