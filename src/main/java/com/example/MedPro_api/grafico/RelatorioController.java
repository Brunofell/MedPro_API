package com.example.MedPro_api.grafico;

import com.example.MedPro_api.entity.consulta.Consulta;
import com.example.MedPro_api.grafico.RelatorioService;
import com.example.MedPro_api.repository.consulta.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping("/consultas-por-mes")
    public ResponseEntity<byte[]> getConsultasPorMes(@RequestParam Long medicoId) {
        List<Consulta> consultas = consultaRepository.findByMedicoId(medicoId);

        byte[] grafico;
        try {
            grafico = relatorioService.gerarGraficoConsultasPorMesMedico(consultas);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(grafico.length);

        return new ResponseEntity<>(grafico, headers, HttpStatus.OK);
    }

    @GetMapping("/consultas-por-especialidade")
    public ResponseEntity<byte[]> getConsultasPorEspecialidade() {
        List<Consulta> consultas = consultaRepository.findAll();

        byte[] grafico;
        try {
            grafico = relatorioService.gerarGraficoConsultasPorEspecialidade(consultas);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(grafico.length);

        return new ResponseEntity<>(grafico, headers, HttpStatus.OK);
    }

    @GetMapping("/consultas-por-mes-geral")
    public ResponseEntity<byte[]> getConsultasPorMesGeral() {
        List<Consulta> consultas = consultaRepository.findAll();

        byte[] grafico;
        try {
            grafico = relatorioService.gerarGraficoConsultasPorMesGeral(consultas);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(grafico.length);

        return new ResponseEntity<>(grafico, headers, HttpStatus.OK);
    }

}