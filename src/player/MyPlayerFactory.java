package player;

import gui.Gui;
import net.PlayerFactory;
import scotlandyard.Colour;
import scotlandyard.Player;
import scotlandyard.ScotlandYardView;
import scotlandyard.Spectator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gebruiker on 24/04/2015.
 */
public class MyPlayerFactory implements PlayerFactory{

    protected Map<Colour, PlayerType> typeMap;

    public enum PlayerType {simpleAI, GUI , smartAI}

    String imageFilename;
    String positionsFilename;

    protected List<Spectator> spectators;
    Gui gui;

    public MyPlayerFactory() {
        typeMap = new HashMap<Colour, PlayerType>();
        typeMap.put(Colour.Black, MyPlayerFactory.PlayerType.smartAI);
        typeMap.put(Colour.Blue, MyPlayerFactory.PlayerType.GUI);
        typeMap.put(Colour.Green, MyPlayerFactory.PlayerType.GUI);
        typeMap.put(Colour.Red, MyPlayerFactory.PlayerType.GUI);
        typeMap.put(Colour.White, MyPlayerFactory.PlayerType.GUI);
        typeMap.put(Colour.Yellow, MyPlayerFactory.PlayerType.GUI);

        positionsFilename = "resources/pos.txt";
        imageFilename     = "resources/map.jpg";

        spectators = new ArrayList<Spectator>();
    }

    public MyPlayerFactory(Map<Colour, PlayerType> typeMap, String imageFilename, String positionsFilename) {
        this.typeMap = typeMap;
        this.imageFilename = imageFilename;
        this.positionsFilename = positionsFilename;
        spectators = new ArrayList<Spectator>();
    }

    @Override
    public Player player(Colour colour, ScotlandYardView view, String s) {
        switch (typeMap.get(colour)) {
            case simpleAI:
                return new RandomPlayer(view, s);
            case smartAI:
                return new MyAIPlayer(view , s);
            case GUI:
                return gui(view) ;
            default:
                return new RandomPlayer(view, s);
        }
    }

    @Override
    public List<Spectator> getSpectators(ScotlandYardView scotlandYardView) {
        List<Spectator> specs = new ArrayList<Spectator>();
        specs.add(gui(scotlandYardView));
        return specs;
    }

    @Override
    public void ready() {
        if (gui != null) gui.run();
    }

    @Override
    public void finish() {
        if (gui != null) gui.update();
    }

    private Gui gui(ScotlandYardView view) {
        System.out.println("GUI");
        if (gui == null) try {
            gui = new Gui(view, imageFilename, positionsFilename);
            spectators.add(gui);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return gui;
    }
}
