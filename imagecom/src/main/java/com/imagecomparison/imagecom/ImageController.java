package com.imagecomparison.imagecom;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

class ImageRequest {
    private byte[] image1;
    private byte[] image2;

    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }
}

@RestController
@RequestMapping("/api") // Ensure correct API path
@CrossOrigin(origins = "http://127.0.0.1:5501")  // Allow CORS for this controller
public class ImageController {

    @PostMapping("/compare")
    public ResponseEntity<String> compareImages(@RequestBody ImageRequest request) {
        try {
            // Create BufferedImage from byte arrays
            BufferedImage img1 = ImageIO.read(new ByteArrayInputStream(request.getImage1()));
            BufferedImage img2 = ImageIO.read(new ByteArrayInputStream(request.getImage2()));

            if (img1 == null || img2 == null) {
                return ResponseEntity.badRequest().body("Failed to decode images.");
            }

            if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
                return ResponseEntity.ok("Failed: Images have different dimensions.");
            }

            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                        return ResponseEntity.ok("Failed: Images are different.");
                    }
                }
            }
            return ResponseEntity.ok("Success: Images are identical.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing images.");
        }
    }
}
