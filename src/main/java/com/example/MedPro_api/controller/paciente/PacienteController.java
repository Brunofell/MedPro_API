package com.example.MedPro_api.controller.paciente;

import com.example.MedPro_api.DTO.ImagemPerfilDTO;
import com.example.MedPro_api.DTO.paciente.*;
import com.example.MedPro_api.entity.paciente.Paciente;
import com.example.MedPro_api.infra.Exception.EmailDuplicadoException;
import com.example.MedPro_api.infra.Exception.EmailValidationService;
import com.example.MedPro_api.infra.security.authPaciente.DadosAuth;
import com.example.MedPro_api.infra.security.authPaciente.TokenServicePaciente;
import com.example.MedPro_api.repository.paciente.PacienteRepository;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

// mensagem
@RestController
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenServicePaciente tokenService;
    @Autowired
    private EmailValidationService emailValidationService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/cadastro")
    @Transactional
    public void cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados){ // valid ali valida as infoss

        emailValidationService.validarEmail(dados.email());

        if(repository.existsByEmail(dados.email())){
            throw new EmailDuplicadoException("Este e-mail já está em uso por outro usuário.");
        }

        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        Paciente paciente = new Paciente(dados);
        paciente.setSenha(senhaCriptografada);

        repository.save(paciente);
    }

    @GetMapping
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public List<DadosListagemPaciente> listarPaciente() {
        return repository.findAll().stream().map(DadosListagemPaciente::new).toList();
    }

    @PutMapping
    @Transactional
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public void atualizarPaciente(@RequestBody @Valid DadosUpdatePaciente dados){

        String senhaCriptografada = null;

        if (dados.senha() != null) {
            senhaCriptografada = passwordEncoder.encode(dados.senha());
        }

        var paciente = repository.getReferenceById(dados.id());

        paciente.atualizarInfos(dados);

        if (senhaCriptografada != null) {
            paciente.setSenha(senhaCriptografada);
        }

        repository.save(paciente);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public void excluir(@PathVariable Long id){
        repository.deleteById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<DadosLoginPaciente> login(@Valid @RequestBody DadosAuth dados){
        var authToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(authToken);

        var paciente = (Paciente) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(paciente);

        var respostaLogin = new DadosLoginPaciente(tokenJWT, "paciente", paciente.getId());

        return ResponseEntity.ok(respostaLogin);
    }


    @GetMapping("/{id}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<DadosListagemPaciente> buscarPacientePorId(@PathVariable Long id) {
        Optional<Paciente> pacienteOptional = repository.findById(id);
        if (pacienteOptional.isPresent()) {
            return ResponseEntity.ok(new DadosListagemPaciente(pacienteOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/image/{id}")
    @Transactional
    public ResponseEntity<String> uploadImagem(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Paciente paciente = repository.findById(id).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        // Deletar a foto antiga se existir
        if (paciente.getImagem() != null && !paciente.getImagem().isEmpty()) {
            String oldFileName = paciente.getImagem().substring(paciente.getImagem().lastIndexOf("/") + 1);
            Bucket bucket = StorageClient.getInstance().bucket();
            Blob oldBlob = bucket.get(oldFileName);
            if (oldBlob != null) {
                oldBlob.delete();
            }
        }

        // Upload da nova foto para o Firebase Storage
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.create(file.getOriginalFilename(), file.getInputStream(), file.getContentType());

        // Tornar o arquivo publicamente acessível
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        String imageUrl = String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), blob.getName());
        paciente.setImagem(imageUrl);
        repository.save(paciente);

        return ResponseEntity.ok("Imagem de perfil atualizada com sucesso!");
    }

    @GetMapping("/image/{id}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<ImagemPerfilDTO> getImagemPerfil(@PathVariable Long id) {
        Optional<Paciente> pacienteOptional = repository.findById(id);
        if (pacienteOptional.isPresent()) {
            String imageUrl = pacienteOptional.get().getImagem();
            ImagemPerfilDTO imagemPerfilDTO = new ImagemPerfilDTO(imageUrl);
            return ResponseEntity.ok(imagemPerfilDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
