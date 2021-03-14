<%@page import="com.upc.idscm.tools.Pages"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    session=request.getSession(false);
    if(session.getAttribute("UserID")==null || session.getAttribute("Username")==null) {
        response.sendRedirect(Pages.LOGIN);
    }
%> 

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
        <div class="wrapper wrapper--w680 wrapper-video">
            <div class="card card-4">
                <div class="card-body">
                    <h2 class="title">Nuevo vídeo</h2>
                    <form id="form" action="servletRegistroVid" method="POST" onsubmit="return validateForm()">
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Título</label>
                                    <input id="title" class="input--style-4" type="text" name="title" value="${fn:escapeXml(param.title)}">
                                    <label class="label label-error" id="title-error">${title_error}</label>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Autor</label>
                                    <input id="author" class="input--style-4" type="text" name="author" value="${fn:escapeXml(param.author)}">
                                    <label class="label label-error" id="author-error">${author_error}</label>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row row-space">
                            <div class="col-3">
                                <div class="input-group">
                                    <label class="label">Fecha creación</label>
                                    <input id="creation-date" class="input--style-4" type="date" name="creation_date" value="${fn:escapeXml(param.creation_date)}">
                                    <label class="label label-error" id="creation-date-error">${creation_date}</label>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="input-group">
                                    <label class="label">Duración</label>
                                    <input id="duration" class="input--style-4" type="time" step="1" name="duration" value="${fn:escapeXml(param.duration)}">
                                    <label class="label label-error" id="duration-error">${duration_error}</label>
                                </div>
                            </div>
                            <div class="col-3">
                                <div class="input-group">
                                    <label class="label">Reproducciones</label>
                                    <input id="plays" class="input--style-4" type="number" name="plays" value="${fn:escapeXml(param.plays)}">
                                    <label class="label label-error" id="plays-error">${plays_error}</label>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Descripción</label>
                                    <input id="description" class="input--style-4" type="text" name="description" value="${fn:escapeXml(param.description)}">
                                    <label class="label label-error" id="description-error">${description_error}</label>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Formato</label>
                                    <input id="format" class="input--style-4" type="text" name="format" value="${fn:escapeXml(param.format)}">
                                    <label class="label label-error" id="format-error">${format_error}</label>
                                </div>
                            </div>
                        </div>
                        
                    </form>
                    <div class="p-t-15">
                        <button class="btn btn--radius-2 btn--blue m-b-15" onclick="location.href = './listadoVid.jsp'">Cancelar</button>
                        <button class="btn btn--radius-2 btn--blue m-b-15" type="submit" form="form">Registrar</button>
                    </div>
                    
                    
                    <p class="p-t-15"> IDSCM - © 2021 Cristian Matas & Pavel Khralovich</p>
                </div>
            </div>
        </div>
    </div>
    </body>
    
    <script>
    
    function validateForm() {
        try {
            var author = document.getElementById("author").value;
            var title = document.getElementById("title").value;

            var valid_form = isNotEmpty(author, "author-error");
            valid_form = isNotEmpty(title, "title-error") && valid_form;

            return valid_form;
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