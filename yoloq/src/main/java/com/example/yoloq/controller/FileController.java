package com.example.yoloq.controller;


import com.example.yoloq.models.dto.response.FileResponse;
import com.example.yoloq.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file")
public class FileController {
    private final FileService service;


    @Autowired
    public FileController(FileService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<FileResponse> fileUpload(
            @RequestBody MultipartFile image
            ) {
        return new ResponseEntity<>(new FileResponse(this.service.uploadImage(image).getName(), "Created successfully"), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getFile(
            @PathVariable("id") String id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(this.service.getImage(id));
    }
}
