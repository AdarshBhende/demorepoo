
package com.imagecomparison;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;



public class ImageController {

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/api/compare")
    public ResponseEntity<String> compareImages(@RequestParam("image1") MultipartFile image1,
                                                @RequestParam("image2") MultipartFile image2) {
        try {
            BufferedImage img1 = ImageIO.read(image1.getInputStream());
            BufferedImage img2 = ImageIO.read(image2.getInputStream());

            if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
                return ResponseEntity.ok("Failed: Images have different dimensions.");
            }

            int width = img1.getWidth();
            int height = img1.getHeight();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
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
