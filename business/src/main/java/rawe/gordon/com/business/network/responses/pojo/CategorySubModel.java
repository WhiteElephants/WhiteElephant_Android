package rawe.gordon.com.business.network.responses.pojo;

/**
 * Created by gordon on 5/16/16.
 */
public class CategorySubModel {
    private String logo;

    private String tile;

    public String getLogo ()
    {
        return logo;
    }

    public void setLogo (String logo)
    {
        this.logo = logo;
    }

    public String getTile ()
    {
        return tile;
    }

    public void setTile (String tile)
    {
        this.tile = tile;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [logo = "+logo+", tile = "+tile+"]";
    }
}
