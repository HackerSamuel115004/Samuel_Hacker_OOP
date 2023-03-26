package sk.stuba.fei.uim.oop.Game.Cards;

/*
Túto kartu pri zahratí vykladáte pred seba. Efekt karty Dynamit sa kontroluje vždy na začiatku kola, pokiaľ dynamit vybuchne pred vami, strácate 3 životy a
karta sa odhadzuje do balíčka. Pokiaľ vám dynamit nevybuchne, karta sa posúva hráčovi, ktorý hral pred vami (Dynamit sa posúva opačným smerom, akým prebieha hra).
Šanca na vybuchnutie dynamitu je 1 ku 8;
Pokiaľ by nastala situácia, že hráč má na začiatku kola pred sebou aj kartu Väzenie aj Dynamit, kontroluje sa najskôr efekt karty Dynamit a až potom Väzenie.
 */

import sk.stuba.fei.uim.oop.Game.Players.CPlayer;

import java.util.List;
import java.util.Random;

public class CDynamit extends CCard{
    public CDynamit(){super();}

    public CDynamit(CPlayer arg_CardOwner) {
        super(arg_CardOwner);
        super.cardType = TypeOfCard.brown;
    }

    public boolean isOwnerCard(CPlayer arg_player)
    {
        return super.isOwnerCard(arg_player);
    }

    @Override
    public boolean playCard()
    {
        Random rand = new Random();
        // 0 - 3
        int randomNumber = rand.nextInt(2);
        int randomNumber2 = rand.nextInt(2);

        // dynamit boom
        if (randomNumber == randomNumber2) {
            super.getCardReceiver().lowerLife(3);
            return true;
        }

        return false;
    }

    public void moveCardToAnotherPlayer(List<CPlayer> arg_listPlayers)
    {
        // dynamit not boom we are moving dynamit to next player
        for (int i = 0; i < arg_listPlayers.size(); i++)
        {
            if (arg_listPlayers.get(i).getId() == super.getCardOwner().getId())
            {
                if (i == 0)
                {
                    CPlayer newCardOwner = arg_listPlayers.get(arg_listPlayers.size() - 1);
                    newCardOwner.addCardToListCardsInFront(this);

                    super.getCardOwner().removeCardFromListCardsInFront(this);
                    super.setCardOwner(newCardOwner);
                }
                else if (i == (arg_listPlayers.size() - 1))
                {
                    CPlayer newCardOwner = arg_listPlayers.get(0);
                    newCardOwner.addCardToListCardsInFront(this);

                    super.getCardOwner().removeCardFromListCardsInFront(this);
                    super.setCardOwner(newCardOwner);

                }
                else {
                    CPlayer newCardOwner = arg_listPlayers.get(i - 1);
                    newCardOwner.addCardToListCardsInFront(this);

                    super.getCardOwner().removeCardFromListCardsInFront(this);
                    super.setCardOwner(newCardOwner);
                }
            }
        }
    }
}
