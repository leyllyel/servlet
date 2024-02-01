package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
  private final Map<Long,Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong();
  public List all() {
    return new ArrayList<>(posts.values());
  }

  public Optional getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      post.setId(idCounter.incrementAndGet());
      posts.put(post.getId(), post);
    } else {
      posts.put(post.getId(), post);
    }
    return post;
  }

  public void removeById(long id) {
    posts.remove(id);
  }
}
