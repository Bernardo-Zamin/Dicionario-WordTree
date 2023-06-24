// 4645G-04 - Algoritmos e Estruturas de Dados I
// 2023-1
import java.util.ArrayList;
import java.util.List;

public class WordTree {

    // Classe interna
    private class CharNode {
        private char character;
        private String significado;
        private boolean isFinal;
        private CharNode father;
        private List<CharNode> children;

        public CharNode(char character) {
            this.character = character;
            this.isFinal = false;
            this.father = null;
            this.children = new ArrayList<>();
        }

        public CharNode(char character, boolean isFinal) {
            this.character = character;
            this.isFinal = isFinal;
            this.father = null;
            this.children = new ArrayList<>();
        }

        /**
         * Adiciona um filho (caracter) no nodo. Não pode aceitar caracteres repetidos.
         * 
         * @param character - caracter a ser adicionado
         * @param isfinal   - se é final da palavra ou não
         * @return o nodo filho adicionado
         */
        public CharNode addChild(char character, boolean isfinal) {
            
            CharNode existeChild = findChildChar(character);
            if (existeChild != null) {
            
                return existeChild;
            } else {
                
                CharNode newNode = new CharNode(character, isfinal);
                newNode.father = this;
                children.add(newNode);
                return newNode;
            }
        }

        public int getNumberOfChildren() {
            return children.size();
        }

        public CharNode getChild(int index) {
            return children.get(index);
        }

        /**
         * Obtém a palavra correspondente a este nodo, subindo até a raiz da árvore
         * 
         * @return a palavra
         */
        private String getWord() {
            StringBuilder sb = new StringBuilder();
            CharNode nodoAtual = this;
            while (nodoAtual.father != null) {
                sb.insert(0, nodoAtual.character);
                nodoAtual = nodoAtual.father;
            }
            return sb.toString();
        }

        /**
         * Encontra e retorna o nodo que tem determinado caracter.
         * 
         * @param character - caracter a ser encontrado.
         * @return o nodo encontrado ou null se não existir.
         */
        public CharNode findChildChar(char character) {
            for (CharNode child : children) {
                if (child.character == character) {
                    return child;
                }
            }
            return null;
        }

    }

    // Atributos
    private CharNode root;
    private int totalNodes = 0;
    private int totalpalavras = 0;

    // Construtor
    public WordTree() {
        root = new CharNode('\0');
    }

    // Metodos
    public int getTotalpalavras() {
        return totalpalavras;
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    /**
     * Adiciona palavra na estrutura em árvore.
     * 
     * @param word - palavra a ser adicionada.
     */
    public void addWord(String word) {
        CharNode nodoAtual = root;
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            boolean isFinal = (i == word.length() - 1);
            nodoAtual = nodoAtual.addChild(character, isFinal);
            totalNodes++;
        }
        totalpalavras++;
    }

    /**
     * Vai descendo na árvore até onde conseguir encontrar a palavra.
     * 
     * @param word - palavra a ser encontrada.
     * @return o nodo final encontrado ou null se a palavra não existir.
     */
    private CharNode findCharNodeForWord(String word) {
        CharNode nodoAtual = root;
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            nodoAtual = nodoAtual.findChildChar(character);
            if (nodoAtual == null) {
                return null;
            }
        }
        return nodoAtual;
    }

    /**
     * Percorre a árvore e retorna uma lista com as palavras iniciadas pelo prefixo
     * dado.
     * Tipicamente, um método recursivo.
     * 
     * @param prefix - prefixo a ser buscado.
     * @return uma lista com as palavras encontradas.
     */
    public List<String> searchAll(String prefix) {
        List<String> palavras = new ArrayList<>();
        CharNode nodoPrefixo = findCharNodeForWord(prefix);
        if (nodoPrefixo != null) {
            getLetrasDoNodo(nodoPrefixo, palavras);
        }
        return palavras;
    }

    public void getLetrasDoNodo(CharNode node, List<String> palavras) {
        if (node.isFinal) {
            palavras.add(node.getWord());
        }
        for (CharNode child : node.children) {
            getLetrasDoNodo(child, palavras);
        }
    }
}
