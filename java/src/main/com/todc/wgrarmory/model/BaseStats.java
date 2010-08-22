/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */
package com.todc.wgrarmory.model;


/**
 * Base stats such as Strength, Agility, Spell Power, Attack Power, etc for a
 * PlayerCharacter.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class BaseStats {


    // ----------------------------------------------------- Instance Variables


    private int m_health;
    private int m_mana;
    private int m_strength;
    private int m_agility;
    private int m_stamina;
    private int m_intellect;
    private int m_spirit;

    private int m_arcaneResist;
    private int m_fireResist;
    private int m_frostResist;
    private int m_holyResist;
    private int m_natureResist;
    private int m_shadowResist;

    private int m_spellDamage;
    private int m_spellHealing;
    private int m_spellHitRating;
    private double m_spellHitPercent;
    private int m_spellCritRating;
    private double m_spellCritPercent;
    private int m_spellPenetration;
    private int m_spellHasteRating;
    private double m_spellHastePercent;
    private double m_mp5Casting;
    private double m_mp5NotCasting;

    private int m_armor;
    private int m_defense;
    private int m_dodgeRating;
    private double m_dodgePercent;
    private int m_parryRating;
    private double m_parryPercent;
    private int m_blockRating;
    private double m_blockPercent;
    private int m_resilience;

    private double m_rangedDps;
    private int m_rangedMaxDamage;
    private int m_rangedMinDamage;
    private double m_rangedSpeed;
    private int m_rangedHasteRating;
    private double m_rangedHastePercent;
    private int m_rangedPower;
    private int m_rangedHitRating;
    private double m_rangedHitPercent;
    private int m_rangedPenetration;
    private double m_rangedReducedArmorPct;
    private int m_rangedCritRating;
    private double m_rangedCritPercent;

    private double m_meleeDps;
    private int m_meleeMaxDamage;
    private int m_meleeMinDamage;
    private double m_meleeSpeed;
    private int m_meleeHasteRating;
    private double m_meleeHastePercent;
    private int m_meleePower;
    private int m_meleeHitRating;
    private double m_meleeHitPercent;
    private int m_meleePenetration;
    private double m_meleeReducedArmorPct;
    private int m_meleeCritRating;
    private double m_meleeCritPercent;
    private int m_meleeExpertise;


    // ------------------------------------------------------ Getters / Setters


    public int getHealth() {
        return m_health;
    }

    public void setHealth(int health) {
        m_health = health;
    }

    public int getMana() {
        return m_mana;
    }

    public void setMana(int mana) {
        m_mana = mana;
    }

    public int getStrength() {
        return m_strength;
    }

    public void setStrength(int strength) {
        m_strength = strength;
    }

    public int getAgility() {
        return m_agility;
    }

    public void setAgility(int agility) {
        m_agility = agility;
    }

    public int getStamina() {
        return m_stamina;
    }

    public void setStamina(int stamina) {
        m_stamina = stamina;
    }

    public int getIntellect() {
        return m_intellect;
    }

    public void setIntellect(int intellect) {
        m_intellect = intellect;
    }

    public int getSpirit() {
        return m_spirit;
    }

    public void setSpirit(int spirit) {
        m_spirit = spirit;
    }

    public int getArcaneResist() {
        return m_arcaneResist;
    }

    public void setArcaneResist(int arcaneResist) {
        m_arcaneResist = arcaneResist;
    }

    public int getFireResist() {
        return m_fireResist;
    }

    public void setFireResist(int fireResist) {
        m_fireResist = fireResist;
    }

    public int getFrostResist() {
        return m_frostResist;
    }

    public void setFrostResist(int frostResist) {
        m_frostResist = frostResist;
    }

    public int getHolyResist() {
        return m_holyResist;
    }

    public void setHolyResist(int holyResist) {
        m_holyResist = holyResist;
    }

    public int getNatureResist() {
        return m_natureResist;
    }

    public void setNatureResist(int natureResist) {
        m_natureResist = natureResist;
    }

    public int getShadowResist() {
        return m_shadowResist;
    }

    public void setShadowResist(int shadowResist) {
        m_shadowResist = shadowResist;
    }

    public int getSpellDamage() {
        return m_spellDamage;
    }

    public void setSpellDamage(int spellDamage) {
        m_spellDamage = spellDamage;
    }

    public int getSpellHealing() {
        return m_spellHealing;
    }

    public void setSpellHealing(int spellHealing) {
        m_spellHealing = spellHealing;
    }

    public int getSpellHitRating() {
        return m_spellHitRating;
    }

    public void setSpellHitRating(int spellHitRating) {
        m_spellHitRating = spellHitRating;
    }

    public double getSpellHitPercent() {
        return m_spellHitPercent;
    }

    public void setSpellHitPercent(double spellHitPercent) {
        m_spellHitPercent = spellHitPercent;
    }

    public int getSpellCritRating() {
        return m_spellCritRating;
    }

    public void setSpellCritRating(int spellCritRating) {
        m_spellCritRating = spellCritRating;
    }

    public double getSpellCritPercent() {
        return m_spellCritPercent;
    }

    public void setSpellCritPercent(double spellCritPercent) {
        m_spellCritPercent = spellCritPercent;
    }

    public int getSpellPenetration() {
        return m_spellPenetration;
    }

    public void setSpellPenetration(int spellPenetration) {
        m_spellPenetration = spellPenetration;
    }

    public int getSpellHasteRating() {
        return m_spellHasteRating;
    }

    public void setSpellHasteRating(int spellHasteRating) {
        m_spellHasteRating = spellHasteRating;
    }

    public double getSpellHastePercent() {
        return m_spellHastePercent;
    }

    public void setSpellHastePercent(double spellHastePercent) {
        m_spellHastePercent = spellHastePercent;
    }

    public double getMp5Casting() {
        return m_mp5Casting;
    }

    public void setMp5Casting(double mp5Casting) {
        m_mp5Casting = mp5Casting;
    }

    public double getMp5NotCasting() {
        return m_mp5NotCasting;
    }

    public void setMp5NotCasting(double mp5NotCasting) {
        m_mp5NotCasting = mp5NotCasting;
    }

    public int getArmor() {
        return m_armor;
    }

    public void setArmor(int armor) {
        m_armor = armor;
    }

    public int getDefense() {
        return m_defense;
    }

    public void setDefense(int defense) {
        m_defense = defense;
    }

    public int getDodgeRating() {
        return m_dodgeRating;
    }

    public void setDodgeRating(int dodgeRating) {
        m_dodgeRating = dodgeRating;
    }

    public double getDodgePercent() {
        return m_dodgePercent;
    }

    public void setDodgePercent(double dodgePercent) {
        m_dodgePercent = dodgePercent;
    }

    public int getParryRating() {
        return m_parryRating;
    }

    public void setParryRating(int parryRating) {
        m_parryRating = parryRating;
    }

    public double getParryPercent() {
        return m_parryPercent;
    }

    public void setParryPercent(double parryPercent) {
        m_parryPercent = parryPercent;
    }

    public int getBlockRating() {
        return m_blockRating;
    }

    public void setBlockRating(int blockRating) {
        m_blockRating = blockRating;
    }

    public double getBlockPercent() {
        return m_blockPercent;
    }

    public void setBlockPercent(double blockPercent) {
        m_blockPercent = blockPercent;
    }

    public int getResilience() {
        return m_resilience;
    }

    public void setResilience(int resilience) {
        m_resilience = resilience;
    }

    public double getRangedDps() {
        return m_rangedDps;
    }

    public void setRangedDps(double rangedDps) {
        m_rangedDps = rangedDps;
    }

    public int getRangedMaxDamage() {
        return m_rangedMaxDamage;
    }

    public void setRangedMaxDamage(int rangedMaxDamage) {
        m_rangedMaxDamage = rangedMaxDamage;
    }

    public int getRangedMinDamage() {
        return m_rangedMinDamage;
    }

    public void setRangedMinDamage(int rangedMinDamage) {
        m_rangedMinDamage = rangedMinDamage;
    }

    public double getRangedSpeed() {
        return m_rangedSpeed;
    }

    public void setRangedSpeed(double rangedSpeed) {
        m_rangedSpeed = rangedSpeed;
    }

    public int getRangedHasteRating() {
        return m_rangedHasteRating;
    }

    public void setRangedHasteRating(int rangedHasteRating) {
        m_rangedHasteRating = rangedHasteRating;
    }

    public double getRangedHastePercent() {
        return m_rangedHastePercent;
    }

    public void setRangedHastePercent(double rangedHastePercent) {
        m_rangedHastePercent = rangedHastePercent;
    }

    public int getRangedPower() {
        return m_rangedPower;
    }

    public void setRangedPower(int rangedPower) {
        m_rangedPower = rangedPower;
    }

    public int getRangedHitRating() {
        return m_rangedHitRating;
    }

    public void setRangedHitRating(int rangedHitRating) {
        m_rangedHitRating = rangedHitRating;
    }

    public double getRangedHitPercent() {
        return m_rangedHitPercent;
    }

    public void setRangedHitPercent(double rangedHitPercent) {
        m_rangedHitPercent = rangedHitPercent;
    }

    public int getRangedPenetration() {
        return m_rangedPenetration;
    }

    public void setRangedPenetration(int rangedPenetration) {
        m_rangedPenetration = rangedPenetration;
    }

    public double getRangedReducedArmorPct() {
        return m_rangedReducedArmorPct;
    }

    public void setRangedReducedArmorPct(double rangedReducedArmorPct) {
        m_rangedReducedArmorPct = rangedReducedArmorPct;
    }

    public int getRangedCritRating() {
        return m_rangedCritRating;
    }

    public void setRangedCritRating(int rangedCritRating) {
        m_rangedCritRating = rangedCritRating;
    }

    public double getRangedCritPercent() {
        return m_rangedCritPercent;
    }

    public void setRangedCritPercent(double rangedCritPercent) {
        m_rangedCritPercent = rangedCritPercent;
    }

    public double getMeleeDps() {
        return m_meleeDps;
    }

    public void setMeleeDps(double meleeDps) {
        m_meleeDps = meleeDps;
    }

    public int getMeleeMaxDamage() {
        return m_meleeMaxDamage;
    }

    public void setMeleeMaxDamage(int meleeMaxDamage) {
        m_meleeMaxDamage = meleeMaxDamage;
    }

    public int getMeleeMinDamage() {
        return m_meleeMinDamage;
    }

    public void setMeleeMinDamage(int meleeMinDamage) {
        m_meleeMinDamage = meleeMinDamage;
    }

    public double getMeleeSpeed() {
        return m_meleeSpeed;
    }

    public void setMeleeSpeed(double meleeSpeed) {
        m_meleeSpeed = meleeSpeed;
    }

    public int getMeleeHasteRating() {
        return m_meleeHasteRating;
    }

    public void setMeleeHasteRating(int meleeHasteRating) {
        m_meleeHasteRating = meleeHasteRating;
    }

    public double getMeleeHastePercent() {
        return m_meleeHastePercent;
    }

    public void setMeleeHastePercent(double meleeHastePercent) {
        m_meleeHastePercent = meleeHastePercent;
    }

    public int getMeleePower() {
        return m_meleePower;
    }

    public void setMeleePower(int meleePower) {
        m_meleePower = meleePower;
    }

    public int getMeleeHitRating() {
        return m_meleeHitRating;
    }

    public void setMeleeHitRating(int meleeHitRating) {
        m_meleeHitRating = meleeHitRating;
    }

    public double getMeleeHitPercent() {
        return m_meleeHitPercent;
    }

    public void setMeleeHitPercent(double meleeHitPercent) {
        m_meleeHitPercent = meleeHitPercent;
    }

    public int getMeleePenetration() {
        return m_meleePenetration;
    }

    public void setMeleePenetration(int meleePenetration) {
        m_meleePenetration = meleePenetration;
    }

    public double getMeleeReducedArmorPct() {
        return m_meleeReducedArmorPct;
    }

    public void setMeleeReducedArmorPct(double meleeReducedArmorPct) {
        m_meleeReducedArmorPct = meleeReducedArmorPct;
    }

    public int getMeleeCritRating() {
        return m_meleeCritRating;
    }

    public void setMeleeCritRating(int meleeCritRating) {
        m_meleeCritRating = meleeCritRating;
    }

    public double getMeleeCritPercent() {
        return m_meleeCritPercent;
    }

    public void setMeleeCritPercent(double meleeCritPercent) {
        m_meleeCritPercent = meleeCritPercent;
    }

    public int getMeleeExpertise() {
        return m_meleeExpertise;
    }

    public void setMeleeExpertise(int meleeExpertise) {
        m_meleeExpertise = meleeExpertise;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("hp = " + m_health + "\n");
        sb.append("mana = " + m_mana + "\n");
        sb.append("str = " + m_strength + "\n");
        sb.append("agi = " + m_agility + "\n");
        sb.append("sta = " + m_stamina + "\n");
        sb.append("int = " + m_intellect + "\n");
        sb.append("spi = " + m_spirit + "\n");
        sb.append("\n");
        sb.append("arcane resist = " + m_arcaneResist + "\n");
        sb.append("fire resist = " + m_fireResist + "\n");
        sb.append("frost resist = " + m_frostResist + "\n");
        sb.append("holy resist = " + m_holyResist + "\n");
        sb.append("nature resist = " + m_natureResist + "\n");
        sb.append("shadow resist = " + m_shadowResist + "\n");
        sb.append("\n");
        sb.append("spell dmg = " + m_spellDamage + "\n");
        sb.append("spell healing = " + m_spellHealing + "\n");
        sb.append("spell hit = " + m_spellHitRating + "\n");
        sb.append("spell hit % = " + m_spellHitPercent + "\n");
        sb.append("spell crit = " + m_spellCritRating + "\n");
        sb.append("spell crit % = " + m_spellCritPercent + "\n");
        sb.append("spell pen = " + m_spellPenetration + "\n");
        sb.append("spell haste = " + m_spellHasteRating + "\n");
        sb.append("spell haste % = " + m_spellHastePercent + "\n");
        sb.append("mp5 casting = " + m_mp5Casting + "\n");
        sb.append("mp5 not casting = " + m_mp5NotCasting + "\n");
        sb.append("\n");
        sb.append("armor = " + m_armor + "\n");
        sb.append("defense = " + m_defense + "\n");
        sb.append("dodge = " + m_dodgeRating + "\n");
        sb.append("dodge % = " + m_dodgePercent + "\n");
        sb.append("parry = " + m_parryRating + "\n");
        sb.append("parry % = " + m_parryPercent + "\n");
        sb.append("block = " + m_blockRating + "\n");
        sb.append("block % = " + m_blockPercent + "\n");
        sb.append("resil = " + m_resilience + "\n");
        sb.append("\n");
        sb.append("ranged dps = " + m_rangedDps + "\n");
        sb.append("ranged max = " + m_rangedMaxDamage + "\n");
        sb.append("ranged min = " + m_rangedMinDamage + "\n");
        sb.append("ranged speed = " + m_rangedSpeed + "\n");
        sb.append("ranged haste = " + m_rangedHasteRating + "\n");
        sb.append("ranged haste % = " + m_rangedHastePercent + "\n");
        sb.append("ranged AP = " + m_rangedPower + "\n");
        sb.append("ranged hit = " + m_rangedHitRating + "\n");
        sb.append("ranged hit % = " + m_rangedHitPercent + "\n");
        sb.append("ranged pen = " + m_rangedPenetration + "\n");
        sb.append("ranged pen % = " + m_rangedReducedArmorPct + "\n");
        sb.append("ranged crit = " + m_rangedCritRating + "\n");
        sb.append("ranged crit % = " + m_rangedCritPercent + "\n");
        sb.append("\n");
        sb.append("melee dps = " + m_meleeDps + "\n");
        sb.append("melee max = " + m_meleeMaxDamage + "\n");
        sb.append("melee min = " + m_meleeMinDamage + "\n");
        sb.append("melee speed = " + m_meleeSpeed + "\n");
        sb.append("melee haste = " + m_meleeHasteRating + "\n");
        sb.append("melee haste % = " + m_meleeHastePercent + "\n");
        sb.append("melee AP = " + m_meleePower + "\n");
        sb.append("melee hit = " + m_meleeHitRating + "\n");
        sb.append("melee hit % = " + m_meleeHitPercent + "\n");
        sb.append("melee pen = " + m_meleePenetration + "\n");
        sb.append("melee pen % = " + m_meleeReducedArmorPct + "\n");
        sb.append("melee crit = " + m_meleeCritRating + "\n");
        sb.append("melee crit % = " + m_meleeCritPercent + "\n");
        sb.append("melee exp = " + m_meleeExpertise + "\n");

        return sb.toString();
    }
}