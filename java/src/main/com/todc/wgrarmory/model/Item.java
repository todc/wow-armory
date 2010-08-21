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
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class Item {


    // ----------------------------------------------------- Instance Variables


    private int m_id;
    private String m_name;
    private int m_slot;
    private int m_rarity;
    private int m_gem0Id;
    private int m_gem1Id;
    private int m_gem2Id;
    private int m_enchantId;


    // ------------------------------------------------------ Getters / Setters


    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    /**
     * Get the armory slot number for this item.
     * NOTE: On the Armory, equipment slots start at zero (0), but in-game they
     * start at one (1).
     *
     * @return Slot number, starting at zero (0).
     */
    public int getSlot() {
        return m_slot;
    }

    public void setSlot(int slot) {
        m_slot = slot;
    }

    public int getRarity() {
        return m_rarity;
    }

    public void setRarity(int rarity) {
        m_rarity = rarity;
    }

    public int getGem0Id() {
        return m_gem0Id;
    }

    public void setGem0Id(int gem0Id) {
        m_gem0Id = gem0Id;
    }

    public int getGem1Id() {
        return m_gem1Id;
    }

    public void setGem1Id(int gem1Id) {
        m_gem1Id = gem1Id;
    }

    public int getGem2Id() {
        return m_gem2Id;
    }

    public void setGem2Id(int gem2Id) {
        m_gem2Id = gem2Id;
    }

    public int getEnchantId() {
        return m_enchantId;
    }

    public void setEnchantId(int enchantId) {
        m_enchantId = enchantId;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("id = " + m_id + "; ");
        sb.append("name = " + m_name + "; ");
        sb.append("slot = " + m_slot + "; ");
        sb.append("rarity = " + m_rarity + "; ");
        sb.append("gem0Id = " + m_gem0Id + "; ");
        sb.append("gem1Id = " + m_gem1Id + "; ");
        sb.append("gem2Id = " + m_gem2Id + "; ");
        sb.append("enchantId = " + m_enchantId);
        sb.append("]");

        return sb.toString();
    }
}
