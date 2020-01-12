public class Date
{
    private final int DEFAULT_DAY = 1;
    private final int DEFAULT_MONTH = 1;
    private final int DEFAULT_YEAR = 2000;
    private final int MIN_VALID_YEAR = 1; 
    private final int MAX_VALID_YEAR = 9999;
    private final int MIN_VALID_MONTH = 1;
    private final int MAX_VALID_MONTH = 12;
    private final int MIN_VALID_DAYS_IN_MONTH = 1;
    private final int MAX_VALID_DAYS_IN_MONTH = 31;
    private final int FEBRUARY = 2;
    private final int APRIL = 4;
    private final int JUNE = 6;
    private final int SEPTEMBER = 9;
    private final int NOVEMBER = 11;
    private final int FEBRUARY_MAX_DAYS_LEAP_YEAR = 29;
    private final int FEBRUARY_MAX_DAYS_NOT_LEAP_YEAR = 28;
    private final int MAX_DAYS_IN_SPECIAL_MONTH = 30;

    private int _day;
    private int _month;
    private int _year;

    public Date(int day, int month, int year)
    {
        var isDateValid = isDateValid(day, month, year);

        if (isDateValid)
        {
            _day = day;
            _month = month;
            _year = year;
        }
        else
        {
            _day = DEFAULT_DAY;
            _month = DEFAULT_MONTH;
            _year = DEFAULT_YEAR;
        }
    }

    public Date(Date other)
    {
        _day = other.getDay();
        _month = other.getMonth();
        _year = other.getYear();
    }

    public int getDay()
    {
        return _day;
    }

    public void setDay(int day)
    {
        if (isDateValid(day, _month, _year))
            _day = day;
    }

    public int getMonth()
    {
        return _month;
    }

    public void setMonth(int month)
    {
        if (isDateValid(_day, month, _year))
            _month = month;
    }

    public int getYear()
    {
        return _year;
    }

    public void setYear(int year)
    {
        if (isDateValid(_day, _month, year))
            _year = year;
    }

    public boolean equals(Date other)
    {
        return (_day == other.getDay() &&
            _month == other.getMonth() &&
            _year == other.getYear());
    }

    public boolean before(Date other)
    {
        if (_year > other.getYear())
            return false;
        if (_year < other.getYear())
            return true;

        if (_month > other.getMonth())
            return false;
        if (_month < other.getMonth())
            return true;

        if (_day > other.getDay())
            return false;
        if (_day < other.getDay())
            return true;

        return false;
    }

    public boolean after(Date other)
    {
        return !before(other);
    }

    public int difference(Date other)
    {
        int myDaysSinceBeginning = numberOfDaysSinceBeginning(_day, _month, _year);
        int otherDaysSinceBeginning = numberOfDaysSinceBeginning(other.getDay(), other.getMonth(), other.getYear());
        return Math.abs(myDaysSinceBeginning - otherDaysSinceBeginning);
    }

    public String toString()
    {
        return (_day < 10 ? ("0" + _day) : _day) + "/" +
        (_month < 10 ? ("0" + _month) : _month) + "/" +
        _year;
    }

    public Date tomorrow()
    {
        Date resultDate = new Date(this);

        // if this is not 31 of december
        if (_month < MAX_VALID_DAYS_IN_MONTH && _day < MAX_VALID_DAYS_IN_MONTH)
        {
            // find max days in object's month
            int maxDaysInMonth = MAX_VALID_DAYS_IN_MONTH;

            if (isSpecialMonth(_month))
                maxDaysInMonth = MAX_DAYS_IN_SPECIAL_MONTH;

            if (_month == FEBRUARY)
                maxDaysInMonth = isLeapYear(_year) ? FEBRUARY_MAX_DAYS_LEAP_YEAR : FEBRUARY_MAX_DAYS_NOT_LEAP_YEAR;
            
            if (_day + 1 > maxDaysInMonth)
            {
                resultDate.setDay(1);
                resultDate.setMonth(_month+1);
            }
            else
                resultDate.setDay(_day+1);
        }
        else // Happy new year!!!
        {
            resultDate.setDay(1);
            resultDate.setMonth(1);
            resultDate.setYear(_year+1);
        }

        return resultDate;
    }

    public int dayInWeek()
    {
        int D = _day;
        int M = _month;
        int year = _year;
        if (M < 3)
        {
            M = M + MAX_VALID_MONTH;
            year = --year;
        }
        int C = year / 100;
        int Y = year % 100;

        return (D + (26*(M+1))/10 + Y + Y/4 + C/4 - 2*C) % 7 ;
    }

    private boolean isSpecialMonth(int month)
    {
        return (month == APRIL || month == JUNE || month == SEPTEMBER || month == NOVEMBER); 
    }

    // computes the days count since the beginning of the Christian counting of years     
    private int numberOfDaysSinceBeginning (int day, int month, int year) 
    {     
        if (month < 3) {             
            year--;             
            month = month + 12;
        }          
        return 365 * year + year / 4 - year / 100 + year / 400 + ((month + 1) * 306) / 10 + (day - 62);    
    }     
    // validate date
    private boolean isDateValid(int d, int m, int y) 
    { 
        if (y > MAX_VALID_YEAR || y < MIN_VALID_YEAR) 
            return false;
        if (m < MIN_VALID_MONTH || m > MAX_VALID_MONTH) 
            return false; 
        if (d < MIN_VALID_DAYS_IN_MONTH || d > MAX_VALID_DAYS_IN_MONTH) 
            return false; 

        // check February, respecting leap year cases
        if (m == FEBRUARY)  
        { 
            if (isLeapYear(y)) 
                return (d <= FEBRUARY_MAX_DAYS_LEAP_YEAR); 
            else
                return (d <= FEBRUARY_MAX_DAYS_NOT_LEAP_YEAR); 
        } 

        // April, June, September and November days count must be >= 30
        if (isSpecialMonth(_month)) 
            return (d <= MAX_DAYS_IN_SPECIAL_MONTH); 

        return true; 
    } 
    // check if year is a leap year
    private boolean isLeapYear(int year)
    {
        if (year % 4 != 0)
            return false;
        else if (year % 100 != 0)
            return true;
        else if (year % 400 != 0)
            return false;
        else
            return true;
    }
}
