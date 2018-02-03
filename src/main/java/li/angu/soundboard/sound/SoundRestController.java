package li.angu.soundboard.sound;

import li.angu.soundboard.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
@RestController
public class SoundRestController {

    private final SoundRepository soundRepository;
    private final StorageService storageService;

    @Autowired
    public SoundRestController(SoundRepository soundRepository, StorageService storageService) {
        this.soundRepository = soundRepository;
        this.storageService = storageService;
    }

    @RequestMapping("/sounds/{owner}")
    public List<Sound> getSounds(@PathVariable String owner) {
        return soundRepository.findAllByOwner(owner);
    }

    @RequestMapping(value = "/sound/add", method = RequestMethod.POST)
    public boolean postSound(@RequestParam String owner, @RequestParam MultipartFile file, @RequestParam String name) {

        if (soundRepository.existsByOwnerAndName(owner, name)) {
            return false;
        }

        storageService.store(file);
        soundRepository.save(new Sound(owner, name, file.getName()));

        return true;
    }
}
