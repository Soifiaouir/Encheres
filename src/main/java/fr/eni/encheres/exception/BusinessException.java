package fr.eni.encheres.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private List<String> clefsExternalisations;

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
}
