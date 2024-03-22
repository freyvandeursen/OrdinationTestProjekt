package controller;

import ordination.DagligFast;
import ordination.Laegemiddel;
import ordination.PN;
import ordination.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void opretPNOrdination() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 90);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        //Der laves en PN Ordination
        LocalDate startDen = LocalDate.now().minus(10, ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        PN pnOrdination = new PN(startDen, slutDen, laegemiddel1, 5.0);
        patient1.addOrdination(pnOrdination);

        //Asserts
        assertEquals(1,patient1.getOrdinationer().size());
    }
    @Test
    void opretPNOrdinationInvalidDate() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 90);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        //Der laves en PN Ordination
        LocalDate startDen = LocalDate.now().plus(10, ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().minus(10,ChronoUnit.DAYS);
        Controller controller = Controller.getController();


        //Asserts
        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.opretPNOrdination(startDen,slutDen,patient1,laegemiddel1,5.0));
        assertEquals("Start date kan ikke være efter slutdato",exception.getMessage());
    }

    @Test
    void opretDagligFastOrdination() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 90);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        Controller controller = Controller.getController();


        // Create a DagligFast ordination
        LocalDate startDen = LocalDate.now().minus(10,ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        DagligFast dagligFastOrdination = controller.opretDagligFastOrdination(startDen, slutDen, patient1, laegemiddel1, 2,0,1,0);

        //Asserts
        assertEquals(1,patient1.getOrdinationer().size());
    }

    @Test
    void opretDagligFastOrdinationInvalidDato() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 90);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        Controller controller = Controller.getController();


        // Create a DagligFast ordination
        LocalDate startDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().minus(10,ChronoUnit.DAYS);

        //Asserts
        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.opretDagligFastOrdination(startDen, slutDen, patient1, laegemiddel1, 2,0,1,0));
        assertEquals("Outside range",exception.getMessage());
    }

    @Test
    void opretDagligSkaevOrdination() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 90);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        Controller controller = Controller.getController();
        // Create a DagligFast ordination
        LocalDate startDen = LocalDate.now().minus(10,ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        LocalTime[] klokkeslet = {LocalTime.of(10,30),LocalTime.of(16,00)};
        double antalEnheder[] = {0.5, 1};
        controller.opretDagligSkaevOrdination(startDen,slutDen,patient1,laegemiddel1, klokkeslet, antalEnheder);

        //Asserts
        assertEquals(1,patient1.getOrdinationer().size());
    }
    @Test
    void opretDagligSkaevOrdinationInvalidDato() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 90);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        Controller controller = Controller.getController();
        // Create a DagligFast ordination
        LocalDate startDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().minus(10,ChronoUnit.DAYS);
        LocalTime[] klokkeslet = {LocalTime.of(10,30),LocalTime.of(16,00)};
        double antalEnheder[] = {0.5, 1};

        //Asserts
        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.opretDagligSkaevOrdination(startDen,slutDen,patient1,laegemiddel1, klokkeslet, antalEnheder));
        assertEquals("Outside range", exception.getMessage());
    }



    @Test
    void opretDagligSkaevOrdinationForkertAntalEnheder() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 90);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        Controller controller = Controller.getController();
        // Create a DagligFast ordination
        LocalDate startDen = LocalDate.now().minus(10,ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        LocalTime[] klokkeslet = {LocalTime.of(10,30),LocalTime.of(16,00)};
        double antalEnheder[] = {0.5,};

        //Asserts
        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.opretDagligSkaevOrdination(startDen,slutDen,patient1,laegemiddel1, klokkeslet, antalEnheder));
        assertEquals("Manglende value i enheder", exception.getMessage());
    }

    @Test
    void opretDagligSkaevOrdinationForkertAntalKlokkeslet() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 90);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        Controller controller = Controller.getController();
        // Create a DagligFast ordination
        LocalDate startDen = LocalDate.now().minus(10,ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        LocalTime[] klokkeslet = {LocalTime.of(10,30)};
        double antalEnheder[] = {0.5,1};

        //Asserts
        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.opretDagligSkaevOrdination(startDen,slutDen,patient1,laegemiddel1, klokkeslet, antalEnheder));
        assertEquals("Manglende klokkeslet", exception.getMessage());
    }

    @Test
    void ordinationPNAnvendt() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 20);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        Controller controller = Controller.getController();

        //Der laves en ordination
        LocalDate startDen = LocalDate.now().minus(10, ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        PN pnOrdination = new PN(startDen, slutDen, laegemiddel1, 5.0);

        //angiv pn ordination
        pnOrdination.givDosis(LocalDate.now());
        //asserts
        assertEquals(true, pnOrdination.erOrdinationGivet());
    }

    @Test
    void ordinationPNAnvendtInvalidDato() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 20);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        Controller controller = Controller.getController();

        //Der laves en ordination
        LocalDate startDen = LocalDate.now().minus(10, ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        PN pnOrdination = new PN(startDen, slutDen, laegemiddel1, 5.0);
        LocalDate invalidDato = LocalDate.now().plus(20, ChronoUnit.DAYS);

        //asserts
        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.ordinationPNAnvendt(pnOrdination,invalidDato));
        assertEquals("Dato out of bounds",exception.getMessage());
    }
    @Test
    void anbefaletDosisPrDoegnLet() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 20);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        //Der laves en ordination
        LocalDate startDen = LocalDate.now().minus(10, ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        PN pnOrdination = new PN(startDen, slutDen, laegemiddel1, 5.0);
        patient1.addOrdination(pnOrdination);

        double recommendedDosis = Controller.getController().anbefaletDosisPrDoegn(patient1,laegemiddel1);
        //daglig dosis recommended for 20kg patient
        patient1.setVaegt(20);
        assertEquals(40,recommendedDosis);
    }
    @Test
    void anbefaletDosisPrDoegnNormal() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 90);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        //Der laves en ordination
        LocalDate startDen = LocalDate.now().minus(10, ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        PN pnOrdination = new PN(startDen, slutDen, laegemiddel1, 5.0);
        patient1.addOrdination(pnOrdination);

        //daglig dosis recommended for 90kg patient
        double recommendedDosis = Controller.getController().anbefaletDosisPrDoegn(patient1,laegemiddel1);
        assertEquals(450, recommendedDosis);

    }
    @Test
    void anbefaletDosisPrDoegnTung() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 125);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        //Der laves en ordination
        LocalDate startDen = LocalDate.now().minus(10, ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        PN pnOrdination = new PN(startDen, slutDen, laegemiddel1, 5.0);
        patient1.addOrdination(pnOrdination);

        double recommendedDosis = Controller.getController().anbefaletDosisPrDoegn(patient1,laegemiddel1);
        //daglig dosis recommended for 20kg patient
        patient1.setVaegt(125);
        assertEquals(1000,recommendedDosis);
    }
    @Test
    void anbefaletDosisPrDoegnGrænse25Test() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 25);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        //Der laves en ordination
        LocalDate startDen = LocalDate.now().minus(10, ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        PN pnOrdination = new PN(startDen, slutDen, laegemiddel1, 5.0);
        patient1.addOrdination(pnOrdination);

        double recommendedDosis = Controller.getController().anbefaletDosisPrDoegn(patient1,laegemiddel1);
        //daglig dosis recommended for 20kg patient
        assertEquals(125,recommendedDosis);
    }
    @Test
    void anbefaletDosisPrDoegnGrænse120Test() {
        //Der laves en patient
        Patient patient1 = new Patient("12456-7890", "Jonas Dyhr", 120);

        //Der laves et laegemiddel
        Laegemiddel laegemiddel1 = new Laegemiddel("Medicin 1", 2,5,8,"Styk");

        //Der laves en ordination
        LocalDate startDen = LocalDate.now().minus(10, ChronoUnit.DAYS);
        LocalDate slutDen = LocalDate.now().plus(10,ChronoUnit.DAYS);
        PN pnOrdination = new PN(startDen, slutDen, laegemiddel1, 5.0);
        patient1.addOrdination(pnOrdination);

        double recommendedDosis = Controller.getController().anbefaletDosisPrDoegn(patient1,laegemiddel1);
        //daglig dosis recommended for 20kg patient
        assertEquals(600,recommendedDosis);
    }
}