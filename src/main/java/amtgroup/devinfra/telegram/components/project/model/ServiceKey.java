package amtgroup.devinfra.telegram.components.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Vitaly Ogoltsov
 */
@RequiredArgsConstructor(staticName = "of", onConstructor = @__(@JsonCreator))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public final class ServiceKey implements Serializable {

    @NonNull
    @NotBlank
    @Size(max = 100)
    private String value;

    @Override
    @JsonValue
    public String toString() {
        return value;
    }

}
