package com.nandina.api.services.implementations;


import com.nandina.api.models.Image;
import com.nandina.api.repositories.interfaces.ImageRepository;
import com.nandina.api.services.interfaces.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image create(byte[] bytes) {
        Image image = new Image();
        image.setImage(bytes);
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> getImage(Long imageId) {
        return imageRepository.getImage(imageId);
    }
}

