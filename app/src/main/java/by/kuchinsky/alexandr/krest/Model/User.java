package by.kuchinsky.alexandr.krest.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "users")
public class User {
   @PrimaryKey(autoGenerate = true)
   @NonNull
    @ColumnInfo(name = "id")
    private int id;

   @ColumnInfo(name = "name")
    private String name;

   @ColumnInfo(name="mail")
    private String email;


    public User() {
    }

@Ignore
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringBuilder(name).append("\n").append(email).toString();
    }
}
