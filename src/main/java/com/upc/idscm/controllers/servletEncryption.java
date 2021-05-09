package com.upc.idscm.controllers;

import static com.upc.idscm.tools.Encryption.decryptDocument;
import static com.upc.idscm.tools.Encryption.decryptXML;
import static com.upc.idscm.tools.Encryption.encryptDocument;
import static com.upc.idscm.tools.Encryption.encryptXML;
import static com.upc.idscm.tools.Encryption.readDocument;
import static com.upc.idscm.tools.Encryption.store;
import com.upc.idscm.tools.Pages;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;

@WebServlet(name = "servletEncryption", urlPatterns = {"/encryptXML", "/encryptDOC", "/decryptXML", "/decryptDOC", "/encryption"})
@MultipartConfig
public class servletEncryption extends HttpServlet {
    private class ACTIONS {
        private static final String ENCRYPT_XML = "/encryptXML";
        private static final String ENCRYPT_DOC = "/encryptDOC";
        private static final String DECRYPT_XML = "/decryptXML";
        private static final String DECRYPT_DOC = "/decryptDOC";
    }
    
    private static final String UPLOAD_PATH = "C:/ISDCMUploads";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
        
        HttpSession session = request.getSession(false);
        if (session.getAttribute("Username") == null || session.getAttribute("UserID") == null) {
            request.getSession().invalidate();
            response.sendRedirect(Pages.LOGIN);
        }
        else request.getRequestDispatcher(Pages.ENCRYPTION).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        
        processRequest(request, response);
        
        HttpSession session = request.getSession(false);
        if (session.getAttribute("Username") == null || session.getAttribute("UserID") == null) {
            request.getSession().invalidate();
            response.sendRedirect(Pages.LOGIN);
        }
        else {
            switch (request.getServletPath()) {
                case ACTIONS.ENCRYPT_DOC: {
                    try {
                        String name = getFilename(request);
                        String extension = FilenameUtils.getExtension(name);
                        String filename = name.replaceFirst("[.][^.]+$", "");
                        String storedPath = storeFile(request, filename, extension);

                        encryptDocument(storedPath, extension);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        response.sendRedirect(Pages.ERROR);
                        return;
                    }
                    break;
                }
                case ACTIONS.ENCRYPT_XML: {
                    try {
                        String name = getFilename(request);
                        String extension = FilenameUtils.getExtension(name);
                        String filename = name.replaceFirst("[.][^.]+$", "");
                        String storedPath = storeFile(request, filename, extension);
                        String targetPath = storedPath + "_encrypted."+extension;

                        Document doc = readDocument(storedPath+"_original."+extension);
                        store(encryptXML(doc), targetPath);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        response.sendRedirect(Pages.ERROR);
                        return;
                    }
                    break;
                }
                case ACTIONS.DECRYPT_DOC: {
                    try {
                        String name = getFilename(request);
                        String extension = FilenameUtils.getExtension(name);
                        String filename = name.replaceFirst("[.][^.]+$", "");
                        String storedPath = storeFile(request, filename, extension);

                        decryptDocument(storedPath, extension);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        response.sendRedirect(Pages.ERROR);
                        return;
                    }
                    break;
                }
                case ACTIONS.DECRYPT_XML: {
                    try {
                        String name = getFilename(request);
                        String extension = FilenameUtils.getExtension(name);
                        String filename = name.replaceFirst("[.][^.]+$", "");
                        String storedPath = storeFile(request, filename, extension);
                        String targetPath = storedPath + "_decrypted." + extension;

                        Document doc = readDocument(storedPath+"_original."+extension);
                        store(decryptXML(doc), targetPath);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        response.sendRedirect(Pages.ERROR);
                        return;
                    }
                    break;
                }
            }
            response.sendRedirect(Pages.ENCRYPTION_COMPLETED);
        }
    }

    private String getFilename(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        return Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
    }
    
    private String storeFile(HttpServletRequest request, String fileName, String ext) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        
        File directory = new File(UPLOAD_PATH);
        if (!directory.exists()) directory.mkdir();
 
        String path = UPLOAD_PATH + File.separator + fileName;
        
        String complete_path = path + "_original." + ext;
        
        OutputStream out = new FileOutputStream(new File(complete_path));
        InputStream filecontent = filePart.getInputStream();

        int read = 0;
        final byte[] bytes = new byte[1024];

        while ((read = filecontent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.close();
        return path;
    }
    
    @Override
    public String getServletInfo() {
        return "Encryption controller";
    }
}
