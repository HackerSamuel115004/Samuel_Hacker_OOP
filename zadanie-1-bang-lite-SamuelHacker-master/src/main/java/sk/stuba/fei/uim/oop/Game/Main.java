package sk.stuba.fei.uim.oop.Game;

import sk.stuba.fei.uim.oop.Game.CGame;
import sk.stuba.fei.uim.oop.Game.KeyboardInput.CKeyboardInput;

public class Main {
    public static void main(String[] args)
    {
        CGame game = new CGame();
        game.mainLoop();
    }
}