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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="https://vjs.zencdn.net/7.7.5/video-js.css" rel="stylesheet" />
        <script src='http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min.js'></script>
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js'></script>
        <script src='http://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone-min.js'></script>
        <script src='reproduccion.js'></script>

        <!-- If you'd like to support IE8 (for Video.js versions prior to v7) -->
        <script src="https://vjs.zencdn.net/ie8/1.1.2/videojs-ie8.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reproduccion</title>
    </head>
    <body>
        <h1 align="center">Bienvenido <%= session.getAttribute("Username")%></h1>
        <div class="container">
            <div class="container">
                <span id="msg"></span></br>
                <b>Autor: </b> <span id="autor"></span>
                <br><b>Video: </b> <span id="name"></span>

            </div>
            <div class="container" id="video">
                <video
                    id="my-video"
                    class="video-js"
                    controls
                    preload="none"
                    width="640"
                    height="264"
                    data-setup="{}"
                    >
                    <source id="src" src="MY_VIDEO2.mp4" type="video/mp4" />
                    <p class="vjs-no-js">
                        To view this video please enable JavaScript, and consider upgrading to a
                        web browser that
                        <a href="https://videojs.com/html5-video-support/" target="_blank"
                           >supports HTML5 video</a
                        >
                    </p>
                </video>

            </div>
            <div class="container">
                <b>Reproducciones:</b> <span id="views"></span>
                <br><b>Duracion:</b><span id="time"></span>
            </div>

        </div>
                <form action="servletRegistroVid" method="POST" align="center">
                    <input type="submit" name="cancelarVideo" value="Volver" class="btn btn-secondary"><br><br>
                </form>
        <script src="https://vjs.zencdn.net/7.7.5/video.js"></script>
    </body>

</html>
