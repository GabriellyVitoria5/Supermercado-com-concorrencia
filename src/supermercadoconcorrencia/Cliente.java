package supermercadoconcorrencia;

public class Cliente{
    private int id;
    private int tempoCompra;
    private boolean precisaTroco;

    public Cliente(int id, int tempoCompra, boolean precisaTroco) {
        this.id = id;
        this.tempoCompra = tempoCompra;
        this.precisaTroco = true;
    }
    
    public int getId() {
        return id;
    }

    public int getTempoCompra() {
        return tempoCompra;
    }

    public boolean isPrecisaTroco() {
        return precisaTroco;
    }

    @Override
    public String toString() {
        return "Cliente " + id;
    }
}
