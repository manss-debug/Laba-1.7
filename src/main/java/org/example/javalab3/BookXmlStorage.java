
package org.example.javalab3;

import org.example.javalab3.model.Author;
import org.example.javalab3.model.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookXmlStorage {

    public static void save(List<Book> books, File file) throws Exception {
        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element root = document.createElement("catalog");
        document.appendChild(root);

        for (Book book : books) {
            Element bookElement = document.createElement("book");
            root.appendChild(bookElement);

            addElement(document, bookElement, "title", book.getTitle());
            addElement(document, bookElement, "binding", book.getBinding());
            addElement(document, bookElement, "publisher", book.getPublisher());
            addElement(document, bookElement, "publicationYear",
                    String.valueOf(book.getPublicationYear()));
            addElement(document, bookElement, "genre", book.getGenre());

            Author author = book.getAuthor();

            Element authorElement = document.createElement("author");
            bookElement.appendChild(authorElement);

            addElement(document, authorElement, "fullName", author.getFullName());
            addElement(document, authorElement, "phone", author.getPhone());
            addElement(document, authorElement, "email", author.getEmail());
            addElement(document, authorElement, "rating",
                    String.valueOf(author.getRating()));
        }

        var transformer = TransformerFactory.newInstance()
                .newTransformer();

        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(
                "{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(
                new DOMSource(document),
                new StreamResult(file)
        );
    }

    public static List<Book> load(File file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl", true);

        Document document = factory.newDocumentBuilder().parse(file);
        document.getDocumentElement().normalize();

        List<Book> books = new ArrayList<>();
        NodeList nodes = document.getDocumentElement()
                .getElementsByTagName("book");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);

            String title = getText(element, "title");
            String binding = getText(element, "binding");
            String publisher = getText(element, "publisher");
            int year = Integer.parseInt(getText(element, "publicationYear"));
            String genre = getText(element, "genre");

            Element authorElement =
                    (Element) element.getElementsByTagName("author").item(0);

            Author author = new Author(
                    getText(authorElement, "fullName"),
                    getText(authorElement, "phone"),
                    getText(authorElement, "email"),
                    Double.parseDouble(getText(authorElement, "rating"))
            );

            books.add(new Book(
                    title, binding, publisher, year, genre, author));
        }

        return books;
    }

    private static void addElement(
            Document document,
            Element parent,
            String name,
            String value) {

        Element element = document.createElement(name);
        element.setTextContent(value == null ? "" : value);
        parent.appendChild(element);
    }

    private static String getText(Element parent, String tag) {
        return parent.getElementsByTagName(tag)
                .item(0).getTextContent();
    }
}
