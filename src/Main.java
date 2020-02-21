import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Main {

    static ArrayList<Book> books = new ArrayList();
    static PriorityQueue<Library> libraries = new PriorityQueue<>();
    static ArrayList<Library> signedUpLibraries = new ArrayList<>();
    static int countLibary;
    static int countBooks;
    static int days;

    private static String a = "a_example.txt";
    private static String b = "b_read_on.txt";
    private static String c = "c_incunabula.txt";
    private static String d = "d_tough_choices.txt";
    private static String e = "e_so_many_books.txt";
    private static String f = "f_libraries_of_the_world.txt";

    public static int remainingDays;

    public static void main(String[] args) throws Exception {
        String data = e;

        readInput(data);

        Library currentLibrary = libraries.poll();
        int daysInSignUp = 0;

        for (int i = 0; i < days; i++, daysInSignUp++) {
            remainingDays = days - i;

            if (currentLibrary != null && daysInSignUp == currentLibrary.signUpDays) {
                signedUpLibraries.add(currentLibrary);
                daysInSignUp = 0;

                //resort libaries
                PriorityQueue<Library> helper = new PriorityQueue<>();
                Library helperElement;
                while ((helperElement = libraries.poll()) != null) {
                    helper.add(helperElement);
                }
                libraries = helper;

                /*signedUpLibraries.forEach(l -> l.simulate());*/

                currentLibrary = libraries.poll();

                /*while (currentLibrary.isEmpty()) {
                    currentLibrary = libraries.poll();

                    signedUpLibraries.forEach(l -> l.simulate());
                }

                signedUpLibraries.forEach(l -> l.undoSimulate());*/
            }

            for (int j = 0; j < signedUpLibraries.size(); j++) {
                signedUpLibraries.get(j).ScanBooks();
            }
        }

        System.out.println("signed "+signedUpLibraries.size());

        int removedLibs = 0;
        for (int i = 0; i < signedUpLibraries.size(); i++) {
            if (signedUpLibraries.get(i).scannedBooks.isEmpty()) {
                removedLibs++;
                signedUpLibraries.remove(i);
                i--;
            }
        }

        System.out.println(removedLibs);

        writeOutput(data);
    }

    private static void readInput(String data) throws Exception {
        String file = "./data/" + data;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            //first Line general data
            line = br.readLine();
            String[] s = line.split(" ");
            countBooks = Integer.parseInt(s[0]);
            countLibary = Integer.parseInt(s[1]);
            days = Integer.parseInt(s[2]);

            //second line books
            line = br.readLine();
            s = line.split(" ");
            int perfectScore = 0;
            for (int i = 0; i < s.length; i++) {
                perfectScore += Integer.parseInt(s[i]);
                Book b = new Book(i, Integer.parseInt(s[i]));
                books.add(b);
            }

            System.out.println("Perfect Score: " + perfectScore);
            System.out.println("Perfect Score average: " + perfectScore / books.size());

            int days = 0;

            for (int i = 0; i < countLibary; i++) {
                line = br.readLine();
                s = line.split(" ");

                Library l = new Library(i, Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                libraries.add(l);
                days += l.signUpDays;

                line = br.readLine();
                s = line.split(" ");

                for (int j = 0; j < s.length; j++) {
                    l.addBook(books.get(Integer.parseInt(s[j])));
                }
            }

            System.out.println(days / countLibary);


            if ((line = br.readLine()) != null) {
                throw new Exception("more lines");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeOutput(String data) {
        String file = "./output/output-" + data;
        try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
            br.write(""+signedUpLibraries.size() + "\n");

            int score = 0;
            for (int i = 0; i < signedUpLibraries.size(); i++) {
                Library l = signedUpLibraries.get(i);
                br.write(""+l.id+" "+l.scannedBooks.size() + "\n");

                for (int j = 0; j < l.scannedBooks.size(); j++) {
                    score += books.get(l.scannedBooks.get(j)).value;
                }

                String listString = l.scannedBooks.stream().map(Object::toString)
                        .collect(Collectors.joining(" "));
                if (!listString.isEmpty()) {
                    br.write(listString + "\n");
                }
            }
            System.out.println("Reached Score: " + score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
