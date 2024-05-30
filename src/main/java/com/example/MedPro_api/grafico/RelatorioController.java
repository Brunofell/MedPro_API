package com.example.MedPro_api.grafico;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/api/relatorio")
    public ResponseEntity<byte[]> getRelatorioPdf() throws DocumentException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();
        document.add(new Paragraph("Relatório de Consultas por Médico"));

        byte[] chartImageBytes = relatorioService.generateChart();
        Image chartImage = Image.getInstance(chartImageBytes);
        document.add(chartImage);

        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "relatorio.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }
}