package com.todc.wgrarmory.model;


import java.util.Date;


/**
 * Models an entry in a character's activity feed. Returned from
 * {@link com.todc.wgrarmory.Armory#fetchCharacterFeed}.
 *
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class FeedEntry {


    // ----------------------------------------------------- Instance Variables


    private String m_title;
    private Date m_updated;
    private Date m_published;
    private String m_id;
    private String m_link;
    private String m_content;


    // ------------------------------------------------------ Getters / Setters


    public String getTitle() {
        return m_title;
    }

    public void setTitle(String title) {
        m_title = title;
    }

    public Date getUpdated() {
        return m_updated;
    }

    public void setUpdated(Date updated) {
        m_updated = updated;
    }

    public Date getPublished() {
        return m_published;
    }

    public void setPublished(Date published) {
        m_published = published;
    }

    public String getId() {
        return m_id;
    }

    public void setId(String id) {
        m_id = id;
    }

    public String getLink() {
        return m_link;
    }

    public void setLink(String link) {
        m_link = link;
    }

    public String getContent() {
        return m_content;
    }

    public void setContent(String content) {
        m_content = content;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append("title = " + m_title + "; ");
        sb.append("updated = " + m_updated + "; ");
        sb.append("published = " + m_published + "; ");
        sb.append("id = " + m_id + "; ");
        sb.append("link = " + m_link + "; ");
        sb.append("content = " + m_content);
        sb.append("]");

        return sb.toString();
    }

}
