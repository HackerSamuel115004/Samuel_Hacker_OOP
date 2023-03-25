package main.java.sk.stuba.fei.uim.oop.Game.Cards;

/*
Túto kartu dávate pred ľubovoného iného hráča, a danou kartou ho uväzníte.
Pokiaľ má hráč pred začiatkom svojho kola pred sebou kartu Väzenie, musí skontrolovať jej efekt, či sa mu podarí z neho ujsť alebo nie.
Šanca na újdenie z väzenia je 1 ku 4. Ak sa mu z väzenia nepodarí ujsť - vynecháva dané kolo, pokiaľ z neho ujde, pokračuje vo svojom ťahu.
V oboch prípadoch sa karta po kontrole jej efektu vyhadzuje do odhadzovacieho balíčka.
 */

import main.java.sk.stuba.fei.uim.oop.Game.KeyboardInput.CKeyboardInput;
import main.java.sk.stuba.fei.uim.oop.Game.Players.CPlayer;

import java.util.List;
import java.util.Random;

public class CPrison extends CCard {
    public CPrison() {super();}
    CPrison(CPlayer arg_CardOwner) {
        super(arg_CardOwner);
        super.cardType = TypeOfCard.brown;
    }

    public boolean isOwnerCard(CPlayer arg_player)
    {
        return super.isOwnerCard(arg_player);
    }

    @Override
    public boolean playCard(List<CPlayer> arg_listPlayers, List<CCard> arg_listTrashCards)
    {
        CPlayer playerToAttack = null;

        System.out.println("Choose ID player on which you want to use Bang card:");

        for (CPlayer player : arg_listPlayers)
        {
            if (player != getCardOwner() && player.getIsAlive())
                player.printPlayerStatus();
        }

        int idPlayer = CKeyboardInput.readInt();

        if (idPlayer != Integer.MIN_VALUE)
        {
            for (CPlayer player : arg_listPlayers)
            {
                if (idPlayer == player.getId())
                {
                    playerToAttack = player;
                }
            }

            if (playerToAttack == null)
            {
                System.out.println("You do not choosed correct ID, repeat action");
                return false;
            }

        }
        else
        {
            System.out.println("You do not choosed correct input, repeat action");
            return false;
        }

        playerToAttack.getListCardsInFront().add(this);
        this.setCardReceiver(playerToAttack);

        return true;
    }

    @Override
    public boolean playCard()
    {
        Random rand = new Random();
        // 0 - 3
        int randomNumber = rand.nextInt(4);
        int randomNumber2 = rand.nextInt(4);

        if (randomNumber == randomNumber2) {
            this.getCardReceiver().setIsInPrison(false);
        }
        else {
            this.getCardReceiver().setIsInPrison(true);
            System.out.println("Player ID: " + this.getCardReceiver().getId() + " was set to PRISON, player move is skipped");
        }

        super.getCardReceiver().removeCardFromListCardsInFront(super.getCardReceiver().getPrisonCard());

        return true;
    }

}
