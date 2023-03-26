package sk.stuba.fei.uim.oop.Game.Cards;

import sk.stuba.fei.uim.oop.Game.Players.CPlayer;

/*
Barrel
Barrel je karta, ktorá vám dáva šancu skryť sa pred útokom karty BANG. Šanca na skytie je 1 ku 4.
Príklad ťahu: Niektorí z hráčov na vás vystrelí kartou BANG, a vy máte pred sebou Barrel, môžete skontrolovať jeho efekt, a môže sa vám podariť sa vyhnúť výstrelu,
pokiaľ sa vám podarí sa mu vyhnúť, dalšie vyhodenie karty vedľa nie je potrebné, ak sa mu nevyhnete, hra ďalej automaticky kontroluje či máte kartu vedľa.
*/
public class CBarrel extends CCard
{
    public CBarrel()
    {
        super();
    }
    public CBarrel(CPlayer arg_CardOwner)
    {
        super(arg_CardOwner);
        super.cardType = TypeOfCard.blue;
    }
}
