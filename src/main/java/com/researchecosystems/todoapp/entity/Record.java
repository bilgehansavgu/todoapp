package com.researchecosystems.todoapp.entity;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    //{"owner":"Mary","permissionType":"PUBLIC"}
    private String owner;
    private DatasetAccessType permissionType;

    public static Record fromJson(String json) {
        return new Gson().fromJson(json, Record.class);
    }
}
