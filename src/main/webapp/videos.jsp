<%@page import="java.util.List"%>
<%@page import="com.upc.idscm.database.VideoDB"%>
<%@page import="com.upc.idscm.models.Video"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                                </tr>
                            </thead>
                            
                            <tbody>
                                <%
                                    List<Video> listaVideos = VideoDB.instance().getUserVideos((int) request.getAttribute("UserID"));
                                    for (Video v : listaVideos) {%> 
                                <tr> 
                                    <td><%=v.getTitle()%></td> 
                                    <td><%=v.getAuthor()%></td> 
                                    <td><%=v.getCreationDate()%></td> 
                                    <td><%=v.getDuration()%></td> 
                                    <td><%=v.getPlays()%></td> 
                                    <td><%=v.getDescription()%></td> 
                                    <td><%=v.getFormat()%></td> 
                                </tr> 
                                <%}%>
                            </tbody>
                        </table>
                        
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue m-b-15" type="submit">Cerrar sesión</button>
                            <button class="btn btn--radius-2 btn--blue m-b-15" type="submit">Añadir vídeo</button>
                        </div>
                        
                        <p class="p-t-15"> IDSCM - © 2020 Cristian Matas & Pavel Khralovich</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>
