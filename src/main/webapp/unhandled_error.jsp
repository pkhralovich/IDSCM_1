<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error inesperado</title>
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
                    <h2 class="title">Error inesperado</h2>
                    <div>
                        <p>Se ha producido un error en la página en la que se encontraba. Pruebe a intentar el proceso de nuevo. </p>
                        
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue m-b-15" onclick="location.href = './login.jsp'">Ir a la página principal</button>
                        </div>
                        <p class="p-t-15"> IDSCM - © 2021 Cristian Matas & Pavel Khralovich</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>