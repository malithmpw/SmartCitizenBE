package cmb.issuereporter.municipal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomError {

    private int errorCode;

    private String message;

    public CustomError(){

    }

    public CustomError(int errorCode, String message){
        this.errorCode=errorCode;
        this.message = message;
    }
}
