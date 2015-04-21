package player;

import scotlandyard.*;

import java.util.*;

/**
 * Created by gebruiker on 21/04/2015.
 */
public class distanceAlgorithm {
    private Set<Node>  visitedNodes;
    private Set<Node>  searchingNodes;
    private Map<Node,Integer> mapNodeValue;
    private Graph graph;
    private ScotlandYardView view;

    public distanceAlgorithm(Graph graph, ScotlandYardView view){
        this.graph = graph;
        this.view = view;
        visitedNodes = new HashSet<Node>();
        searchingNodes = new HashSet<Node>();
        mapNodeValue = new HashMap<Node, Integer>();
    }

    public ArrayList<Integer> calculate(){
        //if(view.getPlayerLocation(Colour.Black) == 0) return new HashSet<Integer>();
        visitedNodes.add(graph.getNode(view.getPlayerLocation(Colour.Black ) + 1));
        searchingNodes.add(graph.getNode(view.getPlayerLocation(Colour.Black) + 1));
        int distance = 1;

        while (!areAllDetectivesMarked()) {
            Set<Node> neighbours = findAllNeighbours();
            giveNeighboursGrade(neighbours, distance);
            visitedNodes.addAll(neighbours);
            searchingNodes.clear();
            searchingNodes.addAll(neighbours);
            distance++;
        }


        return getDistances();
    }


    private ArrayList<Integer> getDistances(){
        List<Colour> players = new ArrayList<Colour>(view.getPlayers());
        players.remove(Colour.Black);
        ArrayList<Integer> distances = new ArrayList<Integer>();
        for(Colour colour : players){
            distances.add(mapNodeValue.get(graph.getNode(view.getPlayerLocation(colour))));
        }
        return distances;
    }



    private boolean areAllDetectivesMarked(){
        List<Colour> players = new ArrayList<Colour>(view.getPlayers());
        players.remove(Colour.Black);
        for(Colour colour : players){
            if(!mapNodeValue.containsKey(graph.getNode(view.getPlayerLocation(colour)))) return false;
        }
        return true;
    }


    private void giveNeighboursGrade(Set<Node> neighbours, int distance){
        for(Node n : neighbours){
            mapNodeValue.put(n,distance);
        }
    }

    private Set<Node> findAllNeighbours(){
        Set<Node> set = new HashSet<Node>();
        for(Node n : searchingNodes){
            set.addAll(findNeighbours(n));
        }
        return set;
    }

    private Set<Node> findNeighbours(Node n){
        Set<Edge> edges = graph.getEdges();
        Set<Node> set = new HashSet<Node>();
        for(Edge e : edges){
            if(e.source() == n.data() && e.data() != Route.Boat && !visitedNodes.contains(graph.getNode(e.target()))){
                set.add(graph.getNode(e.target()));
            }
//            if(e.target() == n.data() && e.data() != Route.Boat && !visitedNodes.contains(graph.getNode(e.target()))){
//                set.add(graph.getNode(e.source()));
//            }
        }
        return set;
    }


}