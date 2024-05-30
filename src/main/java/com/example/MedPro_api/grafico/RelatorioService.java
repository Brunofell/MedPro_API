package com.example.MedPro_api.grafico;


import com.example.MedPro_api.entity.consulta.Consulta;
import com.example.MedPro_api.repository.consulta.ConsultaRepository;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public byte[] generateChart() throws IOException {
        List<Consulta> consultas = consultaRepository.findAll();

        Map<String, Long> consultasPorMedico = consultas.stream()
                .collect(Collectors.groupingBy(c -> c.getMedico().getNome(), Collectors.counting()));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Long> entry : consultasPorMedico.entrySet()) {
            dataset.addValue(entry.getValue(), "Consultas", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Consultas por Médico",
                "Médico",
                "Número de Consultas",
                dataset
        );

        BufferedImage chartImage = barChart.createBufferedImage(640, 480);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ChartUtils.writeBufferedImageAsPNG(baos, chartImage);
        return baos.toByteArray();
    }
}