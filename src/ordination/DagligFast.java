package ordination;

import java.util.ArrayList;

public class DagligFast {
    // TODO
    private final ArrayList<Dosis> dosis = new ArrayList<>();
    private double antal;

    public ArrayList<Dosis> getDosis() {
        return new ArrayList<>(dosis);
    }
    public double getAntal() {
        return antal;
    }
}
