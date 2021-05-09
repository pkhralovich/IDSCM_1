<%@page import="com.upc.idscm.tools.Pages"%>
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
        <title>Encriptado</title>
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
                    <h2 class="title">Encriptación de xml y archivos</h2>
                    <form id="form" method="POST" enctype="multipart/form-data">
                        <div class="row row-space">
                            <div class="input-group">
                                <label class="label">XML a encriptar</label>
                                <input id="file" class="input--style-4" type="file" name="file">
                            </div>
                        </div>
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue m-b-15" formaction="<%= request.getContextPath() %>/encryptXML">Encriptar</button>
                            <button class="btn btn--radius-2 btn--blue m-b-15" formaction="<%= request.getContextPath() %>/decryptXML">Desencriptar</button>
                        </div>
                    </form>
                    <form id="form" method="POST" enctype="multipart/form-data">
                        <div class="row row-space">
                            <div class="input-group">
                                <label class="label">Fichero a encriptar</label>
                                <input id="file" class="input--style-4" type="file" name="file">
                            </div>
                        </div>
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue m-b-15" formaction="<%= request.getContextPath() %>/encryptDOC">Encriptar</button>
                            <button class="btn btn--radius-2 btn--blue m-b-15" formaction="<%= request.getContextPath() %>/decryptDOC">Desencriptar</button>
                        </div>
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue m-b-15" formaction="<%= request.getContextPath() %>/listadoVid.jsp">Listado de Videos</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
