package com.seyijs.springbootmongodb.service;

import com.seyijs.springbootmongodb.collection.Photo;
import com.seyijs.springbootmongodb.repository.PhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public String addPhoto(String originalFilename, MultipartFile image) throws IOException {
        try{
            Photo photo = new Photo();

            photo.setTitle(originalFilename);
            photo.setPhoto(new Binary(BsonBinarySubType.BINARY,image.getBytes()));

            return photoRepository.save(photo).getId();

        }catch (Exception error){
            throw error;
        }
    }

    @Override
    public Photo getPhoto(String id) {
        return photoRepository.findById(id).get();
    }
}
