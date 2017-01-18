package com.learnvest.checklistapi;

import static com.learnvest.checklistapi.ChecklistApiConstants.*;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jerome Simmonds
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ChecklistApiResponse<T> implements Serializable {

	private static final long serialVersionUID = 4040010944372764018L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChecklistApiResponse.class);
	
	public static final int HTTP_STATUS_CODE_422 = 422; // From WebDAV but becoming widely used
	public static final String HTTP_STATUS_CODE_422_MESSAGE = "Unprocessable Entity"; // From WebDAV but becoming widely used

	public static enum Status {
		SUCCESS("success"),
		ERROR("error"),
		WARNING("warning");

		private final String value;

	    private Status(final String value) {
	        this.value = value;
	    }

	    @Override
	    public String toString() {
	        return value;
	    }
	}

	@JsonProperty(STATUS)
	protected String status = Status.SUCCESS.toString();
	@JsonProperty(ERROR)
	protected Error error;
	@JsonProperty(DATA)
	protected T data;
	@JsonProperty(METADATA)
	protected Metadata metadata;

	public ChecklistApiResponse() {
		super();
	}

	public ChecklistApiResponse(Status status) {
		super();
		this.status = status.toString();
	}

	public ChecklistApiResponse(Status status, T data) {
		super();
		this.status = status.toString();
		this.data = data;
	}

	public ChecklistApiResponse(Error error) {
		this.status = Status.ERROR.toString();
		this.error = error;
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public void setError(int code, String message) {
		this.error = new Error(code, message);
	}
	public Error getError() {
		return error;
	}
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status.toString();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(super.toString()).append(SPACE).append(BRACE_OPEN)
			.append(STATUS).append(COLON).append(SPACE).append(this.status.toString())
			.append(BRACE_CLOSE);
		return result.toString();
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonInclude(Include.NON_NULL)
	public static class Error {

		public static final int ERRORCODE_SERIALIZATION = 1;
		public static final int ERRORCODE_AUTHENTICATION = 2;
		public static final int ERRORCODE_AUTHORIZATION = 3;
		public static final int ERRORCODE_MISSINGPARAMETER = 4;
		public static final int ERRORCODE_VALIDATION = 5;
		public static final int ERRORCODE_SERVER = 6;
		public static final int ERRORCODE_CLIENT = 7;
		public static final int ERRORCODE_UNKNOWN = 8;
		public static final String ERRORMESSAGE_RESOURCENOTFOUND = "Resource not found or not allowed";
		public static final String ERRORMESSAGE_VALIDATION = "Validation error(s)";

		@JsonProperty(CODE)
		@JsonInclude(Include.NON_DEFAULT)
		private int code;
		@JsonProperty(MESSAGE)
		private String message;
		@JsonProperty(PATH)
		private String path;
		@JsonProperty(ERRORS) // List of errors with paths, for example "errors": [{"path": "/email", "message": "Invalid email address"}, ...]
		private List<Error> errors;

		public Error() {
			super();
		}

		public Error(int code, String message) {
			super();
			this.code = code;
			this.message = message;
		}

		// Error containing list of errors (with path)
		public Error(int code, String message, List<Error> errors) {
			super();
			this.code = code;
			this.message = message;
			this.errors = errors;
		}

		// For the optional list of errors under Error
		public Error(String message, String path) {
			super();
			this.message = message;
			this.path = path;
		}
		
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public List<Error> getErrors() {
			return errors;
		}
		public void setErrors(List<Error> errors) {
			this.errors = errors;
		}

		@Override
		public String toString() {
			StringBuilder result = new StringBuilder(super.toString()).append(SPACE).append(BRACE_OPEN)
				.append(CODE).append(COLON).append(SPACE).append(code).append(COMMA).append(SPACE)
				.append(MESSAGE).append(COLON).append(SPACE).append(message).append(BRACE_CLOSE);
			return result.toString();
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonInclude(Include.NON_NULL)
	public static class Metadata {
		
		public static final String TOTAL_AVAILABLE = "totalAvailable";
		
		// Using primitives to ignore null values
		
		@JsonProperty(TOTAL_AVAILABLE)
		@JsonInclude(Include.NON_DEFAULT)
		protected Integer totalAvailable;
		@JsonProperty(PAGE)
		@JsonInclude(Include.NON_DEFAULT)
		protected Integer page;
		@JsonProperty(PAGE_SIZE)
		@JsonInclude(Include.NON_DEFAULT)
		protected Integer pageSize;

		public Integer getTotalAvailable() {
			return totalAvailable;
		}
		public void setTotalAvailable(Integer totalAvailable) {
			this.totalAvailable = totalAvailable;
		}
		public Integer getPage() {
			return page;
		}
		public void setPage(Integer page) {
			this.page = page;
		}
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
	}
}
