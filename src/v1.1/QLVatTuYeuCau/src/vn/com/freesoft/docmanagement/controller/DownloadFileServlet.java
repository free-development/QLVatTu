package vn.com.freesoft.docmanagement.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.freesoft.docmanagement.util.FileUtil;

@Controller
public class DownloadFileServlet extends HttpServlet {
	@RequestMapping("/downloadFile")
	protected void downloadFile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// reads input file from an absolute path
		String path = (String) request.getAttribute("path");
		java.io.File file = new java.io.File(path);
		int index = path.lastIndexOf("/");
		int index2 = path.lastIndexOf("-");
		int index3 = path.lastIndexOf(".");
		String fileName = path.substring(index + 1, index2);
		if (index3 != -1)
			fileName += path.substring(index3);
		FileInputStream inStream = new FileInputStream(file);

		String extension = FileUtil.getExtension(file);
		if (extension.equalsIgnoreCase("PDF")) {

			FileInputStream baos = new FileInputStream(file);
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setContentLength((int) file.length());

			OutputStream os = response.getOutputStream();
			copy(baos, os);
			os.flush();
			os.close();
		} else {
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + FileUtil.getNameFile(file) + "." + FileUtil.getExtension(file) + "\"");
			response.setContentLength((int) file.length());
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inStream.close();
			outStream.close();
		}
		// response.setHeader("Cache-Control", "max-age=60");
		// System.out.println("MIME type: " + mimeType);

		// modifies response
		// response.setContentType(mimeType);

		// forces download
		// String headerKey = "Content-Disposition";
		// String headerValue = String.format("attachment; filename=\"%s\"",
		// file.getName());
		// response.setHeader(headerKey, headerValue);

		// obtains response's output stream

	}

	private static void copy(InputStream is, OutputStream os) throws IOException {
		byte buffer[] = new byte[8192];
		int bytesRead, i;

		while ((bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
	}
}
