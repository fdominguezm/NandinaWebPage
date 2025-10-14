package com.nandina.api.services.implementations;


import com.nandina.api.models.Post;
import com.nandina.api.models.User;
import com.nandina.api.repositories.interfaces.FeedRepository;
import com.nandina.api.services.interfaces.FeedService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;

    @Override
    public List<Post> getFeed(User user, int page, int pageSize) {
        return feedRepository.getFeed(user, page, pageSize);
    }
}
