<%-- 
    Document   : newjsp
    Created on : 03-mar-2021, 14:46:40
    Author     : pavel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    </head>
    <body>
        <form method="post" action="#">
            <!-- NOMBRE -->
            <div class="form-group">
                <label for="input-name" for="exampleInputEmail1">Nombre</label>
                <input id="input-name" class="form-control" type="text"/> 
            </div>
            <!-- APELLIDO -->
            <div class="form-group">
                <label for="input-surname" for="exampleInputEmail1">Apellido</label>
                <input id="input-surname" class="form-control" type="text"/> 
            </div
            <!-- EMAIL -->
            <input type="text"/> 
            <!-- USERNAME -->
            <input type="text"/> 
            <!-- PASSWORD -->
            <input type="password"/> 
            <!-- REPEAT PASSWORD -->
            <input type="password"/> 
            <input type="submit"/>
        </form>
    </body>
</html>