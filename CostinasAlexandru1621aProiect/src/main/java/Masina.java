public class Masina {
    private String model;
    private String combustibil;
    private String numar;
    private int an;

    public Masina(String model, String combustibil, String numar, int an) {
        this.model = model;
        this.combustibil = combustibil;
        this.numar = numar;
        this.an = an;
    }

    public Masina() {
        super();
    }

    @Override
    public String toString() {
        return "Masina{" +
                "model='" + model + '\'' +
                ", combustibil='" + combustibil + '\'' +
                ", numar='" + numar + '\'' +
                ", an=" + an +
                '}';
    }

    public String getModel() {
        return model;
    }

    public void setModel() {
        this.model = model;
    }

    public String getCombustibil() {
        return combustibil;
    }

    public void setCombustibil() {
        this.combustibil = combustibil;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar() {
        this.numar = numar;
    }

    public int getAn() {
        return an;
    }

    public void setAn() {
        this.an = an;
    }
}
