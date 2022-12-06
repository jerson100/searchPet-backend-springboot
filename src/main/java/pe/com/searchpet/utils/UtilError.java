package pe.com.searchpet.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;

import java.util.Comparator;
import java.util.List;

public class UtilError {
    private static Logger LOG = LoggerFactory.getLogger(UtilError.class);
    public static final Comparator<FieldError> FIELD_ORDER_COMPARATOR = new Comparator<>() {
        private static final List<String> FIELDS_WITH_ORDER = List.of("NotNull", "NotBlank", "Size");

        @Override
        public int compare(FieldError fe1, FieldError fe2) {
            int field1Index = FIELDS_WITH_ORDER.indexOf(fe1.getCode());
            int field2Index = FIELDS_WITH_ORDER.indexOf(fe2.getCode());
            return field1Index - field2Index;
        }
    };
}
