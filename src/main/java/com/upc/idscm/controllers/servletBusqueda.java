/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upc.idscm.controllers;

import com.upc.idscm.tools.Pages;
import isdcm.webservice.video.search.VideoSearchWS;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import javax.xml.ws.WebServiceRef;


@WebServlet(name = "buscarImagen", urlPatterns = {"/buscarVideo"})
@MultipartConfig
public class servletBusqueda extends HttpServlet {
    private class PARAMS {
            public static final String TYPE = "type";
            public static final String VALUE = "value";
    }
    //TODO change the esdl route
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/WebApplicationUltraSophisticated.wsdl")
    private VideoSearchWS service;
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        //Validates the sesion
        HttpSession session = request.getSession(false);
        if (session.getAttribute("Username") != null
                && session.getAttribute("UserID") != null) {
            
            String type = request.getParameter(servletBusqueda.PARAMS.TYPE);
            String value = request.getParameter(servletBusqueda.PARAMS.VALUE);
             
            System.out.println(type + value);
            response.sendRedirect(Pages.SEARCH_VIDEO);
        } else response.sendRedirect(Pages.LOGIN);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /*private java.util.List<org.me.image.Image> searchByTitle(java.lang.String titol) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.me.image.ImageWS port = service.getImageWSPort();
        return port.searchByTitle(titol);
    }

    private java.util.List<org.me.image.Image> searchbyAuthor(java.lang.String autor) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.me.image.ImageWS port = service.getImageWSPort();
        return port.searchbyAuthor(autor);
    }

    private java.util.List<org.me.image.Image> searchbyCreaDate(java.lang.String datacreacio) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.me.image.ImageWS port = service.getImageWSPort();
        return port.searchbyCreaDate(datacreacio);
    }

    private Image searchbyId(int idImatge) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.me.image.ImageWS port = service.getImageWSPort();
        return port.searchbyId(idImatge);
    }

    private java.util.List<org.me.image.Image> searchbyKeywords(java.lang.String paraulaclau) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.me.image.ImageWS port = service.getImageWSPort();
        return port.searchbyKeywords(paraulaclau);
    }

    private java.util.List<org.me.image.Image> searchbyTitleandKeyword(java.lang.String titulo, java.lang.String paraulaclau) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.me.image.ImageWS port = service.getImageWSPort();
        return port.searchbyTitleandKeyword(titulo, paraulaclau);
    }
*/
    

}