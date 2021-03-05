package ru.chuikov.mservice.music_information_service.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.chuikov.mservice.music_information_service.entity.MusicItemInfo;
import ru.chuikov.mservice.music_information_service.service.FileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
@Log
public class FileServiceImpl implements FileService {

    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;

    public FileServiceImpl(GridFsTemplate gridFsTemplate, GridFsOperations gridFsOperations) {
        this.gridFsTemplate = gridFsTemplate;
        this.gridFsOperations = gridFsOperations;
    }


    @Override
    public void add(MultipartFile multipartFile,String username) throws IOException {
        log.info("add file start by "+username);
        DBObject metadata=new BasicDBObject();
        metadata.put("username", username);
        gridFsTemplate.store(multipartFile.getInputStream()
                ,multipartFile.getOriginalFilename()
                ,multipartFile.getContentType()
                ,metadata);
        log.info("add file end by "+username);
    }

    @Override
    public void delete(String id, String username) {
        log.info("delete file start by "+username+" with id "+id);
        gridFsTemplate.delete(
            new Query(Criteria.where("_id").is(id)
                    .andOperator(Criteria.where("metadata.username").is(username))));
        log.info("delete file end by "+username+" with id "+id);

    }

    @Override
    public GridFsResource getMusicById(String id, String username) {
        log.info("get music by "+username+" with id "+id);
        return gridFsOperations.getResource(gridFsTemplate.findOne(
                new Query(Criteria.where("_id").is(id)
                        .andOperator(Criteria.where("metadata.username").is(username)))));
        }

    @Override
    public List<MusicItemInfo> findAllByUser(String username) {
        log.info("get music list of "+username);
        ArrayList<MusicItemInfo> result=new ArrayList<>();
        List<GridFSFile> gridFSFiles = new ArrayList<GridFSFile>();
        gridFsTemplate.find(new Query(Criteria.where("metadata.username").is(username))).into(gridFSFiles);
        for (GridFSFile file: gridFSFiles) {
            //result.add(file.getObjectId().toString());
            GridFsResource resource=gridFsOperations.getResource(file);
            result.add(MusicItemInfo.builder()
                    .id(resource.getFileId().toString())
                    .name(resource.getFilename())
                    .length(file.getLength())
                    .contentType(resource.getContentType())
                    .uploadDate(file.getUploadDate())
                    .build());
        }
        return result;
    }


}
