package model;

import org.bson.Document;
import org.bson.types.ObjectId;

public class UserRecord implements SerialasableToJSON {
    private String id;
    private String firstName;
    private String secondName;

    public UserRecord(Document doc) {
        id = doc.getObjectId("_id").toHexString();
        firstName = doc.getString("firstName");
        secondName = doc.getString("secondName");
    }

    public UserRecord(String id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public UserRecord(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Document toDocument() {
        Document doc = new Document();
        if (id != null) doc.put("_id", new ObjectId(id));
        doc.put("firstName", firstName);
        doc.put("secondName", secondName);
        return doc;
    }

    public String toJSON() {
        return toDocument().toJson();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}

