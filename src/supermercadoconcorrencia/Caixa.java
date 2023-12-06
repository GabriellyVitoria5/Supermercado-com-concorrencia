package supermercadoconcorrencia;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Caixa implements Runnable {

    private int id;
    private int troco;
    private List<Cliente> filaClientes;
    private final Semaphore semaforo;
    private static Object chaveTroco = new Object();
    private Supermercado supermercado;
    private boolean temTroco = true;
    

    public Caixa() {
        filaClientes = new ArrayList<>();
        this.semaforo = new Semaphore(1);
    }

    public Caixa(int id, int troco, Supermercado supermercado) {
        this.id = id;
        this.troco = troco;
        this.supermercado = supermercado;
        this.filaClientes = new ArrayList<>();
        this.semaforo = new Semaphore(1);
    }

    public synchronized void adicionarClienteNaFila(Cliente c) {
        filaClientes.add(c);
    }
    
    public int getId() {
        return id;
    }

    public int getTroco() {
        return troco;
    }

    public void setTroco(int troco) {
        this.troco = troco;
    }

    public List<Cliente> getFilaClientes() {
        return filaClientes;
    }
    
    public int getIdProximoCaixa(){
        return (id +1) % supermercado.getListaCaixas().size();
    }

    @Override
    public void run() {
        Cliente clienteAtual = null; 
        
        while (supermercado.isSupermercadoAberto() && temTroco) { 
            if (!filaClientes.isEmpty()) {
                
                //por convenção desse projeto, um caixa só consegue atender um cliente se ele possuir troco
                if(troco > 0){
                    if(!SupermercadoGUI.violarExclusaoMutua){
                        //garantir que apenas um cliente será atendido por vez
                        try{
                            //caixa vai atendender o primeiro cliente da fila
                            clienteAtual = filaClientes.remove(0);

                            semaforo.acquire(); //pedir permissão para acessar a próxima seção

                            System.out.println("Caixa " + id + " está atendendo o cliente " + clienteAtual.getId() + " por " + clienteAtual.getTempoCompra() + " segundos");
                            Thread.sleep(clienteAtual.getTempoCompra()* 1000);
                            if(clienteAtual.isPrecisaTroco()){
                                troco--;
                            }
                            System.out.println("Caixa " + id + " terminou de atender o cliente " + clienteAtual.getId() + ".");
                        }
                        catch (InterruptedException e) {
                            System.out.println(CoresMensagens.corVermelho + "Erro no funcionamento do caixa!");
                        } 
                        finally {
                            semaforo.release(); //liberar o semáforo para que outro cliente seja atendido
                        }
                    }
                    else{
                        //não garante que cada cliente terá a sua vez de ser atendido
                        clienteAtual = filaClientes.remove(0);
                        System.out.println("Caixa " + id + " está atendendo o cliente " + clienteAtual.getId() + " por " + clienteAtual.getTempoCompra() + " segundos");
                        if(clienteAtual.isPrecisaTroco()){
                            troco--;
                        }
                        System.out.println("Caixa " + id + " terminou de atender o cliente " + clienteAtual.getId() + ".");
                    }
                }
                else{
                    //caixa precisa pedir troco para o próximo caixa
                    caixaPrecisaDeTroco();
                }
            }
        }
    }

    public void caixaPrecisaDeTroco() {
        Caixa proximoCaixa = supermercado.getListaCaixas().get(getIdProximoCaixa());
        System.out.println(CoresMensagens.corAmarelo + "Caixa " + id + " precisa de troco! Tentando pedir emprestado para o caixa " + proximoCaixa.getId() + ".");

        if(!SupermercadoGUI.violarExclusaoMutua){
            //é preciso sincronizar o acesso à variável de troco do próximo caixa para evitar que ela seja alterada de forma incorreta durante a concorrência
            synchronized (chaveTroco) {
                // Seção crítica para a transferência de troco
                synchronized (proximoCaixa.chaveTroco) {
                    pedirTroco(proximoCaixa);
                }
            }
        }
        else{
            //acesso ao troco não é sincronizado, pode ser que o próximo caixa esteja alterando a variável de troco ao mesmo tempo que um caixa pede troco emprestado
            pedirTroco(proximoCaixa);
        }
    }
    
    public void pedirTroco(Caixa proximoCaixa){
        if(proximoCaixa.getTroco() > 0){
            proximoCaixa.setTroco(proximoCaixa.getTroco()-1);
            this.troco ++;
            System.out.println(CoresMensagens.corAzul + "Caixa " + id + " recebeu troco do caixa " + proximoCaixa.getId() + ".");
        }
        else{
            if(!SupermercadoGUI.deadlock){
                //se caixa não conseguir pedir troco emprestado, o caixa é fechado
                System.out.println(CoresMensagens.corVermelho + "Caixa " + id + " não conseguiu troco emprestado. O caixa não consegue mais funcionar.");
                temTroco = false;
            }
            else{
                //caixa não vai liberar o lock enquanto não conseguir o troco que precisa
                try {
                    System.out.println(CoresMensagens.corAmarelo + "Caixa " + id + " está aguardando troco emprestado do caixa " + proximoCaixa.getId());
                    chaveTroco.wait();
                } catch (InterruptedException ex) {
                    System.err.println(CoresMensagens.corVermelho + "Erro no caixa " + id + " o pedir troco!");
                }
            }

        }
    }
    
    @Override
    public String toString() {
        return "Caixa " + id + ": " + filaClientes;
    }
}