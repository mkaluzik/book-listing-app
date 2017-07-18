package com.example.martinkaluzik.book_listing_app;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Book {


    private String name;
    private String author;
    private String imageUrl;

    private static final String BOOK_NODE_NAME = "volumeInfo";
    private static final String IMAGES_NODE_NAME = "imageLinks";
    private static final String IMAGES_NODE_AUTHORS = "authors";
    private static final String TITLE_ELEMENT_NAME = "title";
    private static final String IMAGE_ELEMENT_NAME = "smallThumbnail";

    public Book() {
    }

    public Book(String name, String author, String imageId) {
        this.name = name;
        this.author = author;
        this.imageUrl = imageId;
    }

    // getters

    public String getName() {
        return name;
    }

    public String getAuthorName() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // fromJson returns books based on JSON data
    public static Book fromJson(JSONObject jsonObject) {
        Book book = new Book();
        JSONObject volumeInfo = null;
        JSONObject images = null;

        try {
            volumeInfo = jsonObject.getJSONObject(BOOK_NODE_NAME);
            images = volumeInfo.getJSONObject(IMAGES_NODE_NAME);

            book.name = volumeInfo.has(TITLE_ELEMENT_NAME) ? volumeInfo.getString(TITLE_ELEMENT_NAME) : "";
            book.author = getAuthorName(volumeInfo);
            if (images.has(IMAGE_ELEMENT_NAME)) {
                book.imageUrl = images.getString(IMAGE_ELEMENT_NAME);
            }

            //catch JSON exception
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return book;
    }

    // If there is more than one result returned separate those by comma
    private static String getAuthorName(final JSONObject jsonObject) {
        try {
            final JSONArray authors = jsonObject.getJSONArray(IMAGES_NODE_AUTHORS);
            int numAuthors = authors.length();
            final String[] authorStrings = new String[numAuthors];
            for (int i = 0; i < numAuthors; ++i) {
                authorStrings[i] = authors.getString(i);
            }
            return TextUtils.join(", ", authorStrings);
        } catch (JSONException e) {
            return "";
        }
    }

    // Decodes array of book json results into business model objects
    public static ArrayList<Book> fromJson(JSONArray jsonArray) {
        ArrayList<Book> books = new ArrayList<Book>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject bookJson = null;
            try {
                bookJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Book book = Book.fromJson(bookJson);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }
}
