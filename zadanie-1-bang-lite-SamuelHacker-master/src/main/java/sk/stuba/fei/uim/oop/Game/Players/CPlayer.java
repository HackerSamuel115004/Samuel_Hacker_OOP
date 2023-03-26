package sk.stuba.fei.uim.oop.Game.Players;

import sk.stuba.fei.uim.oop.Game.Cards.*;
import sk.stuba.fei.uim.oop.Game.KeyboardInput.CKeyboardInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CPlayer
{
    private boolean isAlive = true;
    private boolean inPrison = false;
   private int lifeCount;
   private int ID;

   private List<CCard> listCards = new ArrayList<CCard>();
   private List<CCard> listCardsInFront = new ArrayList<CCard>();

   public CPlayer(int arg_ID, int arg_LifeCount)
   {
       this.lifeCount = arg_LifeCount;
       this.ID = arg_ID;
   }

   public void printPlayerStatus()
   {
       System.out.println("Player ID: " + this.ID + "  HP: " + this.lifeCount + "  Alive: " + isAlive);
   }

   public int getId()
   {
       return this.ID;
   }

   public int getLifeCount()
   {
       return this.lifeCount;
   }

   public void takeCards(CCard arg_firstCard, CCard arg_secondCard)
   {
       arg_firstCard.setCardOwner(this);
       listCards.add(arg_firstCard);

       arg_secondCard.setCardOwner(this);
       listCards.add(arg_secondCard);
   }

   public void takeCard(CCard arg_Card)
   {
       arg_Card.setCardOwner(this);
       listCards.add(arg_Card);
   }

   public void addCardToListCards(CCard arg_Card)
   {
       this.listCards.add(arg_Card);
   }

   public void addCardToListCardsInFront(CCard arg_Card) {this.listCardsInFront.add(arg_Card);}

   public void removeCardFromListCards(CCard arg_Card)
   {
       this.listCards.remove(arg_Card);
   }

   public void removeCardFromListCardsInFront(CCard arg_Card)
    {
        this.listCardsInFront.remove(arg_Card);
    }

   public void setIsInPrison(boolean arg_inPrison)
   {
       this.inPrison = arg_inPrison;
   }

   public boolean getIsInPrison()
   {
       return this.inPrison;
   }

   public void setIsAlive(boolean arg_Alive)
   {
       this.isAlive = arg_Alive;
   }

   public boolean getIsAlive()
   {
       return this.isAlive;
   }

   public List<CCard> getListCards()
   {
       return this.listCards;
   }
    public List<CCard> getListCardsInFront()
    {
        return this.listCardsInFront;
    }

    public boolean containsPrisonCard()
    {
        boolean  contains = false;

        for( CCard card : this.listCardsInFront)
            if (card instanceof CPrison)
                contains = true;

        return contains;
    }

    public boolean containsDynamitCard()
    {
        boolean  contains = false;

        for( CCard card : this.listCardsInFront)
            if (card instanceof CDynamit)
                contains = true;

        return contains;
    }

    public CPrison getPrisonCard()
    {
        return (CPrison)this.listCardsInFront.stream().filter(c -> c instanceof  CPrison).findFirst().orElse(null);
    }

    public CDynamit getDynamitCard()
    {
        return (CDynamit)this.listCardsInFront.stream().filter(c -> c instanceof  CDynamit).findFirst().orElse(null);
    }

    private CMissed getMissedCard()
    {
        CMissed card = (CMissed)this.listCards.stream().filter(c -> c instanceof  CMissed).findFirst().orElse(null);

        if (card == null)
        {
            card = (CMissed)this.listCardsInFront.stream().filter(c -> c instanceof  CMissed).findFirst().orElse(null);
        }

        return card;
    }

    private CBarrel getBarrelCard()
    {
        return (CBarrel)this.listCardsInFront.stream().filter(c -> c instanceof  CBarrel).findFirst().orElse(null);
    }

    private CBang getCBangCard()
    {
        CBang card = (CBang)this.listCards.stream().filter(c -> c instanceof  CBang).findFirst().orElse(null);

        if (card == null)
        {
            card = (CBang)this.listCardsInFront.stream().filter(c -> c instanceof  CBang).findFirst().orElse(null);
        }

        return card;
    }

    public void lowerLife(int arg_points)
    {
        this.lifeCount -= arg_points;
    }

    public void playerMove(List<CPlayer> arg_listPlayers, List<CCard> arg_listCards, List<CCard> arg_listTrashCards)
    {
        System.out.println("\nPlayer ID: " + this.ID + " move");

        printCardsInHand();
        printCardsInFront();

        while (true)
        {
            System.out.println("\nChoose action by number: 1. Put card in front of you from hand || 2. Put card to other player || 3. Use card for yourself || 4. Skip round || 5. Finish your choosing/round || 6. Show your deck");

            int action = CKeyboardInput.readInt();

            if (action == Integer.MIN_VALUE || action < 1 || action > 6)
            {
                System.out.println("Bad input");
                continue;
            }
            else
            {
                if (action == 1)
                {
                    putCardsInFront();
                }

                if (action == 2)
                {
                    addCardToAnotherUser(arg_listPlayers, arg_listTrashCards);
                }

                if (action == 3)
                {
                    useCard(arg_listCards, arg_listTrashCards);
                }

                if (action == 4 || action == 5)
                {
                    break;
                }

                if (action == 6)
                {
                    printCardsInHand();
                    printCardsInFront();
                }
            }
        }
    }

    public void throwAwayCards(List<CCard> arg_listCardsTrash)
    {

        if( this.listCards.size() > this.lifeCount ) {
            System.out.println("You have more cards in hand (" + this.listCards.size() + ") than your life points (" + this.lifeCount + ")");
            System.out.println("In order to continue you need to throw away " + (this.listCards.size() - this.lifeCount) + " cards\n");

            while (this.listCards.size() > this.lifeCount) { printCardsInHand();
                System.out.println((this.listCards.size() - this.lifeCount) + " cards left to delete! Choose index of card which you want to throw away:");

                int cardIndex = CKeyboardInput.readInt();

                if (checkUserChoosenCardIndex(cardIndex, this.listCards) == Integer.MIN_VALUE) {
                    System.out.println("Bad input");
                } else {
                    arg_listCardsTrash.add(this.listCards.get(cardIndex));
                    this.listCards.remove(cardIndex);
                    System.out.println("Card was removed");
                }
            }
        }
    }

    private void useCard(List<CCard> arg_listCards, List<CCard> arg_listTrashCards)
    {
        List<CCard> choosenList = this.listCards;
        printCardsInHand();

        System.out.println("Choose index of card which you want to use:");
        int cardIndex = CKeyboardInput.readInt();

        if (checkUserChoosenCardIndex(cardIndex, choosenList) == Integer.MIN_VALUE)
        {
            System.out.println("Bad input");
        }
        else
        {
            CCard choosenCard = choosenList.get(cardIndex);

            if (choosenCard instanceof CBeer)
            {
                this.lifeCount += 1;

                arg_listTrashCards.add(choosenCard);
                choosenList.remove(choosenCard);

                System.out.println("Life count raised to: " + this.lifeCount);
            }
            else if (choosenCard instanceof CStageCoach)
            {
                if (arg_listCards.size() >= 2)
                {
                    CCard firstCard = arg_listCards.get(0);
                    firstCard.setCardOwner(this);

                    CCard secondCard = arg_listCards.get(0);
                    secondCard.setCardOwner(this);

                    this.listCards.add(firstCard);
                    this.listCards.add(secondCard);

                    arg_listCards.remove(0);
                    arg_listCards.remove(0);
                }
                else
                {
                    CCard card = arg_listCards.get(0);
                    card.setCardOwner(this);

                    this.listCards.add(card);

                    arg_listCards.remove(0);
                }
                arg_listTrashCards.add(choosenCard);
                choosenList.remove(choosenCard);

                System.out.println("Two cards were taken");
            }
            else
            {
                System.out.println("This type of card can't be used for yourself");
            }
        }

    }

    private void putCardsInFront()
    {

        printCardsInHand();
        System.out.println("Choose index of card which you want to use: ");
        int cardIndex = CKeyboardInput.readInt();

        if (checkUserChoosenCardIndex(cardIndex, this.listCards) == Integer.MIN_VALUE)
        {
            System.out.println("Bad input");
        }
        else
        {
            CCard choosenCard = this.listCards.get(cardIndex);
            if (choosenCard instanceof CDynamit || choosenCard instanceof CBarrel)
            {

                if (!isCardAlreadyInFront(choosenCard))
                {
                    System.out.println("Card is already in front of you, duplicated cards are not allowed");
                } else {
                    this.listCardsInFront.add(choosenCard);
                    this.listCards.remove(choosenCard);
                    System.out.println("You have placed a card if front of you");
                }
            }
            else { System.out.println("This type of card cannot be put in front of you "); }
        }
    }

    private boolean isCardAlreadyInFront(CCard arg_choosenCard)
    {
        for (CCard card : this.listCardsInFront)
        {
            if(card.getClass().getSimpleName() == arg_choosenCard.getClass().getSimpleName())
                return false;
        }

        return true;
    }

    private void addCardToAnotherUser(List<CPlayer> arg_listPlayers, List<CCard> arg_listTrashCards)
    {
        printCardsInHand();

        System.out.println("Choose index of card which you want to use: ");
        int cardIndex = CKeyboardInput.readInt();

        if (checkUserChoosenCardIndex(cardIndex, this.listCards) == Integer.MIN_VALUE)
        {
            System.out.println("Bad input");
        }
        else
        {
            CCard card = this.listCards.get(cardIndex);

            if (card instanceof CBang || card instanceof CIndians || card instanceof CPrison || card instanceof CCatBalou) {

                if (!card.playCard(arg_listPlayers, arg_listTrashCards)) {
                    System.out.println("Bad input");
                } else {
                    arg_listTrashCards.add(card);
                    this.listCards.remove(card);
                }
            }
            else
            {
                System.out.println("This type of card can't be used to attack an player");
            }

        }

    }

    private void printCardsInHand()
    {
        System.out.println("Cards in hand: ");

        int idCard = 0;
        for (CCard card : this.listCards)
        {
            System.out.println(idCard + ": " + card.getClass().getSimpleName());
            idCard++;
        }
    }

    private void printCardsInFront()
    {
        System.out.println("Cards in front: ");

        int idCard = 0;
        for (CCard card : this.listCardsInFront)
        {
            System.out.println(idCard + ": " + card.getClass().getSimpleName());
            idCard++;
        }
    }

    int checkUserChoosenCardIndex(int cardIndex, List<CCard> arg_listCards)
    {
        if (cardIndex == Integer.MIN_VALUE || cardIndex < 0 ||  cardIndex > arg_listCards.size() - 1)
            return Integer.MIN_VALUE;

        return cardIndex;
    }

    public void attackedByCard(CCard arg_Card, List<CCard> arg_listCardsTrash)
    {
        if (arg_Card instanceof CBang)
        {
            boolean wasMissed = false;
            CBarrel barrelCard = getBarrelCard();

            if (barrelCard != null)
            {
                Random rand = new Random();
                // 0 - 3
                int randomNumber = rand.nextInt(4);
                int randomNumber2 = rand.nextInt(4);

                if (randomNumber == randomNumber2)
                {
                    wasMissed = true;
                    System.out.println("Player ID: " + arg_Card.getCardOwner().getId() + " tried to attack player " + this.ID + " with Bang but was not successful (blocked by Barrel card)");
                }
                else
                {
                    wasMissed = false;
                    System.out.println("Player ID: " + arg_Card.getCardOwner().getId() + " attacked player ID: " + this.ID + " which have a Barrel but blocking with Barrel was not successful, looking for Missed card in hand ");
                }
            }

            if (!wasMissed)
            {
                CMissed missedCard = getMissedCard();

                if (missedCard != null)
                {
                    if (!this.listCards.remove(missedCard))
                    {
                        this.listCardsInFront.remove(missedCard);
                    }

                    System.out.println("Player ID: " + arg_Card.getCardOwner().getId() + " attacked player ID: " + this.ID + " with Bang but attack was not successful (blocked by Missed card)");
                    arg_listCardsTrash.add(missedCard);
                    arg_listCardsTrash.add(arg_Card);
                }
                else
                {
                    this.lifeCount -= 1;
                    System.out.println("Player ID: " + arg_Card.getCardOwner().getId() + " lowered HP of player ID: " + this.ID + " by 1 with Bang card due lack of Missed card or having a Barrel in front");
                    arg_listCardsTrash.add(arg_Card);
                }
            }
        }

        if (arg_Card instanceof CIndians)
        {
            CCard cardBang = getCBangCard();

            if (cardBang == null)
            {
                this.lifeCount -= 1;
                System.out.println("Player ID: " + arg_Card.getCardOwner().getId() + " lowered HP of player " + this.ID + " by 1 point");
            }
            else
            {
                if (!this.listCards.remove(cardBang))
                {
                    this.listCardsInFront.remove(cardBang);
                }

                System.out.println("Player ID: " + arg_Card.getCardOwner().getId() + " attacked with card Indians but player ID: " + this.ID + " had Bang card, so no HP of player is lost");
            }
        }

        if (this.lifeCount <= 0) {
            this.setIsAlive(false);
            System.out.println("Player with ID: " + this.ID + " have died");
        }
    }
}
