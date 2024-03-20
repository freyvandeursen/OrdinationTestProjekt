package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DagligSkaev extends Ordination {
    // TODO
    private ArrayList<Dosis> dosisList = new ArrayList<>();

    public DagligSkaev(LocalDate startDen, LocalDate slutDen, Laegemiddel laegemiddel) {
        super(startDen, slutDen, laegemiddel);
        this.dosisList = new ArrayList<>();
    }

    public void opretDosis(LocalTime tid, double antal) {
        // TODO
        Dosis dosis = new Dosis(tid,antal);
        dosisList.add(dosis);
    }

    @Override
    public double samletDosis() {
        return 0;
    }

    @Override
    public double doegnDosis() {
        double antalDoser = dosisList.size();
        return 0;
    }

    @Override
    public String getType() {
        return "DagligSkaev";
    }

    public ArrayList<Dosis> getDoser() {
        return dosisList;
    }
}
