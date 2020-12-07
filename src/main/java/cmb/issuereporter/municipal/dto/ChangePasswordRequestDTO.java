package cmb.issuereporter.municipal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDTO {
    private String userId;

    private String oldPassword;

    private String newPassword;
}
