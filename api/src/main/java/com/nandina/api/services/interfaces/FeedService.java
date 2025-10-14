package com.nandina.api.services.interfaces;


import com.nandina.api.models.Post;
import com.nandina.api.models.User;

import java.util.List;

public interface FeedService {

    List<Post> getFeed(User user, int page, int pageSize);
}
