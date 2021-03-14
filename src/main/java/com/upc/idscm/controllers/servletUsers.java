package com.upc.idscm.controllers;

import com.upc.idscm.models.User;
import com.upc.idscm.database.UserDB;
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

@WebServlet(name = "servletUsers", urlPatterns = {"/servletUsers"})
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
    
    private void initialize_response(HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
    }
    
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        initialize_response(response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        initialize_response(response);
        
        String name = request.getParameter(PARAMS.NAME);
        String surname = request.getParameter(PARAMS.SURNAME);
        String username = request.getParameter(PARAMS.USERNAME);
        String email = request.getParameter(PARAMS.EMAIL);
        String password = request.getParameter(PARAMS.PASSWORD);
        String confirmation_password = request.getParameter(PARAMS.CONFIRMATION_PASSWORD);
        
        User user = new User(name, surname, email, username, password);
        
        PrintWriter out = response.getWriter();
        if (!isValidUser(user, confirmation_password, request)) {
            out.println("<label class=\"danger-text\"> Atención, se han detectado errores!</label>");
            request.getRequestDispatcher(Pages.SIGNUP).forward(request, response);
        } else {
            try {
                UserDB.instance().Signup(user);
                request.getRequestDispatcher(Pages.SIGNUP_COMPLETED).forward(request, response);
            } catch (SQLException e) {
                out.println("<label class=\"danger-text\"> ¡Atención! Se ha producido un error guardando el usuario. Vuelva a intentarlo en unos instantes</label>");
                request.getRequestDispatcher(Pages.SIGNUP).include(request, response);
            }
        }
    }
    
    private boolean isValidUser(User user, String confirmation_password, HttpServletRequest request) { 
        boolean isValid = validateUsername(user.getUsername(), request);
        isValid &= validatePassword(user.getPassword(), request);
        isValid &= validateConfirmationPassword(user.getPassword(), confirmation_password, request);
        isValid &= validateName(user.getName(), request);
        isValid &= validateSurname(user.getSurname(), request);
        isValid &= validateEmail(user.getEmail(), request);
        
        return isValid;
    }
    
    private boolean validateUsername(String username, HttpServletRequest request) {
        try {
            if (username == null || username.isEmpty()) {
                request.setAttribute(ERRORS.USERNAME, "Valor obligatório");
                return false;
            } else if (username.length() > UserDB.MAX_LENGTH.USERNAME) {
                request.setAttribute(ERRORS.USERNAME, "Longitud máxima superada");
                return false;
            } else if (UserDB.instance().usernameUsed(username)) {
                request.setAttribute(ERRORS.USERNAME, "Nombre de usuario en uso");
                return false;
            }
            
            return true;
        } catch (SQLException e) {
            request.setAttribute(ERRORS.USERNAME, "Error inesperado");
            return false;
        }
    }
    
    private boolean validatePassword(String password, HttpServletRequest request) {
        if (password == null || password.isEmpty()) {
            request.setAttribute(ERRORS.PASSWORD, "Valor obligatório");
            return false;
        } else if (password.length() > UserDB.MAX_LENGTH.PASSWORD) {
            request.setAttribute(ERRORS.PASSWORD, "Longitud máxima superada");
            return false;
        }
        
        return true;
    }
    
    private boolean validateName(String name, HttpServletRequest request) {
        if (name == null || name.isEmpty()) {
            request.setAttribute(ERRORS.NAME, "Valor obligatório");
            return false;
        } else if (name.length() > UserDB.MAX_LENGTH.NAME) {
            request.setAttribute(ERRORS.NAME, "Longitud máxima superada");
            return false;
        }
        
        return true;
    }
        
    private boolean validateSurname(String surname, HttpServletRequest request) {
        if (surname == null || surname.isEmpty()) {
            request.setAttribute(ERRORS.SURNAME, "Valor obligatório");
            return false;
        } else if (surname.length() > UserDB.MAX_LENGTH.SURNAME) {
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
            } else if (email.length() > UserDB.MAX_LENGTH.EMAIL) {
                request.setAttribute(ERRORS.EMAIL, "Longitud máxima superada");
                result = false;
            } else if (!Validation.isValidEmail(email)) {
                request.setAttribute(ERRORS.EMAIL, "Correo no válido");
                result = false;
            } else if (UserDB.instance().emailInUse(email)) {
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
