package player;

import scotlandyard.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by gebruiker on 24/04/2015.
 */
public class MyAIPlayer implements Player {

    private ScotlandYardView view;
    private Graph graph;

    public MyAIPlayer(ScotlandYardView view, String graphFilename) {
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
        // if(view.getCurrentPlayer() == Colour.Black) score(moves,location);
        ArrayList<Integer> targets = new ArrayList<Integer>();
        Map<Integer, Move> moveMap = new HashMap<Integer, Move>();

        for (Move move : moves) {
            if (move instanceof MoveTicket) {
                if(!targets.contains(((MoveTicket) move).target)){
                    moveMap.put(score(moves, ((MoveTicket) move).target), move);
                    targets.add(((MoveTicket) move).target);
                }
            } else if (move instanceof MoveDouble) {
                if(!targets.contains(((MoveDouble) move).move2.target)){
                    moveMap.put(score(moves, ((MoveDouble) move).move2.target), move);
                    targets.add(((MoveDouble) move).move2.target);
                }
            }
        }

        Tuple tuple = returnBestMove(moveMap);

        System.out.println("this is my move rating " + tuple.getRating());
        return tuple.getMove();
    }


    public int score(Set<Move> moves, int location) {
        int orientation = orientationOnBoard(location);
        int list = distanceScore(location);
        int targets = targetLocations(location);
        int total = orientation + list + targets;

        System.out.println("rating " + total);
        System.out.println(location);

        return total;
    }

    private int distanceScore(int location) {
        distanceAlgorithm algo = new distanceAlgorithm(graph, view);
        ArrayList<Integer> set = algo.calculate(location);
        int ans = 0;
        for (int n : set) {
            switch (n) {
                case 1:
                    ans += 30;
                    break;
                case 2:
                    ans += 18;
                    break;
                case 3:
                    ans += 12;
                    break;
                case 4:
                    ans += 6;
                    break;
                case 5:
                    ans += 3;
                    break;
                default:
                    ans += 0;
                    break;
            }
        }

        return ans;
    }

    private int orientationOnBoard(int location) {
        String zone1 = "80 81 82 99 100 101 110 111 112 113 114 126 125 131 132";
        String zone2 = "49 79 63 64 65 66 97 98 83 102 115 109 130 139 140 154";
        String zone3 = "35 36 48 62 78 96 124 138 152 150 151 153 155 156 157 141 134 133 127 118 116 104 103 85 84 67 68 50";
        String zone4 = "51 69 86 87 88 142 117 129 135 143 128 196 170 158 169 184 168 167 183 166 149 137 123 77 61";
        String zone5 = "95 75 59 45 60 164 46 47 34 22 23 37 24 38 25 39 52 40 76 53 41 54 70 89 105 108 160 172 187 159 186 185 197 195 194 193 180 181 182 165 179 148 122 147";
        String zone6 = "93 92 74 58 44 33 26 27 28 55 71 72 90 106 191 178 163 146 145 121 94 32";
        String zone7 = "9 20 21 1 8 18 19 31 43 57 73 29 2 3 4 10 11 12 13 14 15 5 16 6 7 17 30 42 56 91 107 119 136 161 162 174 173 188 175 171 199 198 192 190 189 176 177 144 120";

        String[] string1 = zone1.split(" ");
        String[] string2 = zone2.split(" ");
        String[] string3 = zone3.split(" ");
        String[] string4 = zone4.split(" ");
        String[] string5 = zone5.split(" ");
        String[] string6 = zone6.split(" ");
        String[] string7 = zone7.split(" ");

        String locationString = Integer.toString(location);

        if (Arrays.asList(string1).contains(locationString)) return 0;
        if (Arrays.asList(string2).contains(locationString)) return 2;
        if (Arrays.asList(string3).contains(locationString)) return 3;
        if (Arrays.asList(string4).contains(locationString)) return 5;
        if (Arrays.asList(string5).contains(locationString)) return 6;
        if (Arrays.asList(string6).contains(locationString)) return 8;
        if (Arrays.asList(string7).contains(locationString)) return 10;


        return 0;
    }

    //this is not perfect need to find a way to get validmoves of any location
    private int targetLocations(int location) {
        Set<Integer> targets = new HashSet<Integer>();
        Set<Edge> edges = graph.getEdges();
        for(Edge e :edges){
            if( Integer.parseInt(e.source().toString()) == location){
                targets.add(Integer.parseInt(e.target().toString()));
            }
        }


        return  -targets.size();
    }


    private Tuple returnBestMove(Map<Integer,Move> moveMap){
        int lowest = 5000;
        Set<Integer> set = moveMap.keySet();
        for(int n : set){
            if(n < lowest) lowest = n;
        }

        return new Tuple(moveMap.get(lowest),lowest);
    }

}
