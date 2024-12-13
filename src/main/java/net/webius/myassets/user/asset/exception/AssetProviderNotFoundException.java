package net.webius.myassets.user.asset.exception;

import net.webius.myassets.exception.ManagedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssetProviderNotFoundException extends ManagedException {
    public AssetProviderNotFoundException(String message) {
        super(message);
    }
}
