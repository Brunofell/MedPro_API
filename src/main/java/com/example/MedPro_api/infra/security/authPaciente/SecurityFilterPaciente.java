package com.example.MedPro_api.infra.security.authPaciente;

import com.example.MedPro_api.infra.security.authMedico.TokenServiceMedico;
import com.example.MedPro_api.repository.medicos.MedicoRepository;
import com.example.MedPro_api.repository.paciente.PacienteRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
 // requisição do token para ações
 @Component
 public class SecurityFilterPaciente extends OncePerRequestFilter {

     @Autowired
     TokenServicePaciente tokenServicePaciente;
     @Autowired
     PacienteRepository pacienteRepository;

     @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         var tokenJWT = recuperarToken(request);

         if(tokenJWT != null){
             try {
                 var subject = tokenServicePaciente.getSubjectPaciente(tokenJWT);
                 var paciente = pacienteRepository.findByEmail(subject);

                 if (paciente != null) {
                     var authentication = new UsernamePasswordAuthenticationToken(paciente, null, paciente.getAuthorities());
                     SecurityContextHolder.getContext().setAuthentication(authentication);
                 }
             } catch (RuntimeException e) {
                 // Log or handle exception
             }
         }

         filterChain.doFilter(request, response);
     }

     private String recuperarToken(HttpServletRequest request) {
         var authHeader = request.getHeader("Authorization");
         if (authHeader != null && authHeader.startsWith("Bearer ")) {
             return authHeader.replace("Bearer ", "").trim();
         }
         return null;
     }
 }
