<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.upc.idscm.tools.Pages"%>

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
        <title>Reproduccion</title>
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
        <link href="./css/main.css" rel="stylesheet">
        <link href="./css/reproduccion.css" rel="stylesheet">
        
        <script src='./javascript/reproduccion.js'></script>

        <link rel="stylesheet" href="https://cdn.plyr.io/3.6.7/plyr.css" />
        <script src="https://cdn.plyr.io/3.6.7/plyr.js"></script>
        
        <link rel="icon" type="image/png" href="./images/favicon.png">
        <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        
        <script>
            onDocumentReady(<%=request.getSession(false).getAttribute("UserID")%>);
        </script>
    </head>
    <body>
        <div class="page-wrapper bg-gra-02 font-poppins">
            <div class="wrapper wrapper--w680 wrapper-grid">
                <div class="player card card-4">
                    <div id="content">
                        <h2 class="title">Reproducción de vídeo</h2>
                        <div id="player" class="js-player"></div>
                        <div class="video-info">
                            <p class="video-label">Reproducciones: </p> <p id="views"></p>
                            <p class="video-label">Duracion:       </p> <p id="time" ></p>
                        </div>
                        <button class="btn btn--radius-2 btn--blue m-b-15" onclick="location.href = '<%= request.getContextPath() %>/videos'">Ver listado</button>
                    </div>
                    <div id="error" hidden>
                        <h2 class="title">Vídeo no encontrado</h2>
                        <div>
                            <p id="error-message" class="error">El vídeo al que has intentado acceder ya no se encuentra disponible. Haz click aquí para volver a la pantalla principal.</p>
                            <div class="p-t-15">
                                <button class="btn btn--radius-2 btn--blue m-b-15" onclick="location.href = '<%= request.getContextPath() %>/videos'">Ir al listado de vídeos</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
