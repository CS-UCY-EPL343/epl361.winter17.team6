
import java.*;

//Created by steph

abstract public class AbstractResponse {

    private ServiceCaller sc;
    abstract public String getResponse();

    public void setServiceCaller (ServiceCaller sc)
    {
        this.sc=  sc;
    };

    public ServiceCaller getServiceCaller()
    {
        return sc;
    };


}
