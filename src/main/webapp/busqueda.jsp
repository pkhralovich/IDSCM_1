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
                            <div class="input-group" id = "searchSelect">
                                <input type="search" class="form-control rounded" placeholder="Filtrar" name="value" />
                                <button type="submit" class="search-button btn btn-outline-primary">Filtrar</button>
                            </div>
<!--                                <div class="container">
                                    <div class="row">
                                      <div id="search" role="form" style="border:0">
                                        <div class="col-md-3">
                                          <ul class="nav nav-stacked">
                                            <li><strong>Dia</strong></li>
                                            <li>
                                              <input type="text" class="form-control" value="" id="dateFrom" />
                                            </li>
                                          </ul>
                                        </div>

                                        <div class="col-md-3">
                                          <ul class="nav nav-stacked">
                                            <li><strong>Mes</strong></li>
                                            <li>
                                              <input type="text" class="form-control" value="" id="dateTo" />
                                            </li>
                                          </ul>
                                        </div>
                                          
                                        <div class="col-md-3">
                                          <ul class="nav nav-stacked">
                                            <li><strong>Año</strong></li>
                                            <li>
                                              <input type="text" class="form-control" value="2" id="dateFrom" />
                                            </li>
                                          </ul>
                                        </div>

                                        <div class="col-md-3">
                                          <ul class="nav nav-stacked">
                                            <li>&nbsp;</li>
                                            <li>
                                              <button class="btn btn-primary" type="button" id="getJsonSrc">Search</button>
                                            </li>
                                          </ul>
                                        </div>
                                      </div>
                                    </div>
                                  </div>-->
                            <div class="row" id = "dates" hidden>
                               
                                <div class="form-group row col-sm">
                                    <input class="form-control rounded col-sm-6" type="number" min="1" max="31" name="day" id="day">
                                    <label class="col-form-label col-sm-6" for="day">
                                      Dia
                                    </label>
                                  </div>
                                 <div class="form-group row col-sm">
                                    <input class="form-control rounded col-sm-6" type="number" min="1" max="12" name="month" id="month" >
                                    <label class="col-form-label col-sm-6" for="month">
                                      Mes
                                    </label>
                                  </div>
                                 <div class="form-group row col-sm">
                                    <input class="form-control rounded col-sm-6" type="number" min="500" max="3000" name="year" id="year" >
                                    <label class="col-sm-2 col-form-label col-sm-6" for="year">
                                      Año
                                    </label>
                                  </div>
                                <button type="submit" class="search-button btn btn-outline-primary col-sm">Filtrar</button>
                            </div>
                            <div class="row">

                                <div class="form-check col-sm">
                                    <input class="form-check-input" type="radio" name="type" id="title" value="1" checked>
                                    <label class="form-check-label" for="title">
                                      Búsqueda por Título
                                    </label>
                                  </div>
                                <div class="form-check col-sm">
                                    <input class="form-check-input" type="radio" name="type" id="author" value="2">
                                    <label class="form-check-label" for="author">
                                      Búsqueda por Autor
                                    </label>
                                  </div>
                                <div class="form-check col-sm">
                                    <input class="form-check-input" type="radio" name="type" id="date" value="3">
                                    <label class="form-check-label" for="date">
                                      Búsqueda por Fecha de Creación
                                    </label>
                                  </div>
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
                                    ArrayList<Video> videos
                                          = (ArrayList<Video>) request.getAttribute("listVideo");
//                                    for (Video s : listaVideos)
//                                    List<Video> videos = getVideos(request);
                                    if (videos != null && videos.size() > 0) {
                                        for (Video v : videos) {                                
                                %> 
                                    <tr> 
                                        <td><%=v.getTitle()%></td> 
                                        <td><%=v.getAuthor()%></td> 
                                        <td><%=v.getCreationDate()%></td> 
                                        <td><%=v.getDuration()%></td> 
                                        <td><%=0%></td> 
                                        <td><%=v.getDescription()%></td> 
                                        <td><%=v.getFormat()%></td> 
                                    </tr> 
                                <% }} else { %>
                                <tr> <td colspan="7"> <div class="empty-list"> No se han dado de alta vídeos! </div> </td> </tr>
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
    var rad = document.searchForm.type;
    var dates = document.getElementById("dates");
    var search = document.getElementById("searchSelect");
    var prev = null;
    function OnChangeRadio() {
            if (this !== prev) {
                prev = this;
                console.log(this.value);
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
    
    </script>
</html>
