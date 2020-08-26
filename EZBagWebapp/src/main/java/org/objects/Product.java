package org.objects;

public class Product {
    private String name;
    private String productCode;
    private float price;
    public Product(String aName, String aProductCode, float aPrice)
    {
        init(aName, aProductCode, aPrice);
    }

    public Product()
    {
        init("", "", -1);
    }

    public void init(String aName, String aProductCode, float aPrice)
    {
        setName(aName);
        setProductCode(aProductCode);
        setPrice(aPrice);
    }

    public String getName()
    {
        return this.name;
    }
    public void setName(String aName)
    {
        this.name = aName;
    }
    public String getProductCode()
    {
        return this.productCode;
    }
    public void setProductCode(String aProductCode)
    {
        this.productCode = aProductCode;
    }
    public float getPrice()
    {
        return this.price;
    }
    public void setPrice(float aPrice)
    {
        this.price = aPrice;
    }

}
