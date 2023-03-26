package sk.stuba.fei.uim.oop.Game.Cards;

import sk.stuba.fei.uim.oop.Game.KeyboardInput.CKeyboardInput;
import sk.stuba.fei.uim.oop.Game.Players.CPlayer;

import java.util.List;
import java.util.Random;

/*
Zahraním tejto karty môžete odhodiť kartu protivníkovi, či už zo stola alebo z ruky.
Príklad zahrania karty: Hráč A zahrá kartu Cat Balou na Hráča B, hra mu ponúkne či chce odhodiť kartu z ruky alebo zo stola, a po danom výbere sa Hráčovi B automaticky vyhodí náhodne zvolená karta.
Pokiaľ Hráč B nemá žiadnu kartu, Hráčovi A sa ukáže hláška že túto kartu nemôže momentálne na daného hráča zahrať.
 */

public class CCatBalou extends CCard {
    public CCatBalou(){super();}

    public CCatBalou(CPlayer arg_CardOwner) {
        super(arg_CardOwner);
        super.cardType = CCard.TypeOfCard.brown;
    }

    public boolean playCard(List<CPlayer> arg_listPlayers, List<CCard> arg_listTrashCards)
    {
        CPlayer playerToAttack = null;

        System.out.println("Choose ID of player on which you want to use CatBalou card:");

        for (CPlayer player : arg_listPlayers)
        {
            if(player != this.getCardOwner() && player.getIsAlive())
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

            if (playerToAttack == null || playerToAttack == getCardOwner())
            {
                System.out.println("You do not chose correct ID, repeat action");
                return false;
            }

        }
        else
        {
            System.out.println("Your input is not correct, repeat action");
            return false;
        }


        System.out.println("Choose if you want throw card from hand or from front of player ID: " + playerToAttack.getId() + " || 1. from hand || 2. from front");

        int option = CKeyboardInput.readInt();

        if (option == Integer.MIN_VALUE || (option != 1 && option != 2))
        {
            System.out.println("Your input is not correct, repeat action");
            return false;
        }
        else
        {
            Random rand = new Random();
            int randomNumber = 0;

            if (option == 1)
            {
                randomNumber = rand.nextInt(playerToAttack.getListCards().size());
                arg_listTrashCards.add(playerToAttack.getListCards().get(randomNumber));
                playerToAttack.getListCards().remove(randomNumber);
                System.out.println("CatBalou randomly threw away one card from player`s hand with player ID: " + playerToAttack.getId());

            }

            if (option == 2)
            {
                randomNumber = rand.nextInt(playerToAttack.getListCardsInFront().size());
                arg_listTrashCards.add(playerToAttack.getListCardsInFront().get(randomNumber));
                playerToAttack.getListCardsInFront().remove(randomNumber);
                System.out.println("CatBalou randomly threw away one card in front of player ID: " + playerToAttack.getId());
            }

            arg_listTrashCards.add(this);
        }

        return true;
    }
}
