<%@page import="com.upc.idscm.tools.Pages"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.upc.idscm.models.Video"%>
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
        <title>Listado de vídeos</title>
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
                    <h2 class="title">Listado de vídeos</h2>
                    <div>
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
                                    <!-- <th>Ruta</th> -->
                                </tr>
                            </thead>
                            
                            <tbody>
                                <%
                                    List<Video> videos = new ArrayList<Video>();
                                    if (request.getSession() != null && request.getSession().getAttribute("UserID") != null) {
                                        videos = Video.getUserVideos((Integer) (request.getSession().getAttribute("UserID")));
                                    }
                                    
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
                                            <div class="empty-list"> No se han dado de alta vídeos! </div> 
                                        </td> 
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                        
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue m-b-15" type="submit" form="logout">Cerrar sesión</button>
                            <button class="btn btn--radius-2 btn--blue m-b-15" onclick="location.href = '<%= request.getContextPath() %>/busqueda'">Filtrar Videos</button>
                            <button class="btn btn--radius-2 btn--blue m-b-15" onclick="location.href = '<%= request.getContextPath() %>/registroVid'">Añadir vídeo</button>
                            <button class="btn btn--radius-2 btn--blue m-b-15" onclick="location.href = '<%= request.getContextPath() %>/encryption'">Encriptación</button>
                            
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
    </script>
</html>
