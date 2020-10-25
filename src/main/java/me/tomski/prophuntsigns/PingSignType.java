package me.tomski.prophuntsigns;

public enum PingSignType
{
    JOINSIGN("JOINSIGN", 0, "JOINSIGN"), 
    INFOSIGN("INFOSIGN", 1, "INFOSIGN");
    
    private String typeName;
    
    private PingSignType(final String s, final int n, final String typeName) {
        this.typeName = typeName;
    }
    
    public String stringValue() {
        return this.typeName;
    }
}
