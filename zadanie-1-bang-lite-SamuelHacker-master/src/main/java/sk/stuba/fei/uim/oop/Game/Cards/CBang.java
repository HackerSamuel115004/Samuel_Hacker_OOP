package sk.stuba.fei.uim.oop.Game.Cards;
import sk.stuba.fei.uim.oop.Game.KeyboardInput.CKeyboardInput;
import sk.stuba.fei.uim.oop.Game.Players.CPlayer;


import java.util.List;

/*
Karty Bang predstavujú hlavný spôsob, ako znížiť počet životov svojim protihráčom.
V našej zjednodušenej verzií hry môžete počas svojho ťahu týchto kariet zahrať neobmedzený počet.

Pokiaľ ste cielom karty Bang, automaticky zahráte kartu vedľa, pokiaľ žiadnu nemáte, strácate život.
Pokiaľ stratíte všetky životy, vypadávate z hry.

Príklad ťahu v našej zjednodušenej verzií hry: Hráč A zahrá kartu Bang na Hráča B, Hráč B automaticky skontroluje či má kartu vedľa,
pokiaľ takú kartu má, karta sa automaticky zahrá, pokiaľ takú kartu nemá, prichádza o život.
 */

public class CBang extends CCard
{
    CPlayer PlayerToAttack = null;

    public CBang() {super();}
    public CBang(CPlayer arg_CardOwner) {
        super(arg_CardOwner);
        super.cardType = TypeOfCard.brown;
    }

    @Override
    public boolean playCard(List<CPlayer> arg_listPlayers, List<CCard> arg_listTrashCards)
   {
       CPlayer  playerToAttack = null;

       System.out.println("Choose ID of player on which you want to use Bang card:");

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

           if (playerToAttack == null || playerToAttack == getCardOwner())
           {
               System.out.println("You do not chose correct ID, repeat action");
               return false;
           }

       }
       else
       {
           System.out.println("You did not enter the correct numerical input, try again");
           return false;
       }

       playerToAttack.attackedByCard(this, arg_listTrashCards);

       return true;
   }
}
