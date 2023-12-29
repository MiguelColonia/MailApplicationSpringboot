package com.example.mail.controller;


import com.example.mail.IEmailService;
import com.example.mail.domain.EmailDTO;
import com.example.mail.domain.EmailFileDTO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping ("/v1")
public class MailController {

    @Autowired
    private IEmailService emailService;

    public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO){


        System.out.println("Mensaje Recibido" + emailDTO);

        emailService.sendEmail(emailDTO.getToUser(),emailDTO.getSubject(),emailDTO.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("estado", "Enviado");

        return ResponseEntity.ok(response);
    }

    @PostMapping("sendMessageFile")
    public ResponseEntity<?> receiveRequestEmailWithFile(@ModelAttribute EmailFileDTO emailFileDTO){

        try {
            String fileName = emailFileDTO.getFile().getName();
            Path path = Paths.get("src/mail/resources/file/" + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(emailFileDTO.getFile().getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            File file = path.toFile();
            emailService.sendEmailWithFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(), emailFileDTO.getMessage(), file);
            Map<String,String> response = new HashMap<>();
            response.put("estado","enviado");
            response.put("archivo","fileName");
            return ResponseEntity.ok(response);

        } catch (Exception e){
           throw new RuntimeException("Error al enviar el correo" +e.getMessage());

        }


    }

}
