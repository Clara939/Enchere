package fr.eni.enchere.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoService {
    //  chemin de sauvegarde
    private final String UPLOAD_DIR = "uploads/articles/";

    public String SaveArticlePhoto(MultipartFile photo, long articleId){
//        si photo est vide ou null on mais l'image marteau par defaut
        if (photo == null || photo.isEmpty()) {
            return "/images/encheres_marteau.jpg";
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
            return "/images/encheres_marteau.jpg";
        }
    }
//    securite sur le nom pour eviter les injection
    private String sanitizeFileName(String originalName) {
        return originalName != null ? originalName.replaceAll("[^a-zA-Z0-9._-]", "_") : "image.jpg";
    }

//    fonction pour supprimer la photo
    public void deleteArticlePhoto(String urlPhoto) {
        // image par deffaut si photo enlever
        if (urlPhoto == null || urlPhoto.equals("/images/encheres_marteau.jpg")) {
            return;
        }

        try {
            // Extraire le nom du fichier depuis l'URL
            String fileName = urlPhoto.substring(urlPhoto.lastIndexOf("/") + 1);
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath();
            Path filePath = uploadPath.resolve(fileName);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println(" Photo supprimée: " + fileName);
            }
        } catch (Exception e) {
            System.err.println(" Erreur suppression photo " + urlPhoto + ": " + e.getMessage());
        }
    }

}

