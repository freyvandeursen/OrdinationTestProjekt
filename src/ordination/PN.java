package ordination;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PN extends Ordination {

    private double antalEnheder;
    private List<LocalDate> datoer;
    private boolean givet;

    public PN(LocalDate startDen, LocalDate slutDen, Laegemiddel laegemiddel, Double antalEnhed) {
        super(startDen, slutDen, laegemiddel);
        antalEnheder = antalEnhed;
        this.datoer = new ArrayList<>();
    }

    /**
     * Registrerer at der er givet en dosis paa dagen givesDen
     * Returnerer true hvis givesDen er inden for ordinationens gyldighedsperiode og datoen huskes
     * Retrurner false ellers og datoen givesDen ignoreres
     * @param givesDen
     * @return
     */
    public boolean givDosis(LocalDate givesDen) {
        LocalDate sidsteGiveDato;
        // TODO
        if (givesDen.isAfter(super.getStartDen()) && givesDen.isBefore(super.getSlutDen())) {
            datoer.add(givesDen);
            givet = true;
            return givet;
        } else {
            givet = false;
         return givet;
        }
    }

    public double doegnDosis() {
        // TODO
        return (datoer.size()*antalEnheder)/antalDage();
    }

    @Override
    public String getType() {
        return "PN";
    }

    public double samletDosis() {
        // TODO
        return 0.0;
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     * @return
     */
    public int getAntalGangeGivet() {
        // TODO
        return datoer.size();
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

    public List getDatoer() {
        return datoer;
    }

    public void addAnvendelse(LocalDate dato) {
        datoer.add(dato);
    }

    public Boolean erOrdinationGivet() {
        return this.givet;
    }
}
