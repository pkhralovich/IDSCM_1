<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formulario de registro</title>
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
                    <h2 class="title">Formulario de registro</h2>
                    <form action="servletUsers" method="POST" onsubmit="return validateForm()">
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Nombre</label>
                                    <input id="name" class="input--style-4" type="text" name="name" value="${fn:escapeXml(param.name)}">
                                    <label class="label label-error" id="name-error">${name_error}</label>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Apellido</label>
                                    <input id="surname" class="input--style-4" type="text" name="surname" value="${fn:escapeXml(param.surname)}">
                                    <label class="label label-error" id="surname-error">${surname_error}</label>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Usuario</label>
                                    <input id="username" class="input--style-4" type="text" name="username" value="${fn:escapeXml(param.username)}">
                                    <label class="label label-error" id="username-error">${username_error}</label>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Correo electrónico</label>
                                    <input id="email" class="input--style-4" type="text" name="email" value="${fn:escapeXml(param.email)}">
                                    <label class="label label-error" id="email-error">${email_error}</label>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Contraseña</label>
                                    <input id="password" class="input--style-4" type="password" name="password" value="${fn:escapeXml(param.password)}">
                                    <label class="label label-error" id="password-error">${password_error}</label>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Confirmar contraseña</label>
                                    <input id="confirmation_password" class="input--style-4" type="password" name="confirmation_password" value="${fn:escapeXml(param.confirmation_password)}">
                                    <label class="label label-error" id="confirmation-password-error">${confirmation_password_error}</label>
                                </div>
                            </div>
                        </div>
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue m-b-15" type="submit">Submit</button>
                        </div>
                        <a class="p-t-15" href="./login.jsp"> ¿Ya registrado? Iniciar sesión </a>
                        <p class="p-t-15"> IDSCM - © 2020 Cristian Matas & Pavel Khralovich</p>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </body>
    
    <script>
    
    function validateForm() {
        try {
            var name = document.getElementById("name").value;
            var surname = document.getElementById("surname").value;
            var username = document.getElementById("username").value;
            var email = document.getElementById("email").value;
            var password = document.getElementById("password").value;
            var confirmation_password = document.getElementById("confirmation_password").value;

            var valid_form = true;

            /*if (!name) {
                valid_form = false;
                document.getElementById("name-error").innerHTML = "Valor obligatório";
            }

            alert(valid_form);*/
            return valid_form;
        } catch (e) {
            alert(e);
            return false;
        }
    }
    </script>
</html>