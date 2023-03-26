package sk.stuba.fei.uim.oop.Game.Cards;

/*
Karty Bang predstavujú hlavný spôsob, ako znížiť počet životov svojim protihráčom.
V našej zjednodušenej verzií hry môžete počas svojho ťahu týchto kariet zahrať neobmedzený počet.
Pokiaľ ste cielom karty Bang, automaticky zahráte kartu vedľa, pokiaľ žiadnu nemáte, strácate život.
Pokiaľ stratíte všetky životy, vypadávate z hry.
Príklad ťahu v našej zjednodušenej verzií hry: Hráč A zahrá kartu Bang na Hráča B, Hráč B automaticky skontroluje
či má kartu vedľa, pokiaľ takú kartu má, karta sa automaticky zahrá, pokiaľ takú kartu nemá, prichádza o život.
 */

import sk.stuba.fei.uim.oop.Game.Players.CPlayer;

public class CMissed extends  CCard
{
     public CMissed() {super();}
    CMissed(CPlayer arg_CardOwner) {
        super(arg_CardOwner);
        super.cardType = TypeOfCard.brown;
    }
}

