package com.nandina.api.repositories.interfaces;


import com.nandina.api.models.Image;

import java.util.Optional;

public interface ImageRepository {
    Image save(Image image);
    Optional<Image> getImage(Long imageId);
}
