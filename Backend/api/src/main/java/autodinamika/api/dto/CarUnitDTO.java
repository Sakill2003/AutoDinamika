package autodinamika.api.dto;

public class CarUnitDTO {
    private String chasis;
    private String displayName;
    private String image;

    public String getChasis() {
        return this.chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}