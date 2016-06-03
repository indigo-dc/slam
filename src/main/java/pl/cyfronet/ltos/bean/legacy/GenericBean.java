package pl.cyfronet.ltos.bean.legacy;

/**
 * Created by lpalonek on 04.12.14.
 */
public class GenericBean<T> {
    private Status status;

    private T type;

    private String message;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getType() {
        return type;
    }

    public void setType(T type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
