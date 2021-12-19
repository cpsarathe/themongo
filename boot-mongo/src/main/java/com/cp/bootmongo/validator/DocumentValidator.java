package com.cp.bootmongo.validator;

import java.util.List;

public interface DocumentValidator {
    public List<DocumentError> validate(Object obj);
}
