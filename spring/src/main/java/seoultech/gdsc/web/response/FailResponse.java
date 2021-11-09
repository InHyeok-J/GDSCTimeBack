package seoultech.gdsc.web.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FailResponse<T> extends BasicResponse{
    private boolean success = false;
    private T data;
    private String  message;


    public FailResponse( String message,T data) {
        this.data = data;
        this.message = message;
    }
}
