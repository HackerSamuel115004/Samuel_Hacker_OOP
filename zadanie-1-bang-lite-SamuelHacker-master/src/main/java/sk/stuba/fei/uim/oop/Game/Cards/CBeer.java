package sk.stuba.fei.uim.oop.Game.Cards;

import sk.stuba.fei.uim.oop.Game.Players.CPlayer;

/*
Pivo
Karta pivo dovoľuje hráčovi pridať si jeden život. Efekt karty sa dá použiť iba na aktuálne hrajúceho hráča.
*/

public class CBeer extends CCard{

    public CBeer(){super();}

    public CBeer(CPlayer arg_CardOwner) {
        super(arg_CardOwner);
        super.cardType = TypeOfCard.brown;
    }
}
