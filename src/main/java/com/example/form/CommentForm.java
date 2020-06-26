package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CommentForm {
	
	@NotBlank(message = "コメント者名を入力して下さい")
	@Size(min = 1, max = 50, message = "コメント者名は1文字以上50文字未満で入力してください")
	private String name;
	@NotBlank(message = "コメント内容を入力して下さい")
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
