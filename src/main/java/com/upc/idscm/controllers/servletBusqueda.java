package com.upc.idscm.controllers;

import com.upc.idscm.tools.Pages;
import com.upc.isdcm_soap.Video;
import com.upc.isdcm_soap.VideoSearchWS;
import com.upc.isdcm_soap.VideoSearchWS_Service;
import java.io.IOException;
import java.util.List;
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
    
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/IDSCM.wsdl")
    private VideoSearchWS_Service service;
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
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
    }

    private List<Video> searchByTitle(java.lang.String titol) {
        VideoSearchWS port = service.getVideoSearchWSPort();
        return port.busquedaPorTitulo(titol, 0);
    }
}