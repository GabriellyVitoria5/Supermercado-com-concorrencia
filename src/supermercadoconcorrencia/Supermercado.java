package supermercadoconcorrencia;

import java.util.List;

public class Supermercado {
    private List<Caixa> listaCaixas;
    private int tempoFuncionamento;
    private boolean supermercadoAberto;

    public Supermercado(int tempoFuncionamento) {
        this.tempoFuncionamento = tempoFuncionamento;
        supermercadoAberto = true;
    }

    public List<Caixa> getListaCaixas() {
        return listaCaixas;
    }

    public void setListaCaixas(List<Caixa> listaCaixas) {
        this.listaCaixas = listaCaixas;
    }

    public int getTempoFuncionamento() {
        return tempoFuncionamento;
    }

    public void setTempoFuncionamento(int tempoFuncionamento) {
        this.tempoFuncionamento = tempoFuncionamento;
    }

    public boolean isSupermercadoAberto() {
        return supermercadoAberto;
    }

    public void setSupermercadoAberto(boolean supermercadoAberto) {
        this.supermercadoAberto = supermercadoAberto;
    }
    
    public void fecharSupermercado(){
        supermercadoAberto = false;
    }
}
