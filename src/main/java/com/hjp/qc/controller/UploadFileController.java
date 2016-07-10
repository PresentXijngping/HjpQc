package com.hjp.qc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hjp.qc.service.ISequenceService;
import com.hjp.qc.service.IStaticService;
import com.hjp.qc.vo.StaticVariable;


@Controller(value="UploadFileController")
public class UploadFileController {
	
	@Resource(name = "sequenceService")
	private ISequenceService sequenceService;
	
	@Resource(name = "staticService")
	private IStaticService staticService;
	
	@ResponseBody
	@RequestMapping(value = "/fileUpload.do", method = RequestMethod.POST)
	public String fileUpload(HttpServletRequest req, HttpServletResponse res,
			@RequestParam() MultipartFile fileInfo) {
		
		byte[] fileByte = null;
		OutputStream out = null;
		String serverFilePath = "";
		String fileName = "";
		String suffix = "";
		try {
			fileByte = fileInfo.getBytes();
			
			StaticVariable staticVariable = staticService.queryStaticByTypeCode("FILE_PATH");
			//获取服务器完整地址，以及将虚拟文件夹加入到地址后
			String serverPath = staticVariable.getKeyName();
			//System.out.println(serverPath);
			
			//根据项目名在虚拟文件夹查找有无该项目使用的文件夹
			String dirPath = staticVariable.getKeyCode() + req.getContextPath() + "/";
			//System.out.println(dirPath);
			File dirFile = new File(dirPath);
			//创建项目使用文件夹
			if (!dirFile.exists() && !dirFile.isDirectory()) {
				if (!dirFile.mkdir()) {
					System.out.println("创建项目使用文件夹失败");
				}
			}
			
			String s = new String(fileInfo.getOriginalFilename());
			suffix = s.substring(s.lastIndexOf("."),s.length());
			fileName = sequenceService.nextval("SEQ_FILE_ID").toString();
			String filePath = dirPath + fileName + suffix;
			
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			
			out = new FileOutputStream(file);
			out.write(fileByte);
			out.flush();
			
			serverFilePath = serverFilePath + serverPath + req.getContextPath() + "/" + fileName + suffix;
			//System.out.println(serverFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//String callBack = req.getParameter("callBack");
		if (!"".equals(serverFilePath)) {
			return "<script>parent.fileUploadCallBack" + "('" +serverFilePath+ "','" + fileName + suffix + "');</script>";
		}
		
		return fileInfo.getOriginalFilename();
	}
	
}
