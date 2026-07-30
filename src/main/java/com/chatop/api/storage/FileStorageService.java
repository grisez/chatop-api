package com.chatop.api.storage;

import com.chatop.api.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Resizes uploaded rental pictures to a fixed size and stores them on disk,
 * so every rental displays a picture with the same dimensions.
 */
@Service
public class FileStorageService {

    private static final int TARGET_WIDTH = 800;

    private final Path uploadDir;
    private final String publicBaseUrl;

    public FileStorageService(@Value("${app.upload-dir}") String uploadDir,
                               @Value("${app.base-url}") String publicBaseUrl) {
        this.uploadDir = Path.of(uploadDir);
        this.publicBaseUrl = publicBaseUrl;
        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException e) {
            throw new FileStorageException("Could not create upload directory: " + uploadDir, e);
        }
    }

    public String store(MultipartFile file) {
        String filename = UUID.randomUUID() + ".jpg";

        try {
            BufferedImage original = ImageIO.read(file.getInputStream());
            if (original == null) {
                throw new FileStorageException("Unsupported or corrupted image file", null);
            }

            BufferedImage resized = resize(original);
            ImageIO.write(resized, "jpg", uploadDir.resolve(filename).toFile());
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file: " + filename, e);
        }

        return publicBaseUrl + "/uploads/" + filename;
    }

    /**
     * Scales the image down to TARGET_WIDTH, preserving its original aspect ratio.
     * Images already narrower than TARGET_WIDTH are left untouched.
     */
    private BufferedImage resize(BufferedImage original) {
        if (original.getWidth() <= TARGET_WIDTH) {
            return original;
        }

        int scaledWidth = TARGET_WIDTH;
        int scaledHeight = (int) Math.round(TARGET_WIDTH * ((double) original.getHeight() / original.getWidth()));

        Image scaled = original.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        BufferedImage scaledBuffered = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = scaledBuffered.createGraphics();
        graphics.drawImage(scaled, 0, 0, null);
        graphics.dispose();

        return scaledBuffered;
    }
}
