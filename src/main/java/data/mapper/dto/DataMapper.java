package data.mapper.dto;

import data.mapper.entity.Record;
import lombok.Data;


import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DataMapper implements Serializable {

    @NotNull(message = "timestamp must not be null")
    @Positive(message = "providerId must be positive integer")
    private Long providerId;
    @Valid
    private List<Record> data = new ArrayList<>();
}
