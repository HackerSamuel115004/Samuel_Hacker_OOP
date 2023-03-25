package main.java.sk.stuba.fei.uim.oop.Game.Cards;

/*
Zahraním karty Indiáni spôsobíte útok indiánov na všetkých hráčov (okrem hráča ktorý ju zahral).
Každý z hráčou odhodí kartu Bang (ak ju má) alebo príde o jeden život.
 */

import main.java.sk.stuba.fei.uim.oop.Game.Players.CPlayer;

import java.util.List;

public class CIndians extends CCard{
    public CIndians(){super();}

    public CIndians(CPlayer arg_CardOwner) {
        super(arg_CardOwner);
        super.cardType = TypeOfCard.brown;
    }

    public boolean playCard(List<CPlayer> listPlayers, List<CCard> arg_listTrashCards)
    {
        for (CPlayer player : listPlayers)
        {
            if (player != getCardOwner() && player.getIsAlive()) {
                player.attackedByCard(this, arg_listTrashCards);
            }
        }

        arg_listTrashCards.remove(this);

        return true;
    }
}
