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
import java.nio.charset.StandardCharsets;
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
        this.value = Base64.encodeBase64String(Long.toString(longValue).getBytes(StandardCharsets.UTF_8));
    }

    @JsonCreator
    public TelegramChatId(String value) {
        Objects.requireNonNull(value);
        this.value = value;
        this.longValue = Long.parseLong(new String(Base64.decodeBase64(this.value), StandardCharsets.UTF_8));
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
