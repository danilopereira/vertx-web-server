package com.github.danilopereira.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelCompany {
    private String id;
    private String name;
    private String logo;
    private String isActive;
    private String slug;

}
