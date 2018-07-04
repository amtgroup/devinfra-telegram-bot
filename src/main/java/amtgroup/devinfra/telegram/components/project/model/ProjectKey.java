package amtgroup.devinfra.telegram.components.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Vitaly Ogoltsov
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public final class ProjectKey implements Serializable {

    @NotBlank
    @Size(max = 100)
    private String value;

    @JsonCreator
    public ProjectKey(String value) {
        Objects.requireNonNull(value);
        this.value = value.toUpperCase();
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }

}
