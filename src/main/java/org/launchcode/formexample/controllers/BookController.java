package org.launchcode.formexample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    // this is the container holding all of the books
    public static ArrayList<HashMap<String, String>> books = new ArrayList<>();

    // GET /book -> returns a JSON List of all the books
    @GetMapping
    @ResponseBody
    public ArrayList<HashMap<String, String>> getBooks() {
        return books;
    }

    // GET /book/new -> returns an HTML form
    @GetMapping(value = "/new")
    @ResponseBody
    public String addBookForm() {
        String newBookForm = "<form action='/book/new' method='POST'>";
        newBookForm += "<label>Title: <input type='text' name='title'></label><br />";
        newBookForm += "<label>Author: <input type='text' name='author'></label><br />";
        newBookForm += "<label>ISBN: <input type='text' name='isbn'></label><br />";
        newBookForm += "<input type='submit'>";
        newBookForm += "</form>";
        return newBookForm;
    }

    // POST /book/new -> takes in three query parameters: title, author, isbn and creates a new book out of these query parameters (these were the inputs of the HTML form in the GET handler)
    @PostMapping(value = "/new")
    @ResponseBody
    public String addBook(@RequestParam String title, @RequestParam String author, @RequestParam String isbn) {
        HashMap<String, String> newBook = new HashMap<>();
        newBook.put("title", title);
        newBook.put("author", author);
        newBook.put("ISBN", isbn);
        addBook(newBook);
        return "OK";
    }

    // GET /book/author/authorName -> returns a JSON List of all the books matching the path variable authorName
    @GetMapping(value = "/author/{authorName}")
    @ResponseBody
    public ArrayList<HashMap<String, String>> getBooksByAuthor(@PathVariable String authorName) {
        ArrayList<HashMap<String, String>> matchingBooks = new ArrayList<>();
        for(HashMap<String, String> book : books) {
            if(book.get("author").equals(authorName)) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }

    // GET /book/title/titleName -> Returns a JSON List of all the books matching the path variable titleName
    @GetMapping(value = "/title/{titleName}")
    @ResponseBody
    public ArrayList<HashMap<String, String>> getBooksByTitle(@PathVariable String titleName) {
        ArrayList<HashMap<String, String>> matchingBooks = new ArrayList<>();
        for(HashMap<String, String> book : books) {
            if(book.get("title").equals(titleName)) {
                matchingBooks.add(book);
            }
        }
        return matchingBooks;
    }

    // a helper method that adds a new book to our static books property
    public static void addBook(HashMap<String, String> book) {
        books.add(book);
    }
}
