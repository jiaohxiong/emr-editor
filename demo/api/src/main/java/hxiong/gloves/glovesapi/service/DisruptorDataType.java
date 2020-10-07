package hxiong.gloves.glovesapi.service;

public enum DisruptorDataType {
    DOC("DOC2020", 1), PATIENTDOC("PATIENTDOC2020", 2),
    DATASOURCE("DATASOURCE2020", 3), DOCTYPE("DOCTYPE2020", 4),
    DEPT("DEPT2020", 4);
    // 成员变量  
    private String key;  
    private int index;
    private DisruptorDataType(String key, int index) {  
        this.key = key;  
        this.index = index;  
    }
    public static String getKey(int index) {  
        for (DisruptorDataType c : DisruptorDataType.values()) {  
            if (c.getIndex() == index) {  
                return c.key;  
            }  
        }  
        return null;  
    } 
    public String getKey() {  
        return key;  
    }  
    public void setKey(String key) {  
        this.key = key;  
    }  
    public int getIndex() {  
        return index;  
    }  
    public void setIndex(int index) {  
        this.index = index;  
    } 
}
