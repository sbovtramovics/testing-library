package app;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        String databaseUrl = "jdbc:mysql://localhost/library";

        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        ((JdbcConnectionSource)connectionSource).setUsername("root");
        ((JdbcConnectionSource)connectionSource).setPassword("");

        TableUtils.createTableIfNotExists(connectionSource, Book.class);

        Dao<Book,String> bookDao = DaoManager.createDao(connectionSource, Book.class);



       get("/books/:id", new Route() {
           @Override
           public Object handle(Request request, Response response) throws SQLException {
               Book book = bookDao.queryForId(request.params(":id"));
               if (book != null) {
                   String bookJson = new Gson().toJson(book);
                   return bookJson;
               } else {
                   response.status(404);
                   return "Book not found";
               }
           }
        });

        get("/books", new Route() {
            @Override
            public Object handle(Request request, Response response) throws SQLException {
                ArrayList<Book> book = (ArrayList<Book>) bookDao.queryForAll();
                String booksJson = new Gson().toJson(book);
                if (book != null) {
                    return booksJson;
                } else {
                    response.status(404);
                    return "Books not found";
                }
            }
        });

        post("/books", new Route() {
            @Override
            public Object handle(Request request, Response response) throws SQLException {
                String name = request.queryParams("name");
                String author = request.queryParams("author");
                String year = request.queryParams("year");
                String available = request.queryParams("available");

                Book book = new Book();
                book.setName(name);
                book.setAuthor(author);
                book.setYear(year);
                book.setAvailable(available);

                bookDao.create(book);
                String booksJson = new Gson().toJson(book);
                response.status(201);
                return booksJson;
            }
        });

        put("/books/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) throws SQLException {
                String name = request.queryParams("name");
                String author = request.queryParams("author");
                String year = request.queryParams("year");
                String available = request.queryParams("available");

                Book book = bookDao.queryForId(request.params(":id"));
                book.setName(name);
                book.setAuthor(author);
                book.setYear(year);
                book.setAvailable(available);
                bookDao.update(book);
                response.status(201);
                return "Book is updated";
            }
        });

        delete("/books/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) throws SQLException {
                Book book = bookDao.queryForId(request.params(":id"));
                bookDao.delete(book);
                response.status(201);
                return "Book is removed";
            }
        });
    }
}