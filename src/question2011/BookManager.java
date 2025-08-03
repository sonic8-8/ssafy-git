package question2011;

import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private static final int MAX_SIZE = 10;
    private Book[] books = new Book[MAX_SIZE];
    private int size;

    public BookManager() {
        new BookManager(10);
    }

    public BookManager(int size) {
        books = new Book[size];
    }

    public void add(Book book) {
        books[size++] = book;
    }

    public void remove(String isbn) {
        for (int i = 0; i < size; i++) {
            if (books[i].getIsbn().equals(isbn)) {
                books[i] = null;
            }
        }
    }

    public Book[] getList() {
        return books;
    }

    public Book searchByIsbn(String isbn) {
        for (int i = 0; i < size; i++) {
            if (books[i].getIsbn().equals(isbn)) {
                return books[i];
            }
        }
        return null;
    }

    public Book[] searchByTitle(String title) {
        List<Book> searchedBookList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (books[i].getTitle().contains(title)) {
                searchedBookList.add(books[i]);
            }
        }

        return searchedBookList.toArray(new Book[0]);
    }

    public Magazine[] getMagazines() {
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (books[i] instanceof Magazine) {
                count++;
            }
        }

        Magazine[] magazines = new Magazine[count];
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (books[i] instanceof Magazine) {
                magazines[index++] = (Magazine) books[i];
            }
        }

        return magazines;
    }

    public Book[] getBooks() {
        int count = size - getMagazines().length;
        Book[] searchedBooks = new Book[count];

        int index = 0;
        for (int i = 0; i < size; i++) {
            if (books[i] instanceof Magazine) {
                continue;
            }
            searchedBooks[index++] = books[i];
        }

        return searchedBooks;
    }

    public int getTotalPrice() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += books[i].getPrice();
        }
        return sum;
    }

    public double getPriceAvg() {
        int sum = getTotalPrice();
        return (double) sum / size;
    }
}
