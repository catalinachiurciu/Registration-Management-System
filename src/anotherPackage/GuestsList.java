package anotherPackage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GuestsList implements Serializable {

	private static final long serialVersionUID = 1L;
	private int nrMaxLocuri;
	private ArrayList<Guest> listaParticipanti;
	private ArrayList<Guest> listaAsteptare;

	public GuestsList(int nrMaxLocuri) {
		try {
			read();
			if(this.listaParticipanti == null) {
				this.nrMaxLocuri = nrMaxLocuri;
				listaParticipanti = new ArrayList<Guest>();
				listaAsteptare = new ArrayList<Guest>();
			} else if (this.listaAsteptare == null) {
				listaAsteptare = new ArrayList<Guest>();
			} else {
				System.out.println("Data saved from previous file.");
			}
		} catch (FileNotFoundException e) {
			this.nrMaxLocuri = nrMaxLocuri;
			listaParticipanti = new ArrayList<Guest>();
			listaAsteptare = new ArrayList<Guest>();
		} catch (IOException e) {
			System.out.println("Exception!");
		}
	}

	// 2. Daca o persoana e inscrisa la eveniment in oricare lista
	private boolean onMainList(Guest guest) {
		if (!listaParticipanti.contains(guest)) {
			return false;
		}
		return true;
	}

	private boolean onWaitingList(Guest guest) {
		if (!listaAsteptare.contains(guest)) {
			return false;
		}
		return true;
	}

	public boolean isSignedUpForEvent(Guest guest) {
		return onMainList(guest) || onWaitingList(guest);
	}

	// 1. Adaugarea unei noi persoane
	public int addPerson(Guest guest) {
		if (onMainList(guest)) {
			return -1;
		}

		if (!onMainList(guest) && listaParticipanti.size() < this.nrMaxLocuri) {
			listaParticipanti.add(guest);
			System.out.println(guest.getFirstName() + " " + guest.getLastName()
					+ " Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
			return 0;
		}

		listaAsteptare.add(guest);
		System.out.println("Te-ai inscris cu succes in lista de asteptare si ai primit numarul de ordine "
				+ listaAsteptare.indexOf(guest) + 1 + ". Te vom notifica daca un loc devine disponibil.");
		return listaAsteptare.indexOf(guest);
	}

	// 3. Eliminarea unei persoane
	public boolean delete(Guest guest) {
		if (onWaitingList(guest)) {
			listaAsteptare.remove(guest);
			System.out.println("Persoana a fost stearsa cu succes!");
			return true;
		} else if (onMainList(guest)) {
			listaParticipanti.remove(guest);
			if (!listaAsteptare.isEmpty()) {
				listaParticipanti.add(listaParticipanti.get(0));
				listaAsteptare.remove(0);
			}
			System.out.println("Persoana a fost stearsa cu succes!");
			return true;
		} else {
			System.err.println("Persoana nu era inscrisa!");
			return false;
		}
	}

	// 4. Actualizare detalii
	// 4.1 Update lastName
	public void updateLastName(Guest guest, String lastName) {
		guest.setLastName(lastName);
	}

	// 4.2 Update firstName
	public void updateFirstName(Guest guest, String firstName) {
		guest.setFirstName(firstName);
	}

	// 4.3 Update email
	public void updateEmail(Guest guest, String email) {
		guest.setEmail(email);
	}

	// 4.4 Update phoneNumber
	public void updatePhoneNumber(Guest guest, String phoneNumber) {
		guest.setPhoneNumber(phoneNumber);
	}

	// 5. Obtinere lista participare
	public void getMainList() {
		System.out.println(listaParticipanti.toString());
	}

	// 6. Obtinere lista asteptare
	public void getWaitList() {
		System.out.println(listaAsteptare.toString());
	}

	// 7. Obtinere numar locuri disponibile
	public int freeSeats() {
		System.out.print("Numarul de locuri disponibile: ");
		return this.nrMaxLocuri - listaParticipanti.size();
	}

	// 8. Obtinere numar persoane lista principala
	public int nrPersons() {
		System.out.print("Numarul de persoane participante: ");
		return listaParticipanti.size();
	}

	// 9. Obtinere numar persoane lista asteptare
	public int nrPersonsWaiting() {
		System.out.print("Numarul de persoane in asteptare: ");
		return listaAsteptare.size();
	}

	// 10. Obtinere numar total de persoane
	public int totalPersons() {
		return nrPersons() + nrPersonsWaiting();
	}

	// 11. Cautare partiala dupa subsir de caractere
	public String search(String random) {
		if (random == null || random == "") {
			return null;
		}

		random = random.toLowerCase();
		ArrayList<Guest> finalResult = new ArrayList<Guest>();
		for (Guest i : listaParticipanti) {
			if (i.getFirstName().toLowerCase().contains(random) || i.getLastName().toLowerCase().contains(random)
					|| i.getEmail().toLowerCase().contains(random) || i.getPhoneNumber().contains(random)) {
				finalResult.add(i);
			}
		}
		for (Guest i : listaAsteptare) {
			if (i.getFirstName().toLowerCase().contains(random) || i.getLastName().toLowerCase().contains(random)
					|| i.getEmail().toLowerCase().contains(random) || i.getPhoneNumber().contains(random)) {
				finalResult.add(i);
			}
		}
		return finalResult.toString();
	}

	public int findByName(String string1, String string2) {
		for (int i = 0; i < listaParticipanti.size(); i++) {
			Guest guest = listaParticipanti.get(i);
			if (guest.getLastName().equals(string1) && guest.getFirstName().equals(string2)) {
				return 0;
			}
		}

		for (int i = 0; i < listaAsteptare.size(); i++) {
			Guest guest = listaAsteptare.get(i);
			if (guest.getLastName().equals(string1) && guest.getFirstName().equals(string2)) {
				return i + 1;
			}
		}
		return -1;
	}

	public void updateLastName(String oldLastName, String oldFirstName, String newLastName) {
		for (int i = 0; i < listaParticipanti.size(); i++) {
			Guest guest = listaParticipanti.get(i);
			if (guest.getLastName().equals(oldLastName) && guest.getFirstName().equals(oldFirstName)) {
				guest.setLastName(newLastName);
				System.out.println("Numele a fost modificat!");
			}
		}
		for (int j = 0; j < listaAsteptare.size(); j++) {
			Guest guest2 = listaAsteptare.get(j);
			if (guest2.getLastName().equals(oldLastName) && guest2.getFirstName().equals(oldFirstName)) {
				guest2.setLastName(newLastName);
				System.out.println("Numele a fost modificat!");
			}
		}
	}

	public void updateFirstName(String oldLastName, String oldFirstName, String newFirstName) {
		for (int i = 0; i < listaParticipanti.size(); i++) {
			Guest guest = listaParticipanti.get(i);
			if (guest.getLastName().equals(oldLastName) && guest.getFirstName().equals(oldFirstName)) {
				guest.setFirstName(newFirstName);
				System.out.println("Prenumele a fost modificat!");
			}
		}
		for (int j = 0; j < listaAsteptare.size(); j++) {
			Guest guest2 = listaAsteptare.get(j);
			if (guest2.getLastName().equals(oldLastName) && guest2.getFirstName().equals(oldFirstName)) {
				guest2.setFirstName(newFirstName);
				System.out.println("Prenumele a fost modificat!");
			}
		}
	}

	public void updateEmail(String oldEmail, String newEmail) {
		for (int i = 0; i < listaParticipanti.size(); i++) {
			Guest guest = listaParticipanti.get(i);
			if (guest.getEmail().equals(oldEmail)) {
				guest.setEmail(newEmail);
			}
		}
		for (int j = 0; j < listaAsteptare.size(); j++) {
			Guest guest2 = listaAsteptare.get(j);
			if (guest2.getEmail().equals(oldEmail)) {
				guest2.setEmail(newEmail);
			}
		}
	}

	public void updatePhone(String oldPhone, String newPhone) {
		for (int i = 0; i < listaParticipanti.size(); i++) {
			Guest guest = listaParticipanti.get(i);
			if (guest.getPhoneNumber().equals(oldPhone)) {
				guest.setPhoneNumber(newPhone);
			}
		}
		for (int j = 0; j < listaAsteptare.size(); j++) {
			Guest guest2 = listaAsteptare.get(j);
			if (guest2.getPhoneNumber().equals(oldPhone)) {
				guest2.setPhoneNumber(newPhone);
			}
		}
	}

	public int findByEmail(String string) {
		for (int i = 0; i < listaParticipanti.size(); i++) {
			Guest guest = listaParticipanti.get(i);
			if (guest.getEmail().equals(string)) {
				return 0;
			}
		}

		for (int i = 0; i < listaAsteptare.size(); i++) {
			Guest guest = listaAsteptare.get(i);
			if (guest.getEmail().equals(string)) {
				return i + 1;
			}
		}
		return -1;
	}

	public int findByPhone(String string) {
		for (int i = 0; i < listaParticipanti.size(); i++) {
			Guest guest = listaParticipanti.get(i);
			if (guest.getPhoneNumber().equals(string)) {
				return 0;
			}
		}

		for (int i = 0; i < listaAsteptare.size(); i++) {
			Guest guest = listaAsteptare.get(i);
			if (guest.getPhoneNumber().equals(string)) {
				return i + 1;
			}
		}
		return -1;
	}

	public void removeFullName(String string1, String string2) {
		for (int i = 0; i < listaParticipanti.size(); i++) {
			Guest guest = listaParticipanti.get(i);
			if (guest.getLastName().equals(string1) && guest.getFirstName().equals(string2)) {
				delete(listaParticipanti.get(i));
			}
		}

		for (int i = 0; i < listaAsteptare.size(); i++) {
			Guest guest = listaAsteptare.get(i);
			if (guest.getLastName().equals(string1) && guest.getFirstName().equals(string2)) {
				delete(listaAsteptare.get(i));
			}
		}
	}

	public void removeByEmail(String email) {
		for (int i = 0; i < listaParticipanti.size(); i++) {
			Guest guest = listaParticipanti.get(i);
			if (guest.getEmail().equals(email)) {
				delete(listaParticipanti.get(i));
			}
		}
		for (int j = 0; j < listaAsteptare.size(); j++) {
			Guest guest2 = listaAsteptare.get(j);
			if (guest2.getEmail().equals(email)) {
				delete(listaAsteptare.get(j));
			}
		}
	}

	public void removeByPhoneNumber(String phoneNumber) {

		for (int i = 0; i < listaParticipanti.size(); i++) {
			Guest guest = listaParticipanti.get(i);
			if (guest.getPhoneNumber().equals(phoneNumber)) {
				delete(listaParticipanti.get(i));
			}
		}
		for (int j = 0; j < listaAsteptare.size(); j++) {
			Guest guest2 = listaAsteptare.get(j);
			if (guest2.getPhoneNumber().equals(phoneNumber)) {
				delete(listaAsteptare.get(j));
			}
		}
	}

	public void write(int nrMaxLocuri, ArrayList<Guest> listaParticipanti, ArrayList<Guest> listaAstptare)
			throws IOException {
		try (ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(
				"C:\\Users\\chiur\\OneDrive\\Desktop\\Stuff\\Devmind\\Exercitii\\Curs31-Modul02\\guestList")))) {
			file.writeInt(nrMaxLocuri);
			file.writeObject(listaAstptare);
			file.writeObject(listaParticipanti);
		}
	}

	public void read() throws IOException {
		try (ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(
				"C:\\Users\\chiur\\OneDrive\\Desktop\\Stuff\\Devmind\\Exercitii\\Curs31-Modul02\\guestList")))) {
			this.nrMaxLocuri = file.readInt();
			this.listaAsteptare = (ArrayList<Guest>) file.readObject();
			this.listaParticipanti = (ArrayList<Guest>) file.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		}
	}

	public void save() {
		try {
			write(this.totalPersons(), this.listaParticipanti, this.listaAsteptare);
		} catch (IOException e) {
			System.out.println("File wasn't saved.");
		}
		System.out.println("File saved.");
	}

	public void printSaved() {
		int nrLocuri = 0;
		ArrayList<Guest> listaParticipanti = null;
		ArrayList<Guest> listaAsteptare = null;
		try (ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(
				"C:\\Users\\chiur\\OneDrive\\Desktop\\Stuff\\Devmind\\Exercitii\\Curs30-Modul02\\guestList")))) {
			nrLocuri = file.readInt();
			listaAsteptare = (ArrayList<Guest>) file.readObject();
			listaParticipanti = (ArrayList<Guest>) file.readObject();
			
			System.out.println("Available seats: " + nrLocuri);

			if (listaParticipanti == null) {
				System.out.println("The main list is empty.");
			} else {
				System.out.println("Main list: ");
				for (Guest i : listaParticipanti) {
					System.out.println(i.toString());
				}
			}

			if (listaAsteptare == null) {
				System.out.println("The waiting list is empty.");
			} else {
				System.out.println("Wainting list:");
				for (Guest i : listaAsteptare) {
					System.out.println(i.toString());
				}
			}
		} catch (IOException e) {
			System.out.println("IOException!");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found!");
		}
	}

	public void reset() {
		this.nrMaxLocuri = 0;
		this.listaAsteptare = new ArrayList<Guest>();
		this.listaParticipanti = new ArrayList<Guest>();
	}
}
