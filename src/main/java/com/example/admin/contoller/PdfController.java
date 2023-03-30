package com.example.admin.contoller;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.admin.repository.PdfService;

@Controller
@RequestMapping("/demo")
public class PdfController {

	@Autowired
	private PdfService pdfService;

	@GetMapping("/getPdf")
	public String getDeshboard1() {

		return "admin/ZDemo";

	}
	
	@GetMapping("/getPdfDemo")
	public String getDeshboard() {

		return "admin/download";

	}

	@PostMapping("/generatePdfFile")
	public void generatePdfFile(HttpServletResponse response, String contentToGenerate) throws IOException {
		ByteArrayInputStream byteArrayInputStream = pdfService.convertHtmlToPdf(contentToGenerate);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=file.pdf");
		IOUtils.copy(byteArrayInputStream, response.getOutputStream());
	}
}
