
package org.example.javalab3.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {

    private final StringProperty title;
    private final StringProperty binding;
    private final StringProperty publisher;
    private final IntegerProperty publicationYear;
    private final StringProperty genre;
    private Author author;

    public Book(
            String title,
            String binding,
            String publisher,
            int publicationYear,
            String genre,
            Author author) {

        this.title = new SimpleStringProperty(title);
        this.binding = new SimpleStringProperty(binding);
        this.publisher = new SimpleStringProperty(publisher);
        this.publicationYear = new SimpleIntegerProperty(publicationYear);
        this.genre = new SimpleStringProperty(genre);
        this.author = author;
    }

    public Book() {
        this("", "", "", 2026, "", new Author("", "", "", 0.0));
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getBinding() {
        return binding.get();
    }

    public void setBinding(String binding) {
        this.binding.set(binding);
    }

    public StringProperty bindingProperty() {
        return binding;
    }

    public String getPublisher() {
        return publisher.get();
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
    }

    public StringProperty publisherProperty() {
        return publisher;
    }

    public int getPublicationYear() {
        return publicationYear.get();
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear.set(publicationYear);
    }

    public IntegerProperty publicationYearProperty() {
        return publicationYear;
    }

    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
