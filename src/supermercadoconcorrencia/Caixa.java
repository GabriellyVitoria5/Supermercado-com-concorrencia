package supermercadoconcorrencia;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Caixa implements Runnable {

    private int id;
    private int troco;
    private List<Cliente> filaClientes;
    private Semaphore semaforo;
    private static Object chaveTroco = new Object();
    private Supermercado supermercado;
    private Caixa proximoCaixa;
    

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

    public List<Cliente> getFilaClientes() {
        return filaClientes;
    }

    @Override
    public void run() {
        
        while (supermercado.isSupermercadoAberto()) {
            
            //caixa vai executar seu trabalho enquanto houver clientes na fila 
            if (!filaClientes.isEmpty()) {
                Cliente clienteAtual = null; 
                try {
                    //caixa está atendendo o primeiro cliente da fila
                    clienteAtual = filaClientes.remove(0);
                    
                    semaforo.acquire(); //pedir permissão para acessar a prócima seção
                    
                    System.out.println("Caixa " + id + " está atendendo o cliente " + clienteAtual.getId() + " por " + clienteAtual.getTempoCompra() + " segundos");
                    Thread.sleep(clienteAtual.getTempoCompra()* 1000);
                    
                    if(clienteAtual.isPrecisaTroco()){
                        if(troco > 0){
                            //caixa tem troco para dar ao cliente
                            troco--;
                            System.out.println("Caixa " + id + " terminou de atender o cliente " + clienteAtual.getId() + ".");
                        }
                        else{
                            //cixa precisa pedir troco emprestado para o caixa vizinho
                            //System.out.println("Caixa " + id + " pede troco para o caixa " + ((id +1) % supermercado.getListaCaixas().size()));
                        }
                    }
                    
                    
                    System.out.println("Caixa " + id + " terminou de atender o cliente " + clienteAtual.getId() + ".");
                } 
                catch (InterruptedException e) {
                    e.printStackTrace(); 
                } 
                finally {
                    semaforo.release(); //liberar o semáforo para que outro cliente seja atendido
                }
                
            }
        }
    }
    

    public void clientePrecisaDeTroco(Cliente cliente) {
        if (cliente.isPrecisaTroco() && troco > 0) {
            troco--;
        } else if (cliente.isPrecisaTroco() && troco == 0) {
            //pegar troco com o próximo caixa
        }
    }
    
    //caixa atual não tem mais troco, precisa aguardar e pedir troco emprestado para o próximo caixa
    public void aguardarTroco(Caixa caixa) {
        synchronized (chaveTroco) {
            try {
                chaveTroco.wait();
            } catch (InterruptedException ex) {
                System.err.println("Caixa " + caixa.getId() + " está aguardando, pois precisa de troco emprestado do caixa ao lado");
            }
        }
    }

    //caixa é acordado e volta a funcionar 
    public static void voltarAoFuncionamento() {
        synchronized (chaveTroco) {
            chaveTroco.notifyAll();
        }
    }
    
    @Override
    public String toString() {
        return "Caixa{" + "id=" + id + ", filaClientes=" + filaClientes + '}';
    }
}
