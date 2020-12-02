package cmb.issuereporter.municipal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String phoneNo;
    private String password;
}
