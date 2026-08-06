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
import java.io.InputStream;
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

        try (InputStream input = file.getInputStream()) {
            BufferedImage original = ImageIO.read(input);
            if (original == null) {
                throw new FileStorageException("Unsupported or corrupted image file", null);
            }

            BufferedImage resized = resize(original);
            boolean written = ImageIO.write(resized, "jpg", uploadDir.resolve(filename).toFile());
            if (!written) {
                throw new FileStorageException("No JPEG writer available for the uploaded image", null);
            }
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file: " + filename, e);
        }

        return publicBaseUrl + "/uploads/" + filename;
    }

    /**
     * Scales the image down to TARGET_WIDTH (preserving aspect ratio, images already
     * narrower are left at their original size) and drops any alpha channel, since the
     * picture is always re-encoded as JPEG, which cannot represent transparency.
     */
    private BufferedImage resize(BufferedImage original) {
        int targetWidth = Math.min(original.getWidth(), TARGET_WIDTH);
        int targetHeight = (int) Math.round(targetWidth * ((double) original.getHeight() / original.getWidth()));

        Image scaled = original.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage buffered = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = buffered.createGraphics();
        graphics.drawImage(scaled, 0, 0, null);
        graphics.dispose();

        return buffered;
    }
}
