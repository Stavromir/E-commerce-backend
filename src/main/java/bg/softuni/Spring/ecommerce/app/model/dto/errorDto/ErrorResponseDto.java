package bg.softuni.Spring.ecommerce.app.model.dto.errorDto;

public class ErrorResponseDto {

    private String message;
    private String[] data;
    private Boolean success;

    public String getMessage() {
        return message;
    }

    public ErrorResponseDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public String[] getData() {
        return data;
    }

    public ErrorResponseDto setData(String[] data) {
        this.data = data;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public ErrorResponseDto setSuccess(Boolean success) {
        this.success = success;
        return this;
    }
}
