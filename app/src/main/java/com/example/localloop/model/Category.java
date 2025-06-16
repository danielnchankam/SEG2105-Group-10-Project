package com.example.localloop.model;

/** Plain data class for a category row. */
public class Category {
    private int id;          // primary key
    private String name;
    private String description;

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public Category(String name, String description) {  // for inserts
        this(-1, name, description);
    }

    // ---------- getters ----------
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    // ---------- setters ----------
    public void setName(String n) { this.name = n; }
    public void setDescription(String d) { this.description = d; }

}
