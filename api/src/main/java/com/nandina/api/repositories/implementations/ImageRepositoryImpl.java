package com.nandina.api.repositories.implementations;


import com.nandina.api.models.Image;
import com.nandina.api.repositories.interfaces.ImageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class ImageRepositoryImpl implements ImageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Image save(Image image) {
        return entityManager.merge(image);
    }

    @Override
    public Optional<Image> getImage(Long imageId) {
        return entityManager.createQuery("FROM Image where id = :id", Image.class)
                .setParameter("id", imageId)
                .getResultStream().findFirst();
    }
}
