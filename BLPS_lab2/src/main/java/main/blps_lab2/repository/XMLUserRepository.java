package main.blps_lab2.repository;

import com.thoughtworks.xstream.XStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab2.data.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Repository
@Slf4j
public class XMLUserRepository {
    private final XStream xstream;
    private final Path xmlPath;

    public XMLUserRepository(@Value("${lab2.dir}") String rawPath) {
        xmlPath = Paths.get(rawPath).resolve("users.xml");
        xstream = new XStream();
        xstream.allowTypes(new Class[] {UserEntity.class});
        xstream.alias("user", UserEntity.class);
    }

    public List<UserEntity> getAll() {
        return (List<UserEntity>) xstream.fromXML(xmlPath.toFile());
    }

    public void save(UserEntity newUser){
        List<UserEntity> users = getAll();
        users.add(newUser);
        saveAll(users);
    }

    public void saveAll(List<UserEntity> users){
        try {
            Files.writeString(xmlPath, xstream.toXML(users));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @SneakyThrows
    public Optional<UserEntity> findByEmail(String email) {
        return getAll().stream()
                .filter(elem -> email.equals(elem.getEmail()))
                .findFirst();
    }

    public void delete(UserEntity user) {
        List<UserEntity> users = getAll();
        users.remove(user);
        saveAll(users);
    }
}
