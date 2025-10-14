package com.nandina.api.services.interfaces;


import com.nandina.api.models.Image;

import java.util.Optional;

public interface ImageService {
    Image create (byte[] bytes);
    Optional<Image> getImage(Long imageId);
}

