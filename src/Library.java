import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Library implements Comparable<Library>{
    int id;
    PriorityQueue<Book> books = new PriorityQueue<>();
    ArrayList<Book> booksList = new ArrayList<>();
    ArrayList<Integer> scannedBooks = new ArrayList<>();
    int signUpDays;
    int booksPerDay;

    ArrayList<Book> simulatedBooks = new ArrayList<>();

    public Library(int id, int signUpDays, int booksPerDay) {
        this.id = id;
        this.signUpDays = signUpDays;
        this.booksPerDay = booksPerDay;
    }

    public void addBook(Book book) {
        books.add(book);
        booksList.add(book);
    }

    public int getBookSum() {
        int sum = 0;
        for (int i = 0; i < books.size(); i++) {
            if (!booksList.get(i).isScanned) {
                sum += booksList.get(i).value;
            }
        }
        return sum;
    }

    public int teest() {
        int sum = 0;
        forLoop: for (int i = 0; i < (Main.remainingDays - signUpDays) * booksPerDay; i++) {
            Book b;
            while (true) {
                b = books.poll();

                if (b == null)
                    break forLoop;

                if (!b.isScanned)
                    break;
            }

            sum += b != null ? b.value : 0;
        }

        booksList.forEach(b -> books.add(b));

        return sum;
    }

    public void ScanBooks() {
        for (int i = 0; i < booksPerDay; i++) {
            Book b;
            while (true) {
                b = books.poll();

                if (b == null)
                    return;

                if (!b.isScanned)
                    break;
            }

            b.isScanned = true;
            scannedBooks.add(b.id);
        }
    }

    public void simulate() {
        for (int i = 0; i < booksPerDay; i++) {
            Book b;
            while (true) {
                b = books.poll();

                if (b == null)
                    return;

                if (!b.isScanned)
                    break;
            }

            b.isScanned = true;
            //scannedBooks.add(b.id);
            simulatedBooks.add(b);
        }
    }

    public void undoSimulate() {
        for (int i = 0; i < simulatedBooks.size(); i++) {
            simulatedBooks.get(i).isScanned = false;
            books.add(simulatedBooks.get(i));
        }

        simulatedBooks.clear();
    }

    public boolean isEmpty() {
        return booksList.stream().filter(book -> !book.isScanned).count() == 0L;
    }

    @Override
    public int compareTo(Library library) {
        if (teest() / signUpDays   < library.teest() / library.signUpDays ) {
            return 1;
        }
        return -1;

        //return signUpDays > library.signUpDays ? 1 : -1;
    }

    private float getCoeff(Library l) {
        long remainingBooks = l.booksList.stream().filter(book -> !book.isScanned).count();
        float daysNeeded = (remainingBooks * 1.0f) / l.booksPerDay;
        return daysNeeded;
    }
}

