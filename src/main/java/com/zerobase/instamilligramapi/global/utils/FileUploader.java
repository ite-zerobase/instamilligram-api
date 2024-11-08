package com.zerobase.instamilligramapi.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.instamilligramapi.global.dto.FileUploadOut;
import com.zerobase.instamilligramapi.global.exceptions.ErrorCode;
import com.zerobase.instamilligramapi.global.exceptions.ZbException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FileUploader {
    @Value("${upload.server.url}")
    private String fileServerUrl;

    @Value("${fileserver.token}")
    private String fileServerToken;

    private final RestTemplate restTemplate;

    public String upload(MultipartFile file, String filename) {
        try {
            return sendFileToServer(file, filename);
        }
        catch (Exception e) {
            throw ZbException.from(ErrorCode.FAILED_TO_UPLOAD, e.getMessage());
        }
    }

    public String extractExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String sendFileToServer(MultipartFile file, String filename) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("token", fileServerToken);

        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return filename;
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<String> result = restTemplate.postForEntity(fileServerUrl, requestEntity, String.class);
        FileUploadOut parsed = objectMapper.readValue(result.getBody(), FileUploadOut.class);
        return parsed.getFilename();

    }
}
