package model.extrato;

import model.Cliente;
import model.Aluguel;

import java.util.Iterator;

public abstract class Extrato {

    protected Cliente cliente;

    public Extrato(Cliente cliente) {
        this.cliente = cliente;
    }

    protected abstract String getHeader(String nomeCliente);

    protected abstract String getLineSeparator();

    protected abstract String alugelFormater(String dvdTitulo, double valor);

    protected abstract String getFooter(double valorTotal, int pontosTotais);

    public final String mostrarExtrato() {

        // 1- Fim da linha
        final String fimDeLinha = this.getLineSeparator();
        // 2- cabeçalho
        StringBuilder resultado = new StringBuilder(
                this.getHeader(this.cliente.getNome().toUpperCase())).append(fimDeLinha);

        double valorTotal = 0.0;

        int pontosDeAlugadorFrequente = 0;

        Iterator<Aluguel> alugueis = this.cliente.getDvdsAlugados().iterator();

        while (alugueis.hasNext()) {
            Aluguel cada = alugueis.next();
            double valorCorrente = cada.calcularAluguel();

            pontosDeAlugadorFrequente++;

            // adiciona bonus para aluguel de um lançamento por pelo menos 2 dias
            if (cada.ehDVDBonus() && cada.getDiasAlugado() > 1) {
                pontosDeAlugadorFrequente++;
            }
            // 3- linha do aluguel
            resultado.append(alugelFormater(cada.getDVD().getTitulo(), cada.calcularAluguel()));
            valorTotal += valorCorrente;
        }

        resultado.append(getFooter(valorTotal, pontosDeAlugadorFrequente));

        return resultado.toString();

    }
}
