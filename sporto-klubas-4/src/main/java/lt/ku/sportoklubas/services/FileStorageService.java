package lt.ku.sportoklubas.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path storageLocation;

    public FileStorageService() {
        this.storageLocation = Paths.get("uploads");
        try {
            Files.createDirectories(storageLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void store(MultipartFile file, String filename) {
        try {
            Path targetFile = this.storageLocation.resolve(filename).normalize().toAbsolutePath();
            InputStream input = file.getInputStream();
            Files.copy(input, targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Išsaugant failą įvyko klaida");
            e.printStackTrace();
        }
    }

    public void store(MultipartFile file) {
        this.store(file, file.getOriginalFilename());
    }

    public Resource loadFile(String filename) {
        try {
            Path filePath = this.storageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!Files.exists(filePath)) return null;
            return resource;
        } catch (IOException e) {
            System.out.println("Įvyko klaida paimant failą");
            e.printStackTrace();
        }
        return null;
    }
}
