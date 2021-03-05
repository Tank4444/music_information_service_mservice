package ru.chuikov.mservice.music_information_service.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;
import ru.chuikov.mservice.music_information_service.entity.MusicItemInfo;
import ru.chuikov.mservice.music_information_service.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface FileService {
    void add (MultipartFile multipartFile, String username) throws IOException;
    void delete(String id,String username);
    GridFsResource getMusicById(String id, String username);
    List<MusicItemInfo> findAllByUser(String username);

}
