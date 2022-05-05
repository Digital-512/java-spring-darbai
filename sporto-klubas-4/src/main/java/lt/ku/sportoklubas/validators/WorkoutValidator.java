package lt.ku.sportoklubas.validators;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class WorkoutValidator {

    private Integer id;

    @NotBlank(message = "Pavadinimo laukelis negali būti tuščias.")
    @Length(min = 3, max = 128, message = "Pavadinimas turi būti ilgesnis nei 3 simboliai ir trumpesnis už 128 simbolius.")
    private String name;

    @NotNull(message = "Datos laukelis negali būti tuščias.")
    private LocalDateTime date;

    @NotNull(message = "Vietų skaičiaus laukelis negali būti tuščias.")
    @Min(value = 0, message = "Vietų skaičius negali būti neigiamas.")
    private Integer places;

    @NotBlank(message = "Adreso laukelis negali būti tuščias.")
    private String location;

    private MultipartFile document = null;

    public WorkoutValidator() {
        this.date = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date.toString().substring(0, 16);
    }

    public void setDate(String date) {
        this.date = LocalDateTime.parse(date);
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MultipartFile getDocument() {
        return document;
    }

    public void setDocument(MultipartFile document) {
        this.document = document;
    }
}
