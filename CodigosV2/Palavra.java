
public class Palavra {
    private String palavra;
    private String significado;

    public Palavra(String palavra, String significado) {
        this.palavra = palavra;
        this.significado = significado;
    }

    public String getPalavra() {
        return palavra;
    }

    public String getSignificado() {
        return significado;
    }

    @Override
    public String toString() {
        return palavra + ": " + significado + "\n";
    }

    
}
