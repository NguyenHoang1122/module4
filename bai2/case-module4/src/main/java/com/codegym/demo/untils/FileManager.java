package com.codegym.demo.untils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
public class FileManager {
    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                System.out.println("Failed to delete file: " + filePath);
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
    }

    public String uploadFile(String uploadDirFile, MultipartFile file) {
        try {
            String originalName = file.getOriginalFilename();
            String ext = "";
            if (originalName != null && originalName.contains(".")) {
                ext = originalName.substring(originalName.lastIndexOf(".")); // ví dụ ".jpg"
            }
            // 3. Sinh tên random (UUID hoặc timestamp)
            String fileName = UUID.randomUUID().toString() + ext;
            File uploadDir = new File(uploadDirFile);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            File dest = new File(uploadDirFile + "/" + fileName);
            file.transferTo(dest);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if upload fails
        }
    }
}
