package com.upc.idscm.controllers;

import com.upc.idscm.tools.Pages;
import com.upc.isdcm_soap.Video;
import com.upc.isdcm_soap.VideoSearchWS;
import com.upc.isdcm_soap.VideoSearchWS_Service;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.ws.WebServiceRef;


@WebServlet(name = "servletBusqueda", urlPatterns = {"/busqueda"})
public class servletBusqueda extends HttpServlet {
    private class PARAMS {
            public static final String TYPE = "type";
            public static final String VALUE = "value";
    }
    
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ISDCM_SOAP/VideoSearchWS.wsdl")
    private VideoSearchWS_Service service;
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(false);
        if (session.getAttribute("Username") != null
                && session.getAttribute("UserID") != null) {
            
            getVideos(request);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Pages.SEARCH_VIDEO);
            dispatcher.include(request, response);
        } else response.sendRedirect(Pages.LOGIN);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private List<Video> searchByTitle(java.lang.String title, int user_id) {
        VideoSearchWS port = service.getVideoSearchWSPort();
        return port.busquedaPorTitulo(title, user_id);
    }
    
    private List<Video> searchByAuthor(java.lang.String autor, int user_id) {
        VideoSearchWS port = service.getVideoSearchWSPort();
        return port.busquedaPorAutor(autor, user_id);
    }
    
    private List<Video> searchByDate(String dia, String mes, String año, int user_id) {
        VideoSearchWS port = service.getVideoSearchWSPort();
        return port.busquedaPorFecha(dia, mes, año, user_id);
    }
    
    private List<Video> getAllVideos(int user_id) {
        VideoSearchWS port = service.getVideoSearchWSPort();
        return port.busquedaTodos(user_id);
    }
    
    public void getVideos(HttpServletRequest request) {
        String searchType = request.getParameter("type");
        Integer user_id = (Integer) request.getSession(false).getAttribute("UserID");
        List <Video> listVideo = null;
        
        if (searchType == null) {
            listVideo = getAllVideos(user_id);
        } else {
            switch (searchType) {
                case "1":
                    listVideo = searchByTitle(request.getParameter("value"), user_id);
                    break;
                case "2":
                    listVideo = searchByAuthor(request.getParameter("value"), user_id);
                    break;
                case "3":
                    listVideo = searchByDate(request.getParameter("day"), 
                                              request.getParameter("month"),
                                              request.getParameter("year"),
                                              user_id); 
                    break;
            }
        }
        request.setAttribute("listVideo", listVideo);
    }
}