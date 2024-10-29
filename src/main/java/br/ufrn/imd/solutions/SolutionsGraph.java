package br.ufrn.imd.solutions;

import br.ufrn.imd.representations.AdjacencyListGraph;
import br.ufrn.imd.representations.AdjacencyMatrixGraph;
import br.ufrn.imd.representations.Graph;
import br.ufrn.imd.representations.IncidenceMatrixGraph;
import br.ufrn.imd.utils.ArticulationPoints;
import br.ufrn.imd.utils.GraphFileReader;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class SolutionsGraph {
    private Graph graph;

    private final GraphFileReader graphFileReader;

    public SolutionsGraph() {
        this.graphFileReader = new GraphFileReader();
    }

    private void readAdjacencyMatrix(String fileName) {
        setGraph(new AdjacencyMatrixGraph());
        graphFileReader.setFileName(fileName);
        graphFileReader.read(graph);
    }

    // Leitura de Matriz de Incidência
    public void readIncidenceMatrix(String fileName) {
        setGraph(new IncidenceMatrixGraph());
        graphFileReader.setFileName(fileName);
        graphFileReader.read(graph);
    }

    private void readAdjacencyList(String fileName) {
        setGraph(new AdjacencyListGraph());
        graphFileReader.setFileName(fileName);
        graphFileReader.read(graph);
    }

    public Graph solution1(String fileName) {
        readAdjacencyList(fileName);
        System.out.println("Lista de Adjacência:");
        ((AdjacencyListGraph) graph).printList();
        graph.printGraph();
        return graph;
    }

    public Graph solution2(String fileName) {
        readAdjacencyMatrix(fileName);
        System.out.println("Matriz de Adjacência:");
        ((AdjacencyMatrixGraph) graph).printMatrix();
        graph.printGraph();
        return graph;
    }

    public Graph solution3(String fileName) {
        readIncidenceMatrix(fileName);
        System.out.println("Matriz de Incidência:");
        ((IncidenceMatrixGraph) graph).printMatrix();
        graph.printGraph();
        return graph;
    }

    public Graph solution4(AtomicInteger graphType, Graph graph) {
        switch (graphType.get()) {
            case 1:
                AdjacencyListGraph adjacencyListGraph = (AdjacencyListGraph) graph;
                AdjacencyMatrixGraph matrixGraph = adjacencyListGraph.toAdjacencyMatrix();
                System.out.println("Matriz de Adjacência equivalente:");
                matrixGraph.printMatrix();
                graphType.set(2);
                return matrixGraph;
            case 2:
                AdjacencyMatrixGraph adjacencyMatrixGraph = (AdjacencyMatrixGraph) graph;
                AdjacencyListGraph listGraph = adjacencyMatrixGraph.toAdjacencyList();
                System.out.println("Lista de Adjacências equivalente:");
                listGraph.printList();
                graphType.set(1);
                return listGraph;
            default:
                System.out.println("Conversão não suportada para este tipo de grafo.");
                return graph;
        }
    }


    public void solution9(Integer graphType, Graph graph) {
        Scanner scanner = new Scanner(System.in);

        printGraph(graphType, graph); // Imprime o grafo antes da operação

        if (addVertexInGraph(scanner, graph)) { // Usa o método genérico addVertexInGraph
            System.out.println("\nGrafo após a adição do vértice:");
            printGraph(graphType, graph); // Imprime o grafo depois da operação
        }
    }

    public void solution10(Integer graphType, Graph graph) {
        Scanner scanner = new Scanner(System.in);

        printGraph(graphType, graph); // Imprime o grafo antes da operação

        if (removeVertexFromGraph(scanner, graph)) { // Usa o método genérico removeVertexFromGraph
            System.out.println("\nGrafo após a remoção do vértice:");
            printGraph(graphType, graph); // Imprime o grafo depois da operação
        }
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    private void printGraph(Integer graphType, Graph graph) {
        switch (graphType) {
            case 1:
                System.out.println("Grafo atual (Lista de Adjacência):");
                ((AdjacencyListGraph) graph).printList();
                break;
            case 2:
                System.out.println("Grafo atual (Matriz de Adjacência):");
                ((AdjacencyMatrixGraph) graph).printMatrix();
                break;
            case 3:
                System.out.println("Grafo atual (Matriz de Incidência):");
                ((IncidenceMatrixGraph) graph).printMatrix();
                break;
            default:
                System.out.println("Tipo de grafo inválido.");
        }
    }

    private boolean addEdgeToGraph(Scanner scanner, Graph graph) {
        System.out.print("Digite a aresta a ser adicionada no formato (x, y) ou 0 para cancelar: ");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            System.out.println("Operação cancelada.");
            return false;
        }

        try {
            // Extrai os vértices da entrada do usuário
            String[] vertices = input.substring(1, input.length() - 1).split(", ");
            String source = vertices[0];
            String destination = vertices[1];

            // Verifica se a aresta já existe
            if (graph.hasEdge(source, destination)) {
                System.out.println("Erro: A aresta (" + source + ", " + destination + ") já existe no grafo.");
                return false;
            }

            // Adiciona a aresta
            graph.addEdge(source, destination);
            System.out.println("Aresta adicionada com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Entrada inválida. Certifique-se de usar o formato (x, y).");
            return false;
        }
    }

    private boolean remEdgeToGraph(Scanner scanner, Graph graph) {
        System.out.print("Digite a aresta a ser removida no formato (x, y) ou 0 para cancelar: ");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            System.out.println("Operação cancelada.");
            return false;
        }

        try {
            // Extrai os vértices da entrada do usuário
            String[] vertices = input.substring(1, input.length() - 1).split(", ");
            String source = vertices[0];
            String destination = vertices[1];

            // Verifica se a aresta já existe
            if (!graph.hasEdge(source, destination)) {
                System.out.println("Erro: A aresta (" + source + ", " + destination + ") não existe no grafo.");
                return false;
            }

            // remove a aresta
            graph.removeEdge(source, destination);
            System.out.println("Aresta removida com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Entrada inválida. Certifique-se de usar o formato (x, y).");
            return false;
        }
    }

    private boolean removeVertexFromGraph(Scanner scanner, Graph graph) {
        System.out.print("Digite o vértice a ser removido ou 0 para cancelar: ");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            System.out.println("Operação cancelada.");
            return false;
        }

        // Verifica se o vértice existe
        if (!graph.hasVertex(input)) {
            System.out.println("Erro: O vértice " + input + " não existe no grafo.");
            return false;
        }

        try {
            // Remove o vértice
            graph.removeVertex(input);
            System.out.println("Vértice removido com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao remover o vértice.");
            return false;
        }
    }

    private boolean addVertexInGraph(Scanner scanner, Graph graph) {
        System.out.print("Digite o vértice a ser adicionado ou 0 para cancelar: ");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            System.out.println("Operação cancelada.");
            return false;
        }

        // Verifica se o vértice existe
        if (graph.hasVertex(input)) {
            System.out.println("Erro: O vértice " + input + " ja existe no grafo.");
            return false;
        }

        try {
            // Remove o vértice
            graph.addVertex(input);
            System.out.println("Vértice adicionado com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao adicionar o vértice.");
            return false;
        }
    }




    public void solution14() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o vértice inicial: ");
        String startVertex = scanner.nextLine();
        if (!graph.hasVertex(startVertex)) {
            System.out.println("Vértice inicial não encontrado no grafo.");
            return;
        }
        graph.bfs(startVertex);
    }

    public void solution16() {
        ArticulationPoints articulationPoints = new ArticulationPoints(graph);
        articulationPoints.findArticulationPointsAndBlocks();
        articulationPoints.printResults();
    }
}
