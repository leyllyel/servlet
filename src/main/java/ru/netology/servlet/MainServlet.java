package ru.netology.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
  private PostController controller;
  private static final String PATH_ALL_POSTS = "/api/posts";
  private static final String PATH_POST_BY_ID = "/api/posts/\\d+";

    @Override
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext();
        controller = context.getBean(PostController.class);
    }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      // primitive routing
      if (method.equals("GET") && path.equals(PATH_ALL_POSTS)) {
        controller.all(resp);
        return;
      }
      if (method.equals("GET") && path.matches(PATH_POST_BY_ID)) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        controller.getById(id, resp);
        return;
      }
      if (method.equals("POST") && path.equals(PATH_ALL_POSTS)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals("DELETE") && path.matches(PATH_POST_BY_ID)) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}

