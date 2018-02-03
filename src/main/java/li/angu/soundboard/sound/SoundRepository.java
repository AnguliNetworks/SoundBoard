package li.angu.soundboard.sound;

import org.springframework.data.mongodb.repository.MongoRepository;

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
public interface SoundRepository extends MongoRepository<Sound, String> {

    List<Sound> findAllByOwner(String owner);

    boolean existsByOwnerAndName(String owner, String name);

}
