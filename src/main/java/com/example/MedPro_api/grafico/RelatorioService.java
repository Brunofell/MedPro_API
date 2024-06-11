package com.example.MedPro_api.grafico;

import com.example.MedPro_api.entity.consulta.Consulta;
import com.example.MedPro_api.repository.medicos.MedicoRepository;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RelatorioService {


    public byte[] gerarGraficoConsultasPorMesMedico(List<Consulta> consultas) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Agregue os dados por mês e ano
        consultas.stream()
                .collect(Collectors.groupingBy(c -> c.getData().getMonth()))
                .forEach((mes, consultasNoMes) -> {
                    dataset.addValue(consultasNoMes.size(), "Consultas", mes.toString());
                });

        JFreeChart barChart = ChartFactory.createBarChart(
                "Consultas por Mês",
                "Mês",
                "Número de Consultas",
                dataset);

        ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(chartOutputStream, barChart, 800, 600);

        return chartOutputStream.toByteArray();
    }

    public byte[] gerarGraficoConsultasPorEspecialidade(List<Consulta> consultas) throws IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();

        consultas.stream()
                .collect(Collectors.groupingBy(Consulta::getEspecialidade, Collectors.counting()))
                .forEach((especialidade, count) -> {
                    dataset.setValue(especialidade, count);
                });

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Consultas por Especialidade",
                dataset,
                true,
                true,
                false);

        ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(chartOutputStream, pieChart, 800, 600);

        return chartOutputStream.toByteArray();
    }

    public byte[] gerarGraficoConsultasPorMesGeral(List<Consulta> consultas) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<Month, Long> consultasPorMes = consultas.stream()
                .collect(Collectors.groupingBy(c -> c.getData().getMonth(), Collectors.counting()));

        for (Map.Entry<Month, Long> entry : consultasPorMes.entrySet()) {
            dataset.addValue(entry.getValue(), "Consultas", entry.getKey().toString());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Consultas Marcadas por Mês",
                "Mês",
                "Número de Consultas",
                dataset);

        ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(chartOutputStream, barChart, 800, 600);

        return chartOutputStream.toByteArray();
    }
}
