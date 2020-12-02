package cmb.issuereporter.municipal.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringArrayConverter implements AttributeConverter<String[], String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String[] imageURL) {

        try {
            String s = objectMapper.writeValueAsString(imageURL);
            return s;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String[] convertToEntityAttribute(String databaseDataAsJSONString) {

        try {
            return objectMapper.readValue(databaseDataAsJSONString, String[].class);
        } catch (Exception ex) {
            return null;
        }
    }

}
