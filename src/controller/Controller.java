package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Slider;
import ordination.*;
import storage.Storage;

public class Controller {
	private Storage storage;
	private static Controller controller;

	private Controller() {
		storage = new Storage();
	}

	public static Controller getController() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}

	public static Controller getTestController() {
		return new Controller();
	}

	/**
	 * Hvis startDato er efter slutDato kastes en IllegalArgumentException og
	 * ordinationen oprettes ikke
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 * Pre: antal >= 0
	 * @return opretter og returnerer en PN ordination.
	 */

	//NOTE: Implementering ift PN class
	public PN opretPNOrdination(LocalDate startDen, LocalDate slutDen,
			Patient patient, Laegemiddel laegemiddel, double antal) {

		if (antal <= 0 || startDen == null || slutDen == null || patient == null || laegemiddel == null) {
			throw new IllegalArgumentException("Values kan ikke være null");
		}
		if (antal < 0) {
			throw new IllegalArgumentException("Antal skal være et positivt tal");
		}
		if (startDen.isAfter(slutDen)) {
			throw new IllegalArgumentException("Start date kan ikke være efter slutdato");
		}
		//Her laves en PN Ordination
		PN pnOrdination = new PN(startDen, slutDen, laegemiddel, antal);
		//Her adder vi den givne PN ordination til patientens andre Ordinationer
		patient.getOrdinationer().add(pnOrdination);
		return pnOrdination;
	}

	/**
	 * Opretter og returnerer en DagligFast ordination. Hvis startDato er efter
	 * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 * Pre: margenAntal, middagAntal, aftanAntal, natAntal >= 0
	 */
	public DagligFast opretDagligFastOrdination(LocalDate startDen,
			LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
			double morgenAntal, double middagAntal, double aftenAntal,
			double natAntal) {
		if (startDen.isAfter(slutDen)) throw new IllegalArgumentException("Outside range");
		else if (morgenAntal < 0 || middagAntal < 0 || aftenAntal < 0 || natAntal < 0 || startDen == null || slutDen == null || patient == null || laegemiddel == null) {
			throw new NullPointerException("Null values");
		}
		else {
			DagligFast dF = new DagligFast(startDen, slutDen, laegemiddel);
			return dF;
		}
	}

	/**
	 * Opretter og returnerer en DagligSkæv ordination. Hvis startDato er efter
	 * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke.
	 * Hvis antallet af elementer i klokkeSlet og antalEnheder er forskellige kastes også en IllegalArgumentException.
	 *
	 * Pre: startDen, slutDen, patient og laegemiddel er ikke null
	 * Pre: alle tal i antalEnheder > 0
	 */
	public DagligSkaev opretDagligSkaevOrdination(LocalDate startDen,
			LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
			LocalTime[] klokkeSlet, double[] antalEnheder) {
		if (startDen.isAfter(slutDen) || antalEnheder.length != klokkeSlet.length)
			throw new IllegalArgumentException("Outside range");
		else if (startDen == null || slutDen == null || patient == null || laegemiddel == null) {
			throw new NullPointerException("Null values");
		}
		for (int j = 0; j < antalEnheder.length; j++) {
			if (antalEnheder[j] == 0)
				throw new IllegalArgumentException("Manglende value i enheder");
		}
		//når hertil hvis der ikke rammes breaks fra exceptions
			DagligSkaev dS = new DagligSkaev(startDen,slutDen,laegemiddel);
			return dS;
		}

	/**
	 * En dato for hvornår ordinationen anvendes tilføjes ordinationen. Hvis
	 * datoen ikke er indenfor ordinationens gyldighedsperiode kastes en
	 * IllegalArgumentException
	 * Pre: ordination og dato er ikke null
	 */
	public void ordinationPNAnvendt(PN ordination, LocalDate dato) {
		if (ordination == null || dato == null) {
			throw new NullPointerException("null values");
		}
		//LINK FRA PN TIL ORDINATION FOR DATO, - Implementering fra abstrakt
		if (dato.isBefore(ordination.getStartDen()) ||
				dato.isAfter(ordination.getSlutDen())) {
			throw new IllegalArgumentException("Dato out of bounds");
		}
	}

	/**
	 * Den anbefalede dosis for den pågældende patient (der skal tages hensyn
	 * til patientens vægt). Det er en forskellig enheds faktor der skal
	 * anvendes, og den er afhængig af patientens vægt.
	 * Pre: patient og lægemiddel er ikke null
	 */

	//OBS? Type enhed ml, enhed etc.??
	public double anbefaletDosisPrDoegn(Patient patient, Laegemiddel laegemiddel) {
		if (patient == null || laegemiddel == null) {
			throw new NullPointerException("null values");
		}
		if (patient.getVaegt() < 25) {
			return laegemiddel.getEnhedPrKgPrDoegnLet() * patient.getVaegt();
		}
		else if (patient.getVaegt() > 25 && patient.getVaegt() < 120) {
			return laegemiddel.getEnhedPrKgPrDoegnNormal() * patient.getVaegt();
		}
		else
		return laegemiddel.getEnhedPrKgPrDoegnTung() * patient.getVaegt();
	}

	/**
	 * For et givent vægtinterval og et givent lægemiddel, hentes antallet af
	 * ordinationer.
	 * Pre: laegemiddel er ikke null
	 */
	public int antalOrdinationerPrVægtPrLægemiddel(double vægtStart,
			double vægtSlut, Laegemiddel laegemiddel) {
		if (laegemiddel == null) {
			throw new NullPointerException("Laegemiddel null");
		}
		int count = 0;
		for (Patient patient : storage.getAllPatienter()) {
			if (patient.getVaegt() > vægtStart && patient.getVaegt() < vægtSlut) {
				count += patient.getOrdinationer().size();
			}
		}
		return count;
	}

	public List<Patient> getAllPatienter() {
		return storage.getAllPatienter();
	}

	public List<Laegemiddel> getAllLaegemidler() {
		return storage.getAllLaegemidler();
	}

	/**
	 * Metode der kan bruges til at checke at en startDato ligger før en
	 * slutDato.
	 *
	 * @return true hvis startDato er før slutDato, false ellers.
	 */
	private boolean checkStartFoerSlut(LocalDate startDato, LocalDate slutDato) {
		boolean result = true;
		if (slutDato.compareTo(startDato) < 0) {
			result = false;
		}
		return result;
	}

	public Patient opretPatient(String cpr, String navn, double vaegt) {
		Patient p = new Patient(cpr, navn, vaegt);
		storage.addPatient(p);
		return p;
	}

	public Laegemiddel opretLaegemiddel(String navn,
			double enhedPrKgPrDoegnLet, double enhedPrKgPrDoegnNormal,
			double enhedPrKgPrDoegnTung, String enhed) {
		Laegemiddel lm = new Laegemiddel(navn, enhedPrKgPrDoegnLet,
				enhedPrKgPrDoegnNormal, enhedPrKgPrDoegnTung, enhed);
		storage.addLaegemiddel(lm);
		return lm;
	}

	public void createSomeObjects() {
		this.opretPatient("121256-0512", "Jane Jensen", 63.4);
		this.opretPatient("070985-1153", "Finn Madsen", 83.2);
		this.opretPatient("050972-1233", "Hans Jørgensen", 89.4);
		this.opretPatient("011064-1522", "Ulla Nielsen", 59.9);
		this.opretPatient("090149-2529", "Ib Hansen", 87.7);

		this.opretLaegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");
		this.opretLaegemiddel("Paracetamol", 1, 1.5, 2, "Ml");
		this.opretLaegemiddel("Fucidin", 0.025, 0.025, 0.025, "Styk");
		this.opretLaegemiddel("Methotrexat", 0.01, 0.015, 0.02, "Styk");

		this.opretPNOrdination(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 12),
				storage.getAllPatienter().get(0), storage.getAllLaegemidler()
						.get(1),
				123);

		this.opretPNOrdination(LocalDate.of(2021, 2, 12), LocalDate.of(2021, 2, 14),
				storage.getAllPatienter().get(0), storage.getAllLaegemidler()
						.get(0),
				3);

		this.opretPNOrdination(LocalDate.of(2021, 1, 20), LocalDate.of(2021, 1, 25),
				storage.getAllPatienter().get(3), storage.getAllLaegemidler()
						.get(2),
				5);

		this.opretPNOrdination(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 12),
				storage.getAllPatienter().get(0), storage.getAllLaegemidler()
						.get(1),
				123);

		this.opretDagligFastOrdination(LocalDate.of(2021, 1, 10),
				LocalDate.of(2021, 1, 12), storage.getAllPatienter().get(1),
				storage.getAllLaegemidler().get(1), 2, 0, 1, 0);

		LocalTime[] kl = { LocalTime.of(12, 0), LocalTime.of(12, 40),
				LocalTime.of(16, 0), LocalTime.of(18, 45) };
		double[] an = { 0.5, 1, 2.5, 3 };

		this.opretDagligSkaevOrdination(LocalDate.of(2021, 1, 23),
				LocalDate.of(2021, 1, 24), storage.getAllPatienter().get(1),
				storage.getAllLaegemidler().get(2), kl, an);
	}

}
