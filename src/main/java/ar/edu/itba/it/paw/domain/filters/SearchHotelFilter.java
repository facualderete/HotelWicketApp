package ar.edu.itba.it.paw.domain.filters;

public class SearchHotelFilter {
	private String name;
	private String city;
	private Integer priceMin;
	private Integer priceMax;
	private Integer categoryMin;
	private Integer categoryMax;
	private String type;
    private String breakfast;

	public SearchHotelFilter(String name, String city, Integer priceMin,
                             Integer priceMax, Integer categoryMin, Integer categoryMax,
                             String type, String breakfast) {
		this.name = name;
		this.city = city;
		this.priceMax = priceMax;
		this.priceMin = priceMin;
		this.categoryMax = categoryMax;
		this.categoryMin = categoryMin;
		this.type = type;
        this.breakfast = breakfast;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public Integer getPriceMin() {
		return priceMin;
	}

	public Integer getPriceMax() {
		return priceMax;
	}

	public Integer getCategoryMin() {
		return categoryMin;
	}

	public Integer getCategoryMax() {
		return categoryMax;
	}

	public String getType() {
		return type;
	}

    public String getBreakfast() {
        return breakfast;
    }
}
