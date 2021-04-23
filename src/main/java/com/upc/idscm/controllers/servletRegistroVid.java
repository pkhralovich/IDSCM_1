package com.upc.idscm.controllers;

import com.upc.idscm.models.Video;
import com.upc.idscm.tools.Pages;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "servletRegistroVid", urlPatterns = {"/videos", "/registroVid"})
public class servletRegistroVid extends HttpServlet {
    private class PARAMS {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String CREATION_DATE = "creation_date";
        public static final String DURATION = "duration";
        public static final String PLAYS = "plays";
        public static final String DESCRIPTION = "description";
        public static final String FORMAT = "format";
        public static final String PATH = "path";
    }
    
    private class ERRORS {
        public static final String TITLE = "title_error";
        public static final String AUTHOR = "author_error";
        public static final String CREATION_DATE = "creation_date_error";
        public static final String DURATION = "duration_error";
        public static final String PLAYS = "plays_error";
        public static final String DESCRIPTION = "description_error";
        public static final String FORMAT = "format_error";
        public static final String PATH = "path_error";
    }
    
    private class ACTIONS {
        private static final String VIDEOS = "/videos";
        private static final String NEW_VIDEO = "/registroVid";
    }
    
    private void initialize_response(HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                                            throws ServletException, IOException {
        
        initialize_response(response);
        
        HttpSession session = request.getSession(false);
        switch (request.getServletPath()) {
            case ACTIONS.VIDEOS: {
                if (session.getAttribute("Username") == null
                        || session.getAttribute("UserID") == null) {
                    request.getSession().invalidate();
                    response.sendRedirect(Pages.LOGIN);
                }
                else {
                    request.getRequestDispatcher(Pages.VIDEOS).forward(request, response);
                }
                break;
            }
            case ACTIONS.NEW_VIDEO: {
                if (session.getAttribute("Username") == null
                        || session.getAttribute("UserID") == null) {
                    request.getSession().invalidate();
                    response.sendRedirect(Pages.LOGIN);
                }
                else {
                    request.getRequestDispatcher(Pages.NEW_VIDEO).forward(request, response);
                }
                break;
            }
            default : {
                response.sendRedirect(Pages.NOT_FOUND);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        initialize_response(response);
        
        HttpSession session = request.getSession(false);
        if (session.getAttribute("Username") == null
                || session.getAttribute("UserID") == null) {
            request.getSession().invalidate();
            response.sendRedirect(Pages.LOGIN);
        } else {
            String title = request.getParameter(PARAMS.TITLE);
            String author = request.getParameter(PARAMS.AUTHOR);
            String string_creation_date = request.getParameter(PARAMS.CREATION_DATE);
            String duration = request.getParameter(PARAMS.DURATION);
            String string_plays = request.getParameter(PARAMS.PLAYS);
            String description = request.getParameter(PARAMS.DESCRIPTION);
            String format = request.getParameter(PARAMS.FORMAT);
            String path = request.getParameter(PARAMS.PATH);
            
            long plays = string_plays != null && !string_plays.isEmpty() ? Long.parseLong(string_plays) : 0;
            int user_id = (Integer) session.getAttribute("UserID");
            
            Date creation_date = null;
            try {
                creation_date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(string_creation_date).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(servletRegistroVid.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Video video = new Video(-1, title, author, creation_date, duration,
                                    plays, description, user_id, format, path);
            if (!isValidVideo(video, request)) {
                request.getRequestDispatcher(Pages.NEW_VIDEO).forward(request, response);
            } else {
                try {
                    Video.insert(video);
                    response.sendRedirect(Pages.VIDEOS);
                } catch (SQLException e) {
                    PrintWriter out = response.getWriter();
                    out.println("<label class=\"danger-text\"> ¡Atención! Se ha producido un error guardando el vídeo. Vuelva a intentarlo en unos instantes</label>");
                    request.getRequestDispatcher(Pages.VIDEOS).include(request, response);
                }
            }
        }
    }

    private boolean isValidVideo(Video video, HttpServletRequest request) { 
        boolean isValid = validateTitle(video.getTitle(), video.getUser(), request);
        
        isValid &= validateAuthor(video.getAuthor(), request);
        isValid &= validateDuration(video.getDuration(), request);
        isValid &= validatePlays(video.getPlays(), request);
        isValid &= validateDescription(video.getDescription(), request);
        isValid &= validateFormat(video.getFormat(), request);
        isValid &= validatePath(video.getPath(), request);
        
        return isValid;
    }
    
    private boolean validateTitle(String title, int user, HttpServletRequest request) {
        try {
            if (title == null || title.isEmpty()) {
                request.setAttribute(ERRORS.TITLE, "Valor obligatório");
                return false;
            } else if (title.length() > Video.MAX_LENGTH.TITLE) {
                request.setAttribute(ERRORS.TITLE, "Longitud máxima superada");
                return false;
            } else if (Video.titleInUse(title, user)) {
                request.setAttribute(ERRORS.TITLE, "Título en uso");
                return false;
            }

            return true;
        } catch (SQLException e) {
            request.setAttribute(ERRORS.TITLE, "Error inesperado");
            return false;
        }
    }
    
    private boolean validateAuthor(String author, HttpServletRequest request) {
        if (author == null || author.isEmpty()) {
            request.setAttribute(ERRORS.AUTHOR, "Valor obligatório");
            return false;
        } else if (author.length() > Video.MAX_LENGTH.AUTHOR) {
            request.setAttribute(ERRORS.AUTHOR, "Longitud máxima superada");
            return false;
        } 
        
        return true;
    }
    
    private boolean validateDuration(String duration, HttpServletRequest request) {
        if (duration != null && duration.length() > Video.MAX_LENGTH.DURATION) {
            request.setAttribute(ERRORS.DURATION, "Longitud máxima superada");
            return false;
        }
        
        return true;
    }
    
    private boolean validatePlays(long plays, HttpServletRequest request) {
        if (plays > Video.MAX_LENGTH.PLAYS) {
            request.setAttribute(ERRORS.PLAYS, "Valor máximo superado");
            return false;
        }
        
        return true;
    }
    
    private boolean validateDescription(String description, HttpServletRequest request) {
        if (description != null && description.length() > Video.MAX_LENGTH.DESCRIPTION) {
            request.setAttribute(ERRORS.DESCRIPTION, "Longitud máxima superada");
            return false;
        }
        
        return true;
    }
    
    private boolean validateFormat(String format, HttpServletRequest request) {
        if (format != null && format.length() > Video.MAX_LENGTH.FORMAT) {
            request.setAttribute(ERRORS.FORMAT, "Longitud máxima superada");
            return false;
        }
        
        return true;
    }
    
    private boolean validatePath(String path, HttpServletRequest request) {
        if (path != null && path.length() > Video.MAX_LENGTH.PATH) {
            request.setAttribute(ERRORS.PATH, "Longitud máxima del path superada");
            return false;
        }
        
        return true;
    }
    
    
    @Override
    public String getServletInfo() {
        return "Videos controller";
    }
}
