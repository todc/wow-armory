package com.todc.wgrarmory.model;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class Item {


    // ----------------------------------------------------- Instance Variables


    private int m_id;
    private String m_name;
    private int m_rarity;
    private int m_itemLevel;
    private int m_areaId;
    private String m_areaKey;
    private int m_creatureId;
    private String m_difficulty;

    private int m_slot;
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

    public int getItemLevel() {
        return m_itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        m_itemLevel = itemLevel;
    }

    public int getAreaId() {
        return m_areaId;
    }

    public void setAreaId(int areaId) {
        m_areaId = areaId;
    }

    public String getAreaKey() {
        return m_areaKey;
    }

    public void setAreaKey(String areaKey) {
        m_areaKey = areaKey;
    }

    public int getCreatureId() {
        return m_creatureId;
    }

    public void setCreatureId(int creatureId) {
        m_creatureId = creatureId;
    }

    public String getDifficulty() {
        return m_difficulty;
    }

    public void setDifficulty(String difficulty) {
        m_difficulty = difficulty;
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
