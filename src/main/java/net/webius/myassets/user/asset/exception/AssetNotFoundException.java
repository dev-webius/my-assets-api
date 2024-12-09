package net.webius.myassets.user.asset.exception;

import net.webius.myassets.exception.ManagedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssetNotFoundException extends ManagedException {
    public AssetNotFoundException(String message) {
        super(message);
    }
}
