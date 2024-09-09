package uz.mango.apps.testtaskgbw.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.mango.apps.testtaskgbw.service.ImageService;
import org.springframework.http.HttpStatus;

import java.io.IOException;


@RestController
@RequestMapping("api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Operation(summary = "Upload an image", description = "Uploads an image file to the server.")
    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = imageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @Operation(summary = "Download an image", description = "Downloads an image file from the server by its file name.")
    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE))
                .body(imageData);
    }
}
