<%@ include file="../header.jsp" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>

<h2>${hotel.name}</h2>
<div class="row">
    <c:if test="${not empty loggedUser}">
        <div class="span1">
            <a class="btn
                <c:if test="${isFavourite}">
                    btn-danger
                </c:if>
                <c:if test="${!isFavourite}">
                    btn-success
                </c:if>
                " href="${pageContext.request.contextPath}/bin/user/toggleFavourite?hotelId=${hotel.id}">
                <i class="icon-heart icon-white"></i>
            </a>
        </div>
    </c:if>


    <c:if test="${admin}">
        <div class="span1">
            <c:if test="${hotel.active}">
                <a class="btn btn-danger" title="Dar de baja" href="${pageContext.request.contextPath}/bin/hotel/toggleActive?hotelId=${hotel.id}"><i class="icon-minus icon-white"></i></a>
            </c:if>
            <c:if test="${!hotel.active}">
                <a class="btn btn-success" title="Dar de alta" href="${pageContext.request.contextPath}/bin/hotel/toggleActive?hotelId=${hotel.id}"><i class="icon-plus icon-white"></i></a>
            </c:if>
        </div>
        <div class="span1">
            <form action="./edit" method="get">
              <input name="hotelId" id="hotelId" type="hidden" value="${hotel.id}">
              <button type="submit" class="btn btn-danger">Editar</button>
            </form>
        </div>
        <div class="span2">
            <form action="../hotel/addPic" method="get">
               <input name="hotelId" id="hotelId" type="hidden" value="${hotel.id}">
               <button type="submit" class="btn btn-inverse">Agregar una foto</button>
            </form>
        </div>
    </c:if>
    <c:if test="${not empty loggedUser}">
        <div class="span3">
            <form action="../comment/add" method="get">
               <input name="hotelId" id="hotelId" type="hidden" value="${hotel.id}">
               <button type="submit" class="btn btn-primary">Dejar un comentario</button>
            </form>
        </div>
    </c:if>
</div>

<div>
    <c:if test="${not empty mainPic}">
        <img class="profile" src="${pageContext.request.contextPath}/bin/hotel/picture?pictureId=${mainPic.id}">
    </c:if>
</div>

<div class="controls">
   <input type="hidden" id="hotelId" name="hotelId" value="${hotel.id}">
</div>

<table class="table table-striped">
    <thead>
        <tr>
            <th>Categoría</th>
            <th>Tipo</th>
            <th>Precio</th>
            <th>Ciudad</th>
            <th>Dirección</th>
            <th>Teléfono</th>
            <th>Sitio Web</th>
            <th>Desayuno</th>
            <th>Cantidad de consultas</th>
        </tr>
    </thead>
    <tbody>        
                <tr>
                    <td>${hotel.category} Estrellas</td>
                    <td>${hotel.type}</td>
                    <td>$${hotel.price}</td>
                    <td>${hotel.destination.destination}</td>
                    <td>${hotel.address}</td>
                    <td>${hotel.phone}</td>
                    <td><a href=http://${hotel.website} target="_blank"> ${hotel.website}</a></td>
                    <td>
                        <c:if test="${hotel.breakfast}">
                            Incluído
                        </c:if>
                        <c:if test="${!hotel.breakfast}">
                            No incluído
                        </c:if>
                    </td>
                    <td>${hotel.accessCounter}</td>
                </tr>
    </tbody>
</table>

<h3>Evaluación</h3>

<table class="table table-striped">
    <thead>
        <tr>
            <th>Higiene</th>
            <th>Instalaciones</th>
            <th>Servicios</th>
            <th>Ubicación</th>
            <th>Precio</th>
            <th>Confort para el descanso</th>
            <th>General</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>
                <c:if test="${evaluation.hygiene == 0}">-</c:if>
                <c:if test="${evaluation.hygiene != 0}">${evaluation.hygiene}</c:if>
            </td>
            <td>
                <c:if test="${evaluation.facilities == 0}">-</c:if>
                <c:if test="${evaluation.facilities != 0}">${evaluation.facilities}</c:if>
            </td>
            <td>
                <c:if test="${evaluation.service == 0}">-</c:if>
                <c:if test="${evaluation.service != 0}">${evaluation.service}</c:if>
            </td>
            <td>
                <c:if test="${evaluation.location == 0}">-</c:if>
                <c:if test="${evaluation.location != 0}">${evaluation.location}</c:if>
            </td>
            <td>
                <c:if test="${evaluation.price == 0}">-</c:if>
                <c:if test="${evaluation.price != 0}">${evaluation.price}</c:if>
            </td>
            <td>
                <c:if test="${evaluation.comfort == 0}">-</c:if>
                <c:if test="${evaluation.comfort != 0}">${evaluation.comfort}</c:if>
            </td>
            <td>
                <c:if test="${evaluation.general == 0}">-</c:if>
                <c:if test="${evaluation.general != 0}">${evaluation.general}</c:if>
            </td>
        </tr>
    </tbody>
</table>

<c:if test="${not empty mainPic}">
    <div id="myCarousel" class="carousel slide" style="width: 800px; height:500px; margin: 0 auto">
        <!-- Carousel items -->
        <div class="carousel-inner">
            <c:forEach begin="0" end="${fn:length(picturesCollection) - 1}" var="i">

                <div class="item
                    <c:if test="${i == 0}">
                        active
                    </c:if>
                ">

                    <img class="carouselImage" src="${pageContext.request.contextPath}/bin/hotel/picture?hotelId=${hotel.id}&pictureId=${picturesCollection[i].id}">
                    <a class="btn btn-link" href="${pageContext.request.contextPath}/bin/hotel/newMainPic?hotelId=${hotel.id}&pictureId=${picturesCollection[i].id}">Marcar como principal</a>
                    <a class="btn btn-link" href="${pageContext.request.contextPath}/bin/hotel/removePic?hotelId=${hotel.id}&pictureId=${picturesCollection[i].id}">Eliminar</a>
                </div>

            </c:forEach>
        </div>
        <!-- Carousel nav -->
        <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
        <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
    </div>
</c:if>
<br><br>

<c:if test="${hasComments}">

    <h3>Comentarios</h3>

    <table class="table table-striped">
        <thead>
            <tr>
                <c:if test="${not empty loggedUser}">
                    <th>Acción</th>
                </c:if>
                <th>Desde</th>
                <th>Hasta</th>
                <th>Fecha de comentario</th>
                <th>Razon de viaje</th>
                <th>Acompanantes</th>
                <th>Detalles</th>
                <th>Higiene</th>
                <th>Instalaciones</th>
                <th>Servicios</th>
                <th>Ubicación</th>
                <th>Precio</th>
                <th>Confort para el descanso</th>
                <th>Puntaje Gral.</th>
                <th>Nombre</th>

            </tr>
        </thead>
        <tbody>
            <c:forEach var="c" items="${commentsList}">
                    <tr>
                        <c:if test="${not empty loggedUser}">
                            <td>
                                <c:if test="${admin}">
                                    <a class="btn btn-danger"  title="Eliminar" href="${pageContext.request.contextPath}/bin/comment/remove?commentId=${c.id}">
                                        <i class="icon-trash icon-white"></i>
                                    </a>
                                </c:if>
                                <c:if test="${!c.forbidden && c.user.email != loggedUser}">
                                    <a class="btn btn-warning"  title="Marcar como inapropiado" href="${pageContext.request.contextPath}/bin/comment/toogleForbidden?commentId=${c.id}">
                                        <i class="icon-thumbs-down icon-white"></i>
                                    </a>
                                </c:if>
                                <c:if test="${c.forbidden && admin}">
                                    <a class="btn btn-success"  title="Marcar como correcto" href="${pageContext.request.contextPath}/bin/comment/toogleForbidden?commentId=${c.id}">
                                        <i class="icon-thumbs-up icon-white"></i>
                                    </a>
                                </c:if>
                            </td>
                        </c:if>
                        <td>${f:dateToString(c.fromDate)}</td>
                        <td>${f:dateToString(c.toDate)}</td>
                        <td>${f:dateToString(c.commentDate)}</td>
                        <td>
                            <c:if test="${empty c.reason}">-</c:if>
                            <c:if test="${not empty c.reason}">${c.reason}</c:if>
                        </td>
                        <td>
                            <c:if test="${empty c.companions}">-</c:if>
                            <c:if test="${not empty c.companions}">${c.companions}</c:if>
                        <td>
                        ${c.details}</td>
                        <td>
                            <c:if test="${c.rating.hygiene == 0}">-</c:if>
                            <c:if test="${c.rating.hygiene != 0}">${c.rating.hygiene}</c:if>
                        </td>
                        <td>
                            <c:if test="${c.rating.facilities == 0}">-</c:if>
                            <c:if test="${c.rating.facilities != 0}">${c.rating.facilities}</c:if>
                        </td>
                        <td>
                            <c:if test="${c.rating.service == 0}">-</c:if>
                            <c:if test="${c.rating.service != 0}">${c.rating.service}</c:if>
                        </td>
                        <td>
                            <c:if test="${c.rating.location == 0}">-</c:if>
                            <c:if test="${c.rating.location != 0}">${c.rating.location}</c:if>
                        </td>
                        <td>
                            <c:if test="${c.rating.price == 0}">-</c:if>
                            <c:if test="${c.rating.price != 0}">${c.rating.price}</c:if>
                        </td>
                        <td>
                            <c:if test="${c.rating.comfort == 0}">-</c:if>
                            <c:if test="${c.rating.comfort != 0}">${c.rating.comfort}</c:if>
                        </td>
                        <td>
                            <c:if test="${c.averagerating == 0}">-</c:if>
                            <c:if test="${c.averagerating != 0}">${c.averagerating}</c:if>
                        </td>
                        <td><span class="navbar-text pull-left">
                                <c:if test="${c.user.haspicture}">
                                    <img class="nav-bar navbar-text pull-left img-rounded" src="../user/profilePic?userEmail=${c.user.email}">
                                </c:if>
                                <c:if test="${!c.user.haspicture}">
                                    <img  class="nav-bar navbar-text pull-left img-rounded" src="../../img/default_user_picture.png">
                                </c:if>
                            <a href="../user/profile/${c.user.email}/">${c.user.email} </a></span></td>
                        <td><c:if test="${c.user.email == loggedUser}" >
                        	<form action="../comment/edit" method="get" class="navbar-form pull-left">
                        	    <input name="commentId" id="commentId" type="hidden" value="${c.id}">
                            	<button type="submit" class="btn btn-primary">Editar comentario</button>
                            </form>
                        </c:if>
                        </td>
                    </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<%@ include file="../footer.jsp" %>