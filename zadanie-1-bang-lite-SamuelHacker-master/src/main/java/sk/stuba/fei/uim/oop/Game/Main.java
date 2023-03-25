package main.java.sk.stuba.fei.uim.oop.Game;

import main.java.sk.stuba.fei.uim.oop.Game.CGame;
import main.java.sk.stuba.fei.uim.oop.Game.KeyboardInput.CKeyboardInput;

public class Main {
    public static void main(String[] args)
    {
        CGame game = new CGame();
        game.mainLoop();
    }
}