package autodinamika.api.dto;

public class RankedItemDTO {
    private String name;
    private int value; 

    public RankedItemDTO(String name, int value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}