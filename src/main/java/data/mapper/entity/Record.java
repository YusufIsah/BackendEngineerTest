package data.mapper.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;


@Data
public class Record implements Serializable {
    @NotEmpty(message = "name must not be null or empty")
    private String name;

    @NotNull(message = "age must not be null")
    @Positive(message = "age must be positive integer")
    private Integer age;

    @NotNull(message = "timestamp must not be null")
    @Positive(message = "timestamp must be positive long")
    private Long timestamp;
}
