package br.com.managementSystem.configuration.swagger;

import org.springframework.lang.Nullable;

import io.swagger.annotations.ApiParam;

public class SwaggerPageable {

	@ApiParam(value = "Page to be loaded", example = "0")
	@Nullable
	private Integer page;
	
	@ApiParam(value = "Quantity of users", example = "0")
	@Nullable
	private Integer size;
	
	@ApiParam(value = "Ordering of users", example = "0")
	@Nullable
	private String sort;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
