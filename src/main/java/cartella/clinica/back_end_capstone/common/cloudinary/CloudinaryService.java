package cartella.clinica.back_end_capstone.common.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) {
        try {
            Map<?, ?> result = cloudinary.uploader()
                    .upload(file.getBytes(), Cloudinary.asMap(
                            "folder", "FS1024",
                            "public_id", file.getOriginalFilename()));

            return result.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'upload dell'immagine su Cloudinary", e);
        }
    }
}
