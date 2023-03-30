package com.example.admin.repository;

import java.io.ByteArrayInputStream;

public interface PdfService {

	ByteArrayInputStream convertHtmlToPdf(String htmlContent);

}
