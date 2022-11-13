import java.math.BigDecimal;

public class Waluta {
    private String label;
    private BigDecimal kurs;

    public Waluta(String label, double kurs) {
        this.label = label;
        this.kurs = kurs;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }
}
