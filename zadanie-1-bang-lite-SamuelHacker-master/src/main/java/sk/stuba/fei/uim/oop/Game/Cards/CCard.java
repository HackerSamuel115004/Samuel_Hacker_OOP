package sk.stuba.fei.uim.oop.Game.Cards;

import sk.stuba.fei.uim.oop.Game.Players.CPlayer;

import java.util.List;

public class CCard
{
    enum TypeOfCard { blue, brown};

    protected TypeOfCard cardType;
    private CPlayer cardOwner;

    private CPlayer cardReceiver;

    public CCard(CPlayer arg_CardOwner)
    {
        this.cardOwner = arg_CardOwner;
    }

    public CCard(){super();}

    public void removeCardOwner()
    {
        this.cardOwner = null;
    }

    public void setCardOwner(CPlayer player)
    {
        this.cardOwner = player;
    }

    public CPlayer getCardOwner()
    {
        return this.cardOwner;
    }

    public void setCardReceiver(CPlayer player)
    {
        this.cardReceiver = player;
    }

    public CPlayer getCardReceiver()
    {
        return this.cardReceiver;
    }

    public boolean isOwnerCard(CPlayer arg_player)
    {
        return arg_player.getId() == this.cardOwner.getId();
    }

    public boolean playCard() { return true;}

    public boolean playCard(List<CPlayer> arg_ListPlayers, List<CCard> arg_listTrashCards) { return true;}
}
