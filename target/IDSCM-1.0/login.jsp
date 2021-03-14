<%@page import="com.upc.idscm.tools.Pages"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    session=request.getSession(false);
    if(session.getAttribute("UserID") != null && session.getAttribute("Username") != null) {
        response.sendRedirect(Pages.VIDEOS);
    }
%> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio de sesión</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
        <link href="./css/main.css" rel="stylesheet">
    
        <link rel="icon" type="image/png" href="./images/favicon.png">
        
        <!-- Font special for pages-->
        <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
    </head>
    <body>
        <div class="page-wrapper bg-gra-02 p-t-130 p-b-100 font-poppins">
        <div class="wrapper wrapper--w680">
            <div class="card card-4">
                <div class="card-body">
                    <h2 class="title">Inicio de sesión</h2>
                    <form action="servletUsers?action=login" method="POST" onsubmit="return validateForm()">
                        <div class="row row-space">
                            <div class="input-group">
                                <label class="label">Nombre de usuario</label>
                                <input id="username" class="input--style-4" type="text" name="username" value="${fn:escapeXml(param.username)}">
                                <label class="label label-error" id="username-error">${username_error}</label>
                            </div>
                        </div>
                        
                        <div class="row row-space">
                            <div class="input-group">
                                <label class="label">Contraseña</label>
                                <input id="password" class="input--style-4" type="password" name="password">
                                <label class="label label-error" id="password-error">${password_error}</label>
                            </div>
                        </div>
                        
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue m-b-15" type="submit">Iniciar sesión</button>
                        </div>
                        
                        <a class="p-t-15" href="./registroUsu.jsp"> ¿Aún no tienes cuenta? Registrar </a>
                        <p class="p-t-15"> IDSCM - © 2021 Cristian Matas & Pavel Khralovich</p>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </body>
    
    <script>
        function validateForm() {
        try {
            var username = document.getElementById("username").value;
            var password = document.getElementById("password").value;
      
            let oRes = isNotEmpty(username, "username-error");
            oRes = isNotEmpty(password, "password-error") && oRes;
            return oRes;
        } catch (e) {
            alert(e);
            return false;
        }
    }
    
    function isNotEmpty(value, error_id) {
        if (!value) {
            document.getElementById(error_id).innerHTML = "Valor obligatório";
            return false;
        }
        
        return true;
    }
    </script>
</html>