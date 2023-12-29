package com.example.mail.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailFileDTO {
    private String[] toUser;
    private String subject;
    String message;
    MultipartFile file;


}
