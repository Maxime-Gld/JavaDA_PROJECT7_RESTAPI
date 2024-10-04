package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NotBlank(message = "Name is mandatory")
    String name;

    @NotBlank(message = "Description is mandatory")
    String description;

    @NotBlank(message = "Json is mandatory")
    String json;

    @NotBlank(message = "Template is mandatory")
    String template;

    @NotBlank(message = "SqlStr is mandatory")
    String sqlStr;

    @NotBlank(message = "SqlPart is mandatory")
    String sqlPart;

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }
}
