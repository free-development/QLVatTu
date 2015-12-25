package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/ReadFile")
public class ReadFile extends HttpServlet {
    
    //Initialize global variables
    String fileName="";
    
    private static Logger log = Logger.getLogger(ReadFile.class.getName());
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doPost(request,response);
    }
    
    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("Entered DownloadServlet");
        OutputStream outStream = response.getOutputStream();
        fileName=request.getParameter("fileName");
        log.info("Filename:"+fileName);
        String filePath = "/QLYeuCauVatTu/";
        File f=new File(filePath, fileName);
        String fileType = fileName.substring(fileName.indexOf(".")+1,fileName.length());
        log.info("Filetype:"+fileType+";"+f.length());

        if (fileType.trim().equalsIgnoreCase("txt")) {
            response.setContentType( "text/plain" );
        } else if (fileType.trim().equalsIgnoreCase("doc")) {
            response.setContentType( "application/msword" );
        } else if (fileType.trim().equalsIgnoreCase("xls")) {
            response.setContentType( "application/vnd.ms-excel" );
        } else if (fileType.trim().equalsIgnoreCase("pdf")) {
            response.setContentType( "application/pdf" );
            log.info("content type set to pdf");
        } else {
            response.setContentType( "application/octet-stream" );
        }

        response.setContentLength((int)f.length());
         response.setHeader("Content-Disposition","attachment; filename=\"SecurityPatterns.pdf\"");

        response.setHeader("Cache-Control", "no-cache");
        byte[] buf = new byte[8192];
        FileInputStream inStream = new FileInputStream(f);
        int sizeRead = 0;
        while ((sizeRead = inStream.read(buf, 0, buf.length)) > 0) {
            log.info("size:"+sizeRead);
            outStream.write(buf, 0, sizeRead);
        }
        inStream.close();
        outStream.close();
        
    }
    
    //Get Servlet information
    public String getServletInfo() {
        return "DownloadServlet Information";
    }
}
