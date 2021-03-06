package io.github.satoruk.servlet.checker;

import static org.apache.commons.io.FilenameUtils.getBaseName;
import static org.apache.commons.io.FilenameUtils.getExtension;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.servlet.SparkApplication;


import java.util.*;
import java.text.*;

public class ServletChecker implements SparkApplication {
  @Override
  public void init() {

    Spark.get(new Route("/now.txt") {
      @Override
      public Object handle(Request request, Response response) {
        Date date1 = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat();
        sdf1.applyPattern("yyyy-MM-dd HH:mmZ");
        response.type("text/plain");
        return sdf1.format(date1);
      }
    });

    Spark.get(new Route("/info.txt") {
      @Override
      public Object handle(Request request, Response response) {
        response.type("text/plain");
        StringBuilder sb = new StringBuilder();

        Date date1 = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat();
        sdf1.applyPattern("yyyy-MM-dd HH:mmZ");
        sb.append(sdf1.format(date1));
        sb.append("\n");

        sb.append("[HEADERS]\n");
        for (String name : request.headers()) {
          sb.append(name);
          sb.append(":");
          sb.append(request.headers(name));
          sb.append("\n");
        }

        return sb;
      }
    });

    Spark.get(new Route("/") {
      @Override
      public Object handle(Request request, Response response) {
        response.redirect("/hello");
        return null;
      }
    });

    Spark.get(new Route("/hello") {
      @Override
      public Object handle(Request request, Response response) {
        return "Hello World!";
      }
    });

    Spark.get(new Route("/hello/:name") {
      @Override
      public Object handle(Request request, Response response) {
        response.type("text/plain");
        String name = getBaseName(request.params(":name"));
        String ext = getExtension(request.params(":name"));
        return  String.format("Hello1, %s! [%s]", name, ext);
      }
    });

  }
}
