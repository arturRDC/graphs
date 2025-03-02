package br.ufrn.imd.representations;

import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixDigraph extends IncidenceMatrixGraph {

    // Construtor para digrafo
    public IncidenceMatrixDigraph() {
        super();
    }

    // Método para adicionar uma aresta direcionada entre dois vértices
    @Override
    public void addEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);

        if (sourceIndex != -1 && destIndex != -1) {
            if (edgeExists(sourceIndex, destIndex)) {
                return;
            }
            // Adiciona uma nova linha representando a nova aresta
            List<Integer> newEdge = new ArrayList<>();
            for (int i = 0; i < vertices.size(); i++) {
                newEdge.add(0); // Inicializa todos os vértices com 0
            }
            newEdge.set(sourceIndex, 1); // Origem
            newEdge.set(destIndex, -1); // Destino
            incidenceMatrix.add(newEdge); // Adiciona a nova aresta na matriz
            edgeCount++;
        }
    }

    // Verifica se uma aresta direcionada já existe
    private boolean edgeExists(int sourceIndex, int destIndex) {
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix.get(i).get(sourceIndex) == 1 && incidenceMatrix.get(i).get(destIndex) == -1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeEdge(String source, String destination) {
        int sourceIndex = vertices.indexOf(source);
        int destIndex = vertices.indexOf(destination);

        // Verifica se os índices são válidos antes de prosseguir
        if (sourceIndex == -1 || destIndex == -1) {
            System.out.println("Aresta direcionada de " + source + " para " + destination + " não pode ser removida: vértice não encontrado.");
            return;
        }

        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix.get(i).get(sourceIndex) == 1 && incidenceMatrix.get(i).get(destIndex) == -1) {
                incidenceMatrix.set(i, incidenceMatrix.get(edgeCount - 1)); // Move a última aresta para a posição atual
                incidenceMatrix.remove(edgeCount - 1); // Remove a última aresta
                edgeCount--;
                return;
            }
        }
    }

    @Override
    public void printGraph() {
        System.out.println("\nDigrafo (A Partir da Matriz de Incidência):");
        for (int i = 0; i < edgeCount; i++) {
            StringBuilder edgeString = new StringBuilder("Aresta " + (i + 1) + ": ");
            String vertex1 = "", vertex2 = "";

            for (int j = 0; j < vertices.size(); j++) {
                if (incidenceMatrix.get(i).get(j) == 1) {
                    vertex1 = vertices.get(j);
                } else if (incidenceMatrix.get(i).get(j) == -1) {
                    vertex2 = vertices.get(j);
                }
            }
            edgeString.append(vertex1).append(" -> ").append(vertex2);
            System.out.println(edgeString);
        }
    }

    @Override
    public List<String> findAdjacentVertices(String vertex) {
        int vertexIndex = vertices.indexOf(vertex);
        List<String> adjacentVertices = new ArrayList<>();

        if (vertexIndex == -1) {
            return adjacentVertices; // Vértice não encontrado
        }

        // Iterar sobre a matriz de incidência para encontrar arestas que incluem o vértice
        for (List<Integer> row : incidenceMatrix) {
            if (row.get(vertexIndex) == 1) {
                // Achar o outro vértice na aresta
                for (int i = 0; i < row.size(); i++) {
                    if (i != vertexIndex && row.get(i) == -1) {
                        adjacentVertices.add(vertices.get(i));
                    }
                }
            }
        }
        return adjacentVertices;
    }
}
