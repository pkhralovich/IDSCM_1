package com.upc.idscm.controllers;

import com.upc.idscm.models.User;
import com.upc.idscm.tools.Pages;
import com.upc.idscm.tools.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "servletUsers", urlPatterns = { "/login", "/signup", "/logout"})
public class servletUsers extends HttpServlet {
    private class PARAMS {
        private static final String NAME = "name";
        private static final String SURNAME = "surname";
        private static final String USERNAME = "username";
        private static final String EMAIL = "email";
        private static final String PASSWORD = "password";
        private static final String CONFIRMATION_PASSWORD = "confirmation_password";
    }
    
    private class ERRORS {
        private static final String NAME = "name_error";
        private static final String SURNAME = "surname_error";
        private static final String USERNAME = "username_error";
        private static final String EMAIL = "email_error";
        private static final String PASSWORD = "password_error";
        private static final String CONFIRMATION_PASSWORD = "confirmation_password_error";
    }
    
    private class ACTIONS {
        private static final String LOGIN = "/login";
        private static final String LOGOUT = "/logout";
        private static final String SIGNUP = "/signup";
    }
    
    private void initialize_response(HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                                            throws ServletException, IOException {
        
        initialize_response(response);
        
        HttpSession session = request.getSession(false);
        if (session.getAttribute("Username") != null
                && session.getAttribute("UserID") != null) {
            response.sendRedirect(Pages.VIDEOS);
        }
        
        switch (request.getServletPath()) {
            case ACTIONS.LOGIN: {
                request.getRequestDispatcher(Pages.LOGIN).forward(request, response);
                break;
            }
            case ACTIONS.SIGNUP: {
                request.getRequestDispatcher(Pages.SIGNUP).forward(request, response);
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

        switch (request.getServletPath()) {
            case ACTIONS.SIGNUP: {
                signup(request, response);
                break;
            }
            case ACTIONS.LOGIN: {
                login(request, response);
                break;
            }
            case ACTIONS.LOGOUT: {
                logout(request, response);
                break;
            }
            default: {
                response.sendRedirect(Pages.NOT_FOUND);
            }
        }
    }
    
    private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter(PARAMS.NAME);
        String surname = request.getParameter(PARAMS.SURNAME);
        String username = request.getParameter(PARAMS.USERNAME);
        String email = request.getParameter(PARAMS.EMAIL);
        String password = request.getParameter(PARAMS.PASSWORD);
        String confirmation_password = request.getParameter(PARAMS.CONFIRMATION_PASSWORD);

        User user = new User(name, surname, email, username, password);
        if (!isValidUser(user, confirmation_password, request)) {
            request.getRequestDispatcher(Pages.SIGNUP).forward(request, response);
        } else {
            try {
                User.signup(user);
                response.sendRedirect(Pages.SIGNUP_COMPLETED);
            } catch (SQLException e) {
                PrintWriter out = response.getWriter();
                out.println("<label class=\"danger-text\"> ¡Atención! Se ha producido un error guardando el usuario. Vuelva a intentarlo en unos instantes</label>");
                request.getRequestDispatcher(Pages.SIGNUP).include(request, response);
            }
        }
    }
    
    private void login(HttpServletRequest request, HttpServletResponse response) 
                                            throws ServletException, IOException {
                
        String username = request.getParameter(PARAMS.USERNAME);
        String password = request.getParameter(PARAMS.PASSWORD);

        PrintWriter out = response.getWriter();
        if (!validateUsername(username, request, false)
                || !validatePassword(password, request, false)) {
            
            request.getRequestDispatcher(Pages.LOGIN).forward(request, response);
        } else {
            try {
                int userID = User.authenticate(username, password);
                if (userID >= 0) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("Username", username);
                    session.setAttribute("UserID", userID);
                    response.sendRedirect(Pages.VIDEOS);
                } else {
                    out.println("<label class=\"danger-text\"> El usuario o la contraseña no son válidos. </label>");
                    request.getRequestDispatcher(Pages.LOGIN).include(request, response);
                }
            } catch (SQLException e) {
                out.println("<label class=\"danger-text\"> ¡Atención! Se ha producido un error comprobando el usuario. Vuelva a intentarlo en unos instantes</label>");
                request.getRequestDispatcher(Pages.LOGIN).include(request, response);
            }
        }
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response) 
                                           throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(Pages.LOGIN);
    }
    
    private boolean isValidUser(User user, String confirmation_password, HttpServletRequest request) { 
        boolean isValid = validateUsername(user.getUsername(), request, true);
        isValid &= validatePassword(user.getPassword(), request, true);
        isValid &= validateConfirmationPassword(user.getPassword(), confirmation_password, request);
        isValid &= validateName(user.getName(), request);
        isValid &= validateSurname(user.getSurname(), request);
        isValid &= validateEmail(user.getEmail(), request);
        
        return isValid;
    }
    
    private boolean validateUsername(String username, HttpServletRequest request, boolean new_user) {
        try {
            if (new_user) {
                if (username == null || username.isEmpty()) {
                    request.setAttribute(ERRORS.USERNAME, "Valor obligatório");
                    return false;
                } else if (username.length() > User.MAX_LENGTH.USERNAME) {
                    request.setAttribute(ERRORS.USERNAME, "Longitud máxima superada");
                    return false;
                } else if (User.usernameUsed(username)) {
                    request.setAttribute(ERRORS.USERNAME, "Nombre de usuario en uso");
                    return false;
                }
            } else {
                if (username == null || username.isEmpty()) {
                    request.setAttribute(ERRORS.USERNAME, "Valor obligatório");
                    return false;
                }
            }
            
            
            return true;
        } catch (SQLException e) {
            request.setAttribute(ERRORS.USERNAME, "Error inesperado");
            return false;
        }
    }
    
    private boolean validatePassword(String password, HttpServletRequest request, boolean new_user) {
        if (new_user) {
            if (password == null || password.isEmpty()) {
                request.setAttribute(ERRORS.PASSWORD, "Valor obligatório");
                return false;
            } else if (password.length() > User.MAX_LENGTH.PASSWORD) {
                request.setAttribute(ERRORS.PASSWORD, "Longitud máxima superada");
                return false;
            }
        } else {
            if (password == null || password.isEmpty()) {
                request.setAttribute(ERRORS.PASSWORD, "Valor obligatório");
                return false;
            } else if (password.length() > User.MAX_LENGTH.PASSWORD) {
                request.setAttribute(ERRORS.PASSWORD, "Longitud máxima superada");
                return false;
            }
        }
        
        
        return true;
    }
    
    private boolean validateName(String name, HttpServletRequest request) {
        if (name == null || name.isEmpty()) {
            request.setAttribute(ERRORS.NAME, "Valor obligatório");
            return false;
        } else if (name.length() > User.MAX_LENGTH.NAME) {
            request.setAttribute(ERRORS.NAME, "Longitud máxima superada");
            return false;
        }
        
        return true;
    }
        
    private boolean validateSurname(String surname, HttpServletRequest request) {
        if (surname == null || surname.isEmpty()) {
            request.setAttribute(ERRORS.SURNAME, "Valor obligatório");
            return false;
        } else if (surname.length() > User.MAX_LENGTH.SURNAME) {
            request.setAttribute(ERRORS.SURNAME, "Longitud máxima superada");
            return false;
        }
        
        return true;
    }
    
    private boolean validateEmail(String email, HttpServletRequest request) {
        try {
            boolean result = true;
            if (email == null || email.isEmpty()) {
                request.setAttribute(ERRORS.EMAIL, "Valor obligatório");
                result = false;
            } else if (email.length() > User.MAX_LENGTH.EMAIL) {
                request.setAttribute(ERRORS.EMAIL, "Longitud máxima superada");
                result = false;
            } else if (!Validation.isValidEmail(email)) {
                request.setAttribute(ERRORS.EMAIL, "Correo no válido");
                result = false;
            } else if (User.emailInUse(email)) {
                request.setAttribute(ERRORS.EMAIL, "Correo en uso");
                result = false;
            }

            return result;
        } catch (SQLException e) {
            request.setAttribute(ERRORS.EMAIL, "Error inesperado");
            return false;
        }
    }
    
    private boolean validateConfirmationPassword(String password, String confirmation_password, HttpServletRequest request) {
        if (confirmation_password == null || confirmation_password.isEmpty()) {
            request.setAttribute(ERRORS.CONFIRMATION_PASSWORD, "Valor obligatório");
            return false;
        } else if (!password.equals(confirmation_password)) {
            request.setAttribute(ERRORS.CONFIRMATION_PASSWORD, "Las contraseñas no coinciden");
            return false;
        }
        
        return true;
    }
    
    
    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Users controller";
    }// </editor-fold>

}
