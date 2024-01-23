package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
  private final List<Post> posts = Collections.synchronizedList(new ArrayList<>());
  private final AtomicLong idCounter = new AtomicLong();
  public List<Post> all() {
    return new ArrayList<>(posts);
  }

  public Optional<Post> getById(long id) {
    return posts.stream()
            .filter(post -> post.getId() == id)
            .findFirst();
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      post.setId(idCounter.incrementAndGet());
      posts.add(post);
    } else {
      posts.removeIf(p -> p.getId() == post.getId());
      posts.add(post);
    }
    return post;
  }

  public void removeById(long id) {
    posts.removeIf(post -> post.getId() == id);
  }
}
