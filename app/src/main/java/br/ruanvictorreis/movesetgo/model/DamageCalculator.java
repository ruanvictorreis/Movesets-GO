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
        Double duration = moveset.getFastMove().getDuration();
        return fourthExpression(moveset) * Math.floor(100000 / duration);
    }

    public Double gymWeaveDamage(Moveset moveset) {
        return sixthExpression(moveset) * secondExpression(moveset)
                + Math.ceil(seventhExpression(moveset)) * fourthExpression(moveset)
                + eighthExpression(moveset) * fourthExpression(moveset);
    }

    private Double firstExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargeMove().getSpentEnergy();
        Double gainedEnergy = moveset.getFastMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        Double durationBasicMove = moveset.getFastMove().getDuration();
        Double durationChargedMove = moveset.getChargeMove().getDuration();
        weaveCycle = weaveCycle * durationBasicMove + (durationChargedMove + 0);

        return Math.floor(100000 / weaveCycle);
    }

    private Double secondExpression(Moveset moveset) {
        Pokemon pokemon = moveset.getPokemon();
        ChargeMove chargeMove = moveset.getChargeMove();

        Double typeFactor = 1d;
        Double damageChargeAtt = chargeMove.getDamage();
        Double criticalChargeAtt = chargeMove.getCritical();
        Double critiaclDamBonus = 0d;

        if (pokemon.containsType(chargeMove.getType())) {
            typeFactor += 0.2d;
        }

        return damageChargeAtt * typeFactor * (1 + (critiaclDamBonus * criticalChargeAtt / 100));
    }

    private Double thirdExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargeMove().getSpentEnergy();
        Double gainedEnergy = moveset.getFastMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        return Math.ceil(firstExpression(moveset)) * weaveCycle;
    }

    private Double fourthExpression(Moveset moveset) {
        Pokemon pokemon = moveset.getPokemon();
        FastMove fastMove = moveset.getFastMove();

        Double typeFactor = 1d;
        if (pokemon.containsType(fastMove.getType())) {
            typeFactor += 0.2d;
        }

        return fastMove.getDamage() * typeFactor;
    }

    private Double fifthExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargeMove().getSpentEnergy();
        Double gainedEnergy = moveset.getFastMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        Double durationBasicAtt = moveset.getFastMove().getDuration();
        Double durationChargedAtt = moveset.getChargeMove().getDuration();

        return Math.floor((100000 - (firstExpression(moveset) * (durationChargedAtt + 0)
                + Math.ceil(firstExpression(moveset) * weaveCycle * durationBasicAtt))) / durationBasicAtt);
    }

    private Double sixthExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargeMove().getSpentEnergy();
        Double gainedEnergy = moveset.getFastMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        Double durationBasicMove = moveset.getFastMove().getDuration();
        Double durationChargedMove = moveset.getChargeMove().getDuration();
        weaveCycle = weaveCycle * (durationBasicMove + 2000) + (durationChargedMove + 0);

        return Math.floor(100000 / weaveCycle);
    }

    private Double seventhExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargeMove().getSpentEnergy();
        Double gainedEnergy = moveset.getFastMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        return Math.ceil(sixthExpression(moveset)) * weaveCycle;
    }

    private Double eighthExpression(Moveset moveset) {
        Double weaveCycle;
        Double spentEnergy = moveset.getChargeMove().getSpentEnergy();
        Double gainedEnergy = moveset.getFastMove().getEnergy();

        if (spentEnergy.equals(100d)) {
            weaveCycle = Math.ceil(spentEnergy / gainedEnergy);
        } else {
            weaveCycle = spentEnergy / gainedEnergy;
        }

        Double durationBasicAtt = moveset.getFastMove().getDuration();
        Double durationChargedAtt = moveset.getChargeMove().getDuration();

        return Math.floor((100000 - (sixthExpression(moveset) * (durationChargedAtt + 0)
                + Math.ceil(sixthExpression(moveset) * weaveCycle * (durationBasicAtt + 2000))))
                / (durationBasicAtt + 2000));
    }
}
