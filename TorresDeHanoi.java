
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;

public class TorresDeHanoi {

    static long movimentos;
    static final Deque<String> ultimosMovimentos = new ArrayDeque<>();

    // Algoritmo recursivo das Torres de Hanói
    public static void hanoi(
            int n, char origem, char auxiliar, char destino) {

        if (n == 1) {
            registrarMovimento(origem, destino);
            return;
        }

        hanoi(n - 1, origem, destino, auxiliar);

        registrarMovimento(origem, destino);

        hanoi(n - 1, auxiliar, origem, destino);
    }

    // Conta os movimentos e guarda o início e o final da execução
    public static void registrarMovimento(char origem, char destino) {
        movimentos++;

        String movimento = "Movimento " + movimentos
                + ": " + origem + " -> " + destino;

        if (movimentos <= 3) {
            System.out.println(movimento);
        }

        ultimosMovimentos.addLast(movimento);

        if (ultimosMovimentos.size() > 3) {
            ultimosMovimentos.removeFirst();
        }
    }

    // Calcula a quantidade de movimentos usando recursividade
    public static BigInteger contarMovimentos(int n) {
        if (n == 0) {
            return BigInteger.ZERO;
        }

        return contarMovimentos(n - 1)
                .multiply(BigInteger.TWO)
                .add(BigInteger.ONE);
    }

    // Formata o tempo como HH:MM:SS:mm
    public static String formatarTempo(long nanos) {
        long centesimos = nanos / 10_000_000L;

        long horas = centesimos / 360000;
        long minutos = (centesimos % 360000) / 6000;
        long segundos = (centesimos % 6000) / 100;
        long centesimosRestantes = centesimos % 100;

        return String.format(
                "%02d:%02d:%02d:%02d",
                horas, minutos, segundos, centesimosRestantes
        );
    }

    public static void executarInstancia(int n) {
        System.out.println("\n================================");
        System.out.println("INSTANCIA: " + n + " disco(s)");
        System.out.println("================================");

        long inicio = System.nanoTime();

        movimentos = 0;
        ultimosMovimentos.clear();

        if (n <= 20) {
            // Executa todas as chamadas recursivas
            hanoi(n, 'A', 'B', 'C');

            System.out.println("\nUltimos movimentos:");
            for (String movimento : ultimosMovimentos) {
                System.out.println(movimento);
            }

            long fim = System.nanoTime();

            System.out.println("\nQuantidade de movimentos: " + movimentos);
            System.out.println("Tempo: "
                    + formatarTempo(fim - inicio));
            System.out.println("Modo: execucao completa");
        } else {
            // Não executa trilhões de movimentos.
            // Calcula o total pela recorrência.
            BigInteger total = contarMovimentos(n);

            long fim = System.nanoTime();

            System.out.println("Inicio ilustrativo da sequencia:");
            System.out.println("Movimento 1: A -> C");

            System.out.println("\nQuantidade total de movimentos: "
                    + total);

            System.out.println("Tempo: "
                    + formatarTempo(fim - inicio));

            System.out.println(
                    "Modo: contagem recursiva, sem executar "
                    + "todas as transferencias"
            );
        }
    }

    public static void main(String[] args) {
        int[] instancias = {1, 10, 20, 30, 40, 41};

        for (int n : instancias) {
            executarInstancia(n);
        }
    }
}
