package ar.edu.itba.it.paw.domain;

import javax.persistence.Entity;

@Entity
public class Picture extends PersistentEntity{

    byte[] picture;

    boolean isMain;

    public Picture(){}

    public Picture(byte[] picture) {
        this.picture = picture;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public boolean getMain() {
        return isMain;
    }

    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;

        Picture picture1 = (Picture) o;

        return this.getId() == picture1.getId();
    }
}
