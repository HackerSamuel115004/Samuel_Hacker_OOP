package main.java.sk.stuba.fei.uim.oop.Game;

import main.java.sk.stuba.fei.uim.oop.Game.Cards.*;
import main.java.sk.stuba.fei.uim.oop.Game.KeyboardInput.CKeyboardInput;
import main.java.sk.stuba.fei.uim.oop.Game.Players.CPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CGame {

    int countOfPlayers;
    private List <CPlayer> listPlayers = new ArrayList<CPlayer>();

    private List<CPlayer> listPlayersDeleted = new ArrayList<CPlayer>();

    private List<CCard> listCards = new ArrayList<CCard>();
    private List<CCard> listTrashCards = new ArrayList<CCard>();

    private enum eTypeOfCards { Barell, Dynamit, Prison, Bang, Miss, Beer, CatBalou, StageCoach, Indians };
    public CGame()
    {
        initializeListCards();
    }

    private void initializeListCards()
    {
        eTypeOfCards[] arrCardsSign = new eTypeOfCards[71]; /* count of all cards */
        Map<eTypeOfCards, Integer> map= new HashMap<>();
        //Adding elements to map

        map.put(eTypeOfCards.Barell, 2);
        map.put(eTypeOfCards.Dynamit, 1);
        map.put(eTypeOfCards.Prison, 3);
        map.put(eTypeOfCards.Bang, 30);
        map.put(eTypeOfCards.Miss, 15);
        map.put(eTypeOfCards.Beer, 8);
        map.put(eTypeOfCards.CatBalou, 6);
        map.put(eTypeOfCards.StageCoach, 4);
        map.put(eTypeOfCards.Indians, 2);

        int index = 0;

        for (Map.Entry<eTypeOfCards, Integer> entry : map.entrySet())
        {
            for (int i = 0; i < entry.getValue(); i++)
            {
                arrCardsSign[index] = entry.getKey();
                index++;
            }
        }

        // Shuffle list of cards
        Random rand = new Random();
        int upperbound = 71;
        // get random index between 0 - 70
        int randomIndex = 0;

        for (int i = 0; i < 71; i++)
        {
            randomIndex = rand.nextInt(upperbound);

            eTypeOfCards typeA = arrCardsSign[randomIndex];

            arrCardsSign[randomIndex] = arrCardsSign[i];
            arrCardsSign[i] = typeA;
        }

        for (int i = 0; i < 71; i++)
        {
            if (arrCardsSign[i] == eTypeOfCards.Barell)
                listCards.add(new CBarrel());
            else if (arrCardsSign[i] == eTypeOfCards.Bang)
                listCards.add(new CBang());
            else if (arrCardsSign[i] == eTypeOfCards.Beer)
                listCards.add(new CBeer());
            else if (arrCardsSign[i] == eTypeOfCards.Miss)
                listCards.add(new CMissed());
            else if (arrCardsSign[i] == eTypeOfCards.Dynamit)
                listCards.add(new CDynamit());
            else if (arrCardsSign[i] == eTypeOfCards.CatBalou)
                listCards.add(new CCatBalou());
            else if (arrCardsSign[i] == eTypeOfCards.Prison)
                listCards.add(new CPrison());
            else if (arrCardsSign[i] == eTypeOfCards.Indians)
                listCards.add(new CIndians());
            else if (arrCardsSign[i] == eTypeOfCards.StageCoach)
                listCards.add(new CStageCoach());
        }
    }

    private void initializeListPlayers()
    {
        for (int i = 0; i < countOfPlayers; i++)
        {
            listPlayers.add(new CPlayer(i, 4));

            for (int j = 0; j < 4; j++)
            {
                // pridanie karty do listu kariet hraca

                CPlayer player = listPlayers.get(i);
                player.addCardToListCards(listCards.get(0));
                player.getListCards().get(j).setCardOwner(player);

                // zatial len vyhodenie ale pojde do odhadzovacej kopy
                listCards.remove(0);
            }
        }
    }

    public void mainLoop()
    {
        System.out.println("Choose count of players in range of <2, 4>: ");

        int pocetHracov = CKeyboardInput.readInt();

        if (pocetHracov != Integer.MIN_VALUE && (pocetHracov >= 2 && pocetHracov <= 4)) {
            countOfPlayers = pocetHracov;
            initializeListPlayers();
        }
        else
        {
            System.out.println("Not valid count of players, end of application");
            return;
        }

        int cntRound = 1;

        while(this.listPlayers.size() != 1)
        {
            boolean dynamitWasPresent = false;
            boolean dynamitExploded = false;
            CDynamit cardDynamit = null;

            for(int i = 0; i < this.listPlayers.size(); i++) {
                CPlayer p = this.listPlayers.get(i);

                if (p.getIsAlive()) {
                    if (p.containsDynamitCard()) {
                        dynamitWasPresent = true;
                        cardDynamit = p.getDynamitCard();
                        dynamitExploded = p.getDynamitCard().playCard();

                        if (p.getLifeCount() <= 0)
                            p.setIsAlive(false);
                    }

                    if (p.getIsAlive()) {
                        if (p.containsPrisonCard()) {
                            CPrison cardPrison = p.getPrisonCard();

                            if (!cardPrison.isOwnerCard(p)) {
                                cardPrison.playCard();
                                this.listTrashCards.add(cardPrison);
                            }
                        }

                        if (!p.getIsInPrison()) {

                            // 1. Player take cards
                            if (this.listCards.size() >= 2) {
                                p.takeCards(listCards.get(0), listCards.get(1));
                                listCards.remove(0);
                                listCards.remove(0);
                            } else {
                                p.takeCard(listCards.get(0));
                                listCards.remove(0);
                            }

                            // 2. Specific player move
                            p.playerMove(this.listPlayers, this.listCards, this.listTrashCards);

                            // 3. Throw away cards if necessary
                            p.throwAwayCards(this.listTrashCards);
                        }
                        else
                        {
                            p.setIsInPrison(false);
                        }
                    }
                    else
                    {
                        System.out.println("Hrac ID: " + p.getId() + " bol zabity");
                        p.setIsAlive(false);
                    }
                }
            }

            if (this.listPlayers.size() != 1) {
                if (dynamitWasPresent && !dynamitExploded) {
                    cardDynamit.moveCardToAnotherPlayer(this.listPlayers);
                } else if (dynamitWasPresent && dynamitExploded) {
                    this.listTrashCards.add(cardDynamit);
                }
            }

            // Check which player have died
            for (CPlayer player : this.listPlayers)
            {
                if (!player.getIsAlive() && !listPlayersDeleted.contains(player))
                    listPlayersDeleted.add(player);
            }

            // Remove player from listPlayers
            for (CPlayer player : this.listPlayersDeleted)
            {
                if (this.listPlayers.contains(player))
                    this.listPlayers.remove(player);
            }

            // Output result after each round
            System.out.println("Status after " + cntRound + ". round :");
            this.listPlayers.forEach((CPlayer p) -> p.printPlayerStatus());
            this.listPlayersDeleted.forEach((CPlayer p) -> p.printPlayerStatus());
        }

        System.out.println("Congratulation player ID: " + this.listPlayers.get(0).getId() + " you won game !!!");
    }

}
