package ks4.campus_contagion.models;

/**
 * This is the ExtractionZone model.
 * @author Joseph Kueny
 */
public class ExtractionZone {

    private Double longitude;
    private Double latitude;
    
	public ExtractionZone()
	{

	}
	
    /** 
	 * @param latitude : The latitude the zone is at.
     * @param longitude : The longitude the zone is at.
     */
    public ExtractionZone(Double latitude, Double longitude)
	{
		this.latitude = latitude;
        this.longitude = longitude;
	}
	
	/**
	 * 
	 * @return Double : The latitude
	 */
	public Double getLatitude()
	{
			return this.latitude;
	}

	/**
	 * 
	 * @param latitude : The latitude
	 */
	public void setLatitude(Double latitude)
	{
			this.latitude = latitude;
	}
    
    /**
	 * 
	 * @return Double : The longitude
	 */
	public Double getLongitude()
	{
			return this.longitude;
	}

	/**
	 * 
	 * @param longitude : The longitude
	 */
	public void setLongitude(Double longitude)
	{
			this.longitude = longitude;
	}

	public static ExtractionZone generateZone()
    {
        return new ExtractionZone(LocalMath.generateLatitude(), LocalMath.generateLongitude());
    }

}
