package ru.epam.miniparking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Getter
public final class OfficeDTO extends BaseDto {
    @NotNull
    private String officeTitle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private List<Long> locationIds;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Nullable
    private List<Long> driverIds;

    public OfficeDTO(Long id, @NotNull String officeTitle, @NotNull List<Long> locationIds,
                     @NotNull List<Long> driverIds) {
        super(id);
        this.officeTitle = officeTitle;
        this.locationIds = locationIds;
        this.driverIds = driverIds;
    }
}
