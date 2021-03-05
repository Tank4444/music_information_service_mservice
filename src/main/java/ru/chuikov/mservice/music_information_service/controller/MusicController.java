package ru.chuikov.mservice.music_information_service.controller;

import lombok.extern.java.Log;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.chuikov.mservice.music_information_service.service.FileService;
import ru.chuikov.mservice.music_information_service.service.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/music")
@Log
public class MusicController {

    private final FileService fileService;
    private final Tika tika;

    public MusicController(FileService fileService, Tika tika) {
        this.fileService = fileService;
        this.tika = tika;
    }

    @PostMapping(value = "/add")
    public ResponseEntity addMusic(@RequestParam("file") MultipartFile multipartFile, Principal principal) {
        try{
            if(!tika.detect(multipartFile.getInputStream()).contains("audio")){
                throw new Exception("Media type not supported");
            }

            fileService.add(multipartFile,principal.getName());
            return new ResponseEntity<>("Music added",HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/getAll")
    public ResponseEntity getListMusicByUser(Principal principal){
        return new ResponseEntity<List>(fileService.findAllByUser(principal.getName()),HttpStatus.OK);
    }

    @PostMapping(value = "/get/{id}")
    public ResponseEntity getListMusicByUser(@PathVariable("id") String id, Principal principal){
        try {
            GridFsResource fsResource=fileService.getMusicById(id,principal.getName());
            return ResponseEntity.ok()
                    .contentLength(fsResource.contentLength())
                    .contentType(MediaType.parseMediaType(fsResource.getContentType()))
                    .body(new InputStreamResource(fsResource.getInputStream()));

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity deleteMusic(@PathVariable("id") String id, Principal principal) {
        fileService.delete(id,principal.getName());
        return ResponseEntity.ok("Music deleted");
    }
}
