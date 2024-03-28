import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CDOrganizer {
    private CDLink head;

    /**
     * Constructor to initialize the CD Organizer
     */
    public CDOrganizer() {
        this.head = null;
    }

    private class CDLink {
        private String artist;
        private String title;
        private int genre;
        private int year;
        private CDLink n;

        // Constructor
        public CDLink(String artist, String title, int genre, int year, CDLink inn) {
            this.artist = artist;
            this.title = title;
            this.genre = genre;
            this.year = year;
            this.n = null;
        }

        public void setNext(CDLink inn) {
            n = inn;
        }

        @Override
        public String toString() {
            return artist.toUpperCase() + ": " + title.toUpperCase() + " - " + genre + ", " + year;
        }
    }

    /**
     * Load CDs from a file and add them to the collection.
     *
     * @param fileName The name of the file to load from.
     */
    public void loadCDfile(String fileName) {
        try {
            Scanner inFile = new Scanner(new File(fileName));
            System.out.println("CD collection loaded from " + fileName);

            while (inFile.hasNextLine()) {
                String line = inFile.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    // Parse the CD information and add it to the collection
                    String artist = parts[0];
                    String title = parts[1];
                    int genre = Integer.parseInt(parts[2]);
                    int year = Integer.parseInt(parts[3]);

                    insertCD(artist, title, genre, year);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing data from the file: " + e.getMessage());
        }
    }

    /**
     * Method to insert a CD into the linked list at the appropriate position.
     *
     * @param artist The artist's name.
     * @param title  The CD title.
     * @param genre  The genre code.
     * @param year   The production year.
     */
    public void insertCD(String artist, String title, int genre, int year) {
        CDLink newCD = new CDLink(artist, title, genre, year, head);

        // If the list is empty or the new CD should be inserted at the beginning
        if (head == null || artist.compareToIgnoreCase(head.artist) < 0 || (artist.compareToIgnoreCase(head.artist) == 0 && year > head.year)) {
            newCD.n = head;
            head = newCD;
            return;
        }

        CDLink current = head;
        CDLink previous = null;

        // Traverse the list to find the correct position for insertion
        while (current != null && (artist.compareToIgnoreCase(current.artist) > 0 || (artist.compareToIgnoreCase(current.artist) == 0 && year < current.year))) {
            previous = current;
            current = current.n;
        }

        // Insert the new CD at the appropriate position
        previous.n = newCD;
        newCD.n = current;
    }

    /**
     * Load and display genres from a file.
     *
     * @param filename The name of the genre file.
     */
    public void loadAndDisplayGenres(String filename) {
        try {
            Scanner inFile = new Scanner(new File(filename));

            System.out.println("Available Genres:");
            while (inFile.hasNextLine()) {
                int genreCode = inFile.nextInt();
                String genreName = inFile.nextLine().trim();
                System.out.println(genreCode + ". " + genreName);
            }

            inFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to display all CDs in the order they appear in the list.
     */
    public void viewCDs() {
        int countCD = 0;
        if (head == null) {
            System.out.println();
            System.out.println("No CDs in the collection.");
            System.out.println();
            return;
        }

        System.out.println();
        System.out.println("List of CDs:");
        System.out.println("Artist Title Genre Year");

        CDLink current = head;
        while (current != null) {
            System.out.println(current.toString());
            current = current.n;
            countCD++;
        }
        System.out.println();
        System.out.println(countCD + " total CDs.");
        System.out.println();
    }

    /**
     * Method to search for a CD by artist.
     *
     * @param searchKeyword The keyword to search for artist.
     */
    public void searchCDbyArtist(String searchKeyword) {
        int countCD = 0;
        if (head == null) {
            System.out.println();
            System.out.println("No CDs in the collection.");
            System.out.println();
            return;
        }

        // Convert the search keyword to upper case for case-insensitive matching
        searchKeyword = searchKeyword.toUpperCase();

        System.out.println();
        System.out.println("Search Results:");
        System.out.println("Artist Title Genre Year");

        CDLink current = head;
        boolean noMatchesFound = true;
        while (current != null) {
            String artist = current.artist.toUpperCase();

            // Check if the artist contains the search keyword
            if (artist.contains(searchKeyword)) {
                System.out.println(current.toString());
                noMatchesFound = false;
                countCD++;
            }

            current = current.n;
        }

        // Inform the user if matches were found
        if (noMatchesFound) {
            System.out.println();
            System.out.println("No matching CDs found.");
            System.out.println();
        } else {
            System.out.println();
            System.out.println(countCD + " CDs found.");
            System.out.println();
        }
    }

    /**
     * Method to search for CDs by genre code.
     *
     * @param genreCode The genre code to search for.
     */
    public void searchByGenreCode(int genreCode) {
        int countCD = 0;
        if (head == null) {
            System.out.println();
            System.out.println("No CDs in the collection.");
            System.out.println();
            return;
        }

        System.out.println();
        System.out.println("Search Results:");
        System.out.println("Artist Title Genre Year");

        CDLink current = head;
        boolean noMatchesFound = true;

        while (current != null) {
            if (current.genre == genreCode) {
                System.out.println(current.toString());
                noMatchesFound = false;
                countCD++;
            }

            current = current.n;
        }

        // Inform the user if matches were found
        if (noMatchesFound) {
            System.out.println();
            System.out.println("No CDs with genre number " + genreCode + " found.");
            System.out.println();
        } else {
            System.out.println();
            System.out.println(countCD + " CDs found.");
            System.out.println();
        }
    }

    /**
     * Delete a CD using artist and title.
     *
     * @param delArtist The Artist of the CD to be deleted.
     * @param delTitle The Title of the CD to be deleted.
     */
    public void deleteCD(String delArtist, String delTitle) {
        if (head == null) {
            System.out.println();
            System.out.println("No CDs in the collection.");
            System.out.println();
            return;
        }

        CDLink current = head;
        CDLink previous = null;
        boolean deleted = false;

        while (current != null) {
            if (current.artist.equalsIgnoreCase(delArtist) && current.title.equalsIgnoreCase(delTitle)) {
                if (previous == null) {
                    // If the match is at the head of the list
                    head = current.n;
                } else {
                    previous.n = current.n;
                }
                deleted = true;
            }

            previous = current;
            current = current.n;
        }

        if (deleted) {
            System.out.println();
            System.out.println("CD deleted successfully.");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("CD not found or deletion failed. Please try again.");
            System.out.println();
        }
    }

    /**
     * Save the CD collection to the specified file.
     *
     * @param fileName The name of the file to save to.
     */
    public void saveToCDFile(String fileName) {
        try (PrintWriter output = new PrintWriter(new File(fileName))) {
            CDLink current = head;
            while (current != null) {
                // Write CD information to the file
                output.println(current.artist + "," + current.title + "," + current.genre + "," + current.year);
                current = current.n;
            }
            System.out.println("CD collection successfully saved to " + fileName);

        } catch (FileNotFoundException e) {
            System.out.println("Error saving the CD collection to the file: " + e.getMessage());
        }
    }
}

