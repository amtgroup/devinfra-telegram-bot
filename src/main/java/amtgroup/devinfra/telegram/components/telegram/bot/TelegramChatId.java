package amtgroup.devinfra.telegram.components.telegram.bot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/**
 * @author Vitaly Ogoltsov
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public final class TelegramChatId implements Serializable {

    @NotBlank
    @Size(max = 100)
    private String value;

    @NotNull
    private Long longValue;


    public TelegramChatId(Long longValue) {
        Objects.requireNonNull(longValue);
        this.longValue = longValue;
        this.value = Long.toUnsignedString(longValue, 16);
    }

    @JsonCreator
    public TelegramChatId(String value) {
        Objects.requireNonNull(value);
        this.value = value;
        this.longValue = Long.parseUnsignedLong(value, 16);
    }


    public long longValue() {
        return longValue;
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }

}
