package rawe.gordon.com.business.network.responses;

/**
 * Created by gordon on 16/5/5.
 */
public class BaseResponse {
    protected boolean status;
    protected String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

