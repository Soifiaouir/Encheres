package fr.eni.encheres.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private List<String> clefsExternalisations;
    private final Map<String, String> fieldErrors = new HashMap<>();


    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public List<String> getClefsExternalisations() {
        return clefsExternalisations;
    }

    public void add(String clef) {
        if (clefsExternalisations == null) {
            clefsExternalisations = new ArrayList<>();
        }
        clefsExternalisations.add(clef);
    }
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
    public void addFieldError(String fieldName, String code) {
        fieldErrors.put(fieldName, code);
    }
}
