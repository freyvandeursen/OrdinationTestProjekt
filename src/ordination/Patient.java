package ordination;

import java.util.ArrayList;

public class Patient {
    private String cprnr;
    private String navn;
    private double vaegt;

    // TODO: Link til Ordination
    private ArrayList<Ordination> ordinationer = new ArrayList<>();

    public Patient(String cprnr, String navn, double vaegt) {
        this.cprnr = cprnr;
        this.navn = navn;
        this.vaegt = vaegt;
    }

    public String getCprnr() {
        return cprnr;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public double getVaegt(){
        return vaegt;
    }

    public void setVaegt(double vaegt){
        this.vaegt = vaegt;
    }

    public ArrayList<Ordination> getOrdinationer() {
        return ordinationer;
    }

    public void addOrdination(Ordination ordination) {
        if (ordination != null) {
            ordinationer.add(ordination);
        }
    }

    //TODO: Metoder (med specifikation) til at vedligeholde link til Ordination

    @Override
    public String toString(){
        return navn + "  " + cprnr;
    }
}
