package ordination;

import java.time.LocalDate;
import java.util.ArrayList;

public class DagligFast extends Ordination {
    // TODO
    private ArrayList<Dosis> dosis = new ArrayList<>();
    private double antal;

    public DagligFast(LocalDate startDen, LocalDate slutDen, Laegemiddel laegemiddel) {
        super(startDen, slutDen, laegemiddel);
    }
    public double getAntal() {
        return antal;
    }

    public void addDosis(Dosis dose) {
        dosis.add(dose);
    }
    @Override
    public double samletDosis() {
        double samletDosis = 0;
        for (Dosis dosis1 : dosis) {
            samletDosis += dosis1.getAntal();
        }
        return samletDosis;
    }

    @Override
    public double doegnDosis() {
        return 0;
    }

    @Override
    public String getType() {
        return "DagligFast";
    }

    public Dosis[] getDoser() {
        Dosis[] doserArray = new Dosis[dosis.size()];
        dosis.toArray(doserArray);
        return doserArray;
    }
}
