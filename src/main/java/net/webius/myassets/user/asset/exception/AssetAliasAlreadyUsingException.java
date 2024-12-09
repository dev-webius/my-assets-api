package net.webius.myassets.user.asset.exception;

import net.webius.myassets.exception.ManagedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AssetAliasAlreadyUsingException extends ManagedException {
    public AssetAliasAlreadyUsingException(String message) {
        super(message);
    }
}
