package controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ImageUrls;

@WebServlet("/picture")
public class PicturesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public PicturesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		if(request.getParameterMap().containsValue("button")){
			url = ImageUrls.BUTTON_PATH + request.getParameter("buttontype") + ".jpg";
			File image = new File(url);
			try(OutputStream out = response.getOutputStream()){
				Path path = image.toPath();
				Files.copy(path, out);
				out.flush();
			}catch (IOException e){
				System.out.println("Image not found");
			}
//			visualisateFile(image, response);
		}
		
	}

	private void visualisateFile(File image, HttpServletResponse response) {
		try(OutputStream out = response.getOutputStream()){
			Path path = image.toPath();
			Files.copy(path, out);
			out.flush();
		}catch (IOException e){
			System.out.println("Image not found");
		}
		
	}

	

}
