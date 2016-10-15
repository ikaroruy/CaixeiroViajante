package Caixeiro;

import java.util.ArrayList;
import java.util.Random;

public class CaixeiroViajante {

    private final int[][] _cidades;
    private int[] _melhorCaminho;

    /**
     * Inicializa a matriz de adjacência e inicializa os valores das arestas
     *
     * @param qtdCidades
     * @param qtdCaminhos
     */
    public CaixeiroViajante(int qtdCidades, int qtdCaminhos) {
        _cidades = new int[qtdCidades][qtdCidades];
        InicializarCaminhos(qtdCaminhos);
    }
    
    public int[][] getCidades(){
        return _cidades;
    }
    /**
     * Exibe todos os caminhos existentes na matriz de adjacência
     *
     * @return
     */
    public String ExibirCaminhos() {
        String caminhos = "000";
        for (int i = 0; i < TotalCidades(); i++) {
            caminhos += " " + FormatN(i);
        }
        caminhos += "\n";

        for (int i = 0; i < TotalCidades(); i++) {
            caminhos += FormatN(i);
            for (int j = 0; j < TotalCidades(); j++) {
                caminhos += " " + FormatN(_cidades[i][j]);
            }
            caminhos += "\n";
        }

        return caminhos;
    }

    public String ResolucaoPersonalizada() {
        String possibilidades = Aleatorio();
        
        int cidadeAtual;
        int[] caminhoAtual;
        Boolean[] percorridos;
        Boolean valido;

        for (int i = 0; i < TotalCidades(); i++) {
            caminhoAtual = new int[TotalCidades()];
            percorridos = new Boolean[TotalCidades()];
            valido = true;

            cidadeAtual = i;
            caminhoAtual[0] = cidadeAtual;
            percorridos[cidadeAtual] = true;

            for (int j = 1; j < TotalCidades(); j++) {
                cidadeAtual = MenorAresta(cidadeAtual, percorridos);
                if (cidadeAtual >= 0) {
                    caminhoAtual[j] = cidadeAtual;
                    percorridos[cidadeAtual] = true;
                } else {
                    valido = false;
                    break;
                }
            }
            if (valido) {
                //Caminho atual possivel
                possibilidades += "Caminho:";
                for (int k = 0; k < caminhoAtual.length; k++) {
                    possibilidades += " " + caminhoAtual[k];
                }

                //Custo para o caminho atual
                possibilidades += "\nCusto: " + CalcularCusto(caminhoAtual) + "\n\n";

                //Verifica se o caminho atual é melhor que o melhor caminho
                if (CalcularCusto(caminhoAtual) < CalcularCusto(_melhorCaminho)) {
                    _melhorCaminho = caminhoAtual.clone();
                }
            }
        }
        return possibilidades;
    }

    /**
     * Resolve o problema do caxeiro viajante montando n percursos
     * aleatoriamente
     *
     * @return
     */
    public String Aleatorio() {
        String possibilidades = "";
        _melhorCaminho = new int[TotalCidades()];
        Random random = new Random();
        int cidadeAtual;
        int[] caminhoAtual;
        Boolean[] percorridos;
        Boolean valido;

        for (int i = 0; i < TotalCidades(); i++) {
            caminhoAtual = new int[TotalCidades()];
            percorridos = new Boolean[TotalCidades()];
            valido = true;

            cidadeAtual = random.nextInt(TotalCidades());
            caminhoAtual[0] = cidadeAtual;
            percorridos[cidadeAtual] = true;

            for (int j = 1; j < TotalCidades(); j++) {
                cidadeAtual = ArestasAleatoria(cidadeAtual, percorridos);
                if (cidadeAtual >= 0) {
                    caminhoAtual[j] = cidadeAtual;
                    percorridos[cidadeAtual] = true;
                } else {
                    valido = false;
                    break;
                }
            }
            if (valido) {
                //Caminho atual possivel
                possibilidades += "Caminho:";
                for (int k = 0; k < caminhoAtual.length; k++) {
                    possibilidades += " " + caminhoAtual[k];
                }

                //Custo para o caminho atual
                possibilidades += "\nCusto: " + CalcularCusto(caminhoAtual) + "\n\n";

                //Verifica se o caminho atual é melhor que o melhor caminho
                if (CalcularCusto(caminhoAtual) < CalcularCusto(_melhorCaminho)) {
                    _melhorCaminho = caminhoAtual.clone();
                }
            }
        }
        return possibilidades;
    }

    /**
     * Inicializa o melhor caminho e chama a função recursiva para verificar
     * todas as possibilidades do PVC
     *
     * @return
     */
    public String ForcaBruta() {
        _melhorCaminho = new int[TotalCidades()];

        int[] caminhoAtual = new int[TotalCidades()];
        caminhoAtual[0] = 0;

        Boolean[] percorridos = new Boolean[TotalCidades()];
        percorridos[0] = true;

        return ForcaBruta(0, 1, caminhoAtual, percorridos);
    }

    /**
     * Função recursiva para resolver o PVC. Verifica todas as possibilidades
     * possiveis e guarda o caminho com menor custo
     *
     * @param cidade
     * @param qtdPercorridas
     */
    private String ForcaBruta(int cidade, int qtdPercorridas, int[] caminhoAtual, Boolean[] percorridos) {
        String possibilidades = "";
        if (qtdPercorridas == 0) {
            caminhoAtual = new int[TotalCidades()];
        } else if (qtdPercorridas == TotalCidades()) {
            //Caminho atual possivel
            String resultado = "Caminho:";
            for (int i = 0; i < caminhoAtual.length; i++) {
                resultado += " " + caminhoAtual[i];
            }

            //Custo para o caminho atual
            resultado += "\nCusto: " + CalcularCusto(caminhoAtual) + "\n\n";
            //Verifica se o caminho atual é melhor que o melhor caminho
            if (CalcularCusto(caminhoAtual) < CalcularCusto(_melhorCaminho)) {
                _melhorCaminho = caminhoAtual.clone();
            }
            return resultado;
        }

        for (int i = 0; i < TotalCidades(); i++) {
            if (_cidades[cidade][i] != 0 && percorridos[i] == null) {
                percorridos[i] = true;
                caminhoAtual[qtdPercorridas] = i;

                possibilidades += ForcaBruta(i, qtdPercorridas + 1, caminhoAtual, percorridos);

                percorridos[i] = null;
                caminhoAtual[qtdPercorridas] = 0;
            }
        }

        return possibilidades;
    }

    /**
     * Exibe na tela o melhor caminho e o seu custo
     *
     * @return
     */
    public String ExibirMelhorCaminho() {
        int total = 0;
        String caminho;
        for (int i : _melhorCaminho) {
            total += i;
        }

        if (total > 0) {
            caminho = "Melhor caminho:";
            for (int i = 0; i < _melhorCaminho.length; i++) {
                caminho += " " + _melhorCaminho[i];
            }

            caminho += "\nCusto: " + CalcularCusto(_melhorCaminho);

        } else {
            return "Não existe caminho válido.";
        }
        return caminho;
    }

    /**
     * Retorna o total de _cidades na matriz de adjacência
     *
     * @return
     */
    public int TotalCidades() {
        return _cidades[0].length;
    }

    /**
     * Inicializa os valores dos caminhos possiveis
     *
     * @param qtdArestas
     */
    private void InicializarCaminhos(int qtdArestas) {
        Random random = new Random();
        for (int i = 0; i < qtdArestas; i++) {
            int linha = random.nextInt(TotalCidades());
            int coluna = random.nextInt(TotalCidades());
            while (linha == coluna || _cidades[linha][coluna] != 0) {
                linha = random.nextInt(TotalCidades());
                coluna = random.nextInt(TotalCidades());
            }

            _cidades[linha][coluna] = random.nextInt(1000);
        }
    }

    /**
     * Retorna uma string do inteiro com 3 digitos
     * @param n
     * @return
     */
    private String FormatN(int n) {
        return String.format("%03d", n);
    }

    /**
     * Calcula o custo para percorrer o caminho
     * @param caminho
     * @return
     */
    private int CalcularCusto(int[] caminho) {
        int custo = 0, aresta;
        for (int i = 0; i < caminho.length; i++) {
            aresta = _cidades[caminho[i]][caminho[(i + 1) % caminho.length]];
            if (aresta > 0) {
                custo += aresta;
            } else {
                return Integer.MAX_VALUE;
            }
        }
        return custo;
    }

    /**
     * Verifica as arestas possiveis apartir de uma cidade e escolhe
     * aleatoriamente uma
     *
     * @param cidade
     * @param percorridos
     * @return
     */
    private int ArestasAleatoria(int cidade, Boolean[] percorridos) {
        ArrayList<Integer> arestas = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < TotalCidades(); i++) {
            if (_cidades[cidade][i] != 0 && percorridos[i] == null) {
                arestas.add(i);
            }
        }

        if (arestas.isEmpty()) {
            return -1;
        } else {
            return arestas.get(random.nextInt(arestas.size()));
        }
    }
    
    /**
     * Retorna a menor aresta possivel apartir de uma cidade
     * @param cidade
     * @param percorridos
     * @return 
     */
    private int MenorAresta(int cidade, Boolean[] percorridos) {
        int menor = -1;
        for (int i = 0; i < TotalCidades(); i++) {
            if (_cidades[cidade][i] != 0 && percorridos[i] == null) {
                if(menor < 0 || menor > _cidades[cidade][i]){
                    menor = i;
                }
            }
        }

        return menor;
    }
}
