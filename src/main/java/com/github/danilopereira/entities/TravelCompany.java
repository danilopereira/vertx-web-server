package com.github.danilopereira.entities;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelCompany {
    private Long id;
    private String name;
    private String slug;
    private String logo;
    private Boolean isActive;

    public TravelCompany(JsonObject jsonObject) {
        this.id = jsonObject.getLong("id");
        this.name = jsonObject.getString("name");
        this.slug = jsonObject.getString("slug");
        this.isActive = jsonObject.getBoolean("is_active");
        this.logo = jsonObject.getString("logo");
    }
}
