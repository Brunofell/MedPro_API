package com.example.MedPro_api.controller.medico;

import com.example.MedPro_api.DTO.medico.DadosCadastroMedico;
import com.example.MedPro_api.DTO.medico.DadosListagemMedico;
import com.example.MedPro_api.DTO.medico.DadosUpdateMedico;
import com.example.MedPro_api.entity.medico.Medico;
import com.example.MedPro_api.entity.paciente.Paciente;
import com.example.MedPro_api.infra.security.authMedico.DadosAuthMedico;
import com.example.MedPro_api.infra.security.authMedico.TokenServiceMedico;
import com.example.MedPro_api.infra.security.authPaciente.DadosAuth;
import com.example.MedPro_api.infra.security.authPaciente.DadosTokenJWT;
import com.example.MedPro_api.infra.security.authPaciente.TokenServicePaciente;
import com.example.MedPro_api.repository.medicos.MedicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenServiceMedico tokenServiceMedico;

    @PostMapping
    @Transactional
    public void cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados){
        repository.save(new Medico(dados));
        System.out.println("resposta");
        System.out.println(dados);
    }


    @GetMapping // Exclusão lógica
    public List<DadosListagemMedico> listarMedicos(){
        return repository.findAllByAtivoTrue().stream().map(DadosListagemMedico::new).toList();
    }

    @PutMapping
    @Transactional
    public void AtualizarMedico(@RequestBody @Valid DadosUpdateMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInfos(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }

    @PostMapping("login")
    public ResponseEntity login( @RequestBody @Valid DadosAuthMedico dadosAuthMedico){
        var authToken = new UsernamePasswordAuthenticationToken(dadosAuthMedico.email(), dadosAuthMedico.senha());
        var authentication =   manager.authenticate(authToken);

        var tokenJWT = tokenServiceMedico.gerarToken((Medico) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }


}
