package mainPackage;

import anotherPackage.GuestsList;
import anotherPackage.Guest;
import java.util.Scanner;

public class Main {

	private static Scanner sc = new Scanner(System.in);
	private static GuestsList guestList = new GuestsList(3);

	private static void print() {
		System.out.println("help         - Afiseaza aceasta lista de comenzi\r\n"
				+ "add          - Adauga o noua persoana (inscriere)\r\n"
				+ "check        - Verifica daca o persoana este inscrisa la eveniment\r\n"
				+ "remove       - Sterge o persoana existenta din lista\r\n"
				+ "update       - Actualizeaza detaliile unei persoane\r\n"
				+ "guests       - Lista de persoane care participa la eveniment\r\n"
				+ "waitlist     - Persoanele din lista de asteptare\r\n" + "available    - Numarul de locuri libere\r\n"
				+ "guests_no    - Numarul de persoane care participa la eveniment\r\n"
				+ "waitlist_no  - Numarul de persoane din lista de asteptare\r\n"
				+ "subscribe_no - Numarul total de persoane inscrise\r\n"
				+ "search       - Cauta toti invitatii conform sirului de caractere introdus\r\n"
				+ "previousFile - Arata fisierul precedent\r\n" + "reset        - Reseteaza fisierul\r\n"
				+ "quit         - Inchide aplicatia");
	}

	private static void add() {
		System.out.println("Last name:"); 
		String lastName = sc.next();

		System.out.println("First name:");
		String firstName = sc.next();

		System.out.println("Email address:");
		String email = sc.next();

		System.out.println("Phone number:");
		String phone = sc.next();

		guestList.addPerson(new Guest(lastName, firstName, email, phone));
	}

	private static void check() {
		System.out.println("Search by (1): last name + first name, (2): email, (3) phone");
		int search = sc.nextInt();
		switch (search) {
		case 1: // name
			System.out.println("Introduceti numele:");
			String lastName = sc.next();

			System.out.println("Introduceti prenumele:");
			String firstName = sc.next();

			if (guestList.findByName(lastName, firstName) >= 0) {
				System.out.println("Persoana este inscrisa la eveniment.");
				System.out.println("Persoana nu este inscrisa la eveniment.");
			}
			break;
		case 2: // email
			System.out.println("Introduceti email-ul:");
			String email = sc.next();

			if (guestList.findByEmail(email) >= 0) {
				System.out.println("Persoana este inscrisa la eveniment.");
			} else {
				System.out.println("Persoana nu este inscrisa la eveniment.");
			}
			break;
		case 3: // phone
			System.out.println("Introduceti numarul de telefon:");
			String phone = sc.next();

			if (guestList.findByPhone(phone) >= 0) {
				System.out.println("Persoana este inscrisa la eveniment.");
			} else {
				System.out.println("Persoana nu este inscrisa la eveniment.");
			}
			break;
		default:
			System.out.println("Persoana nu este inscrisa la eveniment.");
		}
	}

	private static void remove() {
		System.out.println("Search by (1): last name + first name, (2): email, (3) phone");
		int search = sc.nextInt();
		switch (search) {
		case 1: // name
			System.out.println("Introduceti numele:");
			String lastName = sc.next();

			System.out.println("Introduceti prenumele:");
			String firstName = sc.next();

			guestList.removeFullName(lastName, firstName);
			break;
		case 2: // email
			System.out.println("Introduceti email-ul:");
			String email = sc.next();
			guestList.removeByEmail(email);
			break;
		case 3: // phone
			System.out.println("Introduceti numarul de telefon:");
			String phone = sc.next();
			guestList.removeByPhoneNumber(phone);
			break;
		}
	}

	private static void update() {

		System.out.println("By (1):  nume, (2): prenume (3): email, (4): phone");
		int search = sc.nextInt();
		switch (search) {
		case 1:
			System.out.println("Introduceti numele.");
			String oldLastName = sc.next();
			System.out.println("Introduceti prenumele.");
			String oldFirstName = sc.next();
			System.out.println("Introduceti numele nou.");
			String newLastName = sc.next();
			guestList.updateLastName(oldLastName, oldFirstName, newLastName);
			break;
		case 2:
			System.out.println("Introduceti numele.");
			String oldLastName2 = sc.next();
			System.out.println("Introduceti prenumele.");
			String oldFirstName2 = sc.next();
			System.out.println("Introduceti prenumele nou. ");
			String newFirstName = sc.next();
			guestList.updateFirstName(oldLastName2, oldFirstName2, newFirstName);
			break;
		case 3:
			System.out.println("Introduceti email-ul pe care doriti sa il modificati.");
			String oldEmail = sc.next();
			System.out.println("Introduceti email-ul nou.");
			String newEmail = sc.next();
			guestList.updateEmail(oldEmail, newEmail);
			System.out.println("Email a fost modificat!");
			break;
		case 4:
			System.out.println("Introduceti numarul de telefon pe care doriti sa il modificati.");
			String oldPhoneNumber = sc.next();
			System.out.println("Introduceti numarul de telefon nou.");
			String newPhoneNumber = sc.next();
			guestList.updatePhone(oldPhoneNumber, newPhoneNumber);
			System.out.println("Numarul de telefon a fost modificat!");
			break;
		}
	}

	private static void guests() {
		guestList.getMainList();
	}

	private static void waitlist() {
		guestList.getWaitList();
	}

	private static void available() {
		System.out.println(guestList.freeSeats());
	}

	private static void guestsNo() {
		System.out.println(guestList.nrPersons());
	}

	private static void waitlistNo() {
		System.out.println(guestList.nrPersonsWaiting());
	}

	private static void subscribeNo() {
		System.out.println(guestList.totalPersons());
	}

	private static void search() {
		System.out.println("Introduceti sirul de caractere:");
		System.out.println(guestList.search(sc.next()));
	}

	private static void previous() {
		guestList.printSaved();
	}

	private static void reset() {
		guestList.reset();
	}

	public static void main(String[] args) {

		String select = "";
		System.out.println("Bun venit! Apasati \"help\" pentru a vedea meniul de comanda.");

		while (!select.equalsIgnoreCase("quit")) {
			select = sc.next();
			// select = select.toLowerCase();

			switch (select) {
			case "help":
				print();
				break;
			case "add":
				add();
				break;
			case "check":
				check();
				break;
			case "remove":
				remove();
				break;
			case "update":
				update();
				break;
			case "guests":
				guests();
				break;
			case "waitlist":
				waitlist();
				break;
			case "available":
				available();
				break;
			case "guestsNo":
				guestsNo();
				break;
			case "waitlistNo":
				waitlistNo();
				break;
			case "subscribeNo":
				subscribeNo();
				break;
			case "search":
				search();
				break;
			case "previousFile":
				previous();
				break;
			case "reset":
				reset();
				break;
			case "quit":
				guestList.save();
				break;
			}
		}
	}
}
