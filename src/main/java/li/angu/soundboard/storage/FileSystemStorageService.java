package li.angu.soundboard.storage;

import li.angu.soundboard.storage.exception.StorageException;
import li.angu.soundboard.storage.exception.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/*************************************************************************
 *
 * ANGULI NETWORKS CONFIDENTIAL
 * __________________
 *
 *  [2014] - [2018] Anguli Networks 
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Anguli Networks. The intellectual and 
 * technical concepts contained herein are proprietary to 
 * Anguli Networks and may be covered by German/EU and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Anguli Networks
 *
 * This File belongs to the SoundBoard from Anguli Networks
 */
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService() {
        this.rootLocation = Paths.get("/Users/finreinhard/Websites/Anguli/SoundBoard/sounds");
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + StringUtils.cleanPath(file.getOriginalFilename()));
            }

            Files.copy(file.getInputStream(), this.rootLocation.resolve(StringUtils.cleanPath(UUID.randomUUID() + ".mp3")));
        } catch (IOException exception) {
            throw new StorageException("Failed to store file " + StringUtils.cleanPath(file.getOriginalFilename()), exception);
        }
    }

    @Override
    public Path load(String filename) {
        return this.rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new StorageFileNotFoundException("Could not read file: " + filename);

        } catch (MalformedURLException exception) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, exception);
        }
    }


}
