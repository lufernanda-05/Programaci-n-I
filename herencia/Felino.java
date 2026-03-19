package herencia;

public class Felino extends Animal {
    private String especie;
    private boolean esCarnivoro;

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public boolean isEsCarnivoro() {
        return esCarnivoro;
    }

    public void setEsCarnivoro(boolean esCarnivoro) {
        this.esCarnivoro = esCarnivoro;
    }
}