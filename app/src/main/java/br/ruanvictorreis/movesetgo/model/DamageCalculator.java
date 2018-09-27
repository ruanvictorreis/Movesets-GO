package br.ruanvictorreis.movesetgo.model;

/**
 * Class to compute damage from moveset.
 * Created by Ruan on 16/09/2017.
 */

public class DamageCalculator {

    public Double weaveDamage(Moveset moveset) {
        return firstExpression(moveset) * secondExpression(moveset)
                + Math.ceil(thirdExpression(moveset)) * fourthExpression(moveset)
                + fifthExpression(moveset) * fourthExpression(moveset);
    }

    public Double noWeaveDamage(Moveset moveset) {
        Double duration = moveset.getBasicMove().getDuration();
        return fourthExpression(moveset) * Math.floor(100000 / duration);
    }

    public Double gymWeaveDamage(Moveset moveset) {
        return sixthExpression(moveset) * secondExpression(moveset)
                + Math.ceil(seventhExpression(moveset)) * fourthExpression(moveset)
                + eighthExpression(moveset) * fourthExpression(moveset);
    }

    private Double firstExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargedMove().getSpentEnergy();
        Double gainedEnergy = moveset.getBasicMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        Double durationBasicMove = moveset.getBasicMove().getDuration();
        Double durationChargedMove = moveset.getChargedMove().getDuration();
        weaveCycle = weaveCycle * durationBasicMove + (durationChargedMove + 0);

        return Math.floor(100000 / weaveCycle);
    }

    private Double secondExpression(Moveset moveset) {
        Pokemon pokemon = moveset.getPokemon();
        ChargedMove chargedMove = moveset.getChargedMove();

        Double typeFactor = 1d;
        Double damageChargeAtt = chargedMove.getDamage();
        Double criticalChargeAtt = chargedMove.getCritical();
        Double critiaclDamBonus = 0d;

        if (pokemon.containsType(chargedMove.getType())) {
            typeFactor += 0.2d;
        }

        return damageChargeAtt * typeFactor * (1 + (critiaclDamBonus * criticalChargeAtt / 100));
    }

    private Double thirdExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargedMove().getSpentEnergy();
        Double gainedEnergy = moveset.getBasicMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        return Math.ceil(firstExpression(moveset)) * weaveCycle;
    }

    private Double fourthExpression(Moveset moveset) {
        Pokemon pokemon = moveset.getPokemon();
        BasicMove basicMove = moveset.getBasicMove();

        Double typeFactor = 1d;
        if (pokemon.containsType(basicMove.getType())) {
            typeFactor += 0.2d;
        }

        return basicMove.getDamage() * typeFactor;
    }

    private Double fifthExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargedMove().getSpentEnergy();
        Double gainedEnergy = moveset.getBasicMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        Double durationBasicAtt = moveset.getBasicMove().getDuration();
        Double durationChargedAtt = moveset.getChargedMove().getDuration();

        return Math.floor((100000 - (firstExpression(moveset) * (durationChargedAtt + 0)
                + Math.ceil(firstExpression(moveset) * weaveCycle * durationBasicAtt))) / durationBasicAtt);
    }

    private Double sixthExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargedMove().getSpentEnergy();
        Double gainedEnergy = moveset.getBasicMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        Double durationBasicMove = moveset.getBasicMove().getDuration();
        Double durationChargedMove = moveset.getChargedMove().getDuration();
        weaveCycle = weaveCycle * (durationBasicMove + 2000) + (durationChargedMove + 0);

        return Math.floor(100000 / weaveCycle);
    }

    private Double seventhExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargedMove().getSpentEnergy();
        Double gainedEnergy = moveset.getBasicMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        return Math.ceil(sixthExpression(moveset)) * weaveCycle;
    }

    private Double eighthExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargedMove().getSpentEnergy();
        Double gainedEnergy = moveset.getBasicMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        Double durationBasicAtt = moveset.getBasicMove().getDuration();
        Double durationChargedAtt = moveset.getChargedMove().getDuration();

        return Math.floor((100000 - (sixthExpression(moveset) * (durationChargedAtt + 0)
                + Math.ceil(sixthExpression(moveset) * weaveCycle * (durationBasicAtt + 2000))))
                / (durationBasicAtt + 2000));
    }
}
