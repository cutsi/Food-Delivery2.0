package com.example.fooddelivery2_0.services;

import com.example.fooddelivery2_0.entities.Image;
import com.example.fooddelivery2_0.repos.ImageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {
    private ImageRepo imageRepo;
    public List<Image> getAllImages(){
        return imageRepo.findAll();
    }
    public Optional<Image> getImageById(Long id){
        return imageRepo.findById(id);
    }

    public void saveImage(Image image) {
        imageRepo.save(image);
    }
}
