import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        LinkedList<Palavra> lista = new LinkedList<>();
        String aux[];

        Path path1 = Paths.get("dicionario.csv");

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("UTF-8"))) {
            String line = reader.readLine();
            while (line != null) {
                aux = line.split(";");
                if (lista.size() == 0) {
                    aux[0] = aux[0].substring(1);

                }
                Palavra p = new Palavra(aux[0], aux[1]);
                lista.add(p);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }

        

        WordTree tree = new WordTree();
        for (Palavra palavra : lista) {
            tree.addWord(palavra.getPalavra());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite os caracteres iniciais (primeira letra deve ser maiuscula): ");
        String caracteres = scanner.nextLine();

        List<String> palavrasEncontradas = tree.searchAll(caracteres);
        System.out.println("Palavras encontradas: ");
        for (int i = 0; i < palavrasEncontradas.size(); i++) {
            System.out.println(i + ": " + palavrasEncontradas.get(i));
        }

        if (!palavrasEncontradas.isEmpty()) {
            System.out.print("Escolha uma das palavras encontradas: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            if (escolha >= 0 && escolha < palavrasEncontradas.size()) {
                String palavraEscolhida = palavrasEncontradas.get(escolha);
                String significado = buscarSignificado(palavraEscolhida, lista);
                System.out.println("Significado da palavra "+palavraEscolhida+": " + significado);
            } else {
                System.out.println("Opção inválida!");
            }
        } else {
            System.out.println("Nenhuma palavra encontrada!");
        }
        scanner.close();
    }

    public static String buscarSignificado(String palavra, List<Palavra> lista) {
        for (Palavra p : lista) {
            if (p.getPalavra().equals(palavra)) {
                return p.getSignificado();
            }
        }
        return "";
    }
}
