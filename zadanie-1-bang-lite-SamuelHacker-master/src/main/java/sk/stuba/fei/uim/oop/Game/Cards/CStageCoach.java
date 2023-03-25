package main.java.sk.stuba.fei.uim.oop.Game.Cards;

/*
Pomocou karty dostavník si hráč môže potiahnuť dve karty z balíčka.
 */

import main.java.sk.stuba.fei.uim.oop.Game.Players.CPlayer;

public class CStageCoach extends CCard{
    public CStageCoach() {super();}
    CStageCoach(CPlayer arg_CardOwner) {
        super(arg_CardOwner);
        super.cardType = TypeOfCard.brown;
    }
}
