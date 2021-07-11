package spbetu.prim.model.saver;

import spbetu.prim.model.algorithm.PrimAlgorithm;
import spbetu.prim.model.graph.Graph;

public interface ISaver {

    void saveGraph(Graph graph, PrimAlgorithm primAlgorithm);
}