
<%@page import="com.upc.isdcm_soap.Video"%>
<%@page import="com.upc.idscm.tools.Pages"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
        <title>Filtrar Videos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
        <link href="./css/main.css" rel="stylesheet">
    
        <link rel="icon" type="image/png" href="./images/favicon.png">
        
        <!-- Font special for pages-->
        <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
    </head>
    <body>
        <div class="page-wrapper bg-gra-02 p-t-130 p-b-100 font-poppins">
        <div class="wrapper wrapper--w680 wrapper-grid">
            <div class="card card-4">
                <div class="card-body">
                    <h2 class="title">Filtrar Videos</h2>
                    <div>
                        <form action="<%= request.getContextPath() %>/busqueda" method="GET" name="searchForm">
                            <fieldset class="form-group border p-3 input-group">
                                <div class="row">
                                    <label class="form-check-label col-sm" style="">
                                          Tipo de búsqueda:
                                    </label>
                                    <div class="form-group col-sm">
                                        <input class="form-check-input" type="radio" name="type" id="title" value="1"/>
                                        <label class="form-check-label" for="title">
                                          Búsqueda por Título
                                        </label>
                                    </div>
                                    <div class="form-group col-sm">
                                        <input class="form-check-input" type="radio" name="type" id="author" value="2"/>
                                        <label class="form-check-label" for="author">
                                          Búsqueda por Autor
                                        </label>
                                      </div>
                                    <div class="form-group col-sm">
                                        <input class="form-check-input" type="radio" name="type" id="date" value="3"/>
                                        <label class="form-check-label" for="date">
                                          Búsqueda por Fecha de Creación
                                        </label>
                                      </div>
                                </div>
                            </fieldset>
                            
                            <div class="input-group" id="searchSelect">
                                <input type="search" class="form-control rounded input--style-4" placeholder="Filtrar" name="value" value="${fn:escapeXml(param.value)}"/>
                                <button type="submit" class="search-button btn btn-outline-primary">Filtrar</button>
                            </div>
                            <div class="input-group" id="dates" hidden>
                                <div class="form-group row col-sm">
                                    <label class="col-sm-4 col-form-label" for="day">Dia</label>
                                    <div class="col-sm-6">
                                        <input class="input--style-4" type="number" min="1" max="31" name="day" id="day" value="${fn:escapeXml(param.day)}"/>
                                    </div>
                                </div>
                                <div class="form-group row col-sm">
                                    <label class="col-sm-4 col-form-label" for="month">Mes</label>
                                    <div class="col-sm-6">
                                        <input class="input--style-4" type="number" min="1" max="12" name="month" id="month" value="${fn:escapeXml(param.month)}"/>
                                    </div>
                                </div>
                                <div class="form-group row col-sm">
                                    <label class="col-sm-4 col-form-label" for="year">Año</label>
                                    <div class="col-sm-6">
                                        <input class="input--style-4" type="number" min="500" max="3000" name="year" id="year" value="${fn:escapeXml(param.year)}"/>
                                    </div>
                                </div>
                                <button type="submit" class="search-button btn btn-outline-primary col-sm-2">Filtrar</button>
                            </div>
                        </form>
                        <table align="center" class="table table-bordered" >
                            <thead class="thead-dark">
                                <tr>
                                    <th>Titulo</th>
                                    <th>Autor</th>
                                    <th>Fecha Creacion</th>
                                    <th>Duracion</th>
                                    <th>Reproducciones</th>
                                    <th>Descripcion</th>
                                    <th>Formato</th>
                                </tr>
                            </thead>
                            
                            <tbody>
                                <%
                                    ArrayList<Video> videos = (ArrayList<Video>) request.getAttribute("listVideo");
                                    if (videos != null && videos.size() > 0) {
                                        for (Video v : videos) {                                
                                %> 
                                    <tr onclick="onClickRow(<%=v.getId()%>)"> 
                                        <td><%=v.getTitle()%></td> 
                                        <td><%=v.getAuthor()%></td> 
                                        <td><%=v.getCreationDate()%></td> 
                                        <td><%=v.getDuration()%></td> 
                                        <td><%=v.getPlays()%></td> 
                                        <td><%=v.getDescription()%></td> 
                                        <td><%=v.getFormat()%></td>
                                    </tr> 
                                <% }} else { %>
                                    <tr class="empty-message-row">
                                        <td colspan="7">
                                            <div class="empty-list"> No se han encontrado vídeos con estos parámetros de búsqueda! </div>
                                        </td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                        
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue m-b-15" type="submit" form="logout">Cerrar sesión</button>
                            <button class="btn btn--radius-2 btn--blue m-b-15" onclick="location.href = '<%= request.getContextPath() %>/videos'">Ver Todos</button>
                            
                            <form id="logout" action="<%= request.getContextPath() %>/logout" method="POST"/>
                        </div>
                        
                        <p class="p-t-15"> IDSCM - © 2021 Cristian Matas & Pavel Khralovich</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
    <script>
    function onClickRow(video_id) {
        window.location.href = "<%= request.getContextPath() %>/reproduccion.jsp?video=" + video_id;
    }
        
    document.searchForm.addEventListener('submit', function () {
        var allInputs = document.searchForm.getElementsByTagName('input');

        for (var i = 0; i < allInputs.length; i++) {
            var input = allInputs[i];

            if ((input.name && !input.value) || dontSend(input)) {
                input.name = '';
            }
        }
    });
    
    function dontSend(input) {
        if (current_radio && input.name !== "type") {
            if (current_radio.value === "1" || current_radio.value === "2") {
                return input.name === "year" || input.name === "month" || input.name === "day";
            } else {
                return input.name === "value";
            }
        }
    }
    
    var rad = document.searchForm.type;
    var dates = document.getElementById("dates");
    var search = document.getElementById("searchSelect");
    var current_radio = null;
    
    function OnChangeRadio() {
        if (this !== current_radio) {
            current_radio = this;
        }
        if (this.value === "1" || this.value === "2"){
            dates.hidden = true;
            search.hidden = false;
        } else {
            dates.hidden = false;
            search.hidden = true;
        }
    };
        
    for (var i = 0; i < rad.length; i++) {
        rad[i].addEventListener('change', OnChangeRadio);
    }    
    
    let url = new URL(window.location.href);
    let type = url.searchParams.get('type');
    if (type) {
        switch (type) {
            case "1": {
                document.getElementById("title").checked = true;
                break;
            }
            case "2": {
                document.getElementById("author").checked = true;
                break;
            }
            case "3": {
                document.getElementById("date").checked = true;
                dates.hidden = false;
                search.hidden = true;
                break;
            }
            default: {
                document.getElementById("title").checked = true;
                break; 
            }
        }
    } else {
        document.getElementById("title").checked = true;
    }
    </script>
</html>
