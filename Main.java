import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CDOrganizer organizer = new CDOrganizer();

        Scanner kbd = new Scanner(System.in);
        System.out.print("Please enter a filename to create a new CD Collection or load an existing one: ");
        String filename = kbd.nextLine();

        File CDfile = new File(filename);

        if (CDfile.exists() && !CDfile.isDirectory()) {
            organizer.loadCDfile(filename);
            organizer.viewCDs();
        } else {
            try {
                CDfile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating a new file: " + e.getMessage());
                return;
            }
            System.out.println();
            System.out.println("There are no CDs in this collection.");
            System.out.println();
        }

        int choice = 0;
        boolean saved = true;

        while (true) {
            System.out.println("CD Organizer -- Select an option: ");
            System.out.println("1. Enter a New CD");
            System.out.println("2. View all CDs");
            System.out.println("3. Search for CD");
            System.out.println("4. Delete a CD");
            System.out.println("5. Save CD Collection to Disk");
            System.out.println("6. Exit");

            try {
                choice = kbd.nextInt();
                kbd.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println(); // Add a blank line for separation
                System.out.println("Invalid input. Please enter a valid menu option (1-6).");
                System.out.println(); // Add a blank line for separation
                kbd.nextLine(); // Consume the invalid input
                continue;
            }

            switch (choice) {
                case 1: // Insert CD to collection
                    System.out.print("Enter the artist: ");
                    String artist = kbd.nextLine();

                    System.out.print("Enter the title: ");
                    String title = kbd.nextLine();

                    System.out.print("Enter the year of the CD: ");
                    int year = 0;
                    try {
                        year = kbd.nextInt();
                        kbd.nextLine();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println();
                        System.out.println("Invalid input. Please enter a valid year.");
                        System.out.println();
                        kbd.nextLine();
                        continue;
                    }

                    organizer.loadAndDisplayGenres("genre.txt");
                    System.out.print("Enter the genre number: ");
                    int genre = 0;
                    try {
                        genre = kbd.nextInt();
                        kbd.nextLine();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println();
                        System.out.println("Invalid input. Please enter a valid genre number.");
                        System.out.println();
                        kbd.nextLine();
                        continue;
                    }

                    organizer.insertCD(artist, title, genre, year);
                    System.out.println(); // skip a line
                    saved = false;
                    break;
                case 2: // View List of CDs
                    organizer.viewCDs();
                    break;
                case 3: // Search CDs by Artist or Genre
                    int searchNum = 0;
                    System.out.print("Search by (1) Artist or (2) Genre? ");

                    try {
                        searchNum = kbd.nextInt();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println();
                        System.out.println("Invalid input. Please enter a valid menu option (1-2).");
                        System.out.println();
                        kbd.nextLine();
                    }

                    String searchKeyword = null;
                    int genreCode = 0;

                    if (searchNum == 1) { // Search by artist
                        kbd.nextLine(); // Consume search number
                        System.out.print("Enter the artist you would like to search: ");
                        searchKeyword = kbd.nextLine();

                        organizer.searchCDbyArtist(searchKeyword);

                    } else if (searchNum == 2) { // Search by genre
                        kbd.nextLine(); // Consume search number
                        organizer.loadAndDisplayGenres("genre.txt");
                        System.out.print("Enter the genre number you would like to search: ");
                        genreCode = 0;

                        try {
                            genreCode = kbd.nextInt();
                            kbd.nextLine();
                        } catch (java.util.InputMismatchException e) {
                            System.out.println();
                            System.out.println("Invalid input. Please enter a valid genre number.");
                            System.out.println();
                            kbd.nextLine();
                            continue;
                        }

                        organizer.searchByGenreCode(genreCode);

                    } else if (searchNum > 2) {
                        System.out.println();
                        System.out.println("Invalid input. Please enter a valid menu option (1-2).");
                        System.out.println();
                        kbd.nextLine();
                    }
                    break;
                case 4: // Delete a CD
                    System.out.println("Enter the artist and title of the CD you would like to delete.");
                    System.out.print("Artist: ");
                    String delArtist = kbd.nextLine();

                    System.out.print("Title: ");
                    String delTitle = kbd.nextLine();

                    organizer.deleteCD(delArtist, delTitle);
                    saved = false;
                    break;
                case 5: // Save CD Collection to Disk
                    organizer.saveToCDFile(filename);
                    saved = true;
                    break;
                case 6: // Exit the program
                    if (saved) {
                        System.out.println();
                        System.out.println("Exiting CD Organizer...");
                    } else {
                        System.out.println("**WARNING**  Your CD collection has changed since you last saved to disk.");
                        System.out.print("Save your changes? (y/n): ");
                        String save = kbd.nextLine();
                        if (save.equalsIgnoreCase("y")) {
                            organizer.saveToCDFile(filename);
                        }
                        System.out.println();
                        System.out.println("Exiting CD Organizer...");
                    }
                    return;
                default:
                    System.out.println();
                    System.out.println("Invalid input. Please enter a valid menu option (1-6).");
                    System.out.println();
                    break;
            }
        }
    }
}