package fr.eni.enchere.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoService {
    //  chemin de sauvegarde
    private final String UPLOAD_DIR = "target/classes/static/images/articles/";

    public String SaveArticlePhoto(MultipartFile photo, long articleId){
//        si photo est vide ou null on mais l'image marteau par defaut
        if (photo == null || photo.isEmpty()) {
            return "/image/encheres_marteau.jpg";
        }

        try {
            //  creer le chemin absolu ressources
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath();
            Files.createDirectories(uploadPath);

//           nom de fichier unique
            String filename = articleId + "_" + System.currentTimeMillis() + "_" + sanitizeFileName(photo.getOriginalFilename());
//            sauvegarde
            Path filePath = uploadPath.resolve(filename);
            photo.transferTo(filePath.toFile());

//          retour URL
            return "/images/articles/" + filename;
        }
        catch (Exception e) {
            System.err.println("ERREUR PhotoService: " + e.getMessage());
            e.printStackTrace();
            return "/image/encheres_marteau.jpg";
        }
    }
//    securite sur le nom pour eviter les injection
    private String sanitizeFileName(String originalName) {
        return originalName != null ? originalName.replaceAll("[^a-zA-Z0-9._-]", "_") : "image.jpg";
    }
}

