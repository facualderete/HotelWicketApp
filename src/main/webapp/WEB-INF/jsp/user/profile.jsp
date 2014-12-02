<%@ include file="../header.jsp" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>

<center>
<div>
	<c:if test="${askedUser.haspicture}">
	     <img class="profile" src="${pageContext.request.contextPath}/bin/user/profilePic?userEmail=${askedUser.email}">
    </c:if>
    <c:if test="${!askedUser.haspicture}">
         <img class="profile" align="top" src="${pageContext.request.contextPath}/img/default_user_picture.png">
    </c:if>
</div>
</center>

<c:if test="${admin && !askedUser.admin}">
    <form action="${pageContext.request.contextPath}/bin/user/grantAdmin" method="get">
       <input name="userEmail" id="userEmail" type="hidden" value="${askedUser.email}">
       <button type="submit" class="btn btn-warning">Establecer como administrador</button>
    </form>
</c:if>
<c:if test="${admin && askedUser.admin}">
    <div class="alert alert-success span3">
        Usuario administrador
    </div>
</c:if>

<table class="table table-striped">
    <thead>
        <tr>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Descripcion</th>
            <th>Email</th>
      
        </tr>
    </thead>
    <tbody>        
                <tr>
                    <td>${askedUser.name}</td>
                    <td>${askedUser.lastname}</td>
                    <td>${askedUser.description}</td>
                    <td>${askedUser.email}</td>
               </tr>
    </tbody>
</table>
<c:if test="${askedUser.email==loggedUser}">
	<form action="${pageContext.request.contextPath}/bin/user/edit" method="get" class="navbar-form pull-left">
		<input name="userEmail" id="userEmail" type="hidden" value="${askedUser.email}">
   		<button type="submit" class="btn btn-danger">Editar perfil</button>
	</form>
</c:if>
<br><br>

<c:if test="${fn:length(askedUser.favourites) > 0}">
    <h3>Hoteles favoritos</h3>
    <c:forEach items="${askedUser.favourites}" var="favHotel">
        <c:if test="${favHotel.active || admin}">
            <p><a href="${pageContext.request.contextPath}/bin/hotel/view?id=${favHotel.id}">${favHotel.name}</a></p>
        </c:if>
        <c:if test="${!favHotel.active && !admin}">
            ${favHotel.name}
        </c:if>
    </c:forEach>
</c:if>

<h3>Comentarios</h3>
<c:forEach items="${hotels}" var="entry">
    <c:if test="${entry.key.active || admin}">
	    <p><a href="${pageContext.request.contextPath}/bin/hotel/view?id=${entry.key.id}">${entry.key.name}</a></p>
	</c:if>
	<c:if test="${!entry.key.active && !admin}">
	    ${entry.key.name}
	</c:if>
	<table class="table table-striped">
    	<thead>
       		<tr>
            	<th>Fecha</th>
            	<th>Detalle</th>
            	<th>Puntaje Gral.</th>
       		</tr>
    	</thead>
    	<tbody>
			<c:forEach items="${entry.value}" var="comment">
				 <tr>
                    	<td>${f:dateToString(comment.commentDate)}</td>
                    	<td>${comment.details}</td>
                    	<td>${comment.averagerating}</td>
                    	<c:if test="${askedUser.email == loggedUser && (entry.key.active || admin)}" >
							<td>
                                <form action="${pageContext.request.contextPath}/bin/comment/edit" method="get" class="navbar-form pull-left">
                                    <input name="commentId" id="commentId" type="hidden" value="${comment.id}">
                                    <button type="submit" class="btn btn-primary">Editar comentario</button>
                                </form>
							</td>
					</c:if>
        		</tr>
			</c:forEach>
		</tbody>
	</table>
</c:forEach>
<%@ include file="../footer.jsp" %>