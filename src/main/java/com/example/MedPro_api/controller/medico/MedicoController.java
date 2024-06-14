package com.example.MedPro_api.controller.medico;

import com.example.MedPro_api.DTO.ImagemPerfilDTO;
import com.example.MedPro_api.DTO.medico.DadosCadastroMedico;
import com.example.MedPro_api.DTO.medico.DadosListagemMedico;
import com.example.MedPro_api.DTO.medico.DadosLoginMedico;
import com.example.MedPro_api.DTO.medico.DadosUpdateMedico;
import com.example.MedPro_api.DTO.paciente.DadosListagemPaciente;
import com.example.MedPro_api.entity.medico.Medico;
import com.example.MedPro_api.entity.paciente.Paciente;
import com.example.MedPro_api.infra.Exception.EmailDuplicadoException;
import com.example.MedPro_api.infra.Exception.EmailValidationService;
import com.example.MedPro_api.infra.security.authMedico.DadosAuthMedico;
import com.example.MedPro_api.infra.security.authMedico.TokenServiceMedico;
import com.example.MedPro_api.infra.security.authPaciente.DadosAuth;
import com.example.MedPro_api.infra.security.authPaciente.DadosTokenJWT;
import com.example.MedPro_api.infra.security.authPaciente.TokenServicePaciente;
import com.example.MedPro_api.repository.medicos.MedicoRepository;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenServiceMedico tokenServiceMedico;
    @Autowired
    private EmailValidationService emailValidationService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/cadastro")
    @Transactional
    public void cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados){

        emailValidationService.validarEmail(dados.email());

        if(repository.existsByEmail(dados.email())){
            throw new EmailDuplicadoException("Este e-mail já está em uso.");
        }


        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        Medico medico = new Medico(dados);
        medico.setSenha(senhaCriptografada);

        repository.save(medico);
    }


    @GetMapping // Exclusão lógica
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public List<DadosListagemMedico> listarMedicos(){
        return repository.findAllByAtivoTrue().stream().map(DadosListagemMedico::new).toList();
    }

    @PutMapping
    @Transactional
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public void AtualizarMedico(@RequestBody @Valid DadosUpdateMedico dados){


        String senhaCriptografada = null;

        if (dados.senha() != null) {
            senhaCriptografada = passwordEncoder.encode(dados.senha());
        }

        var medico = repository.getReferenceById(dados.id());


        medico.atualizarInfos(dados);

        if (senhaCriptografada != null) {
            medico.setSenha(senhaCriptografada);
        }
        repository.save(medico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public void excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }

    @PostMapping("login")
    public ResponseEntity<DadosLoginMedico> login(@RequestBody @Valid DadosAuthMedico dadosAuthMedico){
        var authToken = new UsernamePasswordAuthenticationToken(dadosAuthMedico.email(), dadosAuthMedico.senha());
        var authentication = manager.authenticate(authToken);

        var medico = (Medico) authentication.getPrincipal();

        if (!medico.isAtivo()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var tokenJWT = tokenServiceMedico.gerarToken((Medico) authentication.getPrincipal());
        var respostaLogin = new DadosLoginMedico(tokenJWT, "medico", medico.getId());

        return ResponseEntity.ok(respostaLogin);
    }

    @GetMapping("/{id}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<DadosListagemMedico> buscarPacientePorId(@PathVariable Long id) {
        Optional<Medico> medicoOptional = repository.findById(id);
        if (medicoOptional.isPresent()) {
            return ResponseEntity.ok(new DadosListagemMedico(medicoOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/image/{id}")
    @Transactional
    public ResponseEntity<String> uploadImagem(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Medico medico = repository.findById(id).orElseThrow(() -> new RuntimeException("Medico não encontrado"));

        // Deletar a foto antiga se existir
        if (medico.getImagem() != null && !medico.getImagem().isEmpty()) {
            String oldFileName = medico.getImagem().substring(medico.getImagem().lastIndexOf("/") + 1);
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
        medico.setImagem(imageUrl);
        repository.save(medico);

        return ResponseEntity.ok("Imagem de perfil atualizada com sucesso!");
    }

    @GetMapping("/image/{id}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<ImagemPerfilDTO> getImagemPerfil(@PathVariable Long id) {
        Optional<Medico> medicoOptional = repository.findById(id);
        if (medicoOptional.isPresent()) {
            String imageUrl = medicoOptional.get().getImagem();
            ImagemPerfilDTO imagemPerfilDTO = new ImagemPerfilDTO(imageUrl);
            return ResponseEntity.ok(imagemPerfilDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
