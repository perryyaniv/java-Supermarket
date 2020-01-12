public class FoodItem
{
    private final int MIN_CATALOGUE_NUMBER = 1000;
    private final int MAX_CATALOGUE_NUMBER = 9999;
    private final int MIN_QUANTITY = 0;
    private final int MIN_PRICE = 1;
    private final String DEFAULT_NAME = "item";

    private String _name;
    private long _catalogueNumber;
    private int _quantity;
    private Date _productionDate;
    private Date _expiryDate;
    private int _minTemperture;
    private int _maxTemperture;
    private int _price;

    public FoodItem(String name, long catalogueNumber, int quantity, Date productionDate, Date expiryDate, int minTemperture, int maxTemperture, int price)
    {
        // if expiry date is before production date, set expiry to day after production date
        if (productionDate.after(expiryDate))
            expiryDate = productionDate.tomorrow();

        // if min temperture is higher than max temperture, swap them
        if (maxTemperture < minTemperture)
        {
            int temp = minTemperture;
            minTemperture = maxTemperture;
            maxTemperture = temp;
        }

        // catalogueNumber must be 4 digits
        if (catalogueNumber < MIN_CATALOGUE_NUMBER || catalogueNumber > MAX_CATALOGUE_NUMBER)
            catalogueNumber = MAX_CATALOGUE_NUMBER;

        // quantity must be a non-negative number
        if (quantity < MIN_QUANTITY)
            quantity = MIN_QUANTITY;

        // if name is empty, set it to "item"
        if(name == null || name.isEmpty())
            name = DEFAULT_NAME;

        // price must be a positive number
        if (price < MIN_PRICE)
            price = MIN_PRICE;

        _name = name;
        _catalogueNumber = catalogueNumber;
        _quantity = quantity;
        _productionDate = productionDate;
        _expiryDate = expiryDate;
        _minTemperture = minTemperture;
        _maxTemperture = maxTemperture;
        _price = price;
    }

    public FoodItem(FoodItem other)
    {
        _name = other.getName();
        _catalogueNumber = other.getCatalogueNumber();
        _quantity = other.getQuantity();
        _productionDate = other.getProductionDate();
        _expiryDate = other.getExpiryDate();
        _minTemperture = other.getMinTemperature();
        _maxTemperture = other.getMaxTemperature();
        _price = other.getPrice();
    }

    public String getName()
    {
        return _name;
    }

    public long getCatalogueNumber()
    {
        return _catalogueNumber;
    }

    public int getMinTemperature()
    {
        return _minTemperture;
    }

    public int getMaxTemperature()
    {
        return _maxTemperture;
    }

    public int getQuantity()
    {
        return _quantity;
    }

    public void setQuantity(int quantity)
    {
        if (quantity >= MIN_QUANTITY)
            _quantity = quantity;
    }

    public Date getProductionDate()
    {
        return _productionDate;
    }

    public void setProductionDate(Date date)
    {
        if (date.before(_expiryDate))
            _productionDate = date;
    }

    public Date getExpiryDate()
    {
        return _expiryDate;
    }

    public void setExpiryDate(Date date)
    {
        if (date.after(_productionDate))
            _expiryDate = date;
    }

    public int getPrice()
    {
        return _price;
    }

    public void setPrice(int price)
    {
        if (price >= MIN_PRICE)
            _price = price;
    }

    public boolean equals(FoodItem other)
    {
        return  _name == other.getName() &&
        _catalogueNumber == other.getCatalogueNumber() &&
        _productionDate == other.getProductionDate() &&
        _expiryDate == other.getExpiryDate() &&
        _minTemperture == other.getMinTemperature() &&
        _maxTemperture == other.getMaxTemperature() &&
        _price == other.getPrice();
    }

    public boolean isFresh(Date date)
    {
        return (date.after(_productionDate) && 
            (date.before(_expiryDate) || date.equals(_expiryDate)));
    }

    public String toString()
    {
        return "FoodItem: " + _name + "\tCatalogue Number: " + _catalogueNumber + "\tProductionDate: " + _productionDate + "\tExpiryDate: " + _expiryDate + "\tQuantity: " + _quantity;
    }

    public boolean olderFoodItem(FoodItem other)
    {
        return _productionDate.before(other.getProductionDate());
    }

    // calculate how many items can be bought with a specific sum of money, respecting stock quantity
    public int howManyItems(int sumOfMoney)
    {
        int quantity = sumOfMoney / _price;
        return sumOfMoney / _price <= _quantity ? quantity : _quantity;
    }

    public boolean isCheaper(FoodItem other)
    {
        return _price < other.getPrice();
    }
}
