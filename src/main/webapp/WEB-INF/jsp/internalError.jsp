<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
	    <title>Hotel App</title>
	    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
    </head>
	<body>
        <div class="row">
            <div class="container">
                <div class="alert alert-error">
                    <strong>ERROR!</strong> Ha ocurrido un error interno. Se ha enviado un email a los desarrolladores informando el origen del problema.
                </div>
            </div>
        </div>
        <div>
            <img style="display: block; margin-left: auto; margin-right: auto;" src="${pageContext.request.contextPath}/img/homer-error.gif" />
        </div>
    </body>
</html>