package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DagligSkaev extends Ordination {
    // TODO
    private ArrayList<Dosis> doser;

    public DagligSkaev(LocalDate startDen, LocalDate slutDen, Laegemiddel laegemiddel) {
        super(startDen, slutDen, laegemiddel);
        this.doser = new ArrayList<>();
    }

    public void opretDosis(LocalTime tid, double antal) {
        // TODO
        Dosis dosis = new Dosis(tid,antal);
        doser.add(dosis);
    }

    @Override
    public double samletDosis() {
        double samletDosis = 0;
        for (Dosis dosis : doser) {
            samletDosis += dosis.getAntal();
        }
        return samletDosis;
    }

    @Override
    public double doegnDosis() {
        double antalDoser = doser.size();
        return antalDoser;
    }

    @Override
    public String getType() {
        return "DagligSkaev";
    }

    public ArrayList<Dosis> getDoser() {
        return doser;
    }
}
