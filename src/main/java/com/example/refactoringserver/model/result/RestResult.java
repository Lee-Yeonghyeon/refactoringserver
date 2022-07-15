package com.example.refactoringserver.model.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown=true) // ignore unknown properties when deserialize
@JsonInclude(JsonInclude.Include.NON_NULL) // not null only
public class RestResult implements Serializable {

    private static final long serialVersionUID = 5786981873269437420L;

    @JsonIgnore
    private boolean success = false;

    // error id
    private String id;

    // error message
    private String message;

    private Map<String, Object> data;

    private List<?> list;

    public RestResult() { }

    public RestResult(Map<String, Object> data) {
        this.data = data;
    }

    public RestResult setData(Map<String, Object> data) {

        this.data = data;
        return this;
    }
}