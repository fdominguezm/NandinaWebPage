package com.nandina.api.repositories.interfaces;


import com.nandina.api.models.Post;
import com.nandina.api.models.User;

import java.util.List;

public interface FeedRepository {
    List<Post> getFeed(User user, int page, int pageSize);
}
