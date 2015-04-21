package player;

import scotlandyard.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The RandomPlayer class is an example of a very simple AI that
 * makes a random move from the given set of moves. Since the
 * RandomPlayer implements Player, the only required method is
 * notify(), which takes the location of the player and the
 * list of valid moves. The return value is the desired move,
 * which must be one from the list.
 */
public class RandomPlayer implements Player {

    private ScotlandYardView view;
    private Graph graph;

    public RandomPlayer(ScotlandYardView view, String graphFilename) {
        //TODO: A better AI makes use of `view` and `graphFilename`.
        this.view = view;
        ScotlandYardGraphReader graphReader = new ScotlandYardGraphReader();
        try {
            graph = graphReader.readGraph(graphFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Move notify(int location, Set<Move> moves) {
        //TODO: Some clever AI here ...
        score(moves);


        int choice = new Random().nextInt(moves.size());
        for (Move move : moves) {
            if (choice == 0) {
                return move;
            }
            choice--;
        }

        return null;
    }

    public void score(Set<Move> moves){
//        distanceScore();
        targetLocations(moves);



        System.out.println("score");
    }

    private int distanceScore(){
        distanceAlgorithm algo = new distanceAlgorithm(graph,view);
        ArrayList<Integer> set = algo.calculate();


        return 0;
    }

    private int orientationOnBoard(){

        return 0;
    }

    private int targetLocations(Set<Move> moves){
        Set<Integer> targets = new HashSet<Integer>();
        for(Move move : moves){
            if(move instanceof MoveTicket){
                targets.add(((MoveTicket) move).target);
            }
            if(move instanceof MoveDouble){
                targets.add(((MoveDouble) move).move1.target);
                targets.add(((MoveDouble) move).move2.target);
            }
        }

      return targets.size();
    }





}
