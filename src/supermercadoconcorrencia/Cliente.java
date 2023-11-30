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

    /*@Override
    public void run() {
        // Implementação da lógica do cliente fazendo compras no caixa
        System.out.println("Cliente " + id + " está fazendo compras por " + tempoAtendimento + " segundos.");
        try {
            Thread.sleep(tempoAtendimento * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cliente " + id + " finalizou as compras.");
    }*/
}
