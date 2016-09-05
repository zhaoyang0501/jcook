package com.pzy.jcook.workflow.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pzy.jcook.dto.json.FailedResponse;
import com.pzy.jcook.dto.json.ObjectResponse;
import com.pzy.jcook.dto.json.Response;
import com.pzy.jcook.workflow.dto.FileUploadDTO;
@Controller
@RequestMapping("fileupload")
public class FileUploadController {
	
	 @Value("${spring.jsp.uploadpath}")
	 private String uploadpath;
	
	 @RequestMapping(value="/upload", method=RequestMethod.POST)
	 @ResponseBody
	  public  Response handleFileUpload( @RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception, IOException{
	        if (!file.isEmpty()) {
	        	String filename =  file.getOriginalFilename();  
                String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"+ file.getOriginalFilename();  
                file.transferTo(new File(filePath));  
                return new ObjectResponse<FileUploadDTO>(new FileUploadDTO(filename,filename));
	        }
	        else
	        	return new FailedResponse("上传失败");
	 }

}