package com.todc.wgrarmory.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author <a href="mailto:odonnellt@gmail.com">Tim O'Donnell</a>
 */
public class Achievement {


    // ----------------------------------------------------- Instance Variables


    private int m_id;
    private int m_parentId;
    private String m_title;
    private String m_description;
    private Date m_completed;

    private List<Achievement> m_subAchievements = new ArrayList<Achievement>();
    private List<Achievement> m_criteria = new ArrayList<Achievement>();


    // ------------------------------------------------------ Getters / Setters


    public int getId() {
        return m_id;
    }

    public void setId(int id) {
        m_id = id;
    }

    public String getTitle() {
        return m_title;
    }

    public void setTitle(String title) {
        m_title = title;
    }

    public String getDescription() {
        return m_description;
    }

    public void setDescription(String description) {
        m_description = description;
    }

    public Date getCompleted() {
        return m_completed;
    }

    public void setCompleted(Date completed) {
        m_completed = completed;
    }

    public int getParentId() {
        return m_parentId;
    }

    public void setParentId(int parentId) {
        m_parentId = parentId;
    }

    public List<Achievement> getSubAchievements() {
        return m_subAchievements;
    }

    public void setSubAchievements(List<Achievement> subAchievements) {
        m_subAchievements = subAchievements;
    }

    public List<Achievement> getCriteria() {
        return m_criteria;
    }

    public void setCriteria(List<Achievement> criteria) {
        m_criteria = criteria;
    }

    
    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("id = " + m_id + "; ");
        sb.append("parentId = " + m_parentId + "; ");
        sb.append("title = " + m_title + "; ");
        sb.append("description = " + m_description + "; ");
        sb.append("completed = " + m_completed + "; ");
        sb.append("criteria = [");

        for (Achievement a : m_criteria) {
            sb.append(a + ", ");
        }
        sb.append("]; ");

        sb.append("subAchievements = [");

        for (Achievement a : m_subAchievements) {
            sb.append(a + ", ");
        }
        sb.append("]");

        sb.append("]");

        return sb.toString();
    }
}
