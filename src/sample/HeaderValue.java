package sample;

public class HeaderValue
{

    private String stringValue;

    public HeaderValue(String value)
    {
        this.stringValue = value;
    }

    public String stringValue()
    {
        return stringValue;
    }

    public int integerValue()
    {
        return Integer.parseInt(stringValue);
    }
}
