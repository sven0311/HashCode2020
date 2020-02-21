import java.util.Comparator;

public class Book implements Comparable<Book> {
    int id;
    int value;
    boolean isScanned = false;

    public Book(int id, int value) {
        this.id = id;
        this.value = value;
        this.isScanned = false;
    }

    @Override
    public int compareTo(Book book) {
        if (value < book.value) {
            return 1;
        }
        return -1;
    }
}
